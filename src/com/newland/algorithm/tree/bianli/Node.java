package com.newland.algorithm.tree.bianli;

public class Node {
    Object value;
    Node left;
    Node right;

    public Node(Object value) {
        this.value = value;
    }

    public Node(Object value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}
