1. Which of the following declarations does NOT compile?

The rest of the examples besides the first one all tackle the same type, on the same line that is correct, in java
we can initialize the variables on the same line, we are allowed to initialize none, or one or two or any number of
variables that were declared and defined,

A. `double num1, int num2 = 0;`
B. int num1, num2;
C. int num1, num2 = 0;
D. int num1 = 0, num2 = 0;

2. What is the output of the following?

```java
public class TestNullConcat {
    public static void main(String... args) {
        String chair, table = "metal";
        chair = chair + table;
        System.out.println(chair);
    }
}
```

A few things, that might catch somebody, first the way the argument for main is defined, usually we see that defined
as String[] but that is functionally equivalent to String... so the type is defined well. Second is the fact that
chair by default is not-initialized, it is not null, but rather has an initialized value. This will trip the
compiler, however had we initialized chair = null, then the concatenation will produce 'nullmetal'. This example will not compile because the variable is not initialized

```java
// this small change will compile and also correctly concatenate the strings, producing nullmetal, it is strange
// that by default the java language interprets string operations with null values like that but that is made to
// safeguard certain operations from NullPointerExcetpion.
String chair = null, table = "metal";
chair = chair + table;
System.out.println(chair);
```

A. metal
B. metalmetal
C. nullmetal
D. `The code does not compile.`

3. Which is correct about an instance variable of type String?

`A local variable (§14.4, §14.14) must be explicitly given a value before it is used, by either initialization
(§14.4) or assignment (§15.26), in a way that can be verified using the rules for definite assignment (§16 (Definite
Assignment)).`

So in java local variable are not by default initialized, unlike class variable members which have default to null,
therefore the compiler will indeed complain here if we try to use the variable without it first being initialized,
it does not really matter if the variable is String or of any other type, is not initialized.

A. It defaults to an empty string.
B. It defaults to null.
C. It does not have a default value.
D. `It will not compile without initializing on the declaration line.`

4. Which of the following is NOT a valid variable name?

The naming rules are not that hard to remember

- they can start with a letter, upper or lower case
- they can also start with underscore or dollar sign
- they can NOT start with any other sign
- they can NOT start with a number
- they can contain numbers, and digits
- they can not contain special symbols which are used by the language for operations and operators - \*,/+,-,>,<,=
  and so on.

A. \_blue - this one is valid it starts with underscore
B. `2blue`- not valid, they can not start with a number or digit but can contain one
C. blue$ - this one is also valid the dollar sign symbol does not have special meaning
D. Blue - they can start with upper case letters as well and that is no restriction

5. Which of these class names best follows standard Java naming conventions?

Java follows the camelCase naming rules for standard variables - local and member variables, for certain specific
use cases such as constants we can use the format which uppercase all letters such as - FOO_BAR

A. `fooBar`
B. FooBar
C. `FOO_BAR`
D. F_o_o_B_a_r

6. How many of the following methods compile?

```java
public String convert(int value) {
    // calling a method on the primitive integer type is not a valid thing
    return value.toString();
}
public String convert(Integer value) {
    // calling toString on this argument is valid and perfectly proper thing to do, inherited from Object directly
    return value.toString();
}
public String convert(Object value) {
    // same as above the toString method is actually inherited from Object implicitly by any other class type in
    // the java language
    return value.toString();
}
```

A. None
B. One
C. `Two`
D. Three

7. Which of the following does not compile?

In the language it is possible and allowed to separate integral and floating point literal declarations and
initializations with an underscore, that way these literals are easier to read.

A. int num = 999;
B. int num = 9_9_9;
C. `int num = \_9_99`;
D. None of the above; they all compile.

8. Which of the following is a wrapper class?

Only one of those are a true wrapper class, that is Integer wrapper for the primitive type int

A. int
B. Int
C. `Integer`
D. Object

9. What is the result of running this code?

```java
public class Values {
    // in this example this is where the code will actually immediately throw compiler error, because of the type
    // of this variable is 'integer', the issue being the lowercase 'i'
    integer a = Integer.valueOf("1");
    public static void main(String[] nums) {
        integer a = Integer.valueOf("2");
        integer b = Integer.valueOf("3");
        System.out.println(a + b);
    }
}
```

