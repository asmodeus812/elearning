1. What is the result of executing the following application? (Choose all that apply.)

```java
import java.util.concurrent.*;
import java.util.stream.*;
public class BabyPandaBathManager {
    public static void await(CyclicBarrier cb) {
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            // Handle exception
        }
    }
    public static void main(String[] args) {
        final CyclicBarrier cb = new CyclicBarrier(3,()-> System.out.
            println("Clean!"));// u1
        ExecutorService service = Executors.newScheduledThreadPool(2);
        IntStream.iterate(1, i-> 1) // u2
            .limit(12)
            .forEach(i-> service.submit( // u3
            ()-> await(cb))); // u4
        service.shutdown();
    }
}
BabyPandaBathManager.main(new String[0])
```

The program above has a few key points, first the cyclic barrier that is created is indeed crated correctly, it will
have to await for 3 threads to call await on it, before it can release the threads, until then all threads calling the
await will block. Now the second core element is the thread pool that is being created with only 2 threads, so that
means that we will never hit the limit of 3 for the barrier, to release the threads so they will block indefinitely. The
third element which is there to confuse you is the iterator that pretends to create 12 threads, really it is creating
and starting the first 2, which start blocking immediately and nothing can release them since there is not enough space
in the pool.

- It outputs Clean! at least once.
- It outputs Clean! four times.
- The code will not compile because of line u1.
- The code will not compile because of line u2.
- The code will not compile because of line u3.
- The code will not compile because of line u4.
- It compiles but throws an exception at runtime.
- `It compiles but waits forever at runtime. (X)`

2. What is the result of trying to compile and run this program

```java
public abstract class Message {
    public String recipient;
    public abstract final void sendMessage();
    public static void main(String[] args) {
        Message m = new TextMessage();
        m.recipient = "1234567890";
        m.sendMessage();
    }
    static class TextMessage extends Message {
        public final void sendMessage() {
            System.out.println("Text message to " + recipient);
        }
    }
}
Message.main(new String[0])
```

On line 3 we have something that is invalid, that is a method is defined as both `abstract & final` that is not valid in
java and will throw compiler error

```
Error:
illegal combination of modifiers: abstract and final
    public abstract final void sendMessage();
    ^---------------------------------------^
```

A. Text message to null.
B. Text message to 1234567890.
C. A compiler error occurs on line 1.
D. `A compiler error occurs on line 3. (X)`
E. A compiler error occurs on line 7.
F. A compiler error occurs on another line.

3. What is the result of executing the following code? (Choose all that apply.)

```java
import java.io.*;
public class Tail {}
public class Bird implements Serializable {
    private String name;
    private transient int age;
    private Tail tail;
    public String getName() { return name; }
    public Tail getTail() { return tail; }
    public void setName(String name) { this.name = name; }
    public void setTail(Tail tail) { this.tail = tail; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public void main(String[] args) {
        try(InputStream is = new ObjectInputStream(
            new BufferedInputStream(new FileInputStream("birds.dat")))) {
            Bird bird = is.readObject();
        }
    }
}
Tail.main(new String[0])
```

There are many, many things wrong here that will lead to a compile time error, way before we even reach the obvious fact
that the birds.dat file probably does not exist and throw something at runtime, which might be the immediate conclusion

- First the main method does not define that it must throw an exception, which is mandated because of the constructor
  calls of FileInputStream (file not found) and the ObjectInputStream (reading stream header) might throw checked exceptions
- Second even if that was fine there is a call on the InputStream object for readObject, but that method is strictly on
  the ObjectInputStream, not the interface of InputStream
- Thirdly even if that was all fine that readObject method returns an object, and we can not up-cast implicitly from
  Object to Bird

A. It compiles and runs without issue.
B. The code will not compile because of line 3.
C. The code will not compile because of line 5.
D. `The code will not compile because of lines 16–17. (X)`
E. The code will not compile because of line 18.
F. It compiles but throws an exception at runtime.

4. What is the result of the following case

