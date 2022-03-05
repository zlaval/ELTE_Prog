package hu.elte.progtech.model.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.player.Player;

public class FighterShip extends SpaceShip {
    public FighterShip(Player player) {
        super(ShipType.FIGHTER, player);
    }
}
