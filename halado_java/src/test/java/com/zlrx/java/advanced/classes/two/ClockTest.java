package com.zlrx.java.advanced.classes.two;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClockTest {


    @Test
    public void addFiveToTenShouldBeFifteen() {
        Clock clock = new Clock(10);

        clock.shift(5);

        assertEquals(15, clock.hour);
    }

    @Test
    public void subFiveFromTenShouldBeFive() {
        Clock clock = new Clock(10);

        clock.shift(-5);

        assertEquals(5, clock.hour);
    }

    @Test
    public void addFourToTwentyShouldBeZero() {
        Clock clock = new Clock(20);

        clock.shift(4);

        assertEquals(0, clock.hour);
    }

    @Test
    public void addThreeToTwentyShouldBeTwentyThree() {
        Clock clock = new Clock(20);

        clock.shift(3);

        assertEquals(23, clock.hour);
    }

    @Test
    public void subFiveFromFourShouldBeTwentyThree() {
        Clock clock = new Clock(4);

        clock.shift(-5);

        assertEquals(23, clock.hour);
    }

    @Test
    public void subFourFromFourShouldBeZero() {
        Clock clock = new Clock(4);

        clock.shift(-4);

        assertEquals(0, clock.hour);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeNumberTest() {
        new Clock(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void greatherThenTwentyThreeTest() {
        new Clock(24);
    }

}
