package com.zlrx.java.advanced.classes.two;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {

    private Point underTest;

    @Before
    public void init() {
        underTest = new Point(5, 5);
    }

    @Test
    public void whenShiftXOnlyXChanges() {

        underTest.shiftX(5);

        assertEquals(new Point(10, 5), underTest);
    }

    @Test
    public void whenShiftYOnlyYChanges() {

        underTest.shiftY(5);

        assertEquals(new Point(5, 10), underTest);
    }

    @Test
    public void whenShiftXYAllChange() {

        underTest.shift(5, 10);

        assertEquals(new Point(10, 15), underTest);
    }


}
