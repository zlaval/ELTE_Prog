package hu.elte.progtech.draw;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;
import lombok.Getter;

public class MissileSprite extends Movable {

    @Getter
    private Player player;

    @Getter
    private Integer damage;

    private DrawManager drawManager;

    public MissileSprite(Player player, Coord start, Coord destination, DrawManager drawManager, int damage) {
        super(ImageContainer.getInstance().image(Images.LASER), start, Images.LASER.getSize());
        this.setDestination(destination);
        this.player = player;
        this.drawManager = drawManager;
        this.damage = damage;
    }

    @Override
    public double getSpeed() {
        return 7;
    }

    @Override
    public void remove() {
        doBeforeRemove();
        removable = true;
    }

    @Override
    protected void doBeforeRemove() {
        var explosion = new ExplosionSprite(coord);
        drawManager.registerMovable(explosion);
    }

    @Override
    public boolean canRemoveOnArrive() {
        return true;
    }
}
