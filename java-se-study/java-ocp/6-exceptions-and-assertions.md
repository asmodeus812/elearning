# Throwable

This is the top level class which is used in pretty much all exception handling and error handling in the Java language,
used by the `JVM` itself, and all exceptions - checked and unchecked extend off of it. Further more the special type of
Error, which is mostly used by the `JVM`, internally, is also extending off of it.

Every thrown object from java Code must be a subclass of the `Throwable` class, or one of its sub-classes, exception
handling constructs in the language such as throw statements, throws clause, and the catch clause, deal only with
`Throwable` and its sub-classes. There are three important sub-classes of `Throwable` - `Error`, `Exception` and
`RuntimeException`.

The general hierarchy of the java exception model is such that: `Object -> Throwable -> Excetpion -> RuntimeException`

- `Exceptions` of type Exception in Java are known as check exceptions. If code can throw an Exception you must handle it
  using a catch block or declare that the method throws that exception forcing the caller of that method to handle that
  exception

- `RuntimeException` is a derived class of the Exception class. The exceptions deriving from `RuntimeException` are known as
  unchecked exceptions. It is optional to handle unchecked exceptions. If a code segment in a method can throw an
  unchecked exception, it is not mandatory to catch that exception or declare that exception in the throws clause of that
  method.

- `Error` - when the `JVM` detects a serious abnormal condition in the program, it raises an exception of type Error.
  Exception of type Error indicate an abnormal condition, in the program. There is no point in catching these exceptions
  and pretend nothing has happened. At this point if an Error exception occurs it is a really bad practice to do so! These
  errors signal that some irrecoverable state has been reached.

## Throwing

```java
public static void main(String []args) {
    if(args.length == 0) {
        // this branch of the code declares that a RuntimeException exception is thrown, as mentioned above these types
        // of exceptions are not mandatory to be handled, and the program will simply exit and the call stack will unwind
        // accordingly.
        throw new IllegalArgumentException("No input passed to echo command");
    }
    else {
        for(String str : args) {
            // command-line arguments are separated and passed as an array
            // print them by adding a space between the array elements
            System.out.print(str + " ");
        }
    }
```

Since there is no explicit user defined handler for the exception thrown above, the `JVM` will itself make sure that this
exception is caught at the moment the program terminates, the reason being, to at the very least log out the error, so
the user can obtain some sort of information about the invalid state that has occurred.

```java
public static void main(String [] args) {
    System.out.println("Type an integer in the console: ");
    Scanner consoleScanner = new Scanner(System.in);
    System.out.println("You typed the integer value: " + consoleScanner.nextInt());
}
```

Here is another example with an unchecked exception which is possible to occur in user code, the method `nextInt` which
tries to read the next integer from `stdin`, can throw an exception if the input is not a valid integral type, this means
that the method can throw an unchecked exception in that case. However this is also documented in the method
documentation, therefore the user code has the ability to, if desired, catch the exception, even though it is unchecked
and handle that case gracefully, instead of completely killing the program.

Another useful feature in the try-catch block is that an exception can be wrapped in another one in the catch block and
re-thrown, instead of handling it immediately in the catch block this is usually useful in practice, since swallowing an
exception (leaving the catch block empty, or adding some generic print statement which simply loses information about
the cause and reason for the exception is a bad idea). Wrapping an exception is a good way to preserve the initial
cause, if it can not be handled at the time of the catch statement. And can be used to be wrapped in a more generic
exception which other layers in the program execution know how to handle.

Chain throwing exceptions is most usually done to do what is called exception translation - translating internal system
exceptions coming from libraries or the java ones, to a more generic business level exception which is easier to handle
and control.

## Catching

Besides creating exceptions the user code can also catch them, this is the companion action to throwing exceptions. The
example below extends the example from above, which reads integer from `stdin`, and tries to correctly handle the case
when the integer is not an integer, or it is unable to parse a valid integer from `stdin`. In this case the program will
simply just print some message on `stdout`, but in a more robust solution, it can be put in a `while(true)` loop and require
a user entry until a valid integral type is read from `stdin`.

