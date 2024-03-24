package com.cci.examples;

import java.util.List;

public class LinkedLists {

    public static final class Node {
        private int value;
        private Node next;
    }

    public static final int getListSize(Node node) {
        int count = 0;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    public static final Node getNextElement(Node node) {
        if (node == null) {
            return null;
        }
        return node.next;
    }

    public static final Node getNextElement(Node node, int k) {
        if (node == null) {
            return null;
        }
        for (int i = 1; i < k; i++) {
            if (node == null) {
                return node;
            }
            node = node.next;
        }
        return node;
    }

    public static final Node createLinkedList(List<Integer> elements) {
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

    public static final int deleteLinkedNode(Node node, int value) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        Node curr = node;
        Node prev = null;
        while (curr != null) {
            if (curr.value == value) {
                if (prev != null) {
                    prev.next = curr.next;
                }
                curr = curr.next;
                count++;
                continue;
            }
            prev = curr;
            curr = prev.next;
        }
        return count;
    }

    public static final Node insertLinkedNode(Node node, int value) {
        if (node == null) {
            return null;
        }

        Node newNode = new Node();
        newNode.value = value;

        newNode.next = node.next;
        node.next = newNode;
        return newNode;
    }

    public static final void printLinkedList(Node node) {
        if (node == null) {
            return;
        }

        while (node != null) {
            System.out.print(String.format("%d, ", node.value));
            node = node.next;
        }
        System.out.println();
    }

    /**
     * Remove all duplicate elements in a list, where the list is a linked list.
     *
     */
    public static final class RemoveDuplicateElements {

        public int removeDuplicateElements(List<Integer> elements) {
            Node list = createLinkedList(elements);

            // This example below runs in n^2 runtime, we are not going to use additional data structures, or sorting, to reduce the
            // complexity, to demonstrate a raw traversal and removal instead, as it is more expressive and straight forward
            int count = 0;
            Node current = list;
            while (list != null) {
                // look forward from the next node, if there is another node with the same value in the list, if so detach it
                Node previous = list;
                Node sublist = list.next;

                while (sublist != null) {
                    // check if the current value in the current sublist node is the same as the starting value, detach if yes
                    if (sublist.value == list.value) {
                        sublist = sublist.next;
                        previous.next = sublist;
                        count++;
                        continue;
                    }
                    // move to the next element
                    previous = sublist;
                    sublist = sublist.next;
                }
                // move to the next element in the list, we have to go through all elements to make sure duplicates are gone
                list = list.next;
            }

            if (count > 0) {
            }
            printLinkedList(current);
            return count;
        }
    }

    /**
     * Return the k-th to last element of a linked list
     *
     */
    public static final class ReturnKthElement {

        private int totalCount;

        private void printKthElementHelper(Node node, int kth, int cnt) {
            if (node == null) {
                // at this point the recursive call has reached it's max depth, meaning that we are the list's tail, therefore the cnt, will
                // be the total count of the items in the list, remember it
                totalCount = cnt;
            }

            // the current element count, important to note, the current element count is 1 (the current element) + cnt (all the rest so
            // far)
            int currentElCount = cnt + 1;

            // drill down the recursive stack, unti we reach the tail of the list, once we are there, we can unwind the stack in the post
            // recursive calls.
            printKthElementHelper(node.next, kth, currentElCount);

            // to find the kth to last element, simply subtract kth from the totalCount, this would give us the position (not 0 based) of
            // the element we care about, compare it with the currentElCount.
            if (totalCount - kth == currentElCount) {
                System.out.println(String.format("Kth %d to last element is %d", kth, node.value));
            }
        }

        /**
         * Given a linked list, find the kth to last element in that list
         *
         * @param elements - the elements from which to build the list
         * @param kth - the kth to last element to print
         */
        public void returnKthElement(List<Integer> elements, int kth) {
            printKthElementHelper(createLinkedList(elements), 2, 0);
        }
    }

    /**
     * Remove a node from the middle of the list, given access only to that node. This problem is not well formulated, it is really bad
     * because we are not removing a node, we are removing the value of a node, or rather simply overriding it. This problem does not use
     * terms correctly, leading to great confusion.
     *
     */
    public static final class DeleteInnerNode {

        public int removeInnerNode(List<Integer> elements, int node) {
            Node list = createLinkedList(elements);
            Node middle = list;
            for (int i = 1; i <= node; i++) {
                middle = middle.next;
            }

            if (middle == list || middle == null || middle.next == null) {
                System.out.println(String.format("The node with position %d is not in the middle of list", node));
                return 0;
            }

            int count = 0;
            Node prev = middle;
            Node curr = middle;

            // starting from the mid node, simply shift left all node values, and then delete the tail node, the problem is really not a
            // linked list node deletion problem it is more akin a static array removal, rather than usual node deletion
            while (curr != null) {
                if (curr.next != null) {
                    // when current element has a next one, that means we have still not reached the tail of the list, therefore the current
                    // value becomes the next value
                    curr.value = curr.next.value;
                    count++;
                } else {
                    // when current has no next element, means the current is the tail, the last two elements are the same value, we can
                    // just delete the current element by unlinking it from it's previous / parent element
                    prev.next = null;
                }

                // move with two pointers, using the slow,fast pointer, by having one pointer trail by one position, remembering the parent
                // of the current node on each iteration
                prev = curr;
                curr = curr.next;
            }

            // print out the result after the removal of the element, the effect is basically a shift left from the tail to the middle
            // element, just like a removal from a static array
            System.out.println(String.format("After removing the middle element %d", middle.value));
            printLinkedList(list);
            return count;
        }
    }

    /**
     * Given a list of elements, move all elements that are bigger than a given value to the right, such that the right side of the array
     * has all elements bigger than or equal to that value, and the left side has all elements be less than that value, the order in each
     * half does not matter.
     *
     */
    public static final class PartitionAroundElement {

        public int partitionLinkedList(List<Integer> elements, int val) {
            Node list = createLinkedList(elements);
            Node tail = list;
            while (tail != null && tail.next != null) {
                tail = tail.next;
            }

            // do sanity check on the input values
            assert (list != null && tail != null);

            // current starts from list, however previous should start from nil, the previous is only going to be set when there was an
            // actual previous element. previous is also updated only when going over elements that were less than x - the partition value
            int count = 0;
            Node curr = list;
            Node prev = null;
            Node origTail = tail;

            // loop until we visit all elements, we have visited all elements, when the curr element becomes the original tail, once that is
            // the case terminate the loop, there is no need to perform move on the last / original tail node, since everyting after it will
            // be greater than or equal to the partition value, the vlaue of the tail does not matter in either case it will comply with the
            // partition condition.
            while (curr != null && curr != origTail) {
                if (curr.value >= val) {
                    // start from the begining of the list, traverse to the right, each time we find a node that is bigger than or
                    // equal to the partition value, move it as a new tail, move the curr element to the next one in the list and
                    // continue, do not change the prev node, however update the prev.next to curr.next
                    Node next = curr.next;

                    // if the current element is the head, and it is indeed bigger than x, then prev would still have not been set, the new
                    // head would then become the next element, but prev must remain unset, due to the fact that there is no prev element
                    // yet
                    if (prev != null) {
                        prev.next = next;
                    }
                    curr.next = null;

                    // prepare the tail, before updating the old tail, set it's next pointer to point to the new tail element, then update
                    // the new tail to point to that element
                    tail.next = curr;
                    tail = curr;

                    // move the current element forward, only the current one
                    curr = next;
                    count++;
                    continue;
                }

                // the first element that is less than the partition value will be the new head
                if (prev == null) {
                    list = curr;
                }
                // when the current element is less than the value, simply move both curr and
                // prev, to the next element, there is no need to put it at the tail, repeat.
                prev = curr;
                curr = curr.next;
            }

            System.out.println(String.format("Partitined list with %d elements around %d", elements.size(), val));
            printLinkedList(list);
            return count;
        }
    }

    /**
     * Given an even numbers array, intersperce the elements such that they alternate. For example given the following linked list
     * a1,a2,a3,b1,b2,b3 -> a1,b1,a2,b2,a3,b3. This is done by having two pointers walking the list, one from the actual head, the other
     * starting from the midpoint node, the midpoint is found by value for simplicity, once we reach the end of the list we terminate
     *
     */
    public static final class IntersperseListItems {

        public Node intersperseListElements(List<Integer> elements, int midpoint) {
            // do sanity check on the input values
            assert (elements.size() % 2 == 0);

            Node list = createLinkedList(elements);
            Node ahead = list;
            Node bhead = list;
            while (bhead != null && bhead.value != midpoint) {
                bhead = bhead.next;
            }

            // now do print the original list of items
            System.out.println("List before interspersion");
            printLinkedList(list);

            // do sanity check on the input values
            assert (bhead != null && ahead != null);

            // loop from both heads, one starting from the actual list head, the other from the midpoint of interspersion,
            // attach the current midpoint node, to the current head, and move them along until the current midpoint's next node points to
            // nil, which means the actual tail of the list was reached
            while (ahead != null && bhead != null) {
                // remember where each of the two heads pointer's next nodes point to
                Node anext = ahead.next;
                Node bnext = bhead.next;

                // we at the list's tail ?
                if (bhead.next != null) {
                    // when the bhead next is not nil, we have not reached the actual tail of the list, thus, attach the current bhead to
                    // the ahead, and to bhead.next the anext
                    ahead.next = bhead;
                    bhead.next = anext;

                    // move the two heads along forward
                    ahead = anext;
                    bhead = bnext;
                } else {
                    // this means we have reached the actual end of the array, when bhead.next is nil, therefore only attach bhead to
                    // ahead.next, the bhead.next here is set to nil for good measure, but it should be already nil
                    ahead.next = bhead;
                    bhead.next = null;

                    // there is nothing more to move to
                    ahead = null;
                    bhead = null;
                }
            }

            // now do print the intersperced list of items
            System.out.println("List after interspersion");
            printLinkedList(list);
            return list;
        }
    }

    /**
     * Given two linked lists, which represent different digits of two numbers written backwards, sum the two lists and return the result as
     * a new list. For example having the two numbers 295 and 1763, represented as (5, 9, 2), (3, 6, 7, 1)
     *
     */
    public static final class SumTwoLists {

        public int sumTwoLists(List<Integer> first, List<Integer> second) {
            Node l1 = createLinkedList(first);
            Node l2 = createLinkedList(second);

            System.out.println("Given the two lists");
            printLinkedList(l1);
            printLinkedList(l2);

            int total = 0;
            int remainder = 0;

            Node result = new Node();
            Node summation = result;

            // iterate wile there is at least one list that is not iterated through completely or there is a remainder bigger than 0
            while (l1 != null || l2 != null || remainder != 0) {
                // move both pointers of the two pointers together, in case one of the lists is of smaller size than the other, just default
                // the value to 0
                int v1 = l1 == null ? 0 : l1.value;
                int v2 = l2 == null ? 0 : l2.value;

                // sum both values and whatever the remainder is from the previous step, or summation
                int sum = v1 + v2 + remainder;

                total += sum;

                if (sum >= 10) {
                    // the sum can either be greather than 9 or less, in case greater than 9, set the remainder to 1, and decrease the sum
                    sum -= 10;
                    remainder = 1;
                } else {
                    // the sum is not greater than 9, then we can remainder to 0 for the next iteration
                    remainder = 0;
                }

                // add the element to the result
                result.value = sum;
                result.next = new Node();
                result = result.next;

                // move both pointers forward, if there is something to move them to, otherwise default to nil
                l1 = l1 == null ? null : l1.next;
                l2 = l2 == null ? null : l2.next;
            }

            System.out.println("Have the following sum");
            printLinkedList(summation);
            return total;
        }
    }

    /**
     * Detect if the given list of items has a loop, if yes, which node points at the start of the loop. This is a classical tortoise & hare
     * problem. Also known as the Floyd's cycle-finding algorithm is a pointer algorithm that uses only two pointers, which move through the
     * sequence at different speeds.
     *
     */
    public static final class LoopDetectionStrategy {

        public static Node detectLoopStart(List<Integer> elements, int target) {
            Node list = createLinkedList(elements);

            // just construct a loop in the list, this is not part of the task, given a target, find it in the list, get that node, and
            // set the node at the tail end of the list
            Node found = list;
            while (found != null && found.value != target) {
                found = found.next;
            }
            Node tail = list;
            while (tail != null && tail.next != null) {
                tail = tail.next;
            }
            // make the loop
            tail.next = found;

            // start from the SAME initial starting point, this is VERY important, we MUST start at the same starting
            // positions, otherwise the loop detection would not work
            Node slow = list;
            Node fast = list;

            // check there is where to move to in the first place
            while (fast != null && fast.next != null) {
                // FIRST move both pointers forward, this step is very important to note, as we have started at the same position when slow
                // and fast were initialized, we have to first move forward, then compare, if they are equal then the fast and slow pointers
                // have collided, they will always collide if there is a loop in the list
                slow = slow.next;
                fast = fast.next.next;

                // the fast pointer in this example is moving 2 steps ahead, for every 1 step the slow one makes, but no matter the number
                // of steps, as long they are integer, they will always collide after a certain number of steps, if there is no collision,
                // there is no loop in the list
                if (slow == fast) {
                    break;
                }
            }

            // if at this point fast is null there is no loop in the list, if fast.next is null then again, there is no loop, fast points at
            // the tail of the list, which means the list has no loops, and the while above never broke, we can also instead check here if
            // slow and fast are equal, if not, then there is no loop again i.e if (slow != fast) - same as the check below
            if (fast == null || fast.next == null) {
                return null;
            }

            // this is important, now that we know we have a loop, there is a theoretical proof, that the distance from the start of the
            // list to the first element in the loop will be exactly the same steps as if we were in the loop and move the same amount of
            // steps. Therefore start the slow at the list head, and keep the fast where it is (somehwere in the loop), move them at the
            // same time, they WILL meet at the starting node of the loop, guaranteed !
            slow = list;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }

            // do sanity check on the resulting values
            assert (fast == slow && fast != null && slow != null && fast.value == target);
            return fast;
        }
    }

