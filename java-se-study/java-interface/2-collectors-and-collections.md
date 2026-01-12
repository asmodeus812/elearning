## Stream

A sequence of elements supporting sequential and parallel aggregate operations. In addition to having Stream for
object references Streams also exist for primitives, but only a fe - IntStream, DoubleStream, LongStream. Stream
pipelines execute either sequentially or in parallel.

### Interface

1. Intermediate

- filter(predicate) - return true if the element should remain in the stream, false otherwise
- peek(consumer) - during the pipeline inspect an element from the stream
- distinct() - using equal
- sorted({comparator}) - sort the elements based on comparator or their natural order using the comparable
  interface implementation
- map(function) - return a new type element for a given element from the stream
- flatMap(stream) - map the element from the stream to a type that returns Stream<T>
- mapToInt/Double/Long(mapper) - allows the stream of reference objects to be mapped to a stream containing the
  specified primitive type
- flatMapToInt/Double/Long(mapper) - exactly the same as mapToInt/Long/Double, but for mapping elements that are
  themselves collections.
- mapToObj - only present in the primitive types of streams and does the reverse of the mapToXXX primitive stream
  converts a primitive stream back to a reference based object stream

2. Terminating

- count, min(cmp), max(cmp) - count the number of elements in the stream or find the max/min element in the stream,
- collect/reduce - common entry point for generic reduction operations, unlike other pre-defined reduction
  operations such as min/max/count these allow the consumer to specify the exact reduction operation to be performed onto the
  stream of elements
- anyMatch, allMatch, noneMatch, findFirst, findAny - matching methods match* versions return boolean, find*
  versions return Optional value, they all take as an argument a predicate to test for the condition.
- forEach, forEachOrdered - the ordered version ensures that the encounter order of the stream is adhered to, if the
  stream has been defined with encounter order, regular forEach does not guarantee ordered iteration.
  note that min/max return Optional if the stream is empty that optional will be empty.

3. Producers

- static iterate(seed, next) - similar to a lisp like cons, where we provide the left hand size head of the list, and the
  right hand size the tail. The head here is the seed that is the first element of the list, and next is a mapping
  that represents the rest of the list. Iterate has overloaded version that takes a 3 arguments - iterate(see,
  hasNext, next), this is to avoid the dreaded issue with infinite looping iterate stream methods
- static generate(supplier) - generates an infinite stream based on the supplier that is in charge of providing elements
  for the stream, combine it with limit to avoid locking up or hanging up the program.
- static builder() - creates a one-shot stream builder, backed by a `SpineBuffer`, an array like structure that is very
  efficient at taking in new elements without incurring extra costs for insertion or expanding its capacity.
- static empty() - create an empty stream, without any elements, useful for side use cases where a stream is always
  returned and we would like to express some sort of empty state, similar to Optional.empty
- static of(...) - create a stream of an unbounded number of values, similar to builder but if we already have the
  values present in an array or we have a set of compile time known fixed number of elements.
- static concat(s1, s2) - concatenate two streams into one single stream, the underlying implementation tries to be
  efficient by creating a combined iterator from the two streams.

### Caveats

- DoubleStream/LongStream/IntStream.average() - returns optional of double that is because if the stream is empty
  then the optional will also be empty. Also it is double because it finds the arithmetic mean of all elements of this
  stream which is not
  guaranteed to be an integral value even if the stream contains only integral values.
- map
- min/max - methods return optional that is because the stream might be empty therefore there is no way to determine
  which is the min/max element, returning null is not a good idea
- allMatch, noneMatch - on empty stream will return true, and allMatch on empty stream will return false
- distinct and sorted - distinct uses equals to compare elements, sorted uses either a custom comparator, or if none
  is provided tries to use the natural comparator i.e cast the element to Comparable, so that might produce a
  ClassCastException based on the type of elements that are inside the stream at the time or calling sorted
- streams can not be re-used once a terminal operation is called on a stream, the stream is closed any any other
  intermediate or terminal operation on it ill end up with an exception - `IllegalStateException`
- streams form I/O are AutoCloseable, meaning that all we need to do is put them into a try-with-resources block,
  they will also close upon terminating operation execution as well.
