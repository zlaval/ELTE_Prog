package com.zlrx.algorithms.one;

public class Legendre {

    public static void main(String[] args) {
        System.out.println(legendre(5f, 3));
    }

    static float legendre(float a, int k) {
        float s = a == 0 ? 0 : 1;
        while (k > 0) {
            if (k % 2 == 0) {
                a = a * a;
                k = k / 2;
            } else {
                s = s * a;
                k = k - 1;
            }
        }
        return s;

    }

}
