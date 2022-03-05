package hu.elte.progtech.behaviour;

import hu.elte.progtech.draw.Drawable;
import hu.elte.progtech.draw.MissileSprite;
import hu.elte.progtech.draw.Movable;
import hu.elte.progtech.draw.Selectable;
import hu.elte.progtech.draw.planet.KryptonPlanetSprite;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.draw.ship.FighterShipSprite;
import hu.elte.progtech.draw.ship.SpaceShipSprite;
import hu.elte.progtech.model.building.mine.DeuteriumMine;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.planet.Planets;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.ship.FighterShip;
import hu.elte.progtech.model.ship.ShuttleShip;
import hu.elte.progtech.model.ship.SpaceShip;
import hu.elte.progtech.screen.SolarSystem;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Images;
import hu.elte.progtech.utils.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static hu.elte.progtech.consts.Const.SHOT_COST;
import static hu.elte.progtech.screen.view.PlayGround.PLANETS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class DrawManagerTest {

    @Mock
    DrawManager sut;
    Player player;
    Player enemyPlayer;
    FighterShip fighterShip;
    FighterShipSprite fighterShipSprite;
    Coord startingCoord;

    @BeforeEach
    void init() {
        sut = new DrawManager();
        player = spy(new Player("Mock Player", Color.BLUE));
        startingCoord = new Coord(10, 10);
        fighterShip = spy(new FighterShip(player));
        fighterShipSprite = spy(new FighterShipSprite(fighterShip, startingCoord));
        enemyPlayer = spy(new Player("Mock Enemy Player", Color.RED));
    }

    @Test
    @DisplayName("redrawTestShiponMoveIsCalled")
    void redrawTestShiponMoveIsCalled() {

        //given
        sut.registerMovable(fighterShipSprite);
        doNothing().when(fighterShipSprite).onMove();

        FighterShip fighterShip2 = spy(new FighterShip(player));
        FighterShipSprite fighterShipSprite2 = spy(new FighterShipSprite(fighterShip2, startingCoord));
        sut.registerMovable(fighterShipSprite2);
        doNothing().when(fighterShipSprite2).onMove();

        //when
        sut.redraw();

        //then
        verify(fighterShipSprite, times(1)).onMove();
        verify(fighterShipSprite2, times(1)).onMove();
    }

//    @Test
//    @DisplayName("handleClickEventTestOnEnemyShip")
//    void handleClickEventTestOnEnemyShip() {
//
//        //given
//        FighterShip enemyFighter = new FighterShip(enemyPlayer);
//        FighterShipSprite enemyFighterSprite = spy(new FighterShipSprite(enemyFighter,startingCoord));
//        doNothing().when(sut).fire(player,startingCoord);
//
//        //when
//        sut.handleClickEvent(startingCoord,player);
//
//        //then
//        verify(sut, times(1)).fire(player,startingCoord);
//    }

    @Test
    @DisplayName("getEnemyShipsTest")
    void getEnemyShipsTest() {

        //given
        FighterShip enemyFighter = new FighterShip(enemyPlayer);
        FighterShipSprite enemyFighterSprite = spy(new FighterShipSprite(enemyFighter, startingCoord));
        sut.registerMovable(enemyFighterSprite);
        doNothing().when(enemyFighterSprite).onMove();

        FighterShip enemyFighter2 = new FighterShip(enemyPlayer);
        FighterShipSprite enemyFighterSprite2 = spy(new FighterShipSprite(enemyFighter, startingCoord));
        sut.registerMovable(enemyFighterSprite2);
        doNothing().when(enemyFighterSprite2).onMove();

        PlanetSprite enemyPlanetSprite = new KryptonPlanetSprite(new Coord(0, 0));
        sut.registerDrawable(new ArrayList<>(Collections.singletonList(enemyPlanetSprite)));
        enemyPlanetSprite.getPlanet().conquer(enemyPlayer);

        //when
        sut.redraw();
        var result = sut.getEnemyShips(player);

        //then
        assertTrue(sut.getDrawableElements().contains(enemyPlanetSprite));
        assertFalse(result.contains(enemyPlanetSprite));

        assertTrue(result.equals(new ArrayList<>(Arrays.asList(enemyFighterSprite, enemyFighterSprite2))));

        verify(enemyFighterSprite, times(1)).onMove();
        verify(enemyFighterSprite2, times(1)).onMove();
    }

    @Test
    @DisplayName("getMiddleTest")
    void getMiddleTest() {

        //given
        int middle_x = startingCoord.getX() + Images.FIGHTER.getSize().getHeight() / 2;
        int middle_y = startingCoord.getY() + Images.FIGHTER.getSize().getWidth() / 2;
        Coord middle = Coord.get(middle_x, middle_y);

        //when
        var res = sut.getMiddle(fighterShipSprite);

        //then
        assertEquals(res, middle);
        assertTrue(res.equals(middle));
    }

    @Test
    @DisplayName("getSelectedPlanetTestPlanetExists")
    void getSelectedPlanetTestPlanetExists() {

        //given
        PlanetSprite planetSprite = new KryptonPlanetSprite(new Coord(0, 0));
        Planet planet = planetSprite.getPlanet();
        planetSprite.setSelected(true);

        sut.registerDrawable(new ArrayList<>(Collections.singletonList(planetSprite)));

        //when
        sut.redraw();
        var res = sut.getSelectedPlanet().get();

        //then
        assertTrue(res.equals(planet));
    }

    @Test
    @DisplayName("getSelectedPlanetSpriteTestPlanetSpriteExists")
    void getSelectedPlanetSpriteTestPlanetSpriteExists() {

        //given
        PlanetSprite planetSprite = new KryptonPlanetSprite(new Coord(0, 0));
        Planet planet = planetSprite.getPlanet();
        planetSprite.setSelected(true);
        sut.registerDrawable(new ArrayList<>(Collections.singletonList(planetSprite)));

        sut.registerDrawable(new ArrayList<>(Collections.singletonList(planetSprite)));

        //when
        sut.redraw();
        var res = sut.getSelectedPlanetSprite().get();

        //then
        assertTrue(res.equals(planetSprite));
    }

    @Test
    @DisplayName("deselectAllTest")
    void deselectAllTest() {

        //given
        PlanetSprite planetSprite = new KryptonPlanetSprite(new Coord(0, 0));
        Planet planet = planetSprite.getPlanet();
        planetSprite.setSelected(true);
        sut.registerDrawable(new ArrayList<>(Collections.singletonList(planetSprite)));

        sut.registerMovable(fighterShipSprite);
        doNothing().when(fighterShipSprite).onMove();
        fighterShipSprite.setSelected(true);

        sut.redraw();
        assertTrue(planetSprite.isSelected());
        assertTrue(fighterShipSprite.isSelected());

        //when
        sut.deselectAll();

        //then
        assertFalse(planetSprite.isSelected());
        assertFalse(fighterShipSprite.isSelected());
    }

    @Test
    @DisplayName("moveSelectedTest")
    void moveSelectedTest() {

        //given
        sut.registerMovable(fighterShipSprite);
        doNothing().when(fighterShipSprite).onMove();
        doNothing().when(fighterShipSprite).setDestination(any());
        fighterShipSprite.setSelected(true);

        FighterShip fighterShip2 = spy(new FighterShip(player));
        FighterShipSprite fighterShipSprite2 = spy(new FighterShipSprite(fighterShip, startingCoord));
        sut.registerMovable(fighterShipSprite2);
        doNothing().when(fighterShipSprite2).setDestination(any());
        fighterShipSprite2.setSelected(true);

        doNothing().when(fighterShipSprite2).onMove();

        //when
        sut.redraw();
        sut.moveSelected(new Coord(100,100));

        //then
        verify(fighterShipSprite, times(1)).setDestination(any());
        verify(fighterShipSprite2, times(1)).setDestination(any());

        verify(fighterShipSprite, times(1)).onMove();
        verify(fighterShipSprite2, times(1)).onMove();
    }

    @Test
    @DisplayName("selectElementTest")
    void selectElementTest() {
        FighterShip fighterShip2 = spy(new FighterShip(player));
        FighterShipSprite fighterShipSprite2 = spy(new FighterShipSprite(fighterShip, startingCoord));

        //given
        sut.registerMovable(fighterShipSprite);
        doNothing().when(fighterShipSprite).onMove();
        doNothing().when(fighterShipSprite).onSelect(any(),any(),any());
        fighterShipSprite.setSelected(true);

        sut.registerMovable(fighterShipSprite2);
        doNothing().when(fighterShipSprite2).onMove();
        doNothing().when(fighterShipSprite2).onSelect(any(),any(),any());
        fighterShipSprite2.setSelected(true);


        //when
        sut.redraw();
        sut.selectElement(new Coord(0,0),new Coord(100,100),player);

        //then
        verify(fighterShipSprite, times(1)).onSelect(any(),any(),any());
        verify(fighterShipSprite2, times(1)).onSelect(any(),any(),any());

        verify(fighterShipSprite, times(1)).onMove();
        verify(fighterShipSprite2, times(1)).onMove();
    }

//    public void fire(Player player, Coord to) {
//        var missiles = drawableElements.stream()
//                .filter(e -> e instanceof SpaceShipSprite)
//                .filter(Drawable::isSelected)
//                .map(e -> (SpaceShipSprite) e)
//                .map(a -> tryFire(player, to, a))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
//        registerDrawable(missiles);
//    }
//    @Test
//    @DisplayName("fireTest")
//    void fireTest() {
//
//        //given
//        Coord target = new Coord(100,100);
//
//        FighterShip enemyFighter = new FighterShip(enemyPlayer);
//        FighterShipSprite enemyFighterSprite = spy(new FighterShipSprite(enemyFighter, target));
//        sut.registerMovable(enemyFighterSprite);
//        doNothing().when(enemyFighterSprite).onMove();
//
//        FighterShip fighter = new FighterShip(player);
//        FighterShipSprite fighterSprite = spy(new FighterShipSprite(fighter, target));
//        sut.registerMovable(fighterSprite);
//        doNothing().when(fighterSprite).onMove();
//        fighterSprite.setSelected(true);
//
//        assertFalse(sut.getDrawableElements().contains(fighterSprite));
//        assertFalse(sut.getDrawableElements().contains(enemyFighterSprite));
//        assertEquals(sut.getDrawableElements().size(),0);
//
//        //when
//        sut.redraw();
//        sut.fire(player,target);
//
//        assertThrows(NullPointerException,sut.redraw());
//
//        //then
//        System.out.println(sut.getDrawableElements());
//        assertTrue(sut.getDrawableElements().contains(enemyFighterSprite));
//        assertTrue(sut.getDrawableElements().contains(fighterSprite));
//
//        verify(fighterSprite, times(1)).onMove();
//        verify(enemyFighterSprite, times(1)).onMove();
//    }
}