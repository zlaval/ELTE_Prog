package com.zlrx.elte.snake.util;

import java.awt.Rectangle;

public final class Const {

    private Const() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static final boolean ENABLE_GIZMOS = true;
    public static final int WIDTH = 1495;
    public static final int HEIGHT = 1000;
    public static final int SNAKE_BODY_DIMENSION = 20;
    public static final int APPLE_DIMENSION = 30;
    public static final int INITIAL_DELAY = 200;
    public static final Rectangle safeArea = new Rectangle(Const.WIDTH / 2 - 150, Const.HEIGHT / 2 - 150, 300, 300);

}
