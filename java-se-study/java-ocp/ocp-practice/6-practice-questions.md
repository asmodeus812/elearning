1. What is the result of compiling and executing the following application?

```java
public class Remember {
    public static void think() throws Exception { // k1
        try {
            throw new Exception();
        } // k2
    }
    public static void main(String... ideas) throws Exception {
        think(); // k3
    }
}
```

This code will not compile because it is using a traditional try block that is missing a catch or a finally block,
in that case that is not considered valid

A. The code compiles and runs without printing anything.
B. The code compiles but a stack trace is printed at runtime.
C. The code does not compile because of line k1.
D. `The code does not compile for another reason.`

2. Choose the answer that lists the keywords in the order that they would be used together.

The usual order is try - catch - finally. Actually not only is it the usual order it is enforced by the compiler any
other order is invalid and will produce a compiler error

A. catch, try, finally
B. `try, catch, finally`
C. finally, catch, try
D. try, finally, catch

3.  4.Which of the following Throwable types is it recommended not to catch in a Java application?

A. Error
B. CheckedException
C. `Exception`
D. RuntimeException

5. What is the output of the following application?

```java
public class Baseball {
    public static void main(String... teams) {
        try {
            int score = 1;
            System.out.print(score++);
        } catch (Throwable t) {
            System.out.print(score++);
        } finally {
            System.out.print(score++);
        }
        System.out.print(score++);
    }
}
Baseball.main(new String[0])
```

Scope, this is a tricky one but it should produce a compiler error because the variable score is used outside of the
scope that it is declared in. If the score was defined at the top before the try block, the value that would have been printed here would be 123

A. `123`
B. 124
C. 12
D. None of the above

6. Which of the following is a checked exception?

The Only one here that is extending off of Exception is IOException, the rest are runtime exceptions and usually we
can deduce this by the name of the exception in the first place.

A. ClassCastException
B. `IOException`
C. ArrayIndexOutOfBoundsException
D. IllegalArgumentException

7. Fill in the blanks: The \***\*\_\_\_\_\*\***keyword is used in method declarations, while the \***\*\_\_\_\_\*\***keyword is used
   to throw an exception to the surrounding process.

The throws is the keyword that is declared at the signature level of a method, the purpose of it is to signatl
to the caller what types of exceptions can be thrown by the method, those include checked and un-checked
exceptions. The compiler will only enforce/require catch block the checked exception when the method is
invoked or used.

The throw is of course the keyword that is used to declare that an exception is being thrown at this point
in the code

A. `throws, throw`
B. catch, throw
C. throw, throws
D. throws, catch

8. If a try statement has catch blocks for both Exception and IOException, then which of the following statements is
   correct?

The only way we can do this is to first declare the more-specific exception type and have the more-generic parent
types be declared later on in blocks further down. That is enforced by the compiler, and if we exchange them the
program will not compile.

A. The catch block for Exception must appear before the catch block for IOException.
B. `The catch block for IOException must appear before the catch block for Exception.`
C. The catch blocks for these two exception types can be declared in any order.
D. A try statement cannot be declared with these two catch block types because they are incompatible.

9. What is the output of the following application?

```java
public class Football {
    public static void main(String officials[]) {
        try {
            System.out.print('A');
            throw new RuntimeException("Out of bounds!");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.print('B');
        } finally {
            System.out.print('C');
        }
    }
}
Football.main(new String[0])
```

Firstly the exception that is going to be thrown here is of type RuntimeException that has no catch block that means
the only thing that will catch this exception is the runtime itself, the code will print A, then C from the finally
block and finally the runtime will catch the exception and print the stack trace.

A. ABC
B. ABC, followed by a stack trace for a RuntimeException
C. `AC, followed by a stack trace for a RuntimeException`
D. None of the above

10. What is the result of compiling and running the following application?

```java
public class Fortress {
    public void openDrawbridge() throws Exception {
        try {
            throw new Exception("Circle");
        } catch (Exception e) {
            System.out.print("Opening!");
        } finally {
            System.out.print("Walls"); // p2
        }
    }
    public static void main(String[] moat) {
        new Fortress().openDrawbridge(); // p3
    }
} // p1
```

This might be confusing but the idea here is simple the method declares that it throws a checked exception, the
openDrawbridge, technically does not throw, because the exception is being caught and handled, but the compiler can
not know that because at runtime anything can really happen, that is why it blindly mandates that the main method
where the openDrawbridge, is called also declare that it must throw or the other option is to catch the exception
that the openDrawbridge is supposed to produce. The code will not compile because the main method is not doing
neither of those two things - catch or re-declare throws

A. The code does not compile because of line p1.
B. The code does not compile because of line p2.
C. `The code does not compile because of line p3.`
D. The code compiles, but a stack trace is printed at runtime.

11. Which of the following exception types must be handled or declared by the method in which they are thrown?

Out of these the only one that is considered checked is the Exception, the rest all inherit from the RuntimeException type.

A. NullPointerException
B. `Exception`
C. RuntimeException
D. ArithmeticException

12. What is the output of the following application?

```java
public class BasketBall {
    public static void main(String[] dribble) {
        try {
            System.out.print(1);
            throw new ClassCastException();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.print(2);
        } catch (Throwable ex) {
            System.out.print(3);
        } finally {
            System.out.print(4);
        }
        System.out.print(5);
    }
}
BasketBall.main(new String[0])
```

The code compiles just fine and the output is based on the order in which these blocks are going to be invoked,
first the. The caveat here is to remember the order -> try -> catch -> finally. The catch that tries to catch the
ArrayIndexOutOfBoundsException, will not trigger that is not an exception that is going to be thrown here, but the
catch that catches Throwable will catch the ClassCastException, that block will be triggered

A. `1345`
B. 1235
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

13. Which of the following statements about a finally block is true?

In the try-catch-finally structure all of the 3 components require brackets, unlike control structures which can
execute single statements or expressions without brackets, that is not the case here. The rest are incorrect

