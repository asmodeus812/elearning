1. What is the output of the following?

```java
Stream<String> stream = Stream.iterate("", (s) -> s + "1");
System.out.println(stream.limit(2).map(x -> x + "2"));
```

This example is all about recognizing that the stream is never terminated. The stream is never finalized or closed
with a terminating operation, and is left with two intermediate operation calls only limit and map. Therefore the
print statement will simply just print out the reference of the stream object that is returned by the final
intermediate operation - map

Now assume that was not the case, lets imagine that the statement was finalized with .collect(Collectors.joining()),
In that case what is the result going to be. First lets tackle what the stream is going to look like from the
iterate perspective alone

We have Stream.iterate starting with the emtpy string identity "", and on each iteration we concatenate "1" to the
previous result. The VERY FIRST element in the stream will be the empty string "", and the second one will be "1"
the third one will be "11" the fourth one will be "111" and so on. Now the trick here is to realize that the empty
string the identity for the iterate method is actually also part of the stream elements, so when the limit is set to
2, then we will have only two elements in the stream - "" and "1".

Now let us move to the map function, now that we know that we have two elements, we map each element by appending
"2" to it. Remember we have two elements in the stream one is the empty string- "", so the map will produce two
elements like such - the first element "" + "2" and the second element "1" + "2" or in other words - "2" and "12".

So if we had a terminating operation, the final result would have been 212 for a limit of 2 items in the stream. If
the limit was set to 3 the result would have been - 212112.

A. 12112
B. 212
C. 212112
D. `java.util.stream.ReferencePipeline$3@4517d9a3`
E. The code does not compile.
F. An exception is thrown.
G. The code hangs.

2. What is the output of the following?

```java
Predicate<? super String> predicate = s -> s.startsWith("g");
Stream<String> stream1 = Stream.generate(() -> "growl! ");
Stream<String> stream2 = Stream.generate(() -> "growl! ");
boolean b1 = stream1.anyMatch(predicate);
boolean b2 = stream2.allMatch(predicate);
System.out.println(b1 + " " + b2);
```

This example might have a few gotchas, first the any/all match could lead somebody to believe that the return result
is Optional, which is not true, optional is the return result of the find first/any version of these methods. The
second issue might be the way the predicate is defined but that is still correct, it says that the element that the
predicate is going to be tested with is lower bounded by String. It would have been a compile error if the predicate
was defined as Predicate<? extends String>, where the upper bound is string but it can be any other child of the
string class.

Finally where the issue is is in the generate method. The generate method here is a simple supplier that provides
the same exact element indefinitely, and there are no .limit calls on the stream pipeline, effectively causing
infinite stream elements generation, therefore the code will hang

A. true false
B. true true
C. java.util.stream.ReferencePipeline$3@4517d9a3
D. The code does not compile.
E. An exception is thrown.
F. `The code hangs.`

4. Which are true statements about terminal operations in a stream? (Choose all that apply.)

The terminal operations are required for us to obtain a result from the stream pipeline as they are the only ones
that provide a result, the intermediate operations all return a Stream instance. And of course only one terminal
operation can be called on a stream, after calling it the stream becomes invalid or closed

A. `At most one terminal operation can exist in a stream pipeline.`
B. `Terminal operations are a required part of the stream pipeline in order to get a result.`
C. Terminal operations have Stream as the return type.
D. The referenced Stream may be used after the calling a terminal operation.
E. The peek() method is an example of a terminal operation.

5. Which terminal operations on the Stream class are reductions? (Choose all that apply.)

Reduction operations as such that they take a sequence of input elements and they combine them into a single summary
result. Count, sum, average, summaryStatistics are examples of such functions. There are generic ones such as reduce
as well that are basically generic reduction operation. Collect is a mutable reduction operation. Why is it called
mutable in the first place ? Well the standard reduce operation combines the data from the stream into a new result,
often creating an entirely new object for the result, while collect does NOT, it often returns the same exact object
/ reference that was originally stored into the collection. That way it is much easier and faster to operate on
streams.

