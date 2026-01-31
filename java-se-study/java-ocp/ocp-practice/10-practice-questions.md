1. Which classes will allow the following to compile? (Choose all that apply.)

```java
InputStream is = new BufferedInputStream(new FileInputStream("zoo.txt"));
InputStream wrapper = new ____________(is);
```

This example shows a typical example of wrapping a concrete file input stream into a buffered input stream. The only
types of streams that can take another type of streams as constructor arguments are those that are decorator/wrapper
based in this case that would be `BufferedInputStream`, and the `ObjectInputStream`. We can certainly chain multiple
`BufferedInputStream` together which is somewhat pointless but a valid construct.

A. `BufferedInputStream` - constructs from input stream
B. FileInputStream - constructs only from file
C. BufferedWriter - constructs from writer
D. `ObjectInputStream` - constructors from input stream
E. ObjectOutputStream - constructs from output stream
F. BufferedReader - constructrs from reader

2. Why does Console.readPassword() return a char[] array instead of a String object? (Choose all that apply.)

This is somewhat interesting, the idea is that we can immediately clean out the password from memory by resetting
the contents of the char array. That is true, however is the password contains Unicode chars, we might not be able
to do much with it

A. It improves performance. 3.
B. It is more secure.
C. To encrypt the password data.
D. To support all character encodings.
E. Because Java puts all String values in a reusable pool.
F. `So that the value can be removed from memory immediately after use.`

3. Which of the following are true? (Choose all that apply.)

The console is a singleton, there is a console instance that is stored as static variable in the console class and the
console constructor itself is also private. The console class instance is created the very first time the console is
obtained and attached to the descriptors of stdin/stdout

A. A new Console object is created every time System.console() is called.
B. Console can only be used for reading input and not writing output.
C. `Console is obtained using the singleton pattern.`
D. `When getting a Console object, it might be null.`
E. When getting a Console object, it will never be null.

4. Which of the following can fill in the blank to make the code compile? (Choose all that apply.)

```java
Console c = System.console();
String s = _________________;
```

The only valid method here that returns a string is the `readLine`, the rest are either ones that do not exist on the
console interface or do not return string like `readPassword`

A. c.input()
B. c.read()
C. `c.readLine()`
D. c.readPassword()
E. c.readString()
F. None of the above

5. What is the result of executing the following code? (Choose all that apply.)

This one is a bit tricky the console class indeed exposes full control over the internal reader and writer instances
that are attached to stdin/out and we can obtain them and use them as any other reader or writer, without having to
obey the interface provided by the console class. In this case this code does that, reads a line and then directly
appends it back through the interface of the writer back to stdout.

```java
String line;
Console c = System.console();
Writer w = c.writer();
if ((line = c.readLine()) != null)
w.append(line);
w.flush();
```

A. The code runs without error but prints nothing.
B. `The code prints what was entered by the user.`
C. An ArrayIndexOutOfBoundsException might be thrown.
D. A NullPointerException might be thrown.
E. An IOException might be thrown.
F. The code does not compile.

6. Which of the following are true statements about serialization in Java? (Choose all that apply.)

The more confusing part here is E, the reason that might happen is because the JVM has to load the actual underlying
class type, to construct the class instance do the cast internally, and if the class loader can not find the class
or the class is invalid, then that exception will indeed be thrown.

A. `The process of converting serialized data back into memory is called deserialization.`
B. All non-thread classes should be marked Serializable.
C. The Serializable interface requires implementing serialize() and deserialize() methods.
D. The Serializable interface is marked final and cannot be extended.
E. `The readObject() method of ObjectInputStream may throw a ClassNotFoundException even if the return object is not explicitly cast.`

7. Fill in the blank:

```plaintext
is the topmost directory on a file system.
```

The root is the term we are looking for here, it is the top most level of a file system.

A. Absolute
B. Directory
C. Parent
D. `Root`
E. Top

8. Assuming / is the root directory, which of the following are true statements? (Choose all that apply.)

The file pointed to by a file object, does not need to exist at the moment of File object construction, the
constructor does not do any file IO, but the statement that IT MUST exist ? How do we interpret this , MUST
according to who ? the exception is checked therefore we can catch it and based on our implementation that might not
be a mistake what IS A MUST referring to HERE ?

A. `/home/parrot is an absolute path.`
B. /home/parrot is a directory.
C. /home/parrot is a relative path.
D. The path pointed to from a File object must exist.
E. The parent of the path pointed to by a File object must exist.

9. What are the requirements for a class that you want to serialize with `ObjectOutputStream`? (Choose all that apply.)

