package com.elte.progtech.model.building.mine;


import hu.elte.progtech.model.building.mine.DeuteriumMine;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeuteriumMineTest {

    @Mock
    Planet planet;

    DeuteriumMine sut;

    @BeforeEach
    void init() {
        sut = new DeuteriumMine(planet);
        when(planet.getPlayer()).thenReturn(new Player("Mock Player", Color.BLUE));
    }

    @Test
    @DisplayName("levelUp() should return true when has enough resources and level below 5")
    void levelUpTrueWhenEnoughResourcesAndBelowLevel5() {
        //given
        when(planet.useResource(any())).thenReturn(true);

        //when
        var result = sut.levelUp();

        //then
        assertTrue(result);
        verify(planet, times(1)).useResource(any());
    }

    @Test
    @DisplayName("levelUp() should return false when has not enough resources")
    void levelUpFalseWhenNotEnoughResources() {
        //given
        when(planet.useResource(any())).thenReturn(false);

        //when
        var result = sut.levelUp();

        //then
        assertFalse(result);
        verify(planet, times(1)).useResource(any());
    }

    @Test
    @DisplayName("levelUp() should return false when level cap reached")
    void levelUpFalseWhenLevelCapReached() {
        //given
        when(planet.useResource(any())).thenReturn(true);
        for (int i = 0; i < 5; i++) {
            sut.levelUp();
            while (!sut.construct()) ;
        }

        //when
        var result = sut.levelUp();

        //then
        assertFalse(result);
    }

}
