1. Suppose that you have a collection of products for sale in a database and you need to display those products. The
   products are not unique. Which of the following collections classes in the java.util package best suit your needs for
   this scenario?

The argument here is that if we truly want to show the list of products but such that the list contains only a single
product we need to use a structure that will allow us to display all of the products but only once, to avoid
duplication during the display

A. Arrays
B. ArrayList
C. HashMap
D. `HashSet`
E. LinkedList

2. Suppose that you need to work with a collection of elements that need to be sorted in their natural order, and each
   element has a unique string associated with its value. Which of the following collections classes in the java.util
   package best suit your needs for this scenario?

Among these structures, only 2 are truly ordered by means of their semantic and actual definitions. The rest are
simply containers with random insertion and non-deterministic order. Now the question tells us that the elements
have a natural order and are associated with a unique value that implies that we can use both TreeSet for the
elements and also TreeMap since we have a unique associated key with each element.

One caveat is that the TreeMap sorts by the keys, not the values, while TreeSet sorts the actual elements that are
inserted into the set/structure.

A. ArrayList
B. HashMap
C. HashSet
D. `TreeMap`
E. `TreeSet`
F. Vector

3. What is the result of the following statements?

```java
List list = new ArrayList();
list.add("one");
list.add("two");
list.add(7);
for (String s: list) {
    System.out.print(s);
}
```

First issue that somebody might see here is that the list is defined without any generics, which is fine by default the
compiler will assume Object, which means that we can certainly add in that list of objects. The integer will be
automatically boxed into Integer object type and be perfectly happy to be added to the list. However once we start
iterating over the list we will print the first 2 elements just fine, but the final one will cause a class cast
exception. Why is that ? Well the 3rd element is a number more specifically it is an Integer and we can not cast it to
String to print it out. If we change the for loop to use Object instead that will work just fine and print all 3
elements of the list.

A. onetwo
B. onetwo7
C. `onetwo followed by an exception`
D. Compiler error on line 6
E. Compiler error on line 7

4. What is the result of the following statements?

```java
ArrayDeque<String> greetings = new ArrayDeque<String>();
greetings.push("hello");
greetings.push("hi");
greetings.push("ola");
greetings.pop();
greetings.peek();
while (greetings.peek() != null) {
    System.out.print(greetings.pop());
}
```

The few details that might seem like real issues here are the pop and the peek, the first pop call will remove from the
end of the queue which in this case is the word 'ola' then we will have 2 elements only, peek does not modify the double
ended queue, so the first call to peek is no-op effectively. The peek condition in the while loop serves as isEmpty
check basically checking if there is an element to look at, it will return null if the queue is empty, not throw
exception. Therefore we pop until there is nothing left from the back of the queue - ending up with 'hihello'

There are methods in the queue interface that will throw, these are the getFirst getLast methods that in contrast to
peekFirst or peekLast will actually throw if the queue is empty. The same is true for the removeXXX methods.

A. hello
B. hellohi
C. hellohiola
D. hi
E. `hihello`
F. The code does not compile.
G. An exception is thrown. 5.

5. Which of these statements compile? (Choose all that apply.)

A - generic types are not covariant, therefore we can not substitute them like this unless we use extends/super and
wildcards, that still does not imply covariancy
D - The HashSet does not implement the List interface so that here is not possible
E - The wild card has to be at the type declaration not definition meaning on the left hand side

B - Is correct, what it says is that the elements inside the hash set are of some unknown type that is super or
parent of the ClassCastException, in this case the right hand side says the map contains Exception which is a parent of
ClassCastException
C - The Vector class is implementing the List interface therefore that is also valid
F - This is also correct what it says is that the Map contains values that are children of Number we do not know which
type these children are going to be of, the right hand side creates a new hash map instance and says these values are of
type are Integer

Just as part of this exercise, How can we fix the examples that do not work to make them work for example ?

B - HashSet<Number> hs = new HashSet<Number>(); or HashSet<? extends Number> hs = new HashSet<Integer>()
D - List<Object> values = new ArrayList<Object>(); or List<? super Number> values = new ArrayList<Object>()
E - List<? extends Object> objects = new ArrayList<Object>();

