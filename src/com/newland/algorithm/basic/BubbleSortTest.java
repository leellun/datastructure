package com.newland.algorithm.basic;

import java.util.Arrays;

public class BubbleSortTest {
    /**
     * 冒泡排序
     *
     * @return
     */
    public static int[] bubbleSort(int a[]) {
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < a.length - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
        return a;
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(bubbleSort(new int[]{57, 68, 59, 52})));
    }
}
