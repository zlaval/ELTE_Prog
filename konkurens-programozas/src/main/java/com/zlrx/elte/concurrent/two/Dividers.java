package com.zlrx.elte.concurrent.two;

import java.util.ArrayList;
import java.util.List;

public class Dividers {
    private int num;
    private int[] divs;

    public Dividers(int num) {
        this.num = num;
        calcualteDividers();
    }

    public int getNum() {
        return num;
    }

    public synchronized void setNum(int num) {
        this.num = num;
        calcualteDividers();
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
        synchronized (this) {
            for (int d : divs) {
                if (num % d != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
