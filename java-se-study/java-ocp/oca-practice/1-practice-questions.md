1. Which of the following variable types is not permitted in a switch statement?

Switch statements permit mostly constant or integral types, and primitives String is the exception that is also
permitted even though it is not a primitive, that was introduces later on in the language

A. String
B. `double`
C. int
D. char

2. What is the value of tip after executing the following code snippet?

```java
int meal = 5;
int tip = 2;
int total = meal + (meal>6 ? ++tip : tip);
```

In this case the meal value is not greater than 6, therefore the pre-increment operator will not be executed and the
tip will never be increment-assigned, the value remains the same as it was

A. 1
B. `2`
C. 3
D. 6

3. What is the output of the following application?

```java
public class NameCheck {
    public static void main(String... data) {
        String john = "john";
        String jon = new String(john);
        System.out.print((john==jon)+" "+(john.equals(jon)));
    }
}
```

The references here are strictly not the same, the two object references even though they hold the same string
value are not the same references because the string is constructed from the constructor. But the equals will return
true because the contents are the same.

A. true true
B. true false
C. `false true`
D. false false

4. What is the output of the following application?

```java
public class ThePlan {
    public static void main(String[] input) {
        int plan = 1;
        plan = plan++ + --plan;
        if(plan==1) {
            System.out.print("Plan A");
        } else {
            if(plan==2) System.out.print("Plan B");
        } else {
            System.out.print("Plan C");
        }
    }
}
```

This is a mess but the code will not compile we have two else statements following each other that is not possible
the first else block has to be re-written to be else if then it will.

A. Plan A
B. Plan B
C. Plan C
D. `None of the above`

5. Which of the following statements about a default branch in a switch statement is correct?

The only true statement here is that the default statement does not take any value, the rest of the imposed
restrictions by this question are not true, the default statement can be put after the case, or before the case,
they are not required to have a default statement, and you might not even have one, no matter how many cases there
are

A. All switch statements must include a default statement.
B. The default statement is required to be placed after all case statements.
C. `Unlike a case statement, the default statement does not take a value.`
D. A default statement can only be used when at least one case statement is present.

6. What is the value of thatNumber after the execution of the following code snippet?

```java
long thatNumber = 5 >= 5 ? 1 + 2 : 1 * 1;
if (++thatNumber < 4) {
    thatNumber += 1;
}
System.out.println(thatNumber);
```

The caveat here is the use of the prefix-increment operator in the if statement which will increment the value of
thatNumber, to 4 before the comparison is executed, therefore we will compare 4 < 4 which is not true the body of
the if statement will not trigger, and thatNumber will remain with a value of 4

A. 3
B. `4`
C. 5
D. The answer cannot be determined until runtime.

7. Which statement immediately exits a switch statement, skipping all remaining case or default branches?

The default keyword that is required to be used is break, however one can certainly achieve the same with goto as
well

A. exit
B. `break`
C. goto
D. continue

8. Which statement about ternary expressions is true?

Ternary operator is just a syntax sugar for if-else therefore the only true statement is C. The statement A is false
, as the ternary will always be executed at runtime, B. They do not require paradises to be present unless we want
to express some very complex expression that needs to be in paradises to ensure proper order of execution. D. The
ternary expression does not support anything but boolean evaluating expressions

A. In some cases, both expressions to the right of the conditional operator in a ternary expression will be evaluated at runtime.
B. Ternary expressions require parentheses for proper evaluation.
C. `The ternary expressions are a convenient replacement for an if-then-else statement.`
D. Ternary expressions support int and boolean expressions for the left-most operand.

9. What is the output of the following application?

```java
public class Calculator {
    public void calculateResult(Integer candidateA, Integer candidateB) {
        boolean process = candidateA == null || candidateA.intValue() < 10;
        boolean value = candidateA && candidateB;
        System.out.print(process || value);
    }
    public static void main(String[] unused) {
        new Election().calculateResult(null,203);
    }
}
```

