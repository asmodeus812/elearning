1. What is the result of the following code?

```java
public class Employee {
    public int employeeId;
    public String firstName, lastName;
    public int yearStarted;
    @Override
    public int hashCode() {
        return employeeId;
    }
    public boolean equals(Employee e) {
        return this.employeeId == e.employeeId;
    }
    public static void main(String[] args) {
        Employee one = new Employee();
        one.employeeId = 101;
        Employee two = new Employee();
        two.employeeId = 101;
        if (one.equals(two)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
        }
    }
}
Employee.main(new String[0])
```

There are a few gotchas here but generally clean question. First the way the firstName and lastName are defined is
valid, and will compile. The second one is the equals, if this method has an @Override annotation on top of it that
would have been a compile time error, because equals's signature is different - equals(Object o), it overriding does not
allow covariant types. Then we have the main method which does most of the work just fine, and will print Success,
because the equals method that is NOT overridden but overloaded will return true.

If the Employee however was cast to Object and call equals on that then reference compare will happen because we do not
override the equals method for employee it will use the default one from the Object class

A. `Success`
B. Failure
C. The hashCode() method fails to compile.
D. The equals() method fails to compile.
E. Another line of code fails to compile.
F. A runtime exception is thrown.

2. What is the result of compiling the following class?

```java
public class Book {
    private int ISBN;
    private String title, author;
    private int pageCount;
    public int hashCode() {
        return ISBN;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) {
            return false;
        }
        Book other = (Book) obj;
        return this.ISBN == other.ISBN;
    }
    public static void main(String[] args) {
        Object book = new Book();
        if (book.equals(book)) {
            System.out.println("Success");
        } else {
            System.out.println("Failed");
        }
    }
}
Book.main(new String[0]);
```

In this example which is very similar to the very first one we have the equals method this time being overridden and it
is correctly done so, the correct signature of the equals method is getting overridden, on top of that, we also have the
hashCode this is also overriding it since it perfectly matches the signature but the annotation (which is optional) is
not provided, that is fine.

A. `The code compiles. Prints Success`
B. The code does not compile because hashCode() is incorrect.
C. The code does not compile because equals() does not override the parent method correctly.
D. The code does not compile because equals() tries to refer to a private field.
E. The code does not compile because the ClassCastException is not handled or declared.
F. The code does not compile for another reason.

3. What is the result of the following code?

```java
String s1 = "Canada";
String s2 = new String(s1);
if(s1 == s2) System.out.println("s1 == s2");
if(s1.equals(s2)) System.out.println("s1.equals(s2)");
```

This one can be a bit tricky since we create one interned string and one copy of that one, both live as different
objects on the heap therefore have different references, that is because we explicitly create the second string using
the constructor of that String. Therefore by reference they will not equal, but by content they do, so equals method
will return true, and that is what will get printed out.

A. There is no output.
B. s1 == s2
C. `s1.equals(s2)`
D. Both B and C.
E. The code does not compile.
F. The code throws a runtime exception.

4. What is true about the following code? You may assume city and mascot are never null.

```java
public class BaseballTeam {
    private String city, mascot;
    private int numberOfPlayers;
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseballTeam)) {
            return false;
        }
        BaseballTeam other = (BaseballTeam) obj;
        return (city.equals(other.city) && mascot.equals(other.mascot));
    }
    public int hashCode() {
        return numberOfPlayers;
    }
}
```

The class implements and overrides the equals method correctly, however the hash code is not correct, that is because
both hash code and equals have to return predictable values and in this case thy are not using the same properties to
compute equals and hashCode. The main rule of thumb is that the equals method has to return true for two truly equal
objects, the hashCode then must return the same value for two equal objects.

A. The class does not compile.
B. The class compiles but has an improper equals() method.
C. `The class compiles but has an improper hashCode() method.`
D. The class compiles and has proper equals() and hashCode() methods.

5. Which of the following statements are true, assuming a and b are String objects? (Choose all that apply.)

This one is important, we usually have to guarantee that when a equals method returns true, then the hash code method
also returns the same value that implies for semantically equal objects, we need the same hash code. The inverse is
however not true, when for two non-equal objects the equals method return false, the hash code is not required to return
different values. Remember the hashCode tells you which bucket this object is in, the equals tells you exact
match/equality, you have found the object you are looking for. The equals method is of higher status

