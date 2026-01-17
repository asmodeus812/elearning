1. Which type of loop is best known for its boolean condition that controls entry to the loop?

The while loop is the only one that can have a boolean condition control on its entry condition. While the for loop
also technically has a boolean control condition on the entry, there are other statements in the header of the for
loop as well.

A. do-while loop
B. for (traditional)
C. for-each
D. `while`

2. Which type of loop is best known for using an index or counter?

The for traditional for-loop, while the rest can still technically make use of a counter or indexed iterator, the
for traditional loop is the one that uses it predominantly

A. do-while loop
B. `for (traditional)`
C. for-each
D. while

3. Which type of loop is guaranteed to have the body execute at least once?

The do-while loop always executes at least once even if the condition upon which it is constrained is immediately
false

A. `do-while loop`
B. for (traditional)
C. for-each
D. while 4. Which of the following can loop through an array without referring to the elements by index?

The for for-each can loop through an array, because internally the compiler does most of the heavy lifting, it is
however syntax sugar for actually iterating through an array by index, it is just hidden from the final user

A. do-while loop
B. for (traditional)
C. `for-each`
D. while

5. What keyword is used to end the current loop iteration and proceed execution with the next iteration of that loop?

The break keyword can be used to break from the current most inner loop iteration, what is interesting to know is
that break can be used with a label instead that means that we can actually break from any other outer non-immediate
loop block

A. `break`
B. continue
C. end
D. skip

6. What keyword is used to proceed with execution immediately after a loop?

A. break
B. continue
C. end
D. skip

7. Which type of loop has three segments within parentheses?

It is only the standard for-loop that defines multiple segments in its header when being declared, but those
constructs can be omitted as well

A. do-while loop
B. `for (traditional)`
C. for-each
D. while

8. Which of the following statements is/are true?

I. A traditional for loop can iterate through an array starting from index 0.
II. A traditional for loop can iterate through an array starting from the end.

Both are technically true, the for loop can iterate from any position in the array as long as that iteration does
not cause any compile-time or run-time errors during the execution

A. Only I
B. Only II
C. `Both statements`
D. Neither statement

9. Which of the following statements is/are true?

I. A for-each loop can iterate through an array starting from index 0.
II. A for-each loop can iterate through an array starting from the end.

The standard for-each loop is very limited as it can not be controlled as to in which direction or where to start
from in the current iterable collection. That is left to the traditional for-loop

A. Only I
B. Only II
C. Both statements
D. `Neither statement`

10. Which type of loop has a boolean condition that is first checked after a single iteration through the loop?

The do-while is the loop type has a condition that is checked after the initial single iteration of the loop. The
rest all have their conditions in their header declaration meaning that the conditions are first checked then the
execution of the loop starts, only if the condition suffices

A. `do-while loop`
B. for (traditional)
C. for-each
D. while

11. What does the following code output?

```java
int singer = 0;
while (singer) {
    System.out.println(singer++);
}
```

The variable is used as a boolean condition check which is not valid, because it is of type int, that is strictly
not allowed in this language, unlike other languages which can treat integral types as boolean expression.

A. 0
B. `The code does not compile.`
C. The loops complete with no output.
D. This is an infinite loop.

12. What does the following code output?

```java
List<String> drinks = Arrays.asList("can", "cup");
for (int container = drinks.size() - 1; container >= 0; container--) {
    System.out.print(drinks.get(container) + ",");
}
```

This code will compile and print the elements of the collection in reverse order, the condition of the loop matches
the end-start range of the container and will not produce any compile or runtime errors. Also note the usually often
occurring issue with appended elements, in this case the comma, the very last iteration will print the last element
appending a comma at the end, this is a problem that is often seen in for-loop iterations like this and sometimes
requires extra checks to avoid

A. can,cup,
B. `cup,can,`
C. The code does not compile.
D. None of the above

13. What does the following code output?

```java
public static void main(String[] args) {
    List<String> bottles = Arrays.asList("glass", "plastic");
    for (int type = 0; type < bottles.size();) {
        System.out.print(bottles.get(type) + ",");
        break;
    }
    System.out.print("end");
}
```

This loop only does one iteration thanks to the break however if the break statement did not exist, then this is
going to cause the program to infinitely print the first element of the array, that is because the for-loop would
constantly jump back to execution since there is no mutation in the variable that is being checked on and it will
never become greater than or equal to the size of the array

A. `glass,end`
B. glass,plastic,end
C. The code does not compile.
D. None of the above

14. What does the following code output?

```java
String letters = "";
while (letters.length() != 2) {
    letters += "a";
}
System.out.println(letters);
```

This loop is pretty valid, and it will keep appending a to the variable until the length is different than 2, in our
case the length starts off as 0, and then becomes 1, and finally becomes 2, so far we have entered the loop twice,
the 3-rd time the loop attempts to jump in will not keep the condition because the length is no longer != 2, it is
equal to 2, and we exit the loop jumping out of it immediately to the next execution instruction right after the
loop which is printing the variable

A. `aa`
B. aaa
C. The loops complete with no output.
D. This is an infinite loop.

