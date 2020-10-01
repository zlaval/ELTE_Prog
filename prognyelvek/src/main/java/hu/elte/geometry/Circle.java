package hu.elte.geometry;

public class Circle {
    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center; // TOOD: complete getter
    }

    public double getRadius() {
        return radius; // TOOD: complete getter
    }
 
    public boolean intersects(Circle other) {
        return Point.getDistance(center, other.center) <= radius + other.radius;
    }
}