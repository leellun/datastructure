package com.newland.algorithm.tree.avl;

public class AVLNode<T extends Comparable> {
    public AVLNode<T> left;
    public AVLNode<T> right;
    public T data;
    public int height;

    public AVLNode(T value) {
        this.data = value;
    }
}
