package com.zlrx.java.advanced.classes.two;


import com.zlrx.java.advanced.classes.one.Fibonacci;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class FibonacciTheoryTest {

    @DataPoint
    public static int POSITIVE = 6;


    @DataPoint
    public static int NEGATIVE = -1;

    private Fibonacci fibonacci = new Fibonacci();

    @Theory
    public void notNegativeFibonacci(int num) {
        assumeTrue(num > 0);
        int result = fibonacci.fibonacci(num);
        assertEquals(8, result);
    }

}
