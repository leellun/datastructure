package com.newland.algorithm.basic;

import java.util.Arrays;

public class InsertSortTest {
    public static int[] insertSort(int[] a) {
        // 算法实际次数
        int count = 0;
        for (int i = 1; i < a.length; i++) {
            int j = i - 1;
            int temp = a[i];
            if (a[j] < temp) {
                count++;
            }
            for (; j >= 0 && a[j] > temp; j--) {
                a[j + 1] = a[j];
                count++;
            }
            a[j + 1] = temp;
        }
        System.out.println(a.length + "---" + count);
        return a;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(insertSort(new int[]{57, 68, 59, 52})));
    }
}
