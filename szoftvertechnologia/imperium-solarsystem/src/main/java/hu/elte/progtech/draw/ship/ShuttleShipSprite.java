package hu.elte.progtech.draw.ship;

import hu.elte.progtech.model.ship.ShuttleShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;
import lombok.Getter;


public class ShuttleShipSprite extends SpaceShipSprite {
    public ShuttleShipSprite(ShuttleShip spaceShip, Coord coord) {
        super(spaceShip, ImageContainer.getInstance().image(Images.SHUTTLE), coord, Images.SHUTTLE.getSize());
    }
}
