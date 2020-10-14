import hu.elte.ShapeType;
import hu.elte.factory.AbstractShapeFactory;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class GeometryMain {

    private AbstractShapeFactory abstractFactory = AbstractShapeFactory.getINSTANCE();

    public static void main(String[] args) {
        var geometryMain = new GeometryMain();
        geometryMain.run();
    }

    public void run() {
        var path = Paths.get("input.txt");
        var file = path.toFile();

        try {
            Scanner scanner = null;
            do {
                if (scanner == null) {
                    scanner = new Scanner(file);
                } else {
                    scanner.nextLine();
                }
                var typeStr = scanner.next();
                try {
                    var type = ShapeType.valueOf(typeStr);
                    var factory = abstractFactory.getShapeFactory(type);
                    var x = scanner.nextInt();
                    var y = scanner.nextInt();
                    var r = scanner.hasNextInt() ? scanner.nextInt() : 0;
                    System.out.println(factory.getShape(x, y, r));
                } catch (IllegalArgumentException e) {
                    System.out.println(typeStr);
                }
            }
            while (scanner.hasNextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}