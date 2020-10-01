package com.zlaval.hw4;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class TriangleCentroidCalculator {

    private static final int TRIANGLE_VERTEX_COUNT = 3;

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private final Scanner scanner;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var triangleCentroidCalculator = new TriangleCentroidCalculator(scanner);
        triangleCentroidCalculator.start();
        scanner.close();
    }

    public TriangleCentroidCalculator(Scanner scanner) {
        this.scanner = scanner;
    }

    void start() {
        var points = IntStream.range(0, TRIANGLE_VERTEX_COUNT).mapToObj(this::generatePoint).toArray(Point[]::new);
        Point centroid = calculateTriangleCenter(points);
        System.out.println("The centroid of the triangle is " + centroid);
    }

    private Point calculateTriangleCenter(Point[] points) {
        double x = 0;
        double y = 0;
        for (Point point : points) {
            x += point.getX();
            y += point.getY();
        }
        x /= TRIANGLE_VERTEX_COUNT;
        y /= TRIANGLE_VERTEX_COUNT;
        return new Point(x, y);
    }

    private Point generatePoint(int i) {
        var x = readCoordinate(i+1, CoordinateType.X_COORD);
        var y = readCoordinate(i+1, CoordinateType.Y_COORD);
        return new Point(x, y);
    }

    private double readCoordinate(int piece, CoordinateType type) {
        System.out.println("Please type in the " + type.value + " coordinate of the " + piece + " point:");
        do {
            String value = scanner.nextLine();
            if (!isNumber(value)) {
                System.out.println("NAN! Please try again");
            } else {
                return Double.parseDouble(value);
            }
        } while (true);
    }

    private boolean isNumber(String probableNumber) {
        //TODO range check missing
        return Objects.nonNull(probableNumber) && pattern.matcher(probableNumber).matches();
    }

    private enum CoordinateType {
        X_COORD("x"), Y_COORD("y");

        private final String value;

        CoordinateType(String value) {
            this.value = value;
        }
    }
}