The one that might trip somebody, The options A. & D. - not every line is guaranteed to be executed because the
finally block can itself throw an exception, e.g. when closing a stream, or cleaning up resources, that is why we
often transition to using try-with-resources as it is safer and avoids this pitfall specifically

A. Every line of the finally block is guaranteed to be executed.
B. The finally block is executed only if the related catch block is also executed.
C. `The finally statement requires brackets {}.`
D. The finally block cannot throw an exception.

14. Given that FileNotFoundException is a subclass of IOException, what is the output of the following application?

```java
public class Printer {
    public void print() {
        try {
            throw new FileNotFoundException();
        } catch (IOException exception) {
            System.out.print("Z");
        } catch (FileNotFoundException enfe) {
            System.out.print("X");
        } finally {
            System.out.print("Y");
        }
    }public static void main(String... ink) {
        new Printer().print();
    }
}
```

The code will not compile the issue is in the question itself, FileNotFoundException is a sub-class of IOException
therefore the order of catch statements above is not correct, it has to be the other way around.

A. XY
B. ZY
C. `The code does not compile.`
D. The code compiles but a stack trace is printed at runtime.

15. Which keywords are required with a try statement?

```
I. catch
II. finalize
III. finally
```

As stated a try block must have at least a finally or a catch block they may be empty, which is not a good idea, but
the compiler will not allow compilation to proceed with these missing. A try block can exist on its own only with
try-with-resources that is because the actual compiler will insert the finally block and catch itself during the
compilation step

A. I only
B. II only
C. `I or III, or both`
D. None of these statements are required with a try statement.

16. Which statement about the role of exceptions in Java is INCORRECT?

Not all exceptions cause the program to terminate that depends on how the program handles the exception

A. Exceptions are often used when things “go wrong” or deviate from the expected path.
B. `An application that throws an exception will terminate.`
C. Some exceptions can be avoided programmatically.
D. An application that can properly handle its exception may recover from unexpected problems.

17. What is the output of the following application?

```java
class CapsizedException extends Exception {
}
class Transport {
    public int travel() throws CapsizedException {
        return 2;
    }
}
public class Boat {
    public int travel() throws Exception {
        return 4;
    } // j1
    public static void main(String... distance) throws Exception{
        try {
            System.out.print(new Boat().travel());
        } catch (Exception e) (
            System.out.print(8);
        )
    }
}
```

The catch block is missing, actually it is incorrect, the block uses () style brackets and not curly ones

A. 4
B. 8
C. The code does not compile due to line j1.
D. `The code does not compile for another reason.`

18. Which of following method signatures would not be allowed in a class implementing the Printer interface?

```java
class PrintException extends Exception {}
class PaperPrintException extends PrintException {}
public interface Printer {
    abstract int printData() throws PrintException;
}
```

When a method signature declares that a method that throws exception is of certain type we are not allowed to change
that type, the only possible thing that one can do is to simply use a covariant type for this exception in the
method signature, a covariant type is such type that is a child or is part of the hierarchy of the target type, and
can be substituted without changing the meaning of the program. For this example what a covariant type implies, is
that we can certainly replace PrintException with another exception that is a child of it, but not with the parent
exception Exception type.

A. public int printData() throws PaperPrintException
B. `public int printData() throws Exception`
C. `public int printData()`
D. None of the above

19. Which import statement is required to be declared in order to use the Exception, RuntimeException, and Throwable classes in an application?

Technically the Exception class and Throwable and RuntimeException are all in the Java Lang package, and that
package is always imported by default. Therefore we need not import anything really, the java.lang package is
automatically imported by the compiler otherwise a huge part of the language would be inaccessible and every program
would have to declare that import at the top in order to be useful, or really do anything meaningful

A. import java.exception._;
B. import java.util.exception._;
C. import java.lang.\_;
D. `None of the above`

20. Which statement about the following classes is correct?

```java
class GasException extends Exception {
}
class Element {
    public int getSymbol() throws GasException {
        return -1;
    } // g1
}
public class Oxygen extends Element {
    public int getSymbol() {
        return 8;
    } // g2
    public void printData() {
        try {
            System.out.print(getSymbol());
        } catch { // g3
            System.out.print("Unable to read data");
        }
    }
}
```

The catch exception above is not declared correctly, as you can notice the catch is missing the actual thing it must
catch in order for that statement to be complete a () style brackets are needed to be added and a proper exception
needs to be declared that has to be caught otherwise this block becomes meaningless

A. The code does not compile because of line g1.
B. The code does not compile because of line g2.
C. `The code does not compile because of line g3.`
D. None of the above

21. Fill in the blanks: A program must handle or declare \***\*\_\_\_\_\*\***but should never handle \***\*\_\_\_\_\*\***.

The general rule of thumb is that we never really want to handle the error exception as it is considered an error
that is unrecoverable from, the only things we handle are checked exceptions, the runtime exceptions we leave the
java virtual machine - the runtime, to handle for us which, that is why they are called RuntimeException after all
(among other reasons too)

A. java.lang.Error, unchecked exceptions
B. `checked exceptions, java.lang.Error`
C. java.lang.Throwable, java.lang.Error
D. unchecked exceptions, java.lang.Exception

22. What is the result of compiling and running the following application?

```java
class CastleUnderSiegeException extends Exception {}
class KnightAttackingException extends CastleUnderSiegeException {}
public class Citadel {
    public void openDrawbridge() throws RuntimeException { // q1
        try {
            throw new KnightAttackingException();
        } catch (Exception e) {
            throw new ClassCastException();
        } finally {
            throw new CastleUnderSiegeException(); // q2
        }
    }
    public static void main(String[] moat) {
        new Citadel().openDrawbridge(); // q3
    }
}
```

That is a tricky one but we can easily reason about it first the method declares that it throws a RuntimeException,
which is true the ClassCastException, in this case is indeed a RuntimeException, however in the finally block the
same method also throws CastleUnderSiegeException, which is extending off of Exception, and that one, is never
declared to be thrown, the KnightAttackingException, is caught by the catch block so that one is safely contained
and handled in this method, but the CastleUnderSiegeException leaks outside therefore it needs to be part of the
throws declaration of the openDrawbridge method.

