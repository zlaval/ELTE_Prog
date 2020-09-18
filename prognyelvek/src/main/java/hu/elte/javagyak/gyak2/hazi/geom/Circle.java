package hu.elte.javagyak.gyak2.hazi.geom;

public class Circle {

    private Point center;
    private double radius;
    
    public Circle(Point p, double radius) {
        center = new Point(p.getX(),p.getY()); 
        this.radius = radius;  
    }
    
    public Circle(double x, double y) {
        center = new Point(x,y);
    }
    
    public Circle() {
        //a double -double parameteru konstruktor hivasa
        this(0.0,0.0);
    }
    
    public Point getCenter() {
        return new Point(center.getX(),center.getY());
    }
    
    public void setCenter(Point center) {
        this.center = new Point(center.getX(),center.getY());
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double r) {
        if (r < 0.0) r = 0.0;
        radius = r;
    }
    
    public void move(double dx, double dy) {
        center.move(dx, dy);
    }
    
    public void enlarge(double factor) {
        radius *= factor;
    }
    
    public String toString() {
        return center + " radius=" + radius;	
    }
    
    public double getArea() {
        return Math.PI*radius*radius;
    }
    
    
    public boolean liesWithin(Point p, double delta) {
        return Math.abs(center.distance(p) - radius) < delta;
    }
    
    public boolean isInside(Point p) {
        return (center.distance(p) <=  radius);
    }
}
