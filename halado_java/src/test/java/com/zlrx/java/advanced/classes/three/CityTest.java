package com.zlrx.java.advanced.classes.three;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CityTest {

    @Test
    public void testCityZip() {
        assertEquals(1000, City.BUDAPEST.getZipCode());
    }

    @Test
    public void testCityZipFromOtherCity() {
        assertEquals(1000, City.ZUGLO.getZipCode());
    }

}
