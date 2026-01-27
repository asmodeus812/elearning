1. What is the best reason for using `StringBuilder` instead of String?

The existence of a string builder is mostly to avoid the nature of native java strings which are immutable. String
builder allows you to actually mutate and append/insert/remove/delete string content, without each and every
operation resulting in a new string.

A. StringBuilder adds support for multiple threads.
B. StringBuilder can use == to compare values.
C. `StringBuilder saves memory by reducing the number of objects created.`
D. StringBuilder supports different languages and encodings.

2. What is not true about a String?

Strings are NOT mutable, they are interned, they can also be created without the use of their constructor, which
actually puts them in the data section of the program or the internal string pool interning them. They are final, in
the sense that they are not mutable not that every variable is actually final

A. It can be created without coding a call to a constructor.
B. It can be reused via the string pool.
C. It is final.
D. `It is mutable.`

3. Which of the following creates a `StringBuilder` with a different value than the other options?

The insert here will actually append at the end of the builder the reason for that is because the insert method
allows us to insert at the string builder length position, which is the way of saying add to the very end of the
builder, otherwise we would have been forced to always use append when we want to insert at the end, this is a
special case for the insert method that gives us more flexibility effectively the same as using append

A. new StringBuilder().append("clown")
B. new StringBuilder("clown")
C. new StringBuilder("cl").insert(2, "own")
D. `All of them create the same value.`

4. What is the output of the following?

```java
StringBuilder teams = new StringBuilder("333");
teams.append(" 806");
teams.append(" 1601");
System.out.print(teams);
```

This is a trick question, you might think that it will produce a compiler error, but that is not the case by default
the toString method will be invoked which is overridden for the StringBuilder, actually it will produce valid
output, printing data.

A. 333
B. `333 806 1601`
C. The code compiles but outputs something else.
D. The code does not compile.

5. How many of the types `ArrayList`, List, and Object can fill in the blank to produce code that compiles?

```java
List frisbees = new ____________();
```

We can only create array list, the rest like List which is an interface we can not create an instance of and we can
also not assign new Object to List, even though the Object class as we know has a `no-args constructor` that is public
and accessible

A. None
B. `One`
C. Two
D. Three

6. What is the output of the following?

```java
List<String> tools = new ArrayList<>();
tools.add("hammer");
tools.add("nail");
tools.add("hex key");
System.out.println(tools.get(1));
```

The get method accesses elements from the list by index, the list is 0 based therefore the second element that was
ever inserted in the list will be printed out "nail"

A. hammer
B. hex key
C. `nail`
D. None of the above

7. What is the result of the following code?

```java
StringBuilder sb = new StringBuilder("radical").insert(sb.length(), "robots");
System.out.println(sb);
```

The builder has a char sequence constructor, so we can create initial values of the string builder like shown, The
insert method returns the string builder instance similarly to the Stream API in java, it is done so that we can
easily chain operations on the object.

However the way the 'sb.length' argument is passed to the insert method is not possible, at this point on the line
the expression is not evaluated and the 'sb' symbol does not exist.

A. radicarobots
B. radicalrobots
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

8. What is the output of the following?

```java
List<String> museums = new ArrayList<>(1);
museums.add("Natural History");
museums.add("Science");
museums.add("Art");
museums.remove(2);
System.out.println(museums);
```

The example creates an array list with capacity of 1, the list has a few items added to it, and the last one at
index 2 is removed, that is valid because the list has 3 items, the type of the list is string, we are adding 3
string elements into it. It will remove the last one leaving only two elements.

A. `[Natural History, Science]`
B. [Natural History, Art, Science]
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

9. What is the output of the following?

```java
StringBuilder b = new StringBuilder("12");
b = b.append("3");
b.reverse();
System.out.println(b.toString());
```

The reverse method exists on a string builder and it does what the name implies it reversed the string such that the
string becomes - 321. Then it is printed out. The example should compile and does not seem to have any errors.

A. 12
B. 123
C. `321`
D. The code does not compile.

10. What is the main benefit of a lambda expression?

The main benefit of lambda is really expressing code as data, but from the given examples and possible answers
we can say that it allows you to write code that will be executed later, which is one of the side effects of being
able to represent code as data.

A. It allows you to convert a primitive to a wrapper class.
B. It allows you to change the bytecode while the application is running.
C. It allows you to inherit from multiple classes.
D. `It allows you to write code that has the execution deferred.`

11. What is the output of the following?

```java
StringBuilder line = new StringBuilder("-");
StringBuilder anotherLine = line.append("-");
System.out.print(line == anotherLine);
System.out.print(" ");
System.out.print(line.length());
```

