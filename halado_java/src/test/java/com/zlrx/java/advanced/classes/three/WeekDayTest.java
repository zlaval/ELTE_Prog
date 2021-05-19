package com.zlrx.java.advanced.classes.three;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WeekDayTest {

    @Test
    public void testNextDay() {
        WeekDay weekDay = WeekDay.TUE;

        WeekDay nextDay = weekDay.nextDay();

        assertEquals(WeekDay.WED, nextDay);
    }

    @Test
    public void testNextDayRound() {
        WeekDay weekDay = WeekDay.SUN;

        WeekDay nextDay = weekDay.nextDay();

        assertEquals(WeekDay.MON, nextDay);
    }


    @Test
    public void testNextDayPositive() {
        WeekDay weekDay = WeekDay.THU;

        WeekDay nextDay = weekDay.nextDay(4);

        assertEquals(WeekDay.MON, nextDay);
    }

    @Test
    public void testNextDayNegative() {
        WeekDay weekDay = WeekDay.THU;

        WeekDay nextDay = weekDay.nextDay(-4);

        assertEquals(WeekDay.SUN, nextDay);
    }

    @Test
    public void testNextDayWithZero() {
        WeekDay weekDay = WeekDay.THU;

        WeekDay nextDay = weekDay.nextDay(0);

        assertEquals(WeekDay.THU, nextDay);
    }

    @Test
    public void testHunLang() {
        assertEquals("Hétfő", WeekDay.MON.get("hu"));
    }

    @Test
    public void testEngLang() {
        assertEquals("Monday", WeekDay.MON.get("en"));
    }

    @Test
    public void testNoLang() {
        assertEquals("?", WeekDay.MON.get("esp"));
    }

    @Test
    public void testLanguages() {
        assertEquals(List.of("hu", "en"), WeekDay.languages);
    }

}