```java
public static void main(String [] args) {
    System.out.println("Type an integer in the console: ");
    Scanner consoleScanner = new Scanner(System.in);
    try {
        System.out.println("You typed the integer value: " + consoleScanner.nextInt());
    } catch(InputMismatchException ime) {
        // nextInt() throws InputMismatchException in case anything other than an integer is typed in the console; so
        // handle it, this is crude example since the exception is swallowed, and some very generic error is printed out
        // in practice this is not a good idea, since information is being lost, namely the information from the
        // exception, or that an exception is occurred
        System.out.println("Error: You typed some text that is not an integer value...");
    }
}
```

The try-catch block has two elements to it, the code in the try block will be the one which will be examined for
exceptions, once an exception occurs, the code in the catch block will be triggered, and if the exception in the catch
block matches the catch exception statements, then whichever matches will be invoked. Note that multiple catch
statements are allowed, it is also possible that no exception is caught, if none of the catch statements match the
exception being thrown. If in the example above, something else than `InputMismatchException` then the catch block will
not be triggered, and the exception will be handled by the `JVM` itself

### Multi-catch catching

```java
public static void main(String [] args) {
    String integerStr = "non-integral-type";
    System.out.println("The string to scan integer from it is: " + integerStr);
    Scanner consoleScanner = new Scanner(integerStr);
    try {
        System.out.println("The integer value scanned from string is: " +
            consoleScanner.nextInt());
    } catch(InputMismatchException ime) {
        System.out.println("Error: Cannot scan an integer from the given string");
    } catch(NoSuchElementException nsee) {
        System.out.println("Error: Cannot scan an integer from the given string");
    } catch(IllegalStateException ise) {
        System.out.println("Error: nextInt() called on a closed Scanner object");
    }
}
```

In the example above, calling `nextInt`, will fail with `NoSuchElementException`, why is that ? Simple, because the
scanner is initialized with an invalid string from which to read/parse int, in this case a string `"non-integral-type"`.
This also shows that the order in which the exceptions are caught also matters, in this case the
`InputMismatchException` will not trigger before the `NoSuchElementException`, however `InputMismatchException` extends
from `NoSuchElementException`, therefore it is not possible two do the following

```java
// note that the catch statement order matters, especially when the exceptions are such that they have intersecting and
// common hierarchy, in this case the NoSuchElementException is parent class of the InputMismatchException
try {
    System.out.println("The integer value scanned from string is: "
        + consoleScanner.nextInt());
} catch(NoSuchElementException nsee) {
    System.out.println("Error: Cannot scan an integer from the given string");
} catch(InputMismatchException ime) {
    System.out.println("Error: Cannot scan an integer from the given string");
}
```

Note that the `NoSuchElementException` which is a super class of the `InputMismatchException` is caught first, that is not
possible, the child / sub classes must come first, otherwise this is considered a compile time error, why is that ? If
that was not an error during run-time the exception that will be caught will always be `NoSuchElementException`, even if
the actual instance is `InputMismatchException`, meaning that this is potentially a logical error, and there is no way to
catch that during run-time, that is why the compiler is helpful, and prompts with an error during compilation, suggesting
something is logically wrong, not syntactically.

`When providing multiple catch handlers, handle specific exceptions before handling general exceptions. If you provide a
derived class exception, catch handler after a base class exception handler, the compiler will issue a compile time
error.`

The language also provides something known as multi catch blocks, which allows one to express the catch statement such that it matches multiple exception in a single catch statement.

```java
// note that a binary or operator (pipe) is used, however, this is technically, a compiler error because in multi-catch
// block the exceptions can not be of related types, in this case one is a parent of the other
try {
    System.out.println("The integer value scanned from string is: " +
        consoleScanner.nextInt());
} catch(NoSuchElementException | IllegalStateException multie) {
    // this is not a valid multi-catch, since the two exceptions are related,
    System.out.println("Error: An error occured while attempting to scan the integer");
}
```

`In multi-catch block, one cannot combine catch handlers for two exceptions that share a base - derived class
relationship. Only unrelated exceptions can be listed and combined in a multi-catch.`

One might notice that both multiple catch statements and multi-catch statements fill a very similar role, which one
makes more sense, depends on the situation. If the exceptions are thrown for the same or similar reason, then a
multi-catch makes more sense, makes code more readable and maintainable. However if the different exceptions are handled
very differently in their catch blocks, then a multiple catch statements approach is required

