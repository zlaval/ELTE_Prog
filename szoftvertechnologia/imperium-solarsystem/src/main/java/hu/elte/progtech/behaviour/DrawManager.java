package hu.elte.progtech.behaviour;

import hu.elte.progtech.draw.Drawable;
import hu.elte.progtech.draw.MissileSprite;
import hu.elte.progtech.draw.Movable;
import hu.elte.progtech.draw.Selectable;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.draw.ship.SpaceShipSprite;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.ship.SpaceShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Utility;
import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hu.elte.progtech.consts.Const.SHOT_COST;

public class DrawManager {

    @Getter
    @Setter
    private static Graphics2D graphics;

    @Getter
    private final List<Drawable> drawableElements;

    private final List<Drawable> preregistered;

    public DrawManager() {
        drawableElements = new ArrayList<>();
        preregistered = new ArrayList<>();
    }

    public void redraw() {
        checkMissileHits();
        removeRemovable();

        drawableElements.addAll(preregistered);
        preregistered.clear();
        drawableElements
                .stream()
                .filter(e -> e instanceof Movable)
                .map(e -> (Movable) e)
                .forEach(Movable::onMove);
    }

    public void handleClickEvent(Coord coord, Player actualPlayer) {
        var attackedShip = checkAttack(coord, actualPlayer);
        if (attackedShip.isPresent()) {
            fire(actualPlayer, coord);
        } else {
            moveSelected(coord);
        }
    }

    private Optional<SpaceShipSprite> checkAttack(Coord click, Player actualPlayer) {
        var enemyShips = getEnemyShips(actualPlayer);
        for (var ship : enemyShips) {
            if (Utility.isClicked(click, ship.getCoord(), ship.getSize())) {
                return Optional.of(ship);
            }
        }
        return Optional.empty();
    }

    private void checkMissileHits() {
        drawableElements.stream()
                .filter(e -> e instanceof MissileSprite)
                .distinct()
                .map(e -> (MissileSprite) e)
                .forEach(m -> {
                    var shipHit = checkAttack(m.getCoord(), m.getPlayer());
                    shipHit.ifPresent(s -> {
                        if (!m.isRemovable()) {
                            m.remove();
                            s.hit(m.getDamage());
                        }
                    });
                });
    }

    private void removeRemovable() {
        var removable = drawableElements
                .stream()
                .filter(e -> e instanceof Movable)
                .map(e -> (Movable) e)
                .filter(Movable::isRemovable)
                .collect(Collectors.toList());
        drawableElements.removeAll(removable);
    }

    void registerDrawable(Collection<? extends Drawable> drawables) {
        preregistered.addAll(drawables);
        //drawableElements.addAll(drawables);
    }

    public List<SpaceShipSprite> getEnemyShips(Player player) {
        return drawableElements.stream()
                .filter(e -> e instanceof SpaceShipSprite)
                .map(e -> (SpaceShipSprite) e)
                .filter(e -> e.isEnemy(player))
                .collect(Collectors.toList());
    }

    public void registerMovable(Movable movable) {
        preregistered.add(movable);
    }

    public void removeMovable(Movable movable) {
        drawableElements.remove(movable);
        movable.remove();
    }

    public void fire(Player player, Coord to) {
        var missiles = drawableElements.stream()
                .filter(e -> e instanceof SpaceShipSprite)
                .filter(Drawable::isSelected)
                .map(e -> (SpaceShipSprite) e)
                .map(a -> tryFire(player, to, a))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        registerDrawable(missiles);
    }

    private Optional<MissileSprite> tryFire(Player player, Coord to, SpaceShipSprite spaceShipSprite) {
        MissileSprite result = null;
        if (Coord.isDistanceInRange(spaceShipSprite.getCoord(), to)) {
            if (player.useActionPoint(SHOT_COST)) {
                result = new MissileSprite(player, getMiddle(spaceShipSprite), to, this, spaceShipSprite.getSpaceShip().getAttack());
            }
        } else {
            spaceShipSprite.setDestination(to);
        }
        return Optional.ofNullable(result);
    }

    public Coord getMiddle(Drawable drawable) {
        var coord = drawable.getCoord();
        var size = drawable.getSize();

        return Coord.get(coord.getX() + (size.getWidth() / 2), coord.getY() + (size.getHeight() / 2));
    }

    public void moveSelected(Coord destination) {
        drawableElements.stream()
                .filter(e -> e instanceof SpaceShipSprite)
                .filter(Drawable::isSelected)
                .forEach(e -> ((SpaceShipSprite) e).setDestination(destination));
    }

    public void selectElement(Coord start, Coord end, Player actualPlayer) {
        drawableElements
                .stream()
                .filter(e -> e instanceof Selectable)
                .map(e -> (Selectable) e)
                .forEach(e -> e.onSelect(start, end, actualPlayer));
    }

    public Optional<Planet> getSelectedPlanet() {
        return drawableElements.stream()
                .filter(Drawable::isSelected)
                .filter(e -> e instanceof PlanetSprite)
                .map(e -> ((PlanetSprite) e).getPlanet())
                .findFirst();
    }

    public Optional<PlanetSprite> getSelectedPlanetSprite() {
        return drawableElements.stream()
                .filter(Drawable::isSelected)
                .filter(e -> e instanceof PlanetSprite)
                .map(e -> ((PlanetSprite) e))
                .findFirst();
    }

    public void deselectAll() {
        drawableElements
                .stream()
                .filter(e -> e instanceof Selectable)
                .map(e -> (Selectable) e)
                .forEach(Selectable::onDeselect);
    }

}
