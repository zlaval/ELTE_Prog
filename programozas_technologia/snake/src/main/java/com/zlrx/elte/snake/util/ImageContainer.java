package com.zlrx.elte.snake.util;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageContainer {

    private static final ImageContainer INSTANCE = new ImageContainer();

    public static ImageContainer getInstance() {
        return INSTANCE;
    }

    private Map<Images, Image> images = new HashMap<>();

    @SneakyThrows
    public ImageContainer() {
        for (Images key : Images.values()) {
            var image = ImageIO.read(ClassLoader.getSystemResource(key.path()));
            images.put(key, image);
        }
    }

    public Image image(Images key) {
        return images.get(key);
    }
}
