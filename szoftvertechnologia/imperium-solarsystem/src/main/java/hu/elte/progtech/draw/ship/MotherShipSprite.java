package hu.elte.progtech.draw.ship;

import hu.elte.progtech.model.ship.MotherShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class MotherShipSprite extends SpaceShipSprite {
    public MotherShipSprite(MotherShip spaceShip, Coord coord) {
        super(spaceShip, ImageContainer.getInstance().image(Images.MOTHERSHIP), coord, Images.MOTHERSHIP.getSize());
    }
}
