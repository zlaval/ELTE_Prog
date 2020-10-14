package hu.elte.factory;

import hu.elte.ShapeType;
import hu.elte.exception.NoSuchFactoryException;

public final class AbstractShapeFactory {

    private final PointFactory pointFactory;
    private final CircleFactory circleFactory;

    private static class InitHelper {
        private static final AbstractShapeFactory INSTANCE = new AbstractShapeFactory();
    }

    public static AbstractShapeFactory getINSTANCE() {
        return InitHelper.INSTANCE;
    }

    private AbstractShapeFactory() {
        this.circleFactory = new CircleFactory();
        this.pointFactory = new PointFactory();
    }

    public ShapeFactory getShapeFactory(ShapeType shapeType) {
        switch (shapeType) {
            case POINT:
                return this.pointFactory;
            case CIRCLE:
                return this.circleFactory;
        }

        throw new NoSuchFactoryException();
    }

}
