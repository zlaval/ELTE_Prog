package hu.elte.progtech.draw;

import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.model.planet.Planet;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;

public interface Collidable{
//    default public Collidable(Image image, Coord coord, Size size) {
//        this.image = image;
//        this.coord = coord;
//        this.size = size;
//    }

    public Optional<Planet> getCollidingPlanetIf();

    public Optional<Planet> isCollidingWith();
}
