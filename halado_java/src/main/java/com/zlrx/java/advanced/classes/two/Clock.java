package com.zlrx.java.advanced.classes.two;

public class Clock {

    int hour;

    public Clock(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        this.hour = hour;
    }

    public void shift(int hours) {
        int tmp = (hour + hours) % 24;
        if (tmp >= 0) {
            hour = tmp;
        } else {
            hour = 24 + tmp;
        }
    }

    public int getHour() {
        return hour;
    }
}
