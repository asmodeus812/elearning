1. What symbol is used for a varargs method parameter?

In java there is only one valid way to express the variable arguments parameter to a function, that is by using the `...` method parameter

A. ..
B. `...`
C. --
D. ---

2. Fill in the blank in the following code to get the first element from the varargs parameter.

```java
public void toss (Frisbee... f) {
Frisbee first = ____________;
}
```

To get the first element from a variable arguments parameter of a function we can use it just like we would use a
regular array, that is what the compiler creates under the hood anyway

A. f
B. `f[0]`
C. f[1]
D. None of the above

3. Which of the following are primitives?

```java
int[] lowercase = new int[0];
Integer[] uppercase = new Integer[0];
```

Technically both of these are considered object references, and object references are not primitives, the primitives
in java are. A primitive type can not be a reference object

A. Only lowercase
B. Only uppercase
C. Bother lowercase and uppercase
D. `Neither lowercase nor uppercase`

4. How many of the following are legal declarations?

```java
[]double lion;
double[] tiger;
double bear[];
```

There are really two ways to define arrays in java that would be either specifying the array brackets after the
type, or after the variable, both have different meaning due to chain definition of multiple variables on the same
line, it is preferred to choose and put the brackets after the name of the variable to avoid confusion.

```java
double arrayOfDoubles[], oneDouble
double[] arrayOfDoubles1, arrayOfDoubles2;
```

A. None
B. One
C. `Two`
D. Three

5. Given the following two methods, which method call will NOT compile?

```java
public void printStormName(String... names) {
    System.out.println(Arrays.toString(names));
}
public void printStormNames(String[] names) {
    System.out.println(Arrays.toString(names));
}
```

A. printStormName("Arlene");
B. printStormName(new String[] { "Bret" });
C. printStormNames("Cindy");
D. printStormNames(new String[] { "Don" });

6. How do you determine the number of elements in an array?

The array object has a header and metadata allocated to it this header metadata holds the information for the length
of the array, and that is what the compiler uses to verify things like accessing index that is out of bounds, etc.

A. `buses.length`
B. buses.length()
C. buses.size
D. buses.size()

7. Which of the following create an empty two-dimensional array with dimensions 2×2?

The syntax for multi dimensional arrays in java is pretty simple every dimension it is declared as []

A. int[][] blue = new int[2, 2];
B. int[][] blue = new int[2], [2];
C. `int[][] blue = new int[2][2];`
D. int[][] blue = new int[2 x 2];

8. How many lines does the following code output?

```java
String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
for (int i = 0; i < days.length; i++) {
    System.out.println(days[i]);
}
```

This code just prints out the days of the week every one new line.

A. Six
B. `Seven`
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

9. What are the names of the methods to do searching and sorting respectively on arrays?

These two methods are actually closely related because the binary search requires a sorted array before it can
perform the search, and unless a custom comparator is used usually that sorting has to be done in ascending order

A. Arrays.binarySearch() and Arrays.linearSort()
B. `Arrays.binarySearch() and Arrays.sort()`
C. Arrays.search() and Arrays.linearSort()
D. Arrays.search() and Arrays.sort()

10. What does this code output?

```java
String[] nums = new String[] { "1", "9", "10" };
Arrays.sort(nums);
System.out.println(Arrays.toString(nums));
```

The code simply sorts in ascending order using the natural order of the elements in this case the strings are sorted
in an lexicographic order that means that the order of the elements are based on the position of their chars in the
ASCII (UTF) table

A. [1, 9, 10]
B. `[1, 10, 9]`
C. [10, 1, 9]
D. None of the above

11. Which of the following references the first and last element in a non-empty array?

The first element index is always the 0-th one, and the final one must be the length - 1

A. trains[0] and trains[trains.length]
B. `trains[0] and trains[trains.length - 1]`
C. trains[1] and trains[trains.length]
D. trains[1] and trains[trains.length - 1]

12. How many of the following are legal declarations?

```java
String lion [] = new String[] {"lion"}; // that is legal, array of one element
String tiger [] = new String[1] {"tiger"}; // illegal declares the size & init block
String bear [] = new String[] {}; // that is legal, empty array, no elements
String ohMy [] = new String[0] {}; // illegal declares the size & init block
```