A. `If a.equals(b) is true, a.hashCode() == b.hashCode() is always true.`
B. If a.equals(b) is true, a.hashCode() == b.hashCode() is sometimes but not always true.
C. If a.equals(b) is false, a.hashCode() == b.hashCode() can never be true.
D. `If a.equals(b) is false, a.hashCode() == b.hashCode() can sometimes be true.`

7. What is the result of the following code?

```java
public class FlavorsEnum {
    enum Flavors {
        VANILLA, CHOCOLATE, STRAWBERRY
    }
    public static void main(String[] args) {
        System.out.println(Flavors.CHOCOLATE.ordinal());
    }
}
FlavorsEnum.main(new String[0])
```

That can be tricky but the enum requires a semi-colon only when there are methods in the enum, but if the enum is plain
empty and has only the enumerated values that is not needed. Therefore this example should compile and run and print the
ordinal which is the index of the enumeration in the list in this case index 1, the second enumeration element. Once
should not rely on ordinal value since the order of enumeration items can change.

A. 0
B. `1`
C. 9
D. CHOCOLATE
E. The code does not compile due to a missing semicolon.
F. The code does not compile for a different reason.

8. What is the result of the following code? (Choose all that apply.)

```java
public class IceCream {
    enum Flavors {
        VANILLA, CHOCOLATE, STRAWBERRY
    }
    public static void main(String[] args) {
        Flavors f = Flavors.STRAWBERRY;
        switch (f) {
            case 0: System.out.println("vanilla");
            case 1: System.out.println("chocolate");
            case 2: System.out.println("strawberry");
            break;
            default: System.out.println("missing flavor");
        }
    }
}
```

That is not C or C++, the enumeration constants in java are not integers, by default. They are fully fledged types that
are not convertible to integer easily, therefore this code will not compile. We can use the ordinal value of the enum
however So if we just do one simple change in the switch expression, with f.ordinal() then that example will actually
compile

A. vanilla
B. chocolate
C. strawberry
D. missing flavor
E. `The code does not compile.`
F. An exception is thrown.

9. What is the result of the following code?

```java
public class Outer {
    private int x = 5;
    protected class Inner {
        public static int x = 10;
        public void go() { System.out.println(x); }
    }
    public static void main(String[] args) {
        Outer out = new Outer();
        Outer.Inner in = out.new Inner();
        in.go();
    }
}
Outer.main(new String[0])
```

This one is subtle. First a few gotchas that might trip somebody. First the class is defined as protected, that is
really not relevant here, also the syntax out.new Inner() is actually correct the new keyword is correctly placed for
creating an inner nested class. The more subtle nuanced issues arise when we take a look at the X variable present in
both the parent and the child classes, the variable in the child class will shadow the variable in the parent, and print
10 in the final output.

But the reason this will not compile is because we have a static member inside an inner non-static class which is not
allowed and if we think about it we will see why that might be the case, the inner class does not really exist without
an instance of the outer one. Therefore it is impossible to hold / initialize this static member. This restriction was
actually loosened in more recent java versions past java 16.

If we were to rename the X variable inside Inner class to Y, then the print will actually output 5, that is because the
Inner has an implicit reference to the outer class with which it was created like so - Outer.Inner in = out.new Inner();

A. The output is 5.
B. The output is 10.
C. `Line 4 generates a compiler error.`
D. Line 8 generates a compiler error.
E. Line 9 generates a compiler error.
F. An exception is thrown

10. What is the result of the following code?

```java
public class Outer {
    private int x = 24;
    public int getX() {
        String message = "x is ";
        class Inner {
            private int x = Outer.this.x;
            public void printX() {
                System.out.println(message + x);
            }
        }
        Inner in = new Inner();
        in.printX();
        return x;
    }
    public static void main(String[] args) {
        int value = new Outer().getX();
    }
}
Outer.main(new String[0])
```

That one might look convoluted and complex but actually it should be intuitive. The inner class is bound to the instance
of Outer, that is because it is defined in a non-static method, so we can simply create new Instance of Inner class in
that method and it will be actually bound to the Outer instance directly. The Outer.this.x line might look weird but it
is actually valid, that is the same as creating a constructor for Inner and assigning the variable of x there, this x
declared in Inner however shadows the one in Outer so when it is used from within the Inner class it will be the one
declared in Inner, in this case both the Outer and Inner x value is the same so that is not very relevant,

