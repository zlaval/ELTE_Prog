package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;

public class Parabokor extends Plant {

    public Parabokor(String name, int nutrient) {
        super(name, nutrient);
    }

    @Override
    protected void handleRadiation(Radiation radiation) {
        switch (radiation) {
            case ALPHA, DELTA -> modifyNutrient(OPERATION.ADD, 1);
            case NEUTRAL -> modifyNutrient(OPERATION.SUB, 1);
        }
    }

    @Override
    protected Effect calculateEffect() {
        return new Effect(Radiation.NEUTRAL, 0);
    }
}
