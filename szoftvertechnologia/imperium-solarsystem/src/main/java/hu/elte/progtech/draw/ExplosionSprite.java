package hu.elte.progtech.draw;

import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ExplosionSprite extends Movable {

    private int tile = 0;
    private int renderCount = 0;
    private final Image allExplosion;

    public ExplosionSprite(Coord coord) {
        super(ImageContainer.getInstance().image(Images.EXPLOSION), coord, Images.EXPLOSION.getSize());
        allExplosion = ImageContainer.getInstance().image(Images.EXPLOSION);
        image = getSubImage();
    }

    @Override
    protected void doBeforeRender() {
        renderCount++;
        if (renderCount % 3 == 0) {
            tile++;
        }
        if (tile == 19) {
            removable = true;
        }
        image = getSubImage();
    }

    private Image getSubImage() {
        int sizeInPixel = size.getHeight();
        var i = (sizeInPixel * tile) % (5 * sizeInPixel);
        var j = (tile / 5) * sizeInPixel;
        return ((BufferedImage) allExplosion).getSubimage(i, j, size.getWidth(), size.getHeight());
    }

    @Override
    public boolean canRemoveOnArrive() {
        return true;
    }

    @Override
    public double getSpeed() {
        return 0;
    }

    @Override
    public void remove() {

    }
}