There are two correct declarations here, in java it is not allowed to have a size of the array declared if the array
is also provided and initialized with a initialization list, either use and declare a size, and manually set the
elements one by one in a loop or whatever, or provide NO SIZE and an initialization list.

A. None
B. One
C. `Two`
D. Three

13. How many of the following are legal declarations?

```java
float[] lion = new float[]; // invalid missing initialization list, and size
float[] tiger = new float[1]; // valid, size is provided, no initialization list
float[] bear = new[] float; // invalid missing the type on the right hand side
float[] ohMy = new[1] float; // invalid missing the type on the right hand side
```

In the example above there is only one valid declaration that matches the array rules in java, that is the second
one, which correctly provides size, but no initialization list, that is the correct form in this case in contrast to
the question before

A. None
B. `One`
C. Two
D. Three

14. Which statement most accurately represents the relationship between searching and sorting with respect to the Arrays class?

The binary search in Array requires the array to be sorted that is how the binary search algorithm works in the
first place, not only that but the same natural ordering used to sort the array must be used to search with binary
search otherwise you will get incorrect results

A. If the array is not sorted, calling Arrays.binarySearch() will be accurate, but slower than if it were sorted.
B. The array does not need to be sorted before calling Arrays.binarySearch() to get an accurate result.
C. `The array must be sorted before calling Arrays.binarySearch() to get an accurate result.`
D. None of the above

15. Which is not a true statement about an array?

There is really only two statements here that are correct, and non-ambigious. The arrays are allowed to hold
duplicate values because they are non-complex data structures that have no concept or equality or uniqueness. The
second statement clearly defines that the array elements start by referencing the 0-th element

A. An array expands automatically when it is full.
B. `An array is allowed to contain duplicate values.`
C. An array understands the concept of ordered elements.
D. `An array uses a zero index to reference the first element.`

16. Which line of code causes an ArrayIndexOutOfBoundsException?

```java
String[][] matrix = new String[1][2];
1. matrix[0][0] = "Don't think you are, know you are.";
2. matrix[0][1] = "I'm trying to free your mind Neo";
3. matrix[1][0] = "Is all around you ";
4. matrix[1][1] = "Why oh why didn't I take the BLUE pill?";
```

The very first line that is going to cause this exception is line 3, however line 4 is also incorrect, remember the
code above declares an array that is 2-dimensional but it holds one element (in its first dimension) of type array
(second dimension), that is of size 2. Therefore accessing index 1, as it is done on line 3 is not possible

A. 1
B. 2
C. `3`
D. 4

17. What does the following output?

```java
String[] os = new String[] { "Mac", "Linux", "Windows" };
Arrays.sort(os);
System.out.println(Arrays.binarySearch(os, "Mac"));
```

This code is correct the code sorts first the array of elements, and then binary searches, the actual array will be
sorted like such - Linux, Mac, Windows, and the target value "Mac" is located on index 1, that is what the binary
search returns. The result would have been indeterminate had the array not been sorted (maybe, that highly depends
on which element we are looking for and if it randomly happens to be in the correct left/right sub-arrays, by
chance)

A. 0
B. `1`
C. 2
D. The output is not defined.

18. Which is the first line to prevent this code from compiling and running without error?

```java
char[][] ticTacToe = new char[3,3]; // r1
ticTacToe[1][3] = 'X'; // r2
ticTacToe[2][2] = 'X';
ticTacToe[3][1] = 'X';
System.out.println(ticTacToe.length + " in a row!"); // r3
```

The very first line that declares the two dimensional array is incorrect or what would be an attempt at declaring a
two dimensional array

A. `Line r1`
B. Line r2
C. Line r3

19. How many objects are created when running the following code?

```java
Integer[] lotto = new Integer[4];
lotto[0] = new Integer(1_000_000);
lotto[1] = new Integer(999_999);
```

This is a bit tricky, technically the array holds space for 4, but these are just reference slots, they are sized
for pointers, because the array holds reference objects. The actual objects that are allocated with a correct header
and metadata by the runtime are 3. That is the array object itself, and the two integers that are assigned to the
first and second slots in the array.

A. Two
B. `Three`
C. Four
D. Five

20. How many of the following are legal declarations and definition?