    public static void main(String[] args) {
        // RemoveDuplicateElements dups = new RemoveDuplicateElements();
        // dups.removeDuplicateElements(Arrays.asList(3, 1, 2, 3, 1, 5, 2, 3, 4, 4, 5, 2, 2, 2, 2));

        // ReturnKthElement kth = new ReturnKthElement();
        // kth.returnKthElement(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), 3);
        // kth.returnKthElement(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), 5);
        // kth.returnKthElement(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), 1);

        // DeleteInnerNode mid = new DeleteInnerNode();
        // mid.removeInnerNode(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), 4);

        // PartitionAroundElement part = new PartitionAroundElement();
        // part.partitionLinkedList(Arrays.asList(3, 5, 8, 5, 10, 2, 1), 5);
        // part.partitionLinkedList(Arrays.asList(10, 8, 1, 3, 5, 8, 5, 10, 2, 1), 5);

        // IntersperceListItems inter = new IntersperceListItems();
        // inter.intersperceListElements(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 5);
        // inter.intersperceListElements(Arrays.asList(1, 2, 3, 4), 3);
        // inter.intersperceListElements(Arrays.asList(1, 2), 2);

        // LoopDetectionStrategy loop = new LoopDetectionStrategy();
        // loop.detectLoopStart(Arrays.asList(1, 2, 3, 4, 5), 3);
        // loop.detectLoopStart(Arrays.asList(1, 2, 3, 4, 5), 5);
        // loop.detectLoopStart(Arrays.asList(1, 2, 3, 4, 5), 6);

        // SumTwoLists sum = new SumTwoLists();
        // sum.sumTwoLists(Arrays.asList(5, 9, 2), Arrays.asList(3, 6, 7, 1));
        // sum.sumTwoLists(Arrays.asList(1, 2), Arrays.asList(2, 3));
        // sum.sumTwoLists(Arrays.asList(1), Arrays.asList(2, 3));
        // sum.sumTwoLists(Arrays.asList(6), Arrays.asList(9));

        return;
    }
}
