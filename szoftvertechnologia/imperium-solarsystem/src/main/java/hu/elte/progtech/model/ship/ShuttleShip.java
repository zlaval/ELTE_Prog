package hu.elte.progtech.model.ship;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.resources.Resource;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ShuttleShip extends SpaceShip {
    @Getter
    private List<Resource> resources = new ArrayList<>();

    public ShuttleShip(Player player) {
        super(ShipType.SHUTTLE, player);
    }

    public void load(List<Resource> export, Planet planet) {
        export.forEach(resource -> {
            if (planet.useResource(resource)) {
                resources.add(resource);
            }

        });
    }

    public void unLoad(Planet planet) {
        planet.addResources(resources);
        resources = new ArrayList<>();
    }
}