The string builder that is initially created with "-" is then appended another "-" and in total the length of this
string is 2, the == operator will return true, because it is comparing the same references, append returns 'this'
and the length is 2.

A. false 1
B. false 2
C. true 1
D. `true 2`

12. The author of this method forgot to include the data type. Which of the following reference types can fill in the blank to complete this method?

```java
public static void secret(____________ mystery) {
    mystery.add("metal");
    String str = mystery.get(0);
    int num = mystery.length();
}
```

This method can not be made to compile, because it is using a mix of methods from a different interfaces - the
methods get and length, are a mix of List and StringBuilder interface

A. ArrayList
B. ArrayList<String>
C. StringBuilder
D. `None of the above`

13. Which portion of code can be removed so that this line of code continues to compile?

```java
Predicate<StringBuilder> p = (StringBuilder b) -> {
    return true;
};
```

The only valid thing when a return statement is present that we can really do is just remove the surrounding braces
and the return statement. The rest of the options are purely wrong, they will leave the lambda in an invalid state.

A. Remove StringBuilder b
B. Remove ->
C. Remove { and ;}
D. `Remove { return and ;}`

14. What is the output of the following?

```java
List<Character> chars = new ArrayList<>();
chars.add('a');
chars.add('b');
chars.set(1, 'c');
chars.remove(0);
System.out.print(chars.size() + " " + chars.contains('b'));
```

There are two caveats here the set and remove methods, the set will replace the element at index 1, which was b
with c, therefore the contains will not return true, the remove will reduce the size with one removing the first
element 'a'

A. `1 false`
B. 1 true
C. 2 false
D. 2 true

15. What is the output of the following?

```java
String b = "12";
b += "3";
b.reverse();
System.out.println(b.toString());
```

The reverse method does not exist on a string, it really should but it does not, at least not in java 11-17.

A. 12
B. 123
C. 321
D. `The code does not compile.`

16. How many of these lines fail to compile?

```java
Predicate<String> pred1 = s -> false; // ok
Predicate<String> pred2 = (s) -> false; // ok
Predicate<String> pred3 = String s -> false; // no-ok
Predicate<String> pred4 = (String s) -> false; // ok
```

The only one that is wrong is the 3-rd one, which is missing the brackets around the type and variable pair, these
are mandatory when a type name is present

A. `One`
B. Two
C. Three
D. Four

17. What does the following do?

```java
public class Shoot {
    interface Target {
        boolean needToAim(double angle);
    }
    static void prepare(double angle, Target t) {
        boolean ready = t.needToAim(angle); // k1
        System.out.println(ready);
    }
    public static void main(String[] args) {
        prepare(45, d -> d > 5 || d < -5); // k2
    }
}
Shoot.main(new String[0])
```

The class here is declared well, there are no compiler errors, so is the lambda, and the usage of the lambda as
well.

A. `It prints true.`
B. It prints false.
C. It doesn’t compile due to line k1.
D. It doesn’t compile due to line k2.

18. What is the output of the following?

```java
String teams = new String("694");
teams.concat(" 1155");
teams.concat(" 2265");
teams.concat(" 2869");
System.out.println(teams);
```

Concat method exists on a string and basically produces a new string every time the string is concatenated, but the
value of the concat methods is ignored never assigned to a new variable, the original teams string is never actually
mutated

A. `694`
B. 694 1155 2265 2869
C. The code compiles but outputs something else.
D. The code does not compile.

19. Which of these classes are in the java.util package?

```
I. ArrayList
II. LocalDate
III. String
```

In java, at least in the core language, the package are hardly ever nested, we have at most (very usual to have more
than one levels) level of package namespacing such as java.time, java.lang, java.util. In this case the only one
that is in java.util is the ArrayList.

A. `I only`
B. II only
C. I and II
D. I, II, and III

20. Which of the answer choices results in a different value being output than the other three choices?

```java
StringBuilder sb = new StringBuilder("radical ");
sb = ________________________;
System.out.print(sb);
```

A, D, B - all print the same, the one that is printing different value is the C option, if you notice it is
inserting not at the end position to append after the space, but exactly where the space is at index 7, because the
string is 8 long, if it were to append to the end after the space the insert should have been called with 8 not 7,
as first argument. The rest of the option append right after the space or build the string "radical robots" piece by
piece as in the B example.

