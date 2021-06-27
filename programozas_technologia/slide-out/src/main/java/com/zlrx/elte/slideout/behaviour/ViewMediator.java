package com.zlrx.elte.slideout.behaviour;

import com.zlrx.elte.slideout.model.Arrows;
import com.zlrx.elte.slideout.model.Dim2D;
import com.zlrx.elte.slideout.model.GameData;
import com.zlrx.elte.slideout.model.Player;
import com.zlrx.elte.slideout.model.Rock;
import com.zlrx.elte.slideout.view.GameBoard;
import com.zlrx.elte.slideout.view.ScoreBoard;

import java.util.List;
import java.util.function.Consumer;

public class ViewMediator {

    private final Consumer<Player> endGameFn;

    private Game game;
    private GameBoard gameBoard;
    private ScoreBoard scoreBoard;

    public ViewMediator(Consumer<Player> endGameFn) {
        this.endGameFn = endGameFn;
    }

    public List<List<Rock>> getBoard() {
        return game.getBoard();
    }

    public Player getPlayer() {
        return game.getPlayer();
    }

    public void registerGame(Game game) {
        this.game = game;
    }

    public void selectTile(Dim2D dimension) {
        game.selectTile(dimension);
        scoreBoard.removeChooseLbl();
    }

    public void updateScores(GameData gameData) {
        scoreBoard.update(gameData);
    }

    public void move(Arrows arrow) {
        game.move(arrow);
        gameBoard.renderBoard();
        game.checkEndGame();
    }

    public void registerGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void registerScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void endGame(Player player) {
        endGameFn.accept(player);
    }

}