There is one more technical detail or caveat that needs to be taken a look at for a multi-catch block what is the
type of the variable that when a pipe is used. The general rule of thumb is that the variable will be the first
common class type in the hierarchy between those two classes, as we have said `the actual class types used in the
multi-catch must be siblings but not have parent-child relationship`, however being siblings they certainly share a
common parent, therefore the type of the variable will be the closest shared parent between the two types

```java
// for example we can have the following multi catch block that intercepts both a runtime exception and a checked
// exception they both share a parent and that is the Exception class
IllegalStateException | IOException -> Exception
RuntimeException | IOException -> Exception

// however say we have this example where we catch Error and RuntimeException in this case the common parent is
// Throwable class actually
Error | RuntimeException -> Throwable
```

`The variable in a multi-catch pipe block is effectively final, it can NOT be re-assigned, we can only read/call
methods on it`

### General catching

As already discussed all exceptions extend from the `Throwable` class, and further more from the Exception class as
well, that means that in the catch statement one can specify a higher level Exception type, `Throwable` even, to make
sure all types of exceptions which a given statement can emit within a try-catch block are caught, not the best idea,
but sometimes it can be useful. For example many types of different exceptions are being thrown when one interacts with
the I/O API operations. If one tries to handle each and every case, trying to be as pedantic as possible, the code will
quickly become unreadable and non really maintainable, in in such general situations, it is simply better to catch
`Exception` type instead, for the cases that are not as important, and a general error can be reported.

```java
try {
    System.out.println("You typed the integer value: " + consoleScanner.nextInt());
} catch(InputMismatchException ime) {
    // if something other than integer is typed, we'll get this exception, so handle it
    System.out.println("Error: You typed some text that is not an integer value...");
} catch(Exception e) {
    // catch IllegalStateException, and anything else here which is unlikely to occur...
    System.out.println("Error: Encountered an exception and could not read an integer from
        the console... ");
}
```

So in the example above the most important exception is being handled, exclusively, which is the `InputMismatchException`,
which will occur in case the scanner can not read a valid integral type on the input, however everything else, is
relegated to just the `catch(Exception)` block

## Finalizing

All of the examples above have one big issue, that is the fact that the Scanner object is not closed, however this can
be mitigated by calling the close method on the scanner object, this can be done in several ways, but the try-catch
block in java have two main methods to achieve this - try-with-resources (introduced in Java 8) and finally (the classic
approach)

### Finally

The finally block is a block which is executed, regardless of the fact that an exception is occurred. The code inside
the finally block will be executed AFTER the code in both the try and catch, even if an exception is not caught, the
finally block will always be executed. This provides a deferred safe way to clean up resources after their usage has
expired

```java
System.out.println("Type an integer in the console: ");
Scanner consoleScanner = new Scanner(System.in);
try {
    System.out.println("You typed the integer value: " + consoleScanner.nextInt());
} catch(Exception e) {
    // call all other exceptions here ...
    System.out.println("Error: Encountered an exception and could not read an
        integer from the console... ");
    System.out.println("Exiting the program - restart and try the program again!");
} finally {
    // note that close itself, as documented can throw an IllegalStateException, in case one tries to call close
    // multiple times on the same scanner object
    System.out.println("Done reading the integer... closing the Scanner");
    consoleScanner.close();

    // if the call to close above, throws, anything below it will not be executed, that is why it is not recommended to
    // use the old fashioned finally block to close, closeable objects, since that could still cause a resource leak in
    // some cases
    System.out.println("Will not be called in caes close() above throws an exception");
}
```

It is also possible to have internal try-catch inside a finally block, that is actually the old school way of closing
file stream objects, since their close method, does in fact throw a checked exception and it has to be handled, it is
not optional. Note that however

`If a call to System.exit() is done inside a method, it will abnormally terminate the program. So if the calling method
has any finally blocks, they will not be called, and resources may leak. For this reason it is a bad practice to call
System.exit(), to terminate or exit from a program`

Some very weird caveats that can occur within the usage of finally block

```java
// since the finally block is always executed, this will `always` return false, therefore it is not really advised to
// return anything from finally blocks
boolean returnTest() {
    try {
        return true;
    }
    finally {
        return false;
    }
}
```

### Try-with-resources

This is a new feature in Java 8, which is meant to circumvent all the usual boilerplate and possible issues that might
occur using the usual finally syntax, the try-with-resources used to close all resources which extend from the
`AutoCloseable` interface, also introduced in Java 8, this interface signals to the `JVM`, that a resource is
`auto-closeable`, and the resource is declared within the try-with-resources statement block the close method of that
resource will be automatically closed.