A. new StringBuilder("radical ").append("robots")
B. new StringBuilder("radical ").delete(1, 100).append("obots").insert(1, "adical r")
C. `new StringBuilder("radical ").insert(7, "robots")`
D. new StringBuilder("radical ").insert(sb.length(), "robots")

21. What is the output of the following?

```java
String[] array = {"Natural History", "Science"};
List<String> museums = Arrays.asList(array);
museums.set(0, "Art");
System.out.println(museums.contains("Art"));
```

This implementation is correct, it creates the array of strings correctly, then the list as well, from the existing
utility methods in the Arrays, and finally replaces the 0-th element, the first one in other words with "Art". And
the contains check will pass, because it checks based on the object's equals method. Just a small side note it does
not use equalsIgnoreCase, which is a special case for String class, and is not part of the general Object equals
contract

A. `true`
B. false
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

22. Which is a true statement?

The only valid deduction we can make here is that if starts with returns true, then the string will surely contain
the same value, the rest are not really going to be true.

A. If s.contains("abc") is true, then s.equals("abc") is also true.
B. If s.contains("abc") is true, then s.startsWith("abc") is also true.
C. If s.startsWith("abc") is true, then s.equals("abc") is also true.
D. `If s.startsWith("abc") is true, then s.contains("abc") is also true.`

23. What is the output of the following?

```java
List<Character> chars = new ArrayList<>();
chars.add('a');
chars.add('b');
chars.set(1, 'c');
chars.remove(0);
System.out.print(chars.length());
```

The list here contains exactly one element, two are added, one replaced, one removed, leaving the list with only one
element.

A. 0
B. `1`
C. 2
D. None of the above

24. The author of this method forgot to include the data type. Which of the following reference types can fill in
    the blank to complete this method?

```java
public static void secret(_____________ mystery) {
    mystery = mystery.replace("1", "8");
    mystery.startsWith("paper");
    String s = mystery.toString();
}
```

Replace exists in different forms on both String and StringBuilder, in String what is replaced is the string, with
another string, in StringBuilder the string replacement is confined by start-end indices, not by a matching string.
The startsWith, is a method that exists on String as well, and so does the toString method coming from the Object
class, therefore we can actually replace the type with String

A. ArrayList
B. `String`
C. StringBuilder
D. None of the above

26. Which of the following can fill in the blank to make the code compile?

```java
import java.util.function.*;
public class Card {
    public static void main(String[] s) {
        Predicate<String> pred = ____________ -> true;
    }
}
```

The lambda argument body has to be well defined, and the definition of this variable declares the lambda to be of
type Predicate that takes in a String as the first argument, so we can pretty much only use on of the options below.
Object is not valid because remember that generics ARE NOT COVARIANT

A. (Integer i)
B. (Object o)
C. `(String s)`
D. None of the above

27. What is the output of the following?

```java
String line = new String("-");
String anotherLine = line.concat("-");
System.out.print(line == anotherLine);
System.out.print(" ");
System.out.print(line.length());
```

What is going on there is the anotherLine will end up being "--" and line will be "-", both refer to different
references in memory because concat method call will produce a new instance of the string, therefore the == compare
will produce false, and the total length of line is 1, it was never mutated.

A. `false 1`
B. false 2
C. true 1
D. true 2

28. What does the following output?

```java
Predicate dash = c -> c.startsWith(" ");
System.out.println(dash.test("–"));
```

The type of the predicate is omitted, therefore the compiler does not know how to call the startsWith method on the
given variable c which is of type Object by default when the type of the generic is omitted.

A. true
B. false
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

29. Of the classes LocalDate, LocalDateTime, LocalTime, and LocalTimeStamp, how many include hours, minutes, and seconds?

LocalTime and LocalDateTime both can deal with hours, minutes and seconds. LocalTimeStamp does not exist in the
standard library.

A. One
B. `Two`
C. Three
D. Four

30. What is the output of the following class?

```java
public class Countdown {
    public static void main(String[] args) {
        String builder = "54321";
        builder = builder.substring(4);
        System.out.println(builder.charAt(2));
    }
}
Countdown.main(new String[0]);
```

This example will explode, the sub string result of this will be of length 1, because the index we are sub-stringing
from is the last element, 5-th element in this case is 1. And the char at method will try to index into an invalid
size of the string, the string will be long only 1 character, and we index the 3-rd element.

A. 2
B. 3
C. 4
D. `None of the above`

31. Which equivalent code can replace i -> i != 0 in the following line?

```java
Predicate<Integer> ip = i > i != 0;
```

There is only one option that contains all the necessary symbols to declare the lambda body correctly, and that is
the last one, where the proper return + brackets and semi colon body is written

