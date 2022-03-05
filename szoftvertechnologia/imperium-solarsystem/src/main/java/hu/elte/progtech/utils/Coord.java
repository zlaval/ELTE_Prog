package hu.elte.progtech.utils;

import hu.elte.progtech.consts.Const;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Coord {

    private final int x;
    private final int y;

    public static Coord get(int x, int y) {
        return new Coord(x, y);
    }

    public static boolean isDistanceInRange(Coord firstCoord, Coord secondCoord){
        if(Const.SHOOT_RANGE >= getShootDistance(firstCoord, secondCoord)){
            return true;
        }
        return false;
    }

    private static double getShootDistance(Coord firstCoord, Coord secondCoord){
        return Math.sqrt(Math.pow(secondCoord.getX() - firstCoord.getX(), 2) + Math.pow(secondCoord.getY() - firstCoord.getY(), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
