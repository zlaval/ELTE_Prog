package hu.elte.progtech.model.building;

import hu.elte.progtech.consts.MineType;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.screen.view.Console;
import hu.elte.progtech.model.player.Player;

public abstract class Mine extends Building {

    private MineType mine;
    private int constructionTime;

    public Mine(MineType mine, Planet planet) {
        super(mine.getProductionRateLevels().get(0), mine.getEnergyConsumptionLevels().get(0), planet, mine.getName());
        this.mine = mine;
        this.constructionTime = 0;
    }

    public boolean construct() {
        if (constructionTime != 0) {
            constructionTime--;
            if (constructionTime == 0) {
                Console.getConsole().addMessage(planet.getPlayer().getName() + ": upgrade " + getName() + " from " + level + " to " + (level + 1));
                super.level += 1;
                this.productionRate = mine.getProductionRateLevels().get(level);
                this.energyConsumption = mine.getEnergyConsumptionLevels().get(level);
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean levelUp() {
        if (super.level < 4
                && planet.getPlayer().isAbleToDevelop()
                && planet.useResource(new Resource(mine.getUpdatePriceLevels().get(level), ResourceType.IRON))
                && this.constructionTime == 0) {
            planet.getPlayer().develop();
            this.constructionTime = mine.getConstructionTime();
            Console.getConsole().addMessage(planet.getPlayer().getName() + ": start to upgrade " + getName() + " from " + level + " to " + (level + 1));
            return true;
        }
        return false;
    }


}