Why are operations like findAny/allMatch etc NOT considered reduction operations, but are short-circuit terminating
search operation, because a reduction operations are such that they are terminal operation combine the entirety of
the stream, meaning they traverse through the collection and collect an output producing an aggregate result.

A. `collect()`
B. `count()`
C. findFirst()
D. map()
E. peek()
F. `sum()`

6. Which of the following can fill in the blank so that the code prints out false? (Choose all
   that apply.)

```java
Stream<String> s = Stream.generate(() -> "meow");
boolean match = s.________________(String::isEmpty);
System.out.println(match);
```

None, the reason is because this method uses generate without providing any means of stopping the generation,
therefore the code will hang, and none of the terminating operations below can prevent this. The terminating
operations require the stream to be finalized, meaning that the intermediate operations should have beforehand
already 'generated' the stream, added filter, limit, map etc. Then the terminating operations operate on a known
data set. So the implementation will hang

A. allMatch
B. anyMatch
C. findAny
D. findFirst
E. noneMatch
F. `None of the above`

7. We have a method that returns a sorted list without changing the original. Which of the following can replace the
   method implementation to do the same with streams?

```java
private static List<String> sort(List<String> list) {
    List<String> copy = new ArrayList<>(list);
    Collections.sort(copy, (a, b) -> b.compareTo(a));
    return copy;
}
```

This example is purely about knowing the stream API, and in this case we know that stream API relies on intermediate
and terminal operations, so even by simply looking at the given options, a lot of them do not express a valid
intermediate + terminating operation

A. compare + collect - compare expressed as intermediate operation
B. compare + sort - sort expressed as terminating reduction operation
C. compareTo + collect - incorrect and similar/same as option A
D. compareTo + sort - incorrect and similar/same as option B
E. sorted + collect - very close to the actual true answer, but missing collect arguments

A. return list.stream()
.compare((a, b) -> b.compareTo(a))
.collect(Collectors.toList());
B. return list.stream()
.compare((a, b) -> b.compareTo(a))
.sort();
C. return list.stream()
.compareTo((a, b) -> b.compareTo(a))
.collect(Collectors.toList());
D. return list.stream()
.compareTo((a, b) -> b.compareTo(a))
.sort();
E. return list.stream()
.sorted((a, b) -> b.compareTo(a))
.collect();
F. `return list.stream()
.sorted((a, b) -> b.compareTo(a))
.collect(Collectors.toList());`

8. Which of the following are true given the declaration IntStream is = IntStream.empty()? (Choose all that apply.)

The empty call here will produce an empty stream of primitive integers. This question affects and touches on the API
contract of the IntStream and in particular the fact that findAny or actually any stream API method that would usually
return Optional here returns an OptionalXXX, in this case OptionalInt, because we work with integer stream primitive.

The part here that is more peculiar is the average method. Average returns optional, which might be quite unexpected,
but the reason for that is to allow handling empty streams, because if the stream is empty the average is not defined,
it is certainly not zero, or +/- infinity.

A. is.average() returns the type int.
B. `is.average() returns the type OptionalInt.`
C. is.findAny() returns the type int.
D. `is.findAny() returns the type OptionalInt.`
E. `is.sum() returns the type int.`
F. is.sum() returns the type OptionalInt.

9. Which of the following can we add after line 5, on line 6, for the code to run without error and NOT produce any output? (Choose all that apply.)

```java
4: LongStream ls = LongStream.of(1, 2, 3);
5: OptionalLong opt = ls.map(n -> n * 10).filter(n -> n < 5).findFirst();
6: _________________________________
```

Again we touch on the API convention, here the important part is to realize that the API of OptioanlXXX is a bit
different than the regular Optional interface, the Primitive versions for Optional do not have a get() method they have
getAsXXX method, that rules is something that the java standard library uses across other areas where primitives are
involved as well.

