package hu.elte.progtech.screen.view;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.behaviour.GameManager;
import hu.elte.progtech.model.building.StarGate;
import hu.elte.progtech.model.building.mine.SonarPanel;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.model.ship.SpaceShip;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hu.elte.progtech.consts.Const.HEIGHT;
import static hu.elte.progtech.consts.Const.SIDE_MENU_WIDTH;

public class SideMenu {

    private Player player;
    private GameManager gameManager;

    public SideMenu(GameManager gameManager, Player player) {
        this.player = player;
        this.gameManager = gameManager;
    }


    public void draw(int x) {
        drawWholeSideMenu(x);
    }

    public void drawWholeSideMenu(int x) {
        var graphics = DrawManager.getGraphics();
        var rectangle = new Rectangle(x, 0, SIDE_MENU_WIDTH, HEIGHT);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(rectangle);
        graphics.draw(rectangle);

        graphics.setColor(player.getColor());

        drawPlayerBox(x, graphics);
        drawStaticStatsForSpaceShips(x, graphics);
        drawPlanetsStats(x, graphics);
        drawSelectedPlanetStats(x, graphics);
    }


    public void drawPlayerBox(int x, Graphics2D graphics) {
        var boundedBoxPlayer = new Rectangle(x, 0, SIDE_MENU_WIDTH, 180);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(boundedBoxPlayer);
        graphics.draw(boundedBoxPlayer);

        drawActivePlayerIndicatorBox(x, graphics);
        drawActionPointRemaining(x, graphics);
    }

    private void drawActionPointRemaining(int x, Graphics2D graphics) {
        graphics.setColor(player.getColor());
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.drawString(player.getName(), x + 50, 50);
        graphics.drawString("Action points:", x + 10, 110);
        graphics.drawString("" + player.getActionPoints(), x + 60, 150);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        graphics.setColor(Color.BLACK);
        graphics.drawString("Deuterium: " + player.getDeuterium().getQuantity(), x + 20, 190);
    }

    private void drawActivePlayerIndicatorBox(int x, Graphics2D graphics) {
        if (player.isActive()) {
            var boundedBox = new Rectangle(x + 30, 20, 110, 45);
            graphics.setColor(Color.GRAY);
            graphics.fill(boundedBox);
            graphics.setColor(player.getColor());
            graphics.draw(boundedBox);
        }
    }

    public Map<String, Long> spaceShipList2Map(List<SpaceShip> list) {
        return list.stream().collect(Collectors.groupingBy(SpaceShip::getName, Collectors.counting()));
    }

    public void drawStaticStatsForSpaceShips(int x, Graphics2D graphics) {
        int y = 250;
        var rectangle = new Rectangle(x, y, SIDE_MENU_WIDTH, 500);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(rectangle);
        graphics.draw(rectangle);

        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.setColor(Color.BLACK);
        graphics.drawString("SpaceShips", x + 20, y + 20);

        y = y + 30;
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        Map<String, Long> spaceShipMap = spaceShipList2Map(player.getShips());
        for (Map.Entry<String, Long> spaceShipOwned : spaceShipMap.entrySet()) {
            y = y + 20;
            graphics.drawString(spaceShipOwned.getKey() + " : " + spaceShipOwned.getValue(), x + 50, y);
        }

    }

    public void drawPlanetsStats(int x, Graphics2D graphics) {
        int y = 450;
        var rectangle = new Rectangle(x, y, SIDE_MENU_WIDTH, 500);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(rectangle);
        graphics.draw(rectangle);


        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.setColor(Color.BLACK);
        graphics.drawString("Planets", x + 30, y + 20);

        y = y + 20;
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        List<Planet> ownedPlanets = player.getPlanets();
        for (Planet ownedPlanet : ownedPlanets) {
            y = y + 20;
            graphics.drawString(ownedPlanet.getName(), x + 50, y);
        }

    }

    public void drawSelectedPlanetStats(int x, Graphics2D graphics) {
        int y = 600;
        var rectangle = new Rectangle(x, y, SIDE_MENU_WIDTH, 500);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(rectangle);
        graphics.draw(rectangle);


        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.setColor(Color.BLACK);
        graphics.drawString("Details", x + 40, y + 20);

        y = y + 20;
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        Planet selectedPlanet = gameManager.getSelectedPlanet().orElse(null);
        if (selectedPlanet != null && selectedPlanet.getPlayer() == player) {

            graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            y += 40;
            graphics.drawString("Name:" + selectedPlanet.getName(), x + 30, y);
            y += 40;
            graphics.drawString("Resources:", x + 10, y);
            graphics.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            for (ResourceType resourceType : selectedPlanet.getResources().keySet()) {
                y = y + 20;
                graphics.drawString("\u2023 " + resourceType + " : " + selectedPlanet.getResources().get(resourceType), x + 10, y);
            }
            y += 40;
            graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            graphics.drawString("Buildings:", x + 10, y);
            graphics.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            for (var building : selectedPlanet.getBuildings()) {
                y = y + 20;
                if (building instanceof StarGate) {
                    graphics.drawString("\u2022 " + building.getName(), x + 10, y);
                } else {
                    String other = "";
                    if (building instanceof SonarPanel) {
                        other += "+" + selectedPlanet.getHotBonus();
                    }
                    graphics.drawString("\u2022 " + building.getName() + " (lvl: " + (building.getLevel() + 1) + ") [" + building.getProductionRate() + other + "/r]", x + 10, y);
                }

            }
        }

    }

}