To go on a bit of a tangent, with the introduction of the try-with-resources construct in the language, the language
specification needed a less restrictive interface to represent an object or resource that can be closed safely, and
then the language specification introduced the `AutoCloseable` interface that is  a parent of the `Closeable` interface

One might wonder, if the logical structure should have been that `Closeable` remains the super class and `AutoCloseable`
becomes the child specialization. That certainly makes sense however the `Closeable` interface declares its method
close to throw `IOException`, and the try-with-resource structure needed a broader exception type to throw, they could
not modify the existing `Closeable` interface due to backwards compatibility and they could not declare the new close
method to be throwing Exception if it were child of `Closeable` interface because that breaks the rules for the throws
declaration when overriding a method.

Therefore the only valid solution was for the new `AutoCloseable` interface to become the parent of the old `Closeable`
interface, and retroactively allow try-with-resources to work with both `AutoCloseable` and `Closeable` interfaces.

`Close method of Closeable is also required to be idempotent - meaning that the close method can be called more
than one time without introducing additional state transitions in the program, compared to the first call, while the
close method in AutoCloseable does not have this restriction. In plain English, if a close method is called N number
of times the program should not perform any differently compared to the method being called N + 1 times, where N is
at least 1`

The syntax of the try-with-resources is quite simple, the resources are declared and initialized in the try block,
multiple ones can be declared by using a semi colon to split them up.

```java
try(Scanner consoleScanner = new Scanner(System.in)) {
    System.out.println("You typed the integer value: " + consoleScanner.nextInt());
} catch(Exception e) {
    // catch all other exceptions here ... some that might occur from calling close too
    System.out.println("Error: Encountered an exception and could not read an
        integer from the console... ");
    System.out.println("Exiting the program - restart and try the program again!");
}
```

The syntax rules for the resources inside the try-with-resources block are quite clear resources are split with a
semi-colon and an optional semi-colon can be present for the last resource but that is not mandatory or required by
the compiler or the language. When only one resource entry is present no semi-colon delimiter is required in the
try-with-resources block

Note the way the resource is being declared, in the example above there is no finally block, however internally the
compiler will translate this into an actual try-catch-finally block when during the code generation phase, the
try-with-resources is mostly a syntax sugar for a regular finally block, this however makes sure that the code is
functionally correct, and there is no logical issues.

To declare more than one resources within the try-with-resource statement as mentioned above one can use a `;` to
separate them, all of them are explicitly defined as effectively final

```java
// note that the two declarations are split with a semi-colon, in the try-with-resources statement
try (ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(zipFileName));
                                FileInputStream fileIn = new FileInputStream(fileName)) {
    // putNextEntry can throw IOException
    zipFile.putNextEntry(new ZipEntry(fileName));
    // the variable to keep track of number of bytes sucessfully read
    // copy the contents of the input file into the zip file
    int lenRead = 0;
    while((lenRead = fileIn.read(buffer)) > 0) { // read can throw IOException
        zipFile.write(buffer, 0, lenRead); // write can throw IOException
    }
    // the streams will be closed automatically because they are within try-with-
    // resources statement
}
```

Note that all resources declared within the try-with-resources block are effectively final, meaning that they can not be
re-assigned, this is because the original reference will be lost, and the resources would effectively leak. However what
is possible is the following

```java
// note that in this example  the console scanner is declared and initialized outside the try-with-resources block,
// however another variable captures the original reference in the try-with-resources block effectively doing the same as
// the example above, but it does show that something like that is possible and not considered an error.
Scanner consoleScanner = new Scanner(System.in);
try (Scanner scan = consoleScanner) {
    // use the scan instance
} catch (Exception e) {
    // do some exception handling
}
```

The main benefit of the try-with-resources statement is that it simplifies the code and makes it less verbose, however
it also ensures correctness, by not having to manually provide finally blocks which can be either forgotten or
functionally or logically wrong.

Note that it is also possible to provide a try-with-resources block that has no catch statement, this is not really
recommended however it is allowed in the language

```java
try(Scanner consoleScanner = new Scanner(System.in)) {
    System.out.println("You typed the integer value: " + consoleScanner.nextInt());
}
```

