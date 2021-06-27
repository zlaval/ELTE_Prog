package com.zlrx.elte.snake.behaviour;

import com.zlrx.elte.snake.model.Apple;
import com.zlrx.elte.snake.model.Bodypart;
import com.zlrx.elte.snake.model.Rocks;
import com.zlrx.elte.snake.model.Snake;
import com.zlrx.elte.snake.util.Const;

import java.awt.Rectangle;

public class Collider {

    private Snake snake;
    private Apple apple;
    private Rocks rocks;

    public boolean testSnakeCollision() {
        var headRect = snake.getHead().getRectangle();
        if (testCollidedMapX(headRect) || testCollidedMapY(headRect)) {
            return true;
        }
        return collideWithElements(headRect);
    }

    private boolean testCollidedMapX(Rectangle head) {
        return head.x < 0 || head.x > Const.WIDTH - Const.SNAKE_BODY_DIMENSION;
    }

    private boolean testCollidedMapY(Rectangle head) {
        return head.y < 0 || head.y > Const.HEIGHT - 3 * Const.SNAKE_BODY_DIMENSION;
    }

    public boolean testSnakeAppleCollision() {
        var headRect = snake.getHead().getRectangle();
        return headRect.intersects(apple.getApple());
    }

    public boolean collideWithElements(Rectangle rectangle) {
        var result = testSnakeCollision(rectangle);
        result |= testRocksCollision(rectangle);
        return result;
    }

    private boolean testRocksCollision(Rectangle rectangle) {
        if (rocks != null) {
            return rocks.getRocks().stream().anyMatch(rectangle::intersects);
        }
        return false;
    }

    private boolean testSnakeCollision(Rectangle rectangle) {
        return snake.getSnakeBody().stream()
                .map(Bodypart::getRectangle)
                .filter(b -> b != rectangle)
                .anyMatch(rectangle::intersects);
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void setRocks(Rocks rocks) {
        this.rocks = rocks;
    }
}