A. The code does not compile because of line q1.
B. `The code does not compile because of line q2.`
C. The code does not compile because of line q3.
D. The code compiles, but a stack trace is printed at runtime.

23. If an exception matches two or more catch blocks, which catch block is executed?

That is possible situation where the first block specifies a more narrow exception and the other block specifies a
more generic exception such as ClassCastException and RuntimeException, in this case first the language mandates
that the catch for the ClassCastException is first and the catch block for the RuntimeException is declared second,
then the one that is declared first/earlier in the code will actually be triggered.

A. `The first one that matches is executed.`
B. The last one that matches is executed.
C. All matched blocks are executed.
D. It is not possible to write code like this.

24. What is the output of the following application?

```java
public class Computer {
    public void compute() throws Exception {
        throw new RuntimeException("Error processing request");
    }
    public static void main(String[] bits) {
        try {
            new Computer().compute();
            System.out.print("Ping");
        } catch (NullPointerException e) {
            System.out.print("Pong");
            throw e;
        }
    }
}
```

This code has a few issues that might trip somebody into thinking that it might not compile first the method compute
declares a method that throws checked exception but the actual implementation does not throw a checked exception
that is fine, the compiler does not enforce this to be true. The compute method is surrounded with a try block
however the catch block for this try block catches a completely unrelated exception that is the
NullPointerException, well in this case the exception that compute throws will go right through and be caught by the
java runtime during the app execution.

A. Ping
B. Pong
C. The code does not compile.
D. `The code compiles but throws an exception at runtime.`

25. In the following application, the value of list has been omitted. Assuming the code compiles without issue,
    which one of the following is not a possible output of executing this class?

```java
public class Attendance {
    private Boolean[] list = new Boolean[5];
    private Boolean[] list = new Boolean[10];
    public int printTodaysCount() {
        int count=0;
        for(int i=0; i<10; i++) {
            if(list[i]) ++count;
        }
        return count;
    }
    public static void main(String[] roster) {
        new Attendance().printTodaysCount();
    }
}
```

There are two things to consider here, first the array of Booleans might contain enough elements in the array
therefore there is not going to be ArrayIndexOutOfBoundsException, if that was the case by default all of those
reference type Boolean objects would have been initialized to NULL by default, and the if statement that checks for
the 'true' condition would blow up with a NullPointerException, because under the hood the compiler tries to unbox a
NULL reference boolean variable from the array of Booleans. Second we will immediately first hit
ArrayIndexOutOfBoundsException, in case the array was not off sufficient size for this example

By the way why is the NullPointerException due to unboxing, the compiler actually translates the code into something
very different and the if statement will look like this `if (list[i].booleanValue())`, you can see how if list[i]
was a null value then a method is attempted to be called on a reference type object that is NULL the result is
pretty clear.

A. `A stack trace for NullPointerException is printed.`
B. `A stack trace for ArrayIndexOutOfBoundsException is printed.`
C. A stack trace for ClassCastException is printed.
D. None of the above

26. Fill in the blanks: A \***\*\_\_\_\_\*\***occurs when a program recurses too deeply into an infinite loop, while
    a(n) \***\*\_\_\_\_\*\***occurs when a reference to a nonexistent object is acted upon.

This one can be easily deduced by the actual options we are given a StackOverflowError can indeed occur in
standard java application, and that is the runtime trying to prevent the virtual machine from crashing, and
producing an Error.

A. NoClassDefFoundError, StackOverflowError
B. `StackOverflowError, NullPointerException`
C. ClassCastException, IllegalArgumentException

27. Which of the following is NOT a reason to add checked exceptions to a method signature?

A. `To force a caller to handle or declare its exceptions`
B. To notify the caller of potential types of problems
C. To ensure that exceptions never cause the application to terminate
D. To give the caller a chance to recover from a problem

28. What is the output of the following application?

```java
public class Stranger {
    public static String getFullName(String firstName, String lastName) {
        try {
            return firstName.toString() + " " + lastName.toString();
        } finally {
            System.out.print("Finished!");
        } catch (NullPointerException npe) {
            System.out.print("Problem?");
        }
        return null;
    }
    public static void main(String[] things) {
        System.out.print(getFullName("Joyce","Hopper"));
    }
}
```

The output of this is a compiler error, the finally block precedes the catch block that is invalid, the ordering of
these blocks is very strict and it is mandatory try -> catch -> finally

A. Joyce Hopper
B. Finished!Joyce Hopper
C. Problem?Finished!null
D. `None of the above`

29. Fill in the blanks: A try statement has \***\*\_\_\_\_\*\***finally block(s) and \***\*\_\_\_\_\*\***catch blocks.

Try-catch-finally block always has zero or at most one finally block, and zero or more catch blocks, but at least
one of each must be present - the finally or catch block

A. `zero or one, zero or more`
B. one, one or more
C. zero or one, zero or one
D. one or more, zero or one

30. What is the output of the following application?

```java
abstract class Duck {
    protected int count;
    public abstract int getDuckies();
}
public class Ducklings extends Duck {
    private int age;
    public Ducklings(int age) {
        this.age = age;
    }
    public int getDuckies() {
        return this.age / count;
    }
    public static void main(String[] pondInfo) {
        Duck itQuacks = new Ducklings(5);
        System.out.print(itQuacks.getDuckies());
    }
}
Ducklings.main(new String[0])
```

Exception is thrown at runtime for a Division by 0, why is that ? Remember that member variables be it primitive or
reference types are always initialized to either 0 for primitive types or NULL for reference types, therefore,the
value of count is going to be 0 here, and age is 5. We get a division by 0, in the method getDuckies.

A. 0
B. 5
C. The code does not compile.
D. `The code compiles but throws an exception at runtime.`

31. Given a try statement, if both the catch block and the finally block each throw an exception, what does the caller see?