A. HashSet<Number> hs = new HashSet<Integer>();
B. `HashSet<? super ClassCastException> set = new HashSet<Exception>();`
C. `List<String> list = new Vector<String>();`
D. List<Object> values = new HashSet<Object>();
E. List<Object> objects = new ArrayList<? extends Object>();
F. `Map<String, ? extends Number> hm = new HashMap<String, Integer>();`

6. What is the result of the following code?

```java
public class Hello<T> {
    T t;
    public Hello(T t) { this.t = t; }
    public String toString() { return t.toString(); }
    public static void main(String[] args) {
        System.out.print(new Hello<String>("hi"));
        System.out.print(new Hello("there"));
    }
}
Hello.main(new String[0])
```

This example tries to demonstrate that skipping or not providing the type parameter when creating the instance is
not an error, it might product a compiler warning which is expected, but it will still work. After type erasure both
of these actually hold <Object> as their primary reference type. This example compiles and prints 'hithere'

A. hi 7.
B. hi followed by a runtime exception
C. `hithere`
D. Compiler error on line 4
E. Compiler error on line 6
F. Compiler error on line 7

7. Which of the following statements are true? (Select two.)

```java
Set<Number> numbers = new HashSet<>();
numbers.add(new Integer(86));
numbers.add(75);
numbers.add(new Integer(86));
numbers.add(null);
numbers.add(309L);
Iterator iter = numbers.iterator();
while (iter.hasNext()) {
    System.out.print(iter.next());
}
```

In this example we have a few issues that might cause us to think in the wrong direction. First the add method is
passed in null, while that is invalid value for TreeSet, HashSet implementation allows us to add null, the set will
eventually contain only one NULL element, because that is a set so we can add as many a we want. The next one is the
fact that we add two Integer objects of the same value which will end up again putting only one of these in the set

- ofcourse.

A. `The code compiles successfully.`
B. The output is 8675null309.
C. The output is 867586null309.
D. `The output is indeterminate.`
E. There is a compiler error on line 3.
F. There is a compiler error on line 9.
G. An exception is thrown.

8. What is the result of the following code?

```java
TreeSet<String> tree = new TreeSet<String>();
tree.add("one");
tree.add("One");
tree.add("ONE");
System.out.println(tree.ceiling("On"));
```

The ceiling interface method is a valid method that exists on NavigableSet. What it does is to return the closest
element that is greater than or equal to a given element. In this case we pass in as argument "On", there is no
element in the set that is equal to that one, but the one that is closest and greater than that element is "One",
Why is that ? We have in total 3 elements in the map. one, One, and ONE. If we order them lexicographically, knowing
that the ASCII sequence is such that numbers come first, then capital letters and then lower case letters we will
get the following order - [ONE One one]. That being said, the target element is "On", the element "ONE" is certainly
lexicographically less than "On", the next one is "One" that one contains one more letter "e" and is therefore the
closest element that is greater than the target one

If we were looking for the element "Onf" instead of "On", then the element that ceiling would have returned is "one"
since "Onf" comes after "One" because "f" comes after "e"

A. On
B. one
C. `One`
D. ONE
E. The code does not compile.
F. An exception is thrown.

9. Which of the answer choices are valid given the following declaration?

```java
Map<String, Double> map = new HashMap<>();
```

The map accepts value of type double, the example which adds 2L is not valid since there is no valid auto-casting in
java that converts automatically from long to double. The example that adds 'x' as key is not valid since that is not a
valid char sequence it is a marker for single char, not a string.

A. `map.add("pi", 3.14159);`
B. map.add("e", 2L);
C. `map.add("log(1)", new Double(0.0));`
D. map.add('x', new Double(123.4));
E. None of the above

10. What is the result of the following program?