For default Java serialization via `ObjectOutputStream`, the object’s class must implement Serializable. Otherwise you
get `NotSerializableException`. And it is generally advised that all reachable fields of that class implement the
Serializable interface as well.

A. `The class must implement the Serializable interface.`
B. The class must extend the Serializable class.
C. The class must declare a static serialVersionUID variable.
D. `All instance members of the class must be Serializable.`
E. All instance members of the class must be marked transient.
F. Any class can be serialized with ObjectOutputStream.

10. The following method is designed to delete a directory tree recursively. Which of the following properties
    reflect the method definition? (Choose all that apply.)

```java
public static void deleteTree(File file) {
    if(!file.isFile()) {
        for(File entry : file.listFiles()) {
            deleteTree(entry);
        }
    } else {
        file.delete();
    }
}
```

One of the possible options here is a bit misleading, since the method actually deleted all files in a given
directory, if a directory is provided, and a single file if a file is provided. The exception part is true, delete
method might throw in case the file is not accessible or generally there are some issues with deleting it, maybe its
protected etc.

A. It can delete a directory that contains only files.
B. It can delete a directory tree of arbitrary length.
C. `It can delete a single file.`
D. The code will not compile because of line 2.
E. The code will not compile because of line 3.
F. `It compiles but may throw an exception at runtime.`

11. Which of the following are methods available to instances of the java.io.File class? (Choose all that apply.)

Given the options presented below there are only a few that actually exist on the File class that deal with -
creating directories & renaming files

A. mv()
B. createDirectory()
C. `mkdirs()`
D. move()
E. `renameTo()`
F. copy()
G. `mkdir()`

12. Suppose that the file c:\book\java exists. Which of the following lines of code creates an object that represents
    the file? (Choose all that apply.)

The only one that is valid for a windows environment is the one that uses the double back slash, because the back
slash is a special escape character, we need to escape it with another back slash

A. new File("c:\book\java");
B. `new File("c:\\book\\java");`
C. new File("c:/book/java");
D. new File("c://book//java");
E. None of the above

13. Which of the following are built-in streams in Java? (Choose all that apply.)

There are really just three primary ones that are exposed by the system class, those are err, in and out, these are
attached to the file descriptors for the `stdout, stderr, and stdin.`

A. `System.err`
B. System.error
C. `System.in`
D. System.input
E. `System.out`
F. System.output

14. Which of the following are NOT java.io classes? (Choose all that apply.)

The only one that does not exist, actually is the `PrintReader`, that would be the equivalent of having a `sscanf` from
C, where we might be able to read formatted text into variables, but does not exist in java by default. The Scanner
is an alternative but it is more coarse and very limited compared to `sscanf`.

A. BufferedReader
B. BufferedWriter
C. FileReader
D. FileWriter
E. `PrintReader`
F. PrintWriter

15. Assuming zoo-data.txt is a multi line text file, what is true of the following method?

```java
private void echo() throws IOException {
    try (FileReader fileReader = new FileReader("zoo-data.txt"); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
        System.out.println(bufferedReader.readLine());
    }
}
```

This is well formatted. There are few gotchas here related to the close method, first we can see that the method
throws `IOException` that is because the close method throws, and the try-with-resources is not going to handle that
for us. Then the reader constructor does indeed accept a string file name, besides having other overloads - File as
well for example. The read line is a method that exists on buffered reader. The method generally will read the first
line print it and close the file

A. `It prints the first line of the file to the console.`
B. It prints the entire contents of the file.
C. The code does not compile because the reader is not closed.
D. The code does compile, but the reader is not closed.
E. The code does not compile for another reason.

16. Why shouldn’t every class be marked Serializable? (Choose all that apply.)

It is generally not advised to serialize fields that are of a more complex type - threads, processes, etc. Also static
fields are not serialized usually because they are not tied to a class instance, but the class type itself.

A. The compiler will throw an exception if certain classes are marked Serializable.
B. Only final classes can be marked Serializable.
C. Classes can implement only one interface, so marking them Serializable would prevent them from using any other interface.
D. `The data of some classes cannot be easily serialized, such as those managing threads or processes.`
E. Only concrete classes can be marked Serializable.
F. `Classes that store most of their data in static fields would not be easily serialized.`

17. Which of the following stream classes are decorators or wrapper stream types, that only wrap around other
    streams ? (Choose all that apply.)

The ones that DO NOT wrap around streams are - `FileWriter(File), OutputStream(abstract), FileInputStream(File)`

A. `ObjectInputStream`
B. `PrintStream`
C. FileWriter
D. `PrintWriter`
E. OutputStream
F. FileInputStream
G. `ObjectOutputStream`

