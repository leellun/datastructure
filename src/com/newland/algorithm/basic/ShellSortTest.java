package com.newland.algorithm.basic;

import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSortTest {
    public static int[] shellSort(int[] a) {
        double d1 = a.length;
        while (true) {
            d1 = Math.ceil(d1 / 2);
            int d = (int) d1;
            for (int i = d; i < a.length; i = i + 1) {
                int j = i - d;
                int temp = a[i];
                for (; j >= 0 && a[j] > temp; j = j - d) {
                    a[j + d] = a[j];
                }
                a[j + d] = temp;
            }
            if (d == 1) {
                break;
            }
        }
        return a;
    }

    public static int[] shellSort2(int[] a) {
        System.out.println(Arrays.toString(a));
        int count=0;
        for (int d = a.length / 2; d>0; d /= 2) {
            for (int i = d; i < a.length; i = i + 1) {
                int j = i - d;
                int temp = a[i];
                for (; j >= 0 && a[j] > temp; j = j - d) {
                    a[j + d] = a[j];
                    System.out.println(Arrays.toString(a)+"=="+i+"---"+j);
                    count++;
                }
                a[j + d] = temp;
                System.out.println(Arrays.toString(a)+"=="+i+"---"+j);
                count++;
            }
        }
        System.out.println(count);
        return a;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(shellSort2(new int[]{57, 68, 59, 52, 72, 28, 96, 33, 24, 19})));
    }
}