In case the X value in Inner, were different, then we might be more confused due to the shadowing that will occur. One
more subtle nuance in java 7 the member variable had to be explicitly defined as final, however in more recent version
past java 7 actually that requirement was dropped, and being effectively final.

A. x is 0.
B. `x is 24.`
C. Line 6 generates a compiler error.
D. Line 8 generates a compiler error.
E. Line 11 generates a compiler error.
F. An exception is thrown.

11. The following code appears in a file named Book.java. What is the result of compiling the source file?

```java
public class Book {
    private int pageNumber;
    private class BookReader {
        public int getPage() {
            return pageNumber;
        }
    }
}
```

A. The code compiles successfully, and one bytecode file is generated: Book.class.
B. The code compiles successfully, and two bytecode files are generated: Book.class and BookReader.class.
C. `The code compiles successfully, and two bytecode files are generated: Book.class and Book$BookReader.class.`
D. A compiler error occurs on line 3.
E. A compiler error occurs on line 5.

12. Which of the following statements can be inserted to make FootballGame compile?

```java
package my.sports;
public class Football {
    public static final int TEAM_SIZE = 11;
}
package my.apps;
import static my.sports.Football.*;
public class FootballGame {
    public int getTeamSize() { return TEAM_SIZE; }
}
```

To really find out the correct answer we can start off with excluding the false positives, the static import ones for
one. These are not correct key word sequences it is import followed by the keyword static. Second we are referencing a
static variable TEAM_SIZE, therefore we need static import, that excludes the very first option. Now the star import
usually imports the very top level of the hierarchy so that means that importing my.sports.\* or my.sports.Football will
not bring the TEAM_SIZE symbol in scope for usage. That leaves really only one viable option which is the one that
imports my.sports.Football.\*, that would bring all static symbols defined in the Football class into scope

A. import my.sports.Football;
B. import static my.sports.\*;
C. import static my.sports.Football;
D. `import static my.sports.Football.\*;`
E. static import my.sports.\*;
F. static import my.sports.Football;
G. static import my.sports.Football.\*;

13. What is the result of the following code?

```java
public class Browsers {
    static class Browser {
        public void go() {
            System.out.println("Inside Browser");
        }
    }
    static class Firefox extends Browser {
        public void go() {
            System.out.println("Inside Firefox");
        }
    }
    static class IE extends Browser {
        @Override public void go() {
            System.out.println("Inside IE");
        }
    }
    public static void main(String[] args) {
        Browser b = new Firefox();
        IE e = (IE) b;
        e.go();
    }
}
Browsers.main(new String[0])
```

This is mostly straightforward, since we create a well defined hierarchy. In the main method the Firefox concrete
instance is assigned correctly to a parent type, but then that instance is cast to a sub-type that is not part of the
Firefox hierarchy, which will produce a compile time error, or in other words ClassCastException

A. Inside Browser
B. Inside Firefox
C. Inside IE
D. The code does not compile.
E. `A runtime exception is thrown`

14. Which is a true statement about the following code?

```java
public class IsItFurry {
    static interface Mammal { }

    static class Furry implements Mammal { }

    static class Chipmunk extends Furry { }

    public static void main(String[] args) {
        Chipmunk c = new Chipmunk();
        Mammal m = c;
        Furry f = c;
        int result = 0;
        if (c instanceof Mammal) result += 1;
        if (c instanceof Furry) result += 2;
        if (null instanceof Chipmunk) result += 4;
        System.out.println(result);
    }
}
IsItFurry.main(new String[0])
```

This can be tricky, the program compiles just fine however, the key here is to understand what instanceof really does,
in Java instanceof does not check or validate if a given object instance is an actual concrete instance of a certain
type it simply checks if that object instance can be assigned / cast to a given type.

In the example above, we can see that both statements that check against instanceof Mammal|Furry are valid, since the
variable in question is a sub-type child of both of those classes, therefore these if statements will be true and the
result variable incremented with 1 and then with 2.

You can think of instanceof that way, a class is an instance of any superclass or interface it extends or implements,
that might sound counter intuitive, but that is how java semantics works work instanceof

To check if a variable is of actual concrete type one needs to use the variable.getClass() == ClassName.class. That way
we can actually check if a given variable is of a concrete specific pre-determined type.

A. The output is 0.
B. `The output is 3.`
C. The output is 7.
D. c instanceof Mammal does not compile.
E. c instanceof Furry does not compile.
F. null instanceof Chipmunk does not compile.

15. Which is a true statement about the following code? (Choose all that apply.)

