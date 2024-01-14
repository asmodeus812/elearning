package com.cci.examples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreesAndGraphs {

    public enum NodePrintOrder {
        PRE, POST, IN
    }

    public static class NamedNode {
        protected String id;
    }

    public static class ValueNode<T> extends NamedNode {
        protected T value;
    }

    public static final class GenericNode<T> extends ValueNode<T> {
        private GenericNode<T>[] children;
    }

    public static final class BinaryNode<T> extends ValueNode<T> {
        private BinaryNode<T> left;
        private BinaryNode<T> right;
    }

    public static final class BinarySearchTree<T extends Comparable<T>> {

        public BinaryNode<T> create(List<T> items) {
            BinaryNode<T> root = new BinaryNode<>();
            Queue<BinaryNode<T>> nodes = new LinkedList<>();

            nodes.add(root);
            for (T item : items) {
                BinaryNode<T> node = nodes.remove();
                node.value = item;

                node.left = new BinaryNode<>();
                node.right = new BinaryNode<>();

                nodes.add(node.left);
                nodes.add(node.right);
            }
            return root;
        }

        public int height(BinaryNode<T> root) {
            if (root == null) {
                // when the node is invalid there is no meaningful height value to return for it, but zero
                return 0;
            }
            if (root.left == null && root.right == null) {
                // when the node is a leaf, the height could be only one, the height is the node node itself
                return 1;
            }
            // when the node has children, the height is the current height, one, plus the max height between the left or right subtrees of
            // the current node
            return 1 + Math.max(height(root.left), height(root.right));
        }

        public BinaryNode<T> max(BinaryNode<T> root) {
            // to find the max node, simply drill down the right subtree of the current node, until there is no more right nodes left
            while (root != null && root.right != null) {
                root = root.right;
            }
            return root;
        }

        public BinaryNode<T> min(BinaryNode<T> root) {
            // to find the min node, simply drill down the left subtree of the current node, until there is no more left nodes left
            while (root != null && root.left != null) {
                root = root.left;
            }
            return root;
        }

        public BinaryNode<T> insert(BinaryNode<T> root, T value) {
            if (root == null) {
                // if we drill down till this point it means that the correct spot for the new element was found, and we create the node and
                // return it, in the assignments below that node will be assigned correctly to it's parent node, in other words, the
                // recursive calls below took right/left paths such that they lead us to a null child, which is the spot at which this new
                // node must be linked, returning it here, and assignment below will ensure that linkage is made to the parent of this new
                // node, to either the right or the left child.
                BinaryNode<T> node = new BinaryNode<>();
                node.value = value;
                return node;
            }

            if (value.compareTo(root.value) > 0) {
                // we have to go down to the right, this recursive call will either return the same node, if the value is equal to
                // root.right, or will drill down until there is not root.right anymore, at which point the node will be created with the
                // new element and returned back
                root.right = insert(root.right, value);
                // early exit, if we take this route, the root was not equal, it is important to exit here
                return root;
            } else if (value.compareTo(root.value) < 0) {
                // we have to go down to the left, this recursive call will either return the same node, if the value is equal to
                // root.left, or will drill down until there is not root.left anymore, at which point the node will be created with the
                // new element and returned back
                root.left = insert(root.left, value);
                // early exit, if we take this route, the root was not equal, it is important to exit here
                return root;
            }

            // default return the node, this return here means that the current's / root's value was equal, it would stop doing recursive
            // calls and return and unwind the call stack
            return root;
        }

        public BinaryNode<T> delete(BinaryNode<T> root, T value) {
            if (root == null) {
                // drilling down did not find the element, simply abort the deletion, at this point we have reached the end of the possible
                // path in the tree and the element was not present, the recursion will unwind the call stack till the top
                return null;
            }

            if (value.compareTo(root.value) > 0) {
                // drill down the right subtree until we find equal element, note the assignment, this allows us to propagate changes to
                // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
                // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
                // keep track of parent nodes.
                root.right = delete(root.right, value);
                // early exit, if we take this route, the root was not equal, it is important to exit here
                return root;
            } else if (value.compareTo(root.value) < 0) {
                // drill down the left subtree until we find equal element, note the assignment, this allows us to propagate changes to
                // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
                // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
                // keep track of parent nodes.
                root.left = delete(root.left, value);
                // early exit, if we take this route, the root was not equal, it is important to exit here
                return root;
            }

            // at this point the root would point at an element with value which must be equal to the target value, why ?, we will be here
            // only if the value is equal to the root, any other case will either exit, if node == null, we have drilled down to the bottom
            // of the path, if value > root.value or value < root.value then we will keep recursing, therefore we reach this point only when
            // v == root.value
            if (root.left == null && root.right == null) {
                // the root with the value has no children, therefore, we return null, this return is very crucial, it will return to the
                // previous recursive call where the root will reference the parent of the current root node, therefore without using any
                // parent pointers in the tree, we will correctly assign to the left or right of the parent of this root, null, detaching
                // the current node that was found to equal the value
                return null;
            } else if (root.left != null && root.right == null) {
                // the same as above, but instead, we know that we have only left subtree, therefore we link the left subtree of the current
                // root, which equals the target value, to the parent of the current root, this will essentially attach the current root's
                // left subtree to the left or right subtree, based on off of where we came, to the parent of the current root / node
                return root.left;
            } else if (root.right != null && root.left == null) {
                // the same as above, but instead, we know that we have only right subtree, therefore we link the right subtree of the
                // current
                // root, which equals the target value, to the parent of the current root, this will essentially attach the current root's
                // right subtree to the right or right subtree, based on off of where we came, to the parent of the current root / node
                return root.right;
            } else {
                // we have both left and right subtrees for the current root for which the value is found to be equal, what we do here, two
                // options are available
                // - find the min element from the left subtree
                // - find the max eleemnt from the right subtree
                BinaryNode<T> curr = root.left;
                BinaryNode<T> prev = root;

                // we choose to find the max element from the left subtree, that is done by starting off from the root.left, and drilling
                // down to the right, we can choose the other option find the min element in the right subtree it does not matter, it has
                // some effect on how we do the checks inside the ifs below though if we go that route, but they are symmetric
                while (curr != null && curr.right != null) {
                    prev = curr;
                    curr = curr.right;
                }

                if (prev == root) {
                    // we have not moved at all, the while did not loop, meaning that there is no right nodes to the left or root, meaning
                    // that the prev will point to root, (what it was initially set to), therefore we can safely detach curr node, by
                    // assigning it's left subtree to the prev's left subtree, effectively detaching it from prev, because prev.left was so
                    // far pointing at curr (see initialization above)
                    prev.left = curr.left;
                } else {
                    // we have drilled down to the right, meaning that curr must be the max node, that node can possibly have a left subtree
                    // (with nodes smaller than it), what we do to detach it, assign to it's parent / previous node's right subtree the left
                    // subtree of the current's left subtree (this will detach curr itself, without loosing curr's links to it's child
                    // nodes) if it has any it will be correctly re-linked (but it has no right one that is for sure, since we drill down to
                    // that condition, see while loop above)
                    prev.right = curr.left;
                }

                // copy the value of the curr node over to the root, the curr node will be the one which replaces the root's value, since
                // the root's value is the one we want to delete in the first place
                root.value = curr.value;
            }
            return root;
        }

        public void print(BinaryNode<T> node, NodePrintOrder order) {
            if (node == null) {
                return;
            }
            System.out.println(node.value);
            print(node.left, order);
            print(node.right, order);
        }

        public static String padRight(String s, int n) {
            return String.format("%-" + n + "s", s);
        }

        public static String padLeft(String s, int n) {
            return String.format("%" + n + "s", s);
        }

        // public void print(BinaryNode<T> node) {
        // return;
        // // Queue<BinaryNode<T>> nodes = new LinkedList();
        // // List<T> values = new ArrayList<>();
        // // nodes.add(node);
        // //
        // // while (!nodes.isEmpty()) {
        // // // int count = (int) Math.pow(2, level);
        // // // BinaryNode<T> current =
        // // // while (count > 0) {
        // // //
        // // // count--;
        // // // }
        // //
        // // BinaryNode<T> current = nodes.remove();
        // // values.add(Objects.isNull(current.value) ? -1 : current.value);
        // //
        // // nodes.add(current.left);
        // // nodes.add(current.right);
        // // }
        // //
        // //
        // // // ```txt
        // // // 8
        // // // 5 1
        // // // 1 2 8 3
        // // // 0 080 0
        // // // 050 010
        // // // 1 2 8 3
        // // //
        // // // 0 0 0 010 0 0 0
        // // // 0 010 0 0 020 0
        // // // 010 020 030 040
        // // // 1 2 3 4 5 6 7 8
        // // // 10
        // //
        // // // / \
        // // // 5 20
        // // // / \
        // // // 3 6
        // // // ```
        // //
        // // // int
        // //
        // // int levels = (int) (Math.log(nodes.size()) / Math.log(2));
        // //
        // // int level = 0;
        // // int offset = 0;
        // // while (offset < values.size()) {
        // // for (int i = 0; i < offset; i++) {
        // // Integer value = values.get(i);
        // // int cells = (int) Math.pow(2, levels - level) - 1;
        // // int pad = cells - 1;
        // //
        // // String padded = padLeft(value.toString(), pad / 2);
        // // padded = padRight(padded, pad / 2);
        // // System.out.print(padded);
        // // }
        // //
        // // level++;
        // // offset = (int) Math.pow(2, level) - 1;
        // // }
        // }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        List<Integer> array = Arrays.asList(5, 1, 3, 8, 0, 2, 9, 4);
        BinaryNode<Integer> node = tree.create(array);
        // tree.print()
        // simple.printSimpleTree(node);
        // simple.print(node);

        return;
    }
}
