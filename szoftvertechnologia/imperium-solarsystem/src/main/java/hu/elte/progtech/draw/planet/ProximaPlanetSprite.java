package hu.elte.progtech.draw.planet;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class ProximaPlanetSprite extends PlanetSprite {
    public ProximaPlanetSprite(Coord coord) {

        super(new Planet("Proxima", coord, 1, 3, Images.PROXIMA.getSize()),
                ImageContainer.getInstance().image(Images.PROXIMA),
                coord,
                Images.PROXIMA.getSize()
        );
    }
}
