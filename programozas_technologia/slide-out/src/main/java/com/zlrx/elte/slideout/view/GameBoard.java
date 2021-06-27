package com.zlrx.elte.slideout.view;

import com.zlrx.elte.slideout.behaviour.ViewMediator;
import com.zlrx.elte.slideout.model.Dim2D;
import com.zlrx.elte.slideout.model.RockButton;
import com.zlrx.elte.slideout.resource.ImageContainer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class GameBoard extends JPanel {

    private ViewMediator mediator;

    private List<RockButton> buttons = new ArrayList<>();

    public GameBoard(Dim2D grid, ViewMediator mediator) {
        this.mediator = mediator;
        setLayout(new GridLayout(grid.getRow(), grid.getCol()));
        renderBoard();
    }

    public void renderBoard() {
        buttons.forEach(b -> remove(b.getJButton()));
        buttons.clear();
        ImageContainer imageContainer = ImageContainer.getInstance();
        var activePlayer = mediator.getPlayer();
        var board = mediator.getBoard();
        int i = 0;
        int j = 0;

        for (var row : board) {
            for (var tile : row) {
                var button = new JButton();
                button.setBackground(null);
                var rockBtn = new RockButton(button, new Dim2D(i, j));
                if (tile != null) {
                    var image = imageContainer.getImage(tile.getImageKey());
                    button.setIcon(new ImageIcon(image));
                    button.setEnabled(tile.getPlayer() == activePlayer);
                    addRockBtnEvent(rockBtn);
                } else {
                    button.setEnabled(false);
                }
                buttons.add(rockBtn);
                add(button);
                j++;
            }
            j = 0;
            i++;
        }
        validate();
    }

    private void addRockBtnEvent(RockButton rockButton) {
        rockButton.getJButton().addActionListener(e -> {
            buttons.forEach(b -> b.getJButton().setBackground(null));
            rockButton.getJButton().setBackground(mediator.getPlayer().color());
            mediator.selectTile(rockButton.getDimension());
        });
    }
    
}
