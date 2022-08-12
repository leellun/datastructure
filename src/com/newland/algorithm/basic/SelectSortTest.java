package com.newland.algorithm.basic;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSortTest {
    public static int[] selectSort(int[] a) {
        int position = 0;
        for (int i = 0; i < a.length; i++) {
            position = i;
            int temp = a[i];
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[i]) {
                    temp = a[j];
                    position = j;
                }
            }
            a[position] = a[i];
            a[i] = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(selectSort(new int[]{57, 68, 59, 52})));
    }
}
