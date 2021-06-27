package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import com.zlrx.progtech.vegetation.simulator.model.plants.Plant;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

public class DayElapseSimulator {

    /**
     * Simulates a day pass.
     * Execute the effect of the radiation on every plants alive.
     * Calculates the radiation of the next day by the effect of the plants.
     *
     * @param plants
     * @param radiation
     * @return
     */
    public Radiation simulateDay(List<Plant> plants, Radiation radiation) {
        var radiations = plants.stream()
                .map(plant -> plant.dayPass(radiation))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(Effect::radiation, summingInt(Effect::quantity)));

        return chooseNext(radiations);
    }

    private Radiation chooseNext(Map<Radiation, Integer> radiations) {
        var alpha = radiations.getOrDefault(Radiation.ALPHA, 0);
        var delta = radiations.getOrDefault(Radiation.DELTA, 0);
        Radiation result;
        if (alpha >= delta + 3) {
            result = Radiation.ALPHA;
        } else if (delta >= alpha + 3) {
            result = Radiation.DELTA;
        } else {
            result = Radiation.NEUTRAL;
        }
        return result;
    }

}