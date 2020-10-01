import hu.elte.geometry.Circle;
import hu.elte.geometry.Point;
import hu.elte.image.Canvas;

import java.awt.Color;
import java.io.IOException;

public class Circles {

    public static void main(String[] args) throws IOException {
        if (args.length % 3 != 0) {
            System.out.println("Arg count not ok");
            return;
        }
        Circle[] circles = createCircles(args);
        try (Canvas canvas = new Canvas(300, 300)) {
            for (var circle : circles) {
                canvas.draw(circle, Color.MAGENTA);
            }
            canvas.saveToPng("korok.png");
        }
    }

    private static Circle[] createCircles(String[] args) {
        var numberOfCircles = args.length / 3;
        var circles = new Circle[numberOfCircles];
        int k = 0;
        for (int i = 0; i < args.length; i += 3) {
            var x = Integer.parseInt(args[i]);
            var y = Integer.parseInt(args[i + 1]);
            var r = Integer.parseInt(args[i + 2]);
            var circle = new Circle(new Point(x, y), r);
            circles[k++] = circle;
        }
        return circles;
    }

}