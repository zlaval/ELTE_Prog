package com.zlrx.elte.snake.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.Rectangle;

@Getter
@Setter
@AllArgsConstructor
public class Bodypart {
    private Rectangle rectangle;
    private Direction direction;
}
