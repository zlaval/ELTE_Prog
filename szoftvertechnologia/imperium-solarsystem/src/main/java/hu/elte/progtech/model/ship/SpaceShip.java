package hu.elte.progtech.model.ship;

import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.draw.ship.SpaceShipSprite;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.screen.view.Console;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class SpaceShip {

    protected int attack;
    protected int defense;
    protected double speed;
    protected int consumption;
    protected int life;
    protected String name;
    protected int price;
    protected Player player;
    @Setter
    @Getter
    protected SpaceShipSprite sprite;

    public SpaceShip(ShipType type, Player player) {
        this.attack = type.getAttack();
        this.defense = type.getDefense();
        this.speed = type.getSpeed();
        this.consumption = type.getConsumption();
        this.life = type.getLife();
        this.name = type.getName();
        this.price = type.getPrice();
        this.player = player;
        registerToPlayer();
    }

    public void hit(int damage) {
        int realDamage = Math.max(damage - defense, 0);
        life -= realDamage;
        Console.getConsole().addMessage(name + " received " + realDamage + " damage.");
    }

    public void consumeDeuterium() {
        player.useDeuterium(consumption);
    }

    public boolean consumeActionPoints(int actionPoints) {
        return player.useActionPoint(actionPoints);
    }

    private void registerToPlayer() {
        player.getShips().add(this);
    }

    public void destroy() {
        player.getShips().remove(this);
    }
}
