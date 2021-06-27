package com.zlrx.elte.snake.model;

import com.zlrx.elte.snake.behaviour.Collider;
import com.zlrx.elte.snake.util.Const;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {

    private final List<Bodypart> snakeBody;
    private final Collider collider;
    private final Random random;

    public Snake(Collider collider) {
        this.collider = collider;
        this.collider.setSnake(this);
        random = new Random(System.currentTimeMillis());
        snakeBody = new ArrayList<>();
        Direction startDirection = Direction.values()[random.nextInt(4)];
        snakeBody.add(createBodyPart(Const.WIDTH / 2, Const.HEIGHT / 2, startDirection));
        eat();
    }

    public List<Bodypart> getSnakeBody() {
        return snakeBody;
    }

    public Bodypart getHead() {
        return snakeBody.get(0);
    }

    public void eat() {
        var headRect = getHead().getRectangle();
        snakeBody.add(1, createBodyPart(headRect.x, headRect.y, getHead().getDirection()));
        computeDirection(getHead());
    }

    public void calculateMoves() {
        Direction previous = getHead().getDirection();
        for (Bodypart bp : snakeBody) {
            computeDirection(bp);
            Direction tmp = bp.getDirection();
            bp.setDirection(previous);
            previous = tmp;
        }
    }

    public boolean handleEat() {
        boolean appleEaten = collider.testSnakeAppleCollision();
        if (appleEaten) {
            eat();
        }
        return appleEaten;
    }

    public boolean testCollision() {
        return collider.testSnakeCollision();
    }

    private Bodypart createBodyPart(int x, int y, Direction direction) {
        return new Bodypart(new Rectangle(x, y, Const.SNAKE_BODY_DIMENSION, Const.SNAKE_BODY_DIMENSION), direction);
    }

    private void computeDirection(Bodypart bp) {
        var rectangle = bp.getRectangle();
        Direction direction = bp.getDirection();
        switch (direction) {
            case UP -> rectangle.y -= Const.SNAKE_BODY_DIMENSION;
            case DOWN -> rectangle.y += Const.SNAKE_BODY_DIMENSION;
            case LEFT -> rectangle.x -= Const.SNAKE_BODY_DIMENSION;
            case RIGHT -> rectangle.x += Const.SNAKE_BODY_DIMENSION;
        }
    }

}
