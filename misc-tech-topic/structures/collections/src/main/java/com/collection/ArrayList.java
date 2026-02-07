package com.collection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Default dynamic array based implementation for a list, this implementation takes advantage of using a plain java array to store the data.
 * This array is dynamically re-sized when new elements are added to it or when elements are removed to allow fro a more compact
 * representation. Several options can be adjusted to make sure that the client has a control over the resize behavior of the internal
 * primitive array.
 *
 */
public class ArrayList<T> implements List<T>, Serializable {

    /**
     * The max amount limit of this array, is boudn by the virtual machine, and in this case should not exceed the max amount a signed
     * integer can hold - 0x7fffffff, or that would be 2^31
     */
    private static final int ARRAY_SIZE_LIMIT = Integer.MAX_VALUE;

    /**
     * By default used when the array is cleared internally and when the internal array is reset. Note that these actions de-reference the
     * elements first.
     */
    private static final Object[] EMTPY_LIST_ARRAY = new Object[0];

    /**
     * Hold the internal data for this array, this holds Object types not T because the value of T can not be instantated.
     */
    private Object[] data = EMTPY_LIST_ARRAY;

    /**
     * The current size of the list, this is not the size of the array rather it is the actual number of elements thus far intserted and
     * held by this array list internally.
     */
    private int size = 0;

    /**
     * Holds the current number of modification generation of the array list, every time the list is modified the modification count is
     * incremented by one, this is tracked when iterating over the array list. The modification count is not serialized with the structure
     * as it is assumed to be reset to zero when read back
     */
    private long modificationCount = 0;

    /**
     * Construct a deafult array list object, that has a predefined size and capacity
     */
    public ArrayList() {
        this.data = new Object[16];
        this.size = 0;
    }

    /**
     * Construct an array list from a given amount of capacity, the default size of the internal array will be equal to the given capacity.
     *
     * @param capacity the new capacity of the internal array
     * @throws throw new IndexOutOfBoundsException() when the capacity exceeds the allowed limits
     */
    public ArrayList(int capacity) {
        if (capacity < 0 || capacity >= ARRAY_SIZE_LIMIT) {
            throw new IndexOutOfBoundsException();
        }
        this.data = new Object[capacity];
        this.size = 0;
    }

    /**
     * Constuct an array lsit from a given array list, strict sizing is in place. What this implies is that the underlying array list that
     * is constructed has the same size and capacity as the source array list.
     *
     * @param from the array list from which to construct this array list
     * @throws throw new IndexOutOfBoundsException() when the from array is invalid
     */
    public ArrayList(ArrayList<T> from) {
        if (!from.isEmpty() || from.capacity() == 0) {
            throw new IndexOutOfBoundsException();
        }
        this.data = Arrays.copyOf(from.data, from.capacity());
        this.size = from.size();
    }

    /**
     * Construct a new array list from a given array list, starting from an offset into the source list, that offset start index must be
     * valid for the source array, and must not exceed its size.
     *
     * @param from the source array list from which to construct the array
     * @param start the starting index from the source array to copy construct into the new array list
     * @throws throw new IndexOutOfBoundsException() when the the starting index is out of boudns or valid
     */
    public ArrayList(ArrayList<T> from, int start) {
        if (from.isEmpty() || start >= from.size() || from.size() > from.capacity()) {
            throw new IndexOutOfBoundsException();
        }
        this.data = Arrays.copyOfRange(from.data, start, from.capacity());
        this.size = from.capacity() - start;
    }

