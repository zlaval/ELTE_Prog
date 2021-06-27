package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.model.SimulationData;

import java.util.Optional;

public interface SimulationDataProvider {

    /**
     * Provides simulation data from the given source
     *
     * @param resourcePath
     * @return SimulationData or empty()
     */
    Optional<SimulationData> provide(String resourcePath);

}
