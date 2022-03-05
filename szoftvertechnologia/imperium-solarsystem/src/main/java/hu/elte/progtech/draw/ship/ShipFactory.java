package hu.elte.progtech.draw.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.ship.BattleShip;
import hu.elte.progtech.model.ship.ColonizerShip;
import hu.elte.progtech.model.ship.CruiserShip;
import hu.elte.progtech.model.ship.FighterShip;
import hu.elte.progtech.model.ship.MotherShip;
import hu.elte.progtech.model.ship.ShuttleShip;
import hu.elte.progtech.utils.Coord;

public class ShipFactory {

    public static SpaceShipSprite getShip(ShipType shipType, Coord coord, Player owner) {
        return switch (shipType) {
            case MOTHER_SHIP -> new MotherShipSprite(new MotherShip(owner), coord);
            case FIGHTER -> new FighterShipSprite(new FighterShip(owner), coord);
            case CRUISER -> new CruiserShipSprite(new CruiserShip(owner), coord);
            case BATTLESHIP -> new BattleshipSprite(new BattleShip(owner), coord);
            case COLONIZER -> new ColonizerShipSprite(new ColonizerShip(owner), coord);
            case SHUTTLE -> new ShuttleShipSprite(new ShuttleShip(owner), coord);
        };
    }

}