That is one of the primary reasons for the existence of suppressed exceptions, in this case the finally block will
suppress any exception thrown by the catch block. To avoid this have a try-catch in the finally that will also catch
the exception from the catch block, get that exception and attach it to the one that the finally throws

```java
public class Suppressed {
    public void catchMeIfYouCan() throws RuntimeException {
        Throwable t = null;
        try {
            // do something that might throw
            throw new NullPointerException();
        } catch (Exception e) {
            t = new ClassCastException();
            throw t;
        } finally {
            try {
                // do something that might throw
                throw new NullPointerException();
            } catch (Exception e) {
                // may check if t is not null here
                RuntimeException s = new IllegalStateException(t);
                s.addSuppressed(t);
                throw s;
            }
        }
    }
    public static void main(String[] moat) {
        try {
            new Suppressed().catchMeIfYouCan();
        } catch (RuntimeException e) {
            System.out.println("exception: " + e.getClass() + " suppressing: " + Arrays.toString(e.getSuppressed()));
        }
    }
}
Suppressed.main(new String[0])
```

A. The exception from the catch block
B. `The exception from the finally block`
C. Both the exception from the catch block and the exception from the finally block
D. None of the above

32. What is the output of the following application?

```java
class BigCat {
    void roar(int level) throw RuntimeException { // m1
        if(level<3) throw new IllegalArgumentException("Incomplete");
        System.out.print("Roar!");
    }
}
public class Lion extends BigCat {
    public void roar() { // m2
        System.out.print("Roar!!!");
    }
    public static void main(String[] cubs) {
        final BigCat kitty = new Lion(); // m3
        kitty.roar(2);
    }
}
```

The roar method declares that it `throw` not `throws`, the name of the keyword is incorrectly used here. Other than that the code is fine in all the other places in this implementation

A. `The code does not compile because of line m1.`
B. The code does not compile because of line m2.
C. The code does not compile because of line m3.
D. The code compiles but a stack trace is printed at runtime.

33. Given the following code snippet, which specific exception will be thrown?

```java
final Object exception = new Exception();
final Exception data = (RuntimeException) exception;
System.out.print(data);
```

In this case we are attempting to cast an exception of type Exception to RuntimeException, however the
RuntimeException is a child type of the Exception class therefore that is never going to be possible since the
exception variable is instantiated like Exception();

A. `ClassCastException`
B. RuntimeException
C. NullPointerException
D. None of the above

34. Which of the following classes/types will handle all types in a catch block?

Throwable is the root of all things that can throw in java, even Error, that is not a great idea to use in a catch
block because you might catch Error, and catching error is usually not the best idea.

A. Exception
B. Error
C. `Throwable`
D. RuntimeException

35. In the following application, the values of street and city have been omitted. Which one of the following is a
    possible output of executing this class?

```
I. 350 5th Ave - New York
II. Posted:350 5th Ave - New York
```

```java
public class Address {
    public String getAddress(String street, String city) {
        try {
            return street.toString() + " : " + city.toString();
        } finally {
            System.out.print("Posted:");
        }
    }
    public static void main(String[] form) {
        String street = null; // value omitted
        String city = null; // value omitted
        System.out.print(new Address().getAddress(street,city));
    }
}
Address.main(new String[0])
```

This will simply print the following text, Posted: java.lang.NullPointerException... This question feels incomplete...

A. I only
B. II only
C. I and II
D. `None of the above`

36. If a try statement has catch blocks for both ClassCastException and RuntimeException, then which of the following statements is correct?

The only one rule that needs to be adhered by here is to ensure that the narrower exception types come before the more
general ones, when they belong to the same class hierarchy, like in this example that is the case, ClassCastException is
a runtime exception and it extends off off RuntimeException, therefore its catch clause has to come before the one that
catches the RuntimeException and that is mandated, by the compiler.

A. `The catch block for ClassCastException must appear before the catch block for RuntimeException.`
B. The catch block for RuntimeException must appear before the catch block for ClassCastException.
C. The catch blocks for these two exception types can be declared in any order.
D. A try statement cannot be declared with these two catch block types because they are incompatible.

37. Which of the following is the best scenario to use an exception?

This is a joke right ?

A. The computer caught fire.
B. The code does not compile.
C. `A caller passes invalid data to a method.`
D. A method finishes sooner than expected.

38. What is the output of the following application?

```java
class Organ {
    public void operate() throws RuntimeException {
        throw new RuntimeException("Not supported");
    }
}
public class Heart extends Organ {
    public void operate() throws Exception {
        System.out.print("beat");
    }
    public static void main(String... cholesterol) throws Exception {
        try {
            new Heart().operate();
        } finally {
            // empty finally block
        }
    }
}
```

This might catch somebody by surprise because of the nature of the throws clause. Note that the method in the parent
class declares that it throws a RuntimeException, which is perfectly fine, however in the child class we override
this method, (not overload) and we declare that the method throws Exception. That is invalid, the reason is simple
had we used a narrower type that is a child of RuntimeException that would have been fine (we could have used /
declared even more than one sub-types of RuntimeException) however the overriding method declares that it throws
Exception, not only is Exception parent of RuntimeException but we completely change the signature of the method
when compared to the parent class which is invalid and leads to a compiler error.

```java
// all of these are valid ways to override the signature by providing additional co-variant types of
// the type RuntimeException which the original method in the parent declares to be throwing
public void operate() throws ClassCastException, IllegalStateException, RuntimeException {
    System.out.print("beat");
}
```

A. beat
B. Not supported
C. `The code does not compile.`
D. The code compiles but a stack trace is printed at runtime.

39. Which statement about the following exception statement is correct?

```java
throw new NullPointerException();
```

NullPointerException is of type RuntimeException, the compiler does not mandate we handle these and it will delegate
the handling of any type of RuntimeException, to the runtime, unless we handle them but that is optional.

A. The code where this is called must include a try-catch block that handles this exception.
B. The method where this is called must declare a compatible exception.
C. This exception cannot be handled.
D. `This exception can be handled with a try-catch block or ignored altogether by the code`