In this example we have an error on the first line where the first member variable for the class Values is defined,
the variable a is declared of type 'integer' that is not a valid type in java, java types and classes start with an
upper case letter, and this is even more obvious when talking about wrapper types

A. 4
B. 5
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

10. Which best describes what the new keyword does?

A. Creates a copy of an existing object and treats it as a new oneB. Creates a new primitive
C. `Instantiates a new object`
D. Switches an object reference to a new one

11. Which is the first line to trigger a compiler error?

```java
1: double d1 = 5f;
2: double d2 = 5.0;
3: float f1 = 5f;
4: float f2 = 5.0;
```

The first line is valid, up-casting from float to double is allowed in the language, the declaration of the literal
is also valid, 5f; and 5.f are both valid declarations. The second line is also valid because all decimal point
literals in java are by default double not float. The third line is valid we have explicit float declaration for the
literal and the left hand side is float too

The line that is not valid is the fourth one, because the literal is expressing a double, but the type of the
variable is float, and automatic down-casting without a cast operation is not permitted in java.

A. 1
B. 2
C. 3
D. `4`

12. Which of the following lists of primitive types are presented in order from smallest to largest data type?

- byte is 1 byte wide in java
- char is 2 bytes wide in java
- float is 4 bytes long
- double is 6-8 byte long (????)

A. `byte, char, float, double`
B. byte, char, double, float
C. char, byte, float, double
D. char, double, float, bigint

13. Which of the following is not a valid order for elements in a class?

A. Constructor, instance variables, method names
B. Instance variables, constructor, method names
C. Method names, instance variables, constructor
D. `None of the above: all orders are valid.`

14. Which of the following lines contains a compiler error?

```java
1: String title = "Weather";
2: int hot, double cold;
3: System.out.println(hot + " " + title);
```

The second line has multiple issues, first it is using a keyword 'double' in the middle of the declaration,
attempting to create a variable named 'cold' of double type, even if we put a comma after double, attempting to
create a variable named double that would still not be possible because the keyword double is reserved and

A. 1
B. `2`
C. 3
D. None of the above

15. How many instance initializers are in this code?

```java
public class Bowling {
    {
        System.out.println();
    }
    public Bowling() {
        System.out.println();
    }
    static {
        System.out.println();
    }
    {
        System.out.println();
    }
}
```

A. None
B. One
C. Two
D. `Three`

16. Of the types double, int, and short, how many could fill in the blank to have this code output 0?

```java
public static void main(String[] args) {
_____ defaultValue;
System.out.println(defaultValue);
}
```

This is a slightly misleading question, because while all three double, int and short are by default initialized to
0, however, double will actually be initialized to 0.0, so the println will actually print 0.0 not 0

A. None
B. One
C. `Two` - int and short both init to 0
D. Three - double will be actually 0.0

17. What is true of the finalize() method?

It will be called exactly once when the garbage collector invokes it, when the object is being freed.

A. It may be called zero or one times.
B. It may be called zero or more times.
C. `It will be called exactly once.`
D. It may be called one or more times.

18. Which of the following is not a wrapper class?

That is mostly straightforward the only non-wrapper class is String, the rest are specifically wrapper classes
around their primitive types. String also technically wraps around char[] but that is implementation detail

A. Double
B. Integer
C. Long
D. `String`

19. Suppose you have the following code. Which of the descriptions best represents the state of the references right
    before the end of the main method, assuming garbage collection hasn’t run?

```java
public class Link {
    private String name;
    private Link next;
    public Link(String name, Link next) {
        this.name = name;
        this.next = next;
    }
    public void setNext(Link next) {
        this.next = next;
    }
    public Link getNext() {
        return next;
    }
    public static void main(String... args) {
        Link link1 = new Link("x", null);
        Link link2 = new Link("y", link1);
        Link link3 = new Link("z", link2);
        link2.setNext(link3);
        link3.setNext(link2);
        link1 = null;
        link3 = null;
    }
}
```

