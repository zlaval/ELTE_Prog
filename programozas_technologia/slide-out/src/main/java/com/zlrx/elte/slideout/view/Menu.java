package com.zlrx.elte.slideout.view;

import com.zlrx.elte.slideout.model.Dim2D;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class Menu extends JMenuBar {

    private final CheckBoxMenuItemMediator mediator;
    private final JMenu tableSizeParentMenu;
    private final Consumer<Dim2D> command;

    private Dim2D actualDimension;

    public Menu(Consumer<Dim2D> command) {
        this.command = command;
        actualDimension = new Dim2D(3, 3);
        mediator = new CheckBoxMenuItemMediatorImpl();
        registerNewGameMenu();
        tableSizeParentMenu = new JMenu("Table size");
        add(tableSizeParentMenu);
        registerCheckMenuItems();
    }

    private void registerCheckMenuItems() {
        var defaultChx = registerCheckMenuItems(3, 3);
        defaultChx.setSelected(true);
        registerCheckMenuItems(4, 4);
        registerCheckMenuItems(5, 5);
        registerCheckMenuItems(6, 6);
        registerCheckMenuItems(5, 10);
        registerCheckMenuItems(6, 8);
        registerCheckMenuItems(8, 8);
    }

    private JCheckBoxMenuItem registerCheckMenuItems(int row, int col) {
        return registerCheckBoxMenuItem(new Dim2D(row, col));
    }

    private JCheckBoxMenuItem registerCheckBoxMenuItem(Dim2D dimension) {
        var chxMenu = new JCheckBoxMenuItem("  " + dimension.getRow() + "x" + dimension.getCol() + "  ");
        chxMenu.addActionListener(e -> {
            actualDimension = dimension;
            mediator.selectChxBox(dimension);
            command.accept(dimension);
        });
        mediator.register(dimension, chxMenu);
        tableSizeParentMenu.add(chxMenu);
        return chxMenu;
    }

    private void registerNewGameMenu() {
        var start = new JMenu("Start");
        var startNewGame = new JMenuItem("New Game");
        startNewGame.addActionListener(e -> command.accept(actualDimension));
        start.add(startNewGame);
        this.add(start);
    }

    private interface CheckBoxMenuItemMediator {
        void register(Dim2D key, JCheckBoxMenuItem value);

        void selectChxBox(Dim2D key);
    }

    private static class CheckBoxMenuItemMediatorImpl implements CheckBoxMenuItemMediator {

        private Map<Dim2D, JCheckBoxMenuItem> chxBoxes = new HashMap<>();

        @Override
        public void register(Dim2D key, JCheckBoxMenuItem value) {
            chxBoxes.put(key, value);
        }

        @Override
        public void selectChxBox(Dim2D key) {
            chxBoxes.values().forEach(cb -> cb.setSelected(false));
            chxBoxes.get(key).setSelected(true);
        }

    }

}