```java
import java.util.*;
public class MyComparator implements Comparator<String> {
    public int compare(String a, String b) {
        return b.toLowerCase().compareTo(a.toLowerCase());
    }
    public static void main(String[] args) {
        String[] values = { "123", "Abb", "aab" };
        Arrays.sort(values, new MyComparator());
        for (String s: values) {
            System.out.print(s + " ");
        }
    }
}
```

That task might seem overwhelming to try and sort these elements in your head. First we might think that the line that
declares the array is actually wrong, the initialization block is valid in Java, it can also be - new String[]{ "values"
}; But what we have here is also correct, then the actual gotcha in this example is the fact that the array is ordered
in reverse it compares the right hand side argument (b) to the left hand side (a) therefore by default the ordering is
assumed to be a - b, but this comparator inverts the ordering therefore it becomes b - a. The condition on the
comparator strictly says that if the left hand side is greater than the right hand side it returns positive, negative
otherwise, or 0 if both are equal. Inverting the compareTo here effectively has the effect of reversing the order of the
sorting

In ASCII the numbers come first then upper case letters then lower case. In our comparator we lower case the letters so
we care only about letter ordering the proper ascending order will then be 123, aab, abb. But remember the comparator is
inverted meaning it sorts the array in reverse therefore the final answer is actually when lower cased - abb, aab, 123,
or actually the array elements Abb aab 123

A. `Abb aab 123`
B. aab Abb 123
C. 123 Abb aab
D. 123 aab Abb
E. The code does not compile.
F. A runtime exception is thrown.

11. What is the result of the following code?

```java
public class MyMap {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>(10);
        for (int i = 1; i <= 10; i++) {
            map.put(i, i \* i);
        }
        System.out.println(map.get(4));
    }
}
MyMap.main(new String[0]);
```

In this example we are getting pretty standard java code. There are no obvious gotchas, besides maybe the size/capacity
of the map which is defined as 10, but that is just the initial capacity of the map which will grow freely. The actual
map keys are numbers from 1 to 10, and the values are the same numbers but squared. The Map contains Integer value and
keys and we are inserting primitives but as we know the java compiler will actually auto box those into their
corresponding wrapper types. So the answer is 16, for the key 4, that is the value for that key will be 4 \* 4 = 16.

A. `16`
B. 25
C. Compiler error on line 3.
D. Compiler error on line 5.
E. Compiler error on line 7.
F. A runtime exception is thrown.

12. Which of these statements can fill in the blank so that the Helper class compiles successfully? (Choose all that apply.)

```java
public class Helper {
    public static <U extends Exception> void printException(U u) {
        System.out.println(u.getMessage());
    }
    public static void main(String[] args) {
        __________________________________
    }
}
```

In this example the more gritty stuff is actually correctly defining the type parameter for the generic method. Meaning
that in Java unlike other languages the type parameter sits exactly between the method name and the Type. So here we
have Helper.<Exception>printException(). This is important it is also easy to remember when you declare the method it
sits in the exact same place right before the name of the method.

A. `Helper.printException(new FileNotFoundException("A"));`
B. `Helper.printException(new Exception("B"));`
C. Helper.<Throwable>printException(new Exception("C"));
D. `Helper.<NullPointerException>printException(new NullPointerException("D"));`
E. Helper.printException(new Throwable("E"));

13. Which of these statements can fill in the blank so that the Wildcard class compiles successfully? (Choose all that apply.)

```java
import java.util.\*;
public class Wildcard {
    public void showSize(List<?> list) {
        System.out.println(list.size());
    }
    public static void main(String[] args) {
        Wildcard card = new Wildcard();
        ____________________________________
        card.showSize(list);
    }
}
```

The only valid cases here are A, B and E. The rest have issues but lets try to fix them to make them compile, here are
some of the changes that need to be done for the rest of the options

C. - The right hand side of the definition must define the concrete type, remember that generics are not magic, they
are a helping hand for the compiler and the user. we can fix this by doing - List<?> list = new ArrayList<Object>();

D. - Again we have mentioned multiple times, generics are not co-variant, that means that the left hand side type can
not (without wildcard) express a parent - child relationship type - List<? extends Exception> list = new
LinkedList<IOException>();.

