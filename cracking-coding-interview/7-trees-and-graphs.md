# Introduction

# Trees

They are a recursive structure which can be defined by having a node with
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
-   referencing the node's parent while iterating through the tree is usually
    implemented using recursion

A sample representation of a tree node might look like this, note that a tree
class is rarely needed, it does not really add any significant value, since
passing the root Node around, to represent the tree is simply enough, a Tree
class might be useful to attach an interface to the tree, actions which might be
done on a tree, but that is usually just it.

An N-arry tree node could look like this

```java
    class Node {
        Integer value;
        Node[] children;
    }
```

A binary tree node could look like this

```java
    class Node {
        Integer value;
        Node left;
        Node right;
    }
```

## Types

Several common types of trees exist, and it is important to be able to
distinguish them, since each have different properties and assumptions that can
be made.

Before solving a problem be sure to understand what type of tree is the problem
or solution requiring so it can be solved, each tree type has different
properties

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
    meaningful or special way

-   binary search trees - these are binary trees which are constructed in a very
    special way, where the tree nodes are ordered, each node on the left is usually
    less than or equal to the it's parent, each node to the right is strictly bigger
    than it's parent. This condition must be met for all sub-children of a given
    node, recursively, not just the immediate children of a given node. See in the
    example below how 10 is not just bigger than 5, but also than 5's children too,
    3 and 6

    ```txt
               10
            /      \
           5       20
        /     \   /  \
       3      6  15  25
    ```

-   binary heaps - these are special type of binary trees, similarly to the
    search trees, but the order of elements is such that either the min element or
    the max, depending on the type of the tree, is at the top of the tree, in the
    root node, elements below each node are all bigger (min) or smaller (max) than
    the root, this is true for each node recursively. See how no matter which child
    you take below the root, they are all bigger (in this example, for min heap).

    ```txt
                1
            /      \
           5       20
        /     \   /  \
       8      9  25  35
    ```

-   balanced trees - these are trees where the height of the left and right sub
    tree do not differ with more than 1 level. Balanced trees might be binary, but
    any n-arry tree could be really. Example of balanced trees are red black trees,
    or AVL trees, these are examples of balanced binary search trees. See below,
    how the height of the left sub tree of the root is 3 and the right is 2, the
    difference is no more than 1

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
    the only nodes that have no children are the leaf nodes

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

-   left, root, right - in order traversal, because the tree nodes would be
    visited in ascending order.

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

-   root, left, right - pre order traversal, where root is first then, ordered are
    visited only the children

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

For example, adding a new element which is always bigger or always smaller than the
previous added one, would form a linked list of nodes, making only one side of
the tree have nodes

Here first 10 was inserted, then 5, then 3, and if we keep adding smaller and
smaller elements, 2, 1, 0 etc, the tree would keep forming a linked list of
nodes. These elements can be re-structured (called balancing) to make the
structure more like a tree, which would have a search of O(log(n)), instead of
the O(n) which is what it is going to be if the tree looks like a list.

```txt
          10
        /
      5
    /
   3
```

## Max

Finding the max element in a BST is simply following all right subtrees from the
root of the tree, the max element would be contained in the right most
child/leaf node starting from the root

```java
    Node max(Node root) {
        // to find the max node, simply drill down the right subtree of the current node, until there is no more right nodes left
        while (root != null && root.right != null) {
            root = root.right;
        }
        return root;
    }
```

## Min

Finding the min element in a BST is simply following all left subtrees from the
root of the tree, the min element would be contained in the left most child/leaf
node starting from the root

```java
    Node min(Node root) {
        // to find the min node, simply drill down the left subtree of the current node, until there is no more left nodes left
        while (root != null && root.left != null) {
            root = root.left;
        }
        return root;
    }
```

## Insertion

This process is very similar to searching a node with a value, we go down down 3
paths, either the value is bigger, smaller or equal to the current root's value,

-   if bigger - go to the right
-   if smaller - go to the left
-   if equal - terminate, duplicates are not allowed

If we never find an equal element, the recursion will eventually terminate with
root == null, meaning we have drilled down the tree into a spot which conforms
to the rule of BST, and is empty, create a node, return it up the call stack.

The return is crucial, it would correctly attach the new node to it's immediate
parent, since we recursed down, and now return back the same way, due to the
call stack unwinding.