```java
import java.util. *;
public class IsItFurry {
    static class Chipmunk { }
    // static class ExtendedChipmunk extends Chipmunk implements Runnable {}
    // static class WeirdList extends ArrayList implements Runnable {}

    public static void main(String[] args) {
        Chipmunk c = new Chipmunk();
        ArrayList<Chipmunk> l = new ArrayList<>();
        Runnable r = new Thread();

        int result = 0;
        if (c instanceof Chipmunk) result += 1;
        if (l instanceof Chipmunk) result += 2;
        if (r instanceof Chipmunk) result += 4;

        System.out.println(result);
    }
}
IsItFurry.main(new String[0])
```

Similarly to the example above, this one aims to demonstrate how instanceof behaves when comparing interfaces and
classes. In the example above the l is of type ArrayList and r is of type Runnable. At compile time the compiler is more
lenient when checking against interfaces when compared with regular classes (abstract included) because java has a
single inheritance model the compiler can tell at compile time that ArrayList and Chipmunk have no relationship
whatsoever. However that same statement can not be made for the relationship between the Runnable interface and the
Chipmunk. There might be class that extends Chipmunk and implements Runnable, or even multiple classes in a deeper
hierarchy, the compiler does not attempt to detect that at compile time.

So all in all what we need to remember is that the compiler will try to resolve the class-class relationship at compile
time when using instanceof, that is easy to check at compile time,due to the single inheritance model in java, and leave
the rest interface-class relationship for runtime because it is safer and does not require walking the entire type
hierarchy to actually detect it at compile time.

A. The code compiles, and the output is 0.
B. The code compiles, and the output is 3.
C. The code compiles, and the output is 7.
D. c instanceof Chipmunk does not compile.
E. `l instanceof Chipmunk does not compile.`
F. r instanceof Chipmunk does not compile.

16. Which of the following statements are true about the equals() method? (Choose all that apply.)

A regular equals method should ensure that both the null check and the correct type instance check are made, before
attempting to cast and compare the two instances. In all cases when null is passed to equals the return should be false,
and when an invalid type that is not cast-able to the given type passed should also return false. The contract of equals
does not expect the implementation to throw anything normally.

A. If equals(null) is called, the method should throw an exception.
B. `If equals(null) is called, the method should return false.`
C. If equals(null) is called, the method should return true.
D. If equals() is passed the wrong type, the method should throw an exception.
E. `If equals() is passed the wrong type, the method should return false.`
F. If equals() is passed the wrong type, the method should return true

17. Which of the following can be inserted in main?

```java
public class Outer {
    class Inner { }
    public static void main(String[] args) {
        // INSERT CODE HERE
    }
}
Outer.main(new String[0])
```

This example can be tricky, but we first need to see that we have one inner non-static class, and the code has to be
inserted into a static method. Then to create an instance of the Inner class we need an instance of the Outer class,
since the Inner has to be bound to a valid non-null instance of the Outer class. The only valid here option is the call
to create the Outer and then the Inner from that one - new Outer().new Inner();

A. Inner in = new Inner();
B. Inner in = Outer.new Inner();
C. Outer.Inner in = new Outer.Inner();
D. Outer.Inner in = new Outer().Inner();
E. `Outer.Inner in = new Outer().new Inner();`
F. Outer.Inner in = Outer.new Inner();

18. What is the result of the following code? (Choose all that apply.)

```java
public enum AnimalClasses {
    MAMMAL(true), FISH(Boolean.FALSE), BIRD(false),
    REPTILE(false), AMPHIBIAN(false), INVERTEBRATE(false)
    boolean hasHair;
    public AnimalClasses(boolean hasHair) {
        this.hasHair = hasHair;
    }
    public boolean hasHair() {
        return hasHair;
    }
    public void giveWig() {
        hasHair = true;
    }
}
```

This one might seem complex, but actually the key here is the enum class, that is defined poorly, since the enum has
methods, and properties it is required that the list of enumerations ends with a semi-colon therefore we have an
immediate compile time error on line 3 where the missing semi-colon is supposed to be at the end of the line.

One more is the public constructor enum types are not allowed to have public constructors, they are by default private
and that is all that they can be since they are instantiated by the JVM at runtime.

A. Compiler error on line 2.
B. `Compiler error on line 3.`
C. `Compiler error on line 5.`
D. Compiler error on line 8.
E. Compiler error on line 12.
F. Compiler error on another line.
G. The code compiles successfully.

