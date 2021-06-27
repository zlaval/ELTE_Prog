package com.zlrx.elte.slideout.utils;

public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int floorToInt(double value) {
        return Double.valueOf(Math.round(value)).intValue();
    }

}
