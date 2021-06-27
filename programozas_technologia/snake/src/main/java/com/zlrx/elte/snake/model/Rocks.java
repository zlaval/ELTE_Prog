package com.zlrx.elte.snake.model;

import com.zlrx.elte.snake.behaviour.Collider;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Rocks extends RandomBaseModel {

    private final List<Rectangle> rocks;

    public Rocks(Collider collider) {
        super(collider);
        this.collider.setRocks(this);
        rocks = new ArrayList<>();
        generateRocks();
    }

    private void generateRocks() {
        var rockCount = random.nextInt(10) + 5;
        for (int i = 0; i < rockCount; i++) {
            var dimension = random.nextInt(50) + 30;
            var rock = createRectangle(dimension);
            rocks.add(rock);
        }
    }

    public List<Rectangle> getRocks() {
        return rocks;
    }

    @Override
    protected boolean useSafeArea() {
        return true;
    }
}
