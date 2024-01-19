# Introduction

# Trees

They are a recursive structure, which can be defined by having a node with
n-children. Each of those children can also have n-children and so on. Nodes
might have a link to their parent but that is strictly implementation dependent
and is not required. Important to note:

-   trees have no loops within them, otherwise they would be graphs
-   each tree has a root node, which has no ancestors
-   each node in the tree including the root might have 0 or more children
-   a tree with a max of n-number of children is called n-ary tree, a node with 2
    children, for example would be 2-ary tree, or binary tree, with 10, 10-ary
    tree
-   a node without children is called a leaf node, the root can be both a leaf
    node, and a root in a tree
-   referencing the node's parent while iterating through the tree is usually
    implemented using recursion

A sample representation of a tree node might look like this, note that a tree
class is rarely needed, it does not really add any significant value, since
passing the root Node around, to represent the tree is simply enough, a Tree
class might be useful to attach an interface to the tree, actions which might be
done on a tree, but that is usually just it.

An N-ary tree node could look like this

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
    less than or equal to it's parent, each node to the right is strictly bigger
    than its parent. This condition must be met for all sub-children of a given
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

-   balanced binary search trees - these are trees where the height of the left
    and right sub tree do not differ with more than 1 level. Balanced trees
    might be binary, but any n-ary tree could be really. Example of balanced trees
    are red black trees, or AVL trees, these are examples of balanced binary search
    trees. See below, how the height of the left sub tree of the root is 3 and the
    right is 2, the difference is no more than 1

    ```txt
               10
            /      \
           5       20
         /   \
        3     6
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

### Binary search trees

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

#### Max

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

#### Height

```java
    int height(Node root) {
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
```

#### Min

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

#### Insertion

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

#### Delete

Deletion is a bit more convoluted, but can still be split into a few base
cases. However first the element to be deleted has to be located in the tree,
only if it exists can we proceed to try to delete it.

-   if deleting a node without children, simply return null
-   if deleting a node with only a left subtree, return that left subtree
-   if deleting a node with only a right subtree, return that right subtree
-   if deleting a node with both subtrees existing, two different options

    -   drill down the left subtree and find the max element of root.left
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
      / \     /
     3   7   15
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
      / \     /
     3   7   15
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
      / \     /
     /   7   15
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
      / \     /
     /   7   15
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

        if (value > root.value) {
            // drill down the right subtree until we find equal element, note the assignment, this allows us to propagate changes to
            // the correct subtree, allowing us to re-link a non-immediate child node with the current root, sub recursive calls can
            // return child nodes deep in the tree, and assign them to the current node, making linking very easy, without having to
            // keep track of parent nodes.
            root.right = delete(root.right, value);
        } else if (value < root.value) {
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

#### Searching

This is in any case a case which is covered in the delete and insert operations,
when inserting the found case means we can not insert in the tree, in delete
operation the found case means we found the node to delete. In the search case
the found case would simply return the node it had found

Use iterative method, simply because it is simpler to return a value, from it,
in this case we only drill down one single path, without doing any tree
modifications or complex operations.

```java
Node serach(Node root, Integer value) {
    // drill down until either a node with a value equaling value is found, or
    // there is no more nodes to look at, while conforming to the BST rules
    while(root != null) {
        if (value > root.value) {
            // drill down the left subtree when the value is bigger than the root
            root = root.left;
        } else if (value < root.value) {
            // drill down the right subtree when the value is smaller than the root
            root = root.right;
        } else {
            // there was a root node found that equals the target value we search for
            return root;
        }
    }
    // at this point no node equaling the target value was found, therefore it
    // was not currently in the tree, if the tree is a complete BST.
    return null;
}
```

#### Balancing

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
    int height(Node root) {
        // height is 0 if the node does not exist
        if (root == null) {
            return 0;
        }
        // return the stored height, for the node
        return root.height;
    }

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
            if (balance > 1 && value < root.left.value) < 0) {
                // first start off from the balance value, if it is positive, then the imbalance is in root.left, then we check if the
                // inserted value is smaller than the left node's value, if yes, then the new node was inserted to the left of the left
                // subtree. meaning the tree is left-left heavy, therefore we do the (opposite rotation) right rotation
                return right(root);
            }

            // right heavy && value > root.right.value - right-right
            if (balance < -1 && value > root.right.value) {
                // first start off from the balance value, if it is negative, then the imbalance is in root.right, then we check if the
                // inserted value is bigger than the right node's value, if yes, then the new node was inserted to the right of the
                // right subtree. meaning the tree is right-right heavy, therefore we do the (opposite rotation) left rotation
                return left(root);
            }

            // left heavy && value > root.left.value - left-right
            if (balance > 1 && value > root.left.value) {
                // first start off from the balance value, if it is positive, then the imbalance is in root.left, then we check if the
                // inserted value is bigger than the left node's value, if yes, then the new node was inserted to the right of the left
                // subtree. meaning the tree is left-right heavy, therefore first we left rotate, which will morph root.left case into a
                // left-left, after than we do a right rotate (same as left-left case above)
                root.left = left(root.left);
                return right(root);
            }

            // right heavy && value < root.right.value - right-left
            if (balance < -1 && value < root.right.value) {
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

        if (value > root.value) {
            root.right = delete(root.right, value);
        } else if (value < root.value) {
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

### Binary heaps

Binary heap tree can be one of two types, a max or a min binary heap tree. Those
are special types of binary trees, where the structure of the tree is such that
the Min or the Max element in the tree is always at the top, and all elements
below it are strictly smaller (max) or bigger (min) heap tree type.

They are usually represent in a dynamic array, but can be done in the usual
left/right pointer style nodes, that regular trees use. The reason to use a
standard array is due to the operations such as insert and delete, which usually
start off from the last element in the tree, and in arrays we have a very fast
way to find, which one is the last element i.e. simply `array[array.len - 1]`

A Min heap might look like this.

```txt
         10
        /  \
      15   20
     /  \
    33  17
```

A Max heap might look like this.

```txt
         10
        /  \
       5    2
     /  \
    3    1
```

```java
    // the type of the heap as simple enum, heap type can not change during heap
    // operations, it remains constant for the lifetime of the heap
    public enum HeapType {
        MIN, MAX
    }

    // sample heap representation as a dynamic array, due to fast insert and
    // look up operations at desired indices, i.e. for child / parent elements
    List<Integer> heap = new ArrayList<>();
```

#### Indexing

We have to worry about 3 types of indexing in a heap, finding the parent, the
left or the right child of a given node.

-   left - the left child of each heap element is always an odd number, it is
    derived from `(2 \* i) + 1`
-   right - the right child of each heap node is always an even number, it is
    derived from `(2 \* i) + 2`
-   parent - the parent, using the two equations above, can be derived, by
    simply reversing them, check if the number is odd/even to know, the child index
    you have, derived from `(child - (child % 2 == 0 ? 2 : 1)) / 2`
-   max/min - the very root of the heap, it's index is simply 0, always, we will
    find the max/min element to be the first element of the heap

```java
    Integer peek(List<Integer> heap) {
        if (heap.isEmpty()) {
            // an empty heap has nothing to peek at
            return null;
        }
        // the element of a heap that can be looked at is always at the top, either the smallest, or the biggest in a heap
        return heap.get(0);
    }

    int parent(int child) {
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
```

#### Swapping

Since it is often used operation in a heap, it is good to show what it does,
simply put swap the two elements located at the input indices in the heap.

```java
    void swap(List<T> heap, int l, int r) {
        // simple method to swap elements located on two indices
        T c = heap.get(r);
        T p = heap.get(l);

        // exchange the elements at the two index locations in the heap array
        heap.set(r, p);
        heap.set(l, c);
    }
```

#### Inserting

To insert in a binary heap tree we have to do two major things.

-   the new element to insert goes to the end of the heap
-   the new element is bubbled up to the correct position

The bubbling is based on the tree type, but briefly, we check the current
element with its parent, and swap if the conditions below are met

-   for min heaps = if smaller than the parent we swap them
-   for min heaps = it bigger than the parent we swap them

```java
    Integer insert(List<Integer> heap, Integer value) {
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
            Integer parent = heap.get(prev);

            // find if the new element is bigger, smaller or equal to the parent
            int diff = value - parent;

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
```

#### Deleting

Deleting an element from the tree is a bit more convoluted, what we do here is
to take the last element from the heap, put it at the very root / top of the
heap and then gradually try to drop it down until a correct spot for it is
found. We can also remember the old root element and return it (optional).

What is important here to take note of is how we decide to move down the
element. On each step the last element we choose would either go down the tree
or stop, if it stops then it has found the correct place. To go down we can
follow these rules for the two types of trees

-   if we have min heap - for min heap we know that smaller elements would be
    closer to the top than bigger ones, therefore we have to find the smallest
    element between the root, left or right if the root happens to be the smallest,
    it is at the correct place, otherwise swap the root with the smaller one between
    left or right. The new root becomes the index of the child with which we swapped

-   if we have max heap - for min heap we know that bigger elements would be
    closer to the top than smaller ones, therefore we have to find the biggest
    element between the root, left or right if the root happens to be the
    biggest, it is at the correct place, otherwise swap the root with the bigger
    one between left or right. The new root becomes the index of the child with
    which we swapped

```jav
    Integer delete(List<Integer> heap) {
        // there is nothing to delete from an emtpy heap in the first place
        if (heap.isEmpty()) {
            return null;
        }

        // first get the last element from the heap and swap with with the root, the last element will then be bubbled down, and the
        // root returned from this function as result
        int last = heap.size() - 1;
        swap(heap, last, 0);

        // the head or root of the heap is now the last one, after the swap, just remember it, and remove it from the heap
        Integer head = heap.get(last);
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
                if (left < heap.size() && heap.get(left) < heap.get(base)) {
                    // left becomes the smaller between base and left
                    base = left;
                }

                // we first compare the value of the 'base' index with it's right child, if the right child is smaller than the 'base',
                // we update the 'base' index to point to the right child index
                if (right < heap.size() && heap.get(right) < heap.get(base)) {
                    // right becomes the smaller between base and right
                    base = right;
                }
            } else if (type == HeapType.MAX) {
                // we first compare the value of the 'base' index with it's left child, if the left child is bigger than the root, we
                // update the 'base' index to point to the left child index
                if (left < heap.size() && heap.get(left) > heap.get(base)) {
                    // left becomes the bigger between base and left
                    base = left;
                }

                // we first compare the value of the 'base' index with it's right child, if the right child is bigger than the 'base',
                // we update the 'base' index to point to the right child index
                if (right < heap.size() && heap.get(right) > heap.get(base)) {
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
```

### Tries

Also called prefix trees, the word trie comes from the word Retrieval, they are
used to store words, and look up if a word is present in O(M) time, where M is
the length of the word, unlike regular trees that store each entire words in a
each node, tries are more efficient. Words are represented as with their
prefixes

These are a type of n-ary trees, where the n in this case is the number of
characters in an alphabet, for the English one that would be 26. Each node
BESIDES the very root of the Trie represents not only a node with children, but
a character as well, even though that is implicitly done.

Each root will have up to the n-children, but not all children are going to
exist at the same time, see example below. However to denote a word trie nodes
usually have a boolean flag, which tells us if the path to that node forms a
word. (e.g. if we have the word bigger, but the word big was not added, to the
trie, the path big would exist but it would not form the word big, until we
insert explicitly the word big)

A trie that represents a few words, might look like that, note how the root
contains a lot of nodes, but the children might just have handful, it is not
mandated that each level is fully filled (with in 26 nodes, for the English
example)

```txt
         *
       / | \ .......... \
      a  b  c           q
     /   |   \        /   \
    n    i    a      u     t
   /     g   / \    /      |
  t     / \  r t   o       i
   \   g   f       |       |
    o  \   |       t       e
     \  e  o       |
     n  |  |       e
        r  t

```

This trie example might contain words such as (remember to denote a word
actually is part of the tree, not just a path to another word, it has to have
its terminating flag set to true, at the node, which represents the end of the
word, e.g. 'big' would have the terminating flag in the g node set to true)

-   an, ant, anton
-   big, bigger, bigfoot,
-   cat, car
-   quote, qtie

```java
    class TriePrefixNode {
        TriePrefixNode[] children;
        boolean terminating = false;
    }
```

#### Inserting

Inserting in a trie, is simply following the existing path, if one does not
exist create the new nodes, along those path using the characters from the input
word / string. At the end mark the final node as terminating, as it would
indicate that the specific unique path that was followed/created terminates with
a word that is part of the trie, it has been inserted

```java
    TriePrefixNode insert(TriePrefixNode root, String string, Integer value) {
        // check if the input values first make any sense at all, the string can not be nil, and the value can not be nil either
        if (string == null || value == null) {
            return null;
        }
        // for simplicity reasons normalize all strings to lower case, this is not really a part of an usual trie implementation but it
        // makes things easier to understand
        string = string.toLowerCase();

        // either start off from the provided root, or make on if the insert was called without a valid root
        TriePrefixNode base = root;
        if (root == null) {
            root = new TriePrefixNode();
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
            TriePrefixNode node = root.children[cval - BASE_CHAR];
            if (node == null) {
                // the node at that char position does not exist, create a new node instance, and assign it to that position in the
                // root's children
                node = new TriePrefixNode();
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
```

#### Searching

Searching is very similar to inserting, but instead of creating missing nodes,
we strictly try follow existing paths, if one does not exit, or the last node in
the path is not `terminating = true`, then we can deduce this particular word
being searched for is not part of the tree (the path might exist, but as part of
another bigger / longer word)

```java
    boolean search(TriePrefixNode root, String string) {
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
```
#### Deleting

todo: this is not yet finalized, finish it

### B-Trees

# Graphs