15. What is the result of the following when run with java peregrine.TimeLoop September 3 1940?

```java
public class TimeLoop {
    public static void main(String[] args) {
        for (int i = args.length; i>=0; i++) {
            System.out.println("args");
        }
    }
}
TimeLoop.main(new String[0])
```

This code will print infinitely because of the condition of the loop combined with the starting value and the
increment mutation of the variable. In this case it starts off with the length of the arguments passed which is 3,
and it will keep increasing, (i++) on top of that our condition here checks if the value is 0 or positive and it is.
Actually that will keep iterating until the value overflows, and then stop, once it overflows the value will become
negative an no longer greater or equal to zero (but those are details). You can test this by replacing the type with byte, which will overflow much faster (after reacing 127, because just like int it is signed)

```java
// demonstrates that the value will overflow once it reaches 127 + 1, then the value will loop over become negative
// and effectively only just then will the check i>=0 suffice, and the loop will stop, so technically not an infinite
// loop
for (byte i = (byte) args.length; i>=0; i++) {
    System.out.println("args");
}
```

A. args
B. argsargs
C. The code does not compile.
D. `None of the above`

16. What is the output of the following code?

```java
public class Loop {
    private static int count;
    private static String[] stops = new String[] { "Washington", "Monroe", "Jackson", "LaSalle" };
    public static void main(String[] args) {
        while (count < stops.length) {
            if (stops[count++].length() < 8) {
                break;
            }
        }
        System.out.println(count);
    }
}
Loop.main(new String[0])
```

This one has a few gotchas, first the count is never initialized only defined, however we must know that the member
variables of type primitive are always initialized to 0, and reference types always initialized to null, that is in
contrast to local variables of these types which are not.

Second we must notice that we have a post increment operation in the check itself, meaning that a check is performed
with the current value then it is incremented regardless of the result of the check that is because in an if
statement the left and right hand side are both evaluated.

Thirdly the array contains only 1 element which length is greater than or equal to 8, that is the first one,
"Washington" the rest are less than 8, therefore on the first iteration we will not enter the break, the if
condition is not true, the first element is longer, the count increments to 1, on the second loop the condition
suffices the element at index 1 is indeed shorter, but the count will still be incremented to 2, right before we
enter the body of the if condition - therefore the value of the count variable will be 2, after we exit the while
loop

A. 1
B. `2`
C. 4
D. The code does not compile.

17. What is the result of the following code?

```java
do {
    int count = 0;
    do {
        count++;
    } while (count < 2);
    break;
} while (true);
System.out.println(count);
```

The code here seems correct but there is a small issue, the count variable being printed out in the program is only
declared in the outer loop, that is being accessed outside of the loop in the print statement, that is invalid, had
the variable been declared / defined outside of the first loop body then the program would print 2

A. 2
B. 3
C. `The code does not compile.`
D. This is an infinite loop.

18. Which of the following segments of a for loop can be left blank?

```java
for (segmentA; segmentB; segmentC) {
}
```

All of them can be left blank, however there is a restriction on which ones can be left blank when others are
present, for example we cant have segmentC operating on a variable that is never defined (doing i++ or i--) unless
segmentA was present declaring that variable, however segmentC can refer to a variable that is in-scope unrelated to
the for-loop at all in the first place.

A. segmentA
B. segmentB
C. segmentC
D. `All of the above`

18. How many of the loop types (while, do while, traditional for, and enhanced for) allow you to write code that creates an infinite loop?

Technically even the enhanced for each loop can do that but it depends heavily on the iterator of the collection
being used, so in a way you can but not though the loop construct itself, but rather if the iterator implementation
of the collection allows that.

For this question only the standard primitive loop constructs allow that, and we have direct control over actually
implementing it like that, unlike the for-each loop over which we do not have direct loop control

A. One
B. Two
C. `Three`
D. Four

19. What is the output of the following?

```java
List<String> drinks = Arrays.asList("can", "cup");
for (int container = 0; container < drinks.size(); container++) {
    System.out.print(drinks.get(container) + ",");
}
```

This construct prints all of the values of the array in forward order nothing particularly wrong here similar
example we already looked at above, where the construct iterated over a similar array in reverse

A. `can,cup,`
B. cup,can,
C. The code does not compile.
D. None of the above

20. What happens when running the following code?

```java
do (
    System.out.println("helium");
) while (false);
```

Okay so just like the other loop constructs the do-while one also allows one to have just one statement in the body
of the loop, however here we have additional symbols - the braces, which are not allowed, however if we had the
following example it would have worked and compiled, and printed out the word "helium"

```java
do
   System.out.println("helium");
while (false);
```

A. It completes successfully without output.
B. It outputs helium once.
C. It keeps outputting helium.
D. `The code does not compile.`

21. Which of the following is equivalent to this code snippet given an array of String objects?

```java
String[] fun = { "test", "word", "array" };
for (int i=0; i<fun.length; i++) {
    System.out.println(fun[i]);
}
```

The standard for-each loop is going to be converted to a traditional for loop by the compiler when an traditional
array type is used, while if the construct was of type that extended / implemented the iterable interface then it
would have been converted to a while loop with a hasNext/next structure, either way a syntactic sugar for what one
would otherwise write manually.

