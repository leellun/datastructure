package com.newland.algorithm.tree.redtree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 红黑树这个是本人自己写的，按照https://pansong291.gitee.io/web/html/other/RedBlackTree.html 这个跳动进行的
 * 添加操作打破红黑树的触发条件：父节点是红
 * 添加操作需要旋转的判定：
 * （1）uncle节点是null
 * （2）uncle节点是black
 *
 * @param <T>
 */
public class RBTree<T extends Comparable> {
    private RBTreeNode<T> root;

    public T addNode(T value) {
        RBTreeNode<T> node = new RBTreeNode<>(value);
        node.makeRed();
        return addNode(node);
    }

    public void removeNode(T value) {
        if (root == null) return;
        RBTreeNode<T> currentNode = root;
        while (currentNode != null) {
            int cmp = currentNode.value.compareTo(value);
            if (cmp < 0) {
                currentNode = currentNode.left;
            } else if (cmp > 0) {
                currentNode = currentNode.right;
            } else {
                RBTreeNode<T> minNode = findMinData(currentNode);
                currentNode.setValue(minNode.value);
                if (minNode.left != null) {
                    minNode.left.setParent(minNode.parent);
                    if (minNode.parent != null) {//放置minNode为root的null异常
                        minNode.parent.setLeft(minNode.left);
                    }
                    fixRemove(minNode.left);
                }
                //移除minnode
                minNode.setLeft(null);
                minNode.setParent(null);
            }
        }
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

    /**
     * 删除操作平衡
     *
     * @param node
     */
    private void fixRemove(RBTreeNode<T> node) {
        while (true) {
            if (node == null || node == root) {
                return;
            }
            RBTreeNode<T> parent = node.parent;
            if (parent == null) {
                node.makeBlack();
                return;
            } else if (node.isRed()) {
                node.makeBlack();
                return;
            }
            RBTreeNode<T> left = parent.left;
            RBTreeNode<T> right = parent.right;
            if (left == node) {
                if (right != null && right.isRed()) {
                    right.makeBlack();
                    parent.makeRed();
                    rotateLeft(parent);
                }
                if (right == null) {
                    node = parent;
                }else{
                }
            } else {

            }

        }
    }
//    void balanceDeletion(BTreeNode<T> node){
//        for(){
//            //if当前节点是否是更节点
//            //else if 当前节点的父节点是否是空节点
//            //else if 当前节点是否是红色
//            //else if 当前节点是左子树
//            //if 当前节点的兄弟节点红色
//            //if 当前节点兄弟节点为空 循环父节点
//            //else
//            //if 兄弟节点的 子节点是black
//            //else
//            // if 兄弟节点的右节点black 如果兄弟节点左节点存在black
//            //   兄弟节点red 右旋转  兄弟节点的右节点=
//
//        }


//    }

    private void fixInsert(RBTreeNode<T> node) {
        RBTreeNode<T> parent = node.parent;
        while (parent != null && parent.isRed()) {
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

    /**
     * 找到node 找到最大的点
     * 该红黑树算法，删除一个node，则
     *
     * @param node
     * @return
     */
    private RBTreeNode<T> findMinData(RBTreeNode<T> node) {
        RBTreeNode<T> findNode = node;
        RBTreeNode<T> minNode = node.left;

        while (minNode != null) {
            findNode = minNode;
            minNode = minNode.right;
        }
        return findNode;
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
