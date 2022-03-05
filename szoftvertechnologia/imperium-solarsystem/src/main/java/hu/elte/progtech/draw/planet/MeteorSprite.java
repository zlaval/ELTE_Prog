package hu.elte.progtech.draw.planet;

import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class MeteorSprite extends PlanetSprite {
    public MeteorSprite(Coord coord) {
        super(null, ImageContainer.getInstance().image(Images.METEOR), coord, Images.METEOR.getSize());
    }

    @Override
    protected void select() {

    }
}
