package com.zlrx.progtech.vegetation.simulator.model;

import com.zlrx.progtech.vegetation.simulator.model.plants.Plant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public final class SimulationData {

    private final int days;

    @Singular
    private final List<Plant> plants;

}
