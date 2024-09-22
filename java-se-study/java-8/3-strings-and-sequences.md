# Sequences

In the java language we have 3 main String like types which can help us to build a dynamic sequence of strings and
represent them in the language. It is important to note that in java the string is an object, but it has some special
properties, such as interning when it is still treated as object but due to its vast usage the creation of these string
objects as literals is optimized and pooled by the JVM.

-   String - this is an immutable type ins Java, each string object can be dynamically created with `new` or defined as
    literal, it exposes an API, to inspect itself, but not mutate it's state, instead methods from String's API that produce
    a string create a copy of the original and the copy is returned

-   StringBuffer - todo

## String

They can be created in variety of ways, but the most common two are either through interning or through dynamic
allocation such has

```java
    String myString1 = "this is a test"; // string is interned by the java runtime,
    String myString2 = new String("this is a test"); // dynamically created, even though it is 'technically the same'
    String myString3 = "this is a test"; // string is interned and reused from the pool, making myString1 == myString3
```

Java defines one operator for String objects: +. It is used to concatenate two strings. For example, this statement
below will results in myString containing "I like Java."

```java
    String myString = "I" + " like " + "Java.";
```
