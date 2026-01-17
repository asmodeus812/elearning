## Specification

0. Control

- switch statements with labels `case: {value}` - without break works like a goto label, meaning that any other case
  block below the one that matched the case, will be executed regardless of the case 'condition' that is because it is
  not really a condition, per-se, it is a label, This is a legacy behavior from old c-style switch statements that was
  kept in java, use the new switch-case with the arrow operator (case -> {expr}) to actually achieve the expected
  behavior

    ```java
    // assign a value to the variable to demonstrate that the switch statement fall through logic is not what
    // normal people would expect from it, extra care should be taken when working with switch-case expressions
    int flavors = 30;
    switch(flavors) {
        // this will enter the case label 30, and execute everything below, until it reaches a break, return or
        // throw, meaning that in our example here we will execute the code in case 40, and default block.
        case 10: System.out.println("10");
        case 20: System.out.println("20");
        case 30: System.out.println("30");
        case 40: System.out.println("40");
        default: System.out.println("default");
    }

    // The output from the example above will actually be:
    // 30
    // 40
    // default
    ```

1. Initialization

- a local variable (§14.4, §14.14) must be explicitly given a value before it is used, by either initialization
  (§14.4) or assignment (§15.26), in a way that can be verified using the rules for definite assignment (§16 (Definite
  Assignment)). `String test; System.out.println(test);`, invalid compiler error for un-initialized variable `test`
- default initialization is present for primitive types only, primitive types get default value regardless of the
  context they are in, integral parts (int, long, short, char, byte, etc) are 0 by default, boolean is false, `int k;
assert(k == 0);`
- by default reference types are initialized to NULL only when declared as member variables of a class type `class
Test { String myName; }`, when creating an instance of `Test`, the member `myName` will be initialized to null
- array initialization with init-block - `String a[] = { "test" };` or `String[] a = new String[] { "test" }` are
  both valid both declare the same exact array of string

2. Naming

- they can start with a letter, upper or lower case - `Base`, `base`
- they can also start with underscore or dollar sign - `$base, _base`
- they can NOT start with any other sign - not possible - `*base, /base`
- they can NOT start with a number - not possible - `4base`
- they can contain numbers, and digits
- they can not contain special symbols which are used by the language for operations and operators - \*,/+,-,>,<,=
  and so on.
- they can NOT be named the same as existing keywords in the language such as - byte, boolean, char, double, int,
  cast, instanceof, and so on.

3. Literals

- all numeric integral literals in java are by default `int`
- all decimal floating point number literals in java are by default `double`
- by default all string literals in the language are interned - pooled internally by the virtual machine and re-used
  `String val1 = "val"; String val2 = "val"; assert(val1 == val2)`
- it is possible and allowed to separate integral and floating point literal declarations and initializations with
  an underscore, that way these literals are easier to read. - `9_9_9, 999_999_999`
- one can represent a floating decimal number in a few ways - `5f, 5.f, 5.0f` all of which are valid
- integral long types can be represented like so - `long l = 10_000_000L`
- overflow declaration - `byte b = 128`, this value overflows byte, but this does not `byte b = 127`, the first
  declaration will produce a compiler error, the second will not.

4. Initializers

- classes can have static and non-static initialization blocks - `{ System.out.println("1"); } { System.out.println("1"); }`
- static initialization block are triggered just once when the class is loaded by the runtime
- instance initialization blocks are triggered every time when a new class instance is created
- instance initialization blocks are called right before EVERY constructor run for a class (even the default one)
- one class can have many static or instance initialization blocks, they are merged or invoked in the order of
  occurrence

```java
// this example demonstrates how a class initialization blocks are triggered, and their actual order, a class can
// have multiple initialization blocks but those will be merged into a single one, and the order of statements is is
// top-down, based on the declaration and definition of the block in the class
public class Bowling {
    {
        // will be called first on a new class instance
        System.out.println("first-instance-initializer-block");
    }
    public Bowling() {
        // will be called right after the initialization blocks
        System.out.println("instance-constructor-block");
    }
    static {
        // will be called the moment the class loader loads this class
        System.out.println("class-type-static-block");
    }
    {
        // will be called second on a new class instance
        System.out.println("second-instance-initializer-block");
    }
}
new Bowling();
```

5. Wrappers

- all core primitive types in java have a wrapper types - `Boolean, Integer, Byte, Character, Long, Double, Float`
- wrapper types all extend off of Number - an abstract class that provides minimal interface for representing a
  number,
