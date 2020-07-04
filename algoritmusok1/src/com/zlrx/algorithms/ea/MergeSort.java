package com.zlrx.algorithms.ea;

import java.util.stream.IntStream;

public class MergeSort {

    public static void main(String[] args) {
        int[] a = new int[]{7, 5, 13, 7, 1, 2, 8, 30, 3, 12};
        mergeSort(a);
        IntStream.of(a).forEach(i -> System.out.print(i + " ,"));
    }

    private static void mergeSort(int[] array) {
        ms(array, 0, array.length);
    }

    private static void ms(int[] a, int u, int v) {
        if (u < (v - 1)) {
            int m = (u + v) / 2;
            ms(a, u, m);
            ms(a, m, v);
            merge(a, u, m, v);
        }
    }

    private static void merge(int[] a, int u, int m, int v) {
        int d = m - u;
        int z[] = new int[d];
        int l = 0;
        for (int f = u; f < m; f++) {
            z[l++] = a[f];
        }
        int k = u;
        int j = 0;
        int i = m;

        while (i < v && j < d) {
            if (a[i] < z[j]) {
                a[k] = a[i++];
            } else {
                a[k] = z[j++];
            }
            k++;
        }
        while (j < d) {
            a[k++] = z[j++];
        }


    }


}
