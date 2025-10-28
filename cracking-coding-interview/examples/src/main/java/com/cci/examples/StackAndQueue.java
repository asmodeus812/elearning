package com.cci.examples;

import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

public class StackAndQueue {

    /**
     * Implement structure which represents n number of stacks, in a single static array.
     *
     * This is done by interleaving the stack elements, for example the array which interleaves the elements of 3 stacks will look like that
     * [s1,s2,s3,s1,s2,s3,s1,s2,s3]. Each element for each unique stack is offset forward exactly n, or the count of stacks that the array
     * holds, forward
     *
     */
    public static class SingleArrayStacks {

        private int stacks;
        private Integer[] stackArray;
        private Integer[] stackHeads;

        public SingleArrayStacks(int stacks) {
            // the number of stacks this array is supposed to hold
            this.stacks = stacks;
            // the array to hold the stacks, starts off initially able to hold at least 1 element for each of the stacks
            this.stackArray = new Integer[stacks];
            // holds the indicies for where each head, for each stack is at the moment, in the big static array
            this.stackHeads = new Integer[stacks];
            for (int i = 0; i < stacks; i++) {
                // initialize the heads to be negative, that would indicate that the stacks start off initialy empty
                this.stackHeads[i] = Integer.MIN_VALUE;
            }
        }

        /**
         * Push a new element at stack with index {stack}, which is 0-based index
         *
         * @param stack - the 0-based stack index at which to add the value
         * @param value - the value to add at the specific stack
         * @return - true if something was added, false otherwise
         */
        public boolean push(int stack, Integer value) {
            if (stack >= this.stacks) {
                return false;
            }

            int index = this.stackHeads[stack];

            if (index < 0) {
                // when the current index is negative, the stack was empty, the first element is simply at the index which corresponds to
                // the stack index itself
                this.stackHeads[stack] = stack;
            } else {
                // the head index was not negative, therefore we offset it forward by the total number of stacks the array holds
                this.stackHeads[stack] += this.stacks;
            }

            // update the index varialbe
            index = this.stackHeads[stack];

            // resize the static array in case the index overshoots the actual size of the array, the resize is done in chunks of of
            // elements equal to the number total stacks, i.e each resize would add atleast this.stacks number of elements, or in other
            // words, 1 element for each stack it can hold
            if (index >= this.stackArray.length) {
                this.stackArray = Arrays.copyOf(this.stackArray, this.stackArray.length + this.stacks);
            }

            // set the element value at the index, the index inside the stackHeads would not point at the head of the stack, for the current
            // stack
            this.stackArray[index] = value;
            return true;
        }

        /**
         * Check if the specific stack is empty, the stack index is 0-based
         *
         * @param stack - the 0 based stack index
         * @return - true if emtpy, false otherwise or if invalid index
         */
        public boolean empty(int stack) {
            if (stack >= this.stacks) {
                return false;
            }
            // stacks are considered empty when the index inside stackHeads is negative, it can become negative when popping elements, or
            // initially when the stack is simply empty, initialized in the construct
            return this.stackHeads[stack] < 0;
        }

        /**
         * Remove from the specific stack, the stack index is 0-based
         *
         * @param stack - the 0 based target stack
         * @return - removed element or nil if stack was empty
         */
        public Integer pop(int stack) {
            if (stack >= this.stacks) {
                return null;
            }

            // get the current head of the specific stack first, and verify that specific stack is valid
            int index = this.stackHeads[stack];

            if (index < 0) {
                // negative index would indicate that there is nothing to pop from this specific stack
                return null;
            }

            // negate the curent head, by the total number of stacks, the index can become 0, when removing the last element from the
            // specific stack, which is okay, since that is the condition we use to verify if the specific stack is empty
            this.stackHeads[stack] -= this.stacks;

            // return whatever value was at the old head index
            return this.stackArray[index];
        }

        /**
         * Simply return whatever is at the current head of the specific stack, the stack index is 0-based
         *
         * @param stack - the 0 based target stack
         * @return - the element at head or nil if invalid stack or empty stack
         */
        public Integer peek(int stack) {
            if (stack >= this.stacks) {
                return null;
            }

            // get the current head of the specific stack first, and verify that specific stack is valid
            int index = this.stackHeads[stack];

            if (index < 0) {
                // negative index would indicate that there is nothing to pop from this specific stack
                return null;
            }

            // the head index is valid, simply return the value at that head index, whatever it is
            return this.stackArray[index];
        }

