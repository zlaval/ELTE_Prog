package com.zlrx.elte.snake.view;

import com.zlrx.elte.snake.behaviour.AbstractMouseListener;
import com.zlrx.elte.snake.model.Score;
import com.zlrx.elte.snake.repository.ScoreRepository;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.List;

public class ScoreBoard extends JPanel {

    private List<Score> scores;

    public ScoreBoard(Runnable action) {
        setLayout(new BorderLayout());
        var repository = new ScoreRepository();
        scores = repository.load();
        JPanel btnPanel = new JPanel();
        var backBtn = createButton("Back to menu", action);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String label, Runnable action) {
        var font = new Font("TimesRoman", Font.PLAIN, 20);
        var size = new Dimension(210, 70);
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
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Arial", Font.PLAIN, 50));
        graphics.drawString("High Scores", 600, 70);

        graphics.setColor(Color.BLUE);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));

        int index = 1;
        int yPosition = 150;
        for (Score score : scores) {
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawString(index + ".", 550, yPosition);
            graphics.setColor(Color.BLUE);
            graphics.drawString(score.getName(), 630, yPosition);
            graphics.setColor(Color.RED);
            graphics.drawString(score.getScore().toString(), 900, yPosition);
            yPosition += 70;
            index++;
        }

    }
}
