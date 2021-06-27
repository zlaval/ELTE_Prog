package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.helper.PlantFactory;
import com.zlrx.progtech.vegetation.simulator.model.SimulationData;
import com.zlrx.progtech.vegetation.simulator.model.plants.Plant;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class TextFileParser implements SimulationDataProvider {

    private final PlantFactory factory;

    private enum DATA_INDEX {
        NAME, TYPE, NUTRIENT;

        private int index() {
            return ordinal();
        }
    }

    @Override
    public Optional<SimulationData> provide(String resourcePath) {
        try (var bf = new BufferedReader(new FileReader(resourcePath))) {
            var builder = SimulationData.builder();
            var plantCount = Integer.parseInt(bf.readLine());
            for (int row = 0; row < plantCount; row++) {
                var line = bf.readLine().split(" ");
                processRow(line).ifPresent(builder::plant);
            }
            var simulationDays = Integer.parseInt(bf.readLine());
            var result = builder.days(simulationDays).build();
            return Optional.of(result);
        } catch (IOException e) {
            System.out.println("File not found");
            return Optional.empty();
        }
    }

    private Optional<Plant> processRow(String[] line) {
        var name = line[DATA_INDEX.NAME.index()];
        var type = line[DATA_INDEX.TYPE.index()];
        var nutrient = Integer.parseInt(line[DATA_INDEX.NUTRIENT.index()]);
        return factory.getPlant(name, type, nutrient);
    }

}
