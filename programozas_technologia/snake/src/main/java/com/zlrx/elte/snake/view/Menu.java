package com.zlrx.elte.snake.view;

import com.zlrx.elte.snake.behaviour.AbstractMouseListener;
import com.zlrx.elte.snake.util.ImageContainer;
import com.zlrx.elte.snake.util.Images;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

public class Menu extends JPanel {


    public Menu(Runnable start, Runnable score) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;

        var label = new JLabel(new ImageIcon(ImageContainer.getInstance().image(Images.COBRA)));
        label.setPreferredSize(new Dimension(250, 250));
        c.gridy = 0;
        add(label, c);

        var startBtn = createButton("Start Game", start);
        var scoreBtn = createButton("High Scores", score);

        c.gridy = 1;
        c.insets = new Insets(200, 0, 0, 0);
        add(startBtn, c);

        c.gridy = 2;
        c.insets = new Insets(30, 0, 0, 0);
        add(scoreBtn, c);
        setVisible(true);
    }

    private JButton createButton(String label, Runnable action) {
        var font = new Font("TimesRoman", Font.PLAIN, 30);
        var size = new Dimension(300, 100);
        var btn = new JButton(label);
        btn.setPreferredSize(size);
        btn.setFont(font);
        btn.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var graphics = (Graphics2D) g;
        graphics.drawImage(ImageContainer.getInstance().image(Images.MENU_BG), 0, 0, null);

    }
}
