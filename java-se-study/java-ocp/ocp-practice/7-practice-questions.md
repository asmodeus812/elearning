1. How many lines of the following program contain compilation errors?

```java
class Cinema {
    private String name;
    public Cinema(String name) {
        this.name = name;
    }
}
public class Movie extends Cinema {
    public Movie(String movie) {
    }
    public static void main(String[] showing) {
        System.out.print(new Movie("Another Trilogy").name);
    }
}
```

There are really two issues here the Cinema class has it default constructor overridden, and the Movie class must
call the super constructor correctly, by default all child classes call the super constructor of the parent class,
but that is implicit in cases where the default constructor is called, in cases where the default constructor does
not exist, the compiler does not know how to call the existing constructor/s.

The second issue is of course accessing the private member of the Cinema from another class, even if Movie extends
Cinema the member is private therefore only accessible to units declared in the Cinema file / class, not outside of
it,

A. None
B. One
C. `Two`
D. Three

2. Which modifier can be applied to an abstract interface method?

Methods are only public in an interface, otherwise interfaces would be pointless if we could declare methods other
than public, the entire idea of interfaces is that the contract is public in the first place.

A. protected
B. static
C. final
D. `public`

3. What is the output of the following application?

```java
public class Song {
    public void playMusic() {
        System.out.print("Play!");
    }
    private static int playMusic() {
        System.out.print("Music!");
    }
    public static void main(String[] tracks) {
        new Song().playMusic();
    }
}
```

In the language it is NOT possible to declare two methods with the same signature and name, regardless of their
modifiers - static or not. Since it is possible to call a static method from an instance of a given class the
compiler will not know which method to call if that was possible

A. Play!
B. Music!
C. `The code does not compile.`
D. The code compiles but the answer cannot be determined until runtime.

4. Which of the following statements about inheritance is true?

A. `Inheritance allows objects to access commonly used attributes and methods.`
B. Inheritance always leads to simpler code.
C. All primitives and objects inherit a set of methods.
D. Inheritance allows you to write methods that reference themselves.

5. Given the class declaration below, which value CANNOT be inserted into the blank line that would allow the code to compile?

```java
interface Pet {}
public class Canine implements Pet {
    public ___________ getDoggy() {
        return this;
    }
}
```

The only class that does not share a common hierarchy with this particular type Canine is Class, all of the rest are
all valid co-variant types that can be returned from this method

A. `Class`
B. Pet
C. Canine
D. Object

6. Imagine you are working with another team to build an application. You are developing code that uses a class that the
   other team has not finished writing yet. Which element of Java would best facilitate this development, allowing easy
   integration once the other team’s code is complete?

If a common interface exists that the other team implements we can program our implementation around that common
interface while the actual implementation is being developed that way both teams are free to work at their pace. The
final result however has to adhere to the agreed upon interface and it behavior

A. An abstract class
B. `An interface`
C. static methods
D. An access modifier

7. What is the output of the following application?

```java
class Automobile {
    private final String drive() {
        return "Driving vehicle";
    }
}
class Car extends Automobile {
    protected String drive() {
        return "Driving car";
    }
}
public class ElectricCar extends Car {
    public final String drive() {
        return "Driving electric car";
    }
    public static void main(String[] wheels) {
        final Car car = new ElectricCar();
        System.out.print(car.drive());
    }
}
```

This code will not compile because Car class tries to override a final method called drive.

A. Driving vehicle
B. Driving electric car
C. Driving car
D. `The code does not compile.`

8. Which of the following statements about inheritance is correct?

These options may sound sound counter intuitive but generally java allows multiple inheritance only when inheriting
behavior like classes also called - interfaces. In all other cases java does not allow multiple inheritance of
classes that provide both state and behavior

A. `Java does not support multiple inheritance.`
B. Java allows multiple inheritance using abstract classes.
C. Java allows multiple inheritance using non-abstract classes.
D. `Java allows multiple inheritance using interfaces.`

9. How many changes need to be made to the classes below to properly override the watch() method?

```java
class Television {
    protected final void watch() {}
}
public class LCD extends Television {
    Object watch() {}
}
```

- We have to remove the final modifier from the method
- Remove protected from the modifiers on the method in Television
- Finally we have to declare the method to be of type void in LCD

