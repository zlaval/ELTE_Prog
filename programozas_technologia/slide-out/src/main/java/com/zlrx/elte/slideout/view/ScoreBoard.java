package com.zlrx.elte.slideout.view;

import com.zlrx.elte.slideout.model.GameData;
import com.zlrx.elte.slideout.model.Player;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

//TODO refactor
public class ScoreBoard extends JPanel {

    public static final String DIALOG = "Dialog";
    private final JLabel remainingRoundLbl;
    private final JLabel actualPlayerLbL;
    private final JLabel blackRockLbl;
    private final JLabel whiteRockLbl;
    private final JLabel chooseTxt;

    public ScoreBoard() {
        setLayout(new GridLayout(2, 5, 20, 15));
        chooseTxt = createLabel("Please select a rock!", 20, Color.BLUE);
        remainingRoundLbl = createLabel();
        actualPlayerLbL = createLabel();
        blackRockLbl = createLabel();
        whiteRockLbl = createLabel();
        buildBoard();
    }

    private JLabel createLabel() {
        return createLabel("", 16, Color.WHITE);
    }

    private JLabel createLabel(String title, int fontSize, Color color) {
        var label = new JLabel(title);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font(DIALOG, Font.BOLD, fontSize));
        label.setForeground(color);
        return label;
    }

    private void addCenteredLabel(String title) {
        var label = new JLabel(title);
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
    }

    private void buildBoard() {
        addCenteredLabel("Remaining rounds");
        addCenteredLabel("Player");
        add(chooseTxt);
        addCenteredLabel(Player.WHITE.title() + " rocks");
        addCenteredLabel(Player.BLACK.title() + " rocks");

        addInPanel(remainingRoundLbl);
        addInPanel(actualPlayerLbL);
        add(new JLabel(""));
        addInPanel(whiteRockLbl);
        addInPanel(blackRockLbl);
    }

    private void addInPanel(JLabel label) {
        var panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.BLUE);
        panel.add(label);
        add(panel);
    }

    public void removeChooseLbl() {
        chooseTxt.setVisible(false);
    }

    public void update(GameData data) {
        chooseTxt.setVisible(true);
        remainingRoundLbl.setText(data.getRemainingStep().toString());
        actualPlayerLbL.setText(data.getPlayer().title());
        blackRockLbl.setText(data.getBlackRock().toString());
        whiteRockLbl.setText(data.getWhiteRock().toString());
    }
}
