package com.zlrx.elte.snake.view;

import com.zlrx.elte.snake.behaviour.GameManager;
import com.zlrx.elte.snake.model.Bodypart;
import com.zlrx.elte.snake.util.Const;
import com.zlrx.elte.snake.util.ImageContainer;
import com.zlrx.elte.snake.util.Images;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Ground extends JPanel implements KeyListener, ActionListener {

    private final GameManager gameManager;
    private final Rectangle pow;

    public Ground(GameManager gameManager) {
        this.gameManager = gameManager;
        pow = new Rectangle(Const.WIDTH / 2 - 100, Const.HEIGHT / 6, 200, 200);
        addKeyListener(this);
        setVisible(true);
        gameManager.startTimer(this);
    }

    private void drawSnake(Graphics2D graphics) {
        var snake = gameManager.getSnakeBody();
        int i = 0;
        for (Bodypart s : snake) {
            graphics.setColor(chooseColor(i++, snake.size() - 1));
            graphics.fill(s.getRectangle());
            graphics.setColor(Color.white);
            graphics.draw(s.getRectangle());
        }
    }

    private void drawApple(Graphics2D graphics) {
        var appleBody = gameManager.getAppleRect();
        if (Const.ENABLE_GIZMOS) {
            graphics.setColor(Color.white);
            graphics.draw(appleBody);
        }
        drawImage(graphics, appleBody, ImageContainer.getInstance().image(Images.APPLE));
    }

    private void drawRocks(Graphics2D graphics) {
        for (var rock : gameManager.getRockRects()) {
            if (Const.ENABLE_GIZMOS) {
                graphics.setColor(Color.white);
                graphics.draw(rock);
            }
            drawImage(graphics, rock, ImageContainer.getInstance().image(Images.ROCK));
        }
    }

    private void refreshDisplay(Graphics2D graphics) {
        graphics.setColor(Color.yellow);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        graphics.drawString(gameManager.getPoint().toString(), 50, 50);
        graphics.drawString(gameManager.calculateElapsedSeconds() + " sec", Const.WIDTH - 150, 50);
    }

    private void drawSafeArea(Graphics2D graphics) {
        if (Const.ENABLE_GIZMOS) {
            graphics.setColor(Color.cyan);
            graphics.draw(Const.safeArea);
        }
    }

    private void drawPow(Graphics2D graphics) {
        if (gameManager.isDead()) {
            drawImage(graphics, pow, ImageContainer.getInstance().image(Images.POW));
        }
    }

    private void drawImage(Graphics2D graphics, Rectangle rectangle, Image image) {
        graphics.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        var graphics = (Graphics2D) g;
        super.paintComponent(g);
        graphics.drawImage(ImageContainer.getInstance().image(Images.BG), 0, 0, null);
        drawSnake(graphics);
        drawApple(graphics);
        drawRocks(graphics);
        refreshDisplay(graphics);
        drawSafeArea(graphics);
        drawPow(graphics);
    }

    private Color chooseColor(int index, int size) {
        var color = Color.BLUE;
        if (index == 0) {
            color = Color.BLACK;
        } else if (index == size) {
            color = Color.white;
        }
        return color;
    }

    private void showNamePanel() {
        if (gameManager.isDead()) {
            String name = JOptionPane.showInputDialog(this, "Enter your name to save you're score!");
            if (name != null) {
                gameManager.saveScore(name);
            }
            gameManager.goBackToMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // gameManager.keyPressed(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameManager.getTimer()) {
            gameManager.tick();
            this.repaint();
            gameManager.checkGameEnd();
            showNamePanel();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameManager.keyPressed(e);
    }

}