```java
[][] String alpha; // invalid, two dimension declaration
[] String beta; // invalid, one dimensional array declaration
String[][] gamma; // valid, it is a legal definition
String[] delta[]; // valid, that declares two dimensional array
String epsilon[][]; // valid, declares two dimensional array
```

The one that might somewhat trip somebody is maybe the 4-th attempt that declares String[] delta[], that actually
works in a peculiar way, because the actual result will be a two dimensional array. Why is that the case the type
definition here is actually String[] not String, that means that everything that is on the right of that type
declaration is actually of type String[] not just String meaning that the delta variable become of type String[][]
or in other words a two dimensional array

A. Two
B. `Three`
C. Four
D. Five

22. What happens when calling the following method with a non-null and non-empty array?

```java
public static void addStationName(String[] names) {
    names[names.length] = "Times Square";
}
```

The array element being accessed here is invalid, there is no valid array element index that is equal to the
names.length, the last valid index is always the `names.length - 1`

A. It adds an element to the array the value of which is Times Square.
B. It replaces the last element in the array with the value Times Square.
C. It does not compile.
D. `It throws an exception.`

23. How many lines does the following code output?

```java
String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
for (int i = 0; i < days.size(); i++) {
    System.out.println(days[i]);
}
```

The code is using incorrect de-reference action here by referencing the .size method, which does not exist on an
array object in java, that is a method that exists on objects that implement the Collection interface which plain
primitive arrays are not in java

A. Six
B. Seven
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

24. How many dimensions does the array reference moreBools allow?

```java
boolean[][][] bools, moreBools;
```

The array brackets are exactly appended to the type definition, therefore any variable defined after is of at least
that size, array

A. One dimension
B. Two dimensions
C. `Three dimensions`
D. None of the above

25. What is a possible output of the following code?

```java
String[] strings = new String[2];
System.out.println(strings);
```

Unlike Collection based implementations the plain primitive arrays in java do not allow straight up printing out
like that because they do not implement toString, that is why methods like Arrays.toString exist - to print out
primitive arrays. This example will simply print the reference of the object strings

A. [null, null]
B. [,]
C. `[Ljava.lang.String;@74a14482`
D. None of the above

26. Which is the first line to prevent this code from compiling and running without error?

```java
char[][] ticTacToe = new char[3][3]; // r1
ticTacToe[1][3] = 'X'; // r2
ticTacToe[2][2] = 'X';
ticTacToe[3][1] = 'X';
System.out.println(ticTacToe.length + " in a row!"); // r3
```

That line is the very first one that will throw an exception, that is because the second dimension that it is trying
to access is out of bounds, the second dimension here does not have index '3' it has 3 elements therefore indices
ranging from 0-2 inclusive

A. Line r1
B. `Line r2`
C. Line r3
D. None of the above

27. What is the result of running the following as java Copier?

```java
package duplicate;
public class Copier {
    public static void main(String... original) {
        String... copy = original;
        System.out.println(copy.length + " " + copy[0]);
    }
}
```

The code will not compile because the ellipsis operator is not used correctly, it is not possible to do this in Java
unlike other languages like javascript which may allow you to expand the variable arguments parameter the only valid
assignment we can do here is to assign that parameter to a local variable of type array.

```java
String... copy = original; // this is invalid variable declaration, we can not do this in local variable declaration
String[] copy = original; // this is however a valid reference to the variable arguments parameter of the method
```

A. 0
B. 0 followed by an exception
C. 1 followed by an exception
D. `The code does not compile.`

28. What is the result of running the following program?

```java
public class Sudoku {
    static int[][] game = new int[6][6];
    public static void main(String[] args) {
        game[3][3] = 6;
        Object[] obj = game;
        obj[3] = "X";
        System.out.println(game[3][3]);
    }
}
Sudoku.main(new String[0])
```

This one is a bit tricky. All arrays in java are covariant, that means that we can correctly implicitly cast the
game array object to an Object[] and the compiler will not complain, however that means that any code afterwards
will be able to add anything that is object to that array and that is not valid. Java runtime performs runtime
checks to verify that what ever is being added to the array even at runtime is of the correct type, in this case we
add to the array through the obj reference a string, but the array is defined to hold only int primitive types
therefore a runtime exception will suppose

