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
        // throw, meaning that in our example here we will execute the code in case 40, and default block. The regular
        // switch statement uses an assembly like labels, meaning that
        // - first, jump is made to the first label that matches the condition, and statement execution beings
        // - second code keeps executing sequentially until an exit or another jump is reached
        case 10: System.out.println("10");
        case 20: System.out.println("20");
        case 30: System.out.println("30");
        case 40: System.out.println("40");
        default: System.out.println("default");
    }

    // The output from the example above will actually be the following, because of the way the switch statement works,
    // as explained the labels below the very first one that catches the condition of the switch will all be run, until the
    // very first one that has a return/break/throw etc statement to exit the switch by a jump. Therefore we will
    // observe the following output fro this example above:
    // 30 - the actual condition that enters the switch statement purely because of the value of the flavors variable
    // 40 - the switch keeps executing the next statements, because those are just labels not flow control statements
    // default - and the default block will also be executed, there is nothing to stop the code from reaching it
    ```

- for-each loop constructs decompose into an iterator based loop that is done by the compiler, therefore every class
  that implements `Iterable<T>` is a candidate to be used in a for-each loop construct

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

- they can start with a letter, upper or lower case - `Base`, `base`.
- they can also start with underscore or dollar sign - `$base, _base`.
- they can NOT start with any other sign - not possible - `*base, /base`.
- they can NOT start with a number - not possible - `4base`.
- they can contain numbers, and digits, and symbols in the middle of the name or at the end of the name.
- they can not contain special symbols which are used by the language for operations and operators - \*,/+,-,>,<,=
  and so on.
- they can NOT be named the same as existing keywords in the language such as - `byte, boolean, char, double, int`
  `cast`, `instanceof`, and so on.

3. Literals

- all numeric integral literals in java are by default `int`
- all decimal floating point number literals in java are by default `double`
- by default all string literals in the language are interned - pooled internally by the virtual machine and re-used
  `String val1 = "val"; String val2 = "val"; assert(val1 == val2)`
- it is possible and allowed to separate integral and floating point literal declarations and initializations with
  an underscore, that way these literals are easier to read. - `9_9_9, 999_999_999`. The underscore CAN NOT be placed in
  front of an empty digit or a decimal floating point - the following are not valid`_9_9 or 19._9`
- one can represent a floating decimal number in a few ways - `5f, 5.f, 5.0f` all of which are valid
- integral long types can be represented like so - `long l = 10_000_000L`
- overflow declaration - `byte b = 128`, this value overflows byte, but this does not `byte b = 127`, the first
  declaration will produce a compiler error, the second will not.

4. Initializers

- classes can have static and non-static initialization blocks - `{ System.out.println("1"); } { System.out.println("1"); }`
- static class type initialization block are triggered just once when the class is loaded by the runtime
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

- `all core primitive types` in java have a wrapper types - `Boolean, Integer, Short, Byte, Character, Long, Double,
Float`, most of them, the numerical types at least implement Number, all of them are Serializable, and all can be boxed
  and unboxed into their primitive counterparts and back into their wrapper types.
- `wrapper types` implement the methods to be converted to a most primitives forms - byteValue, intValue, longValue,
  doubleValue, booleanValue and so on.
- `valueOf` - wrapper classes expose valueOf static methods, which convert a primitive to the wrapper class, usually
  those methods accept either the primitive equivalent of the wrapper class/type or a string - e.g.
  Double.valueOf(double|String), Integer.valueOf(int|String) and so on.
- `parseXXX` - wrapper classes expose parseXXX, all of the parse methods return the primitive not the wrapper classes as
  opposed to the `valueOf` method that is meant to generate a new Wrapper instance. `Integer.parseInt(String)`,
  `Double.parseDouble(String)`

6. Boxing/Unboxing

- `auto-boxing` is the action of implicitly converting a primitive type into a wrapper type, that is done by the
  compiler - `int -> Integer`
- `wrapper transitivity` - auto-boxing DOES NOT create wrappers transitively i.e this is invalid - `void method(Long l)
{}; method(5)`, we can call this method like that `method(5L)`, as explained the literal 5 is by default an `int` the
  `int will NOT be auto-boxed into Long`, it can be only boxed into its wrapper type `Integer`
- `overloading & auto-boxing` can be used with overloading methods - `method(Long l), method(Integer i)` works the same
  way as if those were primitives
- `auto-boxing argument shadowing` - does not hide/shadow primitive method overloads `method(Long l), method(long l)`
  call `method(5l)` will call the primitive version.
- `unboxing` - is the action of implicitly unboxing wrapper types into their primitive, usually done when arithmetic
  operations are involved. This is done by calling the `xxxxValue` method variation on the wrapper class, these methods
  are inherited by the Number class and implemented by the wrapper classes - `doubleValue, shotValue, byteValue, intValue`

7. Auto-casting

8. instanceof/cast

```java
abstract class Ball {}
interface Equipment {}
class SoccerBall extends Ball implements Equipment {}
```

- `instanceof` - what it does in simple terms is simply check if a given variable can be cast to a given type, without
  causing any compile or runtime errors it does NOT check if the runtime type of a variable is of particular type or in
  other words an instance of a some type.
- `SoccerBall ball = new SoccerBall(); if (ball instanceof Ball)` - when the check is applied against an abstract or
  normal non-final class the compiler is capable of checking the type hierarchy and verify if the class can be an
  `instanceof` a given type, as early as compile time, because we have single inheritance model in java, suggesting that
  `Ball ball = new SoccerBall<>(); if (ball instanceOf Equipment)` - this is allowed at compile time even though Ball
  reference type has no common hierarchy with the `Equipment` interface, the runtime type of the ball variable does it is
  of type `SoccerBall`, which implements Equipment
- `cast` - casting operator is the accompanying operator to the `instanceof`, as mentioned `instanceof` checks if a
  given variable (the runtime type) can be cast to to another type, the `cast` operator actually does the itself.

9. Wildcards

10. Object

- `wait()` - cause the current thread to wait until it is awakened, by a call to notify, remember that threads can
  be awakened sporadically and we have to guard against that.
- `notify()` - is the companion method to the wait method, it is supposed to be invoked, to awaken thread which was
  put to sleep with wait, there is a version of notify called `notifyAll()`. That does what the name suggests awakens
  all threads that sleep at the same time.
- `toString()` - provides means of generating a human readable string for the object, this is the primary way of
  providing a human readable representation of an object, it is however not meant to be used for anything more than
  recognizing the object in logging and debugging context.
- `hashCode()` - generate a near unique hash code for an object, used in hash based structures, however it works in
  conduction with equals, and it must return the same hash value for two classes for which equals returns true.
- `equals(Object o)` - compare an object of a given type to another one of the same type, return true if both
  objects are equal false otherwise, primary way of identifying object quality.
- `finalize()` - a method that will be called exactly one when the object is freed by the garbage collector, it is
  however deprecated in more recent versions of the language, because it is not quite reliable, also this method
  should not throw and be safe to execute, there is no way to predict when it is going to be called.
- `new Object()` - the object class has a default public constructor, that is primary used for two reasons, every
  class in Java extends off of object, by default every constructor of a Java class calls the default constructor of
  the parent class, as its first statement, so we have to have an accessible public constructor. Secondly it is quite
  useful to have a way to create a dummy reference object that can be used in synchronization blocks and such.

12. Loops

- `four types exist` - for-i, while, do-while, for-each. The first 3 all follow standard traditional form of
  definition, while the for-each is a special form that is a syntactic sugar for iterating upon common iterable
  structures in the language - standard traditional arrays - `int[]` and `Iterable` implementations
- `the for-i loop has 3 segments` - variable initialization, conditional expression and an update operation. They
  CAN NOT be interchanged, but we can add multiple initialization expressions and update operations separated by comma
  a common example of that can be `for (int i = 0, j = 0; i < 10 && j < 20; i++, j++) {}`
- `the curly braces` - surrounding the body of each of the 4 types of a loops can be omitted, if it contains just
  one statement or expression, including the post-form-condition of the while loop - `do-while`.
- `for-each loop variable` - substitution and boxing/un-boxing - variables in a for-each can be boxed/un-boxed and
  covariant - `List<Integer> ints = new ArrayList<>(); for (Object i : ints) {}; for (int i : ints) {}` or using plain
  primitive arrays - `int[] ints = new int[10]; for (Integer i : ints) {}; for (Object i : ints) {}`
- `for-each targets` - can be applied only to primitive or standard base array types or objects that implement the
  `Iterable` interface only, anything else is considered a compiler error and disallowed by the language

- `Iterator` - a generic iterator interface, implementations provide means of iterating across a collection of items,
  the interface provides means of getting the next element, checking if there are more elements

    ```java
        // the general structure of the iteration, looks like this
        default void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            // first check if there are more items in the iterator, the check on hasNext is mandatory otherwise
            // by default the implementations are allowed to throw if calling next can not produce a 'next' element
            while (hasNext())
            // obtain the next element from the iterator and do something with it, this method call has to be
            // always guarded by a check for hasNext, otherwise it may throw NoSuchElementException
                action.accept(next());
        }
    ```

- `Iterable` - implementing this interface allow any object to be considered iterable, a single method iterator that
  produces an instance implementation of the Iterator interface

- `ListIterator` - this is a specialization and extension of the normal Iterator, and allows iteration to traverse in
  either directions, forward or backward. And also obtain the current position of the iterator in the collection.

    ```
    - previous - counterpart to the next element
    - nextIndex- method allowing one to obtain the index of the next element instead of the element itself
    - previousIndex - counterpart to the `nextIndex`, and the previous methods allowing one to obtain index
    ```

12. Parameters

- `varargs (...)` - are passed to the method by the compiler as plain array, must always be declared at the end of a
  method's list of arguments/parameters, and can be accessed by simply indexing into the variable as one would an
  array. They can be iterated over as well with a normal for each iteration structure construct. Effectively the
  compiler actually calls the method like that - `method(new Type[] { var1, var2, var3, ... });`

13. Definition

- `variable definition` - can be performed on the same line - `double a, b, c`, variable initialization can also be
  performed on the same line on a per variable basis `double a = 1, b, c = 3`, variables can be skipped over
- `type declaration` - on the same line CAN NOT mix up types such as defining two variables of different types on the
  same line - `double a, int k`, this example is not valid
- `array definition` - bracket positions have concrete meaning, `after the type or after the variable`, mostly affects the
  variables defined on the same line - `double[] array, single;` or `double[] array1, array2;` defines two types of
  ways of defining an array variable, the second example actually makes all variables defined on that line be arrays
- `array dimensions` - definitions are additive - when declaring `double[] array[]` - the array variable is actually a 2
  dimensional array here

14. Exception

- `try-catch-finally` - block always has zero or at most one finally block, and zero or more catch blocks, but at
  least one of each must be present - the finally or catch block
- `try-catch-finally` - in the traditional standard try block we are not allowed to omit both catch and finally, one
  of these must be present for the standard try block to be considered valid and for the program to compile.
- `try-with-resources` - must declare the IOException thrown by the call to the close method from the AutoCloseable
  interface, or catch it and handle it. The try-with-resources only ensures that all close methods are called
- `Exception` vs `RuntimeException` - what makes Exception a checked exception is the fact that it is treated as
  such by the compiler, what that means is that when the compiler sees an exception being thrown that extends the
  Exception type it simply requires the code and user to catch it, if it sees exception that extends off of
  `RuntimeException` type it does not mandate any catch blocks or method signature definitions, the instead the
  runtime catches it.
- `multi-catch` order - when multiple catch statements are present then the order of the exception types that are
  caught has to be specific - first we HAVE to declare the more specific/narrow exception type, and then we have to
  declare the less narrow exception type otherwise it is a compiler error - i.e. catch IOException before Exception
- `throws method signature` clause can contain any number of both checked and unchecked exceptions, but only the
  checked ones are required to be caught by the compiler.
- suppressed exceptions - exceptions thrown from finally will suppress any exceptions thrown by the catch block, we
  have to provide the exception as suppressed otherwise it is lost
- `multi-catch` - in a multi catch block, we can not use types that are of parent-child relationship, but rather
  must be strictly siblings types, such types that share only a common parent but do not share a common class
  hierarchy themselves, the type of the piped symbol in a multi catch is the closest parent of the two sibling
  types

15. Overriding

```java
interface Interface {
    default Number play() {
    }
}
class Parent {
    public Long play() {
        return 3L;
    }
}
public class Child extends Parent implements Interface {
    public play() {
    }
}
```

- `class beats interface` - if we have a method with the same signature declared in both a class and an interface,
  and a child class that extends the class and implements the interface, the class beats the interface. Meaning that
  the class method will take precedence, also implying that if we make that method in the class final we will never be
  able to override the method coming from the interface.
- `covariant types` - when overriding a method we are allowed to use what are called covariant types, we can
  substitute the return type of the overridden method with a sub-type of the original return signature .
- `exception signature` - we are allowed to override a method exception throws signature by providing a narrower
  exception types, or omitting the exceptions altogether, we are NOT allowed to provide less narrow exception types.
- `access modifier` - reducing the visibility of the method is NOT allowed, we can increase the visibility of a
  method but never reduce it, remember the order is private -> default (package private) -> protected -> public
- `final modifier` - we can override a method from a parent class or interface and add final modifier to it,
  effectively disabling any other sub-class of the current class that overrides it from being able to override it
- `arguments list` - the arguments list of the method has to match EXACTLY, otherwise we are not overriding it we
  are overloading it the typical example of overloading equals instead of overriding it equals(Child) != equals(Object o)

10. Overloading

- `compile time polymorphism` - this is the technical, term that is used to describe what overloading is, it
  provides the compiler with the ability to choose which method is called based on the type of the arguments with
  which the method is invoked, during compile time.
- `overloading is name + arguments` - overloading is only based on method arguments, and the name of the method, a
  method can not be overridden and overloaded at the same time.
- `call site rules` - the compiler has a few strict call rules that are applied - first the method with the exact
  type arguments is chosen, then wrapper types / boxing is considered and finally variable argument based methods.
- `varargs vs no-args` - it is possible to provide method overload such that we have a version with no arguments and
  a version with only varargs - method() and method(String... vars) both can exist simultaneously and do not cause
  compiler error, if we call method without any arguments the overload with no arguments will win, because the
  compiler makes a choice on the number of arguments first (after matching the types, if any, of course).

# Caveats

- `class wins over interface` - rule that specifies that when both the interface and the class have the same method
  (signature matching) the class always wins, if that method is not abstract in the class, it will serve as the
  implementation of the method, if the method is declared as final it will actually prevent overriding and overshadow
  the method from the interface.
- `member variable shadowing` - child classes can shadow and re-declare member variables form a parent that they
  extend off of, meaning that a variable in the child can have the same name but completely different access
  modifiers, and even type, default value and what not, and both the child member variable and the parent are still
  accessible (through this.varName and super.varName)
- `shadowed variable access` - remember that shadowed variables are not lost or 'overridden', they are simply hidden,
  but we can access shadowed variables, imagine we have a `ChildClass` that extends off of `SuperClass`, and there is a
  member variable that we have shadowed from the `ChildClass` we can access it like such from the child class doing -
  `((SuperClass) this).shadowedVariable`
- `instanceof/cast` - these two operators are tightly related, both allow a more lenient check when the operand is of type
  interface, because at compile time the compiler does not know the type of the underlying variable, it needs to
  wait until runtime to actually check - `if (variable instanceof Collection)` or `(Collection) variable`, these are
  possible even if the reference type of variable does not share common relationship with Collection, because the
  runtime type of the variable might be actually a class that implements Collection, that is might not be known at
  compile time. It could be only known at compile time if we check against an abstract or non-final concrete class,
  due to the single inheritance model of java
- `instacneof` - simply checks if a given variable reference can be safely cast to another type, it does not check if the
  actual runtime type of the variable is of a specific type, meaning that it is not the same as actually checking the
  concrete class of the variable `veriable.getClass() != variable instanceof Class`. Use `getClass` to do that check.
- `visibility hierarchy` - public -> protected -> package-private -> private, when overriding methods we are not
  allowed to reduce the visibility of the method we are overriding, we can only increase or keep it the same.
  Reducing the visibility leads to a compiler error.
- `default constructors` - by default the compiler always calls the default constructor of the super-class, if none
  exists, or it was overridden with a constructor that takes in arguments, then we have to call it manually otherwise the
  compiler will produce an error.
- `new Object()` - YES object class has a default public constructor so we can technically create references and
  instances of new object, that is because classes all extend off of Object and the compiler implicitly always calls the
  default constructor of the super-class in the child-classes, therefore an accessible public constructor is required. The
  new Object reference can also be used for locks and synchronization purposes and so on. A default public constructor for
  the Object class is useful.
- `multi-catch` - the type of the variable in a multi catch is the closest parent that these types share, e.g. let
  us assume that we have the following multi catch IllegalStateException | IOException -> Exception, or Error |
  RuntimeException -> Throwable. Also multi catch exception variable can `NOT BE ASSIGNED`, they are declared and
  considered effectively final.
- `catch-all-checked` - in a catch block the compiler will enforce the known thrown exceptions therefore we ARE NOT
  allowed to catch CHECKED exceptions that are never thrown by the try block, if we do that would be compiler error
- `try-catch-finally` - a try block can have no catch statement only if the try block is try-with-resources or the
  try block has a finally block. A traditional try block without a finally or a catch is a compiler error. It is also
  not possible to have a finally block declared BEFORE the catch block the order is strictly defined as being: try ->
  catch -> finally.
- `try-with-resources` - when Exception is declared to be thrown by the close method from the resource that
  implements the AutoCloseable and Closeable interface, a catch block has to also be declared declared in the try-with-resources
  block, otherwise that is a compiler error
- `suppresed exceptions` - exceptions finally suppress or hide any exceptions thrown in catch block, catch the
  exceptions in the finally block that might have occurred int he catch block and attach them to to the exceptions in
  the finally block as suppressed.
- `traditional for-i` - has 3 sections that are strictly defined in the language specification, and they can not be
  interchanged, that is: variable declaration and initialization, condition, variable mutation & update
- `for-each` loop type allows for direct boxing/un-boxing and implicit parent-child substitution for reference
  types, that implies that we can do that - `ints[] ints = new int[10]; (Integer i : ints) {}`
- `array initialization and declaration` - there are multiple ways to initialize an array with a set number of
  values/entries, but those should not be intermixed - `String a = {"test"};` `String[] a = new String[] {"test"}`,
  `String[] a = new String[1]`, all of these are valid, and construct an array and initialize it correctly, but should
  not mix the size declaration with initialization list, use either and NOT both at the same time.
- `all arrays are covariant` types - `int[] a; Object o = a` or even `int[][] a; Object[] o = a`, that implies that
  we can implicitly cast to Object from a primitive array in Java.
- `default initializing values` - for certain types may trip people, such as double and float which being decimal
  type are initialized to 0.0 by default, the bits internally are all 0, but the floating point representation causes
  this value to be printed/interpreted as 0.0 not 0. Integer primitive values are initialized to 0. Reference types to
  null
- `local variables` and values - by default in java local variables are not initialized, it does not matter if the
  variable is a primitive type or a reference type, the value is undetermined, and more often than not the compiler
  will throw an error if a local uninitialized variable is used.
- `finalize` - is not advised to be used anymore, since there is no guarantee for the fitness of the method, the
  method can not be canceled, it can not be recovered from if it throws and it is generally not a good idea to use it
  anymore since java 11
- `equals` - has a higher weight when overriding from object meaning that `hashCode` must be such that for two
  objects which equals returns true `THEY HAVE TO HAVE THE SAME HASHCODE`, but the inverse is not true, for two
  objects that are not equal it IS POSSIBLE that two objects have the same `hashCode`. This is because `hashCode`
  tells us in which 'bucket' this object goes, while equals tells us that this object is exactly the same as this
  other object.
- `hashCode` - must not be used to check if two objects are equal because a `hashCode` value can not be guaranteed
  to be unique, see above, therefore only the equals method can tell you with great certainty that two objects are
  equal.
- `wait/notify` - a thread that is in wait state can be awakened sporadically, even if actually notify was never called
  in the first place, that is why it is advised to put wait in a while loop guarded by some sort of instance variable,
  that is checked in the while loop, that way wait is going to be called every time the thread waked up, until the
  variable does not match the condition to get out of the while-wait loop

## Modern guidelines

### 1. Use `record` for DTOs

- Auto-generates `equals()`, `hashCode()`, `toString()`, and getters.
- Immutable by design (no Lombok needed).
- Request/response DTOs (`@RequestBody`, `@ResponseBody`).
- Immutable configuration properties (`@ConfigurationProperties`).

```java
public class UserDto {
    private final String name;
    private final int age;
    // Boilerplate: constructor, getters, equals, hashCode, toString
}
```

```java
public record UserDto(String name, int age) { }
```

### 2. Prefer `var` for Local Variables (Judiciously)

- Use `var` when the type is obvious (e.g., `new` expressions, builders).
- Avoid `var` if it reduces readability (e.g., `var result = service.process()`).

```java
List<String> names = new ArrayList<>();
```

```java
var names = new ArrayList<String>();
```

### 3. Replace Lombok with Java Language Features

- Use `record` for DTOs (replaces `@Data`, `@Value`).
- Use compact constructors:
- When to Keep Lombok:
    - `@Slf4j` (still concise).
    - `@Builder` (until Java gets a native builder pattern).

```java
@Data
@Builder
public class Product { ... }
```

```java
public record Product(String id, String name) {
    public Product {
        Objects.requireNonNull(id);
    }
}
```

### 4. Sealed Classes for Domain Models

- Explicitly restricts inheritance (better domain modeling).
- Works great with Spring Data JPA `@Entity` hierarchies.

```java
public abstract class Shape { ... }
public class Circle extends Shape { ... }  // Unlimited extensibility
```

```java
public sealed class Shape permits Circle, Rectangle { ... }
```

### 5. Pattern Matching (`instanceof` and `switch`)

- Cleaner controller logic (e.g., handling polymorphic DTOs).

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
```

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

