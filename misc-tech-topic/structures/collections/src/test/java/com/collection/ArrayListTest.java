package com.collection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class ArrayListTest {

    private Supplier<List<Integer>> factory;

    private List<Integer> list;

    @Before
    void setUp() {
        factory = this::createList;
        list = createList();
    }

    protected List<Integer> createList() {
        return new ArrayList<>();
    }

    @Test
    void newListIsEmptySizeZero() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(Collections.emptyList(), list);
        assertEquals(Collections.emptyList().hashCode(), list.hashCode());
    }

    @Test
    void sizeTracksAddsAndRemoves() {
        assertEquals(0, list.size());
        list.add(10);
        assertEquals(1, list.size());
        list.add(20);
        assertEquals(2, list.size());
        list.remove(Integer.valueOf(10));
        assertEquals(1, list.size());
        list.remove(0);
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void addAppendsAndReturnsTrue() {
        assertTrue(list.add(1));
        assertTrue(list.add(2));
        assertEquals(list, 1, 2);
        assertEquals(2, list.size());
        assertEquals(2, list.get(1));
    }

    @Test
    void addAtIndexInsertsAndShifts() {
        list = createArrayListInstance(factory, 1, 3);
        list.add(1, 2);
        assertEquals(list, 1, 2, 3);

        assertIndexOutOfBounds(() -> list.add(-1, 99));
        assertIndexOutOfBounds(() -> list.add(list.size() + 1, 99));

        list.add(list.size(), 4);
        assertEquals(list, 1, 2, 3, 4);
    }

    @Test
    void addAllAppendsInOrderAndReturnsChangeFlag() {
        list = createArrayListInstance(factory, 1);
        assertFalse(list.addAll(Collections.emptyList()));
        assertTrue(list.addAll(Arrays.asList(2, 3)));
        assertEquals(list, 1, 2, 3);
    }

    @Test
    void addAllAtIndexInsertsInOrderAndReturnsChangeFlag() {
        list = createArrayListInstance(factory, 1, 4);
        assertTrue(list.addAll(1, Arrays.asList(2, 3)));
        assertEquals(list, 1, 2, 3, 4);

        assertFalse(list.addAll(2, Collections.emptyList()));

        assertIndexOutOfBounds(() -> list.addAll(-1, Arrays.asList(9)));
        assertIndexOutOfBounds(() -> list.addAll(list.size() + 1, Arrays.asList(9)));

        assertTrue(list.addAll(list.size(), Arrays.asList(5, 6)));
        assertEquals(list, 1, 2, 3, 4, 5, 6);
    }

    @Test
    void getAndSetWorkAndBoundsChecked() {
        list = createArrayListInstance(factory, 10, 20, 30);
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(2));
        assertIndexOutOfBounds(() -> list.get(-1));
        assertIndexOutOfBounds(() -> list.get(3));

        Integer old = list.set(1, 99);
        assertEquals(20, old);
        assertEquals(list, 10, 99, 30);

        assertIndexOutOfBounds(() -> list.set(-1, 1));
        assertIndexOutOfBounds(() -> list.set(3, 1));
    }

    @Test
    void removeByIndexRemovesAndShiftsAndBoundsChecked() {
        list = createArrayListInstance(factory, 1, 2, 3, 4);
        assertEquals(2, list.remove(1));
        assertEquals(list, 1, 3, 4);
        assertEquals(4, list.remove(list.size() - 1));
        assertEquals(list, 1, 3);

        assertIndexOutOfBounds(() -> list.remove(-1));
        assertIndexOutOfBounds(() -> list.remove(list.size()));
    }

    @Test
    void removeByObjectRemovesFirstOccurrenceReturnsFlag() {
        list = createArrayListInstance(factory, 1, 2, 2, 3);
        assertTrue(list.remove(Integer.valueOf(2)));
        assertEquals(list, 1, 2, 3);
        assertFalse(list.remove(Integer.valueOf(999)));
        assertEquals(list, 1, 2, 3);
    }

    @Test
    void clearEmptiesList() {
        list = createArrayListInstance(factory, 1, 2, 3);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    void containsAndContainsAllWork() {
        list = createArrayListInstance(factory, 1, 2, 3);
        assertTrue(list.contains(2));
        assertFalse(list.contains(9));

        assertTrue(list.containsAll(Arrays.asList(1, 3)));
        assertFalse(list.containsAll(Arrays.asList(1, 9)));
        assertTrue(list.containsAll(Collections.emptyList()));
    }

    @Test
    void indexOfAndLastIndexOfHandleDuplicates() {
        list = createArrayListInstance(factory, 5, 7, 5, 9, 5);
        assertEquals(0, list.indexOf(5));
        assertEquals(4, list.lastIndexOf(5));
        assertEquals(-1, list.indexOf(999));
        assertEquals(-1, list.lastIndexOf(999));
    }

    @Test
    void toArrayNoArgsReturnsObjectArrayInOrder() {
        list = createArrayListInstance(factory, 1, 2, 3);
        Object[] arr = list.toArray();
        assertArrayEquals(new Object[] {1, 2, 3}, arr);
    }

    @Test
    void toArrayTypedArraySemantics() {
        list = createArrayListInstance(factory, 1, 2, 3);

        Integer[] small = new Integer[0];
        Integer[] a1 = list.toArray(small);
        assertArrayEquals(new Integer[] {1, 2, 3}, a1);
        assertNotSame(small, a1);

        Integer[] big = new Integer[5];
        Arrays.fill(big, -1);
        Integer[] a2 = list.toArray(big);
        assertSame(big, a2);
        assertArrayEquals(new Integer[] {1, 2, 3, null, -1}, big);
    }

    @Test
    void iteratorIteratesInOrder() {
        list = createArrayListInstance(factory, 1, 2, 3);
        List<Integer> seen = new ArrayList<>();
        for (Integer x : list)
            seen.add(x);
        assertEquals(Arrays.asList(1, 2, 3), seen);
    }

    @Test
    void iteratorRemoveRemovesLastReturnedIfSupported() {
        list = createArrayListInstance(factory, 1, 2, 3);
        Iterator<Integer> it = list.iterator();
        assertEquals(1, it.next());
        it.remove();
        assertEquals(2, it.next());
        assertEquals(list, 2, 3);
    }

    @Test
    void listIteratorForwardBackwardIndices() {
        list = createArrayListInstance(factory, 10, 20, 30);

        ListIterator<Integer> it = list.listIterator();
        assertEquals(0, it.nextIndex());
        assertEquals(-1, it.previousIndex());

        assertEquals(10, it.next());
        assertEquals(1, it.nextIndex());
        assertEquals(0, it.previousIndex());

        assertEquals(20, it.next());
        assertTrue(it.hasPrevious());
        assertEquals(20, it.previous());
        assertEquals(20, it.next());
        assertEquals(30, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void listIteratorAtIndexStartsCorrectlyAndBoundsChecked() {
        list = createArrayListInstance(factory, 1, 2, 3, 4);

        ListIterator<Integer> it = list.listIterator(2);
        assertEquals(3, it.next());
        assertEquals(3, it.previous());

        assertIndexOutOfBounds(() -> list.listIterator(-1));
        assertIndexOutOfBounds(() -> list.listIterator(5));

        ListIterator<Integer> itPastEnd = list.listIterator(list.size());
        assertFalse(itPastEnd.hasNext());
    }

    @Test
    void listIteratorMutationsIfSupported() {
        list = createArrayListInstance(factory, 1, 3);

        ListIterator<Integer> it = list.listIterator();
        assertEquals(1, it.next());
        it.add(2);
        assertEquals(list, 1, 2, 3);

        assertEquals(3, it.next());
        it.set(99);
        assertEquals(list, 1, 2, 99);

        it.remove();
        assertEquals(list, 1, 2);
    }

    @Test
    void subListIsBackedViewModificationsReflectBothWays() {
        list = createArrayListInstance(factory, 1, 2, 3, 4, 5);
        List<Integer> sub = list.subList(1, 4);
        assertEquals(Arrays.asList(2, 3, 4), sub);

        sub.set(1, 99);
        assertEquals(list, 1, 2, 99, 4, 5);

        sub.remove(0);
        assertEquals(list, 1, 99, 4, 5);

        sub.add(1, 77);
        assertEquals(list, 1, 99, 77, 4, 5);
    }

    @Test
    void subListBoundsChecked() {
        list = createArrayListInstance(factory, 1, 2, 3);
        assertIndexOutOfBounds(() -> list.subList(-1, 2));
        assertIndexOutOfBounds(() -> list.subList(0, 4));
        assertThrows(IllegalArgumentException.class, () -> list.subList(2, 1));
    }

    @Test
    void equalsAndHashCodeMatchStandardListContract() {
        List<Integer> a = createArrayListInstance(factory, 1, 2, 3);
        List<Integer> b = createArrayListInstance(factory, 1, 2, 3);
        List<Integer> c = createArrayListInstance(factory, 1, 2, 4);

        Assert.assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, Arrays.asList(1, 2));
        assertEquals(a, 1, 2, 3);
    }

    @Test
    void removeAllRemovesAllOccurrencesReturnsChangeFlag() {
        list = createArrayListInstance(factory, 1, 2, 2, 3, 4, 2);
        assertTrue(list.removeAll(Arrays.asList(2, 4)));
        assertEquals(list, 1, 3);

        assertFalse(list.removeAll(Arrays.asList(999)));
    }

    @Test
    void retainAllKeepsOnlySpecifiedReturnsChangeFlag() {
        list = createArrayListInstance(factory, 1, 2, 2, 3, 4);
        assertTrue(list.retainAll(Arrays.asList(2, 4)));
        assertEquals(list, 2, 2, 4);

        assertFalse(list.retainAll(Arrays.asList(2, 4)));
    }

    @Test
    void replaceAllAppliesOperator() {
        list = createArrayListInstance(factory, 1, 2, 3);
        UnaryOperator<Integer> op = x -> x * 10;
        list.replaceAll(op);
        assertEquals(list, 10, 20, 30);
    }

    @Test
    void sortSortsWithComparator() {
        list = createArrayListInstance(factory, 3, 1, 2);
        list.sort(Integer::compareTo);
        assertEquals(list, 1, 2, 3);

        list.sort(Comparator.reverseOrder());
        assertEquals(list, 3, 2, 1);
    }

    @Test
    void forEachVisitsAllInOrder() {
        list = createArrayListInstance(factory, 1, 2, 3);
        List<Integer> seen = new ArrayList<>();
        list.forEach(seen::add);
        assertEquals(Arrays.asList(1, 2, 3), seen);
    }

    @Test
    void spliteratorTraversesAllElements() {
        list = createArrayListInstance(factory, 1, 2, 3, 4);
        Spliterator<Integer> sp = list.spliterator();
        List<Integer> seen = new ArrayList<>();
        sp.forEachRemaining(seen::add);
        assertEquals(Arrays.asList(1, 2, 3, 4), seen);
    }

    @Test
    void streamAndParallelStreamWork() {
        list = createArrayListInstance(factory, 1, 2, 3, 4);
        assertEquals(10, list.stream().mapToInt(Integer::intValue).sum());
        assertEquals(10, list.parallelStream().mapToInt(Integer::intValue).sum());
    }

    @Test
    void removeIfRemovesMatchingReturnsFlag() {
        list = createArrayListInstance(factory, 1, 2, 3, 4, 5);
        assertTrue(list.removeIf(x -> x % 2 == 0));
        assertEquals(list, 1, 3, 5);

        assertFalse(list.removeIf(x -> x == 999));
    }

    @Test
    void assertNullElementBehavior() {
        list.add(null);
        assertTrue(list.contains(null));
        assertTrue(list.remove(null));
        assertFalse(list.contains(null));
    }

    static <T> List<T> createArrayListInstance(Supplier<List<T>> factory, T... values) {
        List<T> list = Objects.requireNonNull(factory.get(), "factory.get() returned null");
        for (T v : values)
            list.add(v);
        return list;
    }

    static void assertEquals(int expected, Integer actual) {
        Assert.assertEquals((int) expected, (int) actual);
    }

    static <T> void assertEquals(List<T> actual, T... expected) {
        assertEquals(Arrays.asList(expected), actual);
    }

    static void assertIndexOutOfBounds(ThrowingRunnable ex) {
        assertThrows(IndexOutOfBoundsException.class, ex);
    }

}