A. X
B. The code does not compile.
C. The code compiles but throws a NullPointerException at runtime.
D. `The code compiles but throws a different exception at runtime.`

29. What does the following output?

```java
String[] os = new String[] { "Mac", "Linux", "Windows" };
Arrays.sort(os);
System.out.println(Arrays.binarySearch(os, "RedHat"));
```

When an object or a target is not found by binary search it will return a negative result -1 indicating that the
target was not found in the array, because negative indices are not valid for array positions that should indicate
to the caller that the item was not in fact found in the array.

A. `-1`
B. -2
C. -3
D. The output is not defined.

30. What is the output of the following when run as java FirstName Wolfie?

```java
public class FirstName {
    public static void main(String... names) {
        System.out.println(names[0]);
    }
}
```

The first element of the array when calling a regular program from the command line his the name of the program,
however in java when we start a program the actual program we are executing is java followed by the name of the
class we are telling the java runtime to load and run, the java runtime itself will then pass the arguments (usually
space separated) after the name of the class to the argv (variable arguments) argument in main, therefore that would
be Wolfie

A. FirstName
B. `Wolfie`
C. The code throws an ArrayIndexOutOfBoundsException.
D. The code throws a NullPointerException.

31. What is the output of the following when run as java Count 1 2?

```java
public class Count {
    public static void main(String target[]) {
        System.out.println(target.length);
    }
}
```

When we execute this program the actual contents of the target will be two elements containing the two arguments we
have passed to the command those would be "1" and "2", therefore the length of the array will be 2

A. 0
B. 1
C. `2`
D. The code does not compile.

31. What is the output of the following when run as java unix.EchoFirst seed flower?

```java
import java.util.*;
public class EchoFirst {
    public static void main(String[] args) {
        String one = args[0];
        Arrays.sort(args);
        int result = Arrays.binarySearch(args, one);
        System.out.println(result);
    }
}
EchoFirst.main(new String[] {"seed", "flower"})
```

The code will compile and run, the two elements that args contains are "seed" and "flower" the target element that
is being searched for is "seed" (the first one) after sorting it, the array will be {"flower", "seed"}, and the
target "seed" is now at position or index 1, in the array.

A. 0
B. `1`
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

32. Which of these four array declarations produces a different array than the others?

These examples all (but the last one) define and declare a two dimensional array, where the first dimension is of
size 2, and the second is of size 1. Meaning that they declare array of arrays, where each of the two top level
arrays, are of size one i.e. nums[2][1]. Only the last example does define something different, a two dimensional
array of size one, where the top level array is of size 2 i.e the dimensions are inverted - nums[1][2]

A. int[][] nums = new int[2][1];
B. int[] nums[] = new int[2][1];
C. int[] nums[] = new int[][] { { 0 }, { 0 } };
D. `int[] nums[] = new int[][] { { 0, 0 } };`

33. How many lines does the following code output?

```java
String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
for (int i = 1; i <= days.length; i++) {
    System.out.println(days[i]);
}
```

The code is defined well, and does not produce any compiler error, but the for loop is wrong, the range is from 1 to
days.length inclusive, that will throw IndexOutOfBoundsExcetpion on the last iteration of the loop

A. Six
B. Seven
C. The code does not compile.
D. `The code compiles but throws an exception at runtime.`

34. What is the output of the following when run as java FirstName Wolfie?

```java
public class FirstName {
    public static void main(String... names) {
        System.out.println(names[1]);
    }
}
```

Just as the example above, the issue here is the same the program is called with one argument but we are accessing
the second one by index (1), that will throw at runtime

A. FirstName
B. Wolfie
C. `The code throws an ArrayIndexOutOfBoundsException.`
D. The code throws a NullPointerException.

35. Which is the first line to prevent this code from compiling and running without error?

```java
char[][] ticTacToe = new char[3][3]; // r1
ticTacToe[0][0] = 'X'; // r2
ticTacToe[1][1] = 'X';
ticTacToe[2][2] = 'X';
System.out.println(ticTacToe.length + " in a row!"); // r3
```

There is nothing really wrong here, all of the index accesses are right, the array declaration as well, and the
print statement too.

A. Line r1
B. Line r2
C. Line r3
D. `None of the above`