The ones that are correct we can also express why that is the case by mentioning a few words for each of them.

A. - This is unbounded wildcard, it implies Object and that is all, but is a valid declaration and definition for the
array deque. However this unbound wild card is mostly useless unless we call methods that do not operate with the return
type of the generic, such as size, isEmpty etc. The moment we try to use .add(E element) or .get(index) the compiler
will cry, this is in a way a more loose compiler definition of this - ArrayDeque<Object> list = new ArrayDeque<>(); And
since we can NOT do the following - ArrayDeque<Object> list = new ArrayDeque<String>(), the wild card is our only options

B. - A wild carded array list type variable that is bounded by Date, that implies that we will be able to only call
methods that accept arguments on that list which will be of type date or any child type, effectively this super on the
wildcard says - Date is the upper boundary for this type. Also the right hand side creates array list that holds Date
objects to but, it would have also been correct to put any super type including Date and its parents up to Object in
there i.e. this is also valid - ArrayList<? super Date> list = new ArrayList<Object>();. Says this array list contains
anything that is object, but our wild carded type variable is defined to say well, using this variable reference you
will only be able to add types that are Date or any of its children.

A. `ArrayDeque<?> list = new ArrayDeque<String>();`
B. `ArrayList<? super Date> list = new ArrayList<Date>();`
C. List<?> list = new ArrayList<?>();
D. List<Exception> list = new LinkedList<IOException>();
E. `Vector<? extends Number> list = new Vector<Integer>();`
F. None of the above

14. What is the result of the following program?

```java
import java.util.\*;
public class Sorted implements Comparable<Sorted>, Comparator<Sorted> {
    private int num;
    private String text;
    Sorted(int n, String t) {
        this.num = n;
        this.text = t;
    }
    public String toString() { return "" + num; }
    public int compareTo(Sorted s) { return text.compareTo(s.text); }
    public int compare(Sorted s1, Sorted s2) { return s1.num - s2.num; }
    public static void main(String[] args) {
        Sorted s1 = new Sorted(88, "a");
        Sorted s2 = new Sorted(55, "b");
        TreeSet<Sorted> t1 = new TreeSet<>();
        t1.add(s1);
        t1.add(s2);
        TreeSet<Sorted> t2 = new TreeSet<>(s1);
        t2.add(s1); t2.add(s2);
        System.out.println(t1 + " " + t2);
    }
}
Sorted.main(new String[0]);
```

This example might be a bit convoluted but it tries to demonstrate the usage of comparable and comparator
interfaces. The Sorted class implements both, in this case. Note that the comparable is implemented to compare the
text of the Sorted classes, while the comparator interface is implemented to compare the num properties of the
sorted class. This will become important later on.

Now in the implementation of the main method we have two instances of the class - Sorted. And tow TreeSet maps
created one is using the default constructor and the other is using the constructor that takes in a Comparator
implementation, and can be a bit tricky but the s1 Sorted instance is passed in as argument that is because it
already implements the comparator interface.

Meaning that the t1 TreeSet will use the default Comparable method implementation to order the elements inside
itself. While the t2 TreeSet will use the Comparator compare implementation from Sorted to order the elements inside
itself.

Therefore when we add the s1 and s2 instances to the maps and print them, the elements will be ordered by different
criteria since comapreTo and compare implementations are not the same (one orders based on the text, the other based
on the num properties respectively).

A. [55. 88] [55, 88]
B. [55. 88] [88, 55]
C. `[88. 55] [55, 88]`
D. [88. 55] [88, 55]
E. The code does not compile.
F. A runtime exception is thrown.

15. What is the result of the following code?

```java
Comparator<Integer> c = (o1, o2) -> o2 - o1;
List<Integer> list = Arrays.asList(5, 4, 7, 1);
Collections.sort(list, c);
System.out.println(Collections.binarySearch(list, 1));
```

