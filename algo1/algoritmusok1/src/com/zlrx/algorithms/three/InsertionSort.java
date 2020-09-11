package com.zlrx.algorithms.three;

public class InsertionSort {

    public static void main(String[] args) {
        int numbers[] = {0, 4, 7, 1, 5, 9};
        sort(numbers);
    }

    static void sort(int numbers[]) {
        for (int i = 2; i < numbers.length; i++) {
            if (numbers[i - 1] > numbers[i]) {
                int x = numbers[i];
                int j = i - 2;
                while (j > 0 && numbers[j] > x) {
                    numbers[j + 1] = numbers[j];
                    j--;
                }
                numbers[j + 1] = x;
            }
        }

        for (int i = 1; i < numbers.length;i++) {
            System.out.print(numbers[i] + " ");
        }

    }

}