The final modifier has to be removed to allow us to override the method in the first place. Then we make the LCD
method return void in LCD because of the body implementation returns nothing, although we can change the body of
both methods to return null, either one is fine

The removal of the protected modifier, is required because protected has a wider visibility than package-private
which is the default one when no access modifier is specified, and we are not allowed to override methods by
reducing the visibility. The reason this is considered visibility reduction is because the package private method,
is less visible to other classes compared to protected which is visible outside the current package where the class
is declared. The visibility hierarchy is as follows: public -> protected -> package-private -> private

A. One
B. Two
C. `Three`
D. None; the code compiles as is.

10. Which of the following statements about overriding a method is INCORRECT?

Actually we are not mandated by the compiler to throw the checked exception from the parent class method that we
override, actually this is what many implementations of AutoCloseable and Closeable do, they omit the exception from
the close method and handle that possibility internally, or maybe they do not throw when invoked at all in the first
place

```java
public class SafeCloseable implements Closeable {
    public void close() {}
}
```

A. The return types must be co-variant (TRUE)
B. The access modifier of the method in the child class must be the same or broader than the method in the superclass (TRUE).
C. `A checked exception thrown by the method in the parent class must be thrown by the method in the child class.`
D. A checked exception thrown by a method in the child class must be the same or narrower than the exception thrown by the method in the parent class (TRUE).

11. What is the output of the following application?

```java
class Computer {
    protected final int process() {
        return 5;
    }
}
public class Laptop extends Computer {
    public final int process() {
        return 3;
    }
    public static void main(String[] chips) {
        System.out.print(new Laptop().process());
    }
}
```

As already stated we are not allowed to override final methods, and in this case the process method matches the
signature of the method in the super-class Computer, therefore that will be marked as error from the compiler an d
is not allowed by the language, final methods can not be overridden even if we try to match all the modifiers from
the parent class that the final method has.

A. 5
B. 3
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

12. Given that FileNotFoundException is a subclass of IOException, what is the output of the following application?

```java
import java.io.\*;
class School {
    public int getNumberOfStudentsPerClassroom(String... students) throws IOException {
        return 3;
    }
    public int getNumberOfStudentsPerClassroom() throws IOException {
        return 9;
    }
}
public class HighSchool extends School {
    public int getNumberOfStudentsPerClassroom() throws FileNotFoundException {
        return 2;
    }
    public static void main(String[] students) throws IOException {
        School school = new HighSchool();
        System.out.print(school.getNumberOfStudentsPerClassroom());
    }
}
```

Ugh, this is valid, and that is annoying, one might think that the variable arguments method declaration here will
actually conflict with the one declared without any arguments and that might sound reasonable but actually that is
not the case both methods can exist at the same time. Therefore the method when called without arguments will call
the method that has no arguments.

So to clarify we can call the variable argument method like so getNumberOfStudentsPerClassroom(new String[0]), but
we can call the other method like so getNumberOfStudentsPerClassroom(), both calls technically call the method with
0 arguments yet, those methods can be called independently if we wish to do so.

A. `2`
B. 3
C. 9
D. The code does not compile.

13. Which modifier can be applied to an interface method?

This question has to be read carefully it asks about an interface method, not an abstract interface method which
would imply the question cares about all types of methods in an interface, which includes static methods too, and
static methods exist in interfaces they are final by default and public too.

A. protected
B. `static`
C. private
D. final

14. What is the output of the following application?

```java
package track;
interface Run {
    default void walk() {
        System.out.print("Walking and running!");
    }
}
interface Jog {
    default void walk() {
        System.out.print("Walking and jogging!");
    }
}
public class Sprint implements Run, Jog {
    public void walk() {
        System.out.print("Sprinting!");
    }
    public static void main() {
        new Sprint().walk();
    }
}
```

This is valid, this example tries to employ the diamond pattern problem where two interfaces provide default
implementation for the same methods with the same signature, in that case the implementing class MUST implement that
method explicitly otherwise its an error.

A. Walking and running!
B. Walking and jogging!
C. `Sprinting!`
D. The code does not compile.

15. Which of the following statements about interfaces is NOT true?

The only invalid statement here is the fact that interfaces implement other interfaces, that is not the case they
extend them but do not implement them it is not a coincidence that the keyword for extending interfaces is extend,
and not implement because interfaces do not implement each other.