18. Which values when inserted into the blank would allow the code to compile? (Choose all that apply.)

```java
Console console = System.console();
String color = console.readLine("What is your favorite color? ");
console._______("Your favorite color is " + color);
```

Console has only one print or output method really, that would be the `printf`, the other way we can access the
output is through the writer method, which returns a Writer instance, and on the Writer interface we have much
richer `print*` variations

A. print
B. `printf`
C. println
D. format
E. `writer().println`
F. .out

19. Suppose that you need to write data that consists of int, double, boolean, and String values to a file that
    maintains the format of the original data. For performance reasons, you also want to buffer the data. Which
    three java.io classes can be chained together to best achieve this result?

A. FileWriter
B. `FileOutputStream`
C. `BufferedOutputStream`
D. `ObjectOutputStream` and `DataOutputStream`
E. DirectoryStream
F. PrintWriter
G. PipedOutputStream

20. What are some reasons to use a character stream, such as Reader/Writer, over a byte
    stream, such as InputStream/OutputStream? (Choose all that apply.)

A. `More convenient code syntax when working with purely String data`
B. Improved performance
C. `Automatic character encoding`
D. Built-in serialization and deserialization
E. Character streams are high-level streams
F. Multi-threading support

21. Assuming the following class has proper public getter/setter methods for all of its private fields, which of the
    following fields will always be null after an instance of the class is serialized and then deserialized?
    (Choose all that apply.)

```java
public class Zebra implements Serializable {
    private static final long serialUID = 1L;
    private transient String name = "George";
    private static String birthPlace = "Africa";
    private transient Integer age;
    private List<Zebra> friends = new ArrayList<>();
    private Object tail = null;

    {
        age = 10;
    }

    public Zebra() {
        this.name = "Sophia";
    }
}
```

- `age` - will be null because it is transient, never saved to the file.
- `name` - also transient never sent and serialized to the file.
- `tail` - never serialized, initial value was null anyway.
- `friends` - non-transient, and also the `ArrayList` is serializable
- `birthPlace` - static field its initialization not governed by serialization

A. `name`
B. `tail`
C. `age`
D. friends
E. birthPlace
F. The code does not compile.
G. The code compiles but throws an exception at runtime.

22. What is the value of name after an instance of Eagle is serialized and then deserialized?

```java
public class Bird implements Serializable {
    protected transient String name = "Bridget";
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Bird() {
        this.name = "Matt";
    }
}
public class Eagle extends Bird implements Serializable {
    {
        this.name = "Janette";
    }
    public Eagle() {
        this.name = "Daniel";
    }
}
```

The entire name field is declared as transient, that would imply that the field will never be saved, on top of that
the field itself, is constructed multiple times in a constructor, initialization block and so on, but these are not
going to be run at all, when de-serializing an object from the file.

A. Bridget
B. Matt
C. Janette
D. Daniel
E. `null`
F. The code does not compile.
G. The code compiles but throws an exception at runtime.
H. The value may not be known until runtime.

23. Assume that you have an `InputStream` whose next bytes are `XYZABC`. What is the result of calling the following
    method on the stream, using a count value of 3?

```java
public static String pullBytes(InputStream is, int count) throws IOException {
    is.mark(count);
    final StringBuilder sb = new StringBuilder();
    for(int i=0; i<count; i++) {
        sb.append((char)is.read());
    }
    is.reset();
    is.skip(1);
    sb.append((char)is.read());
    return sb.toString();
}
```

A. It will return a String value of XYZ.
B. It will return a String value of XYZA.
C. It will return a String value of XYZX.
D. `It will return a String value of XYZB`.
E. It will return a String value of XYZY.
F. The code does not compile.
G. The code compiles but throws an exception at runtime.
H. The result cannot be determined with the information given.

24. Fill in the blanks: Writer is\***\*\_\_\*\*** that related stream classes\***\*\_\_\*\*** .

A. a concrete class, extend
B. `an abstract class, extend`
C. an interface, extend
D. an interface, implement

25. Which of the following methods is defined in java.io.File?

A. createDirectory()
B. getLength()
C. listFile()
D. `renameTo()`

26. Which method in InputStream can be used in place of calling skip(1)?

A. jump()
B. mark()
C. `read()`
D. reset()

27. Which methods are classes that implement java.io.Serializable required to implement?

A. deserialize()
B. serial()
C. serialize()
D. `None of the above`

28. Fill in the blanks: Given a valid Console instance, reader() returns a\***\*\_\_\*\*** , while writer() returns a \***\*\_\_\*\***.