A. for (String f = fun) System.out.println(f);
B. `for (String f : fun) System.out.println(f);`
C. for (String = fun) System.out.println(it);
D. None of the above

23. How many of these statements can be inserted after the println to have the code flow follow the arrow.

```java
|--> letters: for (int ch = 'a'; ch < 'z'; ch++) {
|       numbers: for (int n = 0; n < 10; n++) {
|           System.out.println(ch);
|<-------<------<
        }
    }
```

```
break;
break letters;
break numbers;
```

We can really insert only 2, and these two are the ones that break the inner loop, because we target jump to the
outer loop execution, either a break or break numbers which are technically equivalent for this situation.

A. None
B. One
C. `Two`
D. Three

24. Using the diagram in the previous question, how many of these statements can be inserted after the println to
    have the code flow follow the arrow in the diagram?;

```
continue
continue letters;
continue numbers;
```

Here we have the inverse problem, we have to jump immediately to the letters loop skipping the execution of the
numbers loop body.

A. None
B. `One`
C. Two
D. Three

25. What does the following code output?

```java
int singer = 0;
while (singer > 0) {
    System.out.println(singer++);
}
```

The code here will actually not even enter the body of the while loop, because the singer variable is never greater
than 0 at the moment it is first checked by the condition of the while loop, had it been actually >= 0, then this
would have entered and iterated and printed out the value of the variable until the singer singled integral/int
variable overflows back to negative value (after reaching some 2^31 value).

A. 0
B. The code does not compile.
C. `The loops completes with no output.`
D. The loops infinitely printing the variable value

26. Which of the following types is the variable 'taxis' NOT allowed to be in order for this code TO compile?

```java
for (Object obj : taxis) {
}
```

The ArrayList is quite straight forward, the compile will use the iterator implementation to resolve the type and
the value of the elements is Integer which can be substituted for a higher type like Object

The int[] is more nuanced, the compile will replace this iteration with a for-i-loop but on top of that it will also
silently box the int primitive type into Integer, and once that is done, that Integer wrapper can be interpreted as
Object as well.

The one that will prevent the program from compiling is if taxis was a `StringBuilder`, as it does not implement
Iterable interface, therefore the compiler can not convert that into anything that can be iterated over using known
structures.

A. ArrayList<Integer>
B. int[]
C. `StringBuilder`
D. All of these are allowed.

27. What is the output of the following?

```java
boolean balloonInflated = false;
do {
    if (!balloonInflated) {
        balloonInflated = true;
        System.out.print("inflate-");
    }
} while (!balloonInflated);
System.out.println("done");
```

This example demonstrates a single iteration body entry into the do-while loop which is immediately followed by the
condition check that will exit the body of the loop, in this case the balloonInflated started off as false, the if
condition is executed, enters the body of the control structure, changes the value of the variable, prints and the
do-while check will exit the loop without jumping back into the body a second time.

A. done
B. `inflate-done`
C. The code does not compile.
D. This is an infinite loop.

28. What does the following code output?

```java
String letters = "";
while (letters.length() != 3) {
    letters+="ab";
}
System.out.println(letters);
```

The loop here starts of with the length of the string being != 3, it is 0. That means that on the very first
iteration execution of the loop the variable will become "ab", after that the second iteration will cause the
variable length to exceed 3, by now becoming "abab". A third loop iteration will execute, because the condition does
suffice still, the length is 4 and that is != 3. Therefore since we check on a non-even condition value - 3 but
increment the string with an even number of characters the loop will execute forever, appending the "ab" to the
result.

A. ab
B. abab
C. The loop completes with no output.
D. `This is an infinite loop.`

29. What describes the order in which the three expressions appear in a for loop?

It is important to note that while we ca skip segments of the for loop, we can not interchange them, meaning that
the 3 segment positions are very well defined by the compiler and the java language specification. That means that
the first segment is always for variable initialization, the second one is always the condition/s and the third one
is always the mutation or update statement. Also keep in mind that we can put / use as many variable initialization
declarations, separated by comma, and as many variable update statements also again separated by comma, however the
for-loop condition must be either at most one or none at all/no-condition at all, separating conditions with comma
in a for-loop makes no sense.

A. boolean conditional, initialization expression, update statement
B. `initialization expression, boolean conditional, update statement`
C. initialization expression, update statement, boolean conditional

30. What is the result of the following?

```java
int count = 10;
List<Character> chars = new ArrayList<>();
do {
    chars.add('a');
    for (Character x : chars) {
        count -= 1;
    }
} while (count > 0);
System.out.println(chars.size());
```

In here we will go through 4 iterations of the do-while before the variable becomes 0, and the condition stops the
loop, the count in this case is the condition upon which we stop the loop but we print the size of the list/array of
characters. In the end we will have exactly 4 elements inside that list, when the count reaches 0.

1. (chars) a - (count) 9
2. (chars) aa - (count) 7
3. (chars) aaa - (count) 4
4. (chars) aaaa - (count) 0

A. 3
B. `4`
C. The code does not compile.
D. None of the above