- parallel streams are mostly useful when we deal with huge data sets or data sets that are associative in nature
  where we can easily split the work and combine it later without worrying about the order of elements in the stream.
- iterate(...) method might loop indefinitely if we use the variant that only takes 2 arguments, combine it with limit to avoid hanging applications.
- generate(...) will lock the program if there is no limit applied to the stream as generate has no way to be naturally
  stopped unlike iterate which can take a hasNext predicate in its overloaded version to do exactly that. Combine
  generate with limit
- prefer the stream builder instead when generating new elements that will end up going through a stream pipeline,
  instead of creating an intermediate Collection instance on which you will call .stream() anyway.

## Collector

The collector represent a mutable reduction operation, that accumulates input elements into a mutable result
container optionally transforming the accumulated result into a final representation. Reduction operation, can be
performed either sequentially or in parallel

- concatenating
- summary - sum, min, max, average
- pivot table summaries - max value by a <rule>

```java
public interface Collector<T, A, R> {}
```

### Interface

- supplier - creation of a new result container
- accumulator - incorporating a new data element
- combiner - combine two results containers into one
- finisher - perform an optional final transform on the container

T - the type of input elements to the reduction operation
A - the mutable accumulation type of the reduction operation (hidden implementation detail)
R - the result type of the reduction operation

`Do not build mutable results with reduce, instead use the Collectors implementation that provides means of actually
reducing a collection or a sequence of elements into any other collection or sequence`

### Caveats

- collector can be concurrent, unordered
- must ensure that both sequential and parallel produce equivalent results.
- collector requires associativity for the cases where the combination must be performed in parallel, meaning that
  we need to ensure that even joining parallel sub-collector results is an associative operation.

## Collectors

Implementation of the collector interface that implement various useful reduction operation such as accumulating
elements into collection summarizing elements according to various criteria and more.

## Interface

- toList() - returns a collector that accumulates the input elements into a new list, there are no guarantees on the
  actual list implementation that is going to be used (actually uses ArrayList, safer faster alternative to LinkedList, which is generally used for specific needs)
- toSet() - returns collector that accumulates the input elements into a new set, there are no guarantees on the
  type of mutability serializability or thread safety of the set that is returned (actually uses HashSet, safer
  alternative compared to TreeSet, due to comparable and comparator requirements)

- toMap() - has many overloads, from the most basic ones that uses a basic HashMap for the underlying
  implementation. The toMap method accepts key/value mapper functions, do not return null for either one of those as
  you will get `NullPointerExcetpion`, for its first arguments the overloaded versions of this have a mergeFunction,
  if no mergeFunction is provided it will throw on duplicate keys - `IllegalStateException`, that needs to resolve how
  duplicate keys are handled and which one is taken. The other argument that it can take is a map factory, that will
  specify which map instance to use it is a supplier.
- toCollection(supplier) - allows one to provide a supplier that accepts a lambda that creates an instance returning
  a Collection.
- toUnmodifiableXXX - the methods mentioned above have variants that produces unmodifiable versions for these
  structures such as - toUnmodifiableList, toUnmodifiableSet, toUnmodifiableMap, toUnmodifiableMap etc.

- joining() - reduces the given collection into a sequence of string, the caveat here is that the elements of the
  collection have to be a CharSequence, otherwise we have to map or convert them first before feeding into the joining
  reducer. The joining method also is overloaded with delimiter, prefix suffix arguments.
- groupingBy() - produces a map structure where the keys are representative of groups and the values are buckets or
  lists that contain all elements from the source collection that map to this group. The method takes at most 3
  arguments and they are pretty straight forward, the first one is just the classifier that will decide which element
  which go into which bucket based on some condition. The 2 other arguments are suppliers, one is for the underlying
  Map instance - HashMap::new, and the second one is for the bucket instances themselves, like Collector.toSet()
  or Collector.toList() etc. That allows the buckets to be sorted/ordered navigable etc.
