package com.newland.algorithm.tree;

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
