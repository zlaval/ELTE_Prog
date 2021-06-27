package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;

public class Puffancs extends Plant {

    public Puffancs(String name, int nutrient) {
        super(name, nutrient);
    }

    @Override
    protected void handleRadiation(Radiation radiation) {
        switch (radiation) {
            case ALPHA -> modifyNutrient(OPERATION.ADD, 2);
            case DELTA -> modifyNutrient(OPERATION.SUB, 2);
            case NEUTRAL -> modifyNutrient(OPERATION.SUB, 1);
        }
    }

    @Override
    protected boolean dieByCondition() {
        return nutrient > 10;
    }

    @Override
    protected Effect calculateEffect() {
        return new Effect(Radiation.ALPHA, 10 - nutrient);
    }

}