- all wrapper types implement the methods to be converted to a most primitives forms - `byteValue, intValue,
longValue, doubleValue()`
- wrapper classes expose valueOf static methods, which convert a primitive to the wrapper class, usually those
  methods accept either the primitive equivalent of the wrapper class/type or a string - e.g.
  Double.valueOf(double|String), Integer.valueOf(int|String) and so on.
- wrapper classes expose parseXXX, all of the parse methods return the primitive not the wrapper classes as opposed
  to the valueOf method that is meant to generate a new Wrapper instance. Integer.parseInt(String),
  Double.parseDouble(String)

6. Boxing/Unboxing

- auto-boxing is the action of implicitly converting a primitive type into a wrapper type, that is done by the
  compiler - `int -> Integer`
- auto-boxing DOES NOT create wrappers transitively i.e this is invalid - `void method(Long l) {}; method(5)`, we
  can call this method like that `method(5L)`, as explained the literal 5 is by default an `int` the `int will NOT be
auto-boxed into Long`, it can be only boxed into its wrapper type `Integer`
- auto-boxing can be used with overloading methods - `method(Long l), method(Integer i)` works the same way as if
  those were primitives
- auto-boxing does not hide/shadow primitive method overloads `method(Long l), method(long l)` call `method(5l)`
  will call the primitive version.
- unboxing is the action of implicitly unboxing wrapper types into their primitive, usually done when arithmetic
  operations are involved.

7. Auto-casting

8. instanceof

9. Wildcards

10. Overloading

11. Object

- finalize() - a method that will be called exactly one when the object is freed by the garbage collector
- toString() - provides means of generating a human readable string for the object
- hashCode() - generate a near unique hash code for an object, used in hash based structures
- equals(Object o) - compare an object of a given type to another one of the same type, return true if both objects are equal false otherwise
- wait() - cause the current thread to wait until it is awakened

- iterator - list iterator?
- iterable
- generics

12. Parameters

- varargs ... - are passed to the method by the compiler as plain array, always at the end of a method, and can be
  accessed by simply indexing into the variable as one would an array. They can be iterated over as well with a normal
  for each iteration structure construct

13. Definition

- variable definition can be performed on the same line - `double a, b, c`, variable initialization can also be
  performed on the same line on a per variable basis `double a = 1, b, c = 3`, variables can be skipped over
- variable definition on the same line CAN NOT mix up types such as defining two variables of different types on the
  same line`double a, int k`
- array definition bracket positions have concrete meaning, after the type or after the variable, mostly affects the
  variables defined on the same line - `double[] array, single;` or `double[] array1, array2;` defines two types of
  ways of defining an array variable, the second example actually makes all variables defined on that line be arrays

# Caveats

- array initialization and declaration - `String a = {"test"};` `String[] a = new String[] {"test"}`, `String[] a =
new String[1]`, all of these are valid, and construct an array and initialize it correctly, but should not mix the
  size declaration with initialization list, use either not both
- all arrays are `covariant` types - `int[] a; Object o = a` or even `int[][] a; Object[] o = a`, that implies that we can
  implicitly cast to Object from a primitive array in java.
- default initializing values for certain types may trip people - such as double and float which being decimal type
  are initialized to 0.0 by default, the bits internally are all 0, but the floating point representation causes this
  value to be printed/interpreted as 0.0 not 0
-
- finalize - is not advised to be used anymore, since there is no guarantee for the fitness of the method, the
  method can not be canceled, it can not be recovered from if it throws and it is generally not a good idea to use it
  anymore since java 11
- equals - has a higher weight when overriding from object meaning that hashCode must be such that for two objects
  which equals returns true THEY HAVE TO HAVE THE SAME HASHCODE, but the inverse is not true, for two objects that are
  not equal it IS POSSIBLE that two objects have the same hashCode. This is because hashCode tells us in which
  'bucket' this object goes, while equals tells us that this object is exactly the same as this other object.
- hashCode - must not be used to check if two objects are equal because a hashCode value can not be guaranteed to be
  unique, see above, therefore only the equals method can tell you with great certainty that two objects are equal.
- wait/notify - a thread that is in wait state can be awakened sporadically, even if actually notify was never
  called in the first place, that is why it is advised to put wait in a while loop guarded by some sort of instance
  variable
