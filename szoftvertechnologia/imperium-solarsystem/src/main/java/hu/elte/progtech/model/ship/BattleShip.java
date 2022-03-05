package hu.elte.progtech.model.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.player.Player;

public class BattleShip extends SpaceShip {
    public BattleShip(Player player) {
        super(ShipType.BATTLESHIP, player);
    }
}
