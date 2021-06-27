package com.zlrx.java.advanced.classes.one;

import java.util.HashMap;
import java.util.Map;

public class FibonacciIter<T> {

    public int fibonacci(int number) {

        Object map = new HashMap<String, Integer>();

        var x = (Map<String, Integer>) map;

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
