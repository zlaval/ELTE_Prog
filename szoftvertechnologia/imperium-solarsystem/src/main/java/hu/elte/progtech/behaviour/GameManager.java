package hu.elte.progtech.behaviour;

import hu.elte.progtech.consts.MineType;
import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.draw.ship.ShipFactory;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.model.ship.ShipUnderConstruct;
import hu.elte.progtech.screen.view.Console;
import hu.elte.progtech.screen.view.PlanetMenu;
import hu.elte.progtech.utils.Coord;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GameManager {

    @Getter
    private final Player bluePlayer;

    @Getter
    private final Player redPlayer;

    @Getter
    private Player actualPlayer;

    @Setter
    private PlanetMenu planetMenu;

    @Getter
    private final DrawManager drawManager;

    public GameManager(DrawManager drawManager, Player bluePlayer, Player redPlayer) {
        this.redPlayer = redPlayer;
        this.bluePlayer = bluePlayer;
        this.actualPlayer = bluePlayer;
        bluePlayer.setActive(true);
        this.drawManager = drawManager;
    }

    public void setupPlayers(Planet bluePlanet, Planet redPlanet) {
        var blueMothership = ShipFactory.getShip(ShipType.MOTHER_SHIP, Coord.get(380, 100), bluePlayer);
        var redMothership = ShipFactory.getShip(ShipType.MOTHER_SHIP, Coord.get(1240, 720), redPlayer);
        drawManager.registerMovable(blueMothership);
        drawManager.registerMovable(redMothership);
        int bonus = MineType.IRONMINE.getUpdatePriceLevels().get(0) + MineType.SONARPANEL.getUpdatePriceLevels().get(0);

        bluePlanet.conquer(bluePlayer);
        bluePlanet.addResource(new Resource(bonus, ResourceType.IRON));
        bluePlanet.addResource(new Resource(0, ResourceType.ENERGY));
        bluePlanet.buildSolarPanel();
        bluePlanet.buildIronMine();

        redPlanet.conquer(redPlayer);
        redPlanet.addResource(new Resource(bonus, ResourceType.IRON));
        redPlanet.addResource(new Resource(0, ResourceType.ENERGY));
        redPlanet.buildSolarPanel();
        redPlanet.buildIronMine();
    }

    public void switchPlayer() {
            if (actualPlayer == redPlayer) {
                actualPlayer = bluePlayer;
                bluePlayer.setActive(true);
                redPlayer.setActive(false);
                nextRound();
            } else {
                actualPlayer = redPlayer;
                redPlayer.setActive(true);
                bluePlayer.setActive(false);
            }
            Console.getConsole().addMessage(actualPlayer.getName() + "'s turn started.");
            nextPlayer();
    }


    private void nextPlayer() {
        drawManager.deselectAll();
    }

    private void nextRound() {
        bluePlayer.getPlanets().forEach(Planet::produce);
        redPlayer.getPlanets().forEach(Planet::produce);

        bluePlayer.nextRound();
        redPlayer.nextRound();

        emitShips(bluePlayer);
        emitShips(redPlayer);

    }

    private void emitShips(Player player) {


        player.getPlanets().forEach(p -> {
            List<ShipUnderConstruct> shipConstructed = new ArrayList<>();
            p.getShipUnderConstruct().forEach(s -> {
                        if (s.construct()) {
                            var ship = ShipFactory.getShip(s.getShipType(), p.getCoord(), player);
                            drawManager.registerMovable(ship);
                            shipConstructed.add(s);
                        }
                    }
            );
            shipConstructed.forEach(s -> p.getShipUnderConstruct().remove(s));
        });
    }


    public void registerPlanets(Collection<PlanetSprite> planets) {
        drawManager.registerDrawable(planets);
    }

    public void select(Coord start, Coord end) {
        drawManager.selectElement(start, end, actualPlayer);
        drawManager.getSelectedPlanet().ifPresent(p -> planetMenu.open(p));
    }

    public void handleLeftMouseClick(Coord coord) {
        drawManager.handleClickEvent(coord, actualPlayer);
    }


    public void tick() {
        drawManager.redraw();
    }

    public Optional<Planet> getSelectedPlanet() {
        return drawManager.getSelectedPlanet();
    }
}
