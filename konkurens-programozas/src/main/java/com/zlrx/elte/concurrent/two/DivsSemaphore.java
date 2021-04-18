package com.zlrx.elte.concurrent.two;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class DivsSemaphore {
    private final Semaphore semaphore = new Semaphore(1);
    private int num;
    private int[] divs;

    public DivsSemaphore(int num) {
        this.num = num;
        calcualteDividers();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        semaphore.acquireUninterruptibly();
        try {
            this.num = num;
            calcualteDividers();
        } finally {
            semaphore.release();
        }
    }

    private void calcualteDividers() {
        List<Integer> divs = new ArrayList<>();
        for (int i = 2; i < num; ++i) {
            if (num % i == 0) {
                divs.add(i);
            }
        }
        this.divs = new int[divs.size()];
        for (int i = 0; i < divs.size(); ++i) {
            this.divs[i] = divs.get(i);
        }
    }

    public int[] getDivs() {
        return divs;
    }

    public boolean isCorrect() {
        semaphore.acquireUninterruptibly();
        try {
            for (int d : divs) {
                if (num % d != 0) {
                    return false;
                }
            }
            return true;
        } finally {
            semaphore.release();
        }
    }

    public void requestExclusivity() {
        semaphore.acquireUninterruptibly();
    }

    public void releaseExclusivity() {
        semaphore.release();
    }

    public void runExclusively(Runnable r) {
        semaphore.acquireUninterruptibly();
        try {
            r.run();
        } finally {
            semaphore.release();
        }
    }
}
