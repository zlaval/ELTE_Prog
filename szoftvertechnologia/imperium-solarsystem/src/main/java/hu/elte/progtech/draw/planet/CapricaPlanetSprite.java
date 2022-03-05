package hu.elte.progtech.draw.planet;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class CapricaPlanetSprite extends PlanetSprite {

    public CapricaPlanetSprite(Coord coord) {
        super(new Planet("Caprica", coord, 2, 5, Images.CAPRICA.getSize()),
                ImageContainer.getInstance().image(Images.CAPRICA),
                coord,
                Images.CAPRICA.getSize()
        );
    }
}
