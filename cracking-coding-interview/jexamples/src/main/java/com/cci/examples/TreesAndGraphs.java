package com.cci.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static class GraphNode<T> extends ValueNode<T> {
        protected int incoming = 0;
        protected List<GraphNode<T>> children;
    }

    public static class GraphEdge<T> {
        protected T from;
        protected T to;
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

    public static class BinaryTree<T extends Comparable<T>> {

        private BinaryNode<T> create(BinaryNode<T> root, int index, List<T> elements) {
            if (index >= elements.size()) {
                return null;
            }

            root.value = elements.get(index);
            if (root.value == null) {
                return null;
            }
            root.left = create(new BinaryNode<>(), (index * 2) + 1, elements);
            root.right = create(new BinaryNode<>(), (index * 2) + 2, elements);
            return root;
        }

        public BinaryNode<T> create(List<T> elements) {
            return create(new BinaryNode<>(), 0, elements);
        }

        private int height(BinaryNode<T> root) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max(height(root.left), height(root.right));
        }

        public boolean search(BinaryNode<T> root, T value) {
            if (root == null || value == null) {
                return false;
            }

            if (root.value != null && root.value.compareTo(value) == 0) {
                return true;
            }
            return search(root.left, value) || search(root.right, value);
        }

        public BinaryNode<T> find(BinaryNode<T> root, T value) {
            if (root == null || value == null) {
                return null;
            }

            if (root.value != null && root.value.compareTo(value) == 0) {
                return root;
            }

            BinaryNode<T> left = find(root.left, value);
            BinaryNode<T> right = find(root.right, value);

            if (left != null) {
                return left;
            } else if (right != null) {
                return right;
            } else {
                return null;
            }
        }
    }

    public static class BinarySearchTree<T extends Comparable<T>> {

        private BinaryNode<T> create(List<T> elements, int start, int end) {
            int diff = end - start;
            if (diff <= 0) {
                return null;
            }
            int index = start + (diff / 2);
            BinaryNode<T> node = new BinaryNode<>();

            node.value = elements.get(index);
            node.left = create(elements, start, index);
            node.right = create(elements, index + 1, end);
            return node;
        }

        public BinaryNode<T> create(List<T> elements) {
            // sort the incoming array of elements, to guarantee the creation which relies on them being sorted
            elements.sort(Comparable::compareTo);
            // create from a sorted array of elements, a binary tree with initial perfrect balance factor
            return create(elements, 0, elements.size());
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
            if (root == null) {
                return root;
            }
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
            // structure very similar to a linked list, it is quite obvious when either the left or right subtrees have a chain of links,
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
                    root = null;
                } else if (root.left != null && root.right == null) {
                    // the same as above, but instead, we know that we have only left subtree, therefore we link the left subtree of the
                    // current root, which equals the target value, to the parent of the current root, this will essentially attach the
                    // current root's left subtree to the left or right subtree, based on off of where we came, to the parent of the current
                    // root / node
                    root = root.left;
                } else if (root.right != null && root.left == null) {
                    // the same as above, but instead, we know that we have only right subtree, therefore we link the right subtree of the
                    // current root, which equals the target value, to the parent of the current root, this will essentially attach the
                    // current root's right subtree to the right or right subtree, based on off of where we came, to the parent of the
                    // current root / node
                    root = root.right;
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

            // try to check if rebalance is required, all the way, to the root, this is outside the else, because we have to post recurse, to
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

    public static final class SimpleGraphType<T> {

        public GraphEdge<T> edge(T from, T to) {
            GraphEdge<T> edge = new GraphEdge<>();
            edge.from = from;
            edge.to = to;
            return edge;
        }

        public List<GraphEdge<T>> edges(List<T> elements) {
            if (elements.size() % 2 != 0) {
                return Collections.emptyList();
            }

            List<GraphEdge<T>> edges = new ArrayList<>();
            for (int i = 0; i < elements.size(); i += 2) {
                edges.add(edge(elements.get(i), elements.get(i + 1)));
            }
            return edges;
        }

        public List<GraphNode<T>> create(List<GraphEdge<T>> edges) {
            Map<T, GraphNode<T>> cache = new HashMap<>();
            Function<T, GraphNode<T>> constructor = (T value) -> {
                GraphNode<T> node = new GraphNode<>();
                node.children = new ArrayList<>();
                node.value = value;
                node.incoming = 0;
                return node;
            };

            for (GraphEdge<T> edge : edges) {
                if (edge.from != null && !cache.containsKey(edge.from)) {
                    cache.put(edge.from, constructor.apply(edge.from));
                }

                if (edge.to != null && !cache.containsKey(edge.to)) {
                    cache.put(edge.to, constructor.apply(edge.to));
                }

                GraphNode<T> current = cache.get(edge.from);
                if (edge.to != null) {
                    GraphNode<T> child = cache.get(edge.to);
                    current.children.add(child);
                    child.incoming++;
                }
            }
            return cache.values().stream().collect(Collectors.toList());
        }
    }

    /**
     * Construct the most minimal binary search tree from a list of sorted unique items.
     *
     * What we do here is take advantage of the fact that we know, according to the task the array is of unique items and sorted, to
     * construct the binary search tree we have to take the mid element in the array, that would be our root, we go to the left sub-array
     * and right sub-array and recursively do the same.
     *
     */
    public static final class MinimalBinaryTree {

        /**
         * Create a tree helper function from an array of items
         *
         * @param array - the array of items
         * @param start - start, in index space
         * @param end - end in array size space
         * @return - the tree root
         */
        private BinaryNode<Integer> createTreeHelper(Integer[] array, int start, int end) {
            // calculate the diff between start and end, note that start is index, and end is a size, therefore the only valid difference
            // between them can be at most 1, for example when start = 0 and end = 1, that means the sub-array we have to look at has
            // only one element, the one at index 0, end tells us the number of items in the sub-array, the start is the starting index
            int diff = end - start;

            if (diff <= 0) {
                // if the diff is invalid just return here and stop recursion, this would imply that the start (index) overlaps with end
                // (size), which is not a valid state, meaning we can stop, nothing more to subdivide
                return null;
            }

            // the current midpoint index is the diff divided by two, offset with the start index. The diff / 2 would give us the relative
            // mid point element, for the current subdivided sub-array, but to make it absolute in the context of the source input array we
            // have to add the current start index to that.
            int index = start + (diff / 2);

            // create the new root node, the value for that new root node would be the one at 'index', or in other words the midpoint
            // element in the array which is
            BinaryNode<Integer> node = new BinaryNode<>();
            node.value = array[index];

            // the left subtree for the root node start from start up unil, but not including the current node i.e start -> index, remember
            // the end argument to this function is not an index, it is a size, so the range of elements for the left subtree would be
            // the inclusive range [start:index-1]
            node.left = createTreeHelper(array, start, index);

            // the right subtree for the root node start from start up unil, but not including the current node i.e index + 1 -> end,
            // remember the end argument to this function is not an index, it is a size, so the range of elements for the right subtree
            // would be the inclusive range [index+1:end-1]
            node.right = createTreeHelper(array, index + 1, end);

            // return the node
            return node;
        }

        public BinaryNode<Integer> createMinimalTree(List<Integer> elements) {
            return createTreeHelper(elements.toArray(new Integer[elements.size()]), 0, elements.size());
        }
    }

    /**
     * List all nodes at each level in a binary tree.
     *
     * What we need to realize here is how to traverse each level of the tree, add it to a list, and then dump all immediate children of
     * each of the elements in the current level to do the same operation, repeat until there are no more children
     *
     */
    public static final class ListTreeDepts {

        /**
         * Create a list of list of nodes, each sub-list represents all nodes, which occupy that given level / height of the binary tree
         *
         * @param root - the root of the tree
         * @return - the list of levels
         */
        public List<List<BinaryNode<Integer>>> createTreeDepts(List<Integer> elements) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);

            // make a list of lists to hold the nodes for each level
            List<List<BinaryNode<Integer>>> depts = new LinkedList<>();

            // create a queue, which we would use to dump the nodes to go through
            Queue<BinaryNode<Integer>> nodes = new LinkedList<>();

            // start off from the root, obviously
            nodes.add(root);

            // we have two whiles based on the queue emptyness, the outter loop makes sure we exaust the entire queue, the inner queue, is
            // filled once per level
            while (!nodes.isEmpty()) {
                // the current list of depts, is going to be populated with everything in the queue at the current moment, after which we
                // use this list of depths, to populate the emptied queue with the immediate children, for each node in depth list
                LinkedList<BinaryNode<Integer>> depth = new LinkedList<>();
                while (!nodes.isEmpty()) {
                    // dump the entire queue with nodes into the list of depts. we perform a simple breadth first search
                    depth.add(nodes.remove());
                }

                // for each of the dumped nodes, in the current level, get all non-null immediate children and put them back in the same
                // queue, which has been emptied in the step above. The queue will now contain the next full level, repeat until we reach
                // the leaf nodes, which would not add any children, the queue would become emptpy, the outter loop would exit.
                for (BinaryNode<Integer> node : depth) {
                    if (node.left != null) {
                        // add left only if valid
                        nodes.add(node.left);
                    }
                    if (node.right != null) {
                        // add right only if valid
                        nodes.add(node.right);
                    }
                }

                // add the current depths level to the final result
                depts.add(depth);
            }
            return depts;
        }
    }

    /**
     * Check if a tree is balanced, for the purposes of the task, a balanced tree is one which has no heigh difference between its sub-trees
     * of more than 1
     *
     * To solve this we need to realize that all we need to do is calculate the height of the left and right sub-trees of the root, find the
     * absolute value of the difference between the height of them, if it is more than 1, the tree is not balanced
     *
     */
    public static final class CheckBalancedTree {

        private int calculateHeightHelper(BinaryNode<Integer> root) {
            // do some sanity checks
            if (root == null) {
                // an invalid node, has no height
                return 0;
            }
            // the height of the root is always 1 (for the root) and the max height between its subtrees
            return 1 + Math.max(calculateHeightHelper(root.left), calculateHeightHelper(root.right));
        }

        public boolean checkBalancedTree(List<Integer> elements) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);

            // get the height of the left subtree of root
            int left = calculateHeightHelper(root.left);

            // get the height of the right subtree of root
            int right = calculateHeightHelper(root.right);

            // the abs(diff) must not exceed 1
            return Math.abs(left - right) <= 1;
        }
    }

    /**
     * Validate that the given tree is a binary search tree.
     *
     * The trick below is quite neat, what we do is since we know that a BST always has left subtree smaller than the root, and the right
     * one bigger than the root, we can use the root's value as a range to restrict us while we go down the left and right sub-trees
     *
     */
    public static final class CheckBinarySearchTree {

        private boolean checkTreeHelper(BinaryNode<Integer> root, Integer min, Integer max) {
            // do some santiy checks
            if (root == null) {
                // this is a weird base case, but if we reach this point it means we have traversed the entire tree and it has kept the bst
                // property up until the very bottom
                return true;
            }

            // we know that the current root value must not be smaller than min and not bigger than max range, if it is either, then the
            // tree does not meet the bst rules, we return false
            if ((min != null && root.value < min) || (max != null && root.value > max)) {
                // the current root value was not within the max or min ranges, therefore it does not meet the rules of a bst, return false,
                // the and (&&) condition below would guarantee that for this tree as a whole the check for bst would return false
                return false;
            }

            // the trick here is to see how we gradually restrict the max/min values while we go down. When we start going left/right down
            // the tree, the min/max ranges would keep narrowing down, note that both subtrees must evalute to true, i.e must conform to the
            // rules of bst, therefore note the and (&&) sign below
            // - the left sub-tree we know that values there can not be bigger than root.value, therefore we set max to be root.value
            // - the right sub-tree we know that values there can not be smaller than root.value, therefore we set min to be root.value
            return checkTreeHelper(root.left, min, root.value) && checkTreeHelper(root.right, root.value, max);
        }

        public boolean validateBinarySearchTree(List<Integer> elements) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);

            return checkTreeHelper(root, null, null);
        }
    }

    /**
     * Find the next successor of a given node that would be traversed in a standard in-order traversal, Assume that each node has links to
     * it's parent.
     *
     * What we need to realize here is what in-order traversal does, if the root has a right node, then this is where we have to look at,
     * more precisely the left most node of the immediate right child i.e leftMost(root.right), if the root has no right node, that could
     * only mean the next one to print is going to be some of the roots of the current node, having no right node means we have exausted all
     * right nodes, and in the in-order traversal, that means the recursive call stack will start to unwind, see below:
     *
     * The in-order traversal -> traverse(root.left) print(root) traverse(root.right)
     *
     */
    public static final class FindSuccessorNode {

        private BinaryNode<Integer> findParentNode(BinaryNode<Integer> root, BinaryNode<Integer> node, BinaryNode<Integer> parent) {
            if (root == null) {
                // did not find the node to be part of this tree with the given root
                return null;
            }

            if (root == node) {
                // the current root is equal the target node, return the parent
                return parent;
            }

            // go in both directions, first left, then right, it does not matter in which direction we go, as long as we go through all
            // nodes in the tree to find the target node, remember to pass in the current root as the parent of root.left and root.right
            BinaryNode<Integer> left = findParentNode(root.left, node, root);
            BinaryNode<Integer> right = findParentNode(root.right, node, root);

            if (left != null) {
                // the node was in the left subtree, we can return the result of this call which is the parent
                return left;
            } else if (right != null) {
                // the node was in the right subtree, we can return the result of this call which is the parent
                return right;
            } else {
                // the node was not found in the tree with the given root
                return null;
            }
        }

        private BinaryNode<Integer> nextInOrder(List<Integer> elements, Integer target) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);
            BinaryNode<Integer> found = tree.find(root, target);

            if (found.right != null) {
                // the found / target node has a right node, therefore we can drill down to the left of the immediate right, that one is
                // going to be the next in the in-order traversal, if we take a look at the usual in-order, that looks like that -
                // inorder(found) -> traverse(found.left) print(found) traverse(found.right), we can see that the recursion will first drill
                // to the left as far down as it can first, for the inorder node
                found = found.right;
                while (found != null && found.left != null) {
                    // while we have a left node, go to the left
                    found = found.left;
                }
                // that node would be either the immediate right, or the leftmost of the right subtree
                return found;
            } else {
                // this one is more convoluted, if the node has no right children, we have to traverse up the parents, until the path we
                // come from is not a right subtree, if we think about it, after we finish with the entire right subtree in the in-order
                // traversal, inorder(found) -> traverse(found.left) print(found) traverse(found.right), after we print(found), and we have
                // no found.right, the stack will begin unwindiwng,
                BinaryNode<Integer> parent = found;
                do {
                    found = parent;
                    parent = findParentNode(root, found, null);
                } while (parent != null && parent.right == found);
                return parent;
            }
        }
    }

    /**
     * Find the first common ancestor between two given nodes in a binary tree
     *
     * This one is also quite neat, the solution is to realize that we have two cases. either the two nodes are located on the left and
     * right of a given root / parent node or the two nodes are located on the same side (left or right) of a given root / parent node.
     *
     */
    public static final class FirstCommonAncestor {

        private boolean containsNodeHelper(BinaryNode<Integer> root, BinaryNode<Integer> node) {
            // do some sanity checks first
            if (root == null) {
                // the root does not contain the given node
                return false;
            }

            // the root and the node are the same, yes the initial root contained the node
            if (root == node) {
                return true;
            }

            // try go to the left and right of the current root looking for the target node
            return containsNodeHelper(root.left, node) || containsNodeHelper(root.right, node);
        }

        private BinaryNode<Integer> commonAncestorHelper(BinaryNode<Integer> root, BinaryNode<Integer> first, BinaryNode<Integer> second) {
            if (root == null) {
                // invalid root no common ancestors
                return null;
            }

            // check if the first node is on the left of the root
            boolean firstOnLeft = containsNodeHelper(root.left, first);

            // check if the second node is on the right of the root
            boolean secondOnRight = containsNodeHelper(root.right, second);

            // we know that if both of those variables are equal, then the current root is the common ancestor, why ? if we take any two
            // nodes in a binary tree, there exists only one single node, for which the two nodes are located on the left and the right,
            // at the same time, and that node is the common ancestor. Note that it does not matter if we flip the first and second, so the
            // checks above would return false for both instead, if the check above both return false, that simply means the nodes are on
            // the opposite site of what we were checking, but they are on the left and right of root still, as long as firstOnLeft ==
            // secondOnRight, the nodes are on the left and right of current root, which (of the nodes first/second) is on which does not
            // matter
            if (firstOnLeft == secondOnRight) {
                return root;
            }

            if (firstOnLeft) {
                // in the case where the first node was on the left, and the condition above did not meet, that means first node is on the
                // left but the second one is also on the left, not on the right (secondOnRight would be false), so we go to the left
                return commonAncestorHelper(root.left, first, second);
            } else {
                // the same as above but inversed, if firstOnLeft was false, that would automatically mean that secondOnRight was true,
                // therefore the first node is on the right so is the second, go to the right subtree
                return commonAncestorHelper(root.right, first, second);
            }
        }

        public BinaryNode<Integer> findFirstAncestor(List<Integer> elements, Integer first, Integer second) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);

            BinaryNode<Integer> fnode = tree.find(root, first);
            BinaryNode<Integer> snode = tree.find(root, second);

            return commonAncestorHelper(root, fnode, snode);
        }
    }

    public static final class CollectElementsSequence {

        private void listWeaverHelper(LinkedList<Integer> first, LinkedList<Integer> second, LinkedList<Integer> prefix,
                        List<LinkedList<Integer>> result) {

            if (first.isEmpty() || second.isEmpty()) {
                // in the case where one of the source lists are emtpy, meaning elements were moved to the prefix list, clone the prefix and
                // add to that the first and second lists, the prefix at any given time would contain elements from one, or both arrays, in
                // different order, i.e first elements from first array, then second, or vice-versa, but not mess the order of the elements
                // that come from the same array.
                LinkedList<Integer> cloned = (LinkedList<Integer>) prefix.clone();
                cloned.addAll(first);
                cloned.addAll(second);
                result.add(cloned);
                return;
            }

            // The way this flow works, is by first exhausting the first list, down to the very last element, put everything into the prefix
            // list, and append in the base case, now the recursive stack starts to unwind, elements are returned to the first list, one by
            // one, and for each, we go depth first into the recurisive calls for the second list. Let us take the example {1,2} & {3,4}

            // remove the front element from the first list
            Integer element = first.removeFirst();
            // add that element to the tail of the prefix list
            prefix.addLast(element);
            // drill down, depth first, into the first list first
            listWeaverHelper(first, second, prefix, result);
            // remove the last inserted element to the prefix
            prefix.removeLast();
            // restore the element back into the source list
            first.addFirst(element);

            // remove the front element from the second list
            element = second.removeFirst();
            // add that element to the tail of the prefix list
            prefix.addLast(element);
            // drill down, depth first, into the second list second
            listWeaverHelper(first, second, prefix, result);
            // remove the last inserted element to the prefix
            prefix.removeLast();
            // restore the element back into the source list
            second.addFirst(element);
        }

        private List<LinkedList<Integer>> collectElementSequences(BinaryNode<Integer> root) {
            List<LinkedList<Integer>> result = new LinkedList<>();

            if (root == null) {
                return result;
            }

            result.add(new LinkedList<>());

            List<LinkedList<Integer>> leftSequences = collectElementSequences(root.left);
            List<LinkedList<Integer>> rightSequences = collectElementSequences(root.right);

            for (LinkedList<Integer> lefts : leftSequences) {
                for (LinkedList<Integer> rights : rightSequences) {
                    listWeaverHelper(lefts, rights, new LinkedList<>(), result);
                }
            }
            return result;
        }
    }

    /**
     * Check if a given small tree is part of another bigger tree, such that if we cut off that subtree tree from the bigger tree, both the
     * small subtree and the cut tree are identical
     *
     * This solution here is to realize that what we need to do is compare the small tree to the subtrees in the bigger trees. If we find
     * that the bigger tree contains a subtree equal to the small tree, this is our solution
     *
     */
    public static final class DetectExistingSubtree {

        private boolean compareSubtreesHelper(BinaryNode<Integer> first, BinaryNode<Integer> second) {
            if (first == null && second == null) {
                // when the two subtrees are null, they are considered equal, this base case will also be fulfiled when we reach the bottom
                // of the two trees we compare at the same rate / time / steps, knowing we have finished both trees at the same time
                return true;
            }

            if (first == null && second != null) {
                // we have reached the bottom of the first tree, but the second did not, meaning that moving at the same pace, the first
                // tree was shorter / smaller than the second one
                return false;
            }

            if (second == null && first != null) {
                // we have reached the bottom of the second tree, but the first did not, meaning that moving at the same pace, the second
                // tree was shorter / smaller than the first one
                return false;
            }

            // compare the values of the roots
            if (first.value.equals(second.value)) {
                // compare the roots, only if the roots have the same value, can we then continue down, with the same rate/steps into both
                // subtrees, what we do here is just drill down equally deep and on the same side for both input trees, if all roots match
                // up on each side then we can assume they are equal, imagine it as if we had compared two strings, we compare current char,
                // then move right one character, until the string ends
                return compareSubtreesHelper(first.left, second.left) && compareSubtreesHelper(first.right, second.right);
            }
            // the root value did not match, meaning we can terminate, they are not equal here, even though there still might be more
            // children to look at
            return false;
        }

        private boolean checkSubtreeHelper(BinaryNode<Integer> main, BinaryNode<Integer> subtree) {
            if (subtree == null) {
                // a null subtree, means that it is always a part of the main subtree, the main subtree contains many null subtrees, think
                // of the leaves which have 'null' subtrees / children
                return true;
            }
            if (main == null) {
                // the main subtree must not be null, otherwise there is nothing to check against
                return false;
            }

            // we first drill down the left and right branches, then the post recursive call will compare from the bottom - up approach,
            // either bottom-up or down will work, we have selected to use bottom-up in this example solution
            return checkSubtreeHelper(main.left, subtree) || checkSubtreeHelper(main.right, subtree)
                            || compareSubtreesHelper(main, subtree);
        }

        public boolean checkSubtreeValidity(List<Integer> mainelements, List<Integer> subelements) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> maintree = tree.create(mainelements);
            BinaryNode<Integer> subtree = tree.create(subelements);

            return checkSubtreeHelper(maintree, subtree);
        }
    }

    /**
     * Find all paths in a tree, that sum up to a given sum, these paths need not terminate at a leaf node, or start at the main tree root.
     * The tree can contain negative numbers too
     *
     * The solution here is to traverse all nodes, and for each node, to sum up it's subtree, to the very end, we keep track of the current
     * sum at each level of progression down to the leaves, and increment the count when the current sum hits the target sum, we only stop
     * when we reach the bottom / end of the current subtree
     *
     */
    public static final class FindSumPath {

        // use a class variable to simplify the implementation, could be integrated in the recursive calls, but to avoid complexity we have
        // not done that here
        private int count = 0;

        private void countSumAccumulation(BinaryNode<Integer> root, int accum, int sum) {
            if (root == null) {
                // nothing to account for, the root is a leaf node, simply return, this is a base case
                return;
            }

            if (root.value + accum == sum) {
                // increment the count, since the current root added to the accumulated sum equals the target sum. However, do not stop
                // recursing, there still might be paths below this one, which can sum to the target sum, how ? Well remember the tree might
                // contain negative numbers, therefore the sum might increase or decrease, but in the end still equal the target sum, to be
                // sure, traverse till the end of the current tree
                this.count += 1;
            }

            // go in both directions, until there is no more leaves to inspect, if any of the sub-paths sum up to the target sum the count
            // will be incremented, otherwise we will enter the base case which will simply return out and unwind the call stack, eventually
            countSumAccumulation(root.left, accum + root.value, sum);
            countSumAccumulation(root.right, accum + root.value, sum);
        }

        private void traverseAllPaths(BinaryNode<Integer> root, int sum) {
            if (root == null) {
                // an invalid root could not really sum up to anything, this is a base case to make sure we stop trying to go left / right
                // when we reach the leaves
                return;
            }
            // check sums for the root first, using top-down approach
            countSumAccumulation(root, 0, sum);

            // after we sum for the current root, step down to the left and right and do the same, this approach is pretty similar to
            // finding a substring in a string, where we simply move one char to the right compare the next N characters, move on to the
            // right, compare, and so on.
            traverseAllPaths(root.left, sum);
            traverseAllPaths(root.right, sum);
        }

        public int countSumPaths(List<Integer> elements, int sum) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);
            this.count = 0;
            traverseAllPaths(root, sum);
            return this.count;
        }
    }

    /**
     * From a tree, pick a random node, where each node has the same chance of being picked, for example in a tree of 10 total nodes, each
     * node would have a 10% chance or 1/10 of being selected.
     *
     * What we do is we start off with one random number which is between 0 and number of elements in the whole tree. After that we
     * recursively go down to the left if the index is within the boundaries of the left subtree (checking against leftSize), pick the root
     * if index equals the leftSize, or go to the right (and adjust the index before we call the recursive method). In the end the base case
     * is the one that picks the root, index will always hit the 'root index', index will eventuall equal / enter the base case where we
     * check if index == leftSize. Remember the index is in index space from [0-(elementsSize-1)]
     */
    public static final class SelectRandomNode {

        private final static Random RANDOM = new Random();

        private int calculateTreeSize(BinaryNode<Integer> root) {
            if (root == null) {
                return 0;
            }
            return 1 + calculateTreeSize(root.left) + calculateTreeSize(root.right);
        }

        private BinaryNode<Integer> randomNodePick(BinaryNode<Integer> root, int index) {
            // this is not a valid case we can simply return nil
            if (root == null) {
                return null;
            }
            // this must always be true, for each invocation otherwise the index is not within the correct boundaries, this is a sanity
            // check, but is actually deduceable, if we know we are given a valid index always, the index will always be less than the total
            // number of elements / size of the current subtree and always greater or equal to 0
            assert (index >= 0 && index < calculateTreeSize(root));

            // find how many elements there are in the left subtree, if we know how many elements we have on the left, and we know that the
            // input index will never exceed the total number of elements for the current index (which we guarantee below by clamping the
            // index when going to the right) we can know with certainty in which direction we have to go, when the index value is within
            // these 3 ranges below
            // - [0 - (leftSize - 1)] - go to the left
            // - [leftSize - leftSize] - exactly at the root
            // - [(leftSize + 1) - (total subtree size)] - go to the right
            int leftSize = calculateTreeSize(root.left);

            if (index < leftSize) {
                // if the index is within the number of elements in the left subtree go down the left subtree path, the index does not need
                // to be touched for the current left subtree, since the range of the index falls exactly in elements within the current
                // left sub tree, we adjust the index only when we have to go to the right, and we go to the right only when the index is
                // outside the boundaries of left and root i.e between [(leftSize + 1) and (total subtree size)]
                return randomNodePick(root.left, index);
            } else if (index == leftSize) {
                // if the index is exactly the left size, that means this is the root item exactly, left indexed items are only from [0 and
                // leftSize - 1], index equaling leftSize means we have to pick the root, at some point at some level this is the base case
                // which we will hit for sure, while going down, the index will become equal to the root 'index' and we stop and return
                return root;
            } else {
                // if the index falls in the right subtree, before going down the right subtree, we have to normalize the index, while the
                // current index is valid for the current tree, when we go down the right one, we have to clamp the index to be valid for the
                // right sub tree, to do that we have to calculate what the current index corresponds to in terms of the right sub tree, this
                // we can deduce by knowning that (lefSize + 1) is the lower bound index for the right sub-tree, therefore we can subtract that
                // from the current index. This will give us an index which is in the index space of the right subtree for the current root, then
                // we can call the function with the new index and the rigth subtree as the new root.

                // For example if the tree at this moment has 10 elements, and the target index was 9, the left size was 6, and we account
                // for the 1 element being the current root, then we can deduce that the right subtree has 3 elements, therefore the new
                // index must be in range [0-2], and it will be, indeed : 9 - (6 + 1) = 2
                index = index - (leftSize + 1);
                return randomNodePick(root.right, index);
            }
        }

        public BinaryNode<Integer> pickRandomNode(List<Integer> elements) {
            BinaryTree<Integer> tree = new BinaryTree<>();
            BinaryNode<Integer> root = tree.create(elements);

            int index = RANDOM.nextInt(elements.size());
            return randomNodePick(root, index);
        }
    }

    /*
     * Check if there is a valid path between two nodes in a directed graph
     *
     * To do this we can use either bfs or dfs, as we have to go through all nodes, in worse case scenario, best case is we discover the
     * target node early, but that is not guaranteed
     */
    public static final class FindPathExists {

        // private boolean checkPathExists(GraphNode<Integer> start, GraphNode<Integer> end) {
        // Queue<GraphNode<Integer>> queueStart = new LinkedList<>();
        // Queue<GraphNode<Integer>> queueEnd = new LinkedList<>();
        //
        // Set<GraphNode<Integer>> visitedStart = new HashSet<>();
        // Set<GraphNode<Integer>> visitedEnd = new HashSet<>();
        //
        // visitedStart.add(start);
        // queueStart.add(start);
        //
        // visitedEnd.add(end);
        // queueEnd.add(end);
        //
        // while (!queueStart.isEmpty() && !queueEnd.isEmpty()) {
        // start = queueStart.remove();
        // end = queueEnd.remove();
        //
        // for (GraphNode<Integer> child : start.children) {
        // if (!visitedStart.contains(child)) {
        // visitedStart.add(child);
        // queueStart.add(child);
        // }
        // }
        //
        // for (GraphNode<Integer> child : end.children) {
        // if (!visitedEnd.contains(child)) {
        // visitedEnd.add(child);
        // queueEnd.add(child);
        // }
        // }
        //
        // for (GraphNode<Integer> node : visitedStart) {
        // if (visitedEnd.contains(node)) {
        // return true;
        // }
        // }
        // }
        // return false;
        // }

        private boolean checkPathExists(GraphNode<Integer> root, GraphNode<Integer> target, Set<GraphNode<Integer>> visited) {
            if (root == null) {
                // the input root node must not be null
                return false;
            }

            if (root == target) {
                // check if they are equal, if that is the case then we have found the target node we are looking for, return true as there
                // is a
                // path between root and target
                return true;
            }

            // assume by default there is no valid path between the root and the target node, this will either remain like that for all
            // children below, or at least one child check call will return true, which is all that we need
            boolean result = false;
            for (GraphNode<Integer> child : root.children) {
                if (!visited.contains(child)) {
                    // mark the node as visited, before we continue, to avoid infinite looping/recursion, there is no point in going through
                    // the same node more than once
                    visited.add(child);

                    // keep going to the child only when it is not marked as already visited in an earlier pass, this could happen if this
                    // child is also a child to another node which did already mark this child node as visited
                    result = checkPathExists(child, target, visited);
                }

                if (result) {
                    // if any of the calls to check returns true, that means there is a path between the target and root node
                    return true;
                }
            }

            // reaching this point, meant that neither the current root, or any of it's children, or their children etc. were equal to the
            // target node, at this point we bail out, there is no path between the current root and the target node
            return false;
        }

        public boolean validatePath(List<Integer> elements, Integer start, Integer end) {
            SimpleGraphType<Integer> graph = new SimpleGraphType<>();
            List<GraphEdge<Integer>> edges = graph.edges(elements);

            List<GraphNode<Integer>> gnodes = graph.create(edges);
            Set<GraphNode<Integer>> visited = new HashSet<>();

            Optional<GraphNode<Integer>> first = gnodes.stream().filter(n -> n.value.equals(start)).findFirst();
            Optional<GraphNode<Integer>> second = gnodes.stream().filter(n -> n.value.equals(end)).findFirst();

            assert (first.isPresent() && second.isPresent());
            return checkPathExists(first.get(), second.get(), visited);
        }
    }

    /**
     * Given a list of projects and their interdependencies, resolve the order in which the projects have to be built correctly based on
     * their dependencies
     *
     * This task has one straight forward solution, and it is to use topological sort, topological sort is a way to print the nodes of a
     * graph in an ascending order of their incoming edges, meaning that at the front of the topological order would sit the nodes with 0
     * incoming edges, then 1, 2 and so on. This would guarantee we print the order of project dependencies correctly
     *
     */
    public static final class BuildOrderResolver {

        private List<String> computeBuildOrder(List<GraphNode<String>> graph) {
            // the queue here will receive the next node in the graph which has no more incoming edges
            Queue<GraphNode<String>> queue = new LinkedList<>();

            // the result holds the list of nodes in topological order, ordered front to back, at the front the nodes with the least number
            // of incoming edges - zero, and increasing
            List<GraphNode<String>> result = new LinkedList<>();

            // put in the queue all the nodes with no incoming edges, these would be the ones with which we would have to start, if there
            // are none, then there is no topological order, meaning the graph probably has cycles
            for (GraphNode<String> node : graph) {
                if (node.incoming == 0) {
                    // add node with no incoming edges
                    queue.add(node);
                }
            }

            // while the queue is not empty, meaning we have nodes to go through,
            while (!queue.isEmpty()) {
                // pop the current node, remember, they are only added to the queue if they have no incoming edges
                GraphNode<String> node = queue.remove();

                // add the node to the result
                result.add(node);

                // for each child of the current node, go and decrement the incoming edge, each child is linked to the current node,
                // therefore we have to 'remove' this incoming edge from the current node to the parent.
                for (GraphNode<String> child : node.children) {
                    child.incoming--;
                    // when incoming becomes zero or less, add the child node to the queue, the child might have more than one incoming
                    // edges, it will be added only when the last incoming edge is removed
                    if (child.incoming <= 0) {
                        queue.add(child);
                    }
                }
            }

            // transform the result to only get the values of the nodes to print
            return result.stream().map(n -> n.value).collect(Collectors.toList());
        }

        public List<String> extractBuildOrder(List<String> elements) {
            SimpleGraphType<String> graph = new SimpleGraphType<>();
            List<GraphEdge<String>> edges = graph.edges(elements);
            List<GraphNode<String>> gnodes = graph.create(edges);

            return computeBuildOrder(gnodes);
        }
    }

    /**
     * Find the shortest path between two nodes in a graph, consider each node connection in the graph to be a weight of one
     *
     * This one has one obvious solution and that is to implement the Djkstra's algorithm to find the shortest path between two nodes in a
     * graph, it is important to note that the graph has to be directed, and we have to be able to reason about cost between two nodes, i.e.
     * edge weight
     *
     */
    public static final class DijkstraNodePath {

        private static final class WeightNode<T> extends GraphNode<T> implements Comparable<WeightNode<T>> {

            protected int weight = 0;

            public WeightNode(GraphNode<T> node, int weight) {
                this.weight = weight;
                this.value = node.value;
                this.children = node.children;
                this.incoming = node.incoming;
            }

            @Override
            public int compareTo(WeightNode<T> o) {
                return this.weight - o.weight;
            }

            @Override
            public boolean equals(Object obj) {
                WeightNode<T> node = (WeightNode<T>) obj;
                return this.value.equals(node.value);
            }
        }

        private List<String> computeShortestPath(List<GraphNode<String>> nodes, GraphNode<String> start, GraphNode<String> end) {
            // this is the cornerstore of this algorithm, this is a min heap, which orders the nodes such that the ones with smallest cost
            // / weight are at the front of the queue, remember that the same node i.e. node.value can be added in this heap more than once,
            // but with different costs, since we might reach that node from multiple other nodes, but since we work with a min heap, these
            // WeightNode would be ordered by cost, so we will always pull the smallest node by cost first.
            PriorityQueue<WeightNode<String>> heap = new PriorityQueue<>();

            // visited holds nodes by identifier, that is important, since the heap might contain the same 'node' multiple times with
            // different costs
            Set<String> visited = new HashSet<>();

            // distances from a given node / node.value so far, these are the total accumulated distances, and always represent the minimum
            // distance to the specific node, or more precisely the shortest distandce to each node id <-> node.value
            Map<String, Integer> distances = new HashMap<>();

            // a mirror of the distances map, it tells us from where we came to achieve that min distance for a given node, they are both
            // updated together always, this way later on we can use previous to backtrack the shortest path
            Map<String, GraphNode<String>> previous = new HashMap<>();

            // nodes are identified by their value, that is like an id for a node, so update the initial distances, and previous, as well as
            // the heap, which at the start would contain the 'start' node we want to start from. The cost to the current start node is
            // obviously zero, add this as a 0 weight node to the heap
            distances.put(start.value, 0);
            previous.put(start.value, null);
            heap.add(new WeightNode<>(start, 0));

            // loop until we see all nodes in the graph, this is the very basic exit condition, it might not obtain, if we have sub-graphs
            // that have no connections between each other in the main graph, but the heap -> break would be our backup exit strategy
            while (visited.size() < nodes.size()) {
                // it is possible for the queue to become empty, before all nodes in the graph are visited, one way this could happen is if
                // we have two sub-graphs, part of the main graph, which have no connections between each other
                if (heap.isEmpty()) {
                    break;
                }
                // pull the current shortest path node, from the priority queue / min heap, in this case, the heap is sorted based on the
                // cost of the weight node
                WeightNode<String> node = heap.remove();

                // we can check if it has been visited before first, based on the value of the node, if it has it means that it was present
                // already in the heap with a smaller total cost / weight, so any further presence of the same node would be with higher
                // cost / weight we do not want to consider it
                if (visited.contains(node.value)) {
                    continue;
                }
                // add / mark the current node as visited, reaching here means the current node was not visited so far, meaning this is the
                // first time we pull it from the heap, meaning we are pulling the one with the smallest cost, remember the heap might
                // contain multiple nodes with the same value, but different 'costs', due to the fact that in a graph we might reach the
                // same 'node' from multiple parent nodes. By saying 'node' we really mean 'node.value' - that is the identifier for a node
                visited.add(node.value);

                for (GraphNode<String> child : node.children) {
                    // extract the current path to the current child so far, or default to some big value, creaful, we are not using
                    // INT_MAX, due to the fact that it might overflow if we add anything more than 0 to it
                    Integer current = distances.getOrDefault(child.value, 9999);

                    // compute the 'would be' distance from the current node to the current child, this is the cost of the current node so
                    // far + the weight cost of the current child, care with overflow, if using INT_MAX
                    Integer possible = distances.getOrDefault(node.value, 9999) + 1;

                    // in case the possible new distance is actually smaller than the distance stored so far, we update the distance stored
                    // so far, update the previous node we come from, and add the child to the heap
                    if (possible < current) {
                        // update from which node we came to the current child, since we found better / lower cost path value
                        previous.put(child.value, node);

                        // update the distances to the current child node as we have found shorter path to it than what we had so far
                        distances.put(child.value, possible);

                        // every time we find path to a given node which is shorter (so far) we add it to the heap, it would be 'sorted'
                        // based on it's weight into the heap, note we add the total 'weight' not just the 'child.weight'
                        heap.add(new WeightNode<>(child, possible));
                    }
                }
            }

            // collect the path from start - end
            List<String> path = new LinkedList<>();
            GraphNode<String> target = end;

            do {
                // starting off from the end node, track back using the previous map, which tells us from where we came to achieve the
                // current path, remember previous and distances were updated together meaning previous contains the shortest cost / path to
                // a given node / node.value
                path.add(target.value);

                // move backwards to previous node
                target = previous.get(target.value);
            } while (target != null && target != start);

            // return the path, we might want to validate if the start / end correspond to the
            return path;
        }

        public List<String> resolveShortestPath(List<String> elements, String start, String end) {
            SimpleGraphType<String> graph = new SimpleGraphType<>();
            List<GraphEdge<String>> edges = graph.edges(elements);
            List<GraphNode<String>> gnodes = graph.create(edges);

            GraphNode<String> first = null;
            GraphNode<String> second = null;

            for (GraphNode<String> node : gnodes) {
                if (node.value.equals(start)) {
                    first = node;
                } else if (node.value.equals(end)) {
                    second = node;
                }
            }

            return (first != null && second != null ? computeShortestPath(gnodes, first, second) : null);
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

        // RetrievalPrefixTree<Integer> trie = new RetrievalPrefixTree<>();
        // RetrievalPrefixTree.TriePrefixNode<Integer> root = trie.insert(null, "word", 0);
        // trie.insert(root, "wordle", 1);
        // trie.insert(root, "static", 2);
        // trie.insert(root, "ant", 3);
        // trie.insert(root, "tan", 4);
        // trie.insert(root, "antlers", 5);
        // trie.insert(root, "tank", 6);
        // trie.insert(root, "statistics", 7);
        // trie.insert(root, "an", 8);
        // trie.insert(root, "antimeasure", 9);
        // trie.delete(root, "antlers");
        // trie.search(root, "an");
        // trie.search(root, "antlers");
        // trie.search(root, "antimeasure");

        // FirstCommonAncestor anc = new FirstCommonAncestor();
        // anc.findFirstAncestor(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 3, 2), 2, 13);

        // CheckBinarySearchTree check = new CheckBinarySearchTree();
        // check.validateBinarySearchTree(Arrays.asList(10, 9, 15, 4, 13, 8, 19));
        // check.validateBinarySearchTree(Arrays.asList(10, 5, 15, 4, 8, 13, 25));

        // CheckBalancedTree balanced = new CheckBalancedTree();
        // balanced.checkBalancedTree(Arrays.asList(10, 9, 15, 4, 13, 8, 19));
        // balanced.checkBalancedTree(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 2, 1, null, null, null, null, null, null, 0, 6));

        // ListTreeDepts depths = new ListTreeDepts();
        // depths.createTreeDepts(Arrays.asList(10, 9, 15, 4, 13, 8, 19));
        // depths.createTreeDepts(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 2, 1, null, null, null, null, null, null, 0, 6));

        // MinimalBinaryTree min = new MinimalBinaryTree();
        // min.createMinimalTree(Arrays.asList(1, 2, 4, 5, 8, 9, 10, 15, 19, 25));

        // FindSuccessorNode succ = new FindSuccessorNode();
        // succ.nextInOrder(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2), 2);
        // succ.nextInOrder(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2), 13);

        // DetectExistingSubtree subtree = new DetectExistingSubtree();
        // subtree.checkSubtreeValidity(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2), Arrays.asList(4, 1, 2));
        // subtree.checkSubtreeValidity(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2), Arrays.asList(4, 3, 2));
        // subtree.checkSubtreeValidity(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2), Arrays.asList(9, 4, 13));
        // subtree.checkSubtreeValidity(Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2), Arrays.asList(10, 9, 15, 4, 13, 8, 19, 1, 2));

        // FindSumPath finder = new FindSumPath();
        // finder.countSumPaths(Arrays.asList(5, 1, 10, 4, 3, 13, 25, 1, 3, 2, 4, -8, 8, 15, -20, 4, 0, 0, -2), 5);

        // SelectRandomNode randomer = new SelectRandomNode();
        // randomer.pickRandomNode(Arrays.asList(11, 9, 15, 4, 10, 8, 19, 2, 5));
        // randomer.pickRandomNode(Arrays.asList(11, 9, 15, 4, 10, 8, 19, 2, 5));
        // randomer.pickRandomNode(Arrays.asList(11, 9, 15, 4, 10, 8, 19, 2, 5));
        // randomer.pickRandomNode(Arrays.asList(11, 9, 15, 4, 10, 8, 19, 2, 5));

        // SimpleGraphType<Integer> graph = new SimpleGraphType<>();
        // List<GraphNode<Integer>> gnodes = graph.create(graph.edges(Arrays.asList(0, 1, 0, 5, 0, 4, 1, 4, 1, 3, 2, 1, 3, 4, 3, 2)));
        // List<GraphNode<Integer>> groots = Arrays.asList(gnodes.get(0));
        // graph.breadth(groots, 0);
        //
        // gnodes = graph.create(graph.edges(Arrays.asList(0, 1, 0, 5, 0, 4, 1, 4, 1, 3, 2, 1, 3, 4, 3, 2, 3, 1, 1, 0, 4, 0, 5, 0)));
        // groots = Arrays.asList(gnodes.get(0));
        // graph.bipath(graph.breadth(groots, 0), graph.breadth(groots, 3));

        // FindPathExists pather = new FindPathExists();
        // pather.validatePath(Arrays.asList(0, 1, 0, 2, 2, 5, 2, 0, 2, 1, 2, 3, 2, 4, 3, 4, 2, 4, 4, 5, 2, 5, 4, 6, 5, 6, 6, 4, 6, 5), 0,
        // 6);

        // BuildOrderResolver builder = new BuildOrderResolver();
        // builder.extractBuildOrder(Arrays.asList("a", "d", "f", "b", "b", "d", "f", "a", "d", "c", "e", null));
        // builder.extractBuildOrder(Arrays.asList("f", "a", "f", "b", "k", "b", "k", "j", "f", "c", "b", "c", "a", "c", "j", "c", "c",
        // "d"));

        DijkstraNodePath dijkstra = new DijkstraNodePath();
        dijkstra.resolveShortestPath(Arrays.asList("f", "k", "f", "a", "f", "b", "k", "b", "k", "j", "f", "c", "b", "c", "a", "c", "j", "c",
                        "c", "d", "j", "d"), "f", "d");
        return;
    }
}
