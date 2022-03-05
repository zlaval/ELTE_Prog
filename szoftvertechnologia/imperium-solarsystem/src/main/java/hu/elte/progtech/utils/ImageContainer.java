package hu.elte.progtech.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageContainer {
    private static final ImageContainer INSTANCE = new ImageContainer();

    public static ImageContainer getInstance() {
        return INSTANCE;
    }

    private Map<Images, Image> images = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ImageContainer() {
        for (Images key : Images.values()) {
            try {
                BufferedImage image = ImageIO.read(ClassLoader.getSystemResource(key.path()));
                images.put(key, image);
            } catch (IOException e) {
                logger.error("Cannot find picture " + key.path(), e);
                System.exit(1);
            }

        }
    }

    public Image image(Images key) {
        return images.get(key);
    }
}
