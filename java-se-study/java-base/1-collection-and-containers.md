## List

Ordered sequence, duplicates are allowed accessing elements by index, that is specified by the interface itself. One
of the most prolific interfaces in the java standard library. It defines behavior for working with ordered sequence
of objects, accessing them by their index or position

### Properties

- ordered collection or sequence
- precise control over list element position
- allows duplicate elements
- zero based indexing
- may allow null elements based on implementations

### Interface

- size() - return the size of the list
- isEmpty() - checks if the list is empty
- contains(Object o) - checks if element is contained based on .equals
- get(int index) - may throw `IndexOutOfBoundsExcetpion`
- set(int index, E element) - may throw `IndexOutOfBoundsExcetpion`
- toArray(T[] a) or toArray() - array copy of contained elements

### Implementations

1. `ArrayList` - The default general purpose list

```java
public class ArrayList<E> extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

- stores array of elements T[]
- ordered by index
- fast random access
- NOT synchronized
- fast-fail iterator, exception if structure is modified during iteration, `ConcurrentModificationExcetpion`

2. `LinkedList` - Acts as a fast insert remove list and by proxy a good implementation for `Deque` - double ended queue.

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

- NOT synchronized
- slow random access by index
- permits all elements including null
- doubly linked list expressed by head/tail nodes
- finds/iterates from head/tail based on closeness of index to head/tail
- fast-fail iterator, exception if structure is modified during iteration

### Caveats

- remove(int) vs remove(Object) - this bites, when we have List<Integer> because we can really have both remove(1)
  and remove(Integer.valueOf(1)), when removing values.

- subList(from, to) - is a view, it is backed by the original list, structural modification of that view can
  actually trigger `ConcurrentModificationExcetpion`, because that view is not like substring, it does not create
  a new instance of the sub-list.

- Arrays.asList(...) is fixed size, you can not call add/remove/clear or you will get
  `UnsupportedOperationExcetpion`, you can however call set.

## Stack (Deque)

A stack is a Last in first out structure (LIFO), the most recently added element will be the first to be removed,
and that is defined by the interface. The stack data structure in Java is a legacy one it is not advised to use that
one as there are better alternatives like the double ended queue which can be used for both a stack and a queue
structure - `Deque` interface. That is what we have explored below.

### Interface

- push(e) - add new element to the top of the stack
- pop() - from element from top of the stack
- peek() - element at the top of the stack, non throwing
- element() - element at the top of the stack, throwing if empty

### Implementations

1. `ArrayDeque` - the recommended data structure to use. Resizeable array implementation akin to `ArrayList`. Unlike
   `ArrayList` the `ArrayDeque` does not implement List, only `Deque`

```java
public class ArrayDeque<E> extends AbstractCollection<E>
                           implements Deque<E>, Cloneable, Serializable
