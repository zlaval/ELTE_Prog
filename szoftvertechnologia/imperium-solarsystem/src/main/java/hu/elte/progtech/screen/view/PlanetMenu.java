package hu.elte.progtech.screen.view;

import hu.elte.progtech.behaviour.GameManager;
import hu.elte.progtech.consts.MineType;
import hu.elte.progtech.consts.ShipType;
import hu.elte.progtech.draw.AbstractMouseListener;
import hu.elte.progtech.draw.Drawable;
import hu.elte.progtech.draw.Movable;
import hu.elte.progtech.draw.planet.PlanetSprite;
import hu.elte.progtech.draw.ship.ColonizerShipSprite;
import hu.elte.progtech.draw.ship.SpaceShipSprite;
import hu.elte.progtech.model.building.Building;
import hu.elte.progtech.model.building.StarGate;
import hu.elte.progtech.model.building.mine.DeuteriumMine;
import hu.elte.progtech.model.building.mine.IronMine;
import hu.elte.progtech.model.building.mine.SonarPanel;
import hu.elte.progtech.model.planet.Planet;
import hu.elte.progtech.model.resources.Resource;
import hu.elte.progtech.model.resources.ResourceType;
import hu.elte.progtech.model.ship.ColonizerShip;
import hu.elte.progtech.model.ship.ShuttleShip;
import hu.elte.progtech.model.ship.SpaceShip;
import hu.elte.progtech.utils.Coord;
import hu.elte.progtech.utils.Size;
import hu.elte.progtech.utils.Utility;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static hu.elte.progtech.consts.Const.SHUTTLE_DISTANCE_TO_PICK;
import static hu.elte.progtech.consts.Const.STARGATE_PRICE;

public class PlanetMenu extends JPanel {

    private final GameManager gameManager;

    private JLabel label;

    private Planet planet;

    private GridBagConstraints c;

    public PlanetMenu(GameManager gameManager) {
        this.gameManager = gameManager;
        var border = BorderFactory.createLineBorder(Color.black);
        setBorder(border);
        setVisible(false);
    }

    private void registerMainMenu() {
        rebuildPanel(() -> {
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            label = new JLabel(planet.getName());
            label.setFont(font);
            add(label, c);

            createAndAddButton("Colonize", () -> {
                planet.conquerWithShip(gameManager.getActualPlayer(), (ColonizerShipSprite) getColonizerShipSprite().getSprite());
            }, (planet.getPlayer() == null) && colonizerShipNearby(), true);

            createAndAddButton("Build", this::registerBuildMenu, planet.getPlayer() == gameManager.getActualPlayer(), planet.hasPlaceToBuild(), "", false);
            createAndAddButton("Upgrade", this::registerUpgradeMenu, planet.getPlayer() == gameManager.getActualPlayer(), false, canUpgrade());

            createAndAddButton("Conquer", () -> {
                        planet.conquerWithShip(gameManager.getActualPlayer(), null);
                        registerMainMenu();
                    }, planet.getPlayer() != null && planet.getPlayer() != gameManager.getActualPlayer(),
                    !isDefensePresented(),
                    "You must defeat all enemies nearby the planet before conquer it!",
                    true
            );

            createAndAddButton("Produce Ship", this::registerShipFactoryMenu, planet.getPlayer() == gameManager.getActualPlayer() && planet.hasStarGate());
            createAndAddButton("Transport", this::registerTransportMenu, planet.getPlayer() == gameManager.getActualPlayer());
            createAndAddButton("Exit", () -> setVisible(false), true);
        });
    }

    private boolean canUpgrade() {
        if (!gameManager.getActualPlayer().isAbleToDevelop()) return false;
        for (var building : planet.getBuildings()) {
            if (building.getLevel() < 5) {
                return true;
            }
        }
        return false;
    }

    private ColonizerShip getColonizerShipSprite() {
        List<Drawable> drawableList = gameManager.getDrawManager().getDrawableElements();
        PlanetSprite selectedPlanet = gameManager.getDrawManager().getSelectedPlanetSprite().orElse(null);
        if (selectedPlanet != null) {
            for (var drawable : drawableList) {
                if (drawable instanceof ColonizerShipSprite) {
                    ColonizerShip colonizerShip = (ColonizerShip) ((SpaceShipSprite) drawable).getSpaceShip();
                    return colonizerShip;
                }
            }
        }
        return null;
    }

    private boolean colonizerShipNearby() {
        SpaceShip currentShip = getColonizerShipSprite();
        PlanetSprite selectedPlanet = gameManager.getDrawManager().getSelectedPlanetSprite().orElse(null);
        if (currentShip != null && selectedPlanet != null) {
            SpaceShipSprite current = currentShip.getSprite();

            Coord coord = current.getCoord();
            Size size = current.getSize();
            Rectangle shipRect = new Rectangle(coord.getX(), coord.getY(), size.getWidth(), size.getHeight());

            Rectangle overlap = Movable.getOverlappingRectangleWith(shipRect, selectedPlanet);
            Boolean isPositiveOverlap = (overlap.getWidth() >= 0) && (overlap.getHeight() >= 0);

            return isPositiveOverlap;
        }

        return false;
    }

