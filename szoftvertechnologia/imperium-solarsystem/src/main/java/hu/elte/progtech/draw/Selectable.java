package hu.elte.progtech.draw;

import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.utils.Coord;

public interface Selectable {

    void onSelect(Coord start, Coord end, Player actualPlayer);

    void onDeselect();

}
