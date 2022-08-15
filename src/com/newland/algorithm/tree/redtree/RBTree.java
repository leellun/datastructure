package com.newland.algorithm.tree.redtree;

import java.util.Deque;
import java.util.LinkedList;

public class RBTree<T extends Comparable> {
    private RBTreeNode<T> root;

    public void addNode(T value) {
        RBTreeNode<T> node = new RBTreeNode<>(value);
        node.makeRed();
        addNode(node);
    }

    private T addNode(RBTreeNode<T> node) {
        if (root == null) {
            node.makeBlack();
            root = node;
        } else {
            RBTreeNode<T> p = findParentNode(node);
            int result = node.value.compareTo(p.value);
            if (result == 0) {
                return node.value;
            } else if (result > 0) {
                p.setRight(node);
            } else {
                p.setLeft(node);
            }
            node.setParent(p);
            fixInsert(node);
        }
        return node.value;
    }

    private void fixInsert(RBTreeNode<T> node) {
        RBTreeNode<T> parent = node.parent;
        while (parent != null && parent.red) {
            RBTreeNode<T> uncle = getUncle(node);
            RBTreeNode<T> ancestor = parent.parent;
            if (ancestor == null) {
                break;
            }
            if (uncle == null || uncle.isBlack()) {//ancestor节点则为黑色，所以需要进行旋转才能平衡红黑
                if (parent == ancestor.left) {
                    //非直线的三点
                    boolean isRight = node == parent.right;
                    if (isRight) {
                        rotateLeft(parent);
                    }
                    rotateRight(ancestor);
                    if (isRight) {
                        node.makeBlack();
                    } else {
                        parent.makeBlack();
                    }
                } else {
                    boolean isLeft = node == parent.left;
                    if (isLeft) {
                        rotateRight(parent);
                    }
                    rotateLeft(ancestor);
                    if (isLeft) {
                        node.makeBlack();
                    } else {
                        parent.makeBlack();
                    }
                }
                if (uncle == null) {
                    ancestor.makeRed();
                    break;
                } else {
                    uncle.parent.makeRed();
                }
            } else {//uncle 是红色 因为父节点是红色所以uncle一定是红色
                parent.makeBlack();
                uncle.makeBlack();
                ancestor.makeRed();
                node = ancestor;
                parent = node.parent;
            }
        }
        root.makeBlack();
        root.parent = null;
    }

    private RBTreeNode<T> getUncle(RBTreeNode<T> node) {
        RBTreeNode<T> parent = node.parent;
        RBTreeNode<T> ancestor = parent.parent;
        if (ancestor == null) return null;
        if (parent == ancestor.left) {
            return ancestor.right;
        } else {
            return ancestor.left;
        }
    }

    /**
     * 左旋转   左下
     */
    private void rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> child = node.right;
        RBTreeNode<T> parent = node.parent;
        if (parent == null) {
            this.root = child;
        } else {
            parent.right = child;
        }
        node.right = child.left;
        node.parent = child;
        child.parent = parent;
        child.left = node;
    }

    /**
     * 右旋转   右下
     */
    private void rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> child = node.left;
        RBTreeNode<T> parent = node.parent;
        if (parent == null) {
            this.root = child;
        } else {
            parent.left = child;
        }
        node.left = child.right;
        node.parent = child;
        child.parent = parent;
        child.right = node;
    }

    /**
     * 通过value查找应该的父节点
     *
     * @param node
     * @return
     */
    private RBTreeNode<T> findParentNode(RBTreeNode<T> node) {
        RBTreeNode<T> currentNode = root;
        while (currentNode != null) {
            int result = node.value.compareTo(currentNode.value);
            if (result > 0) {
                if (currentNode.right == null) {
                    return currentNode;
                }
                currentNode = currentNode.right;
            } else {
                if (currentNode.left == null) {
                    return currentNode;
                }
                currentNode = currentNode.left;
            }
        }
        return root;
    }

    public void preOrderByStack() {
        Deque<RBTreeNode> queue = new LinkedList<>();
        RBTreeNode currentNode = root;
        while (currentNode != null || !queue.isEmpty()) {
            RBTreeNode temp = currentNode;
            while (temp != null) {
                queue.push(temp);
                System.out.print(temp.value + "(" + temp.isRed() + ")-");
                temp = temp.left;
            }
            RBTreeNode top = queue.poll();
            currentNode = top.right;
        }
    }

    public static void main(String[] args) {
        RBTree<Integer> brtree = new RBTree<>();
        for (int i = 10; i >= 1; i--) {
            if (i == 1) {
                System.out.println();
            }
            brtree.addNode(i);
            brtree.preOrderByStack();
            System.out.println();
        }
    }
}
