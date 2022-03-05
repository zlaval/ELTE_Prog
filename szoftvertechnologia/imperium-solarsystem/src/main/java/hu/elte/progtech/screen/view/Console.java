package hu.elte.progtech.screen.view;

import lombok.Getter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static hu.elte.progtech.consts.Const.HEIGHT;

public class Console {

    private Console() {
    }

    @Getter
    private static final Console console = new Console();

    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        synchronized (this) {
            messages.add(message);
            if (messages.size() > 13) {
                messages.remove(0);
            }
        }
    }

    public void draw(Graphics2D graphics) {
        var y = HEIGHT - 235;
        var rectangle = new Rectangle(205, HEIGHT - 250, 300, 250);
        graphics.setColor(Color.BLACK);
        graphics.fill(rectangle);
        graphics.draw(rectangle);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        graphics.setColor(Color.WHITE);
        synchronized (this) {
            for (var m : messages) {
                graphics.drawString(m, 210, y);
                y += 15;
            }
        }
    }

}