The scanner.close method does not throw any exceptions (it does not declare them in the close method signature)
therefore we are NOT required to have a catch block, BUT that does not mean that this is the case for all resources
that are declared in the try-with-resources. More details on these rules below

`If a finally and try-with-resources are used together, the resources inside the try-with-resource statement will be
closed BEFORE the finally block is called, meaning that closing them again in the finally block is most definitely wrong
and undefined behavior, they will likely throw since the close method is already closed, in that case the finally block
can be used to log or notify the outside world that all resources are cleaned up correctly and finalized.`

To add a bit more technical context to the try-with-resources structure, first as we already stated the
`AutoCloseable` interface can be implemented so the close method is automatically called by the compiler, but how is
that done, well first the resources that are declared in the try-with-resources structure are wrapped by the
compiler in a nested try-catch-finally blocks, each statement in the try-with-resource block becomes a nested
try-catch-finally, where

- in the try part the expression that creates the resource is called
- in the catch block any exceptions are attempted to be caught, not related to the close method
- in the finally the close method is called, wrapped around a try-catch block or added as suppressed

It is important to note here is that due to the rules of method overriding it is possible for a class to
implement the `AutoCloseable` interface and declare the close method as such that it does throw or does NOT throw,
therefore there are cases where the try-with-resources has to have a catch block or does not require one, that
depends on what the close method that is getting overridden specified in its declaration

What does the compiled structure that creates the try-with-resources block look like, after the following example which contains 3 resources that all implement the AutoCloseable interface

```java
void loadUsers(DataSource ds) throws SQLException {
    String sql = "select id, name from users";
    try (Connection con = ds.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
        }
    }
    // If body throws: you see that exception.
    // If close() throws and body didn't: you see that close exception.
    // If both throw: close exception is SUPPRESSED on the body exception.
}
```

What the compiler converts the calls from above, approximately, you can see that the calls are actually nested, in
reverse order, so are the close method calls. If a close method throws in an inner finally it will bubble upwards to
the outer catch, which will trigger the outer finally and so on and so forth, until we reach our own catch block
which wraps the entire thing from the outside.

```java
void loadUsers(DataSource ds) throws SQLException {
    Connection con = ds.getConnection();
    Throwable primary1 = null;
    try {
        PreparedStatement ps = con.prepareStatement("select id, name from users");
        Throwable primary2 = null;
        try {
            ResultSet rs = ps.executeQuery();
            Throwable primary3 = null;
            try {
                while (rs.next()) {
                    // work is being done
                }
            } catch (Throwable t3) {
                primary3 = t3;
                throw t3;                      // (may throw -> by next())
            } finally {
                if (rs != null) {
                    if (primary3 != null) {
                        try { rs.close(); }
                        catch (Throwable close3) { primary3.addSuppressed(close3); }
                    } else {
                        rs.close();            // may throw → bubbles outward
                    }
                }
            }
        } catch (Throwable t2) {
            primary2 = t2;
            throw t2;
        } finally {
            if (ps != null) {
                if (primary2 != null) {
                    try { ps.close(); }
                    catch (Throwable close2) { primary2.addSuppressed(close2); }
                } else {
                    ps.close();                // may throw → bubbles outward
                }
            }
        }
    } catch (Throwable t1) {
        primary1 = t1;
        throw t1;
    } finally {
        if (con != null) {
            if (primary1 != null) {
                try { con.close(); }
                catch (Throwable close1) { primary1.addSuppressed(close1); }
            } else {
                con.close();                    // may throw → bubbles to caller
            }
        }
    }
}
```

So to make long story short, the try-with-resources wraps the resource calls vertically, in reverse order of their
declaration in the try block. That way we can close the resources in reverse of their declaration, and finally all
of the exceptions that have occurred will bubble up to our outer most catch block

`Remember that when a method is overridden and it declares that Exception of certain type is thrown, we are allowed
to either - declare the same exception types, narrower exception types, or no exception types at all, but we are NOT
allowed to declare a broader exception types being thrown from that method`

The reason that works, is due to the Liskov substitution principle all of the classes compiled against the parent
super type will still be correct because the sub-type will either declare the same, narrower or no exceptions being
thrown from that method at all.

