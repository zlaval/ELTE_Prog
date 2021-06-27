package com.zlrx.elte.slideout.behaviour;

import com.zlrx.elte.slideout.model.Arrows;
import com.zlrx.elte.slideout.model.Dim2D;
import com.zlrx.elte.slideout.model.GameData;
import com.zlrx.elte.slideout.model.Player;
import com.zlrx.elte.slideout.model.Rock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Game implements Serializable {

    private final ViewMediator mediator;

    private final Dim2D dimension;

    private List<List<Rock>> board;

    private Player player = Player.WHITE;

    private Dim2D selectedTile = null;

    private int round;

    public Game(Dim2D dimension, ViewMediator mediator) {
        this.dimension = dimension;
        this.mediator = mediator;
        round = dimension.getRow() * 5;
        generateBoard();
        notifyViews();
    }

    private void generateBoard() {
        List<Rock> flatBoard = generateRandomList();
        board = IntStream.range(0, dimension.getRow())
                .mapToObj(i -> flatBoard.subList(i * dimension.getCol(), i * dimension.getCol() + dimension.getCol()))
                .collect(Collectors.toList());
    }

    private List<Rock> generateRandomList() {
        var tileCount = dimension.getRow() * dimension.getCol();
        List<Rock> flatBoard = new ArrayList<>(tileCount);

        IntStream.range(0, dimension.getRow()).forEach(i -> {
            flatBoard.add(Rock.WHITE);
            flatBoard.add(Rock.BLACK);
        });

        var emptyCount = tileCount - (dimension.getRow() * 2);
        IntStream.range(0, emptyCount).forEach(i -> flatBoard.add(null));

        Collections.shuffle(flatBoard);
        return flatBoard;
    }

    public List<List<Rock>> getBoard() {
        return this.board;
    }

    public Player getPlayer() {
        return player;
    }

    public void selectTile(Dim2D tile) {
        this.selectedTile = tile;
    }

    private long calculateRemaining(Rock rock) {
        return board.stream()
                .flatMap(Collection::stream)
                .filter(r -> r == rock)
                .count();
    }

    private Player getWinner(long whiteCount, long blackCount) {
        Player winner = null;
        if (blackCount > whiteCount) {
            winner = Player.BLACK;
        } else if (whiteCount > blackCount) {
            winner = Player.WHITE;
        }
        return winner;
    }

    private void notifyViews() {
        var black = calculateRemaining(Rock.BLACK);
        var white = calculateRemaining(Rock.WHITE);
        var gameData = GameData.builder()
                .blackRock(black)
                .whiteRock(white)
                .player(player)
                .remainingStep(round)
                .build();
        mediator.updateScores(gameData);
    }

    public void checkEndGame() {
        var black = calculateRemaining(Rock.BLACK);
        var white = calculateRemaining(Rock.WHITE);
        if (round <= 0 || black == 0 || white == 0) {
            mediator.endGame(getWinner(white, black));
        }
    }

    public boolean move(Arrows arrows) {
        if (selectedTile == null) {
            return false;
        }
        calculateNewBoard(arrows);
        round--;
        player = switchPlayer();
        selectedTile = null;
        notifyViews();
        return true;
    }

    private Player switchPlayer() {
        return switch (player) {
            case BLACK -> Player.WHITE;
            case WHITE -> Player.BLACK;
        };
    }

    private void calculateNewBoard(Arrows arrows) {
        switch (arrows) {
            case RIGHT, LEFT -> new RowModel(arrows).constructBoard();
            case UP, DOWN -> new ColModel(arrows).constructBoard();
        }
    }

    private abstract class SplitModel {
        List<Rock> model;
        Arrows arrow;

        public SplitModel(Arrows arrow) {
            this.arrow = arrow;
        }

        abstract void rebuild();

        void constructBoard() {
            switch (arrow) {
                case RIGHT, DOWN -> moveRight();
                case LEFT, UP -> moveLeft();
            }
        }

        private void moveRight() {
            for (int i = model.size() - 1; i > 0; --i) {
                model.set(i, model.get(i - 1));
            }
            model.set(0, null);
            rebuild();
        }

        private void moveLeft() {
            for (int i = 1; i < model.size(); ++i) {
                model.set(i - 1, model.get(i));
            }
            model.set(model.size() - 1, null);
            rebuild();
        }
    }

    private class RowModel extends SplitModel {

        public RowModel(Arrows arrows) {
            super(arrows);
            int row = selectedTile.getRow();
            model = new ArrayList<>();
            model.addAll(board.get(row));
        }

        @Override
        void rebuild() {
            board.set(selectedTile.getRow(), model);
        }
    }

    private class ColModel extends SplitModel {
        public ColModel(Arrows arrows) {
            super(arrows);
            int col = selectedTile.getCol();
            model = new ArrayList<>();

            for (List<Rock> row : board) {
                model.add(row.get(col));
            }
        }

        @Override
        void rebuild() {
            int col = selectedTile.getCol();
            for (int i = 0; i < board.size(); i++) {
                board.get(i).set(col, model.get(i));
            }
        }
    }

}
