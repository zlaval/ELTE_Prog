package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeltafaTest {


    @Test
    @DisplayName("Alpha radiation should decrease nutrients by 3")
    void alphaRadiationShouldDecreaseNutrientsByThree() {
        var deltafa = new Deltafa("TestDeltafa", 7);

        var result = deltafa.dayPass(Radiation.ALPHA);

        assertEquals(4, deltafa.getNutrient());
        assertTrue(deltafa.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.DELTA, 4);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Delta radiation should increase nutrients by 4")
    void deltaRadiationShouldIncreaseNutrientsByFour() {
        var deltafa = new Deltafa("TestDeltafa", 1);

        var result = deltafa.dayPass(Radiation.DELTA);

        assertEquals(5, deltafa.getNutrient());
        assertTrue(deltafa.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.DELTA, 1);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Neutral radiation should decrease nutrients by 1")
    void neutralRadiationShouldDecreaseNutrientsByOne() {
        var deltafa = new Deltafa("TestDeltafa", 12);

        var result = deltafa.dayPass(Radiation.NEUTRAL);

        assertEquals(11, deltafa.getNutrient());
        assertTrue(deltafa.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.NEUTRAL, 0);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Deltafa should die when nutrients reach 0")
    void puffancsShouldDieWhenNutrientReachesZero() {
        var deltafa = new Deltafa("TestDeltafa", 2);

        var result = deltafa.dayPass(Radiation.ALPHA);

        assertEquals(0, deltafa.getNutrient());
        assertFalse(deltafa.isAlive());
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return 4 delta when nutrients below five")
    void shouldReturnFourDeltaWhenNutrientsBelowFive() {
        var deltafa = new Deltafa("TestDeltafa", 7);

        var result = deltafa.dayPass(Radiation.ALPHA);

        assertEquals(4, deltafa.getNutrient());
        var expected = new Effect(Radiation.DELTA, 4);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Should return 1 delta when nutrients between five and ten")
    void shouldReturnOneDeltaWhenNutrientsBetweenFiveToTen() {
        var deltafa = new Deltafa("TestDeltafa", 13);

        var result = deltafa.dayPass(Radiation.ALPHA);

        assertEquals(10, deltafa.getNutrient());
        var expected = new Effect(Radiation.DELTA, 1);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Should return neutral when nutrients above ten")
    void shouldReturnNeutralWhenNutrientsAboveTen() {
        var deltafa = new Deltafa("TestDeltafa", 14);

        var result = deltafa.dayPass(Radiation.ALPHA);

        assertEquals(11, deltafa.getNutrient());
        var expected = new Effect(Radiation.NEUTRAL, 0);
        result.ifPresent(e -> assertEquals(expected, e));
    }

}
