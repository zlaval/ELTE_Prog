package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParabokorTest {

    @Test
    @DisplayName("Alpha radiation should increase nutrients by 1")
    void alphaRadiationShouldIncreaseNutrientsByOne() {
        var parabokor = new Parabokor("TestParabokor", 5);

        var result = parabokor.dayPass(Radiation.ALPHA);

        assertEquals(6, parabokor.getNutrient());
        assertTrue(parabokor.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.NEUTRAL, 0);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Delta radiation should increase nutrients by 1")
    void deltaRadiationShouldDecreaseNutrientsByOne() {
        var parabokor = new Parabokor("TestParabokor", 5);

        var result = parabokor.dayPass(Radiation.DELTA);

        assertEquals(6, parabokor.getNutrient());
        assertTrue(parabokor.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.NEUTRAL, 0);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Neutral radiation should decrease nutrients by 1")
    void neutralRadiationShouldDecreaseNutrientsByOne() {
        var parabokor = new Parabokor("TestParabokor", 5);

        var result = parabokor.dayPass(Radiation.NEUTRAL);

        assertEquals(4, parabokor.getNutrient());
        assertTrue(parabokor.isAlive());
        assertTrue(result.isPresent());
        var expected = new Effect(Radiation.NEUTRAL, 0);
        result.ifPresent(e -> assertEquals(expected, e));
    }

    @Test
    @DisplayName("Parabokor should die when nutrients reach 0")
    void parabokorShouldDieWhenNutrientsReachZero() {
        var parabokor = new Parabokor("TestParabokor", 1);

        var result = parabokor.dayPass(Radiation.NEUTRAL);

        assertEquals(0, parabokor.getNutrient());
        assertFalse(parabokor.isAlive());
        assertFalse(result.isPresent());
    }

}