        public void print(int stack) {
            if (stack >= this.stacks) {
                return;
            }

            if (empty(stack)) {
                return;
            }

            for (int i = this.stackHeads[stack]; i >= 0; i -= this.stacks) {
                System.out.print(String.format("%d, ", this.stackArray[i]));
            }
            System.out.println();
        }
    }

    /**
     * Implement a stack which also in addition to the regular interface also has a min() method, which returns the min element in the stack
     * currently, and works in O(1) complexity in time and space.
     *
     * This is done by holding an additional min stack, which is updated whenever a new element that is smaller than the head of the current
     * stack is added. Why does it work ? The min stack retains the order of insertion of the min elements, the only thing needed to do is
     * when popping element from the main stack, to check if we are popping the min element, if yes, pop from the min stack too, this way
     * the min stack will always hold the minimum element at its head, currently in the main stack. Peeking the min stack will give us that
     * element
     *
     */
    public static final class MinStackHolder {

        private Stack<Integer> stackMin = new Stack<>();
        private Stack<Integer> stackBase = new Stack<>();

        /**
         * Push a given element to the stack
         *
         * @param value - the element to add to the stack
         */
        public void push(Integer value) {
            // if the min stack is empty, default to the smallest valid value, otherwise get the head from the min stack
            Integer min = !this.stackMin.isEmpty() ? this.stackMin.peek() : Integer.MAX_VALUE;

            if (value < min) {
                // when the value is less than head of the min stack, then add that value to the min stack
                this.stackMin.push(value);
            }
            // normal pushing to the main stack
            this.stackBase.push(value);
        }

        /**
         * Returns if the stack is empty
         *
         * @return - true if empty, false otherwise
         */
        public boolean empty() {
            return this.stackBase.isEmpty();
        }

        /**
         * Remove from the stack
         *
         * @return - removed relement
         */
        public Integer pop() {
            if (!this.stackMin.isEmpty()) {
                // get the top element from the min stack
                Integer min = this.stackMin.peek();

                if (min.equals(this.stackBase.peek())) {
                    // the top element are removing is the same as the one from the min stack, therefore we have to remove it from the min
                    // stack too, to keep them sync. After removing from the main stack and the min stack, the min stack would now contain
                    // the next most minimal element
                    this.stackMin.pop();
                }
            }
            // normal removal from the main stack
            return this.stackBase.pop();
        }

        /**
         * Get the min element for this stack
         *
         * @return - the min element
         */
        public Integer min() {
            if (!this.stackMin.isEmpty()) {
                // whatever is at the head here, since pushing and popping were kept in sync between the two stacks, we must find the min
                // element in the main stack be present at the head of the min stack
                return this.stackMin.peek();
            }
            return null;
        }

        /**
         * Peek the head of the stack
         *
         * @return - the value at the head
         */
        public Integer peek() {
            if (!this.stackBase.isEmpty()) {
                return this.stackBase.peek();
            }
            return null;
        }

        public void print() {
            for (int i = this.stackMin.size() - 1; i >= 0; --i) {
                System.out.print(String.format("%d, ", this.stackMin.get(i)));
            }
            System.out.println();
            for (int i = this.stackBase.size() - 1; i >= 0; --i) {
                System.out.print(String.format("%d, ", this.stackBase.get(i)));
            }
            System.out.println();
        }
    }

    /**
     * Data structure which can hold a variable number of stacks, each of which has a limit of elements that it can retain, before being
     * considered full.
     *
     * The solution here is to use a stack of stacks, when the current sub stack is full, reaches the limit of elements, add new stack in
     * the main stack
     *
     */
    public static final class StackOfPlates {

        private int limit;
        private Stack<Stack<Integer>> stack = new Stack<>();

        /**
         * Limit the total number of elements in each stack
         *
         * @param limit - the elements limit
         */
        public StackOfPlates(int limit) {
            this.limit = limit;
        }

        /**
         * Checks if the stack is empty
         *
         * @return - true if empty, false otherwise
         */
        public boolean empty() {
            // if either the entire stack of stacks is empty, or the top stack in the stack of stacks is empty
            return this.stack.isEmpty() || this.stack.peek().isEmpty();
        }