```java
public class Box<T> {
    T value;
    public Box(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }
    public static void main(String[] args) {
        Box<String> one = new Box<String>("a string");
        Box<Integer> two = new Box<>(123);
        System.out.print(one.getValue());
        System.out.print(two.getValue());
    }
}
Box.main(new String[0])
```

That one should print the value `a string123`.

A. Compiler error on line 1.
B. Compiler error on line 2.
C. Compiler error on line 11.
D. Compiler error on line 12.
E. `a string123`
F. An exception is thrown.

5. What is the result of executing the following code snippet?

```java
List<Integer> source = new ArrayList<>(Arrays.asList(1,2,3,4));
List<Integer> fish = new CopyOnWriteArrayList<>(source);
List<Integer> mammals = Collections.synchronizedList(source);
Set<Integer> birds = new ConcurrentSkipListSet<>();
birds.addAll(source);
synchronized(new Integer(10)) {
    for(Integer f: fish) fish.add(4); // c1
    for(Integer m: mammals) mammals.add(4); // c2
    for(Integer b: birds) birds.add(5); // c3
    System.out.println(fish.size()+" "+mammals.size()+" "+birds.size());
}
```

This is more convoluted, it hides the actual issue, the bait points, being the source which is copied in the
CopyOnWriteArrayList, and elements added to the ConcurrentSkipListSet, are fine. The synchronizedList, is directly
backed by the source list meaning it references it directly, that is also a point of a possible issue. However the error
here is more subtle and hidden, the standard for loop in java invokes the iterator of the collection class meaning that
the script actually tries to .add() while iterating which will produce ConcurrentModificationException right on the very
first attempt to add while iterating.

A. It outputs 4 8 5.
B. It outputs 8 4 5.
C. It outputs 8 8 8.
D. The code does not compile.
E. `It compiles but throws an exception at runtime on line c1.`
F. It compiles but throws an exception at runtime on line c2.
G. It compiles but throws an exception at runtime on line c3.
H. It compiles but enters an infinite loop at runtime.

6. What changes would need to be made to make the following immutable object pattern correct? (Choose all that apply.)

```java
import java.util.List;
public class Duck {
    private String name;
    private List<Duck> ducklings;
    public Duck(String name, List<Duck> ducklings) {
        this.name = name;
        this.ducklings = new ArrayList<Duck>(ducklings);
    }
    public String getName() { return name; }
    public List<Duck> getDucklings() { return ducklings; }
    public String hasDucklings(Predicate<Duck> p) {
        return p.test(this) ? "Quack Quack": "";
    }
}
```

In the following example we have to ensure first and foremost the return of the getDucklings does not actually provide
direct access to the mutable list in that case the list of ducklings, the rest of the actions we can take like making
the fields and the class final are a very good steps in that direction too.

A. None, the immutable object pattern is properly implemented.
B. `Mark name and ducklings final.`
C. `Mark the Duck class final.`
D. Have Duck implement the Immutable interface.
E. `Remove the hasDucklings() method since any lambda expressions passed to it could modify the Duck object.`
F. `Replace the getDucklings() with a method (or methods) that do not give the caller direct access to the List<Duck> ducklings.`
G. Change the type of List<Duck> to be List<Object>.

7. Assuming the current directory /bats/day and all of the files and directories referenced here exist and are available
   within the file system, what is the result of executing the following code?

```java
Path path1 = Paths.get("/bats/night","../").resolve(Paths.get("./sleep.txt")).normalize();
Path path2 = new File("../sleep.txt").toPath().toRealPath();
System.out.print(Files.isSameFile(path1,path2));
System.out.print(" "+path1.equals(path2));
```

The file structure here looks something like this, and we assume that program.java is started form the same directory
where it resides, that way we can reference ../ relative directory/path elements correctly

```txt
/bats
    sleep.txt
    /nights
        program.java
```

