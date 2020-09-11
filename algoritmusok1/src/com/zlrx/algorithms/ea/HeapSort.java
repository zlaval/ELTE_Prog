package com.zlrx.algorithms.ea;

import java.util.Arrays;

public class HeapSort {


    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        int[] array = {18, 23, 51, 42, 14, 35, 97, 53, 60, 19};

        heapSort.heapSort(array);
        Arrays.stream(array).forEach(a -> System.out.print(a + " "));
    }


    public void heapSort(int[] array) {
        buildMaxHeap(array);
        Arrays.stream(array).forEach(a -> System.out.print(a + " "));
        System.out.println();
        System.out.println("***********************start to sort***************************");
        int index = array.length - 1;
        while (index > 0) {
            System.out.println("----start index_" + index + "----");
            swap(array, 0, index);
            index--;
            sink(array, 0, index);
        }


    }

    private void swap(int[] array, int a, int b) {
        System.out.println("swap index: " + array[a] + "<-->" + array[b] + "");
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private void sink(int[] array, int k, int n) {
        int i = k;
        int j = left(k);
        boolean b = true;
        System.out.println("sink start");
        int x = array[i];
        while (j <= n && b) {
            if (j < n && array[j + 1] > array[j]) {
                System.out.println("j+1>j ->j++");
                j++;
            }
            if (x< array[j]) {
                System.out.println("swap in sink");
                 //swap(array, i, j);
                array[i] = array[j];
                i = j;
                j = left(j);
            } else {
                System.out.println("ret false");
                b = false;
            }

        }
        array[i] = x;
        System.out.println("sink end");
    }

    private int left(int k) {
        return 2 * k + 1;
    }

    private void buildMaxHeap(int[] array) {
        int k = parent(array.length - 1);
        while (k >= 0) {
            System.out.println("+++buildheap round_" + k + "+++");
            sink(array, k, array.length - 1);
            k--;
        }

    }

    private int parent(int k) {
        return ((k + 1) / 2) - 1;
    }
}
