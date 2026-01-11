package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollectionsAndInterfaces {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(CollectionsAndInterfaces.class);

    public static void main(String[] args) {
        // ensure that the logger is first configured toprint abridged message we do not this to ensure that the logger format string does
        // not pollute the stdout with needless noise when printing to stdout.
        InstanceMessageLogger.configureLogger(CollectionsAndInterfaces.class.getResourceAsStream("/logger.properties"));

        List<String> sequentialArrayList = new ArrayList<>();
        sequentialArrayList.add("1");
        sequentialArrayList.add("2");
        sequentialArrayList.add("3");
        sequentialArrayList.add("4");
        sequentialArrayList.add("5");
        sequentialArrayList.add("6");
        sequentialArrayList.add("7");
        sequentialArrayList.add("8");
        sequentialArrayList.add("9");
        sequentialArrayList.add("10");

        LOGGER.logInfo("sequentialArrayList" + sequentialArrayList);

        // the sub list is backed by the actual source root list, that this sub-list is generated from this is actuall important because
        // changes to the original list will modfiy the sub-list itself at the specified position
        List<String> subListOfSequentialArrayList = sequentialArrayList.subList(0, 5);
        sequentialArrayList.set(2, "changed-string-element");

        // here after we have generated the sub-list and we have also modified the element at index 2 we print out the source list and the
        // sublist
        LOGGER.logInfo("sequentialArrayList: " + sequentialArrayList);
        LOGGER.logInfo("sublist(0, 5): " + subListOfSequentialArrayList);

        // demonstrate how the remove method can trip people with regards to array lists that are of integral type
        List<Integer> arrayListBasedIntegerList = new ArrayList<>();
        arrayListBasedIntegerList.add(1);
        arrayListBasedIntegerList.add(2);
        arrayListBasedIntegerList.add(3);

        // this might trip some people the remove method has two overloads one that takes in object the other one takes in a plain int
        // primitive, the one that takes in an object is meant to be used to remove by index, and may throw IndexOutOfBoundsExcetpion ,
        // however the one that takes in an object is meant to remove by equality an object from the array if such exists
        LOGGER.logInfo("arrayListBasedIntegerList: " + arrayListBasedIntegerList);
        arrayListBasedIntegerList.remove(0);
        LOGGER.logInfo("remove(0): " + arrayListBasedIntegerList);
        arrayListBasedIntegerList.remove(Integer.valueOf(3));
        LOGGER.logInfo("remove(Integer.valueOf(3)): " + arrayListBasedIntegerList);

        List<String> nodeBasedLinkedList = new LinkedList<>();
        nodeBasedLinkedList.add("1");
        nodeBasedLinkedList.add("2");
        nodeBasedLinkedList.add("3");
        nodeBasedLinkedList.add("4");
        nodeBasedLinkedList.add("5");
        nodeBasedLinkedList.add("6");
        nodeBasedLinkedList.add("7");
        nodeBasedLinkedList.add("8");
        nodeBasedLinkedList.add("9");
        nodeBasedLinkedList.add("10");

        LOGGER.logInfo("nodeBasedLinkedList: " + nodeBasedLinkedList);

        // similarly to the array list implementation the linked list implementation also creates the sub-lit view out of the
        // source so changes in both the source and the sublist will reflect into the both of the final structures
        List<String> subListOfNodeBasedList = nodeBasedLinkedList.subList(0, 5);
        nodeBasedLinkedList.set(0, "changed-string-element");

        LOGGER.logInfo("set(0, \"changed-string-element\"): " + nodeBasedLinkedList);
        LOGGER.logInfo("sublist(0, 5): " + subListOfNodeBasedList);

        Deque<String> arrayListBasedStack = new ArrayDeque<>();
        try {
            // this will throw with an empty stack in this case, we need to verify that this is actually the case
            arrayListBasedStack.removeFirst();
        } catch (Exception e) {
            LOGGER.logSevere("removeFirst(): " + e);
        }

        try {
            // calling element will also throw that is because this is the throwing variant of the peek method that does not
            arrayListBasedStack.element();
        } catch (Exception e) {
            LOGGER.logSevere("element(): " + e);
        }

        try {
            // for array list double ended queue null elements are not alloweed to be inserted therefore this call will also fail to add a
            // new element to the queue
            arrayListBasedStack.add(null);
        } catch (Exception e) {
            LOGGER.logSevere("add(null): " + e);
        }

        // these are the non throwing variants of the methods and will not cause any issue, will actually return null if the stack is empty
        LOGGER.logInfo("peek(): " + arrayListBasedStack.peek());
        LOGGER.logInfo("poll(): " + arrayListBasedStack.poll());

        // fill the array stack normally
        arrayListBasedStack.push("1");
        arrayListBasedStack.push("2");
        arrayListBasedStack.push("3");
        arrayListBasedStack.push("4");
        arrayListBasedStack.push("5");
        arrayListBasedStack.push("6");
        arrayListBasedStack.push("7");
        arrayListBasedStack.push("8");
        arrayListBasedStack.push("9");
        arrayListBasedStack.push("10");
        LOGGER.logInfo(arrayListBasedStack);

        Deque<String> linkedListBasedStack = new LinkedList<>();

        try {
            // this will throw with an empty stack in this case, we need to verify that this is actually the case
            linkedListBasedStack.removeFirst();
        } catch (Exception e) {
            LOGGER.logSevere("removeFirst(): " + e);
        }

        try {
            // calling element will also throw that is because this is the throwing variant of the peek method that does not
            linkedListBasedStack.element();
        } catch (Exception e) {
            LOGGER.logSevere("element(): " + e);
        }

        // for linked list double ended queue null elements are alloweed to be inserted therefore this call will not fail to add a null
        // element to the queue
        linkedListBasedStack.push(null);
        LOGGER.logInfo("add(null): " + linkedListBasedStack);

        // these are the non throwing variants of the methods and will not cause any issue, will actually return null if the stack is empty.
        // However remember we added a null element so the stack is actually not empty, therefore the isEmpty check below will return false,
        // and the size is 0, even though we have null elements
        LOGGER.logInfo("peek(): " + linkedListBasedStack.peek());
        LOGGER.logInfo("poll(): " + linkedListBasedStack.poll());
        LOGGER.logInfo("isEmpty(): " + linkedListBasedStack.isEmpty());
        LOGGER.logInfo("size(): " + linkedListBasedStack.size());

        // we added a new null element to the stack now the isEmpty and size will report the actual size and is empty as 2 and false, we
        // have two null elements into the stack.
        linkedListBasedStack.push(null);
        linkedListBasedStack.push(null);
        LOGGER.logInfo("add(null): " + linkedListBasedStack);
        LOGGER.logInfo("peek(): " + linkedListBasedStack.peek());
        LOGGER.logInfo("poll(): " + linkedListBasedStack.poll());
        LOGGER.logInfo("isEmpty(): " + linkedListBasedStack.isEmpty());
        LOGGER.logInfo("size(): " + linkedListBasedStack.size());

        // fill the linked stack normally
        linkedListBasedStack.push("1");
        linkedListBasedStack.push("2");
        linkedListBasedStack.push("3");
        linkedListBasedStack.push("4");
        linkedListBasedStack.push("5");
        linkedListBasedStack.push("6");
        linkedListBasedStack.push("7");
        linkedListBasedStack.push("8");
        linkedListBasedStack.push("9");
        linkedListBasedStack.push("10");
        LOGGER.logInfo(linkedListBasedStack);

        // demo the array list based queue interface, the queue interface is the parent interface for the Deque interface and provides the
        // Queue related methods and behavior only
        Queue<String> arrayBasedQueue = new ArrayDeque<>();
        arrayBasedQueue.add("1");
        arrayBasedQueue.add("2");
        arrayBasedQueue.add("3");
        arrayBasedQueue.add("4");
        arrayBasedQueue.add("5");
        arrayBasedQueue.add("6");
        arrayBasedQueue.add("7");
        arrayBasedQueue.add("8");
        arrayBasedQueue.add("9");
        LOGGER.logInfo("arrayBasedQueue: " + arrayBasedQueue);
        LOGGER.logInfo("remove(): " + arrayBasedQueue.remove());
        LOGGER.logInfo("peek(): " + arrayBasedQueue.peek());
        LOGGER.logInfo("element(): " + arrayBasedQueue.element());
        LOGGER.logInfo("peek(): " + arrayBasedQueue.isEmpty());

        // demoes how we can have a min/max heap implementation by just providing a custom comparator, that way the elements that we insert
        // into the heap are not required to be implementing comparable interface out of the box
        Queue<String> heapBasedQueue = new PriorityQueue<>((a, b) -> b.compareTo(a));
        heapBasedQueue.add("1");
        heapBasedQueue.add("2");
        heapBasedQueue.add("3");
        heapBasedQueue.add("4");
        heapBasedQueue.add("5");
        heapBasedQueue.add("6");
        heapBasedQueue.add("7");
        heapBasedQueue.add("8");
        heapBasedQueue.add("9");
        LOGGER.logInfo("heapBasedQueue: " + heapBasedQueue);
        LOGGER.logInfo("remove(): " + heapBasedQueue.remove());
        LOGGER.logInfo("peek(): " + heapBasedQueue.peek());
        LOGGER.logInfo("element(): " + heapBasedQueue.element());
        LOGGER.logInfo("peek(): " + heapBasedQueue.isEmpty());

        // the navigable set is interface that provides the means of navigating a set by keys and their natural ordering or based on the
        // comparator interface used to construct the underlying implementation, that is we can create different views of the set, not just
        // obtain and check for value presence or existence, since the set is ordered already
        NavigableSet<String> navigableSetBasedTree = new TreeSet<>();
        navigableSetBasedTree.add("1");
        navigableSetBasedTree.add("2");
        navigableSetBasedTree.add("3");
        navigableSetBasedTree.add("4");
        navigableSetBasedTree.add("5");
        navigableSetBasedTree.add("6");
        navigableSetBasedTree.add("7");
        navigableSetBasedTree.add("8");
        navigableSetBasedTree.add("9");

        LOGGER.logInfo("navigableSetBasedTree: " + navigableSetBasedTree);
        LOGGER.logInfo("ceiling(4): " + navigableSetBasedTree.ceiling("4"));
        LOGGER.logInfo("floor(4): " + navigableSetBasedTree.floor("4"));
        LOGGER.logInfo("higher(4): " + navigableSetBasedTree.higher("4"));
        LOGGER.logInfo("lower(4): " + navigableSetBasedTree.lower("4"));
        LOGGER.logInfo("subset(4, 9): " + navigableSetBasedTree.subSet("4", "8"));
        LOGGER.logInfo("subset(7, 100): " + navigableSetBasedTree.subSet("7", "99"));
        LOGGER.logInfo("tailset(4): " + navigableSetBasedTree.tailSet("4"));
        LOGGER.logInfo("headset(4): " + navigableSetBasedTree.headSet("4"));
        LOGGER.logInfo("descendingSet(): " + navigableSetBasedTree.descendingSet());

        NavigableMap<String, String> navigableMapBasedTree = new TreeMap<>();
        navigableMapBasedTree.put("key1", "value1");
        navigableMapBasedTree.put("key2", "value2");
        navigableMapBasedTree.put("key3", "value3");
        navigableMapBasedTree.put("key4", "value4");
        navigableMapBasedTree.put("key5", "value5");
        navigableMapBasedTree.put("key6", "value6");
        navigableMapBasedTree.put("key7", "value7");
        navigableMapBasedTree.put("key8", "value8");
        navigableMapBasedTree.put("key9", "value9");

        LOGGER.logInfo("navigableMapBasedTree: " + navigableMapBasedTree);
        LOGGER.logInfo("ceilingKey(key4): " + navigableMapBasedTree.ceilingKey("key4"));
        LOGGER.logInfo("ceilingEntry(key4): " + navigableMapBasedTree.ceilingEntry("key4"));
        LOGGER.logInfo("floorKey(key4): " + navigableMapBasedTree.floorKey("key4"));
        LOGGER.logInfo("floorEntry(key4): " + navigableMapBasedTree.floorEntry("key4"));
        LOGGER.logInfo("subMap(key1, key4): " + navigableMapBasedTree.subMap("key1", "key4"));
        LOGGER.logInfo("subMap(key4, key999): " + navigableMapBasedTree.subMap("key4", "key999"));
        LOGGER.logInfo("tailMap(key4): " + navigableMapBasedTree.tailMap("key4"));
        LOGGER.logInfo("headMap(key4): " + navigableMapBasedTree.headMap("key4"));
        LOGGER.logInfo("descendingMap: " + navigableMapBasedTree.descendingMap());
        LOGGER.logInfo("descendingKeySet: " + navigableMapBasedTree.descendingKeySet());


        LOGGER.logInfo("containsKey(key1): " + navigableMapBasedTree.containsKey("key1"));
        LOGGER.logInfo("get(key1): " + navigableMapBasedTree.get("key1"));
        LOGGER.logInfo("getOrDefault(key1, default): " + navigableMapBasedTree.getOrDefault("key1", "default"));
        LOGGER.logInfo("put(key999, value999): " + navigableMapBasedTree.put("key999", "value999"));
        LOGGER.logInfo("remove(key999, value999): " + navigableMapBasedTree.remove("key999"));
        LOGGER.logInfo("replace(key1, value999, value199): " + navigableMapBasedTree.replace("key1", "value999", "value199"));
        LOGGER.logInfo("merge(key1, newValue1, <merger>): " + navigableMapBasedTree.merge("key1", "newValue1", (oldValue, newValue) -> {
            // if the result is null the merge operation will actually remove the key / value mapping if it exists
            return oldValue + "-" + newValue;
        }));
        LOGGER.logInfo("comptue(key3000, <computer>): " + navigableMapBasedTree.compute("key3000", (key, value) -> {
            // the compute method will be called for the new key, even though there is no a mapping for this key, a new one will be created, but value might be null, so extra care needs to be taken here
            return "computedNewKeyValue" + key + value;
        }));
        LOGGER.logInfo("putIfAbsent(key1234, newValue1234): " + navigableMapBasedTree.putIfAbsent("key1234", "newValue1234"));

        LOGGER.logInfo("computeIfAbsent(key888, <resolver>): " + navigableMapBasedTree.computeIfAbsent("key888", (key) -> {
            // if that returns a null, then there is no new relation to the key created for this key, therefore no new mapping pair will be
            // created, the same is true if this method throws
            return "newKeyMapping-" + key + "-newValue";
        }));
        LOGGER.logInfo("computeIfPresent(key888, <resolver>): " + navigableMapBasedTree.computeIfPresent("key2", (key, value) -> {
            return "newValueForKey-" + key + "-" + value;
        }));

// boolean add(E e) → returns true if set changed.

// May throw NullPointerException if the impl forbids null.

// May throw ClassCastException if element incompatible with the set’s comparator/order (e.g., TreeSet).

// boolean addAll(Collection<? extends E> c) → true if any new element added. Same exception notes as add.

// Lookup

// boolean contains(Object o) → false if absent; may throw ClassCastException/NullPointerException in ordered impls with incompatible/null keys.

// int size(), boolean isEmpty()

// Removal

// boolean remove(Object o) → true if present and removed.

// May throw ClassCastException/NullPointerException in ordered impls.

// boolean removeAll(Collection<?> c) / boolean retainAll(Collection<?> c) → true if set changed.

// void clear() → no exception (unless unmodifiable set).

// Views & traversal

// Iterator<E> iterator() → fail-fast on most non-concurrent sets if structurally modified outside the iterator.

// Iterator.next() → throws NoSuchElementException when exhausted.

// Iterator.remove() → throws IllegalStateException if called before next() or twice for same element.

// forEach, spliterator, stream() (Java 8 defaults)

// Unmodifiable / synchronized wrappers

// Collections.unmodifiableSet(s) → mutators throw UnsupportedOperationException.

// Collections.synchronizedSet(s) → synchronized wrapper (still fail-fast iterators).
    }
}