```java
    Node insert(Node root, int value) {
        if (root == null) {
            // if we drill down till this point it means that the correct spot for the new element was found, and we create the node and
            // return it, in the assignments below that node will be assigned correctly to it's parent node, in other words, the
            // recursive calls below took right/left paths such that they lead us to a null child, which is the spot at which this new
            // node must be linked, returning it here, and assignment below will ensure that linkage is made to the parent of this new
            // node, to either the right or the left child.
            Node node = new Node();
            node.value = value;
            return node;
        }

        if (value > root.value) {
            // we have to go down to the right, this recursive call will either return the same node, if the value is equal to
            // root.right, or will drill down until there is not root.right anymore, at which point the node will be created with the
            // new element and returned back
            root.right = insert(root.right, value);
        } else if (value < root.value) {
            // we have to go down to the left, this recursive call will either return the same node, if the value is equal to
            // root.left, or will drill down until there is not root.left anymore, at which point the node will be created with the
            // new element and returned back
            root.left = insert(root.left, value);
        }
        // return the node always, we only recurse inside the if/else, this case here also handlers equals, but in all 3 cases we want to return
        // the original node anyway
        return root;
    }
```

## Delete

Deletion is a bit more convoluted, but can still be split into a few base
cases. However first the element to be deleted has to be located in the tree,
only if it exists can we proceed to try to delete it.

-   if deleting a node without children, simply return null
-   if deleting a node with only a left subtree, return that left subtree
-   if deleting a node with only a right subtree, return that right subtree
-   if deleting a node with both subtrees existing, two different options

    -   drill down the left subtree and find the max element of root.left

    ```txt
               10
            /      \
           5       20
        /     \  /
       3      7 15
    ```

    -   drill down the right subtree and find the min element of root.right
    -   found element will either be the root.left (max case) or root.right (min
        case), when they have no right (max case) or left (min case) child nodes, or
        the most right leaf node (max case), or the most left leaf node (min case).
    -   the found element's value we swap with the root, the links of the found
        element have to be maintained, and not lost, we have one of two cases
        -   in the max case the found element would not have any right subtrees, only left ones,
        -   in the min case the found element would not have any left subtrees only right ones
    -   attach the left/right of the found to the parent of the found element

Deleting the node with value 5, we have to first find the node we want to
delete, in this case let's assume we have done that. (This example does not
cover all cases, it is just a small showpiece)

```txt
           10
        /      \
       5       20
    /     \  /
   3      7 15
  /
 1
```

It has two children, we choose to find the max node from the left subtree of the
node to delete (5), which is the node with value 3. It is the max since it has
no right children, if it had we would have gone down to the right

```txt
           10
        /      \
       5       20
    /     \  /
   3      7 15
  /
 1
```

The found node is immediate child of the node to delete (5). Now we have to
detach the found node (3) from the tree, therefore node (5).left = (3).left
(Since we went with max case, we know that there could be nothing to the right
of node 3, otherwise we would have gone down that route, but there might be to
the left)

```txt
           10
        /      \
       5       20
    /     \  /
   /      7 15
  /
 1
```

Finally we set the value of the found node (3) to the value of the node we want
to "delete", notice how we did not delete the node which held the value 5, but
exchanged the values, and deleted the child of the node we want to delete (5).
The BST property is maintained

```txt
           10
        /      \
       3       20
    /     \  /
   /      7 15
  /
 1
```

```java
    Node delete(Node root, T value) {
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
                Node curr = root.left;
                Node prev = root;

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
```

## Searching

This is in any case a case which is covered in the delete and insert operations,
when inserting the found case means we can not insert in the tree, in delete
operation the found case means we found the node to delete. In the search case
the found case would simply return the node it had found

Use iterative method, simply because it is simpler to return a value, from it,
in this case we only drill down one single path, without doing any tree
modifications or complex operations.

```java
Node serach(Node root, Integer value) {
    while(root != null) {
        if (value > root.value) {
            root = root.left;
        } else if (value < root.value) {
            root = root.right;
        } else {
            return root;
        }
    }
    return null;
}
```

## Balancing

As we know there are ways to make a BST tree imbalanced, when inserting elements
in specific order, that can be malformed into linked list like structure. A
balancing technique we will look at is called subtree rotation used in AVL
trees, which are special type of very well balanced trees, unlike Red Black
trees, AVL trees always have a height difference at each subtree by no more than
1 level

There are two major rotations that can be done on a branch with a root, left
and right. We do the counter rotation when the tree is heavy on one side

-   left rotation - when branch is heavy on the right - i.e left(root)
-   right rotation - when branch is heavy on the left - i.e right(root)

There are two other ones, which can be done combining the two major ones, in two
separate steps,

-   left-right - combination of the two major rotations, first left, then right - i.e root.left = left(root.left); right(root)
-   right-left- combination of the two major rotations, first right, then left - i.e root.right = right(root.right); left(root)

1. Right-Right rotation - we can see that at the level of node 10, the height of
   the left subtree is 3, the left one is 1, the balance factor is 2 == (3 - 1).
   We rotate around the node 10, in this case, where the imbalance is found
    - the root will become the node 3 (by reference)
    - the old right of node 3 becomes the new left of node 10
    - the new right of node 3 becomes the old root 10 node

