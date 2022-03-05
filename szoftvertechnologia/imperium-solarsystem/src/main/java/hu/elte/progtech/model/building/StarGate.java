package hu.elte.progtech.model.building;

import hu.elte.progtech.model.planet.Planet;

public class StarGate extends Building {

    public StarGate(Planet planet) {
        super(0, 0, planet, "Stargate");
    }

    @Override
    public boolean levelUp() {
        return false;
    }

    @Override
    public void produce() {

    }
}