### 6. Text Blocks for JSON/HTML/SQL

- `@Sql` annotations in internal tests.
- Hardcoded API response examples.

```java
String json = "{\"name\":\"John\", \"age\":30}";
```

```java
String json = """
    {
        "name": "John",
        "age": 30
    }
    """;
```

### 7. Null Checks with `Objects.requireNonNullElse`

```java
return name != null ? name : "default";
```

```java
return Objects.requireNonNullElse(name, "default");
```

### 8. HTTP Interface Clients (Java 21+)

- No need for `@FeignClient` (standard Java interface).

```java
@FeignClient(url = "https://api.example.com")
public interface UserClient {
    @GetMapping("/users/{id}")
    User getUser(@PathVariable String id);
}
```

```java
public interface UserClient {
    @GetExchange("/users/{id}")
    User getUser(String id);
}
```

### 9. Avoid `@Autowired` use Constructor Injection Only

- Immutable dependencies (thread-safe, no Lombok `@RequiredArgsConstructor` needed).

```java
@Autowired
private UserService userService;
```

```java
private final UserService userService;

public UserController(UserService userService) {
    this.userService = userService;
}
```

### 10. Switch Expressions

```java
switch (status)
    case "OK": return 200;
    case "BAD": return 400;
    default: return 500;
}
```

```java
return switch (status) {
    case "OK" -> 200;
    case "BAD" -> 400;
    default -> 500;
};
```
