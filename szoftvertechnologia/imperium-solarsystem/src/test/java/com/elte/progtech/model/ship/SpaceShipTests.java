package com.elte.progtech.model.ship;

import hu.elte.progtech.model.building.mine.DeuteriumMine;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.model.ship.SpaceShip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import hu.elte.progtech.model.ship.*;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import hu.elte.progtech.utils.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SpaceShipTests {

    @Mock
    Player player;
    ShuttleShip sut;
    Planet planet;

    @BeforeEach
    void init() {
        player = spy(new Player("Mock Player", Color.BLUE));
        planet = new Planet("name", new Coord(0, 0), 0, 1, Images.KRYPTON.getSize());
        sut = new ShuttleShip(player);
    }

    @Test
    @DisplayName("hit Decreases Hit Points")
    void hitDecreasesHitPoints() {
        //given
        int initLife = sut.getLife();
        //when
        int damage = 10;
        sut.hit(damage);

        //then
        int defense = sut.getDefense();
        assertEquals(initLife-Math.max(damage - defense, 0),sut.getLife()); //43
    }

    @Test
    @DisplayName("consumeDeuteriumWhenEnoughIsPresent")
    void consumeDeuteriumWhenEnoughIsPresent() {
        //given
        int consumption = sut.getConsumption();
        player.produceDelerium(consumption);
        int initDeuterium = player.getDeuterium().getQuantity();

        //when
        sut.consumeDeuterium();

        //then
        int currentDeuterium = player.getDeuterium().getQuantity();
        assertEquals(currentDeuterium,initDeuterium-consumption);
    }

    @Test
    @DisplayName("consumeDeuteriumFailsWhenEnoughIsPresent")
    void consumeDeuteriumFailsWhenEnoughIsPresent() {
        //given
        int initDeuterium = player.getDeuterium().getQuantity();
        player.useDeuterium(initDeuterium);
        int newDeuterium = player.getDeuterium().getQuantity();
        assertEquals(newDeuterium,0);

        //when
        sut.consumeDeuterium();

        //then
        assertFalse(player.useDeuterium(sut.getConsumption()));
    }

    @Test
    @DisplayName("consumeActionPointsWhenEnoughIsPresent")
    void consumeActionPointsWhenEnoughIsPresent() {
        //given

        //when
        boolean result = sut.consumeActionPoints(player.getActionPoints());

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("consumeActionFailsWhenEnoughIsPresent")
    void consumeActionFailsWhenEnoughIsPresent() {
        //given

        //when
        boolean result = sut.consumeActionPoints(player.getActionPoints()+1);

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("destroyWorksAsExpected")
    void destroyWorksAsExpected() {

        //given
        boolean shipIsRegistered = player.getShips().contains(sut);
        assertTrue(shipIsRegistered);

        //when
        sut.destroy();

        //then
        boolean shipStillRegistered = player.getShips().contains(sut);
        assertFalse(shipStillRegistered);
    }

    @Test
    @DisplayName("shuttleLoadWorksAsExpected")
    void shuttleLoadWorksAsExpected() {

        //given
        List<Resource> initShipResources = sut.getResources();
        List<Resource> resources2add = new ArrayList<>();

        resources2add.add(new Resource(10, ResourceType.IRON));
        resources2add.add(new Resource(10, ResourceType.ENERGY));

        initShipResources.add(new Resource(10, ResourceType.IRON));
        initShipResources.add(new Resource(10, ResourceType.ENERGY));

        //when
        sut.load(resources2add,planet);

        //then

        assertTrue(initShipResources.equals(sut.getResources()));
    }

    @Test
    @DisplayName("shuttleUnLoadWorksAsExpected")
    void shuttleUnLoadWorksAsExpected() {

        //given
        Planet planet2 = new Planet("name", new Coord(0, 0), 0, 1, Images.KRYPTON.getSize());
        List<Resource> resources2add = new ArrayList<>();
        resources2add.add(new Resource(10, ResourceType.IRON));
        resources2add.add(new Resource(10, ResourceType.ENERGY));
        planet2.addResources(resources2add);

        HashMap<ResourceType, Integer> initResources = planet.getResources();
        int initIRON = initResources.get(ResourceType.IRON);
        int initENERGY = initResources.get(ResourceType.ENERGY);

        //when
        sut.load(resources2add,planet2);
        sut.unLoad(planet);

        //then
        HashMap<ResourceType, Integer> newResources = planet.getResources();
        int newIRON = newResources.get(ResourceType.IRON);
        int newENERGY = newResources.get(ResourceType.ENERGY);

        System.out.println(newIRON);
        System.out.println(initIRON);
        assertEquals(newIRON-initIRON,10);
        assertEquals(newENERGY-initENERGY,10);
        assertEquals(sut.getResources().size(),0);
        assertTrue(sut.getResources().equals(new ArrayList<>()));
    }

}
