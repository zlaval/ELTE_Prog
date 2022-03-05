package hu.elte.progtech.model.player;

import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.model.ship.SpaceShip;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static hu.elte.progtech.consts.Const.INIT_ACTION_POINTS;

@Getter
public class Player {

    private final String name;
    private final Color color;
    private int actionPoints;
    private List<Planet> planets;
    private List<SpaceShip> ships;
    private Resource deuterium;
    private boolean ableToDevelop;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        actionPoints = INIT_ACTION_POINTS;
        planets = new ArrayList<>();
        ships = new ArrayList<>();
        deuterium = new Resource(50, ResourceType.DEUTERIUM);
        ableToDevelop = true;
    }

    @Setter
    private boolean active;

    public boolean useDeuterium(int amount) {
        return deuterium.remove(amount);
    }

    public void produceDelerium(int amount) {
        deuterium.add(amount);
    }

    public boolean useActionPoint(int used) {
        var actionPointLeft = actionPoints - used;
        if (actionPointLeft < 0) {
            return false;
        }
        actionPoints = actionPointLeft;
        return true;
    }

    public boolean develop(){
        return (ableToDevelop = false);
    }

    public void nextRound() {
        actionPoints = INIT_ACTION_POINTS;
        ableToDevelop = true;
    }

}
