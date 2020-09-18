package hu.elte.javagyak.gyak2.hazi;

import hu.elte.javagyak.gyak2.hazi.geom.Circle;
import hu.elte.javagyak.gyak2.hazi.geom.Point;

public class PointCircleTest {

    public static void main(String[] args) {

        Point p = new Point(1.22, 1.33);

        System.out.println(p.getX());
        System.out.println(p.getY());
        p.setX(1.23);
        p.setY(2.56);
        System.out.println(p.getX());
        System.out.println(p.getY());
        p.move(1.22, 1.33);
        System.out.println(p.getX());
        System.out.println(p.getY());

        Circle c = new Circle();

        c.setCenter(p);
        c.setRadius(3.21);
        System.out.println(c.getRadius());
        c.move(1.22, 1.33);
        System.out.println(c.getCenter().getX());
        System.out.println(c.getCenter().getY());

        c.enlarge(2);

        System.out.println(c.getCenter().getX());
        System.out.println(c.getCenter().getY());
        System.out.println(c.getRadius());

        Point q = new Point(1.1, 2.2);

        System.out.println(q.getX());
        System.out.println(q.getY());

        System.out.println(q.distance(p));
        System.out.println(Point.distance(p, q));
        System.out.println(p);
        System.out.println(q);

        Circle a = new Circle(q, 3.4);

        System.out.println(a.getCenter().getX());
        System.out.println(a.getCenter().getY());
        System.out.println(a.getRadius());

        System.out.println(a.getArea());

        System.out.println(a);

        a.setCenter(p);

        System.out.println(a.liesWithin(q, 1.1));
        System.out.println(a.isInside(q));

        Circle b = new Circle(1.3, 4.5);

        System.out.println(b.getCenter().getX());
        System.out.println(b.getCenter().getY());
        System.out.println(b.getRadius());

        Circle d = new Circle();

        System.out.println(d.getCenter().getX());
        System.out.println(d.getCenter().getY());
        System.out.println(d.getRadius());

        centerOfMassTest();
    }

    public static void centerOfMassTest() {
        Point[] points1 = new Point[3];
        points1[0] = new Point(2.0, 4.0);
        points1[1] = new Point(3.0, 6.0);
        points1[2] = new Point(1.0, 2.0);

        Point[] points2 =
                {new Point(1.0, 2.0)
                        , new Point(2.0, 4.0)
                        , null
                };
        points2[2] = new Point(3.0, 6.0);

        System.out.println("centerOfMass(points1)==" + centerOfMass(points1));
        System.out.println("centerOfMass(points2)==" + centerOfMass(points2));
    }

    public static Point centerOfMass(Point[] points) {
        double cx = 0.0;
        double cy = 0.0;

//      for(int i=0; i<points.length; ++i) {
//        cx += points[i].getX();
//        cy += points[i].getY();
//      }

        for (Point p : points) {
            cx += p.getX();
            cy += p.getY();
        }

        cx /= points.length;  // double/int  implicit widening primitive conversion 
        cy /= points.length;

        return new Point(cx, cy);
    }
}
