package elte.concurrent.zh;

import java.util.concurrent.ThreadLocalRandom;

public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("Util class");
    }

    public static void sleepRandomly(int min, int max) {
        int random = ThreadLocalRandom.current().nextInt(max - min) + min;
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
