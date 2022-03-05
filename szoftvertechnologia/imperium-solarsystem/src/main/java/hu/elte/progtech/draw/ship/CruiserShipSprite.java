package hu.elte.progtech.draw.ship;

import hu.elte.progtech.model.ship.CruiserShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class CruiserShipSprite extends SpaceShipSprite {
    public CruiserShipSprite(CruiserShip spaceShip, Coord coord) {
        super(spaceShip, ImageContainer.getInstance().image(Images.CRUISER), coord, Images.CRUISER.getSize());
    }
}