The check that evaluates value is not right, we are using the value of candidateB as a boolean even though not only
is it a reference type but is is an Integer, it would have worked had the type of candidateB were a boolean or a
Boolean as the compiler will unbox that Boolean reference to a boolean primitive using booleanValue

A. true
B. false
C. `The code does not compile.`
D. The code compiles but throws a NullPointerException on line 3 at runtime.

10. What is the output of the following application?

```java
public class Park {
    public final static void main(String... arguments) {
        int pterodactyl = 6;
        long triceratops = 3;
        if(pterodactyl % 3 >= 1) {
            triceratops++;
        }
        triceratops--;
        System.out.print(triceratops);
    }
}
Park.main(new String[0])
```

The only caveat here is the if statement that expresses mod operation on the value of 3, 3 mod 3 is 0, therefore we
never enter the first if block, never increment the value of triceratops, therefore w only decrement it to 2

A. `2`
B. 3
C. 4
D. The code does not compile.

11. Which statement about if-then statements is true?

Just like in most c like languages the if statement clause can either execute one statement immediately following it
when no block is provided or the entire block of statements that the block following the if statement

A. An if-then statement is required to have an else statement.
B. If the boolean test of an if-then statement evaluates to false, then the target clause of the if-then statement will still be evaluated.
C. An if-then statement is required to cast an object.
D. `An if-then statement can execute a single statement or a block {}.`

12. What is the output of the following application?

```java
public class Pieces {
    public static void main(String[] info) {
        int flair = 15;
        if(flair >= 15 && flair < 37) {
            System.out.print("Not enough");
        }
        if(flair==37) {
            System.out.print("Just right");
        } else {
            System.out.print("Too many");
        }
    }
}
Piecese.main(new String[0])
```

The block above actually prints two things, because we have two independent if statements in there, the very fist
one is going to indeed print "Not enough" but the second if/else pair will also print, since the second if statement
does not suffice the else will be printed, that implies that we will have another print "Too many".

A. Not enough
B. Just right
C. Too many
D. `None of the above`

13. Which statement about case statements of a switch statement is NOT true?

Nothing really mandates that a case statement must be terminated with a break statement. Even though that is advised
because otherwise the statement will fall through the rest of the case statements

A. A case value can be final.
B. `A case statement must be terminated with a break statement.`
C. A case value can be a literal expression.
D. A case value must match the data type of the switch variable, or be able to be promoted to that type.

14. Given the following truth table, which operator for the boolean expressions x and y corresponds to this relationship?

x = true x = false
y = true
truefalse
y = false falsefalse

A. --
B. ++
C. ||
D. &&

15. What is the output of the following code snippet?

```java
int hops = 0;
int jumps = 0;
jumps = hops++;
if(jumps) {
    System.out.print("Jump!");
} else {
    System.out.print("Hop!");
}
```

We are using the value of an integer as a boolean expression ,even if that is allowed in some other c-like languages
in java that is not, the value of the if has to be a boolean expression, or a variable that can be converted to a
boolean by the compiler, that really only is true for the Boolean wrapper type, anything else is an error.

A. Jump!
B. Hop!
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

16. Fill in the blanks: The **\*\***\_**\*\*** operator increases the value of a variable by 1 and returns the new
    value, while the **\*\***\_**\*\*** operator decreases the value of a variable by 1 and returns the original
    value.

It is important to note the difference between the two operators. The pre-increment operator will be executed with
priority, and will return the latest value after the pre-increment, the post-decrement will first return the value
and then do the decrement and update the value of the variable

A. pre-increment [++v], pre-decrement [--v]
B. `pre-increment [++v], post-decrement [v--]`
C. post-increment [v++], pre-decrement [--v]
D. post-increment [v++], post-decrement [v--]

17. What is the output of the following application?

