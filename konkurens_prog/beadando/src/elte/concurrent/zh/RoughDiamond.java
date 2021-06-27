package elte.concurrent.zh;

import java.util.concurrent.ThreadLocalRandom;

public class RoughDiamond {
    private final int value = ThreadLocalRandom.current().nextInt(49001) + 1000;

    public int getValue() {
        return value;
    }
}
