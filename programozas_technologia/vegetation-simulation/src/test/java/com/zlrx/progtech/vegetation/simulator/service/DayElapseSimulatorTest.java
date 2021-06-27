package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import com.zlrx.progtech.vegetation.simulator.model.plants.Deltafa;
import com.zlrx.progtech.vegetation.simulator.model.plants.Parabokor;
import com.zlrx.progtech.vegetation.simulator.model.plants.Puffancs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayElapseSimulatorTest {

    private DayElapseSimulator underTest = new DayElapseSimulator();

    @Test
    @DisplayName("should return neutral when all plants are dead")
    public void shouldReturnNeutralWhenAllPlantsAreDead() {
        var plants = List.of(
                new Puffancs("Pu", 0),
                new Deltafa("D", 0),
                new Parabokor("Pa", 0)
        );

        var result = underTest.simulateDay(plants, Radiation.ALPHA);

        assertEquals(Radiation.NEUTRAL, result);
    }

    @Test
    @DisplayName("should return alpha when alpha is greater than delta at least by three")
    public void alphaResultTest() {
        var plants = List.of(
                new Puffancs("Pu", 1),
                new Deltafa("D1", 8),
                new Deltafa("D2", 8),
                new Deltafa("D3", 13),
                new Deltafa("D4", 11)
        );

        var result = underTest.simulateDay(plants, Radiation.ALPHA);

        assertEquals(Radiation.ALPHA, result);
    }

    @Test
    @DisplayName("should return delta when delta is greater than alpha at least by three")
    public void deltaResultTest() {
        var plants = List.of(
                new Puffancs("Pu", 5),
                new Deltafa("D1", 6),
                new Deltafa("D2", 6),
                new Deltafa("D3", 5),
                new Deltafa("D4", 11)
        );

        var result = underTest.simulateDay(plants, Radiation.ALPHA);

        assertEquals(Radiation.DELTA, result);
    }

    @Test
    @DisplayName("should return neutral when delta is greater then alpha by two")
    public void neutralResultDeltaTest() {
        var plants = List.of(
                new Puffancs("Pu", 5),
                new Deltafa("D1", 6),
                new Deltafa("D2", 8)
        );

        var result = underTest.simulateDay(plants, Radiation.ALPHA);

        assertEquals(Radiation.NEUTRAL, result);
    }

    @Test
    @DisplayName("should return neutral when alpha is greater then delta by two")
    public void neutralResultAlphaTest() {
        var plants = List.of(
                new Puffancs("Pu", 4),
                new Deltafa("D1", 8),
                new Deltafa("D2", 9)
        );

        var result = underTest.simulateDay(plants, Radiation.ALPHA);

        assertEquals(Radiation.NEUTRAL, result);
    }

}
