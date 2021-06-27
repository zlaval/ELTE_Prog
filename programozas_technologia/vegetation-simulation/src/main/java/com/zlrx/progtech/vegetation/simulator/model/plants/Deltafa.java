package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;

public class Deltafa extends Plant {

    public Deltafa(String name, int nutrient) {
        super(name, nutrient);
    }

    @Override
    protected void handleRadiation(Radiation radiation) {
        switch (radiation) {
            case ALPHA -> modifyNutrient(OPERATION.SUB, 3);
            case DELTA -> modifyNutrient(OPERATION.ADD, 4);
            case NEUTRAL -> modifyNutrient(OPERATION.SUB, 1);
        }
    }

    @Override
    protected Effect calculateEffect() {
        Effect result;
        if (nutrient < 5) {
            result = new Effect(Radiation.DELTA, 4);
        } else if (nutrient > 10) {
            result = new Effect(Radiation.NEUTRAL, 0);
        } else {
            result = new Effect(Radiation.DELTA, 1);
        }
        return result;
    }
}
