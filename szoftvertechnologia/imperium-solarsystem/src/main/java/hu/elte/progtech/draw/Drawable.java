package hu.elte.progtech.draw;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.consts.Const;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.screen.view.PlayGround;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import lombok.Getter;
import lombok.Setter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Optional;

public abstract class Drawable{

    protected Image image;

    @Getter
    protected Coord coord;

    @Getter
    protected Size size;

    @Getter
    @Setter
    protected boolean selected = false;

    public Drawable(Image image, Coord coord, Size size) {
        this.image = image;
        this.coord = coord;
        this.size = size;
    }

    public void render() {
        var graphics = DrawManager.getGraphics();
        doBeforeRender();
        if (Const.ENABLE_GIZMOS) {
            graphics.setColor(Color.white);
            graphics.draw(new Rectangle(coord.getX(), coord.getY(), size.getWidth(), size.getHeight()));
        }
        select();
        graphics.drawImage(image, coord.getX(), coord.getY(), null);
    }

    protected void doBeforeRender() {

    }

    protected void select() {
        var graphics = DrawManager.getGraphics();
        if (selected) {
            var stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            graphics.setColor(Color.yellow);
            var origStroke = graphics.getStroke();
            graphics.setStroke(stroke);
            graphics.drawOval(coord.getX() - 5, coord.getY() - 5, size.getWidth() + 10, size.getHeight() + 10);
            graphics.setStroke(origStroke);
        }
    }



}