        /**
         * Push a new item in the stack
         *
         * @param value - the item to push
         */
        public void push(Integer value) {
            if (this.stack.isEmpty() || this.stack.peek().size() >= this.limit) {
                // in case the main stack is empty or the current stack at the head has reached it's limits, add new stack to the total
                // stack of stacks
                this.stack.push(new Stack<>());
            }
            // peek the head stack and add new element
            stack.peek().push(value);
        }

        /**
         * Pop the element from the head of the stack
         *
         * @return - the element at the head, or nil if none
         */
        public Integer pop() {
            if (this.stack.isEmpty() || this.stack.peek().isEmpty()) {
                return null;
            }

            // get the stack at the top, and pop the value from it, which we will return
            Stack<Integer> top = stack.peek();
            Integer value = top.pop();

            if (top.isEmpty()) {
                // in case that top stack now became empty, we can remove it from the stack of stacks,
                this.stack.pop();
            }

            // return the top value from the last stack that was at the top of stack of stacks
            return value;
        }

        /**
         * Peek the value at the top of the stack
         *
         * @return - the value at the top, or nil if none
         */
        public Integer peek() {
            if (this.stack.isEmpty() || this.stack.peek().isEmpty()) {
                return null;
            }

            // the top of the stack of stacks, would contain at this point a stack with at least one element, peek it
            return this.stack.peek().peek();
        }

        public void print() {
            for (Stack<Integer> var : this.stack) {
                for (int i = var.size() - 1; i >= 0; --i) {
                    System.out.print(String.format("%d, ", var.get(i)));
                }
            }
            System.out.println();
        }
    }

    /**
     * Construct a queue from two stacks, where the api works exactly as if this were a queue
     *
     * The way we solve this is by holding two stacks, one for the new elements, one for the old elements, when pushing we only add to the
     * stack of new elements, when popping, we move all new elements to the old elements stack, when the old elements is empty. When
     * removing we remove only from the old elements stack.
     *
     */
    public static final class QueueFromStacks {

        private Stack<Integer> stackNew = new Stack<>();
        private Stack<Integer> stackOld = new Stack<>();

        /**
         * Add a new element to the queue
         *
         * @param value - the new element
         */
        public void insert(Integer value) {
            // simply push the element to the new stack of elements
            this.stackNew.push(value);
        }

        /**
         * Remove the next element from the queue
         *
         * @return - the next element
         */
        public Integer remove() {
            // first shift all new elements from the new stack to the old stack, only if the old stack was already empty, if not do nothing
            shift();
            return this.stackOld.pop();
        }

        /**
         * Peek the element at the start of the queue
         *
         * @return - the element at the start
         */
        public Integer peek() {
            // first shift all new elements from the new stack to the old stack, only if the old stack was already empty, if not do nothing
            shift();
            return this.stackOld.peek();
        }

        /**
         * Check if this queue is empty
         *
         * @return - true if empty, false otherwise
         */
        public boolean empty() {
            // when either one is empty, then the queue is empty
            return this.stackNew.isEmpty() || this.stackOld.isEmpty();
        }

        /**
         * Shifts elements from new stack to old stack, old stack would contain the new stack elements in reverse order, we use old stck to
         * pop from or peek, to mimick queue behaviour then
         */
        private void shift() {
            // basically the new elements stack is used as temporary storage which is moved to the old elements only when old elements
            // becomes empty, either by popping from old elements stack when doing remove() queue operation this is the meat of the
            // algorithm, we have two cases of how to update the two stacks, see below:
            // - when the old stack is empty, we move all elements from the new stack, and push them in the old elements stack, that would
            // cause the old elements stack to have the reverse order of elements as the ones in the new elements stack, due to the fact
            // that we pop from the new elements stack, and push into the old elements stack, at this point the old elements stack will
            // contain at it's head the first element ever pushed in new elements
            // - when the old stack is not empty, we keep the elements in new elements stack, we do not pop or update old stack elements,
            // because since we implement a queue, we can only remove from the tail, so there are elements to remove from old elements stack
            // still, so until old elements becomes empty, we can keep popping, when it becomes empty, the new batch of elements will come
            // from new elements stack
            if (this.stackOld.isEmpty()) {
                while (!this.stackNew.isEmpty()) {
                    this.stackOld.push(this.stackNew.pop());
                }
            }
        }

        public void print() {
            shift();
            for (int i = this.stackOld.size() - 1; i >= 0; --i) {
                System.out.print(String.format("%d, ", this.stackOld.get(i)));
            }
            System.out.println();
        }
    }

