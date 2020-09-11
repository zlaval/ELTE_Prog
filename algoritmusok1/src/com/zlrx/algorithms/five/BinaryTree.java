package com.zlrx.algorithms.five;

public class BinaryTree {

    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    Node buildTree() {
        return new Node(
                1,
                new Node(
                        2,
                        new Node(4, new Node(8, null, null), null),
                        new Node(5, null, new Node(9, null, null))
                ),
                new Node(3,
                        new Node(6, null, null),
                        new Node(7, new Node(10, null, null), null)

                )
        );
    }

    public static void main(String[] args) {
        BinaryTree b = new BinaryTree();
       Node root = b.buildTree();
//        b.preOrder(root);
//        System.out.println();
//        b.inOrder(root);
//        System.out.println();
//        b.postOrder(root);
        b.findMin(root);
    }

    public void findMin(Node root) {
        int result = -1;
        if (root != null) {
            result = findMin(root, 0, Integer.MAX_VALUE);
        }
        System.out.println(result);
    }

    private int findMin(Node root, int level, int minLevel) {
        System.out.print(level+" - ");
        System.out.print(root == null ? "NIL" : root.value);
        System.out.println();
        if (root == null || level >= minLevel) {
            return minLevel;
        }
        if (root.left == null && root.right == null) {
            if (minLevel > level) {
                minLevel = level;
            }
            return minLevel;
        } else {
            return Math.min(findMin(root.left, level + 1, minLevel), findMin(root.right, level + 1, minLevel));
        }
    }

    public void preOrder(Node tree) {
        if (tree == null) return;
        System.out.println(tree.value + ", ");
        preOrder(tree.left);
        preOrder(tree.right);
    }

    public void inOrder(Node tree) {
        if (tree == null) return;
        inOrder(tree.left);
        System.out.println(tree.value + ", ");
        inOrder(tree.right);
    }

    public void postOrder(Node tree) {
        if (tree == null) return;
        postOrder(tree.left);
        postOrder(tree.right);
        System.out.println(tree.value + ", ");
    }

}
