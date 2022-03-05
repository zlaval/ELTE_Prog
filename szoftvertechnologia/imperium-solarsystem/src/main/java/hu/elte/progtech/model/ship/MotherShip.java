package hu.elte.progtech.model.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.player.Player;

public class MotherShip extends SpaceShip {

    public MotherShip(Player player) {
        super(ShipType.MOTHER_SHIP, player);
    }

}
