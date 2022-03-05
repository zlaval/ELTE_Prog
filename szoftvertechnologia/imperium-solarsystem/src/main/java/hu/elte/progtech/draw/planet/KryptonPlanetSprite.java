package hu.elte.progtech.draw.planet;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class KryptonPlanetSprite extends PlanetSprite {

    public KryptonPlanetSprite(Coord coord) {
        super(new Planet("Krypton", coord, 2, 5, Images.KRYPTON.getSize()),
                ImageContainer.getInstance().image(Images.KRYPTON),
                coord,
                Images.KRYPTON.getSize()
        );
    }

}
