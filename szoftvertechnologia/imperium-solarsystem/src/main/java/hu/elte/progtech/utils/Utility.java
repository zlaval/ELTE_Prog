package hu.elte.progtech.utils;

import hu.elte.progtech.consts.Const;
import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.ship.ColonizerShip;
import hu.elte.progtech.model.ship.MotherShip;
import hu.elte.progtech.model.ship.ShuttleShip;
import hu.elte.progtech.model.ship.SpaceShip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hu.elte.progtech.consts.Const.PLANET_DEFENSE_SHIP_NEARBY_RECTANGLE_DIMENSION;
import static hu.elte.progtech.consts.Const.PLANET_NEARBY_RECTANGLE_DIMENSION;

public final class Utility {


    public static boolean isClicked(Coord click, Coord objectStart, Size objectSize) {
        if (click.getX() >= objectStart.getX() &&
                click.getX() <= objectStart.getX() + objectSize.getWidth() &&
                click.getY() >= objectStart.getY() &&
                click.getY() <= objectStart.getY() + objectSize.getHeight()
        ) {
            return true;
        }
        return false;
    }

    public static List<SpaceShip> getShipNearby(Planet planet, ShipType shipType, int distance) {
        var planetCoord = planet.getCoord();
        var coord = Coord.get(planetCoord.getX() + (planet.getSize().getWidth() / 2), planetCoord.getY() + (planet.getSize().getHeight() / 2));
        var result = new ArrayList<SpaceShip>();
        var ships = planet.getPlayer().getShips().stream().filter(s -> isShipInType(s, shipType)).collect(Collectors.toList());

        var upCornerX = Math.max(0, coord.getX() - distance);
        var upCornerY = Math.max(0, coord.getY() - distance);
        var upCorner = Coord.get(upCornerX, upCornerY);

        var downCornerX = Math.min(Const.WIDTH, coord.getX() + distance);
        var downCornerY = Math.min(Const.HEIGHT, coord.getY() + distance);
        var downCorner = Coord.get(downCornerX, downCornerY);

        for (var ship : ships) {
            if (isSelected(upCorner, downCorner, ship.getSprite().getCoord(), ship.getSprite().getSize())) {
                result.add(ship);
            }

        }
        return result;


    }

    public static boolean isShipNearby(Coord click, Player player, ShipType shipType) {
        var ships = player.getShips().stream().filter(s -> isShipInType(s, shipType)).collect(Collectors.toList());
        var upCornerX = Math.max(0, click.getX() - PLANET_NEARBY_RECTANGLE_DIMENSION);
        var upCornerY = Math.max(0, click.getY() - PLANET_NEARBY_RECTANGLE_DIMENSION);
        var upCorner = Coord.get(upCornerX, upCornerY);

        var downCornerX = Math.min(Const.WIDTH, click.getX() + PLANET_NEARBY_RECTANGLE_DIMENSION);
        var downCornerY = Math.min(Const.HEIGHT, click.getY() + PLANET_NEARBY_RECTANGLE_DIMENSION);
        var downCorner = Coord.get(downCornerX, downCornerY);

        for (var ship : ships) {
            if (isSelected(upCorner, downCorner, ship.getSprite().getCoord(), ship.getSprite().getSize())) {
                return true;
            }

        }
        return false;
    }

    public static boolean isOwnShipNearby(Coord click, List<SpaceShip> ships) {
        var upCornerX = Math.max(0, click.getX() - PLANET_DEFENSE_SHIP_NEARBY_RECTANGLE_DIMENSION);
        var upCornerY = Math.max(0, click.getY() - PLANET_DEFENSE_SHIP_NEARBY_RECTANGLE_DIMENSION);
        var upCorner = Coord.get(upCornerX, upCornerY);

        var downCornerX = Math.min(Const.WIDTH, click.getX() + PLANET_DEFENSE_SHIP_NEARBY_RECTANGLE_DIMENSION);
        var downCornerY = Math.min(Const.HEIGHT, click.getY() + PLANET_DEFENSE_SHIP_NEARBY_RECTANGLE_DIMENSION);
        var downCorner = Coord.get(downCornerX, downCornerY);

        for (var ship : ships) {
            if (isSelected(upCorner, downCorner, ship.getSprite().getCoord(), ship.getSprite().getSize())) {
                return true;
            }

        }
        return false;
    }

    private static boolean isShipInType(SpaceShip spaceShip, ShipType shipType) {
        return switch (shipType) {
            case MOTHER_SHIP -> spaceShip instanceof MotherShip;
            case COLONIZER -> spaceShip instanceof ColonizerShip;
            case SHUTTLE -> spaceShip instanceof ShuttleShip;
            default -> false;
        };
    }

    public static boolean isSelected(Coord start, Coord end, Coord objectStart, Size objectSize) {
        int sx = Math.min(start.getX(), end.getX());
        int sy = Math.min(start.getY(), end.getY());
        int ex = Math.max(start.getX(), end.getX());
        int ey = Math.max(start.getY(), end.getY());


        if (sx - objectSize.getWidth() <= objectStart.getX() &&
                ex >= objectStart.getX() &&
                sy - objectSize.getHeight() <= objectStart.getY() &&
                ey >= objectStart.getY()
        ) {
            return true;
        }

        return false;
    }


}
