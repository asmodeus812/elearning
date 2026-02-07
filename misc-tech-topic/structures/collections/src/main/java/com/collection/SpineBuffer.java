package com.collection;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SpineBuffer<T> implements List<T>, Serializable {

    /**
     * The maximum amount of elements that can be held in the spine buffer, note that every sub-array in the spine buffer is limited to this
     * amount and in total the actual spine buffer itself is also limited to the same size, therefore we can hold that many sub-arrays in
     * the spine buffer
     */
    private static final int ARRAY_SIZE_LIMIT = Integer.MAX_VALUE;

    /**
     * The default empty spine array, this spine array buffer is mostly a placeholder for an empty spine array buffer
     */
    private static final Object[][] EMTPY_LIST_BUFFER = new Object[0][0];

    /**
     * The primary storage component that hold the spine buffer data, every sub-array in that buffer is filled until it is completely full.
     * Then the bucket is moved to the next closest sub-array from the spine buffer.
     */
    private Object[][] buffer = EMTPY_LIST_BUFFER;

    /**
     * Points at the current sub-array index in the spine buffer, this array index is the current spine buffer sub-array that has enough
     * empty space to fit new elements that are about to be added to the spine buffer. When this sub-array is filled up, then this index is
     * incremented to the next closest spine buffer sub-array slot that has empty slots to fit new elements.
     */
    private int current = -1;

    /**
     * P
     */
    private int offset = -1;

    private int size = 0;

    public SpineBuffer() {
        this.buffer = new Object[4][16];
        this.current = 0;
        this.offset = 0;
        this.size = 0;
    }

    public SpineBuffer(int capacity) {
        this.buffer = new Object[capacity / 16][capacity];
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return buffer.length * buffer[0].length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T element : this) {
            if (compare(element, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int subIndex = 0;
            private int baseIndex = 0;

            @Override
            public boolean hasNext() {
                return baseIndex < buffer.length && subIndex < buffer[baseIndex].length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (baseIndex >= buffer.length) {
                    throw new IllegalStateException();
                }
                if (subIndex >= buffer[baseIndex].length) {
                    throw new IllegalStateException();
                }
                if (subIndex == buffer[baseIndex].length - 1) {
                    baseIndex++;
                    subIndex = 0;
                }
                return (T) buffer[baseIndex][subIndex];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean add(T e) {
        if (current >= buffer.length) {
            throw new IllegalStateException();
        }
        if (offset >= buffer[current].length) {
            throw new IllegalStateException();
        }
        if (size >= ARRAY_SIZE_LIMIT) {

        }
        if (offset == buffer[current].length - 1) {
            current++;
            offset = 0;
        }
        buffer[current][offset] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Spine buffer does not support element removal");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public T set(int index, T element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'set'");
    }

    @Override
    public void add(int index, T element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subList'");
    }

    private boolean compare(Object left, Object right) {
        if (Objects.isNull(left) && Objects.isNull(right)) {
            return true;
        }
        return !Objects.isNull(left) && left.equals(right);
    }

    private Object[] ensureArrayCapacity(int factor) {
        if (size() == capacity()) {
            if (buffer.length == ARRAY_SIZE_LIMIT) {
                throw new IllegalStateException();
            }
            long newCapacity = (long) buffer.length * factor;
            newCapacity = Math.min(newCapacity, ARRAY_SIZE_LIMIT);

            Object[][] newBuffer = new Object[(int) newCapacity][];
            for (int i = 0; i < buffer.length; i++) {
                long newSubCapacity = (long) buffer[i].length * factor;
                newSubCapacity = Math.min(newSubCapacity, ARRAY_SIZE_LIMIT);
                newBuffer[i] = Arrays.copyOf(buffer[i], (int) newSubCapacity);
            }
            buffer = newBuffer;
        } else if (size() < (capacity() / 2)) {
            long newCapacity = (long) capacity() / factor;
            newCapacity = Math.max(size, newCapacity);
            data = Arrays.copyOf(data, (int) newCapacity);
        }
        return data;
    }

    private <R> R[] copyArrayContents(R[] a) {
        if (Objects.isNull(a)) {
            return (R[]) EMTPY_LIST_BUFFER;
        } else if (a.length != size) {
            a = new Object[size];
            for (int i = 0; i < max; i++) {

            }
            // a = (R[]) Arrays.copyOf(data, size);
        } else {
            // for (int i = 0; i < size; i++) {
            // a[i] = (R) data[i];
            // }
        }
        return a;
    }
}