    private boolean isDefensePresented() {
        if (planet.getPlayer() != null) {
            var planetPlace = planet.getCoord();
            return Utility.isOwnShipNearby(planetPlace, planet.getPlayer().getShips());
        }
        return false;
    }

    private void registerUpgradeMenu() {
        rebuildPanel(() -> {
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            label = new JLabel(planet.getName());
            label.setFont(font);
            add(label, c);

            for (var building : planet.getBuildings()) {
                if (building.getLevel() < 5 && !(building instanceof StarGate)) {
                    createAndAddButton(
                            building.getName() + " (" + (building.getLevel() + 1) + ")",
                            building::levelUp,
                            true,
                            hasEnoughResources(building.getLevel() + 1, getType(building)),
                            getBuildingToolTip(getType(building), building.getLevel() + 1),
                            true);
                }
            }
            createAndAddButton("Back", this::registerMainMenu, true);
        });
    }

    private MineType getType(Building building) {
        if (building instanceof IronMine) {
            return MineType.IRONMINE;
        } else if (building instanceof SonarPanel) {
            return MineType.SONARPANEL;
        } else if (building instanceof DeuteriumMine) {
            return MineType.DEUTERIUMMINE;
        }
        throw new IllegalArgumentException("Not a building");

    }

    private void registerTransportMenu() {
        rebuildPanel(() -> {
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            label = new JLabel("Transport");
            label.setFont(font);
            add(label, c);
            createAndAddButton("Import", this::importResources, true, !findShuttlesNextToPlanet().isEmpty(), "Unload all Shuttle next to the planet", true);
            createAndAddButton("Export", this::registerExportMenu, true, !findShuttlesNextToPlanet().isEmpty(), "Load resources from planet into shuttle", false);
            createAndAddButton("Back", this::registerMainMenu, true);
        });
    }

    private void registerExportMenu() {
        rebuildPanel(() -> {
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            label = new JLabel("Export");
            label.setFont(font);
            add(label, c);

            SpinnerModel ironModel = new SpinnerNumberModel(
                    0,
                    0,
                    planet.getResources().get(ResourceType.IRON).intValue(),
                    1
            );
            SpinnerModel energyModel = new SpinnerNumberModel(
                    0,
                    0,
                    planet.getResources().get(ResourceType.ENERGY).intValue(),
                    1
            );

            var labelFont = new Font("TimesRoman", Font.PLAIN, 15);
            var size = new Dimension(300, 50);
            c.gridy++;

            var ironLabel = new JLabel("Iron");
            ironLabel.setPreferredSize(size);
            ironLabel.setFont(labelFont);
            add(ironLabel, c);
            c.gridy++;
            var ironSpinner = new JSpinner(ironModel);
            ironLabel.setLabelFor(ironSpinner);
            ironSpinner.setPreferredSize(size);
            add(ironSpinner, c);


            c.gridy++;
            var energyLabel = new JLabel("Energy");
            energyLabel.setPreferredSize(size);
            energyLabel.setFont(labelFont);
            add(energyLabel, c);
            c.gridy++;
            var energySpinner = new JSpinner(energyModel);
            energyLabel.setLabelFor(energySpinner);
            energySpinner.setPreferredSize(size);
            add(energySpinner, c);


            createAndAddButton("OK", () -> putIntoShuttle(
                            Integer.valueOf(ironSpinner.getValue().toString()),
                            Integer.valueOf(energySpinner.getValue().toString())
                    )
                    , true, true);
            createAndAddButton("Back", this::registerMainMenu, true);
        });
    }

    private void putIntoShuttle(int iron, int energy) {
        var shuttles = findShuttlesNextToPlanet();
        if (!shuttles.isEmpty()) {
            var resources = new ArrayList<Resource>();
            resources.add(new Resource(iron, ResourceType.IRON));
            resources.add(new Resource(energy, ResourceType.ENERGY));
            var shuttle = (ShuttleShip) shuttles.get(0);
            shuttle.load(resources, planet);
        }
    }

    private List<SpaceShip> findShuttlesNextToPlanet() {
        return Utility.getShipNearby(planet, ShipType.SHUTTLE, SHUTTLE_DISTANCE_TO_PICK);
    }

    private void importResources() {
        var ships = Utility.getShipNearby(planet, ShipType.SHUTTLE, SHUTTLE_DISTANCE_TO_PICK);
        for (var ship : ships) {
            if (ship instanceof ShuttleShip) {
                ShuttleShip shuttle = (ShuttleShip) ship;
                shuttle.unLoad(planet);
            }
        }
    }

    private void exportResources() {

    }

