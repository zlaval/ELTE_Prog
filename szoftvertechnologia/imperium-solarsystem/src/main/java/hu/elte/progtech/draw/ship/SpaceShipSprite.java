package hu.elte.progtech.draw.ship;

import hu.elte.progtech.draw.Movable;
import hu.elte.progtech.draw.Selectable;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.ship.SpaceShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import hu.elte.progtech.utils.Utility;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;

public abstract class SpaceShipSprite extends Movable implements Selectable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private long lastMillis = 0;

    private long lastActionPointConsumeTime = 0;

    @Getter
    protected SpaceShip spaceShip;

    private boolean canMove = true;

    public SpaceShipSprite(SpaceShip spaceShip, Image image, Coord coord, Size size) {
        super(image, coord, size);
        this.spaceShip = spaceShip;

        spaceShip.setSprite(this);
    }

    public void hit(int damage) {
        spaceShip.hit(damage);
        logger.debug("{} has {} life.", spaceShip.getName(), spaceShip.getLife());
        if (spaceShip.getLife() <= 0) {
            remove();
        }
    }

    public boolean isEnemy(Player actualPlayer) {
        return actualPlayer != spaceShip.getPlayer();
    }

    @Override
    public void onSelect(Coord start, Coord end, Player actualPlayer) {
        if (actualPlayer == spaceShip.getPlayer()) {
            if (end == null) {
                setSelected(Utility.isClicked(start, coord, size));
            } else {
                setSelected(Utility.isSelected(start, end, coord, size));
            }
        }
    }

    @Override
    public void onDeselect() {
        spaceShip.getSprite().clearPointsUntilDestinationArray();
        setSelected(false);
    }

    @Override
    public double getSpeed() {
        return spaceShip.getSpeed();
    }

    @Override
    public void remove() {
        spaceShip.destroy();
        removable = true;
    }

    @Override
    protected void move() {
        super.move();
        var currentTime = System.currentTimeMillis();
        var elapsedTime = currentTime - lastMillis;
        if (elapsedTime > 1500) {
            spaceShip.consumeDeuterium();
            lastMillis = currentTime;
        }
    }

    @Override
    protected boolean canMove() {
        var hasEnoughtDeuretium = spaceShip.getPlayer().getDeuterium().getQuantity() > spaceShip.getConsumption();

        if (!hasEnoughtDeuretium) {
            return false;
        }

        var currentTime = System.currentTimeMillis();
        var elapsedTime = currentTime - lastActionPointConsumeTime;
        if (elapsedTime > 2000) {
            lastActionPointConsumeTime = currentTime;
            canMove = spaceShip.consumeActionPoints(1);
        }

        return canMove;
    }
}
