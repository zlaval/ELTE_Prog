package com.zlrx.progtech.vegetation.simulator.model.plants;

import com.zlrx.progtech.vegetation.simulator.model.Effect;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@Getter
@EqualsAndHashCode
public abstract class Plant {

    private final String name;
    private boolean alive;
    protected int nutrient;

    protected enum OPERATION {
        ADD, SUB
    }

    Plant(String name, int nutrient) {
        this.name = name;
        this.nutrient = Math.max(nutrient, 0);
        this.alive = nutrient > 0;
    }

    /**
     * Simulates a day pass under the given type of radiation.
     * Dead plant's effect returns as an empty() otherwise
     * the calculated effect.
     *
     * @param radiation of the day
     * @return Optional. Effect to the next day
     */
    public final Optional<Effect> dayPass(Radiation radiation) {
        if (!alive) {
            return Optional.empty();
        }
        handleRadiation(radiation);
        Effect effect = null;
        if (inspectDead()) {
            effect = calculateEffect();
        }
        return Optional.ofNullable(effect);
    }

    protected final void modifyNutrient(OPERATION operation, int value) {
        if (operation == OPERATION.ADD) {
            nutrient += value;
        } else if (operation == OPERATION.SUB) {
            nutrient -= value;
        }
        if (nutrient < 0) {
            nutrient = 0;
        }
    }

    private boolean inspectDead() {
        if (nutrient <= 0 || dieByCondition()) {
            alive = false;
        }
        return alive;
    }

    /**
     * Unique behaviour of the plant under the given radiation.
     *
     * @param radiation of the day
     */
    protected abstract void handleRadiation(Radiation radiation);

    /**
     * Every plant dies when nutrients fall below 1.
     * If any other condition is necessary, it can be provided
     * by @Override this method.
     *
     * @return boolean
     */
    protected boolean dieByCondition() {
        return false;
    }

    /**
     * Method calculates the effect to the next day's radiation from
     * the actual condition of the plant.
     * Only executed if the plant is alive.
     *
     * @return effect to the next day
     */
    protected abstract Effect calculateEffect();


    @Override
    public String toString() {
        var type = this.getClass().getSimpleName();
        var str = name + " (" + type + ")" + " has " + nutrient + " nutrients. The plant is";
        str += alive ? " alive. " : " dead. ";
        return str;
    }

}
