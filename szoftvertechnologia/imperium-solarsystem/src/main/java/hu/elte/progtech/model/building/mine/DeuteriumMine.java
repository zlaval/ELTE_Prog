package hu.elte.progtech.model.building.mine;

import hu.elte.progtech.model.building.Mine;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;

import hu.elte.progtech.consts.MineType;

public class DeuteriumMine extends Mine {
    public DeuteriumMine( Planet planet) {
        super(MineType.DEUTERIUMMINE, planet);
    }

    @Override
    public void produce(){
        this.construct();
        if( planet.useResource( new Resource(energyConsumption, ResourceType.ENERGY) )) {
            planet.addResource(new Resource(productionRate, ResourceType.DEUTERIUM));
        }
    }
}