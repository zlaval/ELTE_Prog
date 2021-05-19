package com.zlrx.java.advanced.classes.five;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class JStreams {

    private static final String FIRST_TASK_FILE = "first.txt";
    private static final String APPENDABLE = " kotlin is better :D";

    private static final Random random = new Random();

    @BeforeClass
    public static void init() {
        var rowCount = random.nextInt(50) + 50;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowCount; i++) {
            int strLen = random.nextInt(10) + 1;
            String rndTxt = random.ints(97, 123)
                    .limit(strLen)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            sb.append(rndTxt);
            sb.append(System.lineSeparator());
        }

        writeIntoFile(sb.toString(), FIRST_TASK_FILE);
    }

    private static void writeIntoFile(String str, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void first0() throws IOException {
        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .filter(l -> l.length() > 5)
                .map(l -> l + APPENDABLE)
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "0_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test
    public void firstA() throws IOException {
        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .filter(l -> l.length() > 5)
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "A_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test
    public void firstB() throws IOException {
        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .skip(3)
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "B_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test
    public void firstC() throws IOException {
        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .limit(10)
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "C_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test
    public void firstE() throws IOException {
        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "D_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test
    public void firstF() throws IOException {

        Comparator<String> comparator = (String a, String b) -> {
            var aLen = a.chars().mapToObj(Character::toString).distinct().count();
            var bLen = b.chars().mapToObj(Character::toString).distinct().count();
            return Long.compare(aLen, bLen);
        };

        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .sorted(comparator)
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "F_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test
    public void firstG() throws IOException {
        Predicate<String> isPalindrom = (s) -> IntStream.range(0, s.length() / 2).noneMatch(i -> s.charAt(i) != s.charAt(s.length() - i - 1));

        var result = Files.readAllLines(Paths.get(FIRST_TASK_FILE))
                .stream()
                .map(s -> {
                    if (isPalindrom.test(s)) {
                        return s;
                    } else {
                        return s + new StringBuilder(s).reverse().toString();
                    }
                })
                .collect(Collectors.joining(System.lineSeparator()));
        String filename = "G_out_" + System.currentTimeMillis() + ".txt";
        writeIntoFile(result, filename);
    }

    @Test(timeout = 1000)
    public void second() throws FileNotFoundException {
        Supplier<String> fromFile = new Supplier<>() {
            Scanner scanner = new Scanner(new File(FIRST_TASK_FILE));

            @Override
            public String get() {
                try {
                    return scanner.nextLine();
                } catch (Exception e) {
                    return null;
                }
            }
        };

        var line = fromFile.get();
        while (line != null) {
            System.out.println(line);
            line = fromFile.get();
        }
    }

    @Test
    public void thirdA() {
        var points = IntStream.range(0, 30)
                .mapToObj(i -> new Point(random.nextInt(100), random.nextInt(100)))
                .collect(Collectors.toList());


        var closest = points.stream()
                .min(pointComparator());

        closest.ifPresent(System.out::println);


        var furthest = points.stream()
                .max(pointComparator());
        furthest.ifPresent(System.out::println);
    }

    @Test
    public void thirdB() {
        var points = IntStream.range(0, 30)
                .mapToObj(i -> new Point(random.nextInt(100), random.nextInt(100)))
                .collect(Collectors.toList());

        Predicate<Integer> isPrime = (number) -> BigInteger.valueOf(number).isProbablePrime(1);

        var firstPrimePoint = points.stream()
                .filter(p -> isPrime.test(p.x) && isPrime.test(p.y))
                .findFirst();

        firstPrimePoint.ifPresent(System.out::println);

        var anyPrime = points.stream()
                .anyMatch(p -> isPrime.test(p.x) && isPrime.test(p.y));
        System.out.println("Any prime: " + anyPrime);

        var allPrime = points.stream()
                .allMatch(p -> isPrime.test(p.x) && isPrime.test(p.y));

        System.out.println("All prime: " + allPrime);
    }

    @Test
    public void fourA() {
        var result = LongStream.range(0, Integer.MAX_VALUE).sum();
        System.out.println(result);
    }

    @Test
    public void fourB() {
        var result = LongStream.range(0, Integer.MAX_VALUE).parallel().sum();
        System.out.println(result);
    }

    @Test
    public void fifth() {
        Map<Integer, List<Integer>> result = IntStream.range(0, 200)
                .boxed()
                .filter(n -> n > 1)
                .collect(Collectors.groupingBy(
                        this::lowestPrimeDivisor,
                        Collector.of(ArrayList::new, List::add,
                                (a, b) -> {
                                    a.addAll(b);
                                    return a;
                                }))
                );

        result.forEach((k, v) -> {
            System.out.print(k);
            System.out.print(" -> ");
            v.forEach(n -> System.out.print(", " + n)
            );
            System.out.println();
        });
    }

    private Integer lowestPrimeDivisor(int n) {
        if (n % 2 == 0)
            return 2;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return i;
        }

        return n;
    }


    private Comparator<Point> pointComparator() {
        return (Point a, Point b) -> {
            var aDistance = distanceFromOrigin(a);
            var bDistance = distanceFromOrigin(b);
            return Double.compare(aDistance, bDistance);
        };
    }

    private double distanceFromOrigin(Point p) {
        return Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
    }

    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
