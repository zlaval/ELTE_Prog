package hu.elte.progtech.draw.planet;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class AbierPlanetSprite extends PlanetSprite {

    public AbierPlanetSprite(Coord coord) {
        super(
                new Planet("Abier", coord, 1, 3, Images.ABEIR.getSize()),
                ImageContainer.getInstance().image(Images.ABEIR),
                coord,
                Images.ABEIR.getSize()
        );
    }
}
