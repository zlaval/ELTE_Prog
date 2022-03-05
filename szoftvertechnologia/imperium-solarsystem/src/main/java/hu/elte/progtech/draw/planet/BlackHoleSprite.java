package hu.elte.progtech.draw.planet;

import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class BlackHoleSprite extends PlanetSprite {
    public BlackHoleSprite(Coord coord) {
        super(null, ImageContainer.getInstance().image(Images.BLACK_HOLE), coord, Images.BLACK_HOLE.getSize());
    }

    @Override
    protected void select() {

    }
}