40. What is the output of the following application?

```java
public class Coat {
    public Long zipper() throws Exception {
        try {
            String checkZipper = (String) new Object();
        } catch (Exception e) {
            throw RuntimeException("Broken!");
        }
        return null;
    }
    public static void main(String... warmth) {
        try {
            new Coat().zipper();
            System.out.print("Finished!");
        } catch (Throwable t) {}
    }
}
```

NEW OBJECT() ? YES Object has a public constructor, but this throws a ClassCastException, that is caught and in the
catch does not throw a new instance of a RuntimeException correctly. Missing the keyword new to create the new instance of the RuntimeException object

A. Finished!
B. Finished!, followed by a stack trace
C. The application does not produce any output at runtime.
D. `The code does not compile.`

41. Given the following application, which type of exception will be printed in the stack trace at runtime?

```java
public class WhackAnException {
    public static void main(String... hammer) {
        try {
            throw new ClassCastException();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        } catch (RuntimeException e) {
            throw new NullPointerException();
        } finally {
            throw new RuntimeException();
        }
    }
}
WhackAnException.main(new String[0])
```

As we already mentioned the finally block will swallow all other exceptions that occur in other places like the
catch blocks in this case. Even though the ClassCastException will be caught by the catch clause which catches the
RuntimeException, the finally block will run and swallow/silence the NullPointerException that is thrown from that
catch block above it. Otherwise the code is defined well it will actually compile and run just fine, the catch
clauses are defined in correct order, it would have failed to compile had the RuntimeException catch clause were
declared before the IllegalArgumentException, catch clause, both are type of RuntimeException, but the
IllegalArgumentException is a more narrow type.

A. IllegalArgumentException
B. NullPointerException
C. `RuntimeException`
D. The code does not compile.

42. Which of these method signatures is allowed in a class implementing the Outfielderinterface ?

```java
class BadCatchException extends Exception {}
class OutOfBoundsException extends BadCatchException {}
public interface Outfielder {
    public void catchBall() throws OutOfBoundsException;
}
```

There is only one option here that is truly vaild, the catchBall, signature declares that it throws
OutOfBoundsException, that exception is a child of BadCatchException, which itself is child of Exception, there is
no sub-type of OutOfBoundsException we could possibly replace in the method signature, certainly not
BadCatchException, or the base Exception class, to make the signature valid.

A. `public int catchBall() throws OutOfBoundsException`
B. public int catchBall() throws BadCatchException
C. public int catchBall() throws Exception
D. None of the above

43. What is the output of the following application?

```java
public class Street {
    public static void dancing() throws RuntimeException {
        try {
            throw new IllegalArgumentException();
        } catch (Error e) {
            System.out.print("Unable!");
        }
    }
    public static void main(String... count) throws RuntimeException {
        dancing();
    }
}
Street.main(new String[0]);
```

This class has a catch for Error, the error class is not part of the Exception hierarchy, it is a sibling of
Exception and by proxy it is also a sibling of RuntimeException, IllegalArgumentException is a sub-type of
RuntimeException, therefore this catch clause will never be called, meaning that the main method will re-throw the
exception because nothing is actually catching up to the runtime, which will catch it and print a stack trace.

A. Unable!
B. The application does not produce any output.
C. `The application compiles but produces a stack trace at runtime.`
D. The code does not compile.

44. What is the result of compiling and running the following application?

```java
class DragonException extends Exception {}
public class Lair {
    public void openDrawbridge() throws Exception { // r1
        try {
            throw new Exception("This Exception");
        } catch (RuntimeException e) {
            throw new DragonException(); // r2
        } finally {
            throw new RuntimeException("Or maybe this one");
        }
    }
    public static void main(String[] moat) throws Exception {
        new Lair().openDrawbridge(); // r3
    }
}
```

The exception is correctly declared to be thrown by the openDrawbridge, and the main method as well, which means
that in this case whatever is thrown by the main method will be intercepted by the runtime itself, that is perfectly
fine. The rest of the lines that are marked with possible compile time issues are perfectly fine, therefore this
code compiles and will throw at runtime

A. The code does not compile because of line r1.
B. The code does not compile because of line r2.
C. The code does not compile because of line r3.
D. `The code compiles, but a stack trace is printed at runtime.`

45. If a try statement has catch blocks for both IllegalArgumentException and ClassCastException, then which of the
    following statements is correct?

Those can really be declared in any order both of these classes extend off of RuntimeException but they themselves
have no common hierarchy they are effectively siblings.

A. The catch block for IllegalArgumentException must appear before the catch block for ClassCastException.
B. The catch block for ClassCastException must appear before the catch block for IllegalArgumentException.
C. `The catch blocks for these two exception types can be declared in any order.`
D. A try statement cannot be declared with these two catch block types because they are incompatible.

46. What is the output of the following application?

```java
class Problem implements RuntimeException {}
public class BiggerProblem extends Problem {
    public static void main(String args[]) {
        try {
            throw new BiggerProblem();
        } catch (BiggerProblem re) {
            System.out.print("Problem?");
        } catch (Problem e) {
            System.out.print("Handled");
        } finally {
            System.out.print("Fixed!");
        }
    }
}
BiggerProblem.main(new String[0]);
```

This is cheap using implements keyword where extends is expected, really annoying

A. Problem?Fixed!
B. Handled.Fixed!
C. Problem?Handled.Fixed!
D. `The code does not compile.`

47. What is the output of the following application?

```java
interface Source {
    void flipSwitch() throws Exception;
}
public class LightBulb implements Source {
    public void flipSwitch() {
        try {
            throws new RuntimeException("Circuit Break!");
        } finally {
            System.out.print("Flipped!");
        }
    }
    public static void main(String... electricity) throws Throwable {
        final Source bulb = new LightBulb();
        bulb.flipSwitch();
    }
}
```

The code contains invalid keyword usage in the try block using - throws new RuntimeException(...), instead of throw
new, again similar to the last question this is a cheap one, there is no glory here. However we are allowed to
override the method without specifying any exception being thrown, remember we are allowed to supply a narrower
exception type, same exception or no exception in the overriding child, but NEVER a broader type

