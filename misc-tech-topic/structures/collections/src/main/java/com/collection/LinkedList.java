package com.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements List<T> {

    /**
     * The total amount of elements that can be added to the list as new nodes, this is not a java virtual machine restriction, rather is a
     * restriction of the actual implementation
     */
    private static final int LIST_SIZE_LIMIT = Integer.MAX_VALUE;

    /**
     * The default array list value that will be returned otherwise when this list is empty and there are no values to return
     */
    private static final Object[] EMTPY_LIST_ARRAY = new Object[0];

    /**
     * The head of the linked list, this reference is going to be null when the list is empty
     */
    private ListNode<T> head;

    /**
     * The tail of the linked list, this reference is oging to be null when the list is empty, or it will point exactly at the head of the
     * linked liset, or in other words be equal to the head reference
     */
    private ListNode<T> tail;

    /**
     * The size of the linked list, this is used internally to cache the current size of the linked list, the number of nodes that the list
     * contains are all accounted for in this size. The size is stored internally to avoid walking the linked list every time to compute the
     * number of nodes it contains.
     */
    private int size;

    /**
     * Holds the current number of modificatoin generation of the linked list, every time the list is modified, the modification count is
     * incremented by one, this is tracked when iterating over the linked list. The modification cound is not serialized with the structure
     * as it is assumed to be rest to zero when read back
     */
    private long modificationCount = 0;

    /**
     * Default constructor, will construct the default empty linked list, the list is empty by default. Calling subsequent add methods will
     * add new nodes to the list.
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Create a hard copy from another linked list, this constructor will ensure that all elements of the source linked list are copied by
     * reference into the current list, the elementes themselves are not copied but new links will be created for each new element in this
     * list.
     *
     * @param from the source list from which to create this liset
     */
    public LinkedList(LinkedList<T> from) {}

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T value : this) {
            if (!Objects.isNull(value) && value.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int pointer = 0;
            int lastReturned = -1;
            long expectedModifyCount = modificationCount;
            ListNode<T> node = head;

            @Override
            public boolean hasNext() {
                return node != tail;
            }

            @Override
            public void remove() {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                ListNode<T> next = node.next;
                ListNode<T> prev = node.prev;
                if (!Objects.isNull(next)) {
                    next.prev = prev;
                }
                if (!Objects.isNull(prev)) {
                    prev.next = next;
                }
                size--;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (Objects.isNull(node)) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                lastReturned = pointer;
                pointer = pointer + 1;
                return node.element;
            }

            private final void checkForComodification() {
                if (expectedModifyCount != modificationCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        int index = 0;
        Object[] array = new Object[size];
        for (Object element : this) {
            if (index >= size) {
                throw new IllegalStateException();
            }
            array[index++] = element;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (Objects.isNull(a)) {
            return (T[]) EMTPY_LIST_ARRAY;
        } else if (a.length != size) {
            int index = 0;
            a = Arrays.copyOf(a, size);
            for (Object element : this) {
                a[index++] = (T) element;
            }
        } else {
            int index = 0;
            for (Object element : this) {
                a[index++] = (T) element;
            }
        }
        return a;
    }

    @Override
    public boolean add(T e) {
        if (size >= LIST_SIZE_LIMIT) {
            throw new IllegalStateException();
        }
        if (head == null) {
            head = new ListNode<>();
            head.element = e;
            head.next = null;
            head.prev = null;
            tail = head;
        } else {
            ListNode<T> nextNode = new ListNode<>();
            nextNode.element = e;
            nextNode.prev = tail;
            nextNode.next = null;
            tail.next = nextNode;
            tail = nextNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<T> found = extract(o);
        if (!Objects.isNull(found)) {
            ListNode<T> next = found.next;
            ListNode<T> prev = found.prev;
            if (!Objects.isNull(prev)) {
                prev.next = next;
            }
            if (!Objects.isNull(next)) {
                next.prev = prev;
            }
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            for (T entry : this) {
                if (!compare(entry, element)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T element : c) {
            add(index, element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean contains = true;
        for (Object element : c) {
            if (!contains) {
                remove(element);
            } else {
                contains = remove(element);
            }
        }
        return contains;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ListNode<T> found = extract(index);
        if (Objects.isNull(found)) {
            throw new IllegalStateException();
        }
        return found.element;
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ListNode<T> found = extract(index);
        if (Objects.isNull(found)) {
            throw new IllegalStateException();
        }
        found.element = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ListNode<T> found = extract(index);
        if (Objects.isNull(found)) {
            throw new IllegalStateException();
        }
        ListNode<T> newNode = new ListNode<>();
        ListNode<T> prev = found.prev;

        if (!Objects.isNull(prev)) {
            prev.next = newNode;
            newNode.prev = prev;
            found.prev = newNode;
        }
        newNode.next = found;
        newNode.element = element;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ListNode<T> found = extract(index);
        if (Objects.isNull(found)) {
            throw new IllegalStateException();
        }
        ListNode<T> next = found.next;
        ListNode<T> prev = found.prev;
        if (!Objects.isNull(next)) {
            next.prev = prev;
        }
        if (!Objects.isNull(prev)) {
            prev.next = next;
        }
        size--;
        return found.element;
    }

    @Override
    public int indexOf(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lastIndexOf'");
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {
            int pointer = index;
            int lastReturned = -1;
            ListNode<T> lastReturnedNode = null;
            long expectedModifyCount = modificationCount;
            ListNode<T> node = LinkedList.this.extract(index);

            @Override
            public boolean hasNext() {
                return node != tail;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (Objects.isNull(node)) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                lastReturnedNode = node;
                lastReturned = pointer;
                pointer = pointer + 1;
                T result = node.element;
                node = node.next;
                return result;
            }

            @Override
            public boolean hasPrevious() {
                return node != head;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                if (Objects.isNull(node.prev)) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                lastReturnedNode = node.prev;
                lastReturned = pointer;
                pointer = pointer - 1;
                T result = lastReturnedNode.element;
                node = lastReturnedNode;
                return result;
            }

            @Override
            public int nextIndex() {
                return pointer;
            }

            @Override
            public int previousIndex() {
                return pointer - 1;
            }

            @Override
            public void remove() {
                pointer = lastReturned;
                lastReturned = -1;
                expectedModifyCount = 0;
            }

            @Override
            public void set(T e) {
                if (Objects.isNull(lastReturnedNode)) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                lastReturnedNode.element = e;
            }

            @Override
            public void add(T e) {}

            private final void checkForComodification() {
                if (expectedModifyCount != modificationCount) {
                    throw new ConcurrentModificationException();
                }
            }

        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {}

    private ListNode<T> extract(Object object) {
        ListNode<T> current = head;
        while (current != null) {
            if (compare(current.element, object)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private ListNode<T> extract(int index) {
        ListNode<T> current;
        int median = size / 2;
        if (index < median) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i >= index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private int extract(ListNode<T> node) {
        int index = -1;
        ListNode<T> current = head;
        while (current != null) {
            if (current == node) {
                return index + 1;
            }
            index++;
        }
        return index;
    }

    private boolean compare(Object left, Object right) {
        if (Objects.isNull(left) && Objects.isNull(right)) {
            return true;
        }
        return !Objects.isNull(left) && left.equals(right);
    }

    private static final class ListNode<T> {

        private T element = null;

        private ListNode<T> prev = null;

        private ListNode<T> next = null;
    }
}
