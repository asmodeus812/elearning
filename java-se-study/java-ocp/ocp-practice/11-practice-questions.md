1. Fill in the blanks: A(n)__________ is a file that contains a reference to another file or directory, while
   a(n)__________ is a file that contains content.

A symbolic link is a link to another file that is contained on the file system, it does not exist by itself, and is only
a file system construct, the actual files or directories these links points to are the actual resources or file system
objects that contain the data or represent that data.

A. irregular file, regular file
B. regular file, opaque file
C. `symbolic link, regular file`
D. symbolic link, symbolic directory

2. Which methods listed below are found in the NIO.2 Path interface?

```
I. getRoot()
II. isDirectory()
III. listFiles()
IV. toRealPath()
```

Due to the nature of NIO.2 some of the methods are actually duplicated between Path and the old school File class.
But you have to keep in mind that these classes are different things. Even though most of the File operations are
really good fit for being replaced by Path.

A. I only
B. I, II, and III
C. `I and IV`
D. II and III

3. Assuming the file /secret/hide.txt exists and is marked hidden, what is result of
executing the following program?
package hidden;
import java.nio.file.*;
public class Finder {
public void findHiddenFile(Path p) throws Exception {
if(File.isHidden(p)) {
System.out.print("Found!");
}
}
public static void main(String[] folders) throws Exception {
final Finder f = new Finder();
f.findHiddenFile(Paths.get("/secret/hide.txt"));
}
}
A. The class does not compile.
B. An exception is printed at runtime.
C. Found! is printed at runtime.
D. Nothing is printed at runtime.
4. Fill in the blanks: Files.walk() performs a __________ traversal, while
Files.find() performs a __________ traversal.
A. breadth-first, breadth-firstB. breadth-first, depth-first
C. depth-first, breadth-first
D. depth-first, depth-first
5. When reading file information, what is an advantage of using an NIO.2 attribute
interface rather than reading the values individually from Files methods?
A. Costs fewer round-trips to the file system
B. Guarantees performance improvement
C. Has support for symbolic links
D. Reduces memory leaks
6. What is the result of compiling and executing the following program? Assume the
current directory is /stock and the path /stock/sneakers does not exist prior to
execution.
package shoe;
import java.io.*;
import java.nio.file.*;
public class Sneaker {
public void setupInventory(Path desiredPath) throws Exception {
Path suggestedPath = Paths.get("sneakers");
if(Files.isSameFile(suggestedPath, desiredPath) // j1
&& !Files.exists(suggestedPath))
Files.createDirectories(desiredPath); // j2
}
public static void main(String[] socks) throws Exception {
Path w = new File("/stock/sneakers").toPath(); // j3
new Sneaker().setupInventory(w);
}
}
A. The directory /stock/sneakers is created.
B. Line j1 does not compile or produces an exception at runtime.
C. Line j2 does not compile or produces an exception at runtime.
D. Line j3 does not compile or produces an exception at runtime.
7. Assuming the path referenced below exists and contains a symbolic link that
references /again, what is the expected result of executing the following code snippet?
System.out.print(Files.walk(Paths.get("/again/and/again")).count());
A. An exception is thrown at runtime.
B. A number is printed at runtime.
C. The process hangs indefinitely.
D. The result cannot be determined with the information given.8. Which method in the NIO.2 Files class is equivalent to the java.io.File method
length()?
A. length()
B. size()
C. getLength()
D. None of the above
9. Assuming the current working directory is /home, then what is the output of the
following program?
1: package magic;
2: import java.nio.file.*;
3: public class Magician {
4:
public String doTrick(Path path) {
5:
return path.subpath(2,3)
6:
.getName(1)
7:
.toAbsolutePath()
8:
.toString();
9:
}
10:
public static void main(String... cards) {
11:
final Magician m = new Magician();
12:
System.out.print(m.doTrick(
13:
Paths.get("/bag/of/tricks/.././disappear.txt")));
14:
} }
A. /home/tricks
B. /home
C. The code does not compile.
D. The code compiles but prints an exception at runtime.
10. Which methods listed below are found in the NIO.2 Files class?
I. isSameFile()
II. length()
III. relativize()
IV. mkdir()
A. I only
B. I, II, and IV
C. II and III
D. IV only
11. The following code snippet, which attempts to move a file system record from
oldHardDrivePath to newHardDrivePath, results in an exception at runtime. Which of
the following is the most likely type of exception to be thrown?Files.move(oldHardDrivePath,newHardDrivePath,REPLACE_EXISTING);
A. AtomicMoveNotSupportedException
B. DirectoryNotEmptyException
C. FileAlreadyExistsException
D. None of the above since the line of code does not compile
12. Which of the following can be filled into the blank that would allow the method to
compile?
public String getPathName(String fileName) {
final Path p = ____________________;
return p.getFileName();
}
I. new File(fileName).toPath()
II. new Path(fileName)
III. FileSystems.getDefault().getPath(fileName)
A. I and II
B. I and III
C. II
D. None of the above
13. Which statement about the following class is correct?
package clone;
import java.io.*;
import java.nio.file.*;
public class Rewriter {
public static void copy(Path source, Path target) throws Exception {
try (BufferedReader r = Files.newBufferedReader(source);
Writer w = Files.newBufferedWriter(target)) {
String temp = null;
while((temp = r.readLine()) != null) {
w.write(temp);
}
}
}
public static void main(String[] tooMany) throws Throwable {
Rewriter.copy(Paths.get("/original.txt"),
FileSystems.getDefault().getPath("/","unoriginal.txt"));
}
}
A. The class compiles without issue.
B. The class never throws an exception at runtime.
C. The implementation correctly copies a regular file.D. All of the above
14. Fill in the blanks: The Files.__________ method returns a List, while the
Files.__________ method returns a Stream.
A. lines(), readAllLines()
B. lines(), readLines()
C. readAllLines(), lines()
D. readLines(), lines()
15. What is the output of the following application?
1: package yellow;
2: import java.nio.file.*;
3: public class Road {
4:
public boolean findHome() {
5:
Path oftenTraveled = Paths.get("/highway/street/spot.txt");
6:
Path lessTraveled = Paths.get("/highway/street/house/../.");
7:
lessTraveled.resolve("spot.txt");
8:
return oftenTraveled.equals(lessTraveled.normalize());
9:
}
10:
public static void main(String... emerald) {
11:
System.out.print("AM I HOME? "
12:
+(new Road().findHome() ? "yes" : " no"));
13:
}
14: }
A. AM I HOME? no
B. AM I HOME? yes
C. The class does not compile.
D. The class compiles but throws an exception at runtime.
16. Which of the following is not an advantage of using an NIO.2 Path instead of a
java.io.File to work with files?
A. Contains built-in support for symbolic links
B. Has ability to read operating-system-specific attributes
C. Provides a single method for deleting a directory tree
D. Provides efficient access of file metadata
17. What is the result of executing the following program? Assume the path /driveway
exists and is non-empty, and the directory tree is fully accessible within the file
system.
package weather;
import java.io.*;
import java.nio.file.*;
public class Snow {
public static boolean removeSnow(Path flake) throws IOException {if(!Files.isDirectory(flake) && !Files.isSymbolicLink(flake))
return Files.delete(flake);
else return true;
}
public static void main(String[] cones) throws IOException {
File driveway = new File("/driveway");
for(File f : driveway.listFiles()) {
System.out.println(removeSnow(f.toPath()));
}
}
}
A. The program prints a list of only true values.
B. The program prints a mix of true and false values.
C. The code does not compile.
D. The code compiles but prints an exception at runtime.
18. Which interface name inserted into the blank below allows the code snippet to
compile?
Path file = Paths.get("/data/movie.txt");
BasicFileAttributes b = Files.readAttributes(file, __________);
A. BasicFileAttributes.class
B. DosFileAttributes.class
C. PosixFileAttributes.class
D. All of the above
19. What is the output of the following code snippet? Assume that the current directory is
the root path.
Path p1 = Paths.get("./locks");
Path p2 = Paths.get("/found/red.zip");
System.out.println(p1.relativize(p2));
System.out.println(p2.relativize(p1));
A. ../found/red.zip
../../locks
B. ../../locks
../found/red.zip
C. locks/../found/red.zip
../found/locks
D. None of the above
20. What is the output of the following code snippet? Assume that the current directory is
the root path.Path p1 = Paths.get("./found/../keys");
Path p2 = Paths.get("/lost/blue.txt");
System.out.println(p1.resolve(p2));
System.out.println(p2.resolve(p1));
A. /lost/blue.txt
./found/../keys
B. /found/../keys/./lost/blue.txt
/lost/blue.txt/keys
C. /lost/blue.txt
/lost/blue.txt/./found/../keys
D. None of the above