A. A stack trace for a RuntimeException
B. Flipped!, followed by a stack trace for a RuntimeException
C. The code does not compile because flipSwitch() is an invalid method override.
D. `The code does not compile for another reason.`

48. Given an application that hosts a website, which of the following would most likely result in a java.lang.Error
    being thrown?

Really Error is reserved for special types of issues, such as the entire runtime getting an out of memory error, in
cases where the container/pod where this app is running is unable to obtain more memory and thus the entire
application is bound to fail and completely crash

A. Two users try to register an account at the same time.
B. The application temporarily loses connection to the network.
C. A user enters their password incorrectly.
D. `The application runs out of memory.`

49. Given that FileNotFoundException is a subclass of IOException, what is the output of the following application?

```java
import java.io.*;
public class Backup {
    public void performBackup() {
        try {
            throw new IOException("Disk not found");
        } catch (Exception e) {
            try {
                throw new FileNotFoundException("File not found");
            } catch (FileNotFoundException e) { // z1
                System.out.print("Failed");
            }
        }
    }
    public static void main(String... files) {
        new Backup().performBackup(); // z2
    }
}
Backup.main(new String[0]);
```

Variable naming collision between the two catch blocks the symbol variable name e is already used. Therefore we are
unable to use the same variable name while we are located in the same scope.

A. Failed
B. The application compiles but a stack trace is printed at runtime.
C. `The code does not compile because of line z1.`
D. The code does not compile because of line z2.

50. What is the output of the following application?

```java
public class Sleep {
    public static void snore() {
        try {
            String sheep[] = new String[3];
            System.out.print(sheep[3]);
        } catch (RuntimeException e) {
            System.out.print("Awake!");
        } finally {
            throw new Exception(); // x1
        }
    }
    public static void main(String... sheep) {
        new Sleep().snore(); // x3
    }
}
```

This code throws checked exception in its finally block the compiler can immediately notice that path and will
require the method to declare that it throws a checked exception otherwise it is invalid

There are other issues if this method did declare to throw Exception at its signature, then at runtime the code will
throw ArrayIndexOutOfBoundsException, which is a runtime exception, and will be caught by the catch, printing out
however immediately after the finally block would throw a new exception, and the correct answer would have been A.

A. Awake!, followed by a stack trace
B. `The code does not compile because of line x1.`
C. The code does not compile because of line x2.
D. The code does not compile because of line x3.

51. Which of the following pairs fills in the blanks to make this code compile?

```java
public void read() ___________ SQLException {
    ___________ new SQLException();
}
```

This one is pretty straightforward, the first keyword on the method signature / declaration must be throws while the
one that specifies that the new exception being thrown by the method is - throw

A. throw on line 5 and throw on line 6
B. throw on line 5 and throws on line 6
C. `throws on line 5 and throw on line 6`
D. throws on line 5 and throws on line 6
E. None of the above. SQLException is a checked exception and cannot be thrown.
F. None of the above. SQLException is a runtime exception and cannot be thrown.

52. Which of the following changes when made independently would make this code compile? (Choose all that apply.)

```java
1. public class StuckTurkeyCage implements AutoCloseable {
2.     public void close() throws Exception {
3.         throw new Exception("Cage door does not close");
4.     }
5.     public static void main(String[] args) {
6.         try (StuckTurkeyCage t = new StuckTurkeyCage()) {
7.             System.out.println("put turkeys in");
8.         }
9.     }
10. }
```

We have three options here the method above overrides the close from the AutoCloseable interface and there are fea
options

- either we declare the main method also throws, that is because the close method is declared to throw, and we are
  mandated by the complier to catch it
- another option is to simply catch that exception in the try-with-resources with a standard catch block
- finally we can also omit the throws Exception, that is possible because the method override rules allow this, we
  are not allowed to only specify a broader exception type but everything else is game.

A. `Remove throws Exception from the declaration on line 2.`
B. `Add throws Exception to the declaration on line 5.`
C. `Change line 8 to } catch (Exception e) {}.`
D. Change line 8 to } finally {}.
E. None of the above will make the code compile.
F. The code already compiles as is.

53. Which of the following fills in the blank to make the code compile? (Choose all that apply)

```java
public static void main(String[] args) {
    try {
        throw new IOException();
    } catch (___________) {
    }
}
```

There is really only one option here, the FileNotFoundException is a child of IOException, therefore that will not
work, because that is not the exception that the throws block throws, so the only workable solution here is
IOException | RuntimeException, in this case those two share no common hierarchy they are siblings and are perfectly
valid to be caught in an exception pipe operator.

A. FileNotFoundException | IOException e
B. FileNotFoundException e | IOException e
C. FileNotFoundException | RuntimeException e
D. FileNotFoundException e | RuntimeException e
E. `IOException | RuntimeException e`
F. IOException e | RuntimeException e

54. Which of the following are true statements? (Choose all that apply.)

A. `A traditional try statement without a catch block requires a finally block.`
B. `A traditional try statement without a finally block requires a catch block.`
C. A traditional try statement with only one statement can omit the {}.
D. A try-with-resources statement without a catch block requires a finally block.
E. A try-with-resources statement without a finally block requires a catch block.
F. A try-with-resources statement with only one statement can omit the {}.

55. What is the output of the following code?

```java
import java.io.\*;
public class AutocloseableFlow {
    static class Door implements AutoCloseable {
        public void close() {
            System.out.print("D");
        }
    }
    static class Window implements AutoCloseable {
        public void close() {
            System.out.print("W");
            throw new RuntimeException();
        }
    }
    public static void main(String[] args) {
        try (Door d = new Door(); Window w = new Window()) {
            System.out.print("T");
        } catch (Exception e) {
            System.out.print("E");
        } finally {
            System.out.print("F");
        }
    }
}
AutocloseableFlow.main(new String[0]);
```