In the following options, we have really only two that are valid, the option B and D. Option A, is not because as
already mentioned there is no get() method on the OptionalLong and C is not valid because it incorrectly calls the print
method as a lambda reference, using dot to reference the method instead of double :: syntax

A. if (opt.isPresent()) System.out.println(opt.get());
B. `if (opt.isPresent()) System.out.println(opt.getAsLong());`
C. opt.ifPresent(System.out.println)
D. `opt.ifPresent(System.out::println)`
E. None of these; the code does not compile.
F. None of these; line 5 throws an exception at runtime.

10. Select from the following statements and indicate the order in which they would appear to output 10 lines:

```java
Stream.generate(() -> "1")
L: .filter(x -> x.length() > 1)
M: .forEach(System.out::println)
N: .limit(10)
O: .peek(System.out::println)
```

This example similarly to other ones that use generate, will hang unless we shuffle and/or remove some of the
intermediate or terminating operations. First we the generate here will generate a stream sequence of 1s indefinitely,
that has to be bound by something that is why we need to have the very first call to intermediate operation be limit,
after the limit we can then just use forEach and that is all.

Now the rest of the intermediate operations are not needed, filter in this case will filter out the entire stream
because it requires length > 0, and our 1s are of length 1, not greater than 1. The peek is not needed because it will
duplicate the lines, and effectively print 10 more lines, if we leave the peek and forEach, and we need forEach because
it is the only terminating operation in there.

A. L, N
B. L, N, O
C. L, N, M
D. L, N, M, O
E. L, O, M
F. `N, M`
G. N, O

11. What changes need to be made for this code to print the string 12345? (Choose all that apply.)

```java
Stream.iterate(1, x -> x++).limit(5).map(x -> x).collect(Collectors.joining());
```

In this example we can see multiple small issues that we need to tackle to really understand what is going on. First
the map function.

The first issue is with, it will iterate starting from 1 being the first entry into the stream, but the postfix x++
operation will not increment the value and we will end up with the following stream - 11111 (5 ones), that is because in
order to correctly iterate over we need a prefix increment change it to ++x that way it will first increment then return
the next value to put into the stream.

The second issue is the map, which returns the identity of x that is plain integer. The problem here is that the
collectors joining method requires the arguments passed in to it to be of type CharSequence which Integer or int are
not. Even if auto-boxing takes place that will still not work, one way is to box and use toString the other is to just
do "" + x, which will effectively cause the compiler to do the same.

The final issue is that even though we have a terminating operation like collect it will collect the stream content into
a string yes, but that string is in no way going to be printed anywhere, we have to wrap this entire expression into
system.out.print to actually print anything to the console.

A. Change Collectors.joining() to Collectors.joining("").
B. `Change map(x -> x) to map(x -> "" + x) .`
C. `Change x -> x++ to x -> ++x.`
D. Add forEach(System.out::print) after the call to collect().
E. `Wrap the entire line in a System.out.print statement.`
F. None of the above. The code already prints 12345.

12. Which functional interfaces complete the following code? (Choose all that apply.)

```java
____________ x = String::new;
____________ y = (a, b) -> System.out.println();
____________ z = a -> a + a;
```

- The first one, the String constructor - String() {}, therefore Supplier\<String\>
- The second one, lambda with two inputs and no return value, is a Consumer with two arguments - therefore BinaryConsumer\<String, String\>
- The third one, lambda with one input argument and one return value, because the one argument and return type are both String - therefore UnaryOperator\<String\>

A. BiConsumer\<String, String\>
B. BiFunction\<String, String\>
C. `BinaryConsumer\<String, String\>`
D. BinaryFunction\<String, String\>
E. Consumer\<String\>
F. `Supplier\<String\>`
G. UnaryOperator\<String\>
H. UnaryOperator\<String, String\>

13. Which of the following is true?