A. PrintReader, PrintWriter
B. PrintReader, Writer
C. `Reader, PrintWriter`
D. StringReader, Writer

29. Assuming the file path referenced in the following class is accessible and able to be written, what is the
    output of the following program?

```java
package alarm;
import java.io.\*;
public class Smoke {
    public void sendAlert(File fn) {
        try(BufferedWriter w = new BufferedWriter(new FileOutputStream(fn))) {
            w.write("ALERT!");
            w.flush();
            w.write('!');
            System.out.print("1");
        } catch (IOException e) {
            System.out.print("2");
        } finally {
            System.out.print("3");
        }
    }
    public static void main(String[] testSignal) {
        new Smoke().sendAlert(new File("alarm.txt"));
    }
}
```

The BufferedWriter while a class that exists, does not accept as a part of its constructor a File output stream it
has to be a file writer, or otherwise use the bridge class that bridges the writer and output stream such as -

A. 3
B. 13
C. 23
D. `The code does not compile.`

30. Which class is used to read information about a directory within the file system?

The traditional old school File class can be used to both read information about directories and files as well. It
can list directories in a path as well as files and both at the same time.

A. `java.io.File`
B. java.io.Directories
C. java.io.Directory
D. java.io.Path

31. Which of the following is a high-level stream class that can only be used to wrap a low-level stream?

The high level streams are these that only wrap existing low level streams or writers the low level ones are such
that they are constructed only with a given source - files, strings, byte or char arrays, etc. The one that is a bit
misleading here is the PrintWriter which is actually constructed from both high level streams and low level
components such as file - which makes it a bit of both

A. FileOutputStream - constructed from a file
B. FileReader - constructed from a file
C. `ObjectInputStream` - wraps around another input stream
D. PrintWriter - constructed form writers and file etc

32. Assume the file prime6.txt exists and contains the first six prime numbers as bytes: 2, 3, 5, 7, 11, 13. What is the output of the following application?

```java
package numbers;
import java.io.\*;
public class PrimeReader {
    public static void main(String[] real) throws Exception {
        try (InputStream is = new FileInputStream("prime6.txt")) {
            is.skip(1);
            is.read();
            is.skip(1);
            is.read();
            is.mark(4);
            is.skip(1);
            is.reset();
            System.out.print(is.read());
        }
    }
}
```

TODO:

A. 11
B. 13
C. The code does not compile.
D. The code compiles but throws an exception at runtime.

33. Fill in the blanks: For a given file, the absolute is the path from the \***\*\_\_\*\***to
    the file, while the relative path is the path from the \***\*\_\_\*\***to the file.

A. current directory, current working directory
B. parent directory, temporary directory
C. `root directory, current working directory`
D. root directory, parent directory

34. Which statement best describes the following two methods?

```java
public void writeSecret1() throws IOException {
    final Writer w = new BufferedWriter(new FileWriter("dont.open"));
    w.write("Secret passcode");
    w.close();
}
public void writeSecret2() throws IOException {
    try(final Writer w = new BufferedWriter(new FileWriter("dont.open"))) {
        w.write("Secret passcode");
    }
}
```

Both methods really should compile but the one that is surrounded with try catch will correctly free the resources
allocated for this writer, the other will not and if the write methods throw the close will never be called.

A. Both methods compile and are equivalent to each other.
B. Neither method compiles.
C. Only one of the methods compiles.
D. `The methods compile, but one method may lead to a resource leak.`

35. What is the result of compiling and executing the following program?

```java
import java.io.*;
import java.util.*;
public class Itinerary {
    private List<String> activities = new ArrayList<>();

    private static Itinerary getItinerary(String name) {
        return null;
    }

    public static void printItinerary() throws Exception {
        Console c = new Console();
        final String name = c.readLine("What is your name?");
        final Itinerary stuff = getItinerary(name);
        stuff.activities.forEach(s -> c.printf(s));
    }

    public static void main(String[] holidays) throws Exception {
        printItinerary();
    }
}
```

The console has has NO exposed public constructor, it is meant to be used only in the context of System.console()
call, that is because the console class needs valid handles to attach to - stdout and stdin. Which is what the
system class provides to it the very first time it is constructed. On top of that the console instance produced by
the System class is a singleton.

A. `The code does not compile.`
B. The code compiles and prints a NullPointerException at runtime.
C. The code compiles but does not print anything at runtime.
D. None of the above

36. Let’s say we want to write an instance of Cereal to disk, having a name value of CornLoops. What is the value of
    name after this object has been read using the ObjectInputStream’s readObject() method?