A. i -> { i != 0 }
B. i -> { i != 0; }
C. i -> { return i != 0 }
D. `i -> { return i != 0; }`

32. What is the output of the following?

```java
LocalDate xmas = LocalDate.of(2016, 12, 25);
xmas.plusDays(-1);
System.out.println(xmas.getDayOfMonth());
```

Local date as we know expresses unit times of year, month and days, therefore there a way to subtract a day from
that temporal Object in this case we will land on the previous day of the month, in this case the 24th, however
these are non-mutating fields that means that they produce a new object instance of the local date that will be
actually returned based on the source object, therefore print statement still remains, 25th

A. 24
B. `25`
C. 26
D. None of the above

33. What is the output of the following?

```java
public class Legos {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("red");
        sb.deleteCharAt(0);
        sb.delete(1, 2);
        System.out.println(sb);
    }
}
Legos.main(new String[0])
```

Delete method one of the very few that will clamp the end index to the length of the string builder and will not
throw. After the deletion of the first character the string builder is left with a length of the string that is 2,
and the delete removes anything from index 1 (element at position 2), inclusive to the end of the string builder.

A. `e`
B. d
C. ed
D. None of the above

34. What does the following output?

```java
Predicate clear = c -> c.equals("clear");
System.out.println(clear.test("pink"));
```

This is a tricky one by default the default generic type parameter will be Object however the equals method exist on
the Object class after all that is where this method is initially declared, the snippet will work, the compiler
might print warnings but we call one of the few methods like equals on an Object which is fine.

A. true
B. `false`
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

35. Which starts counting from one rather than zero?

the only thing here that makes some sort of logical sense is the months of the LocalDateTime, the rest - index
based counting is mostly always form 0, and LocalTime has no month component, it only has hours/minutes/seconds and
such.

A. Array indexes
B. The index used by charAt in a String
C. `The months in a LocalDateTime`
D. The months in a LocalTime

36. Which statement is NOT true of Predicate?

The method that the predicate interface declares is called test and it accepts only one argument, BiPredicate's
method accepts two arguments

A. A boolean is returned from the method it declares.
B. It is an interface.
C. `The method it declares accepts two parameters.`
D. The method it declares is named test.

37. Which of these periods represents a larger amount of time?

```java
Period period1 = Period.ofWeeks(1).ofDays(3);
Period period2 = Period.ofDays(10);
```

Okay here is the deal these are static methods, but they are written in such a way to confuse the user, what does it
mean, ofWeeks will truly return 1 week period instance, then ofDays will create a completely new instance of period
spanning 3 days, not in any way related to the previous period. Both methods are static both produce an independent
of the source instance object. A period of 10 days will be greater than a period of 3 days.

A. period1
B. `period2`
C. They represent the same length of time.
D. None of the above. This code does not compile.

38. What is the result of the following?

```java
import java.time.*;
import java.time.format.*;
public class HowLong {
    public static void main(String[] args) {
        LocalDate newYears = LocalDate.of(2017, 1, 1);
        Period period = Period.ofDays(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        System.out.print(format.format(newYears.minus(period)));
    }
}
```

The local date being a representative of year, month and day, correctly adjusts when a situation like that arises,
in this case the date will loop back to 2016, the last day of that year. The interface that is used to create the
local date and the period are all correct, and valid. The interface to print out the formatted local date as well.

A. 01-01-2017
B. `12-31-2016`
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

39. Which of the following can fill in the blank so the following code prints true?

```java
    String happy = " :) - (: ";
    String really = happy.trim();
    String question = __________;
    System.out.println(really.equals(question));
```

Trim will remove the empty spaces from the string, in this case the string that is left over is ":) - (:", and we
need to trim the same characters from the original string with the substring to achieve the same result, that means
we drop the first space and the last one, and the only one that is doing this in the options below is C, that gets
the string starting from index 1 up-until but NOT including the last element/character

A. happy.substring(0, happy.length() - 1)
B. happy.substring(0, happy.length())
C. `happy.substring(1, happy.length() - 1)`
D. happy.substring(1, happy.length())

40. Which is not a true statement about the Period class?

Period is is year,month or day based, just as `LocalDate` it is meant to serve as a representative of relative time, a
counter part to the local date, the duration however is the one that represents a relative duration, it is in the
name it is the counter part of `LocalTime`.

A. A Period is immutable.
B. A Period is typically used for adding or subtracting time from dates.
C. `You can create a Period representing 2 minutes.`
D. You can create a Period representing 5 years