This one might be bit tricky but still manageable, first we have to look at the first path that one is absolute, after
the normalization all of the intermediate jumps like ../ will be removed, and we will end up with the /bats/sleep.txt,
since we go back one directory and that is where the file resides. The second path is a relative one, and lets assume
that the program was started in the child directory or in other words the "night" directory within "bats", then the line
new File("../sleep.txt").toPath().toRealPath(), will actually produce first a relative path with toPath, then toRealPath
will convert that to an absolute path which resolves all the symlinks etc.

That alone will make the two paths equal indeed the first one and the second one. But if we were to remove the
toRealPath, and just compare them like that then they will not be equal because the path with which the File was created
and the path obtained from toPath is a relative one, and the first path that was created manually was an absolute one,
and after the normalization the ../ are resolved and removed correctly, then the paths would NOT have been equal

After all of that being said we know that these two paths are the same so the two print statements will print true
because both statements are true, both paths point at the same file, and both paths have the same form at the moment of
equality testing, because both are converted to absolute form

A. `true true`
B. false false
C. true false
D. false true
E. The code does not compile.
F. The code compiles but throws an exception at runtime.

8. What statements are true about the following code? (Choose all that apply.)

```java
public class Tail {}
public class Animal {
    public String name;
}
public class Canine extends Animal {
    public Tail tail;
}
public class Wolf extends Canine {}
```

The example here demonstrates the relationship between the has-a and is-a between classes when we talk about composition
and inheritance, in the example above we can clearly see the distinction between the has and is. The Animal and Canine
and Wolf have name, and a tail. The Canine and Wolf are both animals, and the Wolf is a type/kind of Canine.

A. `Wolf has-a name.`
B. `Wolf has-a Tail.`
C. Wolf is-a Tail.
D. `Wolf is-a Animal.`
E. Canine is-a Wolf.
F. Animal has-a Tail.

9. Which of the following can fill in the blank? (Choose all that apply.)

```java
public void stmt(Connection conn, int a) throws SQLException {
    Statement stmt = conn.createStatement(a, __________________________);
}
```

TODO:

A. ResultSet.CONCUR_READ_ONLY
B. ResultSet.CONCUR_INSERTABLE
C. ResultSet.CONCUR_UPDATABLE
D. ResultSet.TYPE_FORWARD_ONLY
E. ResultSet.TYPE_SCROLL_INSENSITIVE
F. ResultSet.TYPE_SCROLL_SENSITIVE

10. Which of the following statements is true when the code is run with java AssertDemo?

```java
public class AssertDemo {
    public static void main(String [] args) {
        Integer x = 10;
        x++;
        assert x == null;
        System.out.println(x);
    }
}
AssertDemo.main(new String[0])
```

Some people might get baited into thinking that the ++ might produce a compile time error, while that is not true
because the auto-boxing and auto un-boxing will trigger here and the compiler will produce a more concise version which
does the increment and assign, into a separate statement. The rest is straightforward and we do not get any compile time
errors but we will receive an AssertionError. The variable will be incremented and become 11, but never actually get
printed to the stdout

```java
public static void main(String[] var0) {
      Integer var1 = 10;
      var1 = var1 + 1;
      assert var1 == null;
      System.out.println(var1);
}
main(new String[0])
```

A. Line 3 generates a compiler error.
B. Line 4 generates a compiler error.
C. Line 5 generates a compiler error.
D. `Line 5 throws an AssertionError at runtime.`
E. The output is 10.
F. The output is 11

11. Which of the following are true? (Choose all that apply.)

```java
private static void magic(Stream<Integer> s) {
    Optional o = s.filter(x -> x < 5).limit(3).max((x, y) -> x-y);
    System.out.println(o.get());
}
```

The example above has a few trap holes, mainly the filter, limit, max and get calls. The limit will always ensure that
we will never loop infinitely so that should be fine. The max fill try to find a maximum element of the ones present
after the filter. The filter makes sure to filter out all of the elements that are greater or equal to 5, leaving only
elements in the range, between [1...4]. The Optional.get call on the optional will fail in case there are no elements in
the stream, or the filter produced no elements even though it had some initially.

