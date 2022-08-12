package com.newland.algorithm.tree.basic;

/**
 * 基本二叉树
 */
public class BasicTree {
    private Node root;

    public static void main(String[] args) {
        BasicTree treeNode = new BasicTree();
        int[] array = new int[]{57, 68, 59, 52, 72, 28, 96, 33, 24, 19};
        for (int i = 0; i < array.length; i++) {
            treeNode.addNode(array[i]);
        }
        for (int i = 0; i < array.length; i++) {
            treeNode.removeNode(array[i]);
        }
        System.out.println(treeNode);
    }

    public void addNode(Integer value) {
        Node node = new Node(value);
        if (root == null) {
            root = node;
        } else {
            Node n = root;
            while (true) {
                if (node.value < n.value) {
                    if (n.left == null) {
                        n.left = node;
                        node.parent = n;
                        break;
                    }
                    n = n.left;
                } else {
                    if (n.right == null) {
                        n.right = node;
                        node.parent = n;
                        break;
                    }
                    n = n.right;
                }
            }
        }
    }

    public void removeNode(Integer value) {
        Node n = root;
        while (n != null) {
            if (n.value == value) {
                if (n.left != null && n.right != null) {
                    Node replaceNode = findMinData(n.right);
                    n.value = replaceNode.value;
                    Node p = replaceNode.parent;
                    if (p == null) {
                        System.out.println();
                    }

                    if (replaceNode == p.right) {
                        p.setRight(replaceNode.right);
                    } else {
                        p.setLeft(replaceNode.right);
                    }
                } else {
                    if (root == n) {
                        root = null;
                    } else {
                        Node p = n.parent;
                        n.parent = null;
                        if (p.left == n) {
                            p.setLeft(n.left == null ? n.right : n.left);
                        } else {
                            p.setRight(n.left == null ? n.right : n.left);
                        }
                    }
                }
                break;
            }
            if (value < n.value) {
                n = n.left;
            } else {
                n = n.right;
            }
        }
    }

    /**
     * 找到右子树最小的节点
     *
     * @param node
     * @return
     */
    private Node findMinData(Node node) {
        return node.left == null ? node : findMinData(node.left);
    }

    private static class Node {
        public Node left;
        public Node right;
        public Node parent;
        public Integer value;

        public void setLeft(Node left) {
            this.left = left;
            if(this.left!=null){
                this.left.parent = this;
            }
        }

        public void setRight(Node right) {
            this.right = right;
            if(this.right!=null){
                this.right.parent = this;
            }
        }

        public Node(Integer value) {
            this.value = value;
        }
    }
}