- partitioningBy() - just like the groupingBy, partition is a type of grouping that produces only two keys in the
  final result map - true|false, even if the source collection is empty, it will have these two keys. The
  partitioningBy puts all elements either in the bucket 'true' where elements that match the predicate return true,
  or false otherwise. Takes 2 arguments, works just like groupingBy with the only exception that the classifier for
  partitioning must return boolean not just any value, there is no collection supplier here, just a downstream to
  provide the collection that will contain the bucket of partitioned items

- mapping(mapper, collector) - downstream helper that helps transform before the final collection, the idea is that all methods that
  actually take downstream collection, such as groupingBy, or partitioningBy, can be passed a mapping function, that
  will transform the elements that go into those downstream collections, i.e instead of putting the actual element in,
  it is mapped to a new value that goes into those buckets. It can be also chained like so - mapping(mapper, mapping(mapper, ...))
- filtering(predicate, downstream) - just like the map downstream but instead of changing the value it actually
  decides if the value should at all end up in the final downstream collection
- flatMapping() - just like the mapping one but instead maps the values from a collections to their actual elements,
  can also be chained flatMapping(flatMapper, mapping(elementMapper, Collections.toSet()))
- collectingAndThen(downstream, finisher) - post process the result, right before the collection for the downstream
  collection is created, the finisher lambda operates on the final collection created by the downstream

- counting() - collector reducer that counts the elements in the source collection, each element amounts to a
  quantity of 1, therefore this is equivalent to actually doing collection.size()
- summingInt(mapper) - collector reducer that produces the integral sum of all elements by applying the mapping
  function to each element and that mapping function must produce an integral value for each element, methods called
  summingDouble and summingLong also exist and work in a similar manner
- averagingInt(mapper) - collector reducer that produces the arithmetic mean of an integer-valued function or also
  called a mapper applied to the input element, similar methods averagingDouble and averagingLong exist as well and
  they work in a similar manner

- minBy(comparator) - based on the applied comparator on each element in the source collection returns an optional
  value that represents the minimum element based on the comparator, empty optional of the collection is empty
- maxBy(comparator)-based on the applied comparator on each element in the source collection returns an optional
  value that represents the maximum element based on the comparator, empty optional of the collection is empty

## Caveats

- Stream.toList - unmodifiable, only in new java versions
- toMap - throws on duplicate keys, use key-merge function
- toList - a mutable modifiable list implementation by default
- toSet - a mutable modifiable set implementation by default
- toCollection - provide your own implementation of the collection, robustness
- min/max return an optional that might actually be empty, if the collection is empty
- joining - requires `CharSequence` elements, `NullPointerExcetpion` thrown if any element is null
- TreeMap/TreeSet - when used as the downstream instance, comparator must handle null values
- groupingBy - produces empty map if the source collection is empty, no key/value pairs inside the grouping map result
- partitioningBy - produces map with two keys true|false with empty buckets even if the source collection is empty

## Comparable

The natural order comparison interface for objects in java, that is the primary interface that all objects that
desire or can be compared need to implement, it is said to be consistent with equals if and only if e1.compare(e2)
== 0 has the same boolean value as the e1.equals(e2), for every e1 and e2 in the set of natural order of elements.

## Interface

- compareTo(T o) - the only interface method, compares two objects with the specified object for order, negative
  integer, zero or positive integer are returned if - the object is less than, equal or greater than the specified
  object respectively

`Most importantly take extra care to ensure that equals and compareTo for objects match, when equality is concerned,
otherwise great deal of issues can arise in cases where the equals between two objects say one thing and the
compareTo returns something else than 0, implying they are equal in`

## Caveats

- collections like TreeSet/TreeMap use comparable or comparator and even if equals between two objects say that they
  are equal, if the comparable or comparator interface implementation say otherwise, you might get surprised.
- do not implement compareTo by using just a raw subtraction use the Integer.compareTo or Long.compareTo methods
- inverted sign symmetry meaning that sign(x.compareTo(y)) == -sign(y.compareTo(x))
- transitivity - if x > y and y > z, then x > z

## Comparator

External ordering strategy complementing the natural ordering of the Comparable interface. The ordering imposed by a
comparator on a set of elements is said to be consistent wit equals if and only if e1.compare(e2) == 0 has the same
boolean value as the e1.equals(e2), for every e1 and e2 in the set of natural order of elements.

