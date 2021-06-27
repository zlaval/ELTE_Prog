package com.zlrx.elte.snake.model;

import com.zlrx.elte.snake.behaviour.Collider;
import com.zlrx.elte.snake.util.Const;

import java.awt.Rectangle;
import java.util.Random;

public abstract class RandomBaseModel {

    protected final Random random;

    protected Collider collider;

    public RandomBaseModel(Collider collider) {
        this.collider = collider;
        random = new Random(System.currentTimeMillis());
    }

    private boolean inSafeArea(Rectangle rectangle) {
        if (!useSafeArea()) {
            return false;
        }
        return rectangle.intersects(Const.safeArea);
    }

    protected boolean useSafeArea() {
        return false;
    }

    protected Rectangle createRectangle(int dimension) {
        Rectangle rect;
        do {
            var x = random.nextInt(Const.WIDTH - 200) + 100;
            var y = random.nextInt(Const.HEIGHT - 200) + 100;
            rect = new Rectangle(x, y, dimension, dimension);
        } while (collider.collideWithElements(rect) || inSafeArea(rect));
        return rect;
    }

}