```java
// all of these are allowed for a class that implements AutoCloseable, as you can see we never declare that the
// methods throw a broader type of exception, always the types that are being thrown are compatible with what is
// declared in the signature of the method close in the AutoCloseable interface.
public void close() {}
public void close() throws IOException {}
public void close() throws Exception, IOException {}
public void close() throws Exception, IOException, FileNotFoundException {}
public void close() throws FileNotFoundException, InvalidClassException {}
```

One caveat that you may notice is that we can also do that - `Exception, IOException, FileNotFoundException` - while
that is valid it offers little value, since `FileNotFoundException` is a child of `IOException` which itself is a
child of Exception, that is not a compiler error, rather maybe a logical one, it would be more meaningful to provide
different exceptions that are siblings in the context of the original one in this case Exception, such as -
`FileNotFoundException`, `InvalidClassException`, those are children of Exception but do not share the same
hierarchy.

Another note is that we can declare as many `RuntimeException` as we want, as those are not mandatory to be caught
so the compiler will not complain, further more that is a good way to document our code so external callers know
what exceptions might be expected to be thrown their way.

## Throws

The other component of the Exception framework model in Java, is the throws clause, this clause defines that a certain
method throws an exception, if a method throws an unchecked exception using the throws clause is optional, however if a
method is throwing a checked exception the throws clause is not optional, and it has to declare all the checked
exceptions that this method might throw. It is usually a good practice to also document which exception is thrown from a
method, based on which condition or erroneous state.

```java
// this demonstrates how a checked exception is declared with the throws clause, the exception is propagated from the
// new File() method, which itself declares throws clause, in this case the exception is not handled in the main method,
// therefore it is propagated outisde of the main method, however it has to be declared that the main method throws,
// otherwise a compiler error will occur
public static void main(String []args) throws FileNotFoundException {
    System.out.println("Reading an integer from the file 'integer.txt': ");
    Scanner consoleScanner = new Scanner(new File("integer.txt"));
    System.out.println("You typed the integer value: " + consoleScanner.nextInt());
}
```

