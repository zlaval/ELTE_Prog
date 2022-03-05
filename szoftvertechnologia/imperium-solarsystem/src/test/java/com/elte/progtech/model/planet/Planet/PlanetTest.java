package com.elte.progtech.model.planet.Planet;


import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Images;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlanetTest {


    private Player player;
    private Planet sut;
    private Planet sut2;

    @BeforeEach
    void init() {
        sut = new Planet("name", new Coord(0, 0), 0, 1, Images.KRYPTON.getSize());
        player = spy(new Player("Mock Player", Color.BLUE));
    }

    @Test
    @DisplayName("no resource should be on the planet after instantiation")
    void noResourcesOnInstantiation() {
        //given
        HashMap<ResourceType, Integer> initialResources = sut.getResources();
        //when
        //then
        assertEquals(0, initialResources.get(ResourceType.ENERGY));
        assertEquals(0, initialResources.get(ResourceType.IRON));
    }

    @Test()
    @DisplayName("unconquered planet has no owner but conquered does subsequent conquers works as expected")
    void unconqueredPlanetHasNoOwnerButConqueredDoesSubsequentConquersWorksAsExpected() {
        //given
        assertNull(sut.getPlayer());

        //when
        sut.conquer(player);
        assertEquals(player, sut.getPlayer());

        Player player2 = spy(new Player("Mock Player2", Color.RED));
        sut.conquer(player2);

        //then
        assertEquals(player2, sut.getPlayer());
    }

    @Test
    @DisplayName("initial resources should be placed on conquer")
    void instantiationShouldPlaceResources() {
        //given
        sut.conquer(player);
        //when
        HashMap<ResourceType, Integer> initialResources = sut.getResources();
        //then
        assertEquals(10, initialResources.get(ResourceType.ENERGY));
        assertEquals(15, initialResources.get(ResourceType.IRON));
    }

    @Test
    @DisplayName("conquer doen't place resources on already occupied planet")
    void conquerNotPlaceResourcesOnAlreadyOccupiedPlanet() {

        //given
        sut.conquer(player);
        sut.addResource(new Resource(10, ResourceType.ENERGY));
        sut.addResource(new Resource(10, ResourceType.IRON));

        //when
        HashMap<ResourceType, Integer> initialResources = sut.getResources();
        Player player2;
        player2 = spy(new Player("Mock Player2", Color.RED));
        sut.conquer(player2);

        //then
        assertEquals(20, initialResources.get(ResourceType.ENERGY));
        assertEquals(25, initialResources.get(ResourceType.IRON));
    }

    @Test
    @DisplayName("produce deuterium should increase the initial quantity")
    void addDeuteriumIncreasePlayersDeuteriumQuantity() {
        var increaseAmount = 10;
        sut.conquer(player);

        assertEquals(50, player.getDeuterium().getQuantity());

        sut.addResource(new Resource(increaseAmount, ResourceType.DEUTERIUM));

        assertEquals(60, player.getDeuterium().getQuantity());
        assertFalse(sut.getResources().containsKey(ResourceType.DEUTERIUM));

        verify(player).produceDelerium(increaseAmount);
    }

    @Test
    @DisplayName("addResource(N) should increase the given resources quantity by N")
    void addResourceIncreaseTheQuantity() {
        sut.addResource(new Resource(5, ResourceType.IRON));
        sut.addResource(new Resource(10, ResourceType.ENERGY));

        HashMap<ResourceType, Integer> resourcesOnPlanet = sut.getResources();

        assertEquals(10, resourcesOnPlanet.get(ResourceType.ENERGY));
        assertEquals(5, resourcesOnPlanet.get(ResourceType.IRON));
    }

    @Test
    @DisplayName("addResources increase the given resource list")
    void addResourcesIncreaseTheQuantity() {
        List<Resource> resources2add = new ArrayList<>();
        sut.conquer(player);

        resources2add.add(new Resource(5, ResourceType.IRON));
        resources2add.add(new Resource(10, ResourceType.ENERGY));
        resources2add.add(new Resource(10, ResourceType.DEUTERIUM));
        sut.addResources(resources2add);
        HashMap<ResourceType, Integer> resourcesOnPlanet = sut.getResources();

        assertEquals(20, resourcesOnPlanet.get(ResourceType.IRON));
        assertEquals(20, resourcesOnPlanet.get(ResourceType.ENERGY));
        assertEquals(60, player.getDeuterium().getQuantity());
    }

    @Test()
    @DisplayName("addResourcesFailsOnUnoccupiedPlanet")
    void addResourcesFailsOnUnoccupiedPlanet() {
        List<Resource> resources2add = new ArrayList<>();

        resources2add.add(new Resource(5, ResourceType.IRON));
        resources2add.add(new Resource(10, ResourceType.ENERGY));
        resources2add.add(new Resource(10, ResourceType.DEUTERIUM));
        Throwable thrown = assertThrows(NullPointerException.class, () -> sut.addResources(resources2add));

        assertTrue(thrown.toString().contains("NullPointerException"));
    }

    @Test()
    @DisplayName("useResource fails on unoccupied planets")
    void useResourcesFailsOnUnoccupiedPlanet() {
//        assertFalse(sut.useResource(new Resource(1, ResourceType.IRON)));
//        assertFalse(sut.useResource(new Resource(1, ResourceType.ENERGY)));
        Throwable thrown = assertThrows(NullPointerException.class, () -> sut.useResource(new Resource(1, ResourceType.DEUTERIUM)));
        assertTrue(thrown.toString().contains("NullPointerException"));
    }

    @Test()
    @DisplayName("useResourcesFailsWhenNotEnoughesourcesArePresent")
    void useResourcesFailsWhenNotEnoughResourcesArePresent() {
        sut.conquer(player);
        assertFalse(sut.useResource(new Resource(sut.getResources().get(ResourceType.IRON) + 1, ResourceType.IRON)));
        assertFalse(sut.useResource(new Resource(sut.getResources().get(ResourceType.ENERGY) + 1, ResourceType.ENERGY)));
        assertFalse(sut.useResource(new Resource(player.getDeuterium().getQuantity() + 1, ResourceType.DEUTERIUM)));
    }

    @Test()
    @DisplayName("useResourcesWorksWhenEnoughResourcesArePresentBeforeInit")
    void useResourcesWorksWhenEnoughResourcesArePresentBeforeInit() {
        sut.addResource(new Resource(1, ResourceType.ENERGY));
        sut.addResource(new Resource(1, ResourceType.IRON));

        assertTrue(sut.useResource(new Resource(1, ResourceType.IRON)));
        assertTrue(sut.useResource(new Resource(1, ResourceType.ENERGY)));
    }

    @Test()
    @DisplayName("useResourcesWorksWhenEnoughResourcesArePresent")
    void useResourcesWorksWhenEnoughResourcesArePresent() {
        sut.conquer(player);

        assertTrue(sut.useResource(new Resource(1, ResourceType.DEUTERIUM)));
    }

    @Test()
    @DisplayName("emptyOrOccupiedPlanetHasNoStarGate")
    void emptyOrFreshlyOccupiedPlanetHasNoStarGate() {
        assertFalse(sut.hasStarGate());
        sut.conquer(player);
        assertFalse(sut.hasStarGate());
    }

    @Test()
    @DisplayName("emptyOrFreshlyOccupiedPlanetHasPlaceToBuild")
    void emptyOrFreshlyOccupiedPlanetHasPlaceToBuild() {
        assertTrue(sut.hasPlaceToBuild());
        sut.conquer(player);
        assertTrue(sut.hasPlaceToBuild());
    }

    //TODO itt mi a "" van?
    //TODO mar tudja mi van
    //Cannot invoke "hu.elte.progtech.model.resources.Resource.getResourceType()" because "resource" is null
    @Test()
    @DisplayName("planetCanBeFullWithBuildings")
    void planetCanBeFullWithBuildings() {

        //given
        sut.conquer(player);
        //when(sut.useResource(any())).thenReturn(true);
        sut.addResource(new Resource(1000, ResourceType.IRON));
        sut.addResource(new Resource(1000, ResourceType.ENERGY));
        sut.addResource(new Resource(1000, ResourceType.DEUTERIUM));
        assertTrue(sut.hasPlaceToBuild());

        //when
        sut.buildIronMine();

        //then
        assertFalse(sut.hasPlaceToBuild());
        //verify(sut, times(10)).useResource(any());
    }

    @Test()
    @DisplayName("buildingsCanBeBuild")
    void buildingsCanBeBuild() {

        //given
        sut2 = new Planet("name", new Coord(0, 0), 0, 3, Images.KRYPTON.getSize());
        sut2.conquer(player);
        //when(sut.useResource(any())).thenReturn(true);
        sut2.addResource(new Resource(1000, ResourceType.IRON));
        sut2.addResource(new Resource(1000, ResourceType.ENERGY));
        sut2.addResource(new Resource(1000, ResourceType.DEUTERIUM));
        assertTrue(sut2.hasPlaceToBuild());

        //when
        sut2.buildIronMine();
        sut2.buildDeuteriumMine();
        sut2.buildSolarPanel();

        //then
        assertFalse(sut2.hasPlaceToBuild());
        //verify(sut, times(10)).useResource(any());
    }

    @Test()
    @DisplayName("buildingsCanBeBuild")
    void shipsCanBeBuild() {

        //given
        sut.conquer(player);
        //when(sut.useResource(any())).thenReturn(true);
        //when(sut.hasStarGate()).thenReturn(true);

        sut.addResource(new Resource(1000, ResourceType.IRON));
        sut.addResource(new Resource(1000, ResourceType.ENERGY));
        sut.addResource(new Resource(1000, ResourceType.DEUTERIUM));
        sut.buildStarGate();
        assertEquals(sut.getShipUnderConstruct().size(), 0);

        //when
        sut.buildShip(ShipType.MOTHER_SHIP);

        //then
        assertEquals((sut.getShipUnderConstruct()).size(), 1);
        //verify(sut, times(10)).useResource(any());
        //verify(sut, times(1)).hasStarGate();
    }


}