Stream.iterate(1, x -> x++) - this one might be a bit complicated, the loop above will loop just once, why ? Because x
is 1, it is returned and then incremented, the x value itself is not a reference of any kind, but a copy, therefore the
incrementing does not affect it, the stream will loop just once. Meaning that the filter.limit.max chain will produce
all elements less than 5, limit to 3, and find the max of those, there is only one element in this stream that is [1],
therefore the result is 1

IMPORTANT: The order of stream chain operations matter a lot, if we change it such that we first limit and then filter,
that will ensure that the limit is enacted first then we filter the elements. That is important because if we choose to
iterate over a long sequence it will ensure that the sequence is terminated the moment the limit is reached , otherwise
the full sequence will first be filtered, then, limited and that is a waste of operations. That only applies in this
case where the items are sorted/ordered already in ascending order, otherwise which comes first limit or filter makes
difference for non-ordered streams.

A. magic(Stream.empty()); runs infinitely.
B. `magic(Stream.empty()); throws an exception.`
C. magic(Stream.iterate(1, x -> x++)); runs infinitely.
D. magic(Stream.iterate(1, x -> x++)); throws an exception.
E. `magic(Stream.iterate(1, x -> x++)); prints 1`
F. magic(Stream.of(5, 10)); runs infinitely.
G. `magic(Stream.of(5, 10)); throws an exception.`
X. The method does not compile. (there was a strange dash Unicode character between x-y, that is probably encoding issue)

12. Suppose that we have the following property files and code. Which bundle is used on lines 7 and 8, respectively?

```properties
Dolphins.properties
name=The Dolphin
age=0

Dolphins_fr.properties
name=Dolly

Dolphins_fr_CA.properties
name=Dolly
age=4
```

```java
Locale fr = new Locale("fr");
ResourceBundle b = ResourceBundle.getBundle("Dolphins", fr);
b.getString("name");
b.getString("age");
```

The locale that we load is strictly the French one that implies that properties will be first read from the french
locale and then fallback to the default where no value is present, in this case the value that is not present is the
age, so the age will be picked up from the default properties file, the name will be picked directly from the Dolphins_fr

A. Dolphins.properties and Dolphins.properties
B. Dolphins.properties and Dolphins_fr.properties
C. Dolphins_fr.properties and Dolphins_fr.properties
D. `Dolphins_fr.properties and Dolphins.properties`
E. Dolphins_fr.properties and Dolphins_fr_CA.properties
F. Dolphins_fr_CA.properties and Dolphins_fr.properties

13. What is the result of executing the following code? (Choose all that apply.)

```java
String line;
Console c = System.console();
if ((line = c.readLine()) != null) {
    System.out.println(line);
}
```

While these methods can throw, like when obtaining the system console object, or when doing readLine call, those are all
runtime exceptions and are not checked. In this case unless the user somehow triggers an exception on stdin there is
nothing wrong in the code that would prevent a normally entered data on stdin to be printed. User might enter some
invalid Unicode sequence that might not be parsed correctly or something similar but those are exceptional cases which
our example does not wish to handle

A. The code runs without error but prints nothing.
B. `The code prints what was entered by the user.`
C. An ArrayIndexOutOfBoundsException might be thrown.
D. A NullPointerException might be thrown.
E. An IOException might be thrown.
F. The code does not compile.

14. How many compilation issues are in the following code?

```java
public class Compiles {
    class RainException extends Exception {} // that is non-static class type we can not reference it from the main method
    public static void main(String[] args) { // throws Exception, there is no catch block, we also call close in finally which also throws
        try(Scanner s = new Scanner("rain"); String line = "";) { // the type String for the variable line, is not implementing Closeable
            if (s.nextLine().equals("rain")) {
                throw new RainException();
            }
        } finally {
            s.close(); // invalid, the variable is outside this scope, it is defined and declared in the try block but is not visible here
        }
    }
}
Compile.main(new String[0])
```

