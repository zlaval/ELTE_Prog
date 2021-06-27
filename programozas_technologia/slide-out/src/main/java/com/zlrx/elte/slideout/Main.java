package com.zlrx.elte.slideout;

import com.zlrx.elte.slideout.model.Dim2D;
import com.zlrx.elte.slideout.model.Player;
import com.zlrx.elte.slideout.utils.Utils;
import com.zlrx.elte.slideout.view.GameView;
import com.zlrx.elte.slideout.view.Menu;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

public class Main extends JFrame {

    public static void main(String[] args) {
        new Main();
    }

    private GameView gameView;
    private Dim2D dimension;

    public Main() throws HeadlessException {
        setWindowProperties();
        setJMenuBar(new Menu(this::startGame));
        startGame(new Dim2D(3, 3));
    }

    private void setWindowProperties() {
        setTitle("Slide Out");
        var resolution = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(Utils.floorToInt(resolution.getWidth() * 0.9), Utils.floorToInt(resolution.getHeight() * 0.9));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startGame(Dim2D dimension) {
        if (gameView != null) {
            this.remove(gameView);
        }
        this.dimension = dimension;
        gameView = new GameView(dimension, this::endGame);
        this.add(gameView, BorderLayout.CENTER);
        validate();
    }

    private void endGame(Player winner) {
        JOptionPane.showMessageDialog(this, winner == null ? "Draw" : "The winner is " + winner.title());
        startGame(dimension);
    }

}
