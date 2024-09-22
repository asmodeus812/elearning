## Classes

These are core internal component of the java language, unlike other languages where classes are not required, in JAVA
the class is the cornerstone structure around which the entire language is evolving. Classes have two main components -
class methods and class variables.

```
class classname {
    type instance-variable1;
    type instance-variable2;
    // ...
    type instance-variableN;
    type methodname1(parameter-list) {
    // body of method
    }
    type methodname2(parameter-list) {
    // body of method
    }
    // ...
    type methodnameN(parameter-list) {
    // body of method
    }
}
```

### Constructors

These are special types of class methods. They have no explicit return type, however they have an implicit one, the
return type of the constructor of a given class is the class/type itself. By default the language provides a default
constructor, which takes no parameters. The default constructor can be re-defined manually/explicitly. If at least one
constructor is provided for a class, the default one provided implicitly - `is not` . The constructor in all other
regards is similar to class methods, the only difference is that it is the very first `method` that is invoked when a
new instance of the class is created. This can be used to initialize or construct the object before it is used

```java
class Box {
    double width;
    double height;
    double depth;

    Box(double w, double h, double d) {
        width = w;
        height = h;
        depth = d;
    }
}
```

```java
    Box b = new Box(1,2,3);
    Box b2 = new Box(); // that is compile time error, default constructor is superseded by the explicit one
```

The example above provides an explicit constructor, meaning that the default one would not be provided by the runtime,
the object of type Box can be created by only providing the 3 parameters needed - in this case the dimensions of the
box.

### Methods

The class member methods, differ from constructors mainly by the way they are named, and that they usually have a return
type, which is explicitly defined even if it is void. The class member methods can not have a name that matches the name
of the class since that one is reserved for constructors, other than that, the class member methods can accept any
number of arguments, it is important to note that every class member method (non static ones) receive implicitly by
default as the first argument the object or instance reference of the instance of the class currently being operated one

```java
class Box {
    double width;
    double height;
    double depth;

    double volume() {
        return width * height * depth;
    }
}
```

```java
   Box b = new Box();
   b.volume() // is actually implicitly compiled to Box.volume(b)
```

### Caveats

The `this` keyword is an important detail which is often overlooked, the keyword provides a way to explicitly reference the instance of the class from within its constructors or methods. The most common use case is to avoid ambiguity between constructor or method arguments and class members.

```java
// let us have following constructor for type Box
Box(double width, double height, double depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
}
```

`Note that had we skipped the this keyword usage in the method above, we would face what is called instance variable
hiding, meaning that the width/height/depth would have refered to the local arguments, passed to the function, since
they have a higher weight or scope, they are the closest scope, compared to the class member variables`

### Destructors

Like other languages Java provides a `similar` way of destroying an object, however the destruction of that object, the
time and place is non-deterministic, unlike other languages, such as C++, where the destructor is called when the object
goes out of scope (if created on the stack) or when we call `delete` on an object (created on the heap), the Java's
finalize method is called when the garbage collector mark and sweep algorithm goes through the objects and cleans up the
ones that are no longer referenced, however this is not something we can predict or that can be relied on. The garbage
collection is automatic, meaning that, while, yes at some point in the future the finalize method will be called, we can
not determine when exactly. The finalize method may be used to free non java resources, such as loaded textures, fonts,
images etc.

```java
protected void finalize( ) {
    // free non java related resources, to avoid memory or other resource leak
}
```

### Access

This is a way to control the visibility of class members, meaning that based on the access modifier, certain
restrictions apply for the outside world which is trying to access class specific members. Let's begin by
defining them - default, public, private and protected.

-   When a member of a class is modified by `public`, then that member can be accessed by any other code.

-   When a member of a class is specified as `private`, then that member can only be accessed by other members of its
    class.

-   When a member of a class is specified as `protected`, that is usually mostly used in Inheritance situations, where the
    sub-classes have to access the parent's protected methods to either re-use or override them

-   When no access modifier is specified also known as `package-private` (since it lacks a specific keyword), applies
    when you don't explicitly specify an access modifier for a class, method, variable, or constructor - allows access only
    within the same package. This means classes, methods, or fields with no access modifier can be accessed by other classes
    in the same package, but they are inaccessible outside the package.

    ```java
        // File: package1/ClassA.java
        package package1;

        class ClassA {
            void defaultMethod() {
                System.out.println("Default method in ClassA");
            }
        }

        // File: package1/ClassB.java
        package package1;

        public class ClassB {
            public void test() {
                ClassA a = new ClassA();
                a.defaultMethod();  // Accessible, since it's in the same package.
            }
        }

        // File: package2/ClassC.java
        package package2;

        public class ClassC {
            public void test() {
                ClassA a = new ClassA();  // Error: ClassA is not visible outside package1.
            }
        }
    ```

