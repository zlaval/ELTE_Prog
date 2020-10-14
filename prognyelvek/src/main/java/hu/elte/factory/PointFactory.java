package hu.elte.factory;

import hu.elte.geometry.Point;

class PointFactory implements ShapeFactory {
    @Override
    public Point getShape(int x, int y, int z) {
        return new Point(x, y);
    }
}