The ; at the end of the try block is a rouse, we can actually put a ; there the String itself is what will produce the
compile time error because the String type does not implement the Closeable or AutoCloseable interface which is mandated
for variables defined in the try with resources block.

- class RainException - not defined as static therefore we can not reference it in the static void main, neither create an instance of it.
- main(String[] args) - missing the throws declaration because there is a checked exception being thrown out of this method
- String line = "" - in the try with resources block is not possible for types that do not implement Closeable or AutoCloseable
- s.close(); - calling close on a symbol that does not exist, the Scanner is declared in the try with resources block,
  outside of the scope of finally

A. 0
B. 1
C. 2
D. 3
E. `4`
F. 5

15. What is the result of the following code?

```java
public class VisitPark {
    enum AnimalsInPark {
        SQUIRREL, CHIPMUNK, SPARROW;
    }
    public static void main(String[] args) {
        AnimalsInPark[] animals = AnimalsInPark.values();
        System.out.println(animals[1]);
    }
}
VisitPark.main(new String[0])
```

There is nothing special in this case, besides a few minor nits. First the enum is not defined static but its implicitly
static, there is no way to define non-static inner enum type, they are singletons never meant to be bound to class
instances, its default access modifier is default package private, but for this use case that is fine. The values method
will indeed return the array of animals and each enum literal has a default toString implementation that matches the
name of the literal as defined in code. We extract the 2-nd element, or in other words the one at index 1, which is the
CHIPMUNK enum literal

A. `CHIPMUNK`
B. SQUIRREL
C. The code compiles, but the output is indeterminate.
D. A compiler error occurs on line 2.
E. A compiler error occurs on line 6.
F. A compiler error occurs on line 7.

16. Which of the answer choices is printed out by the following code?

```java
import java.time.Duration;
import java.time.Period;
String d = Duration.ofDays(1).toString();
String p = Period.ofDays(1).toString();
boolean b1 = d == p;
boolean b2 = d.equals(p);
System.out.println(b1 + " " + b2);
```

There are a few gotchas here, the first one is what interface exists on both period and duration, we know that the
duration and period refer to different types of time units, Period measures a period that has a granularity of Days,
Months, Years, etc. While the Duration measures time with a granularity of Days, Hours, Minutes, Seconds etc. Both of
these types have an overlapping method in their interface that create instances of the Duration/Period ofDays. The
second gotcha is the first string comparison which is by reference, which will produce false. The call to the equals
method is a bit more subtle, why is that ? The toString methods of both Period and Duration, produce a different result
since they measure a different amount of time, even though they are created for the same amount of time in this case a
single day, therefore the equals result will also be false

A. `false false`
B. false true
C. true false
D. true true
E. The code does not compile.
F. A runtime exception is thrown.

17. Assuming that the directory /gorilla exists within the file system with the numerous files including
    signed-words.txt, what is the result of executing the following code? (Choose all that apply.)

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

Path path = Paths.get("/gorilla/signed-words.txt");

Files.find(path.getParent(),10.0, // k1
    (Path p) -> p.toString().endsWith(".txt") && Files.isDirectory(p)) // k2
    .collect(Collectors.toList())
    .forEach(System.out::println);

Files.readAllLines(path) // k3
    .flatMap(p -> Stream.of(p.split(" "))) // k4
    .map(s -> s.toLowerCase()) // k5
    .forEach(System.out::println);