`Extra care should be taken when using a comparator capable of imposing an order inconsistent with equals to order a
sorted set or sorted map, with explicit comparator`

### Interface

- comapre(e1, e2) - compare two elements and return an integer value denoting which one is the smaller, bigger or if
  they are equal. If e1 is smaller then a negative value is returned, if e1 is bigger then a positive value is
  returned, if e1 equals exactly e2 then zero is returned
- thenComparing(otherComparator) - if the result of the comparator is not zero chain return the result of comparing
  the same elements with the other comparator passed to this method as first argument
- reversed() - returns an instance of this comparator but in reverse order, in other words a new comparator that
  calls the original one with the order of arguments reversed, thus reversing the order itself - (a, b) ->
  oldComparator.compare(b, a)

- static comparing(extractor, \[comparator\]) - the main comparator builder, that constructs a comparator for a
  given key from an object, or the object itself by using the Function.identity(), the key extractor is a lambda
  function, the second argument is optional (overloaded) and it can take the comparator lambda for the key, e.g. -
  Comparator.comparing(String::length, Integer::compare), use the length of the string as a key for comparison, and
  compare by it but strictly and explicitly using Integer::compare (or can provide a custom one)
- static comparingXXX(extractor) - the primitive version that where XXX stands for methods like comparingLong,
  comparingInt, comparingDouble, etc, these use the default comparators for the wrapper types such as Integer.compare,
  Long.compare, Double.compare etc.
- static naturalOrder() - natural order comparator is such that the elements that are passed to it are compared
  based on their compareTo method that implies that those elements have to be implementing the Comparable interface,
  otherwise they can not be considered valid for natural ordering, and `ClassCastException` will arise
- static reverseOrder() - the reversed of the natural order comparator provided by the naturalOrder method, effectively
  equivalent to doing the following - Comparator.naturalOrder().reversed()
- static comparing(extractor) - returns a comparator instance that compares a specific key or a property based on
  its natural order (compareTo from the Comparable interface) from an element, e.g. comparing(String::length) or
  comparing(User::getName) etc.
- static nullsFirst(comparator) - returns a null friendly comparator that considers null to be less than non-null
  thus providing a natural order for null elements as well, two null elements are considered equal. Order is such that
  null elements come before non-null elements in the natural order. The comparator argument is used for non-null
  elements natural order comparison
- static nullsLast(comparator) - just like nullsFirst but the null elements are sorted at the end of the natural
  ordering when non null and null elements are both present

### Caveats

- equals and compareTo and compare - should be consistent for natural ordering between two elements e1 and e2,
  otherwise mixing them up can cause all sorts of issues, use compare for one shot small ordering adjustments for
  object's properties but avoid providing confusing full comparator implementations for naturally ordered objects.
- nullsFirst/nullsLast - prefer using this comparator to wrap other comparators where you expect collections to
  contain null elements
- several important structures in the standard library use the comparator interface such a s- TreeSet, TreeMap,
  PriorityQueue, Collections.min/max, Collections.sort, Collections.binarySearch, and others.
- binarySearch - by default the binary search method is overloaded with multiple arguments one of which is a custom
  comparator, however if none is passed then the binary search assumes ascending order for the underlying collection,
  when traversing and looking for elements less than (to the left) or greater (to the right) that can trip people if
  the collection is ordered in descending order, use reversed comparator to handle this use-case.

## Functional

Essential functional interfaces from the package java.util.function, such that are meant to work with single,
multiple arguments and primitive versions of the same. Provide building blocks for lambda argument types allowing
functions to be assigned and passed around just as any other variables

### Interface

- a functional interface has exactly one single abstract non final public method, (SAM) lambda references target
  call these with the appropriate arguments.
- many have default combiners and chaining methods - andThen, compose, and, or, negate etc.
- prefer primitive specializations where applicable avoid auto-boxing wrapper types.

### Caveats

- java will wrap primitive type arguments where possible avoid this in hot paths, use the primitive versions of the
  functional lambda interfaces
- none of these declare any checked exceptions which is expected, handle exceptions with care, on your own wrap the body
  in try / catch if exceptions are expected and need to be handled
- the lambda method names for the primitive versions of the interfaces in the java.util.function package follow the
  `yyyAsXXX`, where `yyy` is the name of the method apply,get,test and `XXX` is the return type if the return type is
  primitive those can be Int, Double, Long etc. For example applyAsInt, applyAsLong, applyAsDouble.

### Predicate (.test)

| Interface                                          | Method                                                    | Meaning                     | Common combinators / helpers        |
| -------------------------------------------------- | --------------------------------------------------------- | --------------------------- | ----------------------------------- |
| `Predicate<T>`                                     | `boolean test(T t)`                                       | **Test** `T`.               | `and`, `or`, `negate`, `isEqual(x)` |
| `BiPredicate<T,U>`                                 | `boolean test(T t, U u)`                                  | Test a pair.                | `and`, `or`, `negate`               |
| ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ |
| `IntPredicate`, `LongPredicate`, `DoublePredicate` | `boolean test(int/long/double v)` + `and`, `or`, `negate` |                             |                                     |

### Supplier (.get)

| Interface                                       | Method                       | Meaning                       | Common combinators / helpers |
| ----------------------------------------------- | ---------------------------- | ----------------------------- | ---------------------------- |
| `Supplier<T>`                                   | `T get()`                    | **Produce** a `T` (no input). | —                            |
| ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ |
| `BooleanSupplier`                               | `boolean getAsBoolean()`     |                               |                              |
| `IntSupplier`, `LongSupplier`, `DoubleSupplier` | `getAsInt/Long/Double()`     |                               |                              |

### Consumer (.accept)

| Interface                                       | Method                       | Meaning                     | Common combinators / helpers |
| ----------------------------------------------- | ---------------------------- | --------------------------- | ---------------------------- |
| `Consumer<T>`                                   | `void accept(T t)`           | Use `T` (side effects).     | `andThen`                    |
| `BiConsumer<T,U>`                               | `void accept(T t, U u)`      | Side-effect on two args.    | `andThen`                    |
| ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ |
| `IntConsumer`, `LongConsumer`, `DoubleConsumer` | `void accept(…)` + `andThen` |                             |                              |

### Function (.apply)

| Interface                                                         | Method                          | Meaning                     | Common combinators / helpers       |
| ----------------------------------------------------------------- | ------------------------------- | --------------------------- | ---------------------------------- |
| `Function<T,R>`                                                   | `R apply(T t)`                  | **Map** `T → R`.            | `andThen`, `compose`, `identity()` |
| `BiFunction<T,U,R>`                                               | `R apply(T t, U u)`             | **Combine** two into one.   | `andThen`                          |
| `UnaryOperator<T>` (extends `Function<T,T>`)                      | `T apply(T t)`                  | `T → T`                     | `andThen`, `compose`, `identity()` |
| `BinaryOperator<T>` (extends `BiFunction<T,T,T>`)                 | `T apply(T a, T b)`             | Fold `T×T → T`.             | `minBy(cmp)`, `maxBy(cmp)`         |
| ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ |
| `IntFunction<R>` / `LongFunction<R>` / `DoubleFunction<R>`        | `R apply(int/long/double v)`    | primitive → object          |                                    |
| `IntBinaryOperator`, `LongBinaryOperator`, `DoubleBinaryOperator` | `applyAsInt/Long/Double(a,b)`   | fold two primitives         |                                    |
| `IntUnaryOperator`, `LongUnaryOperator`, `DoubleUnaryOperator`    | `applyAsInt/Long/Double(x)`     | have `andThen` & `compose`  |                                    |
| ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ |
| `ToIntFunction<T>` / `ToLongFunction<T>` / `ToDoubleFunction<T>`  | `int/long/double applyAs…(T t)` | object → primitive          |                                    |
| `IntToLongFunction`, `IntToDoubleFunction`                        | `applyAs…(int v)`               | int → long/double           |                                    |
| `LongToIntFunction`, `LongToDoubleFunction`                       | `applyAs…(long v)`              | long → int/double           |                                    |
| `DoubleToIntFunction`, `DoubleToLongFunction`                     | `applyAs…(double v)`            | double → int/long           |                                    |