`It is prudent to note that classes can also be defined as default, private, protected or public. However top-level
classes can be defined only with default or public modifiers, defining a class as private or protected at top-level
(file level) will result in compilation error`

### Static

Two types of static class members fields exist - static method members, and static variable members. Both of which have the same restrictions and rules for accessing other members of the class.

-   They can not access the `this` keyword, or in other words the current class instance, since static members are by definition not tied up to any instance of the given class
-   They can not call or use the super constructor
-   They can only manipulate other static members - other static variables or methods

To initialize static variables after their declaration, one can use the so called `static block` which is in the
following form presented below. `As soon as the class is loaded it's static blocks and static members are initialized,
this is quite important to note`

```java
public class Type {

    public static int variable;

    static {
        variable = 1;
    }

    public static void method() {
        System.out.println("v");
    }
}
```

### Final

Final members are these which are essentially considered constants, they can be given values only in one of two ways -
either through the constructor or at the moment of declaration, if a final field is not given a value it is considered a
compile time error, and the compiler will complain.

```java
public class Type {
    public final int variable = 1;
    public final int another;

    public Test() {
        another = 2;
    }
}
```

Both are valid usages, and they depend on the general use case, normally when we create immutable but constructible
objects, one would like to use the constructor instead of the declaration, since it gives you more control, however if
you are building a math library, it is very much natural to initialize the number `pi` already at the moment of
declaration, since it would never change.

Applying final to a method, has a different meaning, it usually implies that method will not be available for inherited
classes to override, in java all methods are usually by default eligible for overriding, besides the private ones,
however we can mark a protected or public method as final in the super class to avoid the inheriting classes from
overriding it. Trying to override a final method will result in compile time error.

### Nesting

A very powerful feature of the java language is to allow us to declare nested classes within each other. Each nested
class has direct access to all class members of the one within it is nested, yes even private ones. The enclosing class
has no access to the private members of the classes nested within itself.

There are two ways to define a nested class, one is to define it as `static` and the other is to define it without the
`static` keyword. They have `vastly different meaning and implications`

```java
public class FirstLevel {

    private int member;

    public class NostaticSecondLevel {

        SecondLevel() {
        }
    }

    public static class StaticSecondLevel {

        SecondLevel() {
        }
    }
}
```

-   `static` nested class is such that it can not access the members of the enclosing class without having an instance
    to it first. No instance of the static class is created when a new instance of the outer one is, usually this is done
    manually and is under the control of the programmer

-   `non-static` nested class is such that it is bound to an instance of the enclosing class, meaning we can access
    members of the enclosing class. When a new instance of the outer is created, it effectively creates an annonymous
    instance of the inner non-static class as well

`If we think about it, this is very intuitive, because other types of static and non-static members work the same way,
the non-static ones, are the ones bound to a particular class instance, while the static ones are not`.

This `static` nested class approach is often used to create a nested, a related `utility` class which can be used within
the top level class, such as the following example below, where we define a cart, and it is only natural to have the
cart items be also defined as nested class because those two are somewhat really tightly related.

```java

public class Cart {

    private List<Item> items = new ArrayList<>();

    public static class Item {

        private String name;

        public Item(String name) {
            this.name = name;
        }
    }

    public List<Item> getItems() {
        return this.items;
    }
}
```

`Note that we have not defined the Item class as private, even though we can, if we expect to operate on individual
items in the cart through the getItems member method, we would not be able to declare a variable of type Item, since it
is private`

This `non-static` nested class approach is used when we want to provide some sort encapsulation for our outer class, as
well as provide a logical means of separation between some structures. As mentioned non static inner classes have direct
access to the instance members of a class, since the non-static inner classes are bound to an instance of the outer
enclosing class

```java
public class FirstLevel {

    private int outer;

    public class SecondLevel {

        private int inner;

        SecondLevel() {
            this.inner = outer + 5;
        }
    }
```

As we can see the inner class directly uses the `outer` member of the outer class, but that is in the `scope` of an
instance of `FirstLevel` unlike the `static` class where an instance is not required. It is possible to define inner
classes within any block scope. For example, you can define a nested class within the block defined by a method or even
within the body of a for loop.

```java
class Outer {
    private int outer_x = 100;

    void test() {
        for(int i=0; i<10; i++) {
            class Inner {
                void display() {
                    System.out.println("outer_x = " + outer_x);
                }
            }
            Inner inner = new Inner();
            inner.display();
        }
    }
}
```

`Shadowing in inner non-static class can occur, this is when both the outer class and the inner class have the same
members, this however can be resolved by using a special notation which is to use the name of the outer enclosing class
followed by this, similarly to how we can access static members by using the name of the class, followed by the name of
the member`

```java
public class FirstLevel {

    private int var;

    public class SecondLevel {

        private int var;

        SecondLevel() {
            this.var = FirstLevel.this.var + 5;
        }
    }
```