A. An interface can extend another interface.
B. `An interface can implement another interface.`
C. A class can implement two interfaces.
D. A class can extend another class.

16. What is the output of the following application?

```java
class Ship {
    protected int weight = 3;
    protected int height = 5;
    public int getWeight() {
        return weight;
    }
    public int getHeight() {
        return height;
    }
}
public class Rocket extends Ship {
    public int weight = 2;
    public int height = 4;
    public void printDetails() {
        System.out.print(super.getWeight()+","+super.height);
    }
    public static final void main(String[] fuel) {
        new Rocket().printDetails();
    }
}
```

This is actually possible we can shadow variables from the parent class and still access them, for example here we
completely replace the values and even the modifiers of the class members, yet height/width exist in both classes
independently, and we can reference them as well. We can even re-declare the height and width member variables to be
of completely different type, float, double, String... Here we are referencing and printing the values of the super
class member variables from a method that is actually declared purely in the child class, is that not awesome !

A. 2,5
B. 3,4
C. `3,5`
D. The code does not compile.

17. Fill in the blanks: Excluding default and static methods, a(n) \***\*\_\_\_\_\*\***can contain both abstract and
    concrete methods, while a(n) \***\*\_\_\_\_\*\***contains only abstract methods.

The only option here is that the abstract class can contain both non-abstract and abstract methods while interfaces
can only contain abstract method.

A. concrete class, abstract class
B. concrete class, interface
C. interface, abstract class
D. `abstract class, interface`

18. Which statement about the following class is correct?

```java
abstract class Triangle {
    abstract String getDescription();
}
class RightTriangle extends Triangle {
    protected String getDescription() {
        return "rt";
    } // g1
}
public abstract class IsoscelesRightTriangle extends RightTriangle { // g2
    public String getDescription() {
        return "irt";
    }
    public static void main(String[] edges) {
        final Triangle shape = new IsoscelesRightTriangle(); // g3
        System.out.print(shape.getDescription());
    }
}
```

The code is mostly fine, until it tries to instantiate an abstract class, as the IsoscelesRightTriangle class is
declared as abstract therefore no instances of it can be created regardless of the fact that it has no abstract
methods, that are not overridden

A. The code does not compile due to line g1.
B. The code does not compile due to line g2.
C. `The code does not compile due to line g3.`
D. The code compiles and runs without issue.

19. Given that Short and Integer extend Number, what type can be used to fill in the blank in the class below to allow it to compile?

```java
interface Horn {
    public Integer play();
}
abstract class Woodwind {
    public Short play() {
        return 3;
    }
}
public final class Saxophone extends Woodwind implements Horn {
    public \***\*\_\_\_\*\***play() {
        return null;
    }
}
```

This is a mess, the question tries to be smart about co-variant types but it declares the method as Short play, that
is not a co-variant type that can replace the type for the method that returns Integer - play. All in all there are
like a 1000 issues with this piece of shit snippet.

A. Integer
B. Short
C. Number
D. `None of the above`

20. Fill in the blanks: A class \***\*\_\_\_\_\*\***an interface, while a class \***\*\_\_\_\_\*\***an abstract
    class.

The keywords here are pretty well defined when a class aims to target an interface it always implements it and it
extends off of another class (be it abstract or not)

A. extends, implements
B. extends, extends
C. `implements, extends`
D. implements, implements

21. What is the output of the following application?

```java
abstract class Book {
    protected static String material = "papyrus";
    public Book() {
    }
    public Book(String material) {
        this.material = material;
    }
}
public class Encyclopedia extends Book {
    public static String material = "cellulose";
    public Encyclopedia() {
        super();
    }
    public String getMaterial() {
        return super.material;
    }
    public static void main(String[] pages) {
        System.out.print(new Encyclopedia().getMaterial());
    }
}
```

This class implementation might look good on paper, but we have a red-flag in the first few lines of code that call
this.material, which is not possible because the damn material variable is declared as static

A. papyrus
B. cellulose
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

23. Which of the following modifiers can be applied to an abstract method?

Well an abstract method is meant to be overridden, always, so it would make no sense to make it private or final or
default which is only valid in an interface - but if the method is default it requires implementation if it has
implementation it is no longer abstract method

A. final
B. private
C. default
D. `protected`

24. What is the output of the following application?

