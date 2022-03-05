package hu.elte.progtech.screen;

import hu.elte.progtech.model.player.Player;

@FunctionalInterface
public interface GameEndAction {

    void run(Player player);

}
