package hu.elte.progtech.draw.planet;

import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.draw.Drawable;
import hu.elte.progtech.draw.Selectable;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import hu.elte.progtech.utils.Utility;
import lombok.Getter;

import java.awt.Image;

public abstract class PlanetSprite extends Drawable implements Selectable {

    @Getter
    protected Planet planet;

    public PlanetSprite(Planet planet, Image image, Coord coord, Size size) {
        super(image, coord, size);
        this.planet = planet;
    }

    @Override
    public void onSelect(Coord start, Coord end, Player actualPlayer) {
        if (planet != null && (actualPlayer == planet.getPlayer()
                || (Utility.isShipNearby(start, actualPlayer, ShipType.COLONIZER) && planet.getPlayer() == null)
                || (Utility.isShipNearby(start, actualPlayer, ShipType.MOTHER_SHIP) && planet.getPlayer() != actualPlayer && planet.getPlayer() != null)
        )) {
            if (end == null) {
                setSelected(Utility.isClicked(start, coord, size));
            } else {
                setSelected(false);
            }
        }
    }

    @Override
    public void onDeselect() {
        setSelected(false);
    }
}
