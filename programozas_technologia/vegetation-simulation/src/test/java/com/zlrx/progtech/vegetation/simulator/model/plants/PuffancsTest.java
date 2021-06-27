package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PuffancsTest {

    @Test
    @DisplayName("Plant with negative nutrient should be still-born with 0 nutrient")
    void stillBornTest() {
        var puffancs = new Puffancs("TestPuffancs", -1);

        assertFalse(puffancs.isAlive());
        assertEquals(0, puffancs.getNutrient());
    }

    @Test
    @DisplayName("Alpha radiation should increase nutrients by 2")
    void alphaRadiationShouldIncreaseNutrientsByTwo() {
        var puffancs = spy(new Puffancs("TestPuffancs", 8));

        var result = puffancs.dayPass(Radiation.ALPHA);

        assertEquals(10, puffancs.getNutrient());
        assertTrue(puffancs.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.ALPHA, 0);
        result.ifPresent(e -> assertEquals(expected, e));
        verify(puffancs, times(1)).calculateEffect();
    }

    @Test
    @DisplayName("Delta radiation should decrease nutrients by 2")
    void deltaRadiationShouldDecreaseNutrientsByTwo() {
        var puffancs = spy(new Puffancs("TestPuffancs", 3));

        var result = puffancs.dayPass(Radiation.DELTA);

        assertEquals(1, puffancs.getNutrient());
        assertTrue(puffancs.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.ALPHA, 9);
        result.ifPresent(e -> assertEquals(expected, e));
        verify(puffancs, times(1)).calculateEffect();
    }

    @Test
    @DisplayName("Neutral radiation should decrease nutrients by 1")
    void neutralRadiationShouldDecreaseNutrientsByOne() {
        var puffancs = spy(new Puffancs("TestPuffancs", 5));

        var result = puffancs.dayPass(Radiation.NEUTRAL);

        assertEquals(4, puffancs.getNutrient());
        assertTrue(puffancs.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.ALPHA, 6);
        result.ifPresent(e -> assertEquals(expected, e));
        verify(puffancs, times(1)).calculateEffect();
    }

    @Test
    @DisplayName("Puffancs should die when nutrients reach 0")
    void puffancsShouldDieWhenNutrientsReachZero() {
        var puffancs = spy(new Puffancs("TestPuffancs", 1));

        var result = puffancs.dayPass(Radiation.NEUTRAL);

        assertEquals(0, puffancs.getNutrient());
        assertFalse(puffancs.isAlive());
        assertFalse(result.isPresent());
        verify(puffancs, times(1)).handleRadiation(any());
        verify(puffancs, times(0)).calculateEffect();
    }

    @Test
    @DisplayName("Puffancs should die when nutrients reach 11")
    void puffancsShouldDieWhenNutrientsReach() {
        var puffancs = spy(new Puffancs("TestPuffancs", 9));

        var result = puffancs.dayPass(Radiation.ALPHA);

        assertEquals(11, puffancs.getNutrient());
        assertFalse(puffancs.isAlive());
        assertFalse(result.isPresent());
        verify(puffancs, times(1)).handleRadiation(any());
        verify(puffancs, times(0)).calculateEffect();
    }

    @Test
    @DisplayName("Dead Puffancs should return empty()")
    void deadPuffancsShouldReturnEmpty() {
        var puffancs = spy(new Puffancs("TestPuffancs", 0));

        var result = puffancs.dayPass(Radiation.ALPHA);

        assertEquals(0, puffancs.getNutrient());
        assertFalse(puffancs.isAlive());
        assertFalse(result.isPresent());
        verify(puffancs, times(0)).handleRadiation(any());
        verify(puffancs, times(0)).calculateEffect();
    }

}
