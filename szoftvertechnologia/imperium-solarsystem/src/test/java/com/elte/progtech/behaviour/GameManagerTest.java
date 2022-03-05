package com.elte.progtech.behaviour;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.behaviour.GameManager;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameManagerTest {

    GameManager gameManager;
    Player firstPlayer;
    Player secondPlayer;

    private final String RED_PLAYER_NAME = "RedPlayer";
    private final String BLUE_PLAYER_NAME = "RedPlayer";

    @BeforeEach
    void init() {
        firstPlayer = new Player(RED_PLAYER_NAME, Color.RED);
        secondPlayer = new Player(BLUE_PLAYER_NAME, Color.BLUE);
        gameManager = new GameManager(new DrawManager(), firstPlayer, secondPlayer);
    }

    @Test
    @DisplayName("setupPlayers() check if everything is correct")
    void setupPlayersTest() {
        gameManager.setupPlayers(
                new Planet("FirstPlanet", new Coord(100, 100), 100, 100, Size.get(10, 10)),
                new Planet("SecondPlanet", new Coord(100, 100), 100, 100, Size.get(10, 10))
        );

        assertEquals(firstPlayer.getPlanets().get(0).getName(), "FirstPlanet");
        assertEquals(secondPlayer.getPlanets().get(0).getName(), "SecondPlanet");
    }

    @Test
    @DisplayName("switchPlayer() check if actualPlayer is other after switch")
    void switchPlayerTest() {
        assertEquals(gameManager.getActualPlayer().getName(), RED_PLAYER_NAME);
        gameManager.switchPlayer();
        assertEquals(gameManager.getActualPlayer().getName(), BLUE_PLAYER_NAME);
    }

}
