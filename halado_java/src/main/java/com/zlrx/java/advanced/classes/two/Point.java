package com.zlrx.java.advanced.classes.two;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void shiftX(int x) {
        this.x += x;
    }

    public void shiftY(int y) {
        this.y += y;
    }

    public void shift(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
