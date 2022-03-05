package hu.elte.progtech.draw.planet;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class KasjyyykPlanetSprite extends PlanetSprite {

    public KasjyyykPlanetSprite(Coord coord) {
        super(new Planet("Kasjyyk", coord, 3, 8, Images.KASJYYYK.getSize()),
                ImageContainer.getInstance().image(Images.KASJYYYK),
                coord,
                Images.KASJYYYK.getSize()
        );
    }

}
