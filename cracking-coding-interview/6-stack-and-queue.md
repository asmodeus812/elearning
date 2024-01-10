# Introduction

# Stack

One of the most used data structure, which is often used with various different
algorithms, such as graph or tree traversals, can be used to implement recursive
algorithms in an iterative manner. The common interface that a stack uses is as
follows

-   pop() - remove the element at the top of the stack
-   push() - push a new element at the top of the stack
-   peek() - peek the element at the top of the stack
-   empty() - checks if the stack is empty, true / false

Note, that in the default implementation in java, when using most of those
methods on an empty stack, they would throw StackEmtpyException, or something of
that nature - pop, peek for example.

```java
public static final class Stack<T> {

    // simple node to describe an entry in the stack, due to the nature of the
    // stack, it does not need to be doubly linked, the implementation is very
    // similar to a singly linked list
    private static final StackNode<T> {
        private T value;
        private StackNode<T> next;
    }

    // simply hold a reference to the head element, the head will move forward
    // when pushing, and move backward when popping or removing
    private StackNode<T> head;

    public void push(T value) {
        if (this.head == null) {
            // when the head of the stack if not initialized make sure to set it
            // and leave, that for the first time adding to the stack
            this.head = new StackNode<T>();
            this.head.value = value;
            this.head.next = null;
        } else {
            // on each new element, simply make the new element the head, and
            // correctly link the next element of the new element to the current
            // head.
            StackNode<T> newHead = new StackNode<>();
            newHead.value = value;
            newHead.next = this.head;
            this.head = newHead;
        }
    }

    public T pop() {
        if (this.head == null) {
            // in case the stack is empty, simply throw, which is what actually
            // happens in the default java stack implementation from the standard
            // library
            throw new StackEmtpyException();
        }

        // otherwise when we have at least one head element, extract it, the new
        // head becomes the immediate next node after the current head, and simply
        // return the value of the current head
        StackNode<T> current = this.head;
        this.head = current.next;
        return current.value;
    }

    public T peek() {
        if (this.head == null) {
            // in case the stack is empty, simply throw, which is what actually
            // happens in the default java stack implementation from the standard
            // library
            throw new StackEmtpyException();
        }

        // peeking, does not mutate the state of the stack, it simply returns
        // whatever is at the top, where the current head points at
        return this.head.value;
    }

    public boolean empty() {
        // simple check to verify the stack has any elements at all, when all
        // elements are removed, the head will eventually point at null, or when
        // the stack is not initialized in the first place
        return this.head != null;
    }
}
```

# Queue

One of the most used data structure, which is often used with various different
algorithms, such as graphs or tree traversals. The common interface that a queue
uses is as follows

-   remove() - remove the element from the front of the queue
-   insert() - push a new element to the end of the queue
-   peek() - peek the element at the front of the queue
-   empty() - checks if the queue is empty, true / false

Note, that in the default implementation in java, when using most of those
methods on an empty queue, they would throw QueueEmtpyException, or something of
that nature - peek, remove for example.

```java

public static final class Queue<T> {

    // simple node to describe an entry in the queue, due to the nature of the
    // queue, it does not need to be doubly linked, the implementation is very
    // similar to a singly linked list
    private static final QueueNode<T> {
        private T value;
        private QueueNode<T> next;
    }

    // simply hold a reference to the head element, when removing we would
    // extract value from the head, and move the head forward, when inserting,
    // the head is used to find the end of the queu, to insert at
    private QueueNode<T> head;

    public void insert(T value) {
        if (this.head == null) {
            // when the head of the queue if not initialized make sure to set it
            // and leave, that for the first time adding to the queue
            this.head = new QueueNode<T>();
            this.head.value = value;
            this.head.next = null;
            this.tail = this.head;
        } else {
            // traverse the queue to find where it ends, the loop below would
            // find the last valid node, or in other words the tail of the queue
            // note that we could maintain a tail node, but this is done for
            // simplicity, and ease of use
            QueueNode<T> tail = this.head;
            while (tail && tail.next != null) {
                tail = tail.next;
            }

            // on each new element, simply attach it to the found tail, the
            // queue will always have a tail as long as the head is initialized,
            // which we guarantee in the main if above
            QueueNode<T> nextNode = new QueueNode<>();
            nextNode.value = value;
            curr.next = nextNode;
        }
    }

    public T remove() {
        if (this.head == null) {
            // in case the queue is empty, simply throw, which is what actually
            // happens in the default java queue implementation from the standard
            // library
            throw new QueueEmtpyException();
        }

        // otherwise when we have at least one head element, extract it, the new
        // head becomes the immediate next node after the current head, and simply
        // return the value of the current head
        QueueNode<T> current = this.head;
        this.head = current.next;
        return current.value;
    }

    public T peek() {
        if (this.head == null) {
            // in case the queue is empty, simply throw, which is what actually
            // happens in the default java queue implementation from the standard
            // library
            throw new QueueEmtpyException();
        }

        // peeking, does not mutate the state of the queue, it simply returns
        // whatever is at the start, where the current head points at
        return this.head.value;
    }

    public boolean empty() {
        // simple check to verify the queue has any elements at all, when all
        // elements are removed, the head will eventually point at null, or when
        // the queue is not initialized in the first place
        return this.head != null;
    }
}

```