```java
interface Sphere {
    default String getName() {
        return "Unknown";
    }
}
abstract class Planet {
    abstract String getName();
}
public class Mars extends Planet implements Sphere {
    public Mars() {
        super();
    }
    public String getName() {
        return "Mars";
    }
    public static void main(final String[] probe) {
        System.out.print(((Planet) new Mars()).getName());
    }
}
```

A. `Mars`
B. Unknown
C. The code does not compile due to the declaration of Sphere.

25. Which of the following statements is correct?

The correct statement here is that we can use a reference of a child class type and assign it directly to a
super-class type the rest are incorrect.

A - reference can not be assigned to sub-class unless we cast it to the specific sub-class which might cause class
cast exception or compiler exception if we try to cast to a type that has no common hierarchy with the super-class
type reference

C - reference to interface can not be assigned to a concrete class type that inherits it without an explicit cast to
that specific class, the other way around is possible though

D - this is almost right, but it contains a few words at the end that are wrong 'only with an explicit cast' which
is actually wrong, we can skip the cast and it will still work and probably be the preferred way to do it.

The rule of thumb is that we can not make a narrow type assignments unless we use a cast, and also if those types
share a common hierarchy otherwise its a compiler error on the cast. And runtime ClassCastException if they share a
common hierarchy but at runtime that instance is not of that particular type. And we can make a broader type
assignments from child to parent when they share a common hierarchy, without requiring explicit casting.

A. A reference to a class can be assigned to a subclass reference without an explicit cast.
B. `A reference to a class can be assigned to a superclass reference without an explicit cast.`
C. A reference to an interface can be assigned to a reference of a class that implements the interface without an explicit cast.
D. A reference to a class that implements an interface can be assigned to an interface reference only with an explicit cast.

26. Of the following four modifiers, choose the one that is not implicitly applied to all interface variables.

Well abstract variables do not exist, the rest of the modifiers are applied to interface member variables because
interfaces do not have/can declare state, therefore all member variables declared there are assumed to be static,
final and public.

A. final
B. `abstract`
C. static
D. public

27. What is the output of the following application?

```java
abstract class Car {
    static {
        System.out.print("1");
    }
    public Car(String name) {
        super();
        System.out.print("2");
    }
    {
        System.out.print("3");
    }
}
public class BlueCar extends Car {
    {
        System.out.print("4");
    }
    public BlueCar() {
        super("blue");
        System.out.print("5");
    }
    public static void main(String[] gears) {
        new BlueCar();
    }
}
BlueCar.main(new String[0])
```

This one is tricky, so the first thing we have to realize is the order of invocation of the initialization blocks,
these are called in a specific order, first the static initialize blocks, for the type when it is loaded by the
runtime

Then when we create instances of a given type, all initialization blocks are coalesced into a single structure in
the order they appear in the declaration of the class, they are invoked and only then is the actual constructor for
the class called

Of course the super constructor is invoked first but that is part of the scope of the super class, where the same
ordering happens, with the initialization blocks

So in this scenario, the very first thing that will get called is the static block, which prints 1, then the
instance initialization blocks for the class which prints '3', and then the constructor of the super class printing 2. Then we enter the child class and here we call the initialization block first printing '4', then finally the code
in the constructor printing '5'

A. 23451
B. 12354
C. `13245`
D. The code does not compile.

28. Fill in the blank: Overloaded and overridden methods always have\***\*\_\_\_\_\*\*** .

The only things they really share is the name, because one has runtime polymorphic and the other one is a compile
time polymorphic behavior, the rest of the properties are not shared always among them.

A. the same parameter list
B. different return types
C. `the same method name`
D. covariant return types

29. What is the output of the following application?

```java
abstract class Ball {
    protected final int size;
    public Ball(int size) {
        this.size = size;
    }
}
interface Equipment {}
public class SoccerBall extends Ball implements Equipment {
    public SoccerBall() {
        super(5);
    }
    public Ball get() {
        return this;
    }
    public static void main(String[] passes) {
        Equipment equipment = (Equipment) (Ball) new SoccerBall().get();
        System.out.print(((SoccerBall)equipment).size);
    }
}
```

