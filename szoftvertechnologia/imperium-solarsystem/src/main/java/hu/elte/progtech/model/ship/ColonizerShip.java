package hu.elte.progtech.model.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.player.Player;

public class ColonizerShip extends SpaceShip {
    public ColonizerShip(Player player) {
        super(ShipType.COLONIZER, player);
    }
}
