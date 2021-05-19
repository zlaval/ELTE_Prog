package com.zlrx.java.advanced.classes.one;

public class FibonacciIter {

    public int fibonacci(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Input number must be positive or zero");
        }
        int a = 0;
        int b = 1;
        int c = 0;
        int round = 1;

        while (round < number) {
            c = b + a;
            a = b;
            b = c;
            round = round + 1;
        }
        return c;
    }

}