19. What is the result of the following code? (Choose all that apply.)

```java
public class Swimmer {
    enum AnimalClasses {
        MAMMAL, FISH {
            public boolean hasFins() { return true; }},
        BIRD, REPTILE, AMPHIBIAN, INVERTEBRATE;
        public abstract boolean hasFins();
    }
    public static void main(String[] args) {
        System.out.println(AnimalClasses.FISH);
        System.out.println(AnimalClasses.FISH.ordinal());
        System.out.println(AnimalClasses.FISH.hasFins());
        System.out.println(AnimalClasses.BIRD.hasFins());
    }
}
```

This one will never compile, because while it is valid for an enum class to have an abstract method, all of the
enumeration constants have to implement this method or methods. The way the FISH enum entry does is correct, but that
has to be done for all of these enum constants. will never compile, because while it is valid for an enum class to have
an abstract method, all of the enumeration constants have to implement this method or methods. The way the FISH enum
entry does is correct, but that has to be done for all of these enum constants, which it does not.

A. fish
B. FISH
C. 0
D. 1
E. false
F. true
G. `The code does not compile.`

20. Which of the following can be inserted to override the superclass method? (Choose all that apply.)

```java
public class LearnToWalk {
    public void toddle() {}
    class BabyRhino extends LearnToWalk {
        // INSERT CODE HERE
    }
}
```

This one can be a bit tricky due to the final keyword, however think about it, we can override an existing method and
also make it final that implies that further down the hierarchy no class that extends BabyRhino will ever be able to
override this toddle method. But in the BabyRhino we can so we can change the modifiers but we can NOT change the
signature of the method. Meaning that we can not add input arguments, we can not change the return type from void, and
we can not add throws Exception. All of these modify the signature in an incompatible way.

Note that this does not involve the two ways which can allow you to modify the signature and still override the method,
the covariant types and exception narrowing. That means that if for example this method had a return type of
CharSequence we can override it and change the return type to String since that is a narrower type that implements
CharSequence. The same is true for exceptions, if the method in the parent did declare that it throws Exception, we
could override it with throws IOException since that is again a covariant type of the exception, we can even add more
checked exceptions, and add as many unchecked as we wish since those are not really, mandated by the compiler

```java
public class LearnToWalk {
    public CharSequence toddle() throws Exception { return ""; }
    class BabyRhino extends LearnToWalk {
        @Override
        public String toddle() throws IOException, InvalidParameterException { return ""; }
    }
}
```

This example demonstrates how covariant changes in the signature of the method do not cause compiler error, and we
successfully override the method by narrowing down some of the method signature

A. `public void toddle() {}`
B. public void Toddle() {}
C. `public final void toddle() {}`
D. public static void toddle() {}
E. public void toddle() throws Exception {}
F. public void toddle(boolean fall) {}

21. What is the result of the following code?

```java
public class FourLegged {
    String walk = "walk,";
    static class BabyRhino extends FourLegged {
        String walk = "toddle,";
    }
    public static void main(String[] args) {
        FourLegged f = new BabyRhino();
        BabyRhino b = new BabyRhino();
        System.out.println(f.walk);
        System.out.println(b.walk);
    }
}
```

Just as with regular non-static classes we have shadowing here, that shadowing however is more aggressive because it
actually redefines and redeclares the member variable, it works just like one does override member methods for a class.
We can re-define the entire member change its type and value but keep the name the same

A. toddle,toddle,
B. toddle,walk,
C. `walk,toddle,`
D. walk,walk,
E. The code does not compile.
F. A runtime exception is thrown.

22. Which of the following could be inserted to fill in the blank? (Choose all that apply.)

```java
public interface Otter {
    default void play() { }
}
class RiverOtter implements Otter {
    _____________________________
}
```

The valid answers here include the default equals and hashCode, methods that are provided by default by the Object
parent class. On top of that we also can override the default method but not necessary, the body of this class that
implements this can be left empty. However here we can override the method but only with public void modifiers, we can
not use the option that skips the modifiers which by default when not present is going to be default package private
access. That is not allowed we can NOT reduce the visibility of the method we can only increase it

A. `@Override public boolean equals(Object o) { return false; }`
B. @Override public boolean equals(Otter o) { return false; }
C. `@Override public int hashCode() { return 42; }`
D. @Override public long hashCode() { return 42; }
E. `@Override public void play() { }`
F. @Override void play() { }