This is quite a convoluted question, but lets go step by step, here is what is going to happen, when the compiler
converts the try-with-resources into a standard try-catch-finally:

- first note that the close methods override without specifying throws Exception, which is valid
- only one of the methods throws exception and that is a RuntimeException, from the Window class
- we enter the try-with-resources the constructors trigger and objects are created -> door, window
- the try block prints the "T"
- the try-with-resources starts closing resources, front-to-back, window first then door
- closing window prints "W" and throws exception,
- closing door prints "D", nothing is thrown
- we enter the catch(Exception e) print "E"
- we enter the finally block printing "F"

What is important to note here is that the try catch will be converted into a nested try-catch-finally blocks by the
compiler something like that, the order of the close calls is in the reverse order of their declarations, that is
because the resources are unwound like a stack, allowing us to create dependent resources i.e. a Window can be
created by using the constructed Door resource in our case.

```java
// our actual try block starts here, it is this outer try block and the try-with-resources is unrolled into a nested
// structure of close calls each of which is called in a finally block but even if it throws it will be caught by the
// outer try's catch block
try {
    try  {
        try  {
            // work, system.out.print
        } catch (Exception e) {
            // work related exceptions
        } finally {
            // triggers the exception
            w.close();
        }
    } catch (Exception e) {
        // catch exception from w.close()
    } finally {
        // call does not throw anything
        d.close();
    }
} catch (Exception e) {
    // work, system.out.print
} finally {
    // work, system.out.print
}
```

A. TWF
B. TWDF
C. `TWDEF`
D. TWF followed by an exception
E. TWDF followed by an exception
F. TWEF followed by an exception

56. What is the result of running java EchoInput hi there with the following code?

```java
public class EchoInput {
    public static void main(String [] args) {
        if(args.length <= 3) assert false;
        System.out.println(args[0] + args[1] + args[2]);
    }
}
```

This is a bit of a trick question, because by default asserts and assertions are disable in java, so actually if we
start this program the assert statement will not trigger even if the branch goes through there, therefore this
program will directly throw ArrayIndexOutOfBoundsException because we are accessing indices in args that do not
exist.

A. hithere 8.
B. The assert statement throws an AssertionError.
C. `The code throws an ArrayIndexOutOfBoundsException.`
D. The code compiles and runs successfully, but there is no output.
E. The code does not compile.

57. Which of the following command lines cause this program to fail on the assertion? (Choose all that apply.)

```java
public class On {
    public static void main(String[] args) {
        String s = null;
        assert s != null;
    }
}
```

By default assertions are disabled, so we actually can enable them globally and then disable them for specific
package and class, or the other way around we can disable them globally and enable for specific packages and
classes. Both are valid both -da (-disableassertions) and -ea (-enableasserts) are valid command line options that
can be passed to the java command.

The format is the {package-name}:{class-name}, so there are several options from the possible list below that are
actually valid variations for the command to actually run the program with exceptions enabled.

A. java –da On
B. `java –ea On`
C. `java -da -ea:On On`
D. java -ea -da:On On
E. The code does not compile.

58. Which of the following prints OhNo with the assertion failure when the number is negative? (Choose all that apply.)

The assert keyword in java can be invoked with the short form and the long form, the short form is just the assert
followed by a condition, that has to be met otherwise AssertionError will be thrown at runtime. The format of the
assert is -> assert {condition} : {message} where the condition has to be something that we assert has to be TRUE,
if it is FALSE then the message will be shown/set in the AssertionError. The condition can be wrapped in brackets
just as like if we invoking a function - assert(cond) or assert cond, and has to be a valid condition that evaluates
to TRUE or FALSE, in other words has to be boolean condition construct

A. `assert n < 0: "OhNo";`
B. assert n < 0, "OhNo";
C. assert n < 0 ("OhNo");
D. `assert(n < 0): "OhNo";`
E. assert(n < 0, "OhNo");

59. Which of the following are true of the code? (Choose all that apply.)

```java
4. private int addPlusOne(int a, int b) {
5.     boolean assert = false;
5.     assert a++ > 0;
7.     assert b > 0;
9.     return a + b;
11. }
```

This line will not actually compile because the variable that is being declared is named as a reserved keyword -
assert, remember that assert is not a method in the language but rather a keyword that has a special meaning,
regardless of the state of the assertion flag at runtime the compiler will not allow you to use reserved keyword as
a variable name

If we remove or delete the line 5, which declares a variable that is not used anyway, we can certainly compile this
program without any issues.

A. `Line 5 does not compile.`
B. Lines 6 and 7 do not compile because they are missing the String message.
C. Lines 6 and 7 do not compile because they are missing parentheses.
D. Line 6 is an appropriate use of an assertion.
E. Line 7 is an appropriate use of an assertion.

60. Which of the following are runtime exceptions? (Choose all that apply.)

This is just necessary to know which are which, we can probably use deduction here and easily see that Exception,
IOException and SQLException are all 3 checked but for the rest it is a bit tricky, since for example things like
MissingResourceException might imply that this exception extends off of some IOException hierarchy but it in fact is
a RuntimeException

A. Exception
B. `IllegalStateException`
C. IOException
D. `MissingResourceException`
E. `DateTimeParseException`
F. SQLException

61. Which of the following can legally fill in the blank? (Choose all that apply.)

```java
public class AhChoo {
    static class SneezeException extends Exception { }
    static class SniffleException extends SneezeException { }
    public static void main(String[] args) throws SneezeException {
        try {
            throw new SneezeException();
        } catch (SneezeException e) {
            ---
            throw e;
        }
    }
}
```

We can not throw a more broader exception in this case the ones that are not going to be allowed are the Exception.

The RuntimeException is allowed we can certainly throw it, even though the method declares that it throws exception
of a given type it does not have to actually do that. But we can not assign it to the variable e since it is of type
SneezeException, therefore we can not actually with the given restrictions actually throw the RuntimeException
option, but it would have been allowed under normal circumstances.

A. `// leave line blank`
B. e = new Exception();
C. e = new RuntimeException();
D. `e = new SneezeException();`
E. `e = new SniffleException();`
F. None of the above; the code does not compile.