`When a method which declares at least one checked exception is used through the throws clause, within an outer method,
the outer method has only two possibilities - either handle the exception or simply re-declare the throws clause so
another method which invokes it will handle it or re-throw it.

The throws clause has some additional properties which need to be considered, one of which is what happens if a base
method declares throws clause, and a method in a sub-class has to override it. The rule of thumb, and generally the
accepted approach is that the contract from the base class HAS to be adhered to, it is the contract after all, so if a
sub-class overrides a method, it can NOT change the throws clause such that more general checked exceptions are thrown
from the overridden method, and it can not add more checked exceptions to be thrown form the overridden method, both of
these actions result in compiler error (however a checked exception can be changed from the base method, only if the
checked exception defined in the overriding method is part of the hierarchy, or in other words is a sub-type of the one
defined in the base method)

```java
interface IntReader {
    int readIntFromFile() throws IOException;
}
class InvalidThrowsClause implements IntReader {
    // this is allowed, changing the type of the checked exception with another checked exception, only if it is
    // part of the type hierarchy is possible, in this case FileNotFoundException extends from IOException
    public int readIntFromFile() throws FileNotFoundException {
        Scanner consoleScanner = new Scanner(new File("integer.txt"));
        return consoleScanner.nextInt();
    }
}
```

`The throws declaration in an overridden method can only be changed or modified to add unchecked exception to the list
of exceptions being thrown, any modification to the list of the checked exception where the checked exception is not a
sub-type of the original one being thrown will result in a compiler error`

## Documentation

It is as mentioned already a good practice to use the @throws `JavaDoc` tag, to document the specific situations or causes
in which an exception - checked or unchecked exception might be thrown from a method. Here is an actual example from the
actual implementation of the `nextInt` method form Scanner

```java
/**
* Scans the next token of the input as an int.
*
* <p> An invocation of this method of the form
* nextInt() behaves in exactly the same way as the
* invocation nextInt(radix), where radix
* is the default radix of this scanner.
*
* @return the int scanned from the input
* @throws InputMismatchException
* if the next token does not match the Integer
* regular expression, or is out of range
* @throws NoSuchElementException if input is exhausted
* @throws IllegalStateException if this scanner is closed
*/
public int nextInt() {
    return nextInt(defaultRadix);
}
```

The way the exceptions are listed, by convention is in alphabetical order, when a method can throw more than one
exception in the documentation. They are not listed by severity or in base - child class relationship

## Miscellaneous

- If a method does not have a throws clause, ti does not mean it cannot throw any exceptions; it just means it cannot
  throw any checked exceptions.

- Static block initializers cannot throw any check exceptions, this is because static initialization blocks are
  invoked when the class is loaded, so there is no way to handle the thrown exceptions in the caller. Further more there
  is no way to declare the checked exceptions in a throws clause using static initializer

- Non-static initializer blocks can throw checked exceptions however all the constructors should declare those
  exceptions, in their throws clause. This is because the compiler merges the code in the non-static initalizer block and
  the constructors during the code generation phase, hence the throws clause of the constructor can be used for declaring
  the checked exceptions that a non-static init block can throw.

- An overriding method cannot declare more checked exceptions in the throws clause than the list of exceptions
  declared in the throws clause of the base method

- An overriding method can declare more specific exceptions than the exception listed in the throws clause of the base
  method; in other words, one can still declare derived exception in the throws clause of the overriding method.

- If a method is declared in two or more interfaces, and if that method declares to throw different exceptions in the
  throws clause, the method implementations must declare and list all of these exception in their throws clause, the
  methods throws clauses are effectively merged together

## User-exceptions

Custom exceptions can be declared by users by extending either from the `Exception` class or the `RuntimeException`, it is
bad practice to extend from the `Throwable` or `Error` classes. As already mentioned, based on the type of exception -
checked or unchecked, one extends from `Exception` or `RuntimeException` respectively

The Exception class and by proxy the `RuntimeException` provide the following constructors

| Method                       | Description                                                                                                                                                           |
| ---------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Exception()                  | Default constructor of the Exception class with no additional (or detailed) information on the exception.                                                             |
| Exception(String)            | Constructor that takes a detailed information string about the constructor as an argument.                                                                            |
| Exception(String, Throwable) | In addition to a detailed information string as an argument, this exception constructor takes the cause of the exception (which is another exception) as an argument. |
| Exception(Throwable)         | Constructor that takes the cause of the exception as an argument.                                                                                                     |

| Method                      | Description                                                                                                                                                                                                                                                                                                                                    |
| --------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| String getMessage()         | Returns the detailed message (passed as a string when the exception was created).                                                                                                                                                                                                                                                              |
| Throwable getCause()        | Returns the cause of the exception (if any, or else returns null).                                                                                                                                                                                                                                                                             |
| Throwable[] getSuppressed() | Returns the list of suppressed exceptions (typically caused when using a try-with-resources statement) as an array.                                                                                                                                                                                                                            |
| void printStackTrace()      | Prints the stack trace (i.e., the list of method calls with relevant line numbers) to the console (standard error stream). If the cause of an exception (which is another exception object) is available in the exception, then that information will also be printed. Further, if there are any suppressed exceptions, they are also printed. |

```java
// the class declaration is an example of a custom user exception, which is in this case unchecked one, since it extends
// from the RuntimeException, by default the custom user exceptions are not required to provide any body, since the parent
// class RuntimeException and Exception in this case have default non-arg constructors
class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
```

## Assertions

The assert statement is used to check or test your assumptions about the program. The keyword, assert provides support
for assertions in Java. Each assertion statement contains a Boolean expression. If the result of the Boolean expression
is true, it means the assumption is true, so nothing happens. However if the Boolean result is false, then the
assumption you had about the program holds no more, and the `AssertionError` is thrown. Remember that the Error class and
its derived classes indicate serious `runtime` errors and are not meant to be handled. In the same way, if an
`AssertionError` is thrown the best course of action is not to catch the exception and to allow the program to terminate.
After that the assertion has to be examined, for the reason it failed.

Asserts are quite useful tool for ones program, it allows users to make certain assumptions in the code, and that is
quite helpful to discover when these assumptions fail.

A very important detail to remember is that assertions are by default disabled in the runtime. To enable them use the
`-ea` switch (or its longer form of `-enableasserts`). To disable assertions at `runtime` use a `-da` switch. If
assertions are disabled by default at runtime then what is the use of `-da` switch ? There are many uses. For example,
if you want to enable assertions for all classes within a given package and want to disable asserts in a specific class
in that package. Then a `-da` switch is useful.

| Command-Line Argument | Short Description                                                                                       |
| --------------------- | ------------------------------------------------------------------------------------------------------- |
| -ea                   | Enables assertions by default (except system classes).                                                  |
| -ea:<class name>      | Enables assertions for the given class name.                                                            |
| -ea:<package name>... | Enables assertions in all the members of the given package <package name>.                              |
| -ea:...               | Enable assertions in the given unnamed package.                                                         |
| -esa                  | Short for -enablesystemsassertions; enables assertions in system classes. This option is rarely used.   |
| -da                   | Disable assertions by default (except system classes).                                                  |
| -da:<class name>      | Disable assertions for the given class name.                                                            |
| -da:<package name>... | Disables assertions in all the members of the given package <package name>.                             |
| -da:...               | Disable assertions in the given unnamed package.                                                        |
| -dsa                  | Short for -disablesystemsassertions; disables assertions in system classes. This option is rarely used. |

The assert keyword in java can be invoked with the short form and the long form, the short form is just the assert
followed by a condition, that has to be met otherwise `AssertionError` will be thrown at runtime. The format of the
assert is -> assert {condition} : {message} where the condition has to be something that we assert has to be `TRUE`,
if it is `FALSE` then the message will be shown/set in the `AssertionError`. The condition can be wrapped in
brackets just as like if we invoking a function - assert(condition) or assert condition, and has to be a valid
condition that evaluates to `TRUE` or `FALSE`, in other words has to be boolean condition construct

`Assertions are a runtime construct and are not evaluated at compile time, the only validation that is done at
compile time by the compiler is that the condition to the assert is of boolean type. Assertions are disabled by
default and and must be enabled explicitly for the entire application or on a per-package or per-class basis`

# Summary

Try-catch and throw statements

- When an exception is thrown from a try block, the `JVM` looks for a matching catch handler from the list of catch
  handlers in the method call-chain. If no matching handler is found, that unhandled exception will result in crashing the
  application.

- While providing multiple exception handlers (stacked catch handlers), specific exception handlers should be provided
  before general exception handlers.

- You can programatically access the stack trace using the methods such as `printStackTrace()` and `getStackTrace()`,
  which can be called on any exception object.

Catch, multi-catch, and finally

- A try block can have multiple catch handlers. If the cause of two or more exceptions is similar, and the handling
  code is also similar, you can consider combining the handlers and make it into a multi-catch block.

- A catch block should either handle the exception or `rethrow` it. To hide or swallow an exception by catching an
  exception and doing nothing is really a bad practice.

- You can wrap one exception and throw it as another exception. These two exceptions become chained exceptions. From
  the thrown exception, you can get the cause of the exception.

- The code inside a finally block will be executed irrespective of whether a try block has successfully executed or
  resulted in an exception.

Try-with-resources statement

- Forgetting to release resources by explicitly calling the `close()` method is a common mistake. You can use a
  try-with-resources statement to simplify your code and auto-close resources.

- You can auto-close multiple resources within a try-with-resources statement. These resources need to be separated by
  semicolons in the try-with-resources statement header.

- If a try block throws an exception, and a finally block also throws exception(s), then the exceptions thrown in the
  finally block will be added as suppressed exceptions to the exception that gets thrown out of the try block to the
  caller.

Custom exceptions

- It is recommended that you derive custom exceptions from either the Exception or `RuntimeException` class.

- A method's throws clause is part of the contract that its overriding methods in derived classes should obey.

- An overriding method can provide the same throw clause as the base method's throws clause or a more specific throws
  clause than the base method's throws clause.

- The overriding method cannot provide a more general throws clause or declare to throw additional checked exceptions
  when compared to the base method's throws clause.

- For a resource to be usable in a try-with-resources statement, the class of that resource must implement the
  `java.lang.AutoCloseable` interface and define the `close()` method.

Invariant with asserts

- Assertions are condition checks in the program and should be used for explicitly checking the assumptions you make
  while writing programs.

- The assert statement is of two forms: one that takes a Boolean argument and one that takes an additional string
  argument.

- If the Boolean condition given in the assert argument fails (i.e., evaluates to false), the program will terminate
  after throwing an `AssertionError`. It is not advisable to catch and recover from when an `AssertionError` is thrown by
  the program.

- By default, assertions are disabled at runtime. You can use the command-line arguments of -ea (for enabling asserts)
  and `-da` (for disabling asserts) and their variants when you invoke the `JVM`.
