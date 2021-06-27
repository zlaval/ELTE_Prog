package com.zlrx.elte.snake;

import com.zlrx.elte.snake.behaviour.Collider;
import com.zlrx.elte.snake.behaviour.GameManager;
import com.zlrx.elte.snake.configuration.DatabaseConfiguration;
import com.zlrx.elte.snake.model.Apple;
import com.zlrx.elte.snake.model.Rocks;
import com.zlrx.elte.snake.model.Snake;
import com.zlrx.elte.snake.util.Const;
import com.zlrx.elte.snake.view.Ground;
import com.zlrx.elte.snake.view.Menu;
import com.zlrx.elte.snake.view.ScoreBoard;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {

    private Menu menu;
    private Ground ground;
    private ScoreBoard scoreBoard;

    public static void main(String[] args) {
        DatabaseConfiguration.getINSTANCE().startDatabase();
        new Main();
    }

    public Main() throws HeadlessException {
        setTitle("Snake");
        setSize(Const.WIDTH, Const.HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addExitListener();
        setVisible(true);
        setResizable(false);
        startMenu();
    }

    private void addExitListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseConfiguration.getINSTANCE().stopDatabase();
                System.exit(0);
            }
        });
    }

    private void cleanUp() {
        removePanel(menu);
        removePanel(scoreBoard);
        removePanel(ground);
        menu = null;
        scoreBoard = null;
        ground = null;
        validate();
    }

    private void removePanel(JPanel jPanel) {
        if (jPanel != null) {
            remove(jPanel);
            jPanel.removeAll();
            jPanel.resetKeyboardActions();
            jPanel.revalidate();
            revalidate();
        }
    }

    private void startMenu() {
        cleanUp();
        menu = new Menu(this::startGame, this::showScores);
        this.add(menu);
        validate();
    }

    private void showScores() {
        cleanUp();
        scoreBoard = new ScoreBoard(this::startMenu);
        add(scoreBoard);
        validate();
    }

    private void startGame() {
        cleanUp();
        var collisionDetector = new Collider();
        var snake = new Snake(collisionDetector);
        var apple = new Apple(collisionDetector);
        var rocks = new Rocks(collisionDetector);
        var gameManager = new GameManager(rocks, apple, snake, this::startMenu);
        ground = new Ground(gameManager);
        add(ground);
        ground.setFocusable(true);
        ground.requestFocus();
        validate();
    }

}
