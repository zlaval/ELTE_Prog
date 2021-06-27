package com.zlrx.elte.slideout.view;

import com.zlrx.elte.slideout.behaviour.Game;
import com.zlrx.elte.slideout.behaviour.ViewMediator;
import com.zlrx.elte.slideout.model.Arrows;
import com.zlrx.elte.slideout.model.Dim2D;
import com.zlrx.elte.slideout.model.Player;
import com.zlrx.elte.slideout.resource.ImageContainer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.function.Consumer;

public class GameView extends JPanel {

    private final JPanel scorePanel = new JPanel();
    private final JPanel playGroundPanel = new JPanel(new BorderLayout());

    private final ScoreBoard scoreBoard;
    private final GameBoard gameBoard;

    private final ViewMediator mediator;

    private final Consumer<Player> endGameFn;

    public GameView(Dim2D grid, Consumer<Player> endGameFn) {
        setLayout(new BorderLayout());
        this.endGameFn = endGameFn;
        this.mediator = new ViewMediator(this::endGame);
        scoreBoard = new ScoreBoard();
        mediator.registerScoreBoard(scoreBoard);
        Game game = new Game(grid, mediator);
        mediator.registerGame(game);
        gameBoard = new GameBoard(grid, mediator);
        mediator.registerGameBoard(gameBoard);
        createUi();
    }

    private void createUi() {
        add(scorePanel, BorderLayout.NORTH);
        add(playGroundPanel, BorderLayout.CENTER);
        scorePanel.add(scoreBoard);
        addArrows();
        playGroundPanel.add(gameBoard, BorderLayout.CENTER);
    }

    private void addArrows() {
        addArrow(Arrows.UP, BorderLayout.NORTH);
        addArrow(Arrows.DOWN, BorderLayout.SOUTH);
        addArrow(Arrows.LEFT, BorderLayout.WEST);
        addArrow(Arrows.RIGHT, BorderLayout.EAST);
    }

    private void addArrow(Arrows arrow, String position) {
        ImageContainer imageContainer = ImageContainer.getInstance();
        var button = new JButton();
        button.setIcon(new ImageIcon(imageContainer.getImage(arrow.getImageKey())));
        button.setPreferredSize(new Dimension(100, 100));
        button.addActionListener(e -> mediator.move(arrow));
        button.setMnemonic(arrow.keyEvent());
        playGroundPanel.add(button, position);
    }

    private void endGame(Player player) {
        endGameFn.accept(player);
    }

}