    /**
     * Construct a new array list from a given array list, starting from an offset into the source list up until but not including an end
     * offset. Thus the new arrayw ill contain all elements from the source beteween [start, end - 1].
     *
     * @param from the source array from which to copy construct
     * @param start the starting index from the source array
     * @param end the ending index, exclusive from the source array
     * @throws throw new IndexOutOfBoundsException() when the provided range of indices is invalid or out of boudns
     */
    public ArrayList(ArrayList<T> from, int start, int end) {
        if (start > 0 && end > 0 && start < end && end <= from.size() && end - size > 0 && from.size() <= from.capacity()) {
            throw new IndexOutOfBoundsException();
        }
        this.data = Arrays.copyOfRange(from.data, start, end);
        this.size = end - start;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Return the internal capacity of this array list, this method works in conjunction with the size() method to allow clients of this
     * class to inspect the internal state of the array. The capacity is defined as the maximum number of elements the array can hold
     * internally before a resize is required. A resize is a costly operation you may use this information to restrict or prevent the
     * elements that are inserted into the array.
     *
     * @return the capacity of the array.
     */
    public int capacity() {
        return data.length;
    }

    @Override
    public boolean isEmpty() {
        return capacity() == 0 || size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Object[] toArray() {
        return copyArrayElements();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return copyArrayContents(a);
    }

    @Override
    public boolean add(T e) {
        ensureArrayCapacity(2);
        modificationCount++;
        data[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = locateElementIndex(o, true);
        if (index >= 0 && index < size()) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        for (Object element : c) {
            result = contains(element);
            if (!result) {
                return result;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return false;
        }
        boolean result = true;
        for (T element : c) {
            if (!result) {
                add(element);
            } else {
                result = add(element);
            }
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T element : c) {
            add(index++, element);
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
        boolean retained = true;

        int newDataSize = 0;
        int newDataCapacity = Math.max(c.size(), capacity());
        Object[] newDataArray = new Object[newDataCapacity];

        for (Object element : c) {
            if (contains(c)) {
                newDataArray[newDataSize++] = element;
            } else {
                retained = false;
            }
        }
        clearArrayContents(0);
        data = newDataArray;
        size = newDataSize;
        return retained;
    }

    @Override
    public void clear() {
        clearArrayContents(0);
        data = EMTPY_LIST_ARRAY;
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) data[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ensureArrayCapacity(2);
        shiftElementsRight(index);
        data[index] = element;
        modificationCount++;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T element = (T) data[index];
        ensureArrayCapacity(2);
        shiftElementsLeft(index);
        modificationCount++;
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return locateElementIndex(o, true);
    }

    @Override
    public int lastIndexOf(Object o) {
        return locateElementIndex(o, false);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int pointer = 0;
            int lastReturned = -1;
            long expectedModifyCount = modificationCount;

            @Override
            public boolean hasNext() {
                return pointer < size;
            }

            @Override
            public void remove() {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                ArrayList.this.remove(lastReturned);
                pointer = lastReturned;
                lastReturned = -1;
                expectedModifyCount = modificationCount;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                checkForComodification();
                lastReturned = pointer;
                pointer = pointer + 1;
                return (T) data[lastReturned];
            }

            private final void checkForComodification() {
                if (expectedModifyCount != modificationCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        return new ListIterator<T>() {
            int pointer = index;
            int lastReturned = -1;
            long expectedModifyCount = modificationCount;

            @Override
            public boolean hasNext() {
                return pointer < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                checkForComodification();
                lastReturned = pointer;
                pointer = pointer + 1;
                return (T) data[lastReturned];
            }

            @Override
            public boolean hasPrevious() {
                return pointer > 0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                checkForComodification();
                lastReturned = pointer;
                pointer = pointer - 1;
                return (T) data[lastReturned];
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
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                ArrayList.this.remove(lastReturned);
                pointer = lastReturned;
                lastReturned = -1;
                expectedModifyCount = modificationCount;
            }

            @Override
            public void set(T e) {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                checkForComodification();
                ArrayList.this.set(pointer, e);
            }

            @Override
            public void add(T e) {
                checkForComodification();
                ArrayList.this.add(pointer, e);
                pointer = pointer + 1;
                lastReturned = -1;
                expectedModifyCount = modificationCount;
            }

            private final void checkForComodification() {
                if (expectedModifyCount != modificationCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        // make sure that the supplied indexes make sense from the point of view of the current state of
        // this array list. The target indices are not allowed to be negative they are not allowed to be greater than the current size of
        // the array and from index can never be equal or greater than the to index
        if (fromIndex > toIndex || fromIndex < 0 || toIndex < 0 || fromIndex >= size() || toIndex > size()) {
            throw new IndexOutOfBoundsException();
        }
        // the new list is a view into the old one, such that the elements of the new array list are not actually copied but only a new
        // array is created to hold the elements that fall into the range specfiied by from-to index boundaries
        return new ArrayList<>(this, fromIndex, toIndex);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(data);
        result = prime * result + Objects.hash(capacity(), size());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArrayList)) {
            return false;
        }
        ArrayList<T> other = (ArrayList<T>) obj;
        return Arrays.deepEquals(data, other.data) && capacity() == other.capacity() && size() == other.size();
    }

    private final void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < size; i++) {
            s.writeObject(data[i]);
        }
    }

    private final void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        s.readInt();
        if (size > 0) {
            data = new Object[size];
            for (int i = 0; i < size; i++) {
                data[i] = s.readObject();
            }
        } else if (size == 0) {
            data = EMTPY_LIST_ARRAY;
        } else {
            throw new IOException();
        }
        modificationCount = 0;
    }

    private boolean compare(Object left, Object right) {
        if (Objects.isNull(left) && Objects.isNull(right)) {
            return true;
        }
        return !Objects.isNull(left) && left.equals(right);
    }

    private Object[] copyArrayElements() {
        return Arrays.copyOf(data, size);
    }

    private Object[] ensureArrayCapacity(int factor) {
        if (size() == capacity()) {
            if (capacity() == ARRAY_SIZE_LIMIT) {
                throw new IllegalStateException();
            }
            long newCapacity = (long) capacity() * factor;
            newCapacity = Math.min(newCapacity, ARRAY_SIZE_LIMIT);
            data = Arrays.copyOf(data, (int) newCapacity);
        } else if (size() < (capacity() / 2)) {
            long newCapacity = (long) capacity() / factor;
            newCapacity = Math.max(size, newCapacity);
            data = Arrays.copyOf(data, (int) newCapacity);
        }
        return data;
    }

    private <R> R[] copyArrayContents(R[] a) {
        if (Objects.isNull(a)) {
            return (R[]) EMTPY_LIST_ARRAY;
        } else if (a.length != size) {
            a = (R[]) Arrays.copyOf(data, size);
        } else {
            for (int i = 0; i < size; i++) {
                a[i] = (R) data[i];
            }
        }
        return a;
    }

    private Object[] clearArrayContents(int start) {
        if (start >= data.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = start; i < size; i++) {
            data[i] = null;
        }
        return data;
    }

    private Object[] shiftElementsLeft(int start) {
        if (size > data.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = start + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        data[size - 1] = null;
        return data;
    }

    private Object[] shiftElementsRight(int start) {
        if (size > data.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = size - 1; i >= start; i--) {
            data[i] = data[i + 1];
        }
        data[start - 1] = null;
        return data;
    }

    private int locateElementIndex(Object o, boolean first) {
        int lastIndex = -1;
        for (int i = 0; i < size; i++) {
            Object element = data[i];
            if (compare(element, o)) {
                if (first) {
                    return i;
                } else {
                    lastIndex = i;
                }
            }
        }
        return lastIndex;
    }
}
