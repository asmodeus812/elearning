# Introduction

# Trees

They are a recursive structure which can be explained by having a node with
n-children. Each of those children can also have n-children and so on. Nodes
might have a link to their parent but that is strictly implementation dependent
and is not required. Important to note:

-   trees have no loops within them, otherwise they would be graphs
-   each tree has a root node, which has no ancestors
-   each node in the tree including the root might have 0 or more children
-   a tree with a max of n-number of chilren is called n-arry tree, a node with 2
    children, for example would be 2-ary tree, or binary tree, with 10, 10-ary
    tree
-   a node without children is called a leaf node, the root can be both a leaf
    node and a root in a tree

A sample representation of a tree node might look like this, note that a tree
class is rarely needed, it does not really add any significant value, since
passing the root Node around, to represent the tree is simply enough, a Tree
class might be useful to attach an interface to the tree, actions which might be
done on a tree, but that is usually just it.

```java
    class Node {
        String name;
        Node[] children;
    }
```

## Types

Several common types of trees exit, and it is important to be able to
distinguish them, since each have different properties and assumptions that can
be made.

Before solving a problem be sure to understand what type of tree is the problem
or solution requiring so it can be solved

-   n-ary trees - already mentioned, these are the general case for trees, where
    each node, can have 0 to n number of children.

-   tries - those are type of n-ary trees, which come up very often, and are
    usually used to find word prefixes, tries are sometimes used to store the entire
    English language, each node in the tree has at most 26 children, 1 for each
    letter of the alphabet. Each path of nodes which forms a valid word, is
    terminated with special terminating node which denotes that.

    ```txt
              root
            /      \
           M        L
        /     \    /  \
       A       Y  I    O
     /           /      \
    N           E        W
    ```

-   binary trees - these are just trees where each node has at most two children,
    meaning it can have 0, 1 or 2. Nodes are not arranged or ordered in any
    meaningful way

-   binary search trees - these are binary trees which are constructed in a very
    special way, where the tree nodes are ordered, each node on the left is
    usually less than or equal to the it's parent, each node to the right is
    strictly bigger than it's parent. This condition must be met for all
    sub-children of a given node, right down to the leafs of the tree, not just the
    immediate children of a given node

    ```txt
               10
            /      \
           5       20
        /     \   /  \
       3      6  15  25
    ```

-   binary heaps - these are special type of binary trees, similarly to the search
    trees, but the order of elements is such that either the min element or the
    max, depending on the type of the tree, is at the top of the tree, in the root
    node, elements below each node are all bigger (min) or smaller (max) heap, this
    is true for each node recursively

    ```txt
               1
            /      \
           5       20
        /     \   /  \
       8      9  25  35
    ```

-   balanced trees - these are trees where the height of the left and right
    sub tree do not differ with more than 1 level Example of balanced trees are red black
    trees, or avl trees

    ```txt
               10
            /      \
           5       20
        /     \
       3      6
    ```

-   complete binary trees - are these trees which where each level of the tree is
    completely filled, except maybe the last level.

    ```txt
               10
            /      \
           5       20
        /     \  /
       3      7 15
    ```

-   full binary trees - are these trees which where each node has either 0 or 2
    children, and nothing in between

    ```txt
               10
            /      \
           5       20
        /     \
       3      7
    ```

-   perfect binary trees - are these where all interior nodes, have children,
    the only nodes that have no children are the leaf nodes,

    ```txt
               10
            /      \
           5       20
        /     \  /    \
       3      7 15    23
    ```

## Traversal

There are a few ways to traverse a binary tree, or n-ary tree, usually that
involves different visiting permutations of the nodes. For example for a binary
tree we can visit the 3 nodes, in 6 different ways, in total

-   root, left, right
-   root, right, left
-   left, root, right
-   left, right, root
-   right, left, root
-   right, root, left

Some of the above have names, since they come in very often, for a binary search
tree, we have post, pre and in order traversal

-   left, root, right - in order traversal, because the elements would be sorted,
    in ascending order

    ```java
        void order(Node node) {
            if (node == null) {
                return;
            }
            order(node.left);
            visit(node.root);
            order(node.right);
        }
    ```

-   root, left, right - pre order traversal, where root is first then ordered are
    only the children

    ```java
        void order(Node node) {
            if (node == null) {
                return;
            }
            visit(node.root);
            order(node.left);
            order(node.right);
        }
    ```

-   left, right, root - post order traversal, where children are ordered, then
    lastly the root is visited

    ```java
        void order(Node node) {
            if (node == null) {
                return;
            }
            order(node.left);
            order(node.right);
            visit(node.root);
        }
    ```

## Binary search trees

The most simple implementation of a binary search tree, is one that simply
conform to the rule, and does not try to keep the tree balanced, which in some
cases can lead to linear search time complexity, since the tree can get malformed into
a linked list, which can very often happen, when the tree is not kept balanced.
For example, adding a new element which is always bigger or smaller than the
previous added one, would form a linked list of nodes.

```java

```
