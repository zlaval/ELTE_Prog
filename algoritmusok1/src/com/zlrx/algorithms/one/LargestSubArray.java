package com.zlrx.algorithms.one;

public class LargestSubArray {

    public static void main(String[] args) {
        int[] array = new int[]{1, -2, 3, 6, -9, 4, 6};
        LargestSubArray largestSubArray = new LargestSubArray();
        largestSubArray.findLargestSubArray(array);
    }


    void findLargestSubArray(int[] array) {
        int globalMax = array[0];
        int currentMax = array[0];
        int startIndex = 0;
        int endIndex = 0;
        int currentStartIndex = 0;

        for (int i = 1; i < array.length; i++) {
            int nextValue = currentMax + array[i];
            if (array[i] < nextValue) {
                currentMax = nextValue;
            } else {
                currentMax = array[i];
                currentStartIndex = i;
            }

            if (currentMax > globalMax) {
                globalMax = currentMax;
                endIndex = i;
                startIndex = currentStartIndex;
            }
        }

        System.out.println("Largest subarray sum = " + globalMax + ", start from: " + startIndex + " end:" + endIndex);
    }


}
