package com.zlrx.java.advanced.classes.one;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FibonacciIterTest {

    private FibonacciIter underTest = new FibonacciIter();

    @Test
    public void test_should_return_0_when_input_0() {

        int result = underTest.fibonacci(0);

        assertEquals(0, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_should_throw_exception_on_negative_number() {

        underTest.fibonacci(-1);

    }

    @Test
    public void test_should_return_21_when_input_is_8() {

        int result = underTest.fibonacci(8);

        assertEquals(21, result);

    }

}