```

There are a few issues with this implementation here, first the find method in Files, does not take a float/double as
its second argument, but an integer, and down-casting to integer from float/double is not allowed, therefore type
inference can not occur. Second the BiPredicate that the find method accepts as its third argument has two arguments the
Path and the BasicFileAttributes not just one.

Then the second part of this implementation is the Files.readAllLines which returns a collection of lines, not a stream,
there is a method called lines, that returns stream, and that one would have been more appropriate to use, but that
stream has to be closed - put into try-with-resources,because it probably wraps the IO underneath.

A. The code compiles but does not produce any output at runtime.
B. `It does not compile because of line k1.`
C. `It does not compile because of line k2.`
D. It does not compile because of line k3.
E. `It does not compile because of line k4.`
F. The code prints all of the .txt files in the directory tree.
G. The code prints all of the words in the signed-words.txt file, each on a different line.

18. Which of the following statements can fill in the blank to make the code compile success- fully? (Choose all that
    apply.)

```java
Set<? extends RuntimeException> set = _________________________________
```

Generics are invariant - List<Integer> is not a List<Number>. Use wildcards, ? extends means safe to read, unsafe to
add, ? super the opposite. With ? super, reads are Object unless you use capture/helper methods. While the code will
compile with the options C and D we will not be able to call the add method on the set, the compiler will produce an
error

? extends RuntimeException tells the compiler that the wildcard is a child of the given type that is on the right of the
wild card. What that really translates into is saying that this set contains some unknown type that extends
RuntimeException, some child of RuntimeException, therefore we can not add into the set since we know not which exact
type that extends off of it. IT IS NOT the same as saying the set is of type Set<RuntimeException> != Set<? extends
RuntimeException>,these say different things yet both mean the same , the set contains a concrete type in one case the
concrete type is known it is RuntimeException in the other it is not known but some child of RuntimeException.

? super RuntimeException tells the compiler that the wildcard is anything that is parent of the given type on the right
of the wild card. In other words we actually define the upper bound with this wildcard, in this case these are
Exception, Throwable and Object. That means that we can only add instances that are children of these types, or in other
words anything that is RuntimeException or a child of it. While we can add, we can not read a concrete type, why is that
? Well because the compiler does not know which super type we have chosen, it could be at least one of 3 for our use
case ? Exception, Throwable or Object, so the compiler takes the safe route and allows us to read only Object and we
have to check the type to see if we can cast it to a lower bound in the hierarchy

What is important to note here is that generic types are not covariant/invariant. They just like any other type are
concrete, and are not a substitute for polymorphic behavior or types

A. new HashSet<? extends RuntimeException>();
B. new HashSet<Exception>();
C. `new TreeSet<RuntimeException>();`
D. `new TreeSet<NullPointerException>();`
E. None of the above

TODO: move me to the generics documentation
Set<RuntimeException> set = new HashSet<>();
set.add(new IllegalStateException());
set.add(new NullPointerException());
Set<? extends RuntimeException> set2 = set;
set2.stream().forEach(Exception::toString);

19. Which of the following position a ResultSet cursor to a location immediately before the first row? (Choose all that apply.)

This might be tricky because it says before the first row ? What does it mean though, the result set can be put right
before the very first row, which would imply that the result set is not yet ready to be read, there is no active row.
That means that all getXXX methods will throw, in order to move to the very first row we need to use next, that will put
the cursor exactly on the first row then we can start reading.

Some of the methods below move the cursor right at the start of the result set, before the first result and some at the
very first row exactly, meaning that a call to next will move to the second row.

A. rs.absolute(-1)
B. `rs.absolute(0)`
C. rs.absolute(1)
D. `rs.beforeFirst()`
E. rs.first()
F. rs.next()

20. Assume that today is June 1, 2016. What is the result of the following?

```java
import java.time.*;
Stream<LocalDate> s = Stream.of(LocalDate.now());
UnaryOperator<LocalDate> u = l -> l;
s.filter(l -> l != null).map(u).peek(System.out::println);
```

There is no output here, this is a strange example, the stream is never called with a terminating operation, peek is not
a terminating operation for the stream, therefore nothing will get outputted or printed out to the console. Maybe if we
had a call to count at the very end after the peek, that would have terminated the stream and the entire chain of
functions or transformers would have been applied to the stream.

A. 2016–05–01
B. 2016–06–01
C. `There is no output.`
D. The output is something other than 2016–05–01 or 2016–06–01.
E. The code does not compile.
F. An exception is thrown.
