package com.zlrx.java.advanced.classes.two;

import com.zlrx.java.advanced.classes.one.Fibonacci;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FibonacciParameterizedTest {

    @Parameterized.Parameters(name = "{index}: fibonacci({0})={1}")
    public static Collection<Object[]> injectedData() {
        return Arrays.asList(
                new Object[][]{
                        {0, 0},
                        {1, 1},
                        {2, 1},
                        {3, 2},
                        {4, 3},
                        {5, 5},
                        {6, 8}
                }
        );
    }

    private int number;
    private int expected;

    private Fibonacci fibonacci = new Fibonacci();

    public FibonacciParameterizedTest(int number, int expected) {
        this.number = number;
        this.expected = expected;
    }

    @Test
    public void assertFibonacciResultWithExpected() {

        int result = fibonacci.fibonacci(number);

        assertEquals(expected, result);
    }

}
