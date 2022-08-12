package com.newland.algorithm.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基数排序
 */
public class RadixSortTest {
    public static int[] radixSort(int[] array) {
        List<List<Integer>> queue = new ArrayList<List<Integer>>();
        for (int i = 0; i < 10; i++) {
            queue.add(new ArrayList<>());
        }
        int max = 0;
        for (int arr : array) {
            if (arr > max) {
                max = arr;
            }
            int time = max / 10;
            for (int i = 0; i < time; i++) {
                for (int j = 0; j < array.length; j++) {
                    int x = array[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                    List<Integer> list = queue.get(x);
                    list.add(array[j]);
                    queue.set(x, list);
                }
                int count = 0;
                for (int k = 0; k < 10; k++) {
                    while (queue.get(k).size() > 0) {
                        array[count++] = queue.get(k).get(0);
                        queue.get(k).remove(0);
                    }
                }
            }

        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(radixSort(new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 101, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51})));
    }
}