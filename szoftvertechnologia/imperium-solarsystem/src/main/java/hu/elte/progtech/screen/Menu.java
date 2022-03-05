package hu.elte.progtech.screen;

import hu.elte.progtech.draw.AbstractMouseListener;
import hu.elte.progtech.model.player.Player;
import hu.elte.progtech.utils.ImageContainer;
import hu.elte.progtech.utils.Images;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

public class Menu extends JPanel {

    public Menu(Runnable start, Player winner) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;

        var label = new JLabel(new ImageIcon(ImageContainer.getInstance().image(Images.CRUISER_MENU)));
        label.setPreferredSize(new Dimension(250, 250));
        c.gridy = 0;
        add(label, c);

        int y = 1;
        if (winner != null) {
            var font = new Font("TimesRoman", Font.PLAIN, 30);
            var winnerLabel = new JLabel(winner.getName() + " won");
            winnerLabel.setFont(font);
            winnerLabel.setForeground(Color.BLACK);
            c.gridy = y;
            add(winnerLabel, c);
            y++;
        }

        var startBtn = createButton("Start Game", start);

        c.gridy = y;
        c.insets = new Insets(200, 0, 0, 0);
        add(startBtn, c);

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