```java
public class TheBigRace {
    public static void main(String[] in) {
        int tiger = 2;
        short lion = 3;
        long winner = lion+2*(tiger + lion);
        System.out.print(winner);
    }
}
```

The order of operations in java is mostly following the standard arithmetic and mathematical rules

1. unary operators - ++, --, >>, <<, etc
2. braced expressions with - (,)
3. binary operators \*, /, +, - etc

- tiger(2) + lion(3) = 5
- 5(tiger+lion) \* 2 = 10
- 10(5(tiger+lion) \* 2) + lion(3) = 13

A. 11
B. `13`
C. 25
D. None of the above

18. Given the following code snippet, assuming dayOfWeek is an int, what variable type of saturday is not permitted?

```java
final ****\_**** saturday = 6;
switch(dayOfWeek) {
    default:
        System.out.print("Another Weekday");
        break;
    case saturday:
        System.out.print("Weekend!");
}
```

This is invalid, the case in the switch statement has to be a compiler time constant, even if the variable is
defined as final

A. byte
B. long
C. int
D. `None of the above`

19. Given the following code snippet, what is the value of dinner after it is executed?

```java
int time = 11;
int day = 4;
String dinner = time > 10 ? day ? "Takeout" : "Salad" : "Leftovers";
```

No braces will save you here - incompatible types: int cannot be converted to boolean, a ternary operator does not
work with int used as boolean expression.

A. Takeout
B. Salad
C. The code does not compile but would compile if parentheses were added.
D. `None of the above`

20. What is the output of the following application?

```java
public class Dancing {
    public static void main(String[] vars) {
        int leaders = 10 - (2 + (1 + 2 / 5);
        int followers = leaders - 2;
        System.out.print(leaders + followers < 10 ? "Too few" : "Too many");
    }
}
```

A. Too few
B. Too many
C. The code does not compile.
D. The code compiles but throws a division by zero error at runtime.

21. What is the output of the following application?

```java
public class PrintWeek {
    public static final void main(String[] days) {
        System.out.print(5 + 6 + "7" + 8 + 9);
    }
}
PrintWeek.main(new String[0])
```

Left to right expression evaluation takes precedence here all of the operators are of the same priority, therefore
we get 5 + 6 = 11, + 7, + 8, + 9. The concatenation with "7" will convert the expression to string and the rest of
the values will be appended to the end of the result

A. 56789
B. `11789`
C. 11717
D. The code does not compile.

22. Fill in the blanks: The**\*\***\_\_**\*\*** operator is used to find the difference between two numbers, while
    the**\*\***\_\_**\*\*** operator is used to find the remainder when one number is divided by another.

The minus operator is of course used to find the difference between two numbers and the modulo operator is used to
find the remainder of integer division

A. /, %
B. `–, %`
C. %, <

23. What is the output of the following application?

```java
public class Rematerialize {
    public static void main(String[] input) {
        int dog = 11;
        int cat = 3;
        int partA = dog / cat;
        int partB = dog % cat;
        int newDog = partB + partA \* cat;
        System.out.print(newDog);
    }
}
Rematerialize.main(new String[0])
```

This example is correct it just requires you to know how to apply the rules of integer division, and modulo
division, to know that dividing 11 / 3 = 3, while 11 % 3 = 2, the rest is mostly straightforward.

A. 9
B. `11`
C. 15
D. The code does not compile.

24. What is the output of the following application?

```java
public class IceCream {
    public final static void main(String... args) {
        int flavors = 30;
        int eaten = 0;
        switch(flavors) {
            case 30: eaten++;
            case 40: eaten+=2;
            default: eaten--;
        }
        System.out.print(eaten);
    }
}
```

This example is a bit more convoluted, but that all falls back to understanding how the switch statement works in
java, and that the label version of the switch statement is based on the legacy switch statement from c-like
languages.

In the example above, the first case label 30: will enter, the statement execute and increment the variable then,
every other expression below until the end of the switch statement or the first return/break/throw will
automatically execute. Why is that ? The switch/case works like a goto-label, the first jump is at the label case
30:, and the rest of the case statements no longer work as guards, the code does a complete fall through.

