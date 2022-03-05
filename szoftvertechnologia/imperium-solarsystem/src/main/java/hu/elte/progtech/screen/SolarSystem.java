package hu.elte.progtech.screen;

import hu.elte.progtech.behaviour.DrawManager;
import hu.elte.progtech.behaviour.GameManager;
import hu.elte.progtech.consts.Const;
import hu.elte.progtech.handler.MouseListener;
import hu.elte.progtech.model.planet.Planets;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.screen.view.Console;
import hu.elte.progtech.screen.view.PlanetMenu;
import hu.elte.progtech.screen.view.PlayGround;
import hu.elte.progtech.screen.view.SideMenu;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolarSystem extends JPanel implements ActionListener {

    private final GameManager gameManager;
    private final PlayGround playGround;
    private final SideMenu bluePlayerMenu;
    private final SideMenu redPlayerMenu;
    private final MouseListener mouseListener;
    private final PlanetMenu planetMenu;
    private Timer timer;

    private final GameEndAction endGameAction;

    public SolarSystem(GameManager gameManager, GameEndAction endGameAction) {
        setLayout(new GridLayout(2, 1));
        this.endGameAction = endGameAction;
        this.gameManager = gameManager;
        this.playGround = new PlayGround();
        this.planetMenu = new PlanetMenu(gameManager);
        gameManager.setPlanetMenu(planetMenu);
        this.bluePlayerMenu = new SideMenu(gameManager, gameManager.getBluePlayer());
        this.redPlayerMenu = new SideMenu(gameManager, gameManager.getRedPlayer());
        this.mouseListener = new MouseListener(gameManager);
        gameManager.registerPlanets(playGround.getPlanetSprites());
        gameManager.setupPlayers(
                playGround.getPlanet(Planets.CAPRICA),
                playGround.getPlanet(Planets.KRYPTON)
        );

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        registerNextRoundBtn();
        addInPanel(planetMenu);
        startTimer();
    }

    private void registerNextRoundBtn() {
        var btn = new JButton("Finish round");
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> gameManager.switchPlayer());
        btn.setBounds(100, 100, 100, 100);
        addInPanel(btn);
    }

    private void addInPanel(JComponent component) {
        var panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);
        add(panel);
        panel.add(component);
    }

    public void startTimer() {
        timer = new Timer(25, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            this.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        var graphics = (Graphics2D) g;
        super.paintComponent(g);
        if (!checkGameEnd()) {
            DrawManager.setGraphics(graphics);

            playGround.drawPlayGround();
            bluePlayerMenu.draw(0);
            redPlayerMenu.draw(Const.WIDTH - Const.SIDE_MENU_WIDTH - 10);
            drawSelect();
            gameManager.tick();
            Console.getConsole().draw(graphics);
        }
    }

    private boolean checkGameEnd() {
        boolean end = false;
        Player winner = null;

        if (gameManager.getBluePlayer().getPlanets().size() == Const.PLANET_COUNT) {
            end = true;
            winner = gameManager.getBluePlayer();
        } else if (gameManager.getRedPlayer().getPlanets().size() == Const.PLANET_COUNT) {
            end = true;
            winner = gameManager.getRedPlayer();
        }

        if (end) {
            endGameAction.run(winner);
        }

        return end;
    }

    private void drawSelect() {
        var start = mouseListener.getStartPoint();
        var end = mouseListener.getEndPoint();
        if (start != null && end != null) {
            var graphics = DrawManager.getGraphics();
            int px = Math.min(start.getX(), end.getX());
            int py = Math.min(start.getY(), end.getY());
            int width = Math.abs(start.getX() - end.getX());
            int height = Math.abs(start.getY() - end.getY());
            graphics.setColor(Color.WHITE);
            graphics.drawRect(px, py, width, height);
        }
    }

}
