package com.zlrx.elte.snake.util;

public enum Images {

    BG("desert.jpg"),
    APPLE("apple.png"),
    ROCK("rock.png"),
    POW("pow.png"),
    COBRA("cobra_kai.png"),
    MENU_BG("menubg.jpg");

    private String path;

    Images(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