```java
public class Cereal {
    private String name = "CocoaCookies";
    private transient int sugar;

    {
        name = "SugarPops";
    }

    public Cereal() {
        super();
        this.name = "CaptainPebbles";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }
}
```

If the file contains that value, that is what the object instance will contain, that is because neither the constructor
or the initialization blocks will be run for the class that is being de-serialized.

A. CaptainPebbles
B. `CornLoops`
C. SugarPops
D. None of the above

37. Which statement best describes the difference between a Writer and an OutputStream class?

Both can write text or character data to a file, the issue here is that only one of them has an API that actually
directly allows you to that, otherwise the OutputStream can be used to do this only when we convert the data to actually
be represented as byte data

A. Only one of them can write text or character data.
B. `Only one of them has built-in methods for writing character data.`
C. Only one of them has a flush() method to force the data to be written out.
D. One uses a byte array to process character data more efficiently.

38. What is the output of the following application? It is safe to assume the directories referenced in the class do not
    exist prior to the execution of the program and that the file system is available and able to be written.

```java
import java.io.\*;
public class Resume {
    public void resetWorkingDirectory() throws Exception {
        File f1 = new File("/templates/proofs");
        f1.mkdirs();
        File f2 = new File("/templates");
        f2.mkdir(); // k1
        new File(f2,"draft.doc").createNewFile();
        f1.delete();
        f2.delete(); // k2
    }
    public static void main(String... leads) {
        try {
            new Resume().resetWorkingDirectory();
        } catch (Exception e) {
            new RuntimeException(e);
        }
    }
}
```

The code seems to be correct and will not print out any exceptions that is because it is correctly invoking existing
methods on the File class API / interface, these methods are mkdirs and mkdir, which the first one creates a only
the parent directory for the file path and the second actually creates all the directories up until the full path
that was used in the file.

A. Line k1 does not compile or triggers an exception at runtime.
B. Line k2 does not compile or triggers an exception at runtime.
C. `The code compiles and runs without printing an exception.`
D. None of the above

39. Given the following class, three of the values ensure it runs properly on various different systems. Which value does not?

```java
package magic;
import java.io.\*;
public class Store {
    private final String directory;
    public Store(String directory) {
        this.directory = directory;
    }
    public File getDatabaseFolder(String file) {
        return new File(directory + _________ + file);
    }
}
```

Out of these 4, only the path.separator is not related to them, the path.separator is actually the separator that is
used to separate different components inside the path environment variable on the system, that is : for Unix and ;
for Windows systems

A. java.io.File.separator
B. new File(new String()).separatorChar
C. System.getProperty("file.separator")
D. `System.getProperty("path.separator")`

40. How many compilation errors does the following class contain?

```java
import java.io.\*;
public class Guitar {
    public void readMusic(File f) {
        try (BufferedReader r = new BufferedReader(FileReader(f))) {
            final String music = null;
            try {
                while((music = r.readLine()) != null) {
                    System.out.println(music);
                }
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}
```

There are three here. The first one is the way the `FileReader` constructor is called, it is missing the new keyword.
The second is the fact that the music variable is declared final, but it is attempted to be re-assigned in the while
loop, to read lines. The final one is the try-with-resources, the close method on the `BufferedReader` throws
`IOException`, but this catch here catches a more specific `FileNotFoundException` which is not a parent of
`IOException`, it is actually a child of it.

A. None
B. One
C. Two
D. `Three`

41. What is the difference between the two Console methods, format() and printf()?

A. One of them takes an optional list of arguments; the other does not.
B. One of them takes String as input; the other takes an Object.
C. `There is no difference between the two methods.`
D. Trick question! printf() is not defined in Console.

42. Let’s say you want to write a lot of text data to a file in an efficient manner. Which two java.io stream
    classes are best to use?

The file writer wrapped around the buffered writer will offer the best solution, that is because we will buffer the
writes to the file more efficiently

A. FileOutputStream and BufferedOutputStream
B. FileOutputWriter and FileBufferedWriter
C. `FileWriter and BufferedWriter`
D. ObjectOutputStream and BufferedWriter

43. Assume the file referenced in the StudentManager class exists and contains data. Which statement about the following class is correct?

