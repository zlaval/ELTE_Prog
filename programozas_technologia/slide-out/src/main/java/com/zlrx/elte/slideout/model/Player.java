package com.zlrx.elte.slideout.model;

import java.awt.Color;

public enum Player {
    WHITE("White player", Color.BLUE), BLACK("Black player", Color.YELLOW);

    private final String title;
    private final Color color;

    Player(String title, Color color) {
        this.title = title;
        this.color = color;
    }

    public String title() {
        return title;
    }

    public Color color() {
        return color;
    }
}