```java
List<Integer> l1 = Arrays.asList(1, 2, 3);
List<Integer> l2 = Arrays.asList(4, 5, 6);
List<Integer> l3 = Arrays.asList();
Stream.of(l1, l2, l3).map(x -> x + 1).flatMap(x -> x.stream()).forEach(System.out::print);
```

This example will not compile and the reason is subtle but quite important, first we have 3 List which are converted
to stream, the resulting stream is a stream with 3 elements each of which is of type List. The stream intermediate
operation that immediately follows the creation of this stream is .map, where as it should have bee flatMap first,
then map and then the print statement.

If it were stream.flatMap.map.forEach then the final result would have been 2,3,4,5,6,7. But that is not the case
the code will not compile because the type of 'x' in the map intermediate call is of type List, and it will try to
add one to a List, which is not allowed.

A. The code compiles and prints 123456.
B. The code compiles and prints 234567.
C. The code compiles but does not print anything.
D. The code compiles but prints stream references.
E. The code runs infinitely.
F. `The code does not compile.`
G. The code throws an exception

14. Which of the following is true?

```java
4. Stream<Integer> s = Stream.of(1);
5. IntStream is = s.mapToInt(x -> x);
6. DoubleStream ds = s.mapToDouble(x -> x);
7. Stream<Integer> s2 = ds.mapToInt(x -> x);
8. s2.forEach(System.out::print);
```

This one example might trip somebody and lead them to believe that the primitive versions of the streams do not have the same mapToXXX that Stream does. That is not the case all primitive versions of the streams also have the same conversion to the corresponding primitive variants as well, allowing us to convert from a primitive stream of one type to another primitive stream of another type. Like DoubleStream to IntStream

The issue here is subtle, the problem is what the mapToInt requires, in DoubleStream, the lambda for this method
requires the user to return a primitive of int, but it returns double, there is no auto-casting allowed by the
compiler between primitive versions of integral and decimal types. Therefore that line will fail with compile time
error due to type mismatch. On top of that the other issue here is the left hand side which is of type
Stream<Integer> that is wrong because mapToInt converts the DoubleStream to IntStream, if we wanted to convert it to
a Stream<Integer> then we should have used mapToObj instead

To fix this we can explicitly cast this to (int) x, then change the left hand side type to be IntStream s2. Then we
will need to take care of the runtime issue that this code has see below.

Now even if we fix this this code has more errors, but runtime ones. See, we call mapToXXX on the stream 's' twice.
One time we mapToInt, the other we use the same stream to mapToDouble that is not possible the mapToXXX immediately
produces a new Stream consuming the old one in the process, therefore even if the program compiled the statement
s.mapToDouble will throw at runtime.

If we remove the statement at line '5', and fix the issues on line '7', the code will compile and run and print '1',
but until then the actual error is a compile time one.

A. Line 4 does not compile.
B. Line 5 does not compile.
C. Line 6 does not compile.
D. `Line 7 does not compile.`
E. Line 8 does not compile.
F. The code throws an exception.
G. The code compiles and prints 1.

15. The partitioningBy() collector creates a Map<Boolean, List<String>> when passed to collect() by default. When specific parameters are passed to partitioningBy(), which return types can be created? (Choose all that apply.)

A. Map<boolean, List<String>>
B. Map<Boolean, Map<String>>
C. Map<Long, TreeSet<String>>
D. `Map<Boolean, List<String>>`
E. `Map<Boolean, Set<String>>`
F. None of the above

16. What is the output of the following?

```java
Stream<String> s = Stream.empty();
Stream<String> s2 = Stream.empty();
Map<Boolean, List<String>> p = s.collect(Collectors.partitioningBy(b -> b.startsWith("c")));
Map<Boolean, List<String>> g = s2.collect(Collectors.groupingBy(b -> b.startsWith("c")));
System.out.println(p + " " + g);
```

