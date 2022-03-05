package com.elte.progtech.utils;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.draw.ship.ColonizerShipSprite;
import hu.elte.progtech.draw.ship.FighterShipSprite;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.ship.ColonizerShip;
import hu.elte.progtech.model.ship.FighterShip;
import hu.elte.progtech.model.ship.SpaceShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import hu.elte.progtech.utils.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UtilityTest {

    Planet planet;

    Player playerOne;
    Player playerTwo;

    List<SpaceShip> spaceShipList = new ArrayList<>();

    @BeforeEach
    void init() {
        planet = new Planet("Blue", new Coord(100, 100), 10, 10, Size.get(10, 10));
        playerOne = new Player("BluePlayer", Color.BLUE);
        playerTwo = new Player("RedPlayer", Color.BLUE);
        spaceShipList.add(new ColonizerShip(playerOne));
        spaceShipList.add(new FighterShip(playerTwo));
        ColonizerShipSprite colonizerShipSprite = new ColonizerShipSprite((ColonizerShip) spaceShipList.get(0), new Coord(100, 100));
        FighterShipSprite fighterShipSprite = new FighterShipSprite((FighterShip) spaceShipList.get(1), new Coord(100, 100));
        planet.conquer(playerOne);
    }

    @Test
    @DisplayName("isClicked() should return true if clicked")
    void isClickedReturnTrue() {
        assertTrue(Utility.isClicked(new Coord(100, 200), new Coord(80, 200), Size.get(30, 30)));
    }

    @Test
    @DisplayName("isClicked() should return false if not clicked")
    void isClickedReturnFalse() {
        assertTrue(Utility.isClicked(new Coord(100, 200), new Coord(100, 200), Size.get(30, 30)));
    }

    @Test
    @DisplayName("getShipNearby() should return ships nearby")
    void getShipsNearby() {
        var array = Utility.getShipNearby(planet, ShipType.COLONIZER, 10);
        assertTrue(array.get(0).getName().equals("Colonizer"), "Colonizer");
    }

    @Test
    @DisplayName("isShipNearby() should return true if ship is nearby")
    void isShipNearbyReturnTrue() {
        assertTrue(Utility.isShipNearby(new Coord(100, 100), playerOne, ShipType.COLONIZER));
    }

    @Test
    @DisplayName("isShipNearby() should return false if ship is not nearby")
    void isShipNearbyReturnFalse() {
        assertFalse(Utility.isShipNearby(new Coord(1000, 1000), playerOne, ShipType.COLONIZER));
    }

    @Test
    @DisplayName("isOwnShipNearby() should return true if own ship is nearby")
    void isOwnShipNearbyReturnTrue() {
        assertTrue(Utility.isOwnShipNearby(new Coord(100, 100), spaceShipList));
    }

    @Test
    @DisplayName("isOwnShipNearby() should return false if own ship is not nearby")
    void isOwnShipNearbyReturnFalse() {
        assertFalse(Utility.isOwnShipNearby(new Coord(1000, 1000), spaceShipList));
    }

    @Test
    @DisplayName("isSelected() should return true if ship is selected")
    void isSelectedReturnTrue() {
        assertTrue(Utility.isSelected(
                new Coord(100, 100),
                new Coord(100, 100),
                new Coord(100, 100),
                Size.get(10, 10)
        ));
    }

    @Test
    @DisplayName("isSelected() should return false if ship is not selected")
    void isSelectedReturnFalse() {
        assertFalse(Utility.isSelected(
                new Coord(1000, 1000),
                new Coord(1000, 1000),
                new Coord(100, 100),
                Size.get(10, 10)
        ));
    }
}
