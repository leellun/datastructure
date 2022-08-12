package com.newland.algorithm.tree.avl;

/**
 * 平衡二叉树
 * @param <T>
 */
public class AVLTree<T extends Comparable> {
    private AVLNode<T> root;

    public void insert(T value) {
        if (value == null) {
            throw new RuntimeException("value can not null");
        }
        this.root = insert(value, root);
    }

    private AVLNode insert(T value, AVLNode p) {
        if (p == null) {
            p = new AVLNode(value);
        }
        int result = value.compareTo(p.data);
        if (result < 0) {
            p.left = insert(value, p.left);
            if (height(p.left) - height(p.right) > 1) {
                //异常点 不在一条直线上
                if (p.left.left == null) {
                    p.left = singleRotateLeft(p.left);
                }
                p = singleRotateRight(p);
            }
        } else if (result > 0) {
            p.right = insert(value, p.right);
            if (height(p.right) - height(p.left) > 1) {
                //情况一：说明非直线的三个节点，需要先矫正，再进行左旋转
                //判断条件
                // （1）p.right.right==null 说明新添加的节点在右子节点的左节点
                //  (2) 也可以通过数据来判断 value.compareTo(p.right.data)<0
                if (p.right.right == null) {
                    p.right = singleRotateRight(p.right);

                }
                p = singleRotateLeft(p);
            }
        }
        p.height = Math.max(height(p.left), height(p.right)) + 1;
        return p;
    }

    public void remove(T value) {
        this.root = remove(value, root);
    }

    /**
     * 思想：
     * 第一步：找到value的对应节点node1
     * 第二部：找到value对应节点右子树最小的节点node2
     * 第三部：将node1的值替换为node2，node2的值替换为node1
     * 第四部：递归判断是否有异常点
     *
     * @param value
     * @param p
     * @return
     */
    public AVLNode remove(T value, AVLNode<T> p) {

        int result = value.compareTo(p.data);
        if (result < 0) {
            p.left = remove(value, p.left);
            if (height(p.left) - height(p.right) > 1) {
                //异常点 不在一条直线上
                if (p.left.left == null) {
                    p.left = singleRotateLeft(p.left);
                }
                p = singleRotateRight(p);
            }
        } else if (result > 0) {
            p.right = remove(value, p.right);
            if (height(p.right) - height(p.left) > 1) {
                //情况一：说明非直线的三个节点，需要先矫正，再进行左旋转
                //判断条件
                // （1）p.right.right==null 说明新添加的节点在右子节点的左节点
                //  (2) 也可以通过数据来判断 value.compareTo(p.right.data)<0
                if (p.right.right == null) {
                    p.right = singleRotateRight(p.right);

                }
                p = singleRotateLeft(p);
            }
        } else {
            //如果
            if (p.left != null && p.right != null) {
                AVLNode<T> minNode = findMinData(p.right);
                p.data = minNode.data;
                p.right = remove(minNode.data, p.right);
            } else {
                p = p.left != null ? p.left : p.right;
            }
        }

        return p;
    }

    /**
     * 找到右子树最小的节点
     *
     * @param avlNode
     * @return
     */
    private AVLNode<T> findMinData(AVLNode<T> avlNode) {
        return avlNode.left == null ? avlNode : findMinData(avlNode.left);
    }

    /**
     * 左旋转
     * 所谓的左旋转指的是把 节点 node放置在当前位置的左边，旋转都是以当前node为准
     *
     * @param node
     * @return
     */
    private AVLNode singleRotateLeft(AVLNode node) {
        AVLNode right = node.right;
        AVLNode newTop = right;
        node.right = newTop.left;
        newTop.left = node;
        node.height = Math.max(height(node.left), height(node.right));
        newTop.height = Math.max(height(newTop.left), height(newTop.right));
        return newTop;
    }

    /**
     * 右旋转
     * 所谓的右旋转就是把当前node节点放置在当前位置的右边，旋转都是以当前node为准
     *
     * @param node
     * @return
     */
    private AVLNode singleRotateRight(AVLNode node) {
        AVLNode left = node.left;
        AVLNode newTop = left;
        node.left = newTop.right;
        newTop.right = node;
        node.height = Math.max(height(node.left), height(node.right));
        newTop.height = Math.max(height(newTop.left), height(newTop.right));
        return newTop;
    }

    private int height(AVLNode node) {
        return node == null ? -1 : node.height;
    }
}
