package hu.elte.progtech.draw.ship;

import hu.elte.progtech.model.ship.BattleShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

public class BattleshipSprite extends SpaceShipSprite {

    public BattleshipSprite(BattleShip spaceShip, Coord coord) {
        super(spaceShip, ImageContainer.getInstance().image(Images.BATTLESHIP), coord, Images.BATTLESHIP.getSize());
    }
}
