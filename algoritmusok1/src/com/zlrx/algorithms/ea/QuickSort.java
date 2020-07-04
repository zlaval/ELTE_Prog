package com.zlrx.algorithms.ea;

import java.util.Random;
import java.util.stream.IntStream;

public class QuickSort {

    Random random = new Random();


    public static void main(String[] args) {
        QuickSort q = new QuickSort();
        q.quickSort(new int[]{7, 5, 13, 7, 1, 2, 8, 30, 3, 12});
    }

    public void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
        printArray(array);
    }

    public void printArray(int[] array) {
        IntStream.of(array).forEach(i -> System.out.print(i + " , "));
        System.out.println();
    }

    private void quickSort(int[] array, int p, int r) {
        if (p < r) {
            System.out.println("*********************Sort from " + p + " to " + r + "*************************");
            int q = partition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
            System.out.println("*********************Sort from " + p + " to " + r + " END************************");
        }
    }

    private int partition(int[] array, int p, int r) {
        System.out.println("______________partition " + p + " to " + r + "_______________________");
        int i = random.nextInt(r - p) + p;
        int x = array[i];
        System.out.println("random i=" + i + " the x is " + x);
        array[i] = array[r];
        System.out.println(" change array[i]=array[r] i=" + i + " r=" + r + " new array[i]=" + array[i]);
        printArray(array);
        i = p;

        while (i < r && array[i] <= x) {
            i++;
        }
        System.out.println(" step i while i<r or array[r]<x, i=" + i);
        if (i < r) {
            int j = i + 1;
            while (j < r) {
                if (array[j] < x) {
                    System.out.println("change array[i] with array[j]  i=" + i + " j=" + j + " orig array[i]=" + array[i] + " array[j]=" + array[j]);
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                    i++;
                    System.out.println("after in while change");
                    printArray(array);
                }
                j++;
            }
            System.out.println("array[r]=array[i] i=" + i + " r=" + r + " orig array[i]=" + array[i] + " array[r]=" + array[r]);
            array[r] = array[i];
            System.out.println("after while change");
            System.out.println("set array[i] to x");
            printArray(array);
            array[i] = x;
        } else {
            System.out.println("i>=r, no change set array[r] to x");
            array[r] = x;
            printArray(array);
        }
        System.out.println("array after partition:");
        printArray(array);
        System.out.println("return partition i=" + i);
        System.out.println("______________partition " + p + " to " + r + " END_____________________");
        return i;

    }


}