```

- NOT synchronized
- stores array of elements T[]
- fast-fail iterator, exception if structure is modified during iteration

2. `LinkedList` - works, but usually not the best idea it also implements the `Deque` interface as well, we have
   already had a brief look at the `LinkedList` with the List interface, since it both implements List and `Deque`.

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

- NOT synchronized
- NOT permitting null elements
- slow random access by index
- permits all elements including null
- doubly linked list expressed by head/tail nodes
- finds/iterates from head/tail based on closeness of index to head/tail
- fast-fail iterator, exception if structure is modified during iteration

`The interface of deque defines methods to access the elements at both ends, methods are provided to insert, remove
and examine the element. Each of these methods exists in two forms - one that throws an exception if the operation
fails the other returns a special value - null, or false, depending on the operation`

### Caveats

Legacy Stack class extends Vector, and is synchronized, it woks but is rarely the best choice, can prove to be
slow and hard to use, prefer implementations of `Deque` or more precisely the `ArrayDeque` implementation instead.

Throwing variants - extra care for both implementations `LinkedList` and `ArrayDeque`

- addFirst - might throw based on the underlying implementation does not allow null elements to be inserted, or
  growing the structure is not possible (`ArrayDeque` does not allow null, `LinkedList` does)

- removeFirst - removes the element from the top of the stack, will throw `NoSuchElementExcetpion`, if the stack
  is empty, both the linked list version and the array list will throw
- getFirst - will throw if the stack is empty, or will return the head of the stack if there is at least one element, both main implementation will throw.

NON Throwing variants - safe for both `LinkedList` and `ArrayDeque`

- offerFirst - adds to the stack as first element but does throw if the element to be added is invalid - null
- pollFirst - will remove from the head of the stack, does NOT throw instead returns null if the stack is empty
- peekFirst - will either return the head of the stack or null, does NOT throw if the stack is empty

The internal implementation of `ArrayDeque` uses a mix of the throwing and non-throwing variants where possible. For
example it is critical for the push/pop to be stricter while the peek uses a more loose non-throwing variant

`If a stack implementation allows storing null, then peek/poll returning null becomes ambiguous. This is why
ArrayDeque disallows null. Don’t rely on LinkedList allowing null unless you truly need it (it complicates
emptiness checks).`

## Queue

The queue interface defines a first in first out data structure where elements inserted are retrieved in the order
of insertion. It is common to recommend the default implementation of queue which is the `ArrayDeque`, but others such
as `LinkedList` are also an option.

### Interface

- add(e) - add new element to very end of the queue
- remove() - removes element from the front of the queue
- peek() - element at the front of the queue, non throwing if the queue is empty
- element() - element at the front of the queue throwing if the queue is empty

`The interface of Queue defines a strict queue interface, unlike Deque which represents a more complete double ended
queue, the queue interface strictly defines the expected queue methods and behavior only.`

### Implementations

1. `PriorityQueue` - Or also known as heap - min or max, removes and re-orders elements in a priority order, such that
   at the front of the queue we have either the min or max element in the entire data structure. Represents an unbounded
   priority queue based on a heap, the elements of the queue are ordered according to their Comparable natural ordering, or
   by a comparator. Provided at the queue construction time. The priority queue does NOT permit null elements. The queue is
   unbounded. The priority queue is always initialized with initial capacity, thus accessing 'empty' it on empty state is
   not possible. Meaning that peek will not throw

```java
public abstract class AbstractQueue<E>
    extends AbstractCollection<E>
    implements Queue<E> {}

public class PriorityQueue<E> extends AbstractQueue<E>
    implements java.io.Serializable {}
```

- NOT synchronized
- stores array of elements T[]
- unbounded by capacity restrictions
- iterations are NOT ordered or sorted for a priority queue or a heap
- fast-fail iterator, exception if structure is modified during iteration
- comparators must be consistent, mixing incomplete types leads to runtime failures.

1. `ArrayDeque` - the recommended data structure to use. Resizeable array implementation akin to `ArrayList`. Unlike
   `ArrayList` the `ArrayDeque` does not implement List, only `Deque`

```java
public class ArrayDeque<E> extends AbstractCollection<E>
                           implements Deque<E>, Cloneable, Serializable