Okay this is important, the cast operator in java works similarly to the way the instanceof works, meaning that the
compiler does not try to resolve everything at compile time and will leave certain validations to be done at runtime. In
this case we know that the get method returns a ball which is the cast to the interface Equipment, we know that at
compile time that is not possible because the Ball class has no direct relationship to the Equipment class, however the
compiler is lenient here and does not try to validate this at compile time, because it does not know if the actual
runtime instance that is behind the Ball is not a class that extends/implements the interface Equipment, in our case the
instance behind this variable is SoccerBall, and it does actually implement the interface Equipment, therefore is is
possible to perform the cast to Equipment, and then back to SoccerBall in the print statement, and print the size, the
size is with a protected modifier and it is accessible from the child class SoccerBall where the main method that is
accessing and printing it

A. `5`
B. The code does not compile due an invalid cast.
C. The code does not compile for a different reason.
D. The code compiles but throws a ClassCastException at runtime.

30. Fill in the blanks: A class that defines an instance variable with the same name as a variable in the parent class
    is referred to as \***\*\_\_\_\_\*\***a variable, while a class that defines a static method with the same signature
    as a static method in a parent class is referred to as \***\*\_\_\_\_\*\***a method.

In both cases we are shadowing/hiding the variable and the static method, static methods can not be overridden
because overriding is a runtime process that is tied to an instance of the class. The same is true for the member
variables, static or not. The variables are re-declared, however the parent ones are still accessible though super
or the name of the class if they were declared static

A. hiding, overriding
B. overriding, hiding
C. `hiding, hiding`
D. replacing, overriding

31. Which statement about the following class is correct?

```java
abstract class Parallelogram {
    private int getEqualSides() {
        return 0;
    } // x0
}
abstract class Rectangle extends Parallelogram {
    public static int getEqualSides() {
        return 2;
    } // x1
}
public final class Square extends Rectangle {
    public int getEqualSides() {
        return 4;
    } // x2
    public static void main(String[] corners) {
        final Square myFigure = new Square(); // x3
        System.out.print(myFigure.getEqualSides());
    }
}
```

The core issue with this code is the fact that the Rectangle class declares a static public method, and the Square
class that extends it provides a method iwth the same signature, that is not static, that is not possible and will
lead to a compiler error, it would have been fine had the Square method provided a getEqualSides method that was
static as well. The Parallelogram is saved from this issue because the method is private and not accessible outside
of the scope of the Parallelogram class.

A. `The code does not compile due to line x1.`
B. The code does not compile due to line x2.
C. The code does not compile due to line x3.
D. The code compiles and runs without issue.

32. What is the output of the following application?

```java
class Rotorcraft {
    protected final int height = 5;
    abstract int fly();
}
public class Helicopter extends Rotorcraft {
    private int height = 10;
    protected int fly() {
        return super.height;
    }
    public static void main(String[] unused) {
        Helicopter h = (Helicopter) new Rotorcraft();
        System.out.print(h.fly());
    }
}
```

Attempts to create an instance of an abstract class and on top of that tries to cast it to a sub-class which is
incorrect, so two major issues that will prevent this from compiling.

A. 5
B. 10
C. `The code does not compile.`
D. The code compiles but produces a ClassCastException at runtime.

33. Fill in the blanks: A class may be assigned to a(n) **\*\***\_**\*\*** reference variable automatically but
    requires an explicit cast when assigned to a(n) **\*\***\_**\*\*** reference variable.

What this question is asking is very very unclear, The wording is bad, the terms that are used are wrong, the sentence itself is poor English at best.

So TRANSLATION: A variable of certain type can be assigned to a super-type reference variable, but the inverse requires a cast

```java
Integer exampleInstanceVariable = new Integer(5); // the source instance
Number superReferenceType = exampleInstanceVariable; // no casting required
Integer childReferenceType = (Integer) superReferenceType; // cast required
```

A. subclass, outer class
B. `superclass, subclass`
C. subclass, superclass
D. abstract class, concrete class

34. Fill in the blank: A(n) \***\*\_\_\_\_\*\***is the first non-abstract subclass that is required to implement all
    of the inherited abstract methods.

The very first non-abstract class in a class hierarchy has to declare and implement all abstract methods coming from
interfaces or abstract classes.

A. abstract class
B. abstraction
C. `concrete class`
D. interface

35. How many compiler errors does the following code contain?

```java
interface CanFly {
    public void fly() {
        // empty implementation
    }
}
final class Bird {
    public int fly(int speed) {
        // empty implementation
    }
}
public class Eagle extends Bird implements CanFly {
    public void fly() {
    }
}
```

