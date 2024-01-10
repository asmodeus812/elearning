# Intorduction

Represents a sequence of nodes, where each node is linked to the next. This is
called a singly linked list, while variations exist where each node might have a
link to the node before it, making it a doubly linked list.

```java
class Node {
    int value;
    Node next;
}
```

# Creating

```java
Node create(List<Integer> elements) {
    Node tail = null;
    Node head = null;
    for (Integer entry : elements) {
        if (head == null) {
            head = new Node();
            head.value = entry;
            tail = head;
        } else {
            Node next = new Node();
            next.value = entry;
            tail.next = next;
            tail = next;
        }
    }
    return head;
}
```

# Deleting

Deleting from a list usually implies removing of a node link within the list,
this is done by linking the parent of the node to be removed with the link to
the next node after the one being deleted `prev.next = curr.next`.

```java
void delete(Node node, int value) {

    Node curr = node;
    Node prev = null;

    while (curr != null) {
        if (curr.value == value) {
            if (prev != null) {
                prev.next = curr.next;
            }
            curr = curr.next;
            continue;
        }
        prev = curr;
        curr = curr.next;
    }
}
```

However some tasks with lists can also include normal shift base removal, where
you do not remove the node, but you just remove the value by shifting from the
left, by overriding the curr.value with curr.next.value, then deleting the tail
node, as it would contain the last element two times after the process

That is a gotcha question sometimes. (e.g they want you to remove a `node` from
the middle of the list but you have only access to that node, well the retards
mean that you have to just copy elements left to right, to "remove" the value
not the node itself.

```java
void delete(Node node) {
    Node curr = node;

    // move, shift values to the left, we are not really deleting nodes, simply
    // copying left to right the values until we reach the tail
    while (curr != null && curr.next != null) {
        curr.value = curr.next.value;
        curr = curr.next;
    }

    // curr will point at the tail, we
    curr.value = 0

    return count;
}
```

# Runner approach

This is very important topic when talking about linked lists, there is a common
technique called the runner. Where we loop through, or traverse a linked list
forward or backwards, not with one, but two pointers.

This is very often expected approach to use when trying to detect loops in the
list, where a list might have the same node (reference) more than once, forming
a loop. Using the runner approach the fast pointer will move forward two
elements ahead of the slow pointer, that way we can detect the loop.

Very CRUCIAL to know, that in a list with a loop, when the slow and fast nodes
intersect, in other words when we detect the loop, fast and slow will point at a
node within that loop, if we reset fast or slow to point to the original head,
and we move both pointers together by one step, they will meet exactly at the
start of the loop, the first node that forms the loop, that is GUARANTEED !

```java
void loop(Node node) {
    Node slow = node;
    Node fast = node;

    // move both at different paces, if there is a loop in the list, they will
    // always meet at some point within this loop
    while (fast != null and fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;

        // they met, there is a loop in the list, the same exact node, by
        // reference is contained in the list twice
        if (slow == fast) {
            break;
        }
    }
}

void runner(Node node) {
    // start both fast and slow at the same head of the list
    Node slow = node;
    Node fast = node;

    // make sure there is any nodes left
    while(thereAreMoreNodesToMoveTo) {
        // get the immediate next node, for the slow node
        slow = getNextNode(slow);

        // get the n-th node, from the last fast node
        fast = getNthNode(fast);

        // do something with fast and slow and repeat
    }
}
```

Lets imagine that we have a linked list with the following structure
where assume we have an even number elements such as

-   `a1 - a2 - a3 - a4 - b1 - b2 - b3 - b4` - input
-   `a1 - b1 - a2 - b2 - a3 - b3 - a4 - b4` - result

The runner approach would allows us to have two pointers one starting from the
head of the list, the other starting from size of list divided by 2 + 1. In that
case the example has 8 elements, therefore the second pointer can start from
head + 4 + 1 elements (or the fifth element in the list being `b1`), then move both
forward with one element at a time.

# Previous & current approach

This is a variation of the runner approach where we keep the reference to the
previous element in the list, the previous node, and the current one, usually to
make it easier when we want to delete elements. This comes up a lot, both
pointers are one off each other. The previous node is not always moving one step
behind the current node, it might depend on the algorithm

```java
// initialization
prev = null;
curr = head;

// depending on the algorithm, we might want to move prev with curr, or move it,
// set it only in specific cases, this way the prev node moves in variable
// number of steps, for example if we want we can move prev only for elements which
// meet some condition,

// when looping
prev = curr;
curr = curr.next;
```

# Recursive approach

Another technique which is very spread in linked list problems, if you have
having trouble solving a linked list problem you should explore if a Recursive
approach will work.

How can it help, in a linked list problem, having recursive calls traverse the
list from head to tail, give us the inverse traversal tail - head, when the
recursive call returns. This is not for free since the space complexity O(n)
which is due to the call stack.

Example of this is to find the k-th to last element if a singly linked list.
What can we do here ? Using recursion we can drill down to the tail, keeping the
current count of elements in the list, at the bottom of the recursion we would
have gone through the entire list, having counted all elements, and in the
post recursive calls we can subtract from the total elements count in the list
k, and compare to the current element's count, that way we can find the node
exact node that is k elements behind the tail (or the last element)

```java
void recursive(Node node, int accum) {
    if(node == null) {
        // at the tail, remember the total count in some int variable
        total = accum;
        return;
    }
    // pre-recursive call, increment and accumulate the current count
    int curr = accum + 1

    // this will drill down, till the tail, then start post recursive
    // unwinding of the function call stack, starting from the tail,
    // up to the head of the list, can be used to do operations in reverse on
    // the list, like print the n-th element before the tail for example
    recursive(node.next, curr);

    // post-recursive call, will print the k-th to last element in list
    if(total - kth  == curr) {
        println(node.value)
    }
}
```
