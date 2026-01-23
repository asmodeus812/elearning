## StringBuilder

A mutable string sequence that can be used to construct an arbitrary string sequence at runtime, while maintaining
relative performance, it is not synchronized, unlike its counter part `StringBuffer`.

### Properties

- byte[] value;
- not synchronized
- mutable sequence
- fluent interface
- Unicode and code point compatible
- NO `equals and hashCode` overrides

### Implementations

```java
public final class StringBuilder
    extends AbstractStringBuilder
    implements java.io.Serializable, Comparable<StringBuilder>, CharSequence {}
```

### Interface

1. `insert({start}, {type})` - a generic overloaded method that allows one to insert content at a given position in the
   final string, the caveat there is that offset can range from [0, builder.length] that allows one to insert at
   the end of the string by providing `builder.length` as the argument to insert method. Any other index offset will
   throw an `IndexOutOfBoundsException`.
2. `delete({start}, {end})` - deletes a range of the builder, what is interesting to note is that the end can be any
   value as long as it is greater or equal to start, however the start index has to be within the range of [0,
   lenght - 1], if end index is greater than the length of the sequence then content is deleted to the end of the valid
   sequence. The end index is clamped to the total length of the builder
3. `append({data})` - always appends at the end of the string builder's content that has been constructed thus far,
   just like insert it is overloaded to accept most primitive types along side the primary overload for appending
   a `CharSequence`
4. `replace({start}, {end}, {content})` - the end index is exclusive but it can also be outside the range of the
   String builder as long as it is greater than the start index, the start index is inclusive and must not exceed
   the builder.length - 1. If end index is provided to be greater than the total length of the builder, then the
   content is replaced to the end of the builder (the end index is clamped to the size of the builder in other words)
5. `substring/subSequence({start}, {end})` - stat index is inclusive must always be less than the end index, the end
   index is exclusive, we can call them with the length of the sequence/string such as - substring/Sequence(0,
   string.length()). End index must be valid otherwise it will throw exception (unlike replace/delete)

6. `equals(Object o)` - is NOT overridden for the string builder, meaning that it only compares by reference not the
   actual contents, that should be used with extra care, people might trip expecting this to compare contents meaning
   that `StringBuilder s1 = new StringBuilder("a"); StringBuilder s2 = new StringBuilder("a"); s1.equals(s2) === false`
7. `setLength` - actually pads the string instead of reducing the capacity of the internal buffer, meaning that
   capacity and length will differ.

`All methods that take start, end index require that the start is greater than the end, all of them imply that end
index is exclusive, and most methods in StringBuilder that take end index throw exception, there are few that simply
clamp the end index back to the length of the array but these are the exception not the rule - delete, replace being
some of them`

## String

Immutable character sequence, methods in the String class that appear to mutate the source actually produce a copy
of the original string that is returned to the user. Automatically interned by the compiler when initialized with
string literals

## Properties

- not synchronized
- immutable sequence
- final byte[] value
- Unicode and code point compatible
- 'mutation' methods produce new instance/copy

## Implementations

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {}
```

### Interface

0. `String.valueOf(Object o)` - the primary entry point for safe stringification of an object in the java language,
   which will produce "null" if the object is null and not try to call `toString` on a null reference exploding the
   program
1. `concat({data})` - create a NEW string with the original content and the new concatenated at the end of the
   original
2. `replace({string}, {string})` - replaces a target string matching exactly in the string with another one, this is NOT
   regex
3. `replaceAll({regex}, {string})` - trap replace all does not work as replace does and the first argument is not a plain
   string but a regex, meaning that both methods work quite differently
4. `split({regex})` - just like replaceAll the split takes in a regular expression and splits on every match in the string
5. `substring/subSequence({start}, {end})` - stat index is inclusive must always be less than the end index, the end
   index is exclusive, we can call them with the length of the sequence/string such as - substring/Sequence(0,
   string.length()). End index must be valid otherwise it will throw exception

`All methods that take start, end index require that the start is greater than the end, all of them imply that end
index is exclusive, and most methods in String that take end index throw exception`

## Caveats

- `String.valueOf(Object o)` - is a safe way to obtain a string representation of an object, the method tries to first
  verify that the object is valid and then calls to string on it, this is what the implementation does, internally check
  the target first before de-referencing and calling the method `(obj == null) ? "null" : obj.toString()`
- `StringBuilder equals` - not overridden does compare ONLY by reference, the default Object equals implementation.
- `String s = null` - string concatenation is treated a bit specially it is possible to pass around string
  references that is null, and concatenate without exploding the program, so "s" variable when concatenated with
  another string - `String k = s + "a"; k === nulla`
- `String v = "a"` - every string literal declared in the program is interned, and string literal concatenation is
  computed and interned at compile time - `String newVar = "a" + "b"`, this inters a string "ab". Variable string
  concatenation happens at runtime only - `String newVar = "a" + x;`
- `new String(...)` - every time a string variable is constructed via a constructor unless intern method is called
  that string will never be interned, and every time it produces a new string object instance, regardless of the
  content
- `String.intern()` - method that exists on the String class that allows you to forcibly intern a string, the method
  returns a new string instance, that is going to be interned, if the pool already contains that string, that
  reference is returned instead. `s.intern() == t.intern()`
- `== on string references` - when combined with interned strings this can trip people, as it will return true, even
  if they seem unrelated, look at how the variable is declared, using the String constructor or now.
- `System.out.print StringBuilder` - it is possible to pass a string builder reference to print because the
  StringBuilder correctly overrides the toString method therefore it will actually print the string contents of the
  builder.
- `replace vs replaceAll` - both replace contents in the source string and produce a new string instance, both work
  differently - replaceAll takes a regular expression as the first argument, replace does take a raw string that is
  matched with basic equals/string windowing search rules in the source string.
- `trim vs strip` - both methods aim to remove white space from a string, trim is the old API method that does a
  limited Unicode white space removal, the strip is a more modern (present in more recent versions of the language,
  present in versions starting from Java 11) alternative that removes a broader range of white space Unicode
  code-points