Therefore in the example above, what happens is that the variable becomes incremented with 1, then 2, then finally
decremented in the default block with 1, and the final value of that variable is now 2

A. 1
B. 2
C. 3
D. The code does not compile.

25. What is the output of the following application?

```java
public class Transportation {
    public static String travel(int distance) {
        return distance < 1000 ? "train" : 10;
    }
    public static void main(String[] answer) {
        System.out.print(travel(500));
    }
}
Transportation.main(new String[0])
```

This one is mostly related to compiler, in this ternary if which is always executed at runtime, all code paths are
requires to return the same type, in this case however that is not true. Here the method is declared to return a
string however the branch of the ternary returns an integer (10) in this case

A. train
B. 10
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

26. Fill in the blanks: Given two non-null String objects with reference names

apples**\*\***\_\_**\*\*** and oranges, if that evaluates to true, then apples**\*\***\_\_**\*\*** oranges must also
evaluate to true.

This is mostly straightfoward, if the == operator comparing the reference objects returns true, then we can
guarantee that the equals method will also evaluate to true

A. `==, equals()`
B. !=, equals()
C. equals(), ==
D. equals(), =!

27. For a given non-null String myTestVariable, what is the resulting value of executing the statement myTestVariable.equals(null)?

A good implementation of equals will never force a runtime exception if an object is compared to null with equals,
that is bad practice therefore we can certainly call the method with null and we will not expect any runtime
exceptions to be thrown

A. true
B. `false`
C. The statement does not compile.
D. The statement compiles but will produce an exception when used at runtime.

28. How many 1s are outputted when the following application is compiled and run?

```java
public class Road {
    public static void main(String... in) {
        int intersections = 100;
        int streets = 200;

        if (intersections < 150) {
            System.out.print("1");
        } else if (streets && intersections > 1000) {
            System.out.print("2");
        }

        if (streets < 500) {
            System.out.print("1");
        } else {
            System.out.print("2");
        }
    }
}
```

The code is another example of an issue with using a non-boolean expression in an if statement, in the example
above, we have the streets which is of type int used as a boolean expression again

A. None
B. One
C. Two
D. `The code does not compile.`

29. Which statement about the logical operators & and && is true?

The correct answer here is B. That is because the operator bitwise and - &, will always evaluate both side of the
expression, while the normal logical && will first evaluate the left hand side if it is false, then it will short
circuit and just never evaluate the right hand side at all. This is important to note because there are many ways to
mis-interpret this question, the rest of the are not correct

- A. that is incorrect because they are not interchangeable, because the bitwise and logical operator are both serving
different purposes
- C. both expressions DO NOT evaluate to true if either one is true, that is the definition of the logical or - ||
operator
- D. the && and operator does not ONLY evaluate the right hand side, actually if anything it may evaluate only the LEFT
hand side first and skip the right hand side one

A. The & and && operators are interchangeable, always producing the same results at runtime.
B. The & operator always evaluates both operands, while the && operator may only evaluate the left operand.
C. Both expressions evaluate to true if either operand is true.
D. The & operator always evaluates both operands, while the && operator may only evaluate the right operand.

30. What is the output of the following code snippet?

```java
int x = 10, y = 5;
boolean w = true, z = false;
x = w ? y++ : y--;
w = !z;
System.out.print((x+y)+" "+(w ? 5 : 10));
```

This one is mostly straightforward, why ? Well here we can see that the ternary operator is used correctly, with
variables that are of boolean type, that is great. First the x is assigned whatever y was before the increment (5)
and then y is incremented to 6. That is because a post-increment operator is used here that will first return the
value of the variable, then increment it. Then the w is assigned the inverted value of z which is !false == true.

A. The code does not compile.
B. 10 10
C. `11 5`
D. 12 5
