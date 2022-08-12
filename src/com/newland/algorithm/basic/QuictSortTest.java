package com.newland.algorithm.basic;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuictSortTest {
    /**
     * 找到中间位置
     * @param array
     * @param low 地位
     * @param high 高位
     * @return
     */
    private static int getMiddle(int[] array, int low, int high) {
        //把地位作为中间位置对应值训练
        int temp = array[low];
        while (low < high) {
            // 找到大于temp的左边界
            while (low < high && array[high] >= temp) {
                high--;
            }
            // 将小于temp的放到low的位置
            array[low] = array[high];
            // 找到小于temp的右边界
            while (low < high && array[low] <= temp) {
                low++;
            }
            //交换 大于temp的放到hign位置
            array[high] = array[low];
        }
        //将开始的temp放到最终的位置
        array[low] = temp;
        return low;
    }

    /**
     * 快速排序
     *
     * @param array
     * @param low
     * @param high
     */
    private static void _quick(int[] array, int low, int high) {
        if (low < high) {
            int middle = getMiddle(array, low, high);
            _quick(array, low, middle - 1);
            _quick(array, middle + 1, high);
        }
    }

    /**
     * 快速排序
     *
     * @return
     */
    public static int[] quictSort(int[] array) {
        if (array.length > 0) {
            _quick(array, 0, array.length - 1);
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(quictSort(new int[]{57, 68, 59, 52, 72, 28, 96, 33, 24, 19})));
    }
}
