# Sequences

In the java language we have 3 main String like types which can help us to build a dynamic sequence of strings and
represent them in the language. It is important to note that in java the string is an object, but it has some special
properties, such as interning when it is still treated as object but due to its vast usage the creation of these string
objects as literals is optimized and pooled by the JVM.

- `String` - this is an immutable type ins Java, each string object can be dynamically created with `new` or defined as
  literal, it exposes an API, to inspect itself, but not mutate it's state, instead methods from String's API that produce
  a string create a copy of the original and the copy is returned

- `StringBuffer` - class represents a mutable sequence of characters. Designed for use in scenarios with heavy or repeated
  string modifications,`StringBuffer` allows changes to the internal character array without creating new objects. All
  modifications occur in place, making append, insert, replace, and delete operations efficient.

- `StringBuffer` - methods are synchronized, which means they are thread-safe and can be used safely by multiple threads
  at once. Internally, a `char[]` buffer holds the current data, and it resizes as needed when modifications require more
  capacity. The synchronization makes `StringBuffer` somewhat slower than its unsynchronized cousin, `StringBuilder`,
  which was introduced in Java 5 for single-threaded use cases.

## String

They can be created in variety of ways, but the most common two are either through interning or through dynamic
allocation such has

```java
String myString1 = "this is a test"; // string is interned by the java runtime,
String myString2 = new String("this is a test"); // dynamically created, even though it is 'technically the same'
String myString3 = "this is a test"; // string is interned and reused from the pool, making myString1 == myString3
```

Java defines one operator for String objects: +. It is used to concatenate two strings. For example, this statement
below will results in `myString` containing "I like Java."

```java
String myString = "I" + " like " + "Java.";
```

In Java, string literals are interned automatically. When you use string concatenation with **only literals** (i.e.,
constant values, `"I"`, `" like "`, and `"Java."`), the Java compiler performs **constant folding** at compile time. The
expression is evaluated and replaced with a single interned string literal in the `.class` file. So the code is
effectively equivalent to - `String myString = "I like Java.";`

Here is a more interesting example which shows how we can manually intern a string into the pool, that was dynamically
created, keep in mind that `intern` will actually return a copy of the original one, that copy is going to be the actual
string into the pool, the original one will be destroyed by the garbage collector eventually. This is similar to other
string methods which also return a copy of the original and not the original.

```java
String helloHeap = new String("hello");
String helloInterned = "hello";
assert (helloHeap != helloInterned);

String helloInterned2 = helloHeap.intern();
assert (helloInterned2 == helloInterned);
```

- `new String("hello")` creates a **new String object on the heap**, distinct from the string literal `"hello"`.
- The string literal `"hello"` is interned by default in the string pool.
- `helloHeap != helloInterned` is true, since these are separate objects (`helloHeap` is heap-allocated, and `helloInterned` is the JVM’s interned literal).
- `helloHeap.intern()` returns the reference to the **already-interned** `"hello"` string literal in the pool, not the heap object.
- Therefore, `helloInterned2 == helloInterned` is true―both refer to the interned string literal object, at this point

In general the primary property of the String class which is its immutability provides significant performance and
security benefits. For example, strings can be safely shared across threads, cached, or used as keys in collections like
`HashMap` and `HashSet` without risk of contents changing and de-stabilizing data structures.

### String Interning

String interning is a memory optimization strategy in Java. The JVM maintains a pool—essentially a cache—of unique
string literals and explicitly interned strings. When a string literal is created, or when the `intern()` method is
called on a string, the JVM checks the string pool for an existing entry. If one exists, it returns the pooled
reference; if not, it adds the new string to the pool. This ensures that identical string values share a single
instance, making `==` comparisons possible for interned strings and reducing memory overhead for repeated values.

For example, `"Hello" == "Hello"` evaluates to true because both refer to the same pooled object. However, `new
String("Hello") == "Hello"` is false because the constructor always creates a new object on the heap, not the pool.
Calling `.intern()` on the result of `new String("Hello")` will return the reference from the pool.

Interning is handled in the `StringTable`, a private, internal structure in the JVM. Its efficiency and size have
evolved over versions of Java, making interning particularly useful for applications with large numbers of repeated
string values, such as parsers or compilers.

### String immutability

All Java `String` objects are immutable. Any method that seems to "modify" a string (like `replace`, `substring`,
`toUpperCase`, or even simple concatenation) actually creates a new `String` object. This can lead to unexpected memory
use or performance hits in tight loops if not handled carefully.

### String pooling and equality

The `==` operator checks reference equality, not value equality. Only string literals and interned strings can reliably
be compared using `==`. Almost always, you should use `.equals()` to compare string contents, not `==`. A classic
pitfall is `new String("abc") == "abc"` yields `false` because they're different objects.

### Null & NullPointerException

Calling any method, including `.equals()` or `.length()`, on a `null` string reference results in a
`NullPointerException`. It is common practice to call `someString.equals(target)` in a way that avoids
`NullPointerException` by reversing it to `"constant".equals(variable)`.

### String concatenation

Using `+` to concatenate strings in a loop (e.g., `result = result + x;`) generates unnecessary intermediate `String`
objects and hurts performance. In such scenarios, use a `StringBuilder` or `StringBuffer`.

### Unicode and code-points

Java `String` uses UTF-16 encoding under the hood. Characters outside the Basic Multilingual Plane (BMP)—such as
emoji—are represented using surrogate pairs. This means that `length()` returns the number of `char` values, not actual
Unicode characters. To count actual Unicode code points, use `codePointCount` methods.

### String hash codes caching

`String.hashCode()` is cached after the first computation (since hash codes are used often for map keys). The internal
cache is lazily computed, which improves subsequent lookup efficiency but can be worth knowing for systems that profile
performance.

### Garbage Collection

Interned strings may remain in memory if referenced from the pool, even if not used elsewhere, especially with heavy or
unusual use of `.intern()`. This may affect applications with custom Class loader or that generate masses of interned
values dynamically.

### Default values

In arrays An array of `String` (e.g., `new String[10]`) contains all `null` elements by default, not empty strings.

### String.format pitfalls

`String.format()` and other format methods throw exceptions if the format pattern and arguments do not match. Format
strings are locale-dependent unless you specify a locale explicitly.

## StringBuilder

## StringBuffer
