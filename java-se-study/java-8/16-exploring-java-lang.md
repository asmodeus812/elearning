# Java.lang

The core library which exposes most of the basic language features, classes and functionalities

## Runtime

The `Runtime` class encapsulates the run-time environment. You cannot instantiate a `Runtime` object. However, you can
get a reference to the current `Runtime` object by calling the static method Runtime.getRuntime( ). Once you obtain a
reference to the current `Runtime` object, you can call several methods that control the state and behavior of the Java
Virtual Machine. Applets and other `untrusted` code typically cannot call any of the `Runtime` methods

| Method                                            | Description                                                                                                    |
| ------------------------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| Process exec(String progName)                     | Executes the program specified by progName as a separate process                                               |
| Process exec(String progName, String environment) | Executes the program specified by progName as a separate process with the environment specified by environment |
| Process exec(String comLineArray)                 | Executes the command line specified by the strings in comLineArray as a separate process                       |

## Codepoints

Relatively recently, major additions were made to Character. Beginning with `JDK 5`, the Character class has included
support for `32-bit` Unicode characters. In the past, all Unicode characters could be held by 16 bits, which is the size
of a char (and the size of the value encapsulated within a Character), because those values ranged from 0 to `FFFF`.
However, the Unicode character set has been expanded, and more than 16 bits are required. Characters can now range from
0 to `10FFFF`. Here are three important terms. A code point is a character in the range 0 to `10FFFF`. Characters that
have values greater than `FFFF` are called supplemental characters. The basic multilingual plane (`BMP`) are those
characters between 0 and `FFFF`. The expansion of the Unicode character set caused a fundamental problem for Java.
Because a supplemental character has a value greater than a char can hold, some means of handling the supplemental
characters was needed. Java addressed this problem in two ways. First, Java uses two chars to represent a supplemental
character. The first char is called the high surrogate, and the second is called the low surrogate. New methods, such as
`codePointAt` , were provided to translate between code points and supplemental characters.

## Object

The clone() method generates a duplicate copy of the object on which it is called. Only classes that implement the
`Cloneable` interface can be cloned. The `Cloneable` interface defines no members. It is used to indicate that a class
allows a `bitwise` copy of an object (that is, a clone) to be made. If you try to call clone( ) on a class that does not
implement `Cloneable`, a `CloneNotSupportedException` is thrown. When a clone is made, the constructor for the object being
cloned is not called. As implemented by Object, a clone is simply an exact copy of the original. Some other notable
methods exposed by the object class type are

| Method                            | Description                                                                              |
| --------------------------------- | ---------------------------------------------------------------------------------------- |
| Object clone( ) throws            | CloneNotSupportedException Creates a new object that is the same as the invoking object. |
| boolean equals(Object object)     | Returns true if the invoking object is equivalent to object.                             |
| void finalize( ) throws Throwable | Default finalize( ) method. It is called before an unused object is recycled.            |
| final Class<?> getClass( )        | Obtains a Class object that describes the invoking object.                               |
| int hashCode( )                   | Returns the hash code associated with the invoking object.                               |
| final void notify( )              | Resumes execution of a thread waiting on the invoking object.                            |
| final void notifyAll( )           | Resumes execution of all threads waiting on the invoking object.                         |
| String toString( )                | Returns a string that describes the object.                                              |

## Class

Class encapsulates the run-time state of a class or interface. Objects of type Class are created automatically, when
classes are loaded. You cannot explicitly declare a Class object. Generally, you obtain a Class object by calling the
`getClass()` method defined by Object.

1.  `Method[ ] getDeclaredMethods( ) throws SecurityException` - Obtains a Method object for each method declared by the
    class or interface represented by the invoking object and stores them in an array. Returns a reference to this array.
    (Inherited methods are ignored.)

2.  `Field getField(String fieldName) throws NoSuchMethodException, SecurityException` - Returns a Field object that
    represents the public field specified by fieldName for the class or interface represented by the invoking object.

3.  `Field[ ] getFields( ) throws SecurityException` - Obtains a Field object for each public field of the class or
    interface represented by the invoking object and stores them in an array. Returns a reference to this array.

4.  `Class<?>[ ] getInterfaces( )` - When invoked on an object that represents a class, this method returns an array of the
    interfaces implemented by that class. When invoked on an object that represents an interface, this method returns an
    array of interfaces extended by that interface.

5.  `Method getMethod(String methName, Class<?> ... paramTypes) throws NoSuchMethodException, SecurityException` - Returns
    a Method object that represents the public method specified by methName and having the parameter types specified by
    paramTypes in the class or interface represented by the invoking object.

6.  `Method[ ] getMethods( ) throws SecurityException` - Obtains a Method object for each public method of the class or
    interface represented by the invoking object and stores them in an array. Returns a reference to this array.

7.  `String getName( )` - Returns the complete name of the class or interface of the type represented by the invoking
    object. ProtectionDomain getProtectionDomain( ) Returns the protection domain associated with the invoking object.

8.  `Class<? super T> getSuperclass( )` - Returns the superclass of the type represented by the invoking object. The return
    value is null if the represented type is Object or not a class.

9.  `boolean isInterface( )` - Returns true if the type represented by the invoking object is an interface. Otherwise, it
    returns false.

10.  `T newInstance( ) throws IllegalAccessException, InstantiationException` - Creates a new instance (i.e., a new object)
    that is of the same type as that represented by invoking object. This is equivalent to using new with the class’
    default constructor. The new object is returned. This method will fail if the represented type is abstract, not a class,
    or does not have a default constructor.

11.  `String toString( )` - Returns the string representation of the type represented by the invoking object or interface.
    throws SecurityException

12.  `Class<?>[ ] getClasses( )` - Returns a Class object for each public class and interface that is a member of the class
    represented by the invoking object.
13.  `ClassLoader getClassLoader( )` - Returns the ClassLoader object that loaded the class or interface.

14.  `Constructor<T> getConstructor(Class<?> ... paramTypes) throws NoSuchMethodException, SecurityException` - Returns a
    Constructor object that represents the constructor for the class represented by the invoking object that has the
    parameter types specified by paramTypes.

15.  `Constructor<?>[ ] getConstructors( ) throws SecurityException` - Obtains a Constructor object for each public
    constructor of the class represented by the invoking object and stores them in an array. Returns a reference to this
    array.

`The abstract class ClassLoader defines how classes are loaded. Your application can create subclasses that extend
ClassLoader, implementing its methods. Doing so allows you to load classes in some way other than the way they are
normally loaded by the Java run-time system. However, this is not something that you will normally need to do.`

##


