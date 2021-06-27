package com.zlrx.elte.slideout.resource;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImageContainer {

    private static ImageContainer INSTANCE;

    public static ImageContainer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageContainer();
        }
        return INSTANCE;
    }

    private Map<ImageResource, Image> images;

    private ImageContainer() {
        loadResources();
    }

    private void loadResources() {
        images = Arrays.stream(ImageResource.values()).collect(
                Collectors.toMap(Function.identity(), r -> loadImage(r.resource()))
        );
    }

    private Image loadImage(String name) {
        try {
            return ImageIO.read(ClassLoader.getSystemResource(name));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Image not found");
        }
    }

    public Image getImage(ImageResource key) {
        return images.get(key);
    }

}
