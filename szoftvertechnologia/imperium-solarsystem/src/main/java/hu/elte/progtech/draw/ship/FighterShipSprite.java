package hu.elte.progtech.draw.ship;

import hu.elte.progtech.model.ship.FighterShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class FighterShipSprite extends SpaceShipSprite {
    public FighterShipSprite(FighterShip spaceShip, Coord coord) {
        super(spaceShip, ImageContainer.getInstance().image(Images.FIGHTER), coord, Images.FIGHTER.getSize());
    }
}