    private void registerShipFactoryMenu() {
        rebuildPanel(() -> {
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            label = new JLabel("Produce Ship");
            label.setFont(font);
            add(label, c);

            createAndAddButton("BattleShip", () -> {
                        planet.buildShip(ShipType.BATTLESHIP);
                        registerMainMenu();
                    }, true,
                    hasResourceToShip(ShipType.BATTLESHIP),
                    "Price is " + ShipType.BATTLESHIP.getPrice() + " iron",
                    true
            );

            createAndAddButton("Colonizer Ship", () -> {
                        planet.buildShip(ShipType.COLONIZER);
                        registerMainMenu();
                    }, true,
                    hasResourceToShip(ShipType.COLONIZER),
                    "Price is " + ShipType.COLONIZER.getPrice() + " iron",
                    true);

            createAndAddButton("Cruiser", () -> {
                        planet.buildShip(ShipType.CRUISER);
                        registerMainMenu();
                    }, true,
                    hasResourceToShip(ShipType.CRUISER),
                    "Price is " + ShipType.CRUISER.getPrice() + " iron",
                    true);

            createAndAddButton("Fighter", () -> {
                        planet.buildShip(ShipType.FIGHTER);
                        registerMainMenu();
                    }, true,
                    hasResourceToShip(ShipType.FIGHTER),
                    "Price is " + ShipType.FIGHTER.getPrice() + " iron",
                    true);

            createAndAddButton("MotherShip", () -> {
                        planet.buildShip(ShipType.MOTHER_SHIP);
                        registerMainMenu();
                    }, true,
                    hasResourceToShip(ShipType.MOTHER_SHIP),
                    "Price is " + ShipType.MOTHER_SHIP.getPrice() + " iron",
                    true);

            createAndAddButton("Shuttle", () -> {
                        planet.buildShip(ShipType.SHUTTLE);
                        registerMainMenu();
                    }, true,
                    hasResourceToShip(ShipType.SHUTTLE),
                    "Price is " + ShipType.SHUTTLE.getPrice() + " iron",
                    true);

            createAndAddButton("Back", this::registerMainMenu, true);
        });
    }

    private boolean hasResourceToShip(ShipType shipType) {
        return shipType.getPrice() <= planet.getResources().get(ResourceType.IRON);
    }

    private void registerBuildMenu() {
        rebuildPanel(() -> {
            c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 1;
            c.gridy = 0;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            label = new JLabel("Build");
            label.setFont(font);
            add(label, c);

            createAndAddButton("Solar panel", planet::buildSolarPanel, true, hasEnoughResources(0, MineType.SONARPANEL), getBuildingToolTip(MineType.SONARPANEL, 0), true);
            createAndAddButton("Iron mine", planet::buildIronMine, true, hasEnoughResources(0, MineType.IRONMINE), getBuildingToolTip(MineType.IRONMINE, 0), true);
            createAndAddButton("Deuterium mine", planet::buildDeuteriumMine, true, hasEnoughResources(0, MineType.DEUTERIUMMINE), getBuildingToolTip(MineType.DEUTERIUMMINE, 0), true);
            createAndAddButton("Stargate", planet::buildStarGate, true, planet.getResources().get(ResourceType.IRON) >= STARGATE_PRICE, getBuildingToolTip(null, 0), true);
            createAndAddButton("Back", this::registerMainMenu, true, false);
        });
    }

    private String getBuildingToolTip(MineType building, int level) {
        String price = "Price is ";
        if (building == null) {
            price += STARGATE_PRICE;
        } else {
            price += building.getUpdatePriceLevels().get(level);
        }

        return price + " iron";
    }

    private boolean hasEnoughResources(int level, MineType mineType) {
        return planet.getResources().get(ResourceType.IRON) >= mineType.getUpdatePriceLevels().get(level);
    }

    private void rebuildPanel(Runnable action) {
        removeAll();
        synchronized (getTreeLock()) {
            setLayout(new GridBagLayout());
            action.run();
            revalidate();
            repaint();
        }
    }

    public void open(Planet planet) {
        this.planet = planet;
        registerMainMenu();
        setVisible(true);
    }

    private void createAndAddButton(String label, Runnable action, boolean visible) {
        createAndAddButton(label, action, visible, true, "", false);
    }

    private void createAndAddButton(String label, Runnable action, boolean visible, String tooltip) {
        createAndAddButton(label, action, visible, true, tooltip, false);
    }

    private void createAndAddButton(String label, Runnable action, boolean visible, boolean exit) {
        createAndAddButton(label, action, visible, true, "", exit);
    }

    private void createAndAddButton(String label, Runnable action, boolean visible, boolean exit, boolean enabled) {
        createAndAddButton(label, action, visible, enabled, "", exit);
    }

    private void createAndAddButton(String label, Runnable action, boolean visible, boolean enabled, String tooltip, boolean exit) {
        if (visible) {
            c.gridy++;
            var font = new Font("TimesRoman", Font.PLAIN, 20);
            var size = new Dimension(300, 50);
            var btn = new JButton(label);
            btn.setPreferredSize(size);
            btn.setFont(font);
            btn.setEnabled(enabled);
            if (tooltip != null) {
                btn.setToolTipText(tooltip);
            }
            btn.addMouseListener(new AbstractMouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (action != null && enabled) {
                        action.run();
                        if (exit) {
                            setVisible(false);
                        }
                    }
                }
            });
            add(btn, c);

        }
    }

}
