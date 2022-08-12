package com.newland.algorithm.tree.redtree;

public class RBTreeNode<T extends Comparable> {
    public RBTreeNode<T> parent;
    public RBTreeNode<T> left;
    public RBTreeNode<T> right;
    public T value;
    public boolean red;

    public RBTreeNode(T value) {
        this.value = value;
    }

    public void setParent(RBTreeNode<T> parent) {
        this.parent = parent;
    }

    public void setRight(RBTreeNode<T> right) {
        this.right = right;
    }

    public void setLeft(RBTreeNode<T> left) {
        this.left = left;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void makeRed() {
        red = true;
    }

    public void makeBlack() {
        red = false;
    }
}
