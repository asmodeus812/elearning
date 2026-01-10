1. Which of the following statements about design principles and design patterns are true? (Choose all that apply.)

A. A design principle is focused on solving a specific commonly occurring problem.
B. Design principles and design patterns are the same thing.
C. `Design principles are often applied throughout an application, whereas design patterns are applied to solve specific problems.`
D. Design patterns can only be applied to static classes.
E. `Design principles and design patterns tend to produce code that is easier to maintain and easier for other developers to read.`

2. What is the result of the following code?

```java
public interface CanClimb {
    public abstract void climb();
}
public interface CanClimbTrees extends CanClimb {
}
public abstract class Chipmunk implements CanClimbTrees {
    public abstract void chew();
}
public class EasternChipmunk extends Chipmunk {
    public void chew() { System.out.println("Eastern Chipmunk is Chewing"); }
}
```

In this example the very first thought somebody might be having is that the way the method is defined in the interface
is actually wrong, which is not, by default all methods in the interface are abstract, we can omit or add the keyword
modifier but that does not interfere with the declaration of the interface. The more egregious mistake here is the fact
that the non-abstract class EasternChipmunk does not implement all inherited abstract methods from both the abstract
class it extends and the transitive ones from the interface

A. It compiles and runs without issue.
B. The code will not compile because of line 2.
C. The code will not compile because of line 4.
D. The code will not compile because of line 5.
E. `The code will not compile because of line 8.`
F. It compiles but throws an exception at runtime.

3. Which of the following are valid functional interfaces? (Choose all that apply.)

```java
public interface Climb {
    public int climb();
}
public abstract class Swim {
    public abstract Object swim(double speed, int duration);
}
public interface ArcticMountainClimb extends MountainClimb {
    public default int getSpeed();
}
public interface MountainClimb extends Climb {
}
```

There are only two interfaces here that have truly one non implemented methods, the Swim class might be considered
conceptually functional interface but if we attempt to annotate it with the FunctionalInterface annotation we will
quickly find out that it is not, and the compiler will throw error. That is because the functional interface as the name
suggests should not provide anything but behavior and abstract classes can hold state even though in this case it does
not. The other interface that has a default method does not provide an implementation for that method so it is actually
a compiler error.

A. `Climb`
B. Swim
C. ArcticMountainClimb
D. `MountainClimb`
E. None of these are valid functional interfaces.

4. Which of the following are valid lambda expressions? (Choose all that apply.)

The only true valid ones are the A and D, the rest have issues, which are not compatible with the lambda syntax.

B - Has a missing pair of brackets to surround the type arguments or parameters, since they are more than one.
C - Has a return statement but that is enforced to be implicit when no curly braces are present and is invalid in that state.
E - Missing brackets around the parameter arguments, error when the type and name are provided.
F - Missing body of the functional interface / lambda.
G - Missing the type declaration of the second argument, when at least on is present then all must be there.

A. `() -> ""`
B. x,y -> x+y
C. (Coyote y) -> return 0;
D. `(Camel c) -> {return;}`
E. Wolf w -> 39
F. () ->
G. (Animal z, m) -> a

5. What are some of the properties of using the singleton pattern? (Choose all that apply.)

A singleton type should be providing a private constructor that is only used internally to create the singleton the
very first time it is required, then that singleton object is stored internally that is it, ensuring there is only
one instance of that singleton for the entire duration of the program. Marking it as private only is also a good
practice, that way the singleton is accessible only through the getInstance method alone

A. Singleton object can be replaced with encapsulated setter method.
B. `Requires constructor of singleton class to be private.`
C. Singleton object must be named instance.
D. Singleton object may be private or protected.
E. `Ensures that there is only one instance of an object in memory.`
F. `Requires a public static method to retrieve the instance of the singleton.`

6. What is the result of the following class?

```java
import java.util.function.*;
public class Panda {
    int age;
    public static void main(String[] args) {
        Panda p1 = new Panda();
        p1.age = 1;
        check(p1, p -> p.age < 5); // h1
    }
    private static void check(Panda panda, Predicate<Panda> pred) { // h2
        String result = pred.test(panda) ? "match": "not match"; // h3
        System.out.print(result);
    }
}
```

