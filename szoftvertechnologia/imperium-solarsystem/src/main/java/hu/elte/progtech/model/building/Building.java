package hu.elte.progtech.model.building;

import hu.elte.progtech.model.planet.Planet;
import lombok.Getter;

@Getter
public abstract class Building {

    protected String name;

    protected int level;

    protected int energyConsumption;

    protected int productionRate;

    protected Planet planet;

    public Building(int productionRate, int energyConsumption, Planet planet, String name) {
        this.level = 0;
        this.productionRate = productionRate;
        this.energyConsumption = energyConsumption;
        this.planet = planet;
        this.name = name;
    }

    public abstract boolean levelUp();

    public abstract void produce();

}

