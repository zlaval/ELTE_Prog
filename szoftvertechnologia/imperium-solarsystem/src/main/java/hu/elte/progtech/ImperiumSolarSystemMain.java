package hu.elte.progtech;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.behaviour.GameManager;
import hu.elte.progtech.consts.Const;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.screen.Menu;
import hu.elte.progtech.screen.SolarSystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ImperiumSolarSystemMain extends JFrame {

    private Menu menu;
    private SolarSystem solarSystem;

    public static void main(String[] args) {
        var monitor = System.getenv("MONITOR");
        var frame = new ImperiumSolarSystemMain();
        if (monitor != null) {
            var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            var devices = graphicsEnvironment.getScreenDevices();
            var selectedDevice = devices[Integer.parseInt(monitor)];
            frame.setLocation(selectedDevice.getDefaultConfiguration().getBounds().x, frame.getY());
        }
    }

    public ImperiumSolarSystemMain() {
        setTitle("Imperium Solar System");
        setSize(Const.WIDTH, Const.HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addExitListener();
        setVisible(true);
        setResizable(false);
        startMenu(null);
    }


    private void startMenu(Player winner) {
        cleanUp();
        menu = new Menu(this::startGame, winner);
        this.add(menu);
        validate();
    }

    private void cleanUp() {
        removePanel(menu);
        removePanel(solarSystem);
        menu = null;
        validate();
    }

    private void startGame() {
        cleanUp();
        var drawManager = new DrawManager();
        var bluePlayer = generatePlayerWithName("Blue", Color.BLUE);
        var redPlayer = generatePlayerWithName("Red", Color.RED);
        var gameManager = new GameManager(drawManager, bluePlayer, redPlayer);

        solarSystem = new SolarSystem(gameManager, this::startMenu);
        add(solarSystem);
        solarSystem.setFocusable(true);
        solarSystem.requestFocus();
        validate();
    }

    private Player generatePlayerWithName(String name, Color color) {
        return new Player(name, color);
    }

    private void removePanel(JPanel jPanel) {
        if (jPanel != null) {
            remove(jPanel);
            jPanel.removeAll();
            jPanel.resetKeyboardActions();
            jPanel.revalidate();
            revalidate();
        }
    }

    private void addExitListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
