package hu.elte.progtech.utils;

import lombok.Getter;

@Getter
public class Size {

    private final int width;
    private final int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size get(int width, int height) {
        return new Size(width, height);
    }

}
