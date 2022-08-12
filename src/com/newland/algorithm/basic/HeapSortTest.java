package com.newland.algorithm.basic;

import java.util.Arrays;

/**
 * 堆排序
 */
public class HeapSortTest {
    private static void buildMaxHeap(int[] array, int lastIndex) {
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            int k = i;
            while (2 * k + 1 <= lastIndex) {
                int bigIndex = 2 * k + 1;
                if (bigIndex < lastIndex) {
                    if (array[bigIndex] < array[bigIndex + 1]) {
                        bigIndex++;
                    }
                }
                if (array[k] < array[bigIndex]) {
                    swap(array, k, bigIndex);
                    k = bigIndex;
                    break;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 堆排序
     *
     * @return
     */
    public static int[] heapSort(int[] array) {
        int arrayLength = array.length;
        for (int i = 0; i < array.length; i++) {
            buildMaxHeap(array, arrayLength - 1 - i);
            swap(array, 0, arrayLength - 1 - i);
        }
        return array;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 算法原理：通过筛选把最大值逐渐往root节点迁移，然后swap
     * @param array
     * @return
     */
    public static int[] heapSort2(int[] array) {
        int arrayLength = array.length;
        for (int i = 0; i < array.length; i++) {
            int lastIndex=arrayLength - 1 - i;
            for (int j = lastIndex / 2; j >= 0; j--) {
                int bigIndex=2*j+1;
                if(bigIndex<=lastIndex){
                    if(bigIndex<lastIndex&&array[bigIndex]<array[bigIndex+1]){
                        bigIndex++;
                    }
                    if(array[j]<array[bigIndex]){
                        swap(array,j,bigIndex);
                    }
                }
            }
            swap(array, 0, lastIndex);
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(heapSort2(new int[]{46, 79, 56, 38, 40, 84})));
    }
}
