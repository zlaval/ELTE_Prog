package hu.elte.geometry;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double getDistance(Point p1, Point p2) {
        return Math.sqrt((p2.x-p1.x) * (p2.x-p1.x) + (p2.y-p1.y) * (p2.y-p1.y));
    }

    public double getX() {
        return x; // complete getter
    }

    public double getY() {
        return y; // complete getter
    }
    
}