package hu.elte.progtech.draw.planet;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class Lv426PlanetSprite extends PlanetSprite {
    public Lv426PlanetSprite(Coord coord) {
        super(
                new Planet("Lv426", coord, 3, 8, Images.LV426.getSize()),
                ImageContainer.getInstance().image(Images.LV426),
                coord,
                Images.LV426.getSize()
        );
    }
}
