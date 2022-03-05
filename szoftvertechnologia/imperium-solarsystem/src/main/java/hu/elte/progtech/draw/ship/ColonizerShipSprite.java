package hu.elte.progtech.draw.ship;

import hu.elte.progtech.model.ship.ColonizerShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class ColonizerShipSprite extends SpaceShipSprite {
    public ColonizerShipSprite(ColonizerShip spaceShip, Coord coord) {
        super(spaceShip, ImageContainer.getInstance().image(Images.COLONY_SHIP), coord, Images.COLONY_SHIP.getSize());
    }
}
