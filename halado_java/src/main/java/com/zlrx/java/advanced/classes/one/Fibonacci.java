package com.zlrx.java.advanced.classes.one;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public int fibonacci(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Input number must be positive or zero");
        }
        if (number == 0 || number == 1) {
            return number;
        }
        List h=new ArrayList();
        int left = fibonacci(number - 1);
        int right = fibonacci(number - 2);
        return left + right;

    }

}
