package hu.elte.progtech.draw;

import hu.elte.progtech.draw.planet.BlackHoleSprite;
import hu.elte.progtech.draw.planet.MeteorSprite;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.screen.view.PlayGround;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import lombok.Getter;

import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;

public abstract class Movable extends Drawable{

    private Coord destination;
    private Deque<Coord> pointsUntilDestination;
    protected boolean started = false;

    @Getter
    protected boolean removable = false;

    public Movable(Image image, Coord coord, Size size) {
        super(image, coord, size);
        pointsUntilDestination = new LinkedList<>();
    }

    public abstract double getSpeed();

    protected void doBeforeRemove() {

    }

    protected void move() {

    }

    protected boolean canMove() {
        return true;
    }

    public void clearPointsUntilDestinationArray(){
        pointsUntilDestination.clear();
    }

    public void onMove() {
        if (destination != null) {
            started = true;
            calculateRoute();
            destination = null;
        }
        if (!pointsUntilDestination.isEmpty()) {
            if (canMove()) {
                move();
                coord = pointsUntilDestination.poll();
            }
        } else if (started && canRemoveOnArrive()) {
            doBeforeRemove();
            removable = true;
        }
        render();
    }



    private void calculateRoute() {
        var speed = getSpeed();
        pointsUntilDestination = new LinkedList<>();
        double x = coord.getX();
        double y = coord.getY();

        boolean end = true;
//        Rectangle destinationRectangle = new Rectangle( destination.getX() , destination.getY() , size.getWidth() , size.getHeight() );
//        Rectangle destinationOverlap = checkIntersection(destinationRectangle);
//        if( isOverlapping(destinationOverlap) ){
//            end = false;
//        }

        while (end) {
            double deltaX = destination.getX() - x;
            double deltaY = destination.getY() - y;
            double direction = Math.atan2(deltaY, deltaX);

            double next_x = x + speed * Math.cos(direction);
            double next_y = y + speed * Math.sin(direction);

            int thisWidth = this.size.getWidth();
            int thisHeight = this.size.getHeight();

            Rectangle nextState = new Rectangle( (int)next_x , (int)next_y , thisWidth , thisHeight );
            Rectangle overlap = checkIntersection(nextState);
            Boolean shipIsOverlapping = isOverlapping(overlap);

            if (shipIsOverlapping){
                x += 0;
                y += 0;
                end = false;
//                if ( overlap.getHeight() < overlap.getWidth() ) {
//                    x += ( deltaX > 0 ? 1 : -1 ) * speed;
//                    y += 0;
//                }
//                else if ( overlap.getWidth() < overlap.getHeight() ){
//                    x += 0;
//                    y += ( deltaY > 0 ? 1 : -1 )  * speed;
//                }
//                else {
//                    end = false;
//                }
            }
            else {
                x += speed * Math.cos(direction);
                y += speed * Math.sin(direction);
            }


            end = end && isEnd((int)x, (int)y, coord, destination);

            Coord nextStep = new Coord((int) x, (int) y);
            pointsUntilDestination.offer(nextStep);
        }


    }

    private boolean isOverlapping(Rectangle overlap) {
        return ((overlap.getWidth() >= 0 ) && (overlap.getHeight() >= 0));
    }

    private static boolean isEnd(int x, int y, Coord coord, Coord destination) {
        var endX = coord.getX() < destination.getX() == x < destination.getX();
        var endY = coord.getY() < destination.getY() == y < destination.getY();
        return (endX && endY);
    }

    public static Rectangle getOverlappingRectangleWith(Rectangle current, PlanetSprite that){
        int thisX = (int)current.getX();
        int thisY = (int)current.getY();
        int thisWidth = (int)current.getWidth();
        int thisHeight = (int)current.getHeight();

        int thatX = that.coord.getX();
        int thatY = that.coord.getY();
        int thatWidth = that.size.getWidth();
        int thatHeight = that.size.getHeight();

        Rectangle thisRect = new Rectangle(thisX, thisY, thisWidth, thisHeight);
        Rectangle thatRect = new Rectangle(thatX, thatY, thatWidth, thatHeight);

        return thisRect.intersection(thatRect);
    }

    private Rectangle checkIntersection(Rectangle current){
        var PlanetSprites =  PlayGround.PLANETS.values();
        for(PlanetSprite planetSprite : PlanetSprites){
            if ( ( planetSprite instanceof MeteorSprite ) || ( planetSprite instanceof BlackHoleSprite) ){
                Rectangle overlap =  getOverlappingRectangleWith(current,planetSprite);
                Boolean isPositiveOverlap = (overlap.getWidth() >= 0) && (overlap.getHeight() >= 0);

                if (isPositiveOverlap) {
                    return overlap;
                }
            }


        }
        return new Rectangle(0,0,-1,-1);
    }
    public void setDestination(Coord destination) {
        this.destination = destination;
    }

    public abstract void remove();

    public boolean canRemoveOnArrive() {
        return false;
    }

}