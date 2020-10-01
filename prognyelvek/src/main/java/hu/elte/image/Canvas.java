package hu.elte.image;

import hu.elte.geometry.Circle;
import hu.elte.geometry.Rectangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// az itt talalhato API-kat nem kell ismerni, nem tananyag!
public class Canvas implements Closeable {

    private final BufferedImage bufferedImage;
    private final Graphics2D graphics2d;

    public Canvas(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics2d = bufferedImage.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void draw(Circle circle, Color color) {
        graphics2d.setPaint(color);
        int x = (int) (circle.getCenter().getX() - circle.getRadius());
        int y = (int) (circle.getCenter().getY() - circle.getRadius());
        int diameter = (int) circle.getRadius() * 2;
        graphics2d.fillOval(x, y, diameter, diameter);
    }

    public void draw(Rectangle rectangle, Color color) {
        graphics2d.setPaint(color);
        int x = (int) rectangle.getX();
        int y = (int) rectangle.getY();
        int width = (int) rectangle.getWidth();
        int height = (int) rectangle.getHeight();
        graphics2d.fillRect(x, y, width, height);
    }

    public void saveToPng(String path) throws IOException {
        ImageIO.write(bufferedImage, "png", new File(path));
    }

    @Override
    public void close() {
        graphics2d.dispose();
    }

}