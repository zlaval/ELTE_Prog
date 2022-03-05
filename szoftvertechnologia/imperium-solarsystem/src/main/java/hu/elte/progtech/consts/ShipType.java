package hu.elte.progtech.consts;

import lombok.Getter;

@Getter
public enum ShipType {
    MOTHER_SHIP(15, 8, 1.1, 150, 8, 40, "Mothership", 3),
    FIGHTER(15, 3, 1.5, 50, 2, 15, "Fighter", 1),
    CRUISER(20, 5, 1.8, 70, 3, 25, "Cruiser", 2),
    BATTLESHIP(20, 8, 1.1, 100, 5, 40, "Battleship", 2),
    COLONIZER(10, 5, 1.1, 100, 5, 25, "Colonizer", 2),
    SHUTTLE(5, 3, 1.1, 50, 2, 10, "Shuttle", 2);

    private final int attack;
    private final int defense;
    private final double speed;
    private final int life;
    private final int consumption;
    private final int price;
    private final String name;
    private final int constructionTime;

    ShipType(int attack, int defense, double speed, int life, int consumption, int price, String name, int constructionTime) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.life = life;
        this.consumption = consumption;
        this.price = price;
        this.name = name;
        this.constructionTime = constructionTime;
    }
}