The two variables link1 and link2 both are re-assigned to null, however their actual data is still referenced by the
link2 variable, link2 was set to reference link3, and link3 was set to reference link2 right before their variables
were null assigned. So link2 actually has a circular reference to itself by proxy by being linked to link3 (which
itself references link2)

```
var link1 = null
var link3 = null
var link2 -> (ref) link3 <-> (ref) link2
```

20. Which type can fill in the blank?
    **\_\_\_** pi = 3.14;

Only the double can fill up this empty space here because the variable pi is initialized to 3.14 literal, by default
that is double

Attempting to put float on the left hand side will produce a compiler error, because the compiler can not down-cast
to a lesser type automatically without us manually casting it ourselves

A. byte
B. float
C. `double`
D. short

21. What is the first line in the following code to not compile?

```java
public static void main(String[] args) {
    int Integer = 0; // k1
    Integer int = 0; // k2
    Integer ++; // k3
    int++; // k3
}
```

This one might be tricky but the second line in the main method uses a name for the variable that is invalid, it
uses a keyword, more precisely it uses the name of a primitive for a variable name

A. k1
B. `k2`
C. k3
D. k4

22. Suppose foo is a reference to an instance of a class. Which of the following is not true
    about foo.bar?

Only one of these statements is false, IF AND ONLY IF the bar member variable is defined public and
non-final, we lack this information therefore we assume that this is a normally defined member variable in
the type that foo is instantiated from.

A. bar is an instance variable.
B. `bar is a local variable.`
C. It can be used to read from bar.
D. It can be used to write to bar.

23. Which of the following is not a valid class declaration?

Just like the naming convention for variables the same ones apply to the naming rules for types, we are not allowed
to start a name with a digit

A. class building {}
B. class Cost$ {}
C. `class 5MainSt {}`
D. class \_Outside {}

24. Which of the following can fill in the blanks to make this code compile?

The one that is only valid is double + Double, that is purely working because the compiler is pro-active and uses
auto-unboxing in this case to unbox the Double created on the right hand side of the expression to a double
primitive effectively translating into - new Double({value}).doubleValue(), when it gets comopiled

\_**\_ d = new \_\_**(1*000_000*.00);
A. double, double
B. `double, Double`
C. Double, double
D. None of the above

25. Which is correct about a local variable of type String?

By default we have already established that ALL LOCAL REFERENCE variables do not have any initialization, while that
is not true for primitive which are initialized to 0, by default. However MEMBER instance REFERENCE variables are
initialized by default to NULL

A. It defaults to an empty string.
B. It defaults to null.
C. It does not have a default value.
D. `It will not compile without initializing on the declaration line.`

26. Of the types double, int, long, and short, how many could fill in the blank to have this code output 0?

```java
static **\_\_\_**defaultValue;
public static void main(String[] args) {
    System.out.println(defaultValue);
}
```

A. One
B. Two
C. `Three` - long, int, short, all initialized to just 0 by default
D. Four - double not included because the output will be 0.0 not 0

27. Which of the following is true about primitives?

The only one that might trip people is the value of however the wrapper types provide valueOf but to convert from a
primitive or a string back to the wrapper type, not the other way around - convert wrapper into primitive

A. You can call methods on a primitive.
B. `You can convert a primitive to a wrapper class object simply by assigning it.`
C. You can convert a wrapper class object to a primitive by calling valueOf().
D. You can store a primitive directly into an ArrayList.

28. What is the output of the following?

```java
Integer integer = new Integer(4);
System.out.print(integer.byteValue());
System.out.print("-");
int i = new Integer(4);
System.out.print(i.byteValue());
```

Extra care has to be taken here, since the very last line attempts to de-reference something from the primitive
type, which is not allowed,

A. 4-0
B. 4-4
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

29. Given the following code, fill in the blank to have the code print bounce.

```java
public class TennisBall {
    public TennisBall() {
        System.out.println("bounce");
    }
    public static void main(String[] slam) {
        ____________
    }
}
```

A. TennisBall;
B. TennisBall();
C. new TennisBall;
D. `new TennisBall()`;

