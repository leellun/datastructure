package com.newland.algorithm.tree.redtree;

import javax.swing.tree.TreeNode;
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
                    if (minNode.isBlack()) {
                        this.root = fixRemove(this.root, minNode.left);
                    }
                } else {
                    minNode.right.setParent(minNode.parent);
                    if (minNode.parent != null) {//放置minNode为root的null异常
                        minNode.parent.setLeft(minNode.right);
                    }
                    if (minNode.isBlack()) {
                        this.root = fixRemove(this.root, minNode.right);
                    }
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
            this.root = fixInsert(this.root, node);
        }
        return node.value;
    }

    /**
     * 删除操作平衡
     *
     * @param node
     */
    private RBTreeNode<T> fixRemove(RBTreeNode<T> root, RBTreeNode<T> node) {
        while (true) {
            if (node == null || node == root) {//当前node定位到root
                return root;
            }
            RBTreeNode<T> parent = node.parent;
            if (parent == null) {//定位node父节点为null，则为root节点
                node.makeBlack();
                return node;
            } else if (node.isRed()) {
                //定位节点为红色，重置定位节点为黑色，因为进行删除的节点在定位节点或子树
                //经过多次旋转，黑色节点的层级会变成之前的 n-1，所以设置定位节点为黑色
                node.makeBlack();
                return root;
            }
            RBTreeNode<T> left = parent.left;
            RBTreeNode<T> right = parent.right;
            //如果定位节点左子树
            if (left == node) {
                //如果右节点为红色，则父节点为黑色，那么设置父节点红色、右节点黑色，然后左旋转平衡定位节点
                if (right != null && right.isRed()) {
                    right.makeBlack();
                    parent.makeRed();
                    //左旋转
                    root = rotateLeft(root, parent);
                    //左旋转后 parent的右节点变为之前右节点的左节点，需要重新赋值
                    right = parent.right;
                }
                //如果右节点为空，定位节点上移一层
                if (right == null) {
                    node = parent;
                } else {
                    //兄弟节点的左节点和右节点
                    RBTreeNode<T> siblingLeft = right.left, sibingRight = right.right;
                    //如果兄弟节点的左右节点都为黑色则定位节点重置为当前定位节点的上一层，即parent
                    //黑 嘿嘿  、红嘿嘿
                    if (isBlack(siblingLeft) && isBlack(sibingRight)) {
                        right.makeRed();
                        node = parent;
                    } else {
                        //如果兄弟节点的左节点是红色右节点是黑色
                        //需要将左节点设置
                        if (isBlack(sibingRight)) {
                            //这里是右节点的左节点是否为空
                            //注意：这里是jdk的源码逻辑，个人认为，这里不可能为空
                            if (siblingLeft != null) {
                                //由红变黑
                                siblingLeft.makeBlack();
                            }
                            // 右节点设置为红色
                            right.makeRed();
                            //因为siblingLeft设置了黑色，所以需要右旋转平衡right子树
                            root = rotateRight(root, right);
                            //重新获取right
                            right = parent.right;
                            //
                            siblingLeft = right.left;
                            sibingRight = right.right;
                        }
                        //right：黑  sl：黑 sr:红
                        //right：黑  sl：红 sr：黑=》right：黑 sl：未知 sr：红
                        // 通过分析 sibingRight在这里不为空一定是红
                        if (right != null) {
                            if (parent == null) {
                                right.makeBlack();
                            } else {
                                right.red = parent.red;
                            }
                            //将右子树设置为黑色
                            if (sibingRight != null) {
                                sibingRight.makeBlack();
                            }
                        }
                        if (parent != null) {//以为需要删除一个定位点为黑色的节点所以需要左旋转平衡节点
                            parent.makeBlack();
                            root = rotateLeft(root, parent);
                        }
                        node = root;
                    }
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
//            //if 兄弟节点的 子节点全是black
//            //else
//            // if 兄弟节点的右节点black 如果兄弟节点左节点存在black
//            //   兄弟节点red 右旋转  兄弟节点的右节点=
//
//        }
//    }

    private RBTreeNode<T> fixInsert(RBTreeNode<T> root, RBTreeNode<T> node) {
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
                        root = rotateLeft(root, parent);
                    }
                    root = rotateRight(root, ancestor);
                    if (isRight) {
                        node.makeBlack();
                    } else {
                        parent.makeBlack();
                    }
                } else {
                    boolean isLeft = node == parent.left;
                    if (isLeft) {
                        root = rotateRight(root, parent);
                    }
                    root = rotateLeft(root, ancestor);
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
        return root;
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
    private RBTreeNode<T> rotateLeft(RBTreeNode<T> root, RBTreeNode<T> node) {
        RBTreeNode<T> right = node.right;
        RBTreeNode<T> parent = node.parent;
        if (parent == null) {
            root = right;
            root.makeBlack();
        } else {
            parent.right = right;
        }
        node.right = right.left;
        node.parent = right;
        right.parent = parent;
        right.left = node;
        return root;
    }

    /**
     * 右旋转   右下
     */
    private RBTreeNode<T> rotateRight(RBTreeNode<T> root, RBTreeNode<T> node) {
        RBTreeNode<T> left = node.left;
        RBTreeNode<T> parent = node.parent;
        if (parent == null) {
            root = left;
            root.makeBlack();
        } else {
            parent.left = left;
        }
        node.left = left.right;
        node.parent = left;
        left.parent = parent;
        left.right = node;
        return root;
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

    private boolean isBlack(RBTreeNode<T> node) {
        return node == null || node.isBlack();
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
