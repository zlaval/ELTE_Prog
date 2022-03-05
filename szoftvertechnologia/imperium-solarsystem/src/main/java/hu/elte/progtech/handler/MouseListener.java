package hu.elte.progtech.handler;

import hu.elte.progtech.behaviour.GameManager;
import hu.elte.progtech.utils.Coord;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    private final GameManager gameManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    private Coord startPoint;

    @Getter
    private Coord endPoint;

    public MouseListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private void logMouseEvent(MouseEvent e, String eventName) {
        logger.info("{} event received. {} button clicked on coordinate x={}, y={}", eventName, e.getButton(), e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        logMouseEvent(e, "mouseClicked");
        if (e.getButton() == MouseEvent.BUTTON1) {
            gameManager.select(Coord.get(e.getX(), e.getY()), null);
        } else if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
            gameManager.handleLeftMouseClick(Coord.get(e.getX(), e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logMouseEvent(e, "mousePressed");
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == 0) {
            startPoint = Coord.get(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        logMouseEvent(e, "mouseDragged");
        if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == 0) {
            endPoint = Coord.get(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        logMouseEvent(e, "mouseReleased");
        if (startPoint != null && endPoint != null) {
            gameManager.select(
                    Coord.get(startPoint.getX(), startPoint.getY()),
                    Coord.get(endPoint.getX(), endPoint.getY())
            );
            startPoint = null;
            endPoint = null;
        } else {
           // mouseClicked(e);
        }
    }
}
