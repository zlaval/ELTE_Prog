package com.zlrx.java.advanced.classes.six;

import java.util.Random;
import java.util.Scanner;

public class Dice {

    private Random random = new Random();

    public static void main(String[] args) {
        var dice = new Dice();
        dice.throwDices();
    }

    public void throwDices() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ird be a kocka méretét a dobáshoz: ");
        var val = scanner.next();
        while (!"0".equals(val)) {
            throwDice(Integer.parseInt(val));
            System.out.println("ird be a kocka méretét a dobáshoz: ");
            val = scanner.next();
        }

    }

    public void throwDice(int size) {
        var result = random.nextInt(size) + 1;
        System.out.println("A dobásod eredménye " + size + " oldalú kockával: " + result);
        System.out.println();
    }


}