```

- NOT synchronized
- stores array of elements T[]
- fast-fail iterator, exception if structure is modified during iteration

2. `LinkedList` - works, but usually not the best idea it also implements the `Deque` interface as well, we have
   already had a brief look at the `LinkedList` with the List interface, since it both implements List and `Deque`.

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

- NOT synchronized
- NOT permitting null elements
- slow random access by index
- permits all elements including null
- doubly linked list expressed by head/tail nodes
- finds/iterates from head/tail based on closeness of index to head/tail
- fast-fail iterator, exception if structure is modified during iteration

### Interface

- add(e) - add new element to end of the queue, does not allow null elements
- remove() - removes element from the front of the queue, throws if empty
- peek() - element at the front of the queue, does not throw if empty
- element() - element at the front of the queue, throws if empty

### Caveats

Just like the `Deque` interface which actually is extending from Queue interface. The queue interface defines methods
that throw under certain conditions and some that do not, and return special types of values either null or false,
instead. Again that depends on which implementation we are using for the Queue

Throwing variants - extra care for both implementations `LinkedList` and `ArrayDeque`

- addLast - might throw based on the underlying implementation does NOT allow NULL elements to be inserted, or
  growing the structure is not possible (`ArrayDeque` does not allow null, `LinkedList` does)
- removeFirst - removes the element from the front of the queue, will throw `NoSuchElementExcetpion`, if the queue
  is empty, both the linked list version and the array list will throw
- getLast - will throw if the queue is empty, or will return the front of the queue if there is at least one
  element, both main implementation will throw.

NON Throwing variants - safe for both `LinkedList` and `ArrayDeque`

- offerLast - adds to the queue as last element but also does throw if the element to be added is invalid - null
- pollFirst - will remove from the head of the stack, does NOT throw instead returns null if the stack is empty
- peekFirst - will either return the head of the stack or null, does NOT throw if the stack is empty

The internal implementation of `ArrayDeque` uses a mix of the throwing and non-throwing variants where possible. For
example it is critical for the push/pop to be stricter while the peek uses a more loose non-throwing variant

If a queue implementation allows storing `null`, then `peek/poll` returning `null` becomes ambiguous. This is why
`ArrayDeque` disallows `null`. Don’t rely on `LinkedList` allowing `null` unless you truly need it (it complicates
emptiness checks for the queue).

## Set

A set is a data structure that stores unique elements, unique depends on the implementation. The set depends on
equals. There are no pair of elements such that the equals method returns true for them. There can be only at most one
null element in the set. Some implementations have restrictions on the elements that they may contain, for example
prohibit null elements or some may limit the allowed types in their implementations.

1. `HashSet` - fast membership no order guarantee -

- NOT synchronized
- backed by a hash table, transient `HashMap`<E,Object> map
- it makes no guarantee as to the iteration order of the set, fail-fast iterator
- offers constant time performance for the basic operations - add/remove/contains/size.

2. `LinkedHashSet` - preserves insertion order

- NOT synchronized
- backed by an instance of `LinkedHashMap`
- hash table and linked list implementation
- no increased cost to keep the insertion order

3. `TreeSet` - sorted navigable queries

- NOT synchronized
- based on red-black tree implementation
- provides guaranteed log(n) access time cost
- backed by a transient `NavigableMap`<E,Object> map;
- elements are ordered using their comparable natural order

### Interface

- add - add specified element to this set if not already present, based on implementation, such as `TreeSet` - `NullPointerExcetpion` or `ClassCastException`, if the object can not be cast comparable if not using the comparator interface.
- remove - removes the specified element from the set if present, based on the implementation `NullPointerExcetpion`
  or `ClassCastException` if the element is to be removed is null or can not be cast to comparable
- contains - returns true if this set contains the specified object/element, based on the implementation
  `NullPointerExcetpion` or `ClassCastException` if the element is to be removed is null or can not be cast to comparable

### Caveats

- HashSet and `LinkedHashSet` - allow at most one null
- TreeSet implementation DOES NOT allow null elements
- TreeSet can drop distinct objects if compare == 0, even when !equals
- Mutating an element after inserting into a tree or hash set changing fields can lose it.
- TreeSet uniqueness uses comparator or comparable result not equals meaning - by compare == 0
- As stated the uniqueness of the hash based sets is based on the on the elements and their hash code value, while the
  uniqueness for the tree set implementations are based on the comparable interface implementation and their natural
  ordering.

## Map

An object that maps keys to values. A map CANNOT contain duplicate keys, but each key map to at most one value. The
map interface provides collection views which allows the map's contents to be viewed as a set of keys collection of
values or set of key-value mappings. The order of a map is defined as the order in which the iterators on the map
collection views return their elements. Some map implementations may provide ordering (`TreeSet`) some will not
(`HashMap`)

### Interface

Note that based on the underlying implementation having null values mapping for keys is possible, however there is no
clear way of indicating if a key exists or not in the map, unless we use methods such as containsKey, otherwise using
the return type of the most functions in map is not reliable way to test if a key exists because it might be mapped to a
null value, and methods in Map do not return optional, they return null for missing key/value mappings as well as null
for keys that might be already mapped to a null value

1. Basic interface

- get(key) or getOrDefault(key, default) - attempts to get a value for a key mapping if such exists in the map,
  if that is not the case,then it returns null in the case of get(...) or it returns the default value in the case
  of getOrDefault(..., ...)

- containsKey(key) - checks if the map contains a get and returns true/false if it does contain it or not, that is
  viable to avoid having null values on get(key)

- put(key, value) - put a new key value mapping into the map, and it returns the previous mapping for that key if any
  actually existed, null can also be returned, in case a mapping did not exist for that key.

- remove(key) - removes the key/value mapping from the map if such mapping already existed the return of this method
  is the value to which the key was associated with before the removal, that also includes null values, the map will not
  contain any mapping for this key after this remove method returns

- putAll(map) - puts all key/value pairs from the given target map, that is basically equivalent of doing
  iterating over the source map and calling the put method on the destination map, that means that keys in the
  source map will override ones in the destination map

- replace(key, value) - unlike the put method this call will only attempt to replace a key/value mapping in the map
  if it already exists, otherwise no-op, unlike put which will actually update the map, returns the old value if
  the key existed and replacement was done, or null otherwise, there is a version of the replace that will replace
  only if the key is mapped to a specific value, conditionally - boolean replace(key, oldValue, newValue)

- putIfAbsent(key, value) - only put the key and value mapping in the map if the existing key does not exist or the
  underlying key is already mapped to null value

2. Advanced interface

- merge(key, value, merger) - the merge function works similarly to the overloaded replace method form above, what
  it does is that a given key is not already mapped to a value in the map, it will associate it with the value (2nd
  argument) otherwise the mapping merger lambda/function will be used to compute the new value based on the old, new
  value, it also returns the final new value associated with the key. NOTE that if this merger lambda returns a
  null, then the key and value pair will be removed from the map

- compute(key, computer) - similar to the merge function but it does try to update an existing value for a key
  mapping, based on the current key and value which are passed to the computer lambda, if this mapping returns
  null then the key and value mapping a is actually removed from the map. The compute method is very near
  identical to the merge but the only difference is that merge takes care of associating the key with a new value if
  the key was previously not in the map or the value it was associated with was null, here in compute we are going to
  receive null as value if a key mapping does not exist

- computeIfAbsent(key, resolver) - the compute function will ensure that a new value is create for a given key if a
  value mapping already exists for that key, if this function returns null or throws no new mapping will be created
  for the given key. What it returns is the new value mapping

- computeIfPresent(key, resolver) - when a key value mapping that is non null exists for the given key then a new value
  is computed based on the resolver lambda that takes in as arguments the existing key and value mapping and computes a
  new value from them, if this resolver returns null the key and value mapping pair is removed from the map

3. View interface

- keySet() - returns the values of the map as a set of values, the underlying implementation of this set is not
  specified, so no guarantees are made if it will be a `TreeSet` or `HashSet`
- entrySet() - returns the key value pairs of the map as a set of map pairs, that method should be preferred for
  iterating over the map instead of using get(key) method, the underlying implementation is not specified just as for the keySet
- values() - returns all only the values of the map for every non null key

The interface for the Map.Entry<K,V> provides means of getting the key, value from the pair, as well as updating
them too, that is the preferred approach as it allows us to update the values as we iterate over the map if required

`Certain implementation such as TreeMap do not allow null keys, while HashMap might allow at most one null key, as far
as values are concerned there are no special restrictions onto those in most implementations`

## Set

That is a collection that contains no duplicate elements or more formally sets contain no pair of elements e1 and e2
such that the equals returns true for them, and at most one null element. Great care must be exercised if mutable
objects are used as set elements. The behavior of a set is not specified if the value of an object is changed in a
manner that affects comparison or natural order while the object is still in the set.

### Interface

- add(E e) - returns true if the element was added and the set was actually changed. That might throw
  `NullPointerExcetpion` if the set implementation does not allow any null elements to be added to the set. Might throw
  `ClassCastException`, if the underlying implementation expects something like `TreeSet` elements to be Comparable if a
  comparator was not added at the moment of creation for the `TreeSet`
- addAll(Collection<> e) - returns true if any new elements were added to the set, the same rules apply as for the add method above

- contains(Object o) - false if the object is absent, this is compared by the equals however, for `TreeSet` we might face
  the same exceptional cases where if the `TreeSet` was not constructed with a comparator in mind, then a comparable object
  is expected here, and if the object is null or does not implement comparable this method could throw
- remove(Object o) true if present and actually removed, same exceptional cases might apply as in contains, for the
  target object, that is purely based on the underlying implementation that the set is.
- removeAll(Collection<> c) - returns true if any elements were actually removed, same rules as for remove from above
  apply, take extra care when dealing with more coarse Set implementations

### Caveats

- as mentioned extra care must be taken when dealing with checking/removing elements based on the implementation that
  might throw exception because certain expectations are not met for the element in question,
- avoid using mutable objects that might affect the natural order of the elements in the set, especially if the elements
  rely on the Comparable interface implementation or a comparator. But generally the equals will also be affected too.

## NavigableSet & SortedSet

Is a sorted set extended with navigation methods reporting closest matches for a given search targets. Methods
lower, floor, ceiling and higher.

### Interface

- higher(E o) - the least element in this set strictly greater than the given element (the greatest element
  immediately bigger than the target element), null if there is no such element
- lower(E o) - the greatest element in this set strictly lower to the given element (i.e the closest element
  still smaller than the target), null if there is no such element
- ceiling(E e) - the least element in this set greater than or equal to the given element, just like higher but equality including
- floor(E e) - the greatest element in this set less than or equal to the target element, just like lower but
  equality included

- descendingSet - reverse order view of the elements contained in this set. That set is backed by this set, so
  changes to the set are reflected in the descending set and vice-versa
- subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive)- returns a view of the portion of this
  set whose elements range from to element, if they are equal the returned set is empty unless include are both true

- headSet(E toElement, boolean inclusive) - returns a view of the portion of this set whose elements are less than
  or equal (if inclusive is true) to a target element
- tailSet(E fromElement, boolean inclusive) - returns a view of this portion of this set whose elements are greater
  than or equal (if inclusive is true) to the target element.

### Caveats

- `lower(x)` greatest `< x`
- `floor(x)` greatest `<= x`
- `ceiling(x)` smallest `>= x`
- `higher(x)` smallest `> x`
- `first()`/`last()` throw if empty; `pollFirst/pollLast` return `null` when empty.
- The range views are backed structural changes can trip the fail-fast behavior
- The subset method will throw exception if the fromKey is larger than the toKey,

## NavigableMap & SortedMap

Is a sorted, usually tree map supports nearest key and range queries, the interface is pretty similar to the one of
the navigable set, however it deals with the keys of the map instead of the plain elements inside the set just like
`NavigableSet` does. In a navigable map one can not navigate through the values just the keys of the map, and for
reference the `TreeSet` actually is backed by a navigable map implementation.

### Interface

- ceilingKey - just like the ceiling method of the navigable set, the ceiling method finds the closest key that is
  higher or bigger by natural order then the target, the ceilingEntry method does the same but returns the full map
  entry meaning the value and the key together
- floorKey - just like the floor method of the navigable set, the floor method here finds the closest key that is
  smaller or lower by natural order than the target, the floorEntry method does the same but returns the full map
  entry meaning the value and the key together
- subMap - just like the subSet method it is meant to return a view of the source which is a sub view of the
  original, the from/to targets that can be passed to this method are again for keys, it is important to note that the
  from must always be less than or equal to the to target, otherwise the method will throw.
- tailMap - just like the tailSet method, but returns a sub view of the map where the sub-view contains all the
  elements are greater than or equal to the target to the end of the map inclusive, the tailMap is overloaded with
  a second boolean argument that tells if the starting target is inclusive, by default that is true
- headMap - just like the headSet method but returns a sub view of the map where the sub-view contains all the
  elements from the beginning up until but not including the target element, the headMap method is overloaded with a
  second argument which tells if the target should be inclusive, false by default
- descendingMap - is a map view that represents the map key elements sorted in the reverse of their natural order,
  the descendingKeySet is just a view of the keys represented as `NavigableSet`, again sorted in reverse natural order.

### Caveats

- `lower(x)` greatest `< x`
- `floor(x)` greatest `<= x`
- `ceiling(x)` smallest `>= x`
- `higher(x)` smallest `> x`
- `first()`/`last()` throw if empty; `pollFirst/pollLast` return `null` when empty.
- The range views are backed structural changes can trip the fail-fast behavior
- The submap method will throw exception if the fromKey is larger than the toKey,

##