30. Which of the following correctly assigns animal to both variables?

```
I.   String cat = "animal", dog = "animal";
II.  String cat = "animal"; dog = "animal";
III. String cat, dog = "animal";
IV.  String cat, String dog = "animal";
```

Declaration I is correct declares two variables of type string on the same line. The II declaration is not right
because it is missing the type declaration for type String in front of the dog variable.

A. `I`
B. I, II
C. I, III
D. I, II, III, IV

31. Which two primitives have wrapper classes that are not merely the name of the primitive with an uppercase letter?

The wrapper classes are named Byte, Integer, Character, none of the combinations below match that, since all of them
include at least one incorrect one

A. byte and char
B. byte and int
C. char and int
D. `None of the above`

32. Which of the following is true about String instance variables?

    A. They can be set to null.
    B. They can never be set from outside the class they are defined in.
    C. They can only be set in the constructor.
    D. They can only be set once per run of the program.

33. Which statement is true about primitives?

A. `Primitive types begin with a lowercase letter.`
B. Primitive types can be set to null.
C. String is a primitive.
D. You can create your own primitive types.

34. How do you force garbage collection to occur at a certain point?

The correct statement is to invoke the gc static method from the System class, which will force the garbage
collector to attempt to run internally, that does not however guarantee that it will run, it serves as mere
suggestion to the collector to run

A. Call System.forceGc()
B. Call `System.gc()`
C. Call System.requireGc()
D. None of the above

34. How many of the String objects are eligible for garbage collection right before the end
    of the main method?

```java
public static void main(String[] fruits) {
    String fruit1 = new String("apple");
    String fruit2 = new String("orange");
    String fruit3 = new String("pear");
    fruit3 = fruit1;
    fruit2 = fruit3;
    fruit1 = fruit2;
}
```

reference to fruit3 lost, assigned to fruit1
reference to fruit2 lost, assigned to fruit3 (fruit1)
reference to fruit1 kept transitively through fruit2 -> fruit3

A. None
B. One
C. `Two`
D. Three

35. Which of the following can fill in the blanks to make this code compile?

```java
    **\_\_\_**d = new**\_\_\_** (1_000_000.00);
```

A. double, double
B. double, Double
C. Double, double
D. None of the above

36. What does the following output?

```java
public class InitOrder {
    static String method() {
        System.out.println("method");
        return "method";
    }
    public String first = method();
    public InitOrder() {
        System.out.println("constructor");
        first = "constructor";
    }
    {
        first = "block";
        System.out.println("block");
    }
    public void print() {
        System.out.println(first);
    }
    public static void main(String... args) {
        new InitOrder().print();
    }
}
```

The init order here will be as follows, the example was slightly modified to demonstrate the order of initialization
that will occur for this member variable. In the end the member variable will end up with the value constructor

- member type declaration - method
- initialization block - block
- type class constructor - constructor

A. block
B. `constructor`
C. instance
D. The code does not compile.

37. How many of the following lines compile?

```java
int i = null; // invalid
Integer in = null; // valid
String s = null; // valid
```

A. None
B. One
C. `Two`
D. Three

38. Which pairs of statements can accurately fill in the blanks in this table? Variable Type Can be called within
    the class from what type of method

```java
InstanceBlank 1:**\_\_\_**
StaticBlank 2: **\_\_\_**
```

????? WHAT IS THIS EVEN

A. Blank 1: an instance method only, Blank 2: a static method only
B. Blank 1: an instance or static method, Blank 2: a static method only
C. Blank 1: an instance method only, Blank 2: an instance or static method
D. Blank 1: an instance or static method, Blank 2: an instance or static method

39. Which of the following does not compile?

The only one that is really prohibited here is having the digit delimiter without any digit in front of it in this
case the B example has an underscore right before the dot, which is not right, but other than that, the rest of the
examples are right

A. `double num = 2.718;`
B. double num = 2.\_718;
C. `double num = 2.7_1_8;`
D. None of the above; they all compile.

40. Which of the following lists of primitive numeric types is presented in order from smallest to largest data type?