```txt
       10                 3
      /  \              /   \
     3   15    ->     1      10
   /  \              /      /  \
  1    4            0      4    15
 /
0
```

2. Left-Left rotation - we can see that at the level of node 10, the height of
   the left subtree is 1, the right one is 3, the balance factor is -2 == (1 - 3).
   We rotate around the node 10, in this case, where the imbalance is found
    - the root will become the node 15 (by reference)
    - the old left of node 15 becomes the new right of node 10
    - the new left of node 15 becomes the old root 10 node

```txt
       10                 15
      /  \              /   \
     3   15    ->     10    20
        /   \        /  \     \
       13   20      3   13    25
             \
             25
```

3. Left-right rotation - here we first perform left rotation around node 3, then
   around the main node 10, where the actual imbalance is. It is again 2 == (3 - 1).
    - First a left rotation around the node 3,
        - the root will become the node 4 (by reference)
        - the old left of node 4 becomes the new right of node 3
        - the new left of node 4 becomes the old root 3 node
    - Second a right rotation around the node 10
        - the root will become the node 4 (by reference)
        - the old right of node 4 becomes the new left of node 10
        - the new right of node 4 becomes the old root 10 node

```txt
       10                 10                  4
      /  \              /   \               /   \
     3   15    ->     4      15    ->      3     10
   /  \              / \                  /      /  \
  1    4            3   5                1      5   15
        \         /
         5       1
```

4. Right-left rotation - here we first perform right rotation around node 15,
   then around the main node 10, where the actual imbalance is. It is again -2
   == (1 - 3)
    - First a right rotation around the node 13
        - the root will become the node 13 (by reference)
        - the old right of node 13 becomes the new left of node 15
        - the new right of node 13 becomes the old root 15 node
    - Second a left rotation around the node 10
        - the root will become the node 13 (by reference)
        - the old left of node 13 becomes the new right of node 10
        - the new left of node 13 becomes the old root 10 node

```txt
      10               10                  13
    /   \             /  \                /  \
   3    15    ->     3   13      ->      10   15
       /  \             /  \            /  \   \
      13  20           11  15          3   11  20
     /                       \
    11                       20
```

```java
    Node right(Node root) {
        // right rotation is done to offset left heavy tree branches, right rotation means that left subtree would "move" or "shift" to
        // the right, or in other words the root.left would become the new root, and the old root would become the right, of the new
        // root. This way the subtrees of the new node would be balanced

        // remember the left node, it will become our new root at the end of the function the new root is returned
        Node left = root.left;

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

    Node left(Node root) {
        // left rotation is done to offset right heavy tree branches, left rotation means that right subtree would "move" or "shift" to
        // the left, or in other words the root.right would become the new root, and the old root would become the left of the new
        // root. This way the subtrees of the new node would be balanced

        // remember the right node, it will become our new root at the end of the function the new root is returned
        Node right = root.right;

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
```

```java
    Node rebalance(Node root, T value) {
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
```

Insertion and deletion also need slight modification to include the re balancing
of the tree after the operation, all parents up to the root, have to be visited,
and update their heights at the very least, also check for imbalance

```java
    Node insert(Node root, int value) {
        // generic insert and lookup logic for bst
        if (root == null) {
            Node node = new Node();
            node.value = value;
            return node;
        }

        if (value > root.value) {
            root.right = insert(root.right, value);
        } else if (value < root.value) {
            root.left = insert(root.left, value);
        } else {
            return root;
        }

        // reaching at this point, we have very likely inserted an element, therefore we need to re-balance the tree, the return of
        // re-balance might be the same root, if no re-balance is required, or one of it's left or right children depending on what
        // rotation was done during balancing
        return rebalance(root, value);
    }
```

```java
    Node delete(Node root, T value) {
        if (root == null) {
            return null;
        }

        if (value.compareTo(root.value) > 0) {
            root.right = delete(root.right, value);
        } else if (value.compareTo(root.value) < 0) {
            root.left = delete(root.left, value);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left != null && root.right == null) {
                return root.left;
            } else if (root.right != null && root.left == null) {
                return root.right;
            } else {
                Node curr = root.left;
                Node prev = root;

                while (curr != null && curr.right != null) {
                    prev = curr;
                    curr = curr.right;
                }
                if (prev == root) {
                    prev.left = curr.left;
                } else {
                    prev.right = curr.left;
                }
                root.value = curr.value;
            }
        }

        // try to check if re-balance is required, all the way, to the root, this is outside the else, because we have to post recurse, to
        // the root, to at the very least update the heights of each parent along the way, if a deletion has occurred, and also check if
        // the balance is okay.
        return rebalance(root, value);
    }
```
