package hu.elte.factory;

import hu.elte.geometry.Circle;
import hu.elte.geometry.Point;

final class CircleFactory implements ShapeFactory {

    @Override
    public Circle getShape(int x, int y, int r) {
        return new Circle(new Point(x, y), r);
    }
}
