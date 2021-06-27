package com.zlrx.elte.snake.behaviour;

import com.zlrx.elte.snake.model.Apple;
import com.zlrx.elte.snake.model.Bodypart;
import com.zlrx.elte.snake.model.Direction;
import com.zlrx.elte.snake.model.Rocks;
import com.zlrx.elte.snake.model.Score;
import com.zlrx.elte.snake.model.Snake;
import com.zlrx.elte.snake.repository.ScoreRepository;
import com.zlrx.elte.snake.util.Const;

import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class GameManager {

    private final Rocks rocks;
    private final Apple apple;
    private final Snake snake;
    private final Instant startTime;
    private final Runnable endGameAction;

    private Timer timer;
    private boolean dead = false;
    private Integer point = 0;

    public GameManager(Rocks rocks, Apple apple, Snake snake, Runnable endGameAction) {
        this.rocks = rocks;
        this.apple = apple;
        this.snake = snake;
        this.endGameAction = endGameAction;
        this.startTime = Instant.now();
    }

    public void keyPressed(KeyEvent event) {
        var head = snake.getHead();
        switch (event.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> head.setDirection(Direction.LEFT);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> head.setDirection(Direction.DOWN);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> head.setDirection(Direction.RIGHT);
            case KeyEvent.VK_W, KeyEvent.VK_UP -> head.setDirection(Direction.UP);
            case KeyEvent.VK_C -> useCheat();
        }
    }

    public void startTimer(ActionListener listener) {
        timer = new Timer(Const.INITIAL_DELAY, listener);
        timer.start();
    }

    public void checkGameEnd() {
        if (dead) {
            timer.stop();
        }
    }

    public void saveScore(String name) {
        var score = new Score(name, point);
        ScoreRepository sc = new ScoreRepository();
        sc.save(score);
    }

    public void goBackToMenu() {
        endGameAction.run();
    }

    public void tick() {
        moveSnake();
        checkEat();
        dead = snake.testCollision();
    }

    public long calculateElapsedSeconds() {
        return Duration.between(startTime, Instant.now()).getSeconds();
    }

    private void checkEat() {
        boolean eaten = snake.handleEat();
        if (eaten) {
            point++;
            apple.newApple();
            setSpeed(timer);
        }
    }

    private void setSpeed(Timer timer) {
        timer.setDelay(Math.max(50, Const.INITIAL_DELAY - 5 * snake.getSnakeBody().size()));
    }

    private void moveSnake() {
        if (!dead) {
            snake.calculateMoves();
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isDead() {
        return dead;
    }

    public Integer getPoint() {
        return point;
    }

    public Rectangle getAppleRect() {
        return apple.getApple();
    }

    public List<Bodypart> getSnakeBody() {
        return snake.getSnakeBody();
    }

    public List<Rectangle> getRockRects() {
        return rocks.getRocks();
    }

    private void useCheat() {
        if (Const.ENABLE_GIZMOS) {
            snake.eat();
        }
    }
}
