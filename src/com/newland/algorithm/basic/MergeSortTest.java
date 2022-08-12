package com.newland.algorithm.basic;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSortTest {
    public static int[] mergeSort(int[] array) {
        sort(array, 0, array.length - 1);
        return array;
    }

    private static void merge(int[] array, int left, int center, int right) {
        int[] tempArray = new int[array.length];
        int middle = center + 1;
        int third = left;
        int temp = left;
        while (left <= center && middle <= right) {
            if (array[left] < array[middle]) {
                tempArray[third++] = array[left++];
            } else {
                tempArray[third++] = array[middle++];
            }
        }
        while (left <= center) {
            tempArray[third++] = array[left++];
        }
        while (middle <= right) {
            tempArray[third++] = array[middle++];
        }
        while (temp <= right) {
            array[temp] = tempArray[temp++];
        }
    }

    private static void sort(int[] array, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            sort(array, left, center);
            sort(array, center + 1, right);
            merge(array, left, center, right);
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeSort(new int[]{57, 68, 59, 52, 72, 28, 96, 33})));
    }
}