This problem example affects the API and the general usage of the collectors, the partitioningBy by and groupingBy
work on different principles, both aim at providing means of categorizing and aggregating data from a collection,
the partitioningBy produces a map with only two keys, which are boolean [true] and [false] that is it, it then
collects all values that do not match the predicate go into the [false] key values bucket, and all that match the
predicate go into the [true] key values bucket.

The groupingBy does something similar but different, the keys here are determined by a classifier lambda. That
classifier produces keys from the values inside the collection, for every unique key an entry in the resulting map
is created.

In few words, the partitioningBy partitions the collection into a map with exactly two keys, true and false, all
elements matching the predicate go into the true key all that do not match the predicate go into the false key. The
grouping on the other hands produces many keys based on the classifier lambda and input arguments and it and groups
values under one or more keys

That means that for the partitioningBy the map is always guaranteed to have two keys, which are pre-populated into
the map true|false, the groupingBy however does not guarantee any keys if there are no elements in the source
collection.

A. {} {}
B. {} {false=[], true=[]}
C. `{false=[], true=[]} {}`
D. {false=[], true=[]} {false=[], true=[]}
E. The code does not compile.
F. An exception is thrown.

17. Which of the following is equivalent to this code? UnaryOperator<Integer> u = x -> x \* x;

This unary operator says that we have one input argument and one output argument which are both of the same type. In
this case above that would be Integer. In the possible examples below  we have mostly incorrect usages of the
BiFunction which actually defines two input arguments and one return type & the BinaryOperator functional interface
that defines BiFunction where all three arguments are of the same type.

The only valid operation here is the E. Function\<Integer\>, Integer> that is the one that defines exactly the same
signatur as the UnaryOperator\<Integer\> specified above

A. BiFunction\<Integer\> f = x -\> x\*x;
B. BiFunction\<Integer, Integer\> f = x -\> x\*x;
C. BinaryOperator\<Integer, Integer\> f = x -\> x\*x;
D. Function\<Integer\> f = x -\> x\*x;
E. `Function\<Integer, Integer\> f = x -\> x\*x;`
F. None of the above

18. What is the result of the following?

```java
DoubleStream s = DoubleStream.of(1.2, 2.4);
s.peek(System.out::println).filter(x -> x > 2).count();
```

This one is tricky, first the filter lambda is expressed correctly, it compares the double value x to '2' which is
valid even though '2' by default is integral. The second caveat is the filter.count, however the value returned by
the count call is never used. What is important to realize is that we have a terminating reduction operation -
count. That will trigger the entire stream pipeline and ensure that we actually also get the lambda in the peek
called which is the only statement that contains some output. Therefore we are just going to get the elements of the
stream printed out by peek and that is all.

A. 1
B. 2
C. 2.4
D. `1.2 and 2.4`
E. There is no output.
F. The code does not compile.
G. An exception is thrown.

19. Which of the following return primitives? (Choose all that apply.)

Well the java library defines the following primitive suppliers, these cover the most basic requirements. There are
no Float, Char suppliers, and that is mostly because there are no such primitive streams provided in the first
place. The argument for not providing all of the primitives is that these provide the building blocks to obtain the
rest of the primitives - like byte, char, short, float through conversions

Primitive suppliers
```
BooleanSupplier
DoubleSupplier
IntSupplier
LongSupplier
```

Primitive streams
```
DoubleStream
IntStream
LongStream
```

A. `BooleanSupplier`
B. CharSupplier
C. `DoubleSupplier`
D. FloatSupplier
E. `IntSupplier`
F. StringSupplier

20. What is the simplest way of rewriting this code?

```java
List<Integer> l = IntStream.range(1, 6).mapToObj(i -> i).collect(Collectors.toList());
l.forEach(System.out::println);
```

A. IntStream.range(1, 6);
B. `IntStream.range(1, 6).forEach(System.out::println);`
C. IntStream.range(1, 6).mapToObj(1 -> i).forEach(System.out::println);
D. None of the above is equivalent.
E. The provided code does not compile.