The example is mostly straightforward, the structure is valid there are no compile time errors or issues here neither
are there any runtime ones, no null pointers are accessed etc. The example uses the short form of the lambda expression
when there is only one parameter and there is no type defined for it we can skip the surrounding brackets and directly
use the arrow syntax straight away like so - p -> p.age < 5. That is possibly the detail that might trip some people up

A. `match`
B. not match
C. Compiler error on line h1.
D. Compiler error on line h2.
E. Compile error on line h3.
F. A runtime exception is thrown

7. What changes need to be made to make the following immutable object pattern correct? (Choose all that apply.)

```java
import java.util.List;
public class Seal {
    String name;
    private final List<Seal> friends;
    public Seal(String name, List<Seal> friends) {
        this.name = name;
        this.friends = friends;
    }
    public String getName() { return name; }
    public List<Seal> getFriends() { return friends; }
}
```

There is no such thing as an Immutable interface in java that is a gotcha, there is an Immutable annotation but that is
not part of the official specification, and has nothing to do with this question. Adding setters is not a good idea if
we want to have this class really be treated as immutable. Changing the return from Seal to Object just to possibly
protect it from mutation is also bad practice, we should ensure that the Seal object is not mutable itself instead of
relying on tricks like those.

A. None; the immutable object pattern is properly implemented.
B. Have Seal implement the Immutable interface.
C. `Mark name final and private.`
D. Add setters for name and List<Seal> friends.
E. `Replace the getFriends() method with methods that do not give the caller direct access to the List<Seal> friends.`
F. Change the type of List<Seal> to List<Object>.
G. `Make a copy of the List<Seal> friends in the constructor.`
H. `Mark the Seal class final.`

8. Which of the following are true of interfaces? (Choose all that apply.)

Interfaces do not extend other classes they extend other interfaces, classes implement interfaces, also interfaces can
be extended so the first two options are really not right. Interfaces can contain default methods as of java 8, and they
can not be declared final otherwise it defeats the purpose of the interface

A. They can extend other classes.
B. They cannot be extended.
C. `They enable classes to have multiple inheritance.`
D. They can only contain abstract methods.
E. They can be declared final.
F. `All members of an interface are public.`

9. What changes need to be made to make the following singleton pattern correct? (Choose all that apply.)

```java
public class CheetahManager {
    public static CheetahManager cheetahManager;
    private CheetahManager() {}
    public static CheetahManager getCheetahManager() {
        if(cheetahManager == null) {
            cheetahManager = new CheetahManager();
        }
        return cheetahManager;
    }
}
```

In the example above to maintain proper conventional singleton structure, we have to at least ensure that the member
that holds the instance is private, naming the member variable and member method that operates on the instance is not
part of the pattern so renaming to getInstance, is NOT required, might be good practice, and on top of that to change
the implementation to use synchronized, that way we can ensure that if the instance does not exist and multiple threads
try to get the instance only one will ever manage to create it the very first time that prevents some nasty race
conditions.

A. None; the singleton pattern is properly implemented.
B. Rename cheetahManager to instance.
C. Rename getCheetahManager() to getInstance().
D. `Change the access modifier of cheetahManager from public to private.`
E. Mark cheetahManager final.
F. `Add synchronized to getCheetahManager().`

10. What is the result of the following code?

```java
public interface CanWalk {
    default void walk() { System.out.println("Walking"); }
}
public interface CanRun {
    public default void walk() { System.out.println("Walking"); }
    public abstract void run();
}
public interface CanSprint extends CanWalk, CanRun {
    void sprint();
}
```

In the example above we have 3 interfaces two of which define default methods correctly. The third one defines only one
abstract non-implemented method which is perfectly valid. Then it extends the other two interfaces however here comes
the issue. The walk method exists with a default implementation in both of the parent interfaces, that enforces the
compiler to either define non-default walk method in the CanSprint interface or to provide a new default implementation.

A. The code compiles without issue.
B. The code will not compile because of line 5.
C. The code will not compile because of line 6.
D. The code will not compile because of line 8.
E. `The code will not compile because of line 9.`

11. Which lambda can replace the MySecret class to return the same value? (Choose all that apply.)

```java
public interface Secret {
    String magic(double d);
}
public class MySecret implements Secret {
    public String magic(double d) {
        return "Poof";
    }
}
```

