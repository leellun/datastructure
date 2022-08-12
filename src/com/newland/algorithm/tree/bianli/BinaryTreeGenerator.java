package com.newland.algorithm.tree.bianli;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 *
 * 二叉树推演
 * Author: leell
 * Date: 2022/8/10 16:19:31
 */
public class BinaryTreeGenerator {

    public static void main(String[] args) {
        System.out.println("通过先序中序获取后续遍历：");
        new BinaryTreeGenerator().postBinaryTree(new int[]{1, 4, 5, 8, 2, 3, 6, 7}, new int[]{4, 5, 8, 1, 3, 2, 6, 7});
        System.out.println("通过中序后续获取先序遍历：");
        new BinaryTreeGenerator().preBinnaryTree(new int[]{4, 5, 8, 1, 3, 2, 6, 7}, new int[]{8, 5, 4, 3, 7, 6, 2, 1});
    }

    public void postBinaryTree(int[] preArr, int[] inArr) {
        Deque<Integer> deque = new ArrayDeque<>();
        postBinnaryTree(preArr, 0, preArr.length - 1, inArr, 0, inArr.length - 1, deque);
        System.out.println(Arrays.toString(deque.toArray(new Integer[]{})));
    }

    /**
     * 通过先序中序得出后续
     * 思想：通过先序节点确定一棵树的顶部，然后通过找到该节点在中序中的位置，然后拆分二叉树。
     * 序序排列
     * @param preArr   先序队列
     * @param preStart
     * @param preEnd
     * @param inArr    中序队列
     * @param inStart
     * @param inEnd
     */
    private void postBinnaryTree(int[] preArr, int preStart, int preEnd, int[] inArr, int inStart, int inEnd, Deque<Integer> deque) {
        if (preStart > preEnd || inStart > inEnd) return;
        int i = inStart;
        //找到中子树的指定位置，用于拆分二叉树
        for (; i <= inEnd && inArr[i] != preArr[preStart]; i++) ;
        //左子树
        postBinnaryTree(preArr, preStart + 1, preStart + i - inStart, inArr, inStart, i - 1, deque);
        //右子树
        postBinnaryTree(preArr, preStart + i - inStart + 1, preEnd, inArr, i + 1, inEnd, deque);

        //后续遍历父节点最后添加
        deque.add(preArr[preStart]);
    }

    public void preBinnaryTree(int[] inArr, int[] postArr) {
        Deque<Integer> deque = new ArrayDeque<>();
        preBinnaryTree(inArr, 0, inArr.length - 1, postArr, 0, postArr.length - 1, deque);
        System.out.println(Arrays.toString(deque.toArray(new Integer[]{})));
    }

    /**
     * 中序和后续得到先序
     * @param inArr 中序列表
     * @param inStart
     * @param inEnd
     * @param postArr 后续列表
     * @param postStart
     * @param postEnd
     * @param deque
     */
    private void preBinnaryTree(int[] inArr, int inStart, int inEnd, int[] postArr, int postStart, int postEnd, Deque<Integer> deque) {
        if (postStart > postEnd || inStart > inEnd) return;
        deque.add(postArr[postEnd]);
        int i = inStart;
        //找到中子树的指定位置，用于拆分二叉树
        for (; i <= inEnd && inArr[i] != postArr[postEnd]; i++) ;
        preBinnaryTree(inArr, inStart, i - 1, postArr, postStart, postStart + (i - 1 - inStart), deque);
        preBinnaryTree(inArr, i + 1, inEnd, postArr, postStart + (i - inStart), postEnd - 1, deque);
    }
}
