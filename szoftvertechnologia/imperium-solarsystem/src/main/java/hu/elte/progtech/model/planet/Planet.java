package hu.elte.progtech.model.planet;

import hu.elte.progtech.consts.MineType;
import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.draw.ship.ColonizerShipSprite;
import hu.elte.progtech.model.building.Building;
import hu.elte.progtech.model.building.StarGate;
import hu.elte.progtech.model.building.mine.DeuteriumMine;
import hu.elte.progtech.model.building.mine.IronMine;
import hu.elte.progtech.model.building.mine.SonarPanel;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.model.ship.ShipUnderConstruct;
import hu.elte.progtech.screen.view.Console;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static hu.elte.progtech.consts.Const.STARGATE_PRICE;

@Getter
public class Planet {

    private final String name;

    private final HashMap<ResourceType, Integer> resources = new HashMap<>();

    private final List<Building> buildings = new ArrayList<>();

    private final List<ShipUnderConstruct> shipUnderConstruct = new ArrayList<>();

    private Player player;

    private final Coord coord;

    private final Size size;

    private final int hotBonus;

    private final int maxBuilding;

    public Planet(String name, Coord coord, int hotBonus, int maxBuilding, Size size) {
        this.name = name;
        this.coord = coord;
        for (ResourceType type : ResourceType.values()) {
            if (type != ResourceType.DEUTERIUM) {
                resources.put(type, 0);
            }
        }
        this.hotBonus = hotBonus;
        this.maxBuilding = maxBuilding;
        this.size = size;
    }

    public void addResources(List<Resource> resources) {
        for (Resource resource : resources) {
            addResource(resource);
        }
    }

    public void addResource(Resource resource) {
        if (resource.getResourceType() != ResourceType.DEUTERIUM) {
            resources.put(resource.getResourceType(),
                    resources.get(resource.getResourceType()) + resource.getQuantity());
        } else {
            this.player.produceDelerium(resource.getQuantity());
        }
    }

    public boolean useResource(Resource resource) {
        if (resource.getResourceType() == ResourceType.DEUTERIUM) {
            return this.player.useDeuterium(resource.getQuantity());
        }
        synchronized (this) {
            if (resources.get(resource.getResourceType()) >= resource.getQuantity()) {
                resources.put(resource.getResourceType(),
                        resources.get(resource.getResourceType()) - resource.getQuantity());
                return true;
            }
        }
        return false;
    }

    public void conquer(Player conqueror) {
        if (player != null) {
            player.getPlanets().remove(this);
            Console.getConsole().addMessage(conqueror.getName() + ": Conquer " + getName() + ".");
        } else {
            addResource(new Resource(15, ResourceType.IRON));
            addResource(new Resource(10, ResourceType.ENERGY));
            Console.getConsole().addMessage(conqueror.getName() + ": Colonize " + getName() + ".");
        }

        player = conqueror;
        player.getPlanets().add(this);
    }

    public void conquerWithShip(Player conqueror , ColonizerShipSprite colonizerShipSprite) {
        conquer(conqueror);

        if( colonizerShipSprite != null ){
            colonizerShipSprite.remove();
        }

    }

    public boolean hasStarGate() {
        for (Building building : buildings) {
            if (building instanceof StarGate) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlaceToBuild() {
        return buildings.size() < maxBuilding;
    }


    public void buildIronMine() {
        if (hasPlaceToBuild() && useResource(new Resource(MineType.IRONMINE.getUpdatePriceLevels().get(0), ResourceType.IRON))) {
            Console.getConsole().addMessage(getPlayer().getName() + ": Building Iron mine...");
            buildings.add(new IronMine(this));
        }
    }

    public void buildStarGate() {
        if (hasPlaceToBuild() && useResource(new Resource(STARGATE_PRICE, ResourceType.IRON))) {
            Console.getConsole().addMessage(getPlayer().getName() + ": Building Stargate..");
            buildings.add(new StarGate(this));
        }
    }


    public void buildDeuteriumMine() {
        if (hasPlaceToBuild() && useResource(new Resource(MineType.DEUTERIUMMINE.getUpdatePriceLevels().get(0), ResourceType.IRON))) {
            Console.getConsole().addMessage(getPlayer().getName() + ": Building Deuterium mine..");
            buildings.add(new DeuteriumMine(this));
        }
    }

    public void buildSolarPanel() {
        if (hasPlaceToBuild() && useResource(new Resource(MineType.SONARPANEL.getUpdatePriceLevels().get(0), ResourceType.IRON))) {
            Console.getConsole().addMessage(getPlayer().getName() + ": Building Sonar Panel..");
            buildings.add(new SonarPanel(this));
        }
    }

    public void buildShip(ShipType shipType) {
        if (hasStarGate() && useResource(new Resource(shipType.getPrice(), ResourceType.IRON))) {
            Console.getConsole().addMessage(getPlayer().getName() + ": Building Space Ship " + shipType.getName() + "..");
            shipUnderConstruct.add(new ShipUnderConstruct(shipType));
        }
    }

    public void produce() {
        for (Building building : buildings) {
            building.produce();
        }
    }

}