```java
package school;
import java.io.\*;
class Student implements Serializable {}
public class StudentManager {
    public static void main(String[] grades) {
        try(ObjectInputStream ios = new ObjectInputStream(new FileInputStream(new File("C://students.data")))) {
            Student record;
            while((record = (Student) ios.readObject()) != null) {
                System.out.print(record);
            }
        } catch (EOFException e) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

A. The code does not compile.
B. `The code compiles but prints an exception at runtime.`
C. The program runs and prints all students in the file.
D. The program runs but may only print some students in the files.

44. Assuming the path /Earth does not exist within the file system, what is the output of the following program?package center;

```java
import java.io.\*;
public class Journey {
    public static void main(String[] dig) {
        File file = new File("/Earth");
        System.out.print(file.getParent()
            + " - "
            + file.getParent().getParent());
    }
}
```

While the get parent method exists on the file class interface, it does not return a File instance it actually
returns just a plain String, in this case the code will not compile

A. / - /
B. / - null
C. `The code does not compile.`
D. The code compiles but throws an exception at runtime.

44. Which statements about executing the following program are true?

```java
package test;
import java.io.\*;
public class Turing {
    public static void main(String... robots) {
        Console c = System.console();
        final String response = c.readLine("Are you human?");
        System.err.print(response);
    }
}
```

I. The program may ask the user a question and print the response to the error stream.
II. The program may throw a NullPointerException at runtime.
III. The program may wait indefinitely.

All of those are actually true, the program may ask the user for something and that something will be printed out to
the err stream, on top of that if the user never presses enter, the program will wait and block indefinitely. The
console object returned by the System class might be null, therefore this code can also throw null pointer exception
too.

A. I
B. I and III
C. II and III
D. `I, II, and III`

45. Which of the following statements about the deleteTree() method is correct?

```java
public void deleteTree(File f) {
    if(!f.isDirectory()) {
        f.delete();
    }
    else {
        Stream.of(f.list()).forEach(s -> deleteTree(s));
        f.deleteDirectory();
    }
}
```

The methods that exist on the File API are mostly right, there is a isDirectory that checks if the current pat
pointed at by File is a directory, it also has a method called list, that lists the paths of the current files and
directories pointed at to by the File. The deleteDirectory method does not exist there is only one delete method
that is called delete, that also throws unchecked exception as well

1. first remove the deleteDirectory method or replace with plain delete method call - f.delete()
2. change the stream line to create a stream of File objects that are passed to the deleteTree method -
   Stream.of(f.list()).map(File::new).foreAch(deleteTree);

A. It compiles and is capable of deleting a directory tree.
B. If one line were modified, it would be capable of deleting a directory tree.
C. `If two lines were modified, it would be capable of deleting a directory tree.`
D. None of the above

46. Which of the following is not a built-in stream in Java?

By default the System class exposes all of the standard streams, that would include the standard error, input and
output steams, info stream is not something that actually exists or is a non-standard thing.

A. System.err
B. System.in
C. `System.info`
D. System.out

47. Assuming the file path referenced in the following class is accessible and able to be written, what is the output of the following program?

```java
package store;
import java.io.\*;
public class Furniture {
    public final static void main(String... inventory) throws Exception {
        Writer w = new FileWriter("couch.txt");
        try (BufferedWriter bw = new BufferedWriter(w)) {
            bw.write("Blue coach on Sale!");
        } finally {
            w.flush();
            w.close();
        }
        System.out.print("Done!");
    }
}
```

This code will compile just fine but will throw exception at runtime because we are closing the writer twice, that
is not the primary issue here though. The first time it is closed is by the try-with-resources, and in the finally
block we will attempt to call flush the writer that has already been closed by the try-with-resources, which has
already closed the writer by the time we reach the finally block, therefore an exception will be thrown by the flush
method

A. Done!
B. The code does not compile for one reason.
C. The code does not compile for two reasons.
D. `The code compiles but throws an exception at runtime.`

48. Given an instance of Console c, which of the following method calls is NOT a way to read input from the user?

The reader method of console returns a reader, and the reader is an abstract top level class for all text based
readers, but it does not provide a reading method that would allow you to read an entire line, that method exists on
dedicated readers such as `BufferedReader`. The rest of

A. c.reader().read()
B. `c.reader().readLine()`
C. c.readLine()
D. c.readPassword()

49. The copyPidgin() method is used to copy the contents of one file to another. Which statement about the implementation is correct?

```java
import java.io.\*;
public class Pidgin {
    public void copyPidgin(File s, File t) throws Exception {
        try(InputStream is = new FileInputStream(s); OutputStream os = new FileOutputStream(t)) {
            byte[] data = new byte[123];
            int chirps;
            while((chirps = is.read(data))>0) {
                os.write(data);
            }
        }
    }
}
```

The method is interesting, the copy will certainly work just fine, however there is a catch, the contents of the
destination file will or can contain garbage data, because the buffer that we write to that file is of quite a big
size, so we are not checking how many bytes we have actually read therefore every time the write will write 123
bytes to the destination file, the bytes past the valid read count when calling read are zeroed out, but still those
bytes will be written as NULL to the file.

Say the very last chunk that we read is only 23 bytes big, until the end of the file, the rest 100 bytes will be
zeroed out, but still written to the output file because we have a buffer of size 123 bytes, the write method is not
told how many bytes to write out of those 123 bytes. So what it does is to write them all.

A. The class does not compile because read(byte[]) and write(byte[]) can only be called on BufferedInputStream and BufferOutputStream, respectively.
B. `The method correctly copies the contents of all files.`
C. The method correctly copies the contents of some files.
D. The method will always throw an exception at runtime because the data array size is not a power of 2.

50. Using what you know about java.io stream class names, what would a non existent class named `BufferedFileReader`
    most likely be used for?

The same thing for which `BufferedReader` is used, actually that name implies that we are going to be buffering read
calls from the file system, allowing us to read bigger chunks in more efficient manner, possibly using or utilizing
special system calls.

A. Reading a small text file from a remote network
B. Reading an image from disk
C. `Reading large text files from a file system`
D. Reading serialized data from disk

51. What is the output of the following application?

```java
import java.io.*;
public class WidgetProcessor {
    public int getWidgetNumber(byte[] data) throws Exception {
        try (InputStream is = new ByteArrayInputStream(data)) {
            is.read(new byte[2]);
            if(!is.markSupported()) {
                return -1;
            }
            is.mark(5);
            is.read();
            is.read();
            is.skip(3);
            is.reset();
            return is.read();
        }
    }
    public static void main(String... sprockets) throws Exception {
        final WidgetProcessor p = new WidgetProcessor();
        System.out.print(p.getWidgetNumber(new byte[] {1,2,3,4,5,6,7}));
    }
}
```

TODO:

A. 3
B. 5
C. 7
D. An exception is thrown at runtime.

32. Assuming the working directory is accessible, empty, and able to be written, how many
    file system objects does the following class create?

```java
import java.io.\*;
public class Bakers {
    public static void main(String... tooMany) throws IOException {
        File cake = new File("cake.txt");
        Writer pie = new FileWriter("pie.txt");
        pie.flush();
        new File("fudge.txt").mkdirs();
    }
}
```

The key here is to know a few things first, creating a File and calling its constructor will not in fact trigger any
exceptions, the constructor does not do any validation on the presence of the resource against which this File
instance is created.

Then the Writer itself, when created does not also do any validation, however once we call flush it will actually
try to write to a resource with that name, if it exists it will trample over it, if it does not exist it will
actually write to the file, effectively creating it.

The final call to mkdirs, is also valid, because the call will effectively try to create all the directories
described by the path with with the File instance was created, in this case that implies that it will create a
directory "fudge.txt"

A. `None`
B. One
C. Two
D. Three

33. Let’s say you wanted to read data from a file stored on disk that consists of String, long, and Object values?
    Given that the file is quite large, you intend to use three classes to achieve this result. Which of the following
    is not one of the three classes you should use?

The question does not specify how the data is stored, we presume it is stored as a binary format as an output of a
serialization process, if that is the case the only one here that does not make sense is the BufferedReader this one
deals with character data and our file does not have such, presumably, because the data was stored as binray data

A. BufferedInputStream
B. `BufferedReader`
C. FileInputStream
D. ObjectInputStream

34. Which statement best describes the following two methods?

```java
public String getNameQuick() throws IOException {
    final BufferedReader r = new BufferedReader(new FileReader("saved.name"));
    final String name = r.readLine();
    r.flush();
    return name;
}
public String getNameSafely() throws IOException {
    try(final BufferedReader r = new BufferedReader(new FileReader("saved.name"))) {
        final String name = r.readLine();
        r.flush();
        return name;
    }
}
```

This implementation might seem safe however the interfaces here that are used are incorrect, the Reader interface
and API does not have a flush method, it does not make sense because the data is coming into the program not going
out. The flush method exist on the Writer interface only.

A. Both methods compile and are equivalent to each other.
B. Neither method compiles.
C. Only one of the methods compiles.
D. The methods compile, but one method may lead to a resource leak.

35. What is the output of the following application? Assume the System.console() is
    available and the user enters `badxbad` and presses Enter.

```java
import java.io.\*;
public class InconvenientImplementation {
    public static void main(String... dontDoThis) throws Exception {
        Console c = System.console();
        if(c != null) {
            c.writer().write('P');
            c.writer().write('a');
            c.writer().write('s');
            c.writer().write('s');
            c.writer().flush(); // t1
            int i;
            StringBuilder sb = new StringBuilder();
            while((i = c.reader().read()) != 'x') { // t2
                c.printf(" " + (char)i);
                sb.append((char)i);
            }
            c.writer().format("Result: %s", sb.toString()); // t3
        }
    }
}
```

This implementation is correct, it will append a few characters to the output console so the user actually sees the
output "pass" then it will start reading user input, char by char, until the user entered the letter 'x' after which
the while loop exists and the word "bad" will be printed, because the user entered "bad|xbad", stopping the program
on the first x in the user input.

What is more interesting here is how the read method works, the very first call will start buffering data, meaning
that until the next new line character the while loop will actually block on the first call to read, then every
subsequent call will actually read from the internal buffer, and not block for user input because it has already
buffered the user input until the next new line, so even though we enter the full line `badxbad`, we will read character
by character until the first X in the user input, then the while loop will terminate and by that time the string builder
will contain the word "bad".

A. `Result: bad`
B. Line t1 does not compile or triggers an exception at runtime.
C. Line t2 does not compile or triggers an exception at runtime.
D. None of the above

36. Why does Console readPassword() return a char array rather than a String?

The main reason is two fold really. The primary reason is indeed security we can reset and fill the array with
garbage immediately after we read the password, the second reason is more obscure, we really don't care about the
contents, or in other words we do not wish to display them in a human readable way obviously, so we don't actually
need the password as a UTF-8 encoded String instance, which is hard to clear out of memory and generally completely
useless to us.

A. It improves performance.
B. `It improves security.`
C. Passwords must be stored as a char array.
D. String cannot hold the individual password characters.

37. Which statement about the following program is true?

```java
import java.io.\*;
public class Unicorn {
    public void findUnicorns() {
        try(InputStream o = new ObjectInputStream(readBook())) {
            while(o.read() != -1) {
                System.out.println(o.read());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
    private InputStream readBook() throws IOException {
        return new BufferedInputStream(new FileReader("magic.book"));
    }
    public static void main(String... horn) {
        new Unicorn().findUnicorns();
    }
}
```

The method buffered input stream can not be constructed by a file reader. Those classes are not compatible with each
other we can however use a bridge interface to bridge the gap between them by using -

A. `The code does not compile.`
B. The program prints every byte in the file without throwing an exception.
C. The program prints every other byte in the file without throwing an exception.
D. The program throws an EOFException when the end of the file is reached.

38. Choose the class that is least likely to be marked Serializable.
    A. A class that holds data about the amount of rain that has fallen in a given year
    B. A class that manages the memory of running processes in an application
    C. A class that stores information about apples in an orchard
    D. A class that tracks the amount of candy in a gumball machine
39. What is the output of the following application?
    package cell;
    import java.io.\*;
    public class TextMessage {
    public String receiveText() throws Exception {
    try (Reader r = new FileReader("messages.txt")) {
    StringBuilder s = new StringBuilder();
    int c;
    while((c = r.read()) != -1) {
    s.append((char)c);
    if(r.markSupported()) {
    r.mark(100);
    r.skip(10);
    r.reset();
    }
    }
    return s.toString();
    }
    }
    public void sendText(String message) throws Exception {
    try (Writer w = new FileWriter("messages.txt")) {
    for(int i=0; i<message.length(); i++) {
    w.write(message.charAt(i));
    w.skip(1);
    }
    }
    }public static void main(String[] minutes) throws Exception {
    final TextMessage m = new TextMessage();
    m.sendText("You up?");
    System.out.println(m.receiveText());
    } }
    A. You up?
    B. Y o u u p ?
    C. The code does not compile because of the receiveText() method.
    D. The code does not compile because of the sendText() method.
40. What is the output of the following program? Assume the file paths referenced in the
    class exist and are able to be written to and read from.
    package heart;
    import java.io.\*;
    public class Valve implements Serializable {
    private int chambers = -1;
    private transient Double size = null;
    private static String color;
    public Valve() {
    this.chambers = 3;
    color = "BLUE";
    }
    public static void main(String[] love) throws Throwable {
    try (ObjectOutputStream o = new ObjectOutputStream(
    new FileOutputStream("scan.txt"))) {
    final Valve v = new Valve();
    v.chambers = 2;
    v.size = 10.0;
    v.color = "RED";
    o.writeObject(v);
    }
    new Valve();
    try (ObjectInputStream o = new ObjectInputStream(
    new FileInputStream("scan.txt"))) {
    Valve v = (Valve)o.readObject();
    System.out.print(v.chambers+","+v.size+","+v.color);
    }
    }
    { chambers = 4; }
    }
    A. 2,null,RED
    B. 2,null,BLUE
    C. 3,10.0,RED
    D. The code does not compile.
