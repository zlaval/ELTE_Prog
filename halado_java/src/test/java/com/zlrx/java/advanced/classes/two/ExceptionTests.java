package com.zlrx.java.advanced.classes.two;

import org.junit.Test;

public class ExceptionTests {

    @Test(expected = ArithmeticException.class)
    public void nullDivTest() {
        var x = 1 / 0;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void arrayOutBoundIndex() {
        int[] arr = new int[1];

        var x = arr[10];

    }

}
