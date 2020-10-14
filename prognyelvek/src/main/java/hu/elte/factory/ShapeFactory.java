package hu.elte.factory;

import hu.elte.geometry.Shape;

public interface ShapeFactory {

    //antipattern :D
    Shape getShape(int x, int y, int r);

}