The examples below are mostly straightforward there are no hidden gotchas there are missing return statements in {}
blocks and missing semi-colons. The statements are well formed even though on single lines. The examples below that
declare a variable string called 'e' are also not correct, because the re-declare a variable of different type with the
same name as the lambda expression. One should take extra care with examples as these

A. `caller((e) -> "Poof");`
B. caller((e) -> {"Poof"});
C. caller((e) -> { String e = ""; "Poof" });
D. caller((e) -> { String e = ""; return "Poof"; });
E. caller((e) -> { String e = ""; return "Poof" });
F. `caller((e) -> { String f = ""; return "Poof"; });`

12. What is the result of the following code?

```java
public interface Climb {
    boolean isTooHigh(int height, int limit);
}
public class Climber {
    public static void main(String[] args) {
        check((h, l) -> h.toString(), 5); // x1
    }
    private static void check(Climb climb, int height) {
        if (climb.isTooHigh(height, 10)) // x2
        System.out.println("too high");
        else System.out.println("ok");
    }
}
```

The issue here is subtle the check method is called with a lambda of the functional interface, the lambda accepts two
primitive integers, however the implementation body of it tries to call toString on the height, which is quite
impossible, because auto-boxing to Integer will not take place, and will produce compiler error. If we were to first
cast that to Integer and then call to String, either way the return of that lambda function has to be boolean so even
with valid call to toString that is not returning boolean value anyway

A. ok
A. too high
A. `Compiler error on line x1.`
A. Compiler error on line x2.
A. Compiler error on a different line.
A. A runtime exception is thrown.

13. Which of the following are properties of classes that define immutable objects? (Choose all that apply.)

A. They don’t define any getter methods.
B. `All of the instance variables marked private and final.`
C. `They don’t define any setter methods.`
D. They mark all instance variables static.
E. `They prevent methods from being overridden.`
F. All getter methods are marked synchronized.

14. Which of the following statements can be inserted in the blank line so that the code will compile successfully? (Choose all that apply.)

```java
public interface CanHop {}
public class Frog implements CanHop {
    public static void main(String[] args) {
        _______________ frog = new TurtleFrog();
    }
}
public class BrazilianHornedFrog extends Frog {}
public class TurtleFrog extends Frog {}
```

The following is a good example of polymorphic behavior and types. The TurtleFrog is a Frog which CanHop and is an
Object ultimately, therefore we can certainly substitute the left hand side with these types

A. `Frog`
B. `TurtleFrog`
C. BrazilianHornedFrog
D. `CanHop`
E. `Object`
F. Long

15. Which of the following statements about polymorphism are true? (Choose all that apply.)

A. A reference to an object may be cast to a subclass of the object without an explicit cast.
B. `If a method takes a class that is the superclass of three different object references, then any of those objects may be passed as a parameter to the method.`
C. `A reference to an object may be cast to a superclass of the object without an explicit cast.`
D. All cast exceptions can be detected at compile time.
E. By defining a public instance method in the superclass, you guarantee that the specific method will be called in the parent class at runtime.

16. Choose the correct statement about the following code:

```java
public interface Herbivore {
    int amount = 10;
    public static void eatGrass();
    public int chew() {
        return 13;
    }
}
```

That one is an incredible bait. The most obvious thing to anyone here should me the 'member' variable amount, but that
is not a member by default in java interfaces all member variables are both static and public, therefore that amount
there is allowed. However a few lines down we have two methods first an 'abstract' static method, a static method
without a body which is not right, and a non-default abstract method chew with a body, both of those are wrong, both are
not correct

A. It compiles and runs without issue.
B. The code will not compile because of line 2.
C. The code will not compile because of line 3.
D. The code will not compile because of line 4.
E. The code will not compile because of lines 2 and 3.
F. `The code will not compile because of lines 3 and 4.`

17. Which of the following are properties of classes that are properly encapsulated as a JavaBean? (Choose all that apply.)

Java Bean is a standard for defining a reusable java object, those must not be confused with library classes which have
a looser less strict rules for defining them. Java beans are more akin to pojo or data transfer object. They are meant to be
very small collection of properties - member variables and methods, to hold some piece of data.

