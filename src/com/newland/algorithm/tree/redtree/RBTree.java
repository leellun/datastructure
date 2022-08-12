package com.newland.algorithm.tree.redtree;

public class RBTree<T extends Comparable> {
    private RBTreeNode<T> root;

    public void addNode(T value) {
        RBTreeNode<T> node = new RBTreeNode<>(value);
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
            if (uncle == null) {
                
            } else {
                parent.makeBlack();
                uncle.makeBlack();
                parent.parent.makeRed();
                node = parent;
                parent = parent.parent;
            }
        }
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
}