This problem is quite tricky and well hidden, the sorting here is performed in reverse order, meaning that the array
will actually look like this after the sort call - 7, 5, 4, 1. Now the trick here is to understand how binary search
works, by default binary search assume that the array is sorted in ascending order, that is because it expects lower
elements to be to the left, the higher value elements to be to the right - and that comes directly from the default
implementation of the compareTo in Integer. But our array is sorted in the opposite direction. That means that the
result of the binarySearch method call which returns the index of the key/element being searched for will return -1, or
undetermined, it can not 'find' the index of the value its on the wrong end of the array.

Consider the following, the fact that by definition the wrapper Integer implements compareTo, the binarySearch method
has overloads which accept the comparator, that means that if we add/pass our comparator as an argument to the
binarySerach the method will work and return correctly 3, that is index 3 position 4, where the element with value '1'
is, a small change, akin to something like this - Collections.binarySearch(list, 1, c)

A. 0
B. 1
C. 2
D. `The result is undefined.`
E. The code does not compile.
F. A runtime exception is thrown.

16. Which of the following statements are true? (Choose all that apply.)

The comparable interface is actually in the java.lang package because it is a code component of the standard library,
while Comparator is an auxiliary helper interface that allows us to occasionally override certain comparison operations,
in certain situations. The comparable interface defines that something is comparable, and has one method that is int
compareTo(T o);. The comparator implies that a compare action can be performed and usually that is done between two
things and it has one method that is int compare(T a, T b). Both return integer, both have the same semantics and
mandate that positive values are returned when the left is greater than the right and the reverse - when the right is
greater than the left negative value is returned, zero is returned when both are considered equal.

A. Comparable is in the java.util package.
B. `Comparator is in the java.util package.`
C. compare() is in the Comparable interface.
D. `compare() is in the Comparator interface.`
E. compare() takes one method parameter.
F. `compare() takes two method parameters.`

17. Which two options can fill in the blanks to make this code compile? (Choose all that apply.)

```java
public class Generic<________> {
    public static void main(String[] args) {
        Generic<String> g = new Generic<________>();
        Generic<Object> g2 = new Generic();
    }
}
```

A. On line 1, fill in with \<\>.
B. `On line 1, fill in with \<T\>.`
C. On line 1, fill in with \<?\>.
D. `On line 3, fill in with \<\>.`
E. On line 3, fill in with \<T\>.
F. On line 3, fill in with \<?\>.

18. Which of the following lines can be inserted to make the code compile? (Choose all that apply.)

```java
class A {}
class B extends A {}
class C extends B {}
class D<C> {
    // INSERT CODE HERE
}
```

A. `A a1 = new A();`
B. `A a2 = new B();`
C. `A a3 = new C();`
D. C c1 = new A();
E. C c2 = new B();
F. `C c1 = new C();`

19. Which options are true of the following code? (Choose all that apply.)

```java
_________<Integer> q = new LinkedList<>();
q.add(10);
q.add(12);
q.remove(1);
System.out.print(q);
```

This example aims to test the general language knowledge and more specifically the API. LinkedList is something that
implements List and Collection but it it also implements the Queue (Deque) interface. that is unusual because the
ArrayList, the other variant of the most common structure in the java standard library does not implement the Queue
interface.

However even if that is the case, we can not replace it with Queue<Integer> because the .remove(int) method does not
exist on the on the Queue, it does however exist on the Deque interface but it does not remove element by index as it
does in the List interface. It does something different, removes object by value / equality.

A. `If we fill in the blank with List, the output is [10].`
B. If we fill in the blank with List, the output is [10, 12].
C. If we fill in the blank with Queue, the output is [10].
D. If we fill in the blank with Queue, the output is [10, 12].
E. The code does not compile in either scenario.
F. A runtime exception is thrown.

20. What is the result of the following code?

```java
Map m = new HashMap();
m.put(123, "456");
m.put("abc", "def");
System.out.println(m.contains("123"));
```

This one is subtle and again affects the usage and application of the java standard library interface, the map does not
contain a method called contains, that is part of the public interface anyway. It has two containsXXX methods, one is
containsKey and the other is containsValue. If this example were to be containsKey("123") that would have compiled just
fine, but the result would have been false.

