package hu.elte.progtech.model.building.mine;

import hu.elte.progtech.consts.MineType;
import hu.elte.progtech.model.building.Mine;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;

public class SonarPanel extends Mine {
    public SonarPanel(Planet planet) {
        super(MineType.SONARPANEL, planet);
    }

    @Override
    public void produce(){
        this.construct();
        if( planet.useResource( new Resource(energyConsumption, ResourceType.ENERGY) )) {
            planet.addResource(new Resource(productionRate + super.planet.getHotBonus(), ResourceType.ENERGY));
        }
    }
}