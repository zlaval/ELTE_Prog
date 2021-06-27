package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.helper.ResultPrinter;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VegetationSimulator {

    private final SimulationDataProvider provider;
    private final DayElapseSimulator dayElapseSimulator;
    private final ResultPrinter printer;

    /**
     * Reads data from the file.
     * Runs the simulation until the given day and print the result of every day
     * to the standard input.
     *
     * @param path of the data file
     */
    public final void runSimulation(String path) {
        var maybeSimulationData = provider.provide(path);
        maybeSimulationData.ifPresent(simulationData -> {
            var plants = simulationData.getPlants();
            var actualRadiation = Radiation.NEUTRAL;
            for (int day = 0; day < simulationData.getDays(); day++) {
                var nextRadiation = dayElapseSimulator.simulateDay(plants, actualRadiation);
                printer.printResult(plants, day, actualRadiation, nextRadiation);
                actualRadiation = nextRadiation;
            }
        });
    }
}