They provide getter and setters which usually do not do too much, besides some surface level validation of the user
data or simple transforms to initialize its internal state. Unlike library classes they do not pack too much business
level logic into their methods or bodies.

What they usually have is all instance member variables defined as private, optional getter/setter and public no-args
constructor at the very least. They could be defined to implement Serializable interface, but that is not strictly
required. The methods and variable names should follow the standard java naming conventions there are no special
restrictions or requirements for the definition of these types of java classes. Getters use getXXX pattern or isXXX for
boolean properties, and the setters follow the setXXX pattern, where XXX is the name of the property starting with upper
case letter. There are tools that use these naming rules to introspect the state of the java beans.

Events and constraints, the final part that makes a JavaBean complete is the event-constraint model, which defines that
a bean may expose the internal change of its property values through an even driven model, these changes can be vetoed
by throwing an exception by the listeners for these events too. See example below

```java
public class Person implements Serializable {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String name;                                // private field
    public Person() {}                                  // public no-arg constructor
    public String getName() { return name; }            // accessor defines the property
    public void setName(String newName) {               // setter mutates the property
        String old = this.name;
        this.name = newName;
        pcs.firePropertyChange("name", old, newName);   // bound property notification
    }
    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }
}
```

A. All instance variables are marked final.
B. `boolean instance variables are accessed with is or get.`
C. `All instance variables are marked private.`
D. They implement the JavaBean interface.
E. Variables are created using lazy instantiation.
F. `The first letter of the any getter/setter, after the get, set, or is prefix, must be uppercase.`

18. Which of the following statements about inheritance and object composition are correct? (Choose all that apply.)

The one that stands out as the most confusing here is the last one, there is a word in there 'always' that if it were
not in place the statement would have been correct, but saying 'always' is making it wrong, as it is not always the case
that compositions is better than inheritance. The rest are mostly obviously wrong, inheritance does not rely on the
has-a principle but the is-a, and the object composition pattern does not support any method overriding at runtime, or
compile time for that matter.

A. `Inheritance supports access to protected variables.`
B. `Object composition tends to promote greater code reuse than inheritance.`
C. Inheritance relies on the has‐a principle.
D. Object composition supports method overriding at runtime.
E. `Object composition requires a class variable to be declared public or accessible from a public method to be used by a class in a different package.`
F. Object composition is always preferred to inheritance.

19. Which three scenarios would best benefit from using a singleton pattern? (Choose all three.)

Creating read-only objects is the immutable pattern not directly related to singleton, while we can have a read-only
singleton that is not mandatory. Ensuring that objects are lazy initialized is another pattern that is again not
directly related to singleton, it might be used with singleton but is not the same thing. Allowing multiple instances to
be managed, technically the entire idea of singleton is to manage / create a single instance across the entire
application, so that is wrong.

The rest are the correct ones. The cache of objects is should generally be single cache instance that holds these
objects and allows us to extract objects from there. We also want to manage access to a log file through a singleton
that ensures that the log file is accessed by a single object / point of entry and there are no race write/read
conditions. And lastly singleton is often used to hold and provide access to application, configuration, files

A. Create read‐only objects that are thread‐safe.
B. `Manage a reusable cache of objects.`
C. Ensure that all objects are lazily instantiated.
D. `Manage write access to a log file.`
E. `Provide central access to application configuration data.`
F. Allow multiple instances of a static object to be managed in memory.

20. Choose the correct statement about the following code:

```java
public interface CanFly {
    void fly();
}
interface HasWings {
    public abstract Object getWingSpan();
}
abstract class Falcon implements CanFly, HasWings {
}
```

The example above might be a bit contrived but it is not that confusing. We have two interfaces one using the implicit
abstract keyword the other not, in either case the interfaces are defined properly. The abstract class that implements
both will inherit from them the two abstract methods and since it is marked as abstract it will just carry on those
methods, does not require any implementation whatsoever. If the class WAS NOT marked as abstract then we would have a
compiler error because the methods that have to be overridden from the two interfaces are actually missing

A. `It compiles without issue.`
B. The code will not compile because of line 2.
C. The code will not compile because of line 4.
D. The code will not compile because of line 5.
E. The code will not compile because of lines 2 and 5.
F. The code will not compile because the class Falcon doesn’t implement the interface methods.
