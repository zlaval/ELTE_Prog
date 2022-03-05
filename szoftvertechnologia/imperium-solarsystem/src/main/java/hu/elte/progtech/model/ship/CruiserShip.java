package hu.elte.progtech.model.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.player.Player;

public class CruiserShip extends SpaceShip {
    public CruiserShip(Player player) {
        super(ShipType.CRUISER, player);

    }
}
