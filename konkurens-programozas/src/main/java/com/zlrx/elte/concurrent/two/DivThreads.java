package com.zlrx.elte.concurrent.two;

import java.util.Arrays;
import java.util.Random;

public class DivThreads {

    private static final int MAX_RANDOM = 1_000;
    private static final int MIN_RANDOM = 4;

    public static void main(String[] args) {
        //Dividers divs = new Dividers(7);
        DivsSemaphore divs = new DivsSemaphore(7);
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            new Thread(() -> {
                while (true) {
                    int nextDiv = random.nextInt(MAX_RANDOM - MIN_RANDOM) + MIN_RANDOM;
                    divs.setNum(nextDiv);
                    if (divs.isCorrect()) {
                        divs.runExclusively(() -> {
                            System.out.println("divs for " + divs.getNum() + " are OK " + Arrays.toString(divs.getDivs()));
                        });
                    } else {
                        System.err.println("divs for " + divs.getNum() + " are NOT OK " + Arrays.toString(divs.getDivs()));
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