A. false
B. true
C. `Compiler error on line 4.`
D. Compiler error on line 5.
E. Compiler error on line 7.
F. A runtime exception is thrown.

21. Fill in the blanks to make this code compile and print 123. (Choose all that apply.)

```java
List<String> list = Arrays.asList("1", "2", "3");
Iterator iter = list.iterator();
while(iter.________()) {
    System.out.print(iter.________());
}
```

This method just tests your general knowledge on the iterator interface. The iterator interface has two primary work
methods - one is the hasNext which checks if the cursor of the iterator can be moved forward to a next element, the next
method is actually the thing that moves the iterator forward. Always we need to first check if there is a next element
to move to before advancing the iterator otherwise the next method might throw NoSuchElementException.

A. `On line 6, fill in the blank with hasNext().`
B. On line 6, fill in the blank with isNext().
C. On line 6, fill in the blank with next().
D. On line 7, fill in the blank with getNext().
E. On line 7, fill in the blank with hasNext().
F. `On line 7, fill in the blank with next().`

22. What code change is needed to make the method compile?

```java
public class Test {
    public static T identity(T t) {
        return t;
    }
}
Test.<Integer>identity(1)
```

In this example we have to realize that in a language like java the modifiers come first, then come the type
definitions. In this case the type parameter definition and the type definition for the return type come after all the
possible modifiers, like access modifier, static, final etc.

A. Add <T> after the public keyword.
B. `Add <T> after the static keyword.`
C. Add <T> after T.
D. Add <?> after the public keyword.
E. Add <?> after the static keyword.
F. No change required. The code already compiles.

23. Which of the answer choices make sense to implement with a lambda? (Choose all that apply.)

A. Comparable interface
B. `Comparator interface`
C. remove method on a Collection
D. removeAll method on a Collection
E. `removeIf method on a Collection`

24. Which of the following compiles and print outs the entire set? (Choose all that apply.)

```java
Set<String> s = new HashSet<>();
s.add("lion");
s.add("tiger");
s.add("bear");
s.forEach(________);
```

A. () -> System.out.println(s)
B. `s -> System.out.println(s)`
C. `(s) -> System.out.println(s)`
D. System.out.println(s)
E. System::out::println
F. `System.out::println`

25. What is the result of the following?

```java
Map<Integer, Integer> map = new HashMap<>();
map.put(1, 10);
map.put(2, 20);
map.put(3, null);
map.merge(1, 3, (a,b) -> a + b);
map.merge(3, 3, (a,b) -> a + b);
System.out.println(map);
```

The following example creates a valid instance of a hash map. Then puts 3 entries inside of it. Now one might
consider that one of the entries contains a null value, that is valid, while maps can NOT STORE NULL KEYS, they can
store null values. The example compiles and produces a valid result which in this case is the map converted to
string. The merge function is a valid method that exists on the Map interface. And what it does it merge a value for
a given key (if exists) with another user provided value (2nd argument of the merge function.

In our example the first merge(1,3, ...) call will merge the existing value for key 1, which is 10, with the value 3
by simply adding them together (as described by the lambda). In the second merge(3, 3, ...) since the value for that
key is null what merge does is simply put in the value for the key, effectively mapping the value 3 to the key 3

```java
V oldValue = map.get(key);
V newValue = (oldValue == null) ? value : remappingFunction.apply(oldValue, value);
if (newValue == null) {
    map.remove(key);
} else {
    map.put(key, newValue);
}
```

One small note is that if the new value as provided by the merge/remappingFunction ends up being null, the merge
function will remove that key/value pair from the map, otherwise it will attempt to put it into the map for the same
key.

A. {1=10, 2=20}
B. {1=10, 2=20, 3=null}
C. {1=10, 2=20, 3=3}
D. {1=13, 2=20}
E. {1=13, 2=20, 3=null}
F. `{1=13, 2=20, 3=3}`
G. The code does not compile.
H. An exception is thrown.
