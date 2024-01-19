package com.cci.examples;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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

    public static class GenericNode<T> extends ValueNode<T> {
        protected GenericNode<T>[] children;
    }

    public static class GenericHeightNode<T> extends ValueNode<T> {
        protected int height = 0;
        protected GenericNode<T>[] children;
    }

    public static class BinaryNode<T> extends ValueNode<T> {
        protected BinaryNode<T> left;
        protected BinaryNode<T> right;
    }

    public static class BinaryHeightNode<T> extends ValueNode<T> {
        protected int height = 0;
        protected BinaryHeightNode<T> left;
        protected BinaryHeightNode<T> right;
    }

    public static class BinarySearchTree<T extends Comparable<T>> {

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
            } else if (value.compareTo(root.value) < 0) {
                // we have to go down to the left, this recursive call will either return the same node, if the value is equal to
                // root.left, or will drill down until there is not root.left anymore, at which point the node will be created with the
                // new element and returned back
                root.left = insert(root.left, value);
            }
            // return the node always, we only recurse inside the if/else, this case here also handlers equals, but in all 3 cases we want
            // to return
            // the original node anyway
            return root;
        }

        public BinaryNode<T> delete(BinaryNode<T> root, T value) {
            if (root == null) {
                // drilling down did not find the element, simply abort the deletion, at this point we have reached the end of the possible
                // path in the tree and the element was not present, the recursion will unwind the call stack till the top, returning
                // the nodes unmodified
                return null;
            }

            if (value.compareTo(root.value) > 0) {
                // drill down the right subtree until we find equal element, note the assignment, this allows us to propagate changes to
                // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
                // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
                // keep track of parent nodes.
                root.right = delete(root.right, value);
            } else if (value.compareTo(root.value) < 0) {
                // drill down the left subtree until we find equal element, note the assignment, this allows us to propagate changes to
                // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
                // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
                // keep track of parent nodes.
                root.left = delete(root.left, value);
            } else {
                // at this point the root would point at an element with value which must be equal to the target value, why ?, we will be
                // here only if the value is equal to the root, any other case will either exit, if node == null, we have drilled down to
                // the bottom of the path, if value > root.value or value < root.value then we will keep recursing, therefore we reach this
                // point only when value equals root.value
                if (root.left == null && root.right == null) {
                    // the root with the value has no children, therefore, we return null, this return is very crucial, it will return to
                    // the previous recursive call where the root will reference the parent of the current root node, therefore without
                    // using any parent pointers in the tree, we will correctly assign to the left or right of the parent of this root,
                    // null, detaching the current node that was found to equal the value
                    return null;
                } else if (root.left != null && root.right == null) {
                    // the same as above, but instead, we know that we have only left subtree, therefore we link the left subtree of the
                    // current root, which equals the target value, to the parent of the current root, this will essentially attach the
                    // current root's left subtree to the left or right subtree, based on off of where we came, to the parent of the current
                    // root / node
                    return root.left;
                } else if (root.right != null && root.left == null) {
                    // the same as above, but instead, we know that we have only right subtree, therefore we link the right subtree of the
                    // current root, which equals the target value, to the parent of the current root, this will essentially attach the
                    // current root's right subtree to the right or right subtree, based on off of where we came, to the parent of the
                    // current root / node
                    return root.right;
                } else {
                    // we have both left and right subtrees for the current root for which the value is found to be equal, what we do here,
                    // two options are available, in that scenario, both equal, and either one is fine
                    // - find the min element from the left subtree
                    // - find the max eleemnt from the right subtree
                    BinaryNode<T> curr = root.left;
                    BinaryNode<T> prev = root;

                    // we choose to find the max element from the left subtree, that is done by starting off from the root.left, and
                    // drilling down to the right, we can choose the other option find the min element in the right subtree it does not
                    // matter, it has some effect on how we do the checks inside the ifs below though if we go that route, but they are
                    // symmetric
                    while (curr != null && curr.right != null) {
                        // prev will always move one step behind curr, and prev.right will always point at curr at any point inside or
                        // outside this loop, we need to know prev, to detach curr from it, and attach the curr's children to prev instead
                        prev = curr;
                        curr = curr.right;
                    }

                    if (prev == root) {
                        // we have not moved at all, the while did not loop, meaning that there is no right nodes to the left or root,
                        // meaning that the prev will point to root, (what it was initially set to), therefore we can safely detach curr
                        // node, by assigning it's left subtree to the prev (which still points at root) left subtree, effectively detaching
                        // it from prev, because root.left == prev.left == curr, so far, no longer after we link root.left == prev.left to
                        // curr.left
                        prev.left = curr.left;
                    } else {
                        // we have drilled down to the right, meaning that curr must be the max node, that node can possibly have a left
                        // subtree (with nodes smaller than it), what we do to detach it ? assign to it's parent/previous node's right
                        // subtree the left subtree of the current's left subtree (this will detach curr itself, without loosing curr's
                        // links to it's child nodes, because prev.right == curr) if it has any it will be correctly re-linked (but it has
                        // no right one that is for sure, since we drill down to that condition, see while loop above)
                        prev.right = curr.left;
                    }

                    // copy the value of the curr node over to the root, the curr node will be the one which replaces the root's value,
                    // since the root's value is the one we want to delete in the first place, but it had to be replaced with a correct
                    // value computed from it's children first. The curr.value will maintain the bst property, since we found the max value
                    // from the root.left subtree, and replaced the root.value with it, therefore the property for the subtrees of root, and
                    // their values are maintained
                    root.value = curr.value;
                }
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

    public static final class AvlBinarySearchTree<T extends Comparable<T>> {

        private BinaryHeightNode<T> right(BinaryHeightNode<T> root) {
            // right rotation is done to offset left heavy tree branches, right rotation means that left subtree would "move" or "shift" to
            // the right, or in other words the root.left would become the new root, and the old root would become the right, of the new
            // root. This way the subtrees of the new node would be balanced

            // remember the left node, it will become our new root at the end of the function the new root is returned
            BinaryHeightNode<T> left = root.left;

            // the left of the current root would become the right subtree of the new root i.e left.right node
            root.left = left.right;

            // the new root's right subtree now is assigned the old root
            left.right = root;

            // since root's subtrees were modified above, left was assigned a new subtree, calculate the height again
            root.height = 1 + Math.max(height(root.right), height(root.left));

            // since left's subtrees were modified above, right was assigned a new subtree, calculate the height again
            left.height = 1 + Math.max(height(left.right), height(left.left));

            // this is the new root, after the rotation was done, i.e the old .left subtree of root
            return left;
        }

        private BinaryHeightNode<T> left(BinaryHeightNode<T> root) {
            // left rotation is done to offset right heavy tree branches, left rotation means that right subtree would "move" or "shift" to
            // the left, or in other words the root.right would become the new root, and the old root would become the left of the new
            // root. This way the subtrees of the new node would be balanced

            // remember the right node, it will become our new root at the end of the function the new root is returned
            BinaryHeightNode<T> right = root.right;

            // the right of the current root would become the left subtree of the new root i.e right.left node
            root.right = right.left;

            // the new root's left subtree now is assigned the old root
            right.left = root;

            // since root's subtrees were modified above, right was assigned a new subtree, calculate the height again
            root.height = 1 + Math.max(height(root.right), height(root.left));

            // since right's subtrees were modified above, left was assigned a new subtree, calculate the height again
            right.height = 1 + Math.max(height(right.right), height(right.left));

            // this is the new root, after the rotation was done, i.e the old .right subtree of root
            return right;
        }

        private BinaryHeightNode<T> rebalance(BinaryHeightNode<T> root, T value) {
            // first make sure the current height is up to date, if re-balance was called, caller must expect that the height of the root
            // might have changed, during a tree action, insert or delete
            root.height = 1 + Math.max(height(root.right), height(root.left));

            // after the update of the height we check what is the current balance, between the current level's subtrees, for the current
            // level, i.e if the balance value is abs(balance) > 1, the tree needs to be rebalanced around the current root / level
            int balance = balance(root.right, root.left);

            // check if there is imbalance in the tree, it must be a value strictly greater, (not equal or greater) than one, does not
            // matter which subtree (left or right) is heavier. This post recursive action will find the very first level in the tree which
            // is imbalanced, from bottom to top.
            // - balance will be positive if the left side is heavier than the right
            // - balance will be negative if the right side is heavier than the left
            if (balance > 1 || balance < -1) {
                // the checks blelow are very neat and cool, why ?, well having the value to insert and the property of the binary search
                // tree, we can know in which direction the new node was inserted, combined with the sign of the balance we can tell in
                // which direction the new node imbalanced the tree, i.e which subtree became more heavy / imbalanced, this helps us to find
                // which of the 4 rotations have to be applied around the current root

                // left heavy && value < root.left.value - left-left
                if (balance > 1 && value.compareTo(root.left.value) < 0) {
                    // first start off from the balance value, if it is positive, then the imbalance is in root.left, then we check if the
                    // inserted value is smaller than the left node's value, if yes, then the new node was inserted to the left of the left
                    // subtree. meaning the tree is left-left heavy, therefore we do the (opposite rotation) right rotation
                    return right(root);
                }

                // right heavy && value > root.right.value - right-right
                if (balance < -1 && value.compareTo(root.right.value) > 0) {
                    // first start off from the balance value, if it is negative, then the imbalance is in root.right, then we check if the
                    // inserted value is bigger than the right node's value, if yes, then the new node was inserted to the right of the
                    // right subtree. meaning the tree is right-right heavy, therefore we do the (opposite rotation) left rotation
                    return left(root);
                }

                // left heavy && value > root.left.value - left-right
                if (balance > 1 && value.compareTo(root.left.value) > 0) {
                    // first start off from the balance value, if it is positive, then the imbalance is in root.left, then we check if the
                    // inserted value is bigger than the left node's value, if yes, then the new node was inserted to the right of the left
                    // subtree. meaning the tree is left-right heavy, therefore first we left rotate, which will morph root.left case into a
                    // left-left, after than we do a right rotate (same as left-left case above)
                    root.left = left(root.left);
                    return right(root);
                }

                // right heavy && value < root.right.value - right-left
                if (balance < -1 && value.compareTo(root.right.value) < 0) {
                    // first start off from the balance value, if it is negative, then the imbalance is in root.right, then we check if the
                    // inserted value is smaller than the right node's value, if yes, then the new node was inserted to the left of the
                    // right subtree. meaning the tree is right-left heavy, therefore first we right rotate, which will morph root.right
                    // case into a right-right, after than we do a left rotate (same as right-right case above)
                    root.right = right(root.right);
                    return left(root);
                }
            }
            return root;
        }

        public int height(BinaryHeightNode<T> root) {
            // height is 0 if the node does not exist
            if (root == null) {
                return 0;
            }
            // return the stored height, for the node
            return root.height;
        }

        public int balance(BinaryHeightNode<T> right, BinaryHeightNode<T> left) {
            // the balance calculated as signed integer from left to right, when a tree is imbalanced, it would tend to malform into a
            // structure very simlar to a linked list, it is quite obvious when either the left or right subtrees have a chain of links,
            // - node->left->left->left....etc, with no right links, along the path, left heavy, positive balance value
            // - node->right->right->right..etc, with no left links, along the path, right heavy, negative balance value
            return height(left) - height(right);
        }

        public BinaryHeightNode<T> insert(BinaryHeightNode<T> root, T value) {
            if (root == null) {
                // if we drill down till this point it means that the correct spot for the new element was found, and we create the node and
                // return it, in the assignments below that node will be assigned correctly to it's parent node, in other words, the
                // recursive calls below took right/left paths such that they lead us to a null child, which is the spot at which this new
                // node must be linked, returning it here, and assignment below will ensure that linkage is made to the parent of this new
                // node, to either the right or the left child.
                BinaryHeightNode<T> node = new BinaryHeightNode<>();
                node.value = value;
                node.height = 1;
                return node;
            }

            if (value.compareTo(root.value) > 0) {
                // we have to go down to the right, this recursive call will either return the same node, if the value is equal, since we
                // can not have duplicate elements in a bst to root.right, or will drill down until there is not root.right anymore, at
                // which point the node will be created with the new element and new element node returned back
                root.right = insert(root.right, value);
            } else if (value.compareTo(root.value) < 0) {
                // we have to go down to the left, this recursive call will either return the same node, if the value is equal since we can
                // not have duplicate elements in a bst to root.left, or will drill down until there is not root.left anymore, at which
                // point the node will be created with the new element and new element node returned back
                root.left = insert(root.left, value);
            } else {
                // we have found the root value to be equal here, there is nothing to do, we can not insert a duplicate element into the
                // tree, therefore we can simply return the root, and unwind the stack back to it's starting position, nothing to balance,
                // nothing to modfiy, the element would never be inserted and modfiy the tree in any way
                return root;
            }

            // reaching at this point, we have very likely inserted an element, therefore we need to re-balance the tree, the return of
            // re-balance might be the same root, if no re-balance is required, or one of it's left or right children depending on what
            // rotation was done during balancing
            return rebalance(root, value);
        }

        public BinaryHeightNode<T> delete(BinaryHeightNode<T> root, T value) {
            if (root == null) {
                // drilling down did not find the element, simply abort the deletion, at this point we have reached the end of the possible
                // path in the tree and the element was not present, the recursion will unwind the call stack till the top, returning
                // the nodes unmodified
                return null;
            }

            if (value.compareTo(root.value) > 0) {
                // drill down the right subtree until we find equal element, note the assignment, this allows us to propagate changes to
                // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
                // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
                // keep track of parent nodes.
                root.right = delete(root.right, value);
            } else if (value.compareTo(root.value) < 0) {
                // drill down the left subtree until we find equal element, note the assignment, this allows us to propagate changes to
                // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
                // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
                // keep track of parent nodes.
                root.left = delete(root.left, value);
            } else {
                // at this point the root would point at an element with value which must be equal to the target value, why ?, we will be
                // here only if the value is equal to the root, any other case will either exit, if node == null, we have drilled down to
                // the bottom of the path, if value > root.value or value < root.value then we will keep recursing, therefore we reach this
                // point only when value equals root.value
                if (root.left == null && root.right == null) {
                    // the root with the value has no children, therefore, we return null, this return is very crucial, it will return to
                    // the previous recursive call where the root will reference the parent of the current root node, therefore without
                    // using any parent pointers in the tree, we will correctly assign to the left or right of the parent of this root,
                    // null, detaching the current node that was found to equal the value
                    return null;
                } else if (root.left != null && root.right == null) {
                    // the same as above, but instead, we know that we have only left subtree, therefore we link the left subtree of the
                    // current root, which equals the target value, to the parent of the current root, this will essentially attach the
                    // current root's left subtree to the left or right subtree, based on off of where we came, to the parent of the current
                    // root / node
                    return root.left;
                } else if (root.right != null && root.left == null) {
                    // the same as above, but instead, we know that we have only right subtree, therefore we link the right subtree of the
                    // current root, which equals the target value, to the parent of the current root, this will essentially attach the
                    // current root's right subtree to the right or right subtree, based on off of where we came, to the parent of the
                    // current root / node
                    return root.right;
                } else {
                    // we have both left and right subtrees for the current root for which the value is found to be equal, what we do here,
                    // two options are available, in that scenario, both equal, and either one is fine
                    // - find the min element from the left subtree
                    // - find the max eleemnt from the right subtree
                    BinaryHeightNode<T> curr = root.left;
                    BinaryHeightNode<T> prev = root;

                    // we choose to find the max element from the left subtree, that is done by starting off from the root.left, and
                    // drilling down to the right, we can choose the other option find the min element in the right subtree it does not
                    // matter, it has some effect on how we do the checks inside the ifs below though if we go that route, but they are
                    // symmetric
                    while (curr != null && curr.right != null) {
                        // prev will always move one step behind curr, and prev.right will always point at curr at any point inside or
                        // outside this loop, we need to know prev, to detach curr from it, and attach the curr's children to prev instead
                        prev = curr;
                        curr = curr.right;
                    }

                    if (prev == root) {
                        // we have not moved at all, the while did not loop, meaning that there is no right nodes to the left or root,
                        // meaning that the prev will point to root, (what it was initially set to), therefore we can safely detach curr
                        // node, by assigning it's left subtree to the prev (which still points at root) left subtree, effectively detaching
                        // it from prev, because root.left == prev.left == curr, so far, no longer after we link root.left == prev.left to
                        // curr.left
                        prev.left = curr.left;
                    } else {
                        // we have drilled down to the right, meaning that curr must be the max node, that node can possibly have a left
                        // subtree (with nodes smaller than it), what we do to detach it ? assign to it's parent/previous node's right
                        // subtree the left subtree of the current's left subtree (this will detach curr itself, without loosing curr's
                        // links to it's child nodes, because prev.right == curr) if it has any it will be correctly re-linked (but it has
                        // no right one that is for sure, since we drill down to that condition, see while loop above)
                        prev.right = curr.left;
                    }

                    // copy the value of the curr node over to the root, the curr node will be the one which replaces the root's value,
                    // since the root's value is the one we want to delete in the first place, but it had to be replaced with a correct
                    // value computed from it's children first. The curr.value will maintain the bst property, since we found the max value
                    // from the root.left subtree, and replaced the root.value with it, therefore the property for the subtrees of root, and
                    // their values are maintained
                    root.value = curr.value;
                }
            }

            // try to check if rebalance is required, all the way, to the root, this is outside the else, because we have to post recurse,
            // to
            // the root, to at the very least update the heights of each parent along the way, if a deletion has occurred, and also check if
            // the balance is okay.
            return rebalance(root, value);
        }
    }

    public static final class HeapBinaryTree<T extends Comparable<T>> {

        public enum HeapType {
            MIN, MAX
        }

        private final HeapType type;
        private final List<T> heap = new ArrayList<>();

        public HeapBinaryTree(HeapType type) {
            this.type = type;
        }

        private int parent(int child) {
            // parent index of a heap element can be computed from either the left or the right child, we know that left child indices are
            // always not even, and right ones are always even, therefore, first check mod 2, and subtract 2, for right child, or 1 for a
            // left child
            child = child % 2 == 0 ? child - 2 : child - 1;

            // finally to get the parent divide by 2, this basically reverses the equation for finding a child from a parent index, see the
            // methods below
            return child / 2;
        }

        private int left(int parent) {
            // to get the left child, multiply the parent index by two, and add one, each number multiplied by two becomes an even, adding
            // one would make it odd, all right children are on odd indices
            return (2 * parent) + 1;
        }

        private int right(int parent) {
            // to get the right child, multiply the parent index by two, and add two, each number multiplied by two becomes an even, adding
            // two would keep it even, all left children are on even indices
            return (2 * parent) + 2;
        }

        private void swap(List<T> heap, int l, int r) {
            // simple method to swap elements located on two indices, not particularly important to the heap implementation, but a utility
            // method to help us have cleaner implementation
            T c = heap.get(r);
            T p = heap.get(l);

            // exchange the elements at the two index locations in the heap array
            heap.set(r, p);
            heap.set(l, c);
        }

        public T peek(List<T> heap) {
            if (heap.isEmpty()) {
                // an empty heap has nothing to peek at
                return null;
            }
            // the element of a heap that can be looked at is always at the top, either the smallest, or the biggest in a heap
            return heap.get(0);
        }

        public T insert(List<T> heap, T value) {
            // first off we start by putting the new item at the end of the array or heap, and getting that new index location of the last
            // element we have just inserted
            heap.add(value);
            int curr = heap.size() - 1;

            // what is going on here, is looping until the current element is not pointing at the root, or in other words at index 0,
            // current starts off from the insertion position, i.e the end of the heap, the very last element and bubbles up the new element
            // until the correct spot is found
            while (curr > 0) {
                // calculate the parent of the current index, and extract the value of the parent
                int prev = parent(curr);
                T parent = heap.get(prev);

                // find if the new element is bigger, smaller or equal to the parent
                int diff = value.compareTo(parent);

                // when equal to the parent, we can abort, duplicates are not allowed, for simplicity
                if (diff == 0) {
                    break;
                }

                if (type == HeapType.MIN && diff < 0) {
                    // when we are in a min heap, the swap is happening only if the new element is smaller than it's parent, this is because
                    // the new element being smaller, has to be bubbled up, until it is no longer smaller than it's parent
                    swap(heap, curr, prev);
                }

                if (type == HeapType.MAX && diff > 0) {
                    // when we are in a max heap, the swap is happening only if the new element is bigger than it's parent, this is because
                    // the new element being bigger, has to be bubbled up, until it is no longer bigger than it's parent
                    swap(heap, curr, prev);
                }

                // move up to the parent of the current element, eventually prev here would become 0, index 0, and the while loop will
                // finish, meaning that the element was bubbled to the top, for extra peformance one might just break after we find the
                // first element where diff does not perform a swap, meaning the element is already at the correct spot.
                curr = prev;
            }
            return value;
        }

        public T delete(List<T> heap) {
            // there is nothing to delete from an emtpy heap in the first place
            if (heap.isEmpty()) {
                return null;
            }

            // first get the last element from the heap and swap with with the root, the last element will then be bubbled down, and the
            // root returned from this function as result
            int last = heap.size() - 1;
            swap(heap, last, 0);

            // the head or root of the heap is now the last one, after the swap, just remember it, and remove it from the heap
            T head = heap.get(last);
            heap.remove(last);

            // we start off from the top or head of the heap, going down locating the left and right indices of the current element and
            // check if the root needs to stay in it's place, or go left or right
            int next = 0;
            while (next < heap.size()) {
                // get the indices of the left and right children of the current element
                int left = left(next);
                int right = right(next);

                // start off by assuming the smallest / biggest (whichever heap type we have) element between the root and it's children is
                // the root, set the 'base' index to point at the root initially, this base index will either change, or remain the same,
                // based on this we would know to continue or not to swap elements
                int base = next;

                if (type == HeapType.MIN) {
                    // we first compare the value of the 'base' index with it's left child, if the left child is smaller than the root, we
                    // update the 'base' index to point to the left child index
                    if (left < heap.size() && heap.get(left).compareTo(heap.get(base)) < 0) {
                        // left becomes the smaller between base and left
                        base = left;
                    }

                    // we first compare the value of the 'base' index with it's right child, if the right child is smaller than the 'base',
                    // we update the 'base' index to point to the right child index
                    if (right < heap.size() && heap.get(right).compareTo(heap.get(base)) < 0) {
                        // right becomes the smaller between base and right
                        base = right;
                    }
                } else if (type == HeapType.MAX) {
                    // we first compare the value of the 'base' index with it's left child, if the left child is bigger than the root, we
                    // update the 'base' index to point to the left child index
                    if (left < heap.size() && heap.get(left).compareTo(heap.get(base)) > 0) {
                        // left becomes the bigger between base and left
                        base = left;
                    }

                    // we first compare the value of the 'base' index with it's right child, if the right child is bigger than the 'base',
                    // we update the 'base' index to point to the right child index
                    if (right < heap.size() && heap.get(right).compareTo(heap.get(base)) > 0) {
                        // right becomes the bigger between base and right
                        base = right;
                    }
                }

                if (base != next) {
                    // if the base index actually changed, then we can swap the base with next, meaning that either the left or right
                    // children of the root were smaller / bigger (based on the heap type we have)
                    swap(heap, base, next);
                    // now we move to the next index with which we swapped, either the left or right, to continue to drop down
                    // the element until it finds its place
                    next = base;
                } else {
                    // at this point the base did not change, meaning the root was the smallest / biggest of the 3 (root,left,right),
                    // therefore the element is at the correct position, there is nowhere for it to go further, it conforms to the rules
                    // of a heap
                    break;
                }
            }
            return head;
        }
    }

    public static final class RetrievalPrefixTree<T> {

        private static final int CHAR_COUNT = 26;
        private static final int BASE_CHAR = 'a';

        public static class TriePrefixNode<T> extends ValueNode<T> {
            protected TriePrefixNode<T>[] children;
            protected boolean terminating = false;
        }

        private void cleanup(TriePrefixNode<T> root) {
            if (root == null || root.children == null) {
                return;
            }
            root.value = null;
            root.terminating = true;
            for (int i = 0; i < root.children.length; i++) {
                TriePrefixNode<T> child = root.children[i];
                if (child != null && child.terminating && child.value == null) {
                    root.children[i] = null;
                }
            }
        }

        private int children(TriePrefixNode<T> root) {
            if (root == null || root.children == null) {
                return 0;
            }

            int count = 0;
            for (int i = 0; i < root.children.length; i++) {
                if (root.children[i] != null) {
                    count++;
                }
            }
            return count;
        }

        public TriePrefixNode<T> insert(TriePrefixNode<T> root, String string, T value) {
            // check if the input values first make any sense at all, the string can not be nil, and the value can not be nil either
            if (string == null || value == null) {
                return null;
            }
            // for simplicity reasons normalize all strings to lower case, this is not really a part of an usual trie implementation but it
            // makes things easier to understand
            string = string.toLowerCase();

            // either start off from the provided root, or make on if the insert was called without a valid root
            TriePrefixNode<T> base = root;
            if (root == null) {
                root = new TriePrefixNode<>();
                base = root;
            }

            // go over each character in the input string.
            for (int i = 0; i < string.length(); i++) {
                // fetch the character and convert it to a number, in the ascii table the english alphabet lower case letters start at 97,
                // up until 97 + 26. This is used to index the character in node in the children list of a trie node
                Character c = string.charAt(i);
                int cval = c.charValue();

                if (root.children == null) {
                    // the current root has no children, therefore we have to create an array of CHAR_COUNT children first, this is needed,
                    // to make an empty array of null pointers, which are going to be filled in with node instances
                    root.children = new TriePrefixNode[CHAR_COUNT];
                }

                // find which child index the current character correspons to, it can either already exist, or not, if it exists we just
                // take that path, and continue down
                TriePrefixNode<T> node = root.children[cval - BASE_CHAR];
                if (node == null) {
                    // the node at that char position does not exist, create a new node instance, and assign it to that position in the
                    // root's children
                    node = new TriePrefixNode<>();
                    root.children[cval - BASE_CHAR] = node;
                }

                // the next root would now be the last character node we inserted, continue until the string is completely looped over
                root = node;
            }

            // after the string has been looped over the root node here would point at the last character 'node', we mark it as terminating,
            // meaning that it represents an end of the path, or in other words one complete word, and we set a value mapping to it. The
            // value here is not a usual part of trie implementation, it is done to demonstrate how a value can be mapped to a word/string
            // in a trie, similarly to a hash-map
            root.terminating = true;
            root.value = value;

            // return the base root node, which represents the passed in or created root
            return base;
        }

        public boolean delete(TriePrefixNode<T> root, String string) {
            // check if the input values first make any sense at all, the string can not be nil, and the value can not be nil either
            if (string == null || root == null) {
                return false;
            }
            // for simplicity reasons normalize all strings to lower case, this is not really a part of an usual trie implementation but it
            // makes things easier to understand
            string = string.toLowerCase();

            // go over each character in the input string, keep a stack of element which will represent all nodes thorugh which we have
            // traversed. Note that deletion as with every tree involves the same algorithm, first search for the node, therefore this block
            // below is pretty much search with the added change that we add the path that we go through to a stack
            Stack<TriePrefixNode<T>> path = new Stack<>();
            for (int i = 0; i < string.length(); i++) {
                // fetch the character and convert it to a number, in the ascii table the english alphabet lower case letters start at 97,
                // up until 97 + 26. This is used to index the character in node in the children list of a trie node
                Character c = string.charAt(i);
                int cval = c.charValue();

                // if the current root is invalid or has no children array initialized, there is nowhere to go really, therefore the passed
                // in string 'word' is not part of the trie. there is no path in the tree which would describe this word
                if (root == null || root.children == null) {
                    return false;
                }
                // set the next root to the child which corresponds to the char index, from the root.children nodes, that node can either be
                // null, or be initialized, when we re-enter the next iteration, it will either terminate on root == null / or root.children
                // == null or keep going
                root = root.children[cval - BASE_CHAR];

                // this is the important part, add the current root path to the stack, this is done after the root assignment, note why ?
                // the very first root of the tree is not representing a character, it is just the common point from which we start, after
                // root has been assigned below, it would actually point to the node which represents the first character from the input
                // string 'word', could be null, or a valid one, but in either case has to happen after root is re-assigned
                path.push(root);
            }

            // the root element would point at the last character of the input string, if it is terminating, then we know we have hit a
            // valid word which can be removed, otherwise, there is nothing to delete
            if (root.terminating) {
                // empty the stack, and clean up the children, remember we start off from the very last trie node / character representing
                // the word, going up to the begining of the word. At each level that node can have
                // - 1 or less children, meaning that
                while (!path.isEmpty()) {
                    TriePrefixNode<T> curr = path.pop();
                    // if (curr != root && curr.terminating) {
                    // cleanup(curr);
                    // break;
                    // }
                    // if (curr != null && children(curr) <= 1) {
                    // curr.terminating = true;
                    // curr.children = null;
                    // curr.value = null;
                    // cleanup(curr);
                    // }
                }
                return true;
            }
            return false;
        }

        public boolean search(TriePrefixNode<T> root, String string) {
            // make some validation on the input, nil strings are not valid, neither are non existent roots
            if (string == null || root == null) {
                return false;
            }

            // for simplicity reasons normalize all strings to lower case, this is not really a part of an usual trie implementation but it
            // makes things easier to understand
            string = string.toLowerCase();

            // go over each character in the input string.
            for (int i = 0; i < string.length(); i++) {
                // fetch the character and convert it to a number, in the ascii table the english alphabet lower case letters start at 97,
                // up until 97 + 26. This is used to index the character in node in the children list of a trie node
                Character c = string.charAt(i);
                int cval = c.charValue();

                // if the current root is invalid or has no children array initialized, there is nowhere to go really, therefore the passed
                // in string 'word' is not part of the trie. there is no path in the tree which would describe this word
                if (root == null || root.children == null) {
                    // nothing was found, return false
                    return false;
                }
                // set the next root to the child which corresponds to the char index, from the root.children nodes, that node can either be
                // null, or be initialized, when we re-enter the next iteration, it will either terminate on root == null / or root.children
                // == null or keep going
                root = root.children[cval - BASE_CHAR];
            }
            // reaching this point does not mean we have found the string 'word' still, the path might exist, as a subpath of another longer
            // word, we have to see if the node at the end of the path for the current string 'word' was marked as terminating, meaning it
            // represent a word in the trie.
            return root.terminating;
        }
    }

    public static void main(String[] args) {
        // BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        // BinaryNode<Integer> root = tree.insert(null, 10);
        // tree.insert(root, 8);
        // tree.insert(root, 25);
        // tree.insert(root, 3);
        // tree.insert(root, 9);
        // tree.insert(root, 19);
        // tree.insert(root, 35);
        // tree.insert(root, 1);
        // tree.insert(root, 4);
        // tree.insert(root, 5);
        // tree.delete(root, 8);

        // AvlBinarySearchTree<Integer> tree = new AvlBinarySearchTree<>();
        // BinaryHeightNode<Integer> root = tree.insert(null, 10);
        // tree.insert(root, 8);
        // tree.insert(root, 25);
        // tree.insert(root, 3);
        // tree.insert(root, 9);
        // tree.insert(root, 19);
        // tree.insert(root, 35);
        // tree.insert(root, 1);
        // tree.insert(root, 4);
        // tree.insert(root, 5);
        // tree.delete(root, 8);

        // HeapBinaryTree<Integer> heap = new HeapBinaryTree<>(HeapBinaryTree.HeapType.MIN);
        // heap.insert(heap.heap, 5);
        // heap.insert(heap.heap, 4);
        // heap.insert(heap.heap, 3);
        // heap.insert(heap.heap, 2);
        // heap.insert(heap.heap, 9);
        // heap.insert(heap.heap, 8);
        // heap.insert(heap.heap, 10);
        // heap.insert(heap.heap, 1);
        // heap.peek(heap.heap);
        // heap.delete(heap.heap);
        // heap.peek(heap.heap);

        RetrievalPrefixTree<Integer> trie = new RetrievalPrefixTree<>();
        RetrievalPrefixTree.TriePrefixNode<Integer> root = trie.insert(null, "word", 0);
        trie.insert(root, "wordle", 1);
        trie.insert(root, "static", 2);
        trie.insert(root, "ant", 3);
        trie.insert(root, "tan", 4);
        trie.insert(root, "antlers", 5);
        trie.insert(root, "tank", 6);
        trie.insert(root, "statistics", 7);
        trie.insert(root, "an", 8);
        trie.insert(root, "antimeasure", 9);
        trie.delete(root, "antlers");
        trie.search(root, "an");
        trie.search(root, "antlers");
        trie.search(root, "antimeasure");

        return;
    }
}