    /**
     * Implement a data structure which is a stack that is automatically sorted when new elements are inserted into it. You are allowed to
     * use one extra temporary stack to achieve this.
     *
     * The way this works, is by having one more temporary stack which is used to move elements to, from the main stack. What we do is move
     * elements from the main stack into the temporary stack until the head of the main stack is not greater than the value we want to
     * insert, then, we move back all elements we pushed into the temporary stack into the main stack.
     */
    public static final class SortStackOrder {

        // used to store the sorted values
        private Stack<Integer> sorted = new Stack<>();

        // used to hold whatever elements were pulled from sorted
        private Stack<Integer> temp = new Stack<>();

        /**
         * Push a new element into the stack
         *
         * @param value - the new element
         */
        public void push(Integer value) {
            if (!sorted.isEmpty()) {
                // pull all elements from the actual stack, that are greater than the new element to be inserted, and put them in the
                // temporary one, once that is done, the temp stack will have all the elements that are bigger than the element we want to
                // insert
                while (!sorted.isEmpty() && sorted.peek() > value) {
                    temp.push(sorted.pop());
                }

                // add the element, meaning that the head of the sorted stack before this push will only have elements smaller than value,
                // or equal, and after we do the push the value would be the new head of the stack
                sorted.push(value);

                // from the temporary stack, return back all elements, back to the sorted stack, the temporary stack will have the order
                // correctly preserved, the temporary stack would have the old elements from the sorted stack in a sorted order, therefore
                // the top of temporary would be whatever was last pulled from the sorted stack. Poping from temp and adding to the sroted
                while (!temp.isEmpty()) {
                    sorted.push(temp.pop());
                }
            } else {
                // the sorted is empty, so just push the element, this case would happen only if we have nothing in the sorted stack, there
                // is nothing to sort
                sorted.push(value);
            }
        }

        /**
         * Check if the stack is empty
         *
         * @return - true if empty, false otherwise
         */
        public boolean empty() {
            return this.sorted.isEmpty();
        }

        /**
         * Remove the top element from the stack
         *
         * @return - the top element
         */
        public Integer pop() {
            return this.sorted.pop();
        }

        /**
         * Peek the top element from the stack
         *
         * @return - the top element
         */
        public Integer peek() {
            return this.sorted.peek();
        }

        public void print() {
            for (int i = this.sorted.size() - 1; i >= 0; --i) {
                System.out.print(String.format("%d, ", this.sorted.get(i)));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // SortStackOrder sorted = new SortStackOrder();
        // sorted.push(3);
        // sorted.push(1);
        // sorted.push(8);
        // sorted.push(5);
        // sorted.push(2);
        // sorted.push(0);
        // sorted.push(10);
        // sorted.print();

        // QueueFromStacks queue = new QueueFromStacks();
        // queue.insert(1);
        // queue.insert(2);
        // queue.insert(3);
        // queue.insert(4);
        // queue.insert(5);
        // queue.insert(6);
        // queue.insert(7);
        // queue.insert(8);
        // queue.insert(9);
        // queue.insert(10);
        // queue.print();

        // StackOfPlates plates = new StackOfPlates(2);
        // plates.push(1);
        // plates.push(2);
        // plates.push(3);
        // plates.push(4);
        // plates.push(5);
        // plates.push(6);
        // plates.push(7);
        // plates.push(8);
        // plates.print();

        // MinStackHolder min = new MinStackHolder();
        // min.push(3);
        // System.out.println(min.min());
        // min.push(1);
        // System.out.println(min.min());
        // min.push(8);
        // System.out.println(min.min());
        // min.push(5);
        // System.out.println(min.min());
        // min.push(2);
        // System.out.println(min.min());
        // min.push(0);
        // System.out.println(min.min());

        // min.pop();
        // System.out.println(min.min());
        // min.pop();
        // System.out.println(min.min());

        // SingleArrayStacks stacks = new SingleArrayStacks(3);
        // stacks.push(0, 0);
        // stacks.push(1, 10);
        // stacks.push(2, 20);
        //
        // stacks.push(0, 1);
        // stacks.push(1, 11);
        // stacks.push(2, 21);
        //
        // stacks.push(0, 2);
        // stacks.push(1, 12);
        // stacks.push(2, 22);
        //
        // stacks.print(0);
        // stacks.print(1);
        // stacks.print(2);

        return;
    }
}