A. `byte, short, int, long`
B. int, short, byte, long
C. short, byte, int, long
D. short, int, byte, long

42. Fill in the blank to make the code compile:

```java
package animal;
public class Cat {
    public String name;
    public static void main(String[] meow) {
        Cat cat = new Cat();
        **\*\***\_\_**\*\*** = "Sadie";
    }
}
```

A. `cat.name`
B. cat-name
C. cat.setName
D. cat[name]

41. Which of the following is the output of this code, assuming it runs to completion?

```java
public class Toy {
    public void play() {
        System.out.print("play-");
    }
    public void finalizer() {
        System.out.print("clean-");
    }
    public static void main(String[] fun) {
        Toy car = new Toy();
        car.play();
        System.gc();
        Toy doll = new Toy();
        doll.play();
    }
}
```

There is no method called finalizer, even if it was the finalize method that was intended to be overridden is from
the Object class it will still not be invoked here because car variable still holds reference to the first Toy
instance, and the garbage collector can see that it has not been re-assigned

A. play-
B. `play-play-`
C. play-clean-play-
D. play-play-clean-clean-

42. Which is the most common way to fill in the blank to implement this method?

```java
public class Penguin {
    private double beakLength;
    public static void setBeakLength(Penguin p, int b) {
        ____________
    }
}
```

A. `p.beakLength = b;`
B. p\['beakLength'\] = b;
C. p\[beakLength\] = b;
D. None of the above

45. Fill in the blanks to indicate whether a primitive or wrapper class can be assigned without the compiler using the autoboxing feature.

```java
**\_\_\_**first = Integer.parseInt("5");
**\_\_\_**second = Integer.valueOf("5");
```

As mentioned above, the valueOf methods deal with actually generating a value of the wrapper types, while the parseInt
in this case will return plain primitive

A. int, int
B. `int, Integer`
C. Integer, int
D. Integer, Integer

46. How many objects are eligible for garbage collection right before the end of the main
    method?

```java
public class Person {
    public Person youngestChild;
    public static void main(String... args) {
        Person elena = new Person();
        Person diana = new Person();
        elena.youngestChild = diana;
        diana = null;
        Person zoe = new Person();
        elena.youngestChild = zoe;
        zoe = null;
    }
}
```

First the reference to Diana was not lost, by re-assigning the variable to null, but the reference was still kept in
youngestChild, however later on the same reference from Elena that was keeping track of Diana is re-assigned to the
new Zoe instance, thus losing the reference to Diana, that object can then be considered for garbage collection
precisely because it is not longer used anywhere, and no longer can be referenced by anything

A. None
B. `One`
C. Two
D. Three

47. Which is a valid constructor for this class?

```java
    public class TennisBall {
    }
```

A. public TennisBall static create() { return new TennisBall(); }
B. public TennisBall static newInstance() { return new TennisBall():}
C. `public TennisBall() {}`
D. public void TennisBall() {}

48. Which of the following is not a possible output of this code, assuming it runs to completion?

```java
public class Toy {
    public void play() {
        System.out.print("play-");
    }
    public void finalize() {
        System.out.print("clean-");
    }
    public static void main(String[] args) {
        Toy car = new Toy();
        car.play();
        System.gc();
        Toy doll = new Toy();
        doll.play();
    }
}
```

A. `play-`
B. play-play-
C. `play-play-clean-`
D. `play-play-clean-clean-`

49. Which converts a primitive to a wrapper class object without using autoboxing?

The only non auto-boxing form the options presented that is valid is basically constructing the wrapper class type
from calling their constructor using the primitive type

A. Call the asObject() method
B. `Call the constructor of the wrapper class`
C. Call the convertToObject() method
D. Call the toObject() method

50. What is the output of the following?

```java
public class Sand {
    public Sand() {
        System.out.print("a");
    }
    public void Sand() {
        System.out.print("b");
    }
    public void run() {
        new Sand();
        Sand();
    }
    public static void main(String... args) {
        new Sand().run();
    }
}
```

The class will not compiler, there is an instance non-static method that is with the same name as the class
constructor,

A. a
B. ab
C. aab
D. `None of the above`
