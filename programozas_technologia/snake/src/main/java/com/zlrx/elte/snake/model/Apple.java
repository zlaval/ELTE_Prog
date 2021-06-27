package com.zlrx.elte.snake.model;

import com.zlrx.elte.snake.behaviour.Collider;
import com.zlrx.elte.snake.util.Const;

import java.awt.Rectangle;

public class Apple extends RandomBaseModel {

    private Rectangle apple;

    public Apple(Collider collider) {
        super(collider);
        this.collider.setApple(this);
        apple = createRectangle(Const.APPLE_DIMENSION);
    }

    public void newApple() {
        apple = createRectangle(Const.APPLE_DIMENSION);
    }

    public Rectangle getApple() {
        return apple;
    }
}
