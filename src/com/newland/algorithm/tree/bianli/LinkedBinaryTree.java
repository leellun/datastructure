package com.newland.algorithm.tree.bianli;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class LinkedBinaryTree<T> implements BinaryTree<T> {
    private Node root;

    public LinkedBinaryTree(Node root) {
        this.root = root;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return this.size(root);
    }

    /**
     * 通过递归获取大小
     *
     * @param root
     * @return
     */
    public int size(Node root) {
        if (root == null) {
            return 0;
        } else {
            int leftSize = size(root.left);
            int rightSize = size(root.right);
            return leftSize + rightSize + 1;
        }
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    /**
     * 通过地柜获取高度
     *
     * @param root
     * @return
     */
    private int getHeight(Node root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = getHeight(root.left);
            int rightHeight = getHeight(root.right);
            return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
        }
    }

    @Override
    public Node find(T value) {
        return find(root, value);
    }

    /**
     * 通过地柜查找指定元素
     *
     * @param node
     * @param value
     * @return
     */
    private Node find(Node node, T value) {
        if (node == null) {
            return null;
        } else if (node.value == value) {
            return node;
        } else {
            Node node1 = find(node.left, value);
            if (node1 != null) return node1;
            Node node2 = find(node.right, value);
            return node2;
        }
    }

    /**
     * 前序递归遍历
     * 思想：
     * 1 通过递归遍历:打印节点->遍历左子树->遍历右子树
     * 2 递归的思想就是只要实现一个操作，后续操作即可按照前面的重复执行
     */
    @Override
    public void preOrderTraverse() {
        preOrderTraverse(root);
        System.out.println();
    }

    private void preOrderTraverse(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrderTraverse(node.left);
            preOrderTraverse(node.right);
        }
    }

    /**
     * 中序遍历递归操作
     * 思想：通过递归遍历:遍历左子树->打印节点->遍历右子树
     */
    @Override
    public void inOrderTraverse() {
        inOrderTraverse(root);
        System.out.println();
    }

    private void inOrderTraverse(Node node) {
        if (node != null) {
            inOrderTraverse(node.left);
            System.out.print(node.value + " ");
            inOrderTraverse(node.right);
        }
    }

    /**
     * 后序遍历递归操作
     * 思想：遍历左子树->遍历右子树->打印节点
     */
    @Override
    public void postOrderTraverse() {
        postOrderTraverse(root);
        System.out.println();
    }

    private void postOrderTraverse(Node node) {
        if (node != null) {
            postOrderTraverse(node.left);
            postOrderTraverse(node.right);
            System.out.print(node.value + " ");
        }
    }

    /**
     * 前序遍历非递归操作
     * 1）对于任意节点current，若该节点不为空则访问该节点后再将节点压栈，并将左子树节点置为current，重复此操作，直到current为空。
     * 2）若左子树为空，栈顶节点出栈，将该节点的右子树置为current
     * 3) 重复1、2步操作，直到current为空且栈内节点为空。
     * <p>
     * 思想：
     * 1 从root节点先遍历左节点，左节点需要加到队列中，当左节点为空时，弹出队列中最后添加进去的左节点，再遍历该节点的有节点
     */
    @Override
    public void preOrderByStack() {
        Deque<Node> deque = new LinkedList<>();
        Node current = root;
        while (current != null || !deque.isEmpty()) {
            while (current != null) {
                System.out.print(current.value + " ");
                deque.push(current);
                current = current.left;
            }
            if (!deque.isEmpty()) {
                current = deque.poll();
                current = current.right;
            }
        }
        System.out.println();
    }

    /**
     * 中序遍历非递归操作
     * 1）对于任意节点current，若该节点不为空则将该节点压栈，并将左子树节点置为current，重复此操作，直到current为空。
     * 2）若左子树为空，栈顶节点出栈，访问节点后将该节点的右子树置为current
     * 3) 重复1、2步操作，直到current为空且栈内节点为空。
     */
    @Override
    public void inOrderByStack() {
        Deque<Node> deque = new LinkedList<>();
        Node current = root;
        while (current != null || !deque.isEmpty()) {
            while (current != null) {
                deque.push(current);
                current = current.left;
            }
            if (deque.size() > 0) {
                current = deque.poll();
                System.out.print(current.value + " ");
                current = current.right;
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历非递归操作
     * 1）对于任意节点current，若该节点不为空则访问该节点后再将节点压栈，并将左子树节点置为current，重复此操作，直到current为空。
     * 2）若左子树为空，取栈顶节点的右子树，如果右子树为空或右子树刚访问过，则访问该节点，并将preNode置为该节点
     * 3) 重复1、2步操作，直到current为空且栈内节点为空。
     */
    @Override
    public void postOrderByStack() {
        Deque<Node> deque = new LinkedList<>();
        Node head = root;
        Node prev = null;
        while (head != null || !deque.isEmpty()) {
            Node temp = head;
            while (temp != null) {
                deque.push(temp);
                temp = temp.left;
            }
            Node top = deque.poll();
            if (top.right == null || prev == top.right) {
                System.out.print(top.value + " ");
                head = null;
            } else {
                head = top.right;
                deque.push(top);
            }
            prev = top;
        }
        System.out.println();
    }

    /**
     * 后序遍历
     */
    public void test1() {
        Deque<Node> queue = new LinkedList<>();
        Node currentNode = root;
        Node prev = null;
        while (currentNode != null || !queue.isEmpty()) {
            Node temp = currentNode;
            while (temp != null) {
                queue.push(temp);
                temp = temp.left;
            }
            Node top = queue.poll();
            if (top.right == null || top.right == prev) {
                currentNode = null;
                System.out.println(top.value + "-");
            } else {
                queue.push(top);
                currentNode = top.right;
            }
            prev = top;
        }
    }

    /**
     * 中序遍历
     */
    public void test2() {
        Deque<Node> queue = new LinkedList<>();
        Node currentNode = root;
        while (currentNode != null || !queue.isEmpty()) {
            Node temp = currentNode;
            while (temp != null) {
                queue.push(temp);
                temp = temp.left;
            }
            Node node = queue.poll();
            System.out.print(node.value + "-");
            currentNode = node.right;
        }
        System.out.println();
    }

    /**
     * 先序遍历
     */
    public void test3() {
        Deque<Node> queue = new LinkedList<>();
        Node currentNode = root;
        while (currentNode != null || !queue.isEmpty()) {
            Node temp = currentNode;
            while (temp != null) {
                queue.push(temp);
                System.out.print(temp.value + "-");
                temp = temp.left;
            }
            Node top = queue.poll();
            currentNode = top.right;
        }
    }

    /**
     * 按照层次遍历二叉树
     */
    @Override
    public void levelOrderByStack() {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (queue.size() != 0) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Node temp = queue.poll();
                System.out.print(temp.value + " ");
                if (temp.left != null) queue.add(temp.left);
                if (temp.right != null) queue.add(temp.right);
            }
        }

        System.out.println();
    }
}