This class Eagle extends off of Bird, but the bird class is declared as final. Therefore it does not allow any other
class to ever extend it. The other issue is that the interface provides an implementation for a method that is not
declared as default. The fly method from Bird does not return anything yet it is declared to return int. So there
are at least 3 errors.

A. None
B. One
C. Two
D. `Three`

36. Which of the following is NOT an attribute common to both abstract classes and interfaces?

Default methods are only a property of interfaces, abstract classes have regular methods or abstract methods, they
do not have default methods.

A. They both can contain static variables.
B. `They both can contain default methods.`
C. They both can contain static methods.
D. They both can contain abstract methods.

37. What is the output of the following application?

```java
interface SpeakDialogue {
    default int talk() {
        return 7;
    }
}
interface SingMonologue {
    default int talk() {
        return 5;
    }
}
public class Performance implements SpeakDialogue, SingMonologue {
    public int talk(String... x) {
        return x.length;
    }
    public static void main(String[] notes) {
        System.out.print(new Performance().talk(notes));
    }
}
```

This code will not compile, the method talk MUST be overridden in the Performance class because of the diamond
issue, however it is NOT correctly overridden, because the signature is not the same, arguments declaration does not
match

A. 7B. 5
C. The code does not compile.
D. The code compiles without issue, but the output cannot be determined until runtime.

38. Which of the following is a virtual method?

The only ones that are virtual, or in other words poses the quality of runtime polymorphism are the ones that are
non-final, non-static class methods. From the options below only one of the types is actually capable of being a
virtual method and be overridden - the rest are either static, final or private, all modifiers that prevent
overriding in the first place

A. `protected instance methods`
B. static methods
C. private instance methods
D. final instance methods

39. Fill in the blanks: An interface \***\*\_\_\_\_\*\***another interface, while a class \***\*\_\_\_\_\*\***another class.

Both are considered to 'extend', interfaces extend other interfaces, and so do classes extend other classes.

A. implements, extends
B. `extends, extends`
C. implements, implements
D. extends, implements

40. What is the output of the following application?

```java
class Math {
    public final double secret = 2;
}
class ComplexMath extends Math {
    public final double secret = 4;
}
public class InfiniteMath extends ComplexMath {
    public final double secret = 8;
    public static void main(String[] numbers) {
        Math math = new InfiniteMath();
        System.out.print(math.secret);
    }
}
InfiniteMath.main(new String[0]);
```

Now this is a good one ! What is going on here, the idea of this example is to show the difference between the way
methods can be overridden and how variables are stored in an object, so here is the structure. When we create an
object of type InfiniteMath we actually create a structure that holds the 3 variables (+ data for the methods, and
some additional meta data that is not very important at the moment).

```plaintext
Math
[members]
secret = 8bytes

ComplexMath
[members]
secret = 8bytes

InfiniteMath
[members]
secret = 8bytes
```

When we create a variable of type InfiniteMath, the runtime allocates space to fit the 3 variables that we inherit,
even though the InfiniteMath type is shadowing them they are still part of the object's structure and memory
footprint they are there, therefore we are actually capable of accessing each variable even though directly
accessing this.secret from InfiniteMath instance will actually provide the secret variable's value that is in
InfiniteMath. What the compiler does when it accesses the specific member is offset into the object from the start
to find each member - to access the third secret declared in InfiniteMath we have to offset 16 bytes (simplified
explanation, because there might be other meta data and member fields which actually change this).

```java
// we can access each of the shadowed member variables easily, by a simple cast, that way we simply tell the
// compiler how to interpret the member access patterns and how far into the object memory data to offset when
// accessing the member variables
Math m = new InfiniteMath(); assert m.secret == 2
ComplexMath m = new InfiniteMath(); assert m.secret == 4
InfiniteMath m = new InfiniteMath(); assert m.secret == 8
```

```java
// from the InfiniteMath class we can also access the shadowed member variables like so, by casting to the specific
// parent and accessing the member directly, that is possible because the way the compiler will interpret the offsets
// into the data of the object, based on the concrete type.
void show() {
    System.out.print(this.secret);                 // 8
    System.out.print(((ComplexMath)this).secret);  // 4
    System.out.print(((Math)this).secret);         // 2
}
```

A. `2`
B. 4
C. 8
D. The code does not compile.