62. Which of the following can legally fill in the blank? (Choose all that apply.)

```java
public class AhChoo {
    static class SneezeException extends Exception { }
    static class SniffleException extends SneezeException { }
    public static void main(String[] args) throws SneezeException {
        try {
            throw new SneezeException();
        } catch (SneezeException | RuntimeException e) {
            ---
            throw e;
        }
    }
}
```

The variable in a multi-catch pipe block is effectively final, it can NOT be re-assigned, we can only read/call
methods on it

A. `// leave line blank`
B. e = new Exception();
C. e = new RuntimeException();
D. e = new SneezeException();
E. e = new SniffleException();
F. None of the above; the code does not compile.

63. Which of the following can legally fill in the blank? (Choose all that apply.)

```java
public class AhChoo {
    static class SneezeException extends Exception { }
    static class SniffleException extends SneezeException { }
    public static void main(String[] args) throws SneezeException {
        try {
            throw new SneezeException();
        } catch (SneezeException | SniffleException e) {
            -------
            throw e;
        }
    }
}
```

A. `// leave line blank`
B. e = new Exception();
C. e = new RuntimeException();
D. e = new SneezeException();
E. e = new SniffleException();
F. None of the above; the code does not compile.

64. Which of the following are checked exceptions? (Choose all that apply.)

```java
class One extends RuntimeException{}
class Two extends Exception{}
class Three extends Error{}
class Four extends One{}
class Five extends Two{}
class Six extends Three{}
```

All class types that extend off of Exception are checked exceptions, Throwable really has two primary children,
Exception and Error.

What is considered a check exception is a construct that is really only enforced by the compiler, and the language
spec, there is nothing special about the class types such as Exception or Error or even RuntimeException.

A. One
B. `Two`
C. Three
D. Four
E. `Five`
F. Six

65. What is the output of the following?

```java
public class SnowStorm {
    static class Walk implements AutoCloseable {
        public void close() {
            throw new RuntimeException("snow");
        }
    }
    public static void main(String[] args) {
        try (Walk walk1 = new Walk(); Walk walk2 = new Walk();) {
            throw new RuntimeException("rain");
        } catch(Exception e) {
            System.out.println(e.getMessage() + " " + e.getSuppressed().length);
        }
    }
}
```

```java

```

A. rain 0
B. rain 1
C. rain 2
D. show 0
E. snow 1
F. snow 2
G. The code does not compile.

66. Fill in the blank: A class that implements **\*\*\*\***\_**\*\*\*\*** may be in a try-with-resource statement.
    (Choose all that apply.)

    Both the AutoCloseable which is the super interface and Closeable can be used in a try-with-resources

A. `AutoCloseable`
B. `Closeable`
C. Exception
D. RuntimeException
E. Serializable

67. Which pairs fill in the blanks? The close() method is not allowed to throw a(n) **\*\*\*\***\_**\*\*\*\*** in a
    class that implements **\*\*\*\***\_**\*\*\*\***. (Choose all that apply.)

The Closeable interface declares that the close method is idempotent, and also it is declared to throw IOException,
therefore we are not allowed to throw Exception from implementations of Closeable, while we certainly are allowed to
do so for implementations of AutoCloseable

A. Exception, AutoCloseable
B. `Exception, Closeable`
C. IllegalStateException, AutoCloseable
D. IllegalStateException, Closeable
E. IOException, AutoCloseable
F. IOException, Closeable

68. Which of the following CANNOT fill in the blank? (Choose all that apply.)

```java
private void readFromDatabase() throws SQLException {
}
public void read() throws SQLException {
    try {
        readFromDatabase();
    } catch (_____________ e) {
        throw e;
    }
}
```

Technically all the options here are possible lets examine them. First lets clear a few facts, the method read declares
that it throws SQLException while that is true there is nothing prohibiting us from never throwing anything in the first
place.

A. We can catch as Exception, and re-throw it, that is possible because the compile knows that we actually catch
SQLException because that is what is being declared to be thrown by readFromDatabase, meaning that the read method
re-declares that it throws back, possibly, all the checked exceptions that could possibly occur already, or we might
catch a runtime exception but that is too.

B. This is a special case of the option A, because we will only catch a RuntimeException leaving the checked ones to
bubble up, which is fine because the read method declares that it is re-throwing all the checked exceptions that the
method it calls - readFromDatabase does, in this case - SQLException

C. In this scenario we simply catch the exception directly in our catch block and re-throw it, the same one that is
being thrown, that is not much different than option A, however remember that we can also decide to never re-throw
and that would still be fine.

D. This one not possible, because we handle the one that is being thrown by the readFromDatabase - that would be
SQLException, however the compiler knows that there is no chance anything else can be thrown because the method that
we call never declared anything else could be thrown, besides the SQLException, if instead the method were to
declare that it throws Exception then the compiler will allow this multi-catch as it would have no way of knowing
which type will be thrown, since both the SQLException and the IOException share the same parent - Exception

E. This one similarly to option D, however it does multi-catch with a RuntimeException, for which are are not
required to catch, therefore the compiler will never complain because it has no way of knowing if an unchecked
exception might be thrown by calling this method, again SQLException and RuntimeException share no common
child-parent relationship in their hierarchy between themselves, therefore they are allowed in the multi catch

A. Exception
B. RuntimeException
C. SQLException
D. `SQLException | IOException`
E. SQLException | RuntimeException
F. `None of the above; all options are possible`

69. Which of the following is true when creating your own exception class?

We can create a new exception with a simple use of the extends keyword, and that is usually enough, we are not required
to do any additional work as the default constructors from Exception will be used, we can certainly provide our own and
override the existing behavior but that is not mandatory. We can also create any type and any number of - unchecked
RuntimeException or checked Exception.

A. One or more constructors must be coded.
B. Only checked exceptions may be created.
C. Only unchecked exceptions may be created.
D. The toString() method must be coded.
E. `None of the above.`
