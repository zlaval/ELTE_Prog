package com.zlrx.progtech.vegetation.simulator.helper;

import com.zlrx.progtech.vegetation.simulator.model.plants.Deltafa;
import com.zlrx.progtech.vegetation.simulator.model.plants.Parabokor;
import com.zlrx.progtech.vegetation.simulator.model.plants.Plant;
import com.zlrx.progtech.vegetation.simulator.model.plants.Puffancs;

import java.util.Optional;

public class PlantFactory {

    private static final String PUFFANCS = "a";
    private static final String DELTAFA = "d";
    private static final String PARABOKOR = "p";

    /**
     * Creates object from the corresponding subtype of the Plant class
     *
     * @param name     of the Plant
     * @param type     of the Plant
     * @param nutrient of the Plant
     * @return Plant
     */
    public Optional<Plant> getPlant(String name, String type, int nutrient) {
        Plant plant = switch (type) {
            case PUFFANCS -> new Puffancs(name, nutrient);
            case DELTAFA -> new Deltafa(name, nutrient);
            case PARABOKOR -> new Parabokor(name, nutrient);
            default -> null;
        };

        return Optional.ofNullable(plant);
    }

}
