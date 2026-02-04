## Reader/Writer

The core interface in java that deals with reading and writing String based content to streams and buffers, the Readers
and Writers are tightly related to and aware of Unicode encoding.

### Properties

- char is 2 bytes wide
- reads/writes character streams
- works with Unicode encoded characters
- implements read/write methods, returns integer

### Interface

- `int read(buf, off, len)` - the primary read method for the Reader needs to return integer, which fits both invalid
  return result (negative value) and a valid char result, that is at most 2 bytes, by default it deals with Unicode
  encoded char streams
- `void write(buf, off, len)` - the primary write method for the Writer interface, no explicit return failure throws
  exception if IO error occurs, writes the target buffer to the output stream, deals with a buffer of characters (2byte
  wide)
- `read(CharBuffer)` - reads into a NIO char buffer, the contents from the Reader stream, that is a core Reader
  method and part of the interface
- `append(char)` - append a single character to a stream of characters, that is a core Writer method, and part of the interface
- `append(CharSequence)` - append a character sequence to a stream Writer, that is a core Writer method, and part of the interface

### Implementations

Most implementations follow a simple rule, there are two types of streams, leaves or also called nodes and
decorators or also called wrappers, the leaves streams are these that require actual source to be constructed and
read/write From and To. The decorator reader/writer implementation wrap around existing reader and writer
implementations and override the read or write behavior in some sort. These streams are as follows:

#### Nodes/Leaves

- `FileReader/Writer` - requires a file handle to read from and a file handle to write to.
- `PipedReader/Writer` - requires a source that is a pipe to read from, and a pipe to write to.
- `StringReader/Writer` - requires a source string to read from, writes to a generic instance of a `StringBuffer`
  (note that is indeed a string buffer not a builder)

#### Decorator/Wrapper

- `FilteWriter/Reader` - generic abstract class that exposes writer/reader constructors
- `BufferedReader/Writer` - wraps around another reader or writer to buffer reads/writes
- `PrintWriter` - wraps around another writer to print formatted output to it
- `LineNumberReader` - wraps around reader and just keeps track of line numbers
- `PushbackReader` - wraps around a reader allowing read characters to be pushed back

`Remember that these are the two types of reader and writers, either it is a reader/writer that requires an input or
output source from where to read or write data to, or a reader or writer is just a decorator around another reader
or writer that enhances or modifies the behavior of that stream in some way. The generic FilterWriter/Reader
abstract classes are meant for clients/users to implement to achieve this behavior in case cusotm wrapper classes
are needed`

## InputStream/OutputStream

These are byte aware streams unlike their counter parts the Writer and Reader the Input and Output streams are only
concerned with working and processing byte data, that implies 8 bit chunks can be read or written to a target resource.
Just like the Writer and Reader interfaces the Stream interfaces also provide means of working with single or multiples
of the chunks they operate on like read() to read one single byte or read an array of bytes all at once in one shot.

### Properties

- byte is 8 bits wide
- reads/writes pure byte arrays/streams
- implements read/write methods, returns integer
- does not interpret any type of Unicode encoding

### Interface

- `int read(buf, off, len)` - the primary read method for the `InputStream` needs to return integer, which fits both invalid
  return result (negative value) and a valid byte result, that is at most 1 byte
- `void write(buf, off, len)` - the primary write method for the `OutputStream` interface, no explicit return failure
  throws exception if IO error occurs, writes the target buffer to the output stream, deals with a buffer of bytes
  method and part of the interface

`The return statement of these methods all either return -1 or the number of bytes that were successfully written or
read, just like they do for the Writer and Reader`

### Implementations

Just like the reader and writer, the input and output stream implementations also implement similar rules. There are two
types of input and output streams, ones that act as leaves also called nodes - these implementations are meant to
receive a source of data to read or write to, and decorator or wrapper implementations that are meant to only proxy wrap
and override calls to underlying input/output streams, modifying the behavior in some sort. The
`FilterInput/OutputStream` exist in similar fashion to the filter writer/reader to provide abstract classes that custom
user implementations can plug into.

#### Leaves/Nodes

- `FileInput/OutputStream` - requires a file handle to read from and a file handle to write to.
- `PipeInput/OutputStream` - requires a source that is a pipe to read from and a pipe to write to.
- `ByteArrayInput/OutputStream` - requires a byte array to read from, and a byte array to write to.

#### Decorators/Wrappers

- `FilterInput/OutputStream` - generic abstract class that exposes input/output stream constructors
- `BufferedInput/OutputStream` - buffers data reads and writes from and to an input or output stream
- `DataInput/OutputStream` - reads and writes structured java primitives to an input or output stream.
- `ObjectInput/OutputStream` - reads and writes structured java objects to an input or output stream
- `PushbackInputStream` - allows bytes read from an input stream to be pushed back for re-reads

`Remember that just like the reader and writer there are two primary types of byte streams, that require an input or
output source, and such that wrap and decorate the behavior of other streams, by modifying it in some way. The
generic FilterInput/OutputStream abstract class exists to allow users to create custom wrapping classes that take in
input or output streams as constructor arguments to alter existing behavior`

## Bridges

In the old input/output package there are a couple of classes that serve as bridge between the traditional Reader/Writer
interfaces and the Input/Output Stream. These allow us to convert between the different types of resource streams, char
or byte based

- `OutputStreamWriter` - is the writer bridge class that is constructed from from an `OutputStream`
- `InputStreamReader` - is the reader bridge class that is constructed from an `InputStream`

`The inverse bridges do not exist as classes why ? Well the primary reason is that we can not just as easily convert
form a higher order data like characters to a byte, we would have to split/decode the character into multiple bytes
which is not easy to do , remember that a char is of size 2 bytes. However since we also have to deal with Unicode
characters and the fact that one character such as emoji can actually span up to 4 bytes. You can make a partial but not
a true complete bridge type that bridges these types in reverse`

To dig deeper why there are not any classes that can do the bridging conversion in reverse remember that when a text is
read by java, it is interpreted under certain encoding rules, and normalized to UTF-16 because that is what java uses by
default, meaning that once the content is re-encoded under UTF-16 the original bytes are gone, the new content while
still representing the same semantic text / meaning is comprised of completely different bytes, that were not in the
original, therefore we can not really restore / re-construct the original without knowing what encoding the original
content was in, there are other limitations too.

## Serialization

- The first super class', no argument constructor in the hierarchy that is not `Serializable` is run
- Every class needs to implement `Serializeable` interface in order to be serialized,
- Static fields are never serialized they are tied to class types not instances
- Constructors and initialization blocks are not run for `Serializable` classes
- Transient marked fields are never serialized for a given class instance
- Extending from a serializable class also inherits the serializable interface
- private readObject/writeObject methods that can customize the object reading logic

`It is important to note that we can serialize more than one object into a file, on top of that we can also write more
than one object instance of different type to the file. Also we can read all of them in the same order we have read
them, by using a combination of while(true) loop, readObject wrapped around with catch for EOFException, that will
ensure that we read all the instances contained in the file.`

## File

### Properties

- path - the abstract path name that is normalized, using default name separator character
- status - flag that indicates if the path is invalid.

### Interface

- `list()` - list both files and directories contained by the path described for this file object, note that this method actually returns String[], not File[]
- `listFiles()` - list only the files contained in the path that is described by this file object, note that this
  returns File[] and the array contains paths that describe files.
- `delete()` - deletes a file, may throw an exception if the file is not accessible, or permissions are invalid, or
  otherwise the operating system prevents the JVM from deleting the file.
- `renameTo(File)` - renamed the current file described by this file object to another file object that describes a
  new path, effectively can be also used to move files too.
- `createNewFile()` - creates a file that is denoted by this file path, note that this will throw if the file already
  exists, or the location is not accessible/writeable
- `mkdir/mkdirs()` - creates all the non existent parent directories described by this path, this is the equivalent of
  the mkdir and mkdir -p in UNIX, either create the current directory described by path, or all parent directories
  that are along the path and do not already exist.

## Console

The console class is a singleton class created the very first time we call System.console(), it attaches to the
stdin/out of the `tty`

- `writer()` - obtain direct handle to the internal writer instance, this is helpful if we want more control on how
  we output to stdout, since we have direct reference tot he Writer API and instance.
- `printf(...)` - the primary print method used to print from the console class internally it is using the Formatter which
- `format(...)` - the same signature as `printf` as `printf` actually calls this method to do the printing, the
  `printf` is alias for this method gets passed the out handle Writer instance of stdout, and directly writes to it.
- `readLine()` - reads a line from the console, as a String, this is important because this implies that this will be
  UTF encoding aware
- `readPassword()` - reads a char[], two reasons, first easy to clean it out with Arrays.fill, second you really do
  not care if the password contains UTF characters, because we are not meant to read/see the password, maybe
  encode/encrypt or salt it but that is it

`The underlying implementation opens a LineReader against stdin, and an FileOutputStream, for the stdout. The
line reader implies that even if we read character by character we will still need a new line trigger to
actually unblock the read calls the program`

## Caveats

- `wrapper vs concrete` - there are either wrapper versions of the IO classes or concrete ones that take in actual
  sources to read/write to. When constructing wrappers from concrete IO classes the order of the chaining matters, they
  are also called lower vs higher level streams. The lower levels are the concrete streams, the higher ones are the
  wrappers
- `close on wrapper vs concrete` - the close method works a bit differently based on if the stream is a wrapper or a
  concrete one, if we are dealing with a concrete stream then close will close the actual resource handle that the stream
  used to communicate with the underlying resource. If we are using a wrapper the close method will close the wrapped
  stream itself, meaning that if we call close on the wrapped stream a second time an exception will be thrown
- `implement serializable` - classes must implement serializable if they want to be serialized through the usage of
  an ObjectOutputStream, it is also advised that their properties (non transient) are also serializable
- `de-serialize` - load classes, allocate objects (skipping `Serializable` constructors and initializer blocks), run
  first not `Serializable` super no-argument constructor, then populate fields from the stream
- `while(true) de-serialize` - it is possible to read all the entries of a given class stored in a file, the `readObject`
  will throw EOFException, when the end of the file has been reached, but we can put the `readObject` in a while(true) loop
  and read until the exception is caught ensuring that all the entries in the file are going to be caught/read
- `serialize` - write an object graph’s non-static, non-transient state and the primary class metadata along size
  the serialization UUID to the stream.
- `File.list/listFiles` - list either all files and directories in a given path, or just the files, the list files
  returns File[] while list returns String[]
- `Console.printf/format` - the console class really has only two print methods, that is a `printf`, and format,
  both have the same signature, the `printf` even calls format internally, that is forwarded to the `Formatter` which in
  turn formats the output and writes that to the output stream, which is an instance of a Writer.
- `File.separatorChar` - the default file path and location separator, for the system, this is a static property
  that exists on the File class, and is different from the system property `path.separator`
- `System.getProperty("file.separator")` - effectively the same value as the static variable in `File.separatorChar`
  value that is the path separator for the current system. The File class also exposes the separator static variable
  which is actually the separatorChar (which is of type char) but of type String. It is Same value though.
- `System.getProperty("path.separator")` - this one is quite a bit different and has nothing to do with the ones above,
  this is actually the separator between path elements inside a PATH variable on the system, for Widows that is the
  following ";", for Unix is ":"
- `resources persistence` - generally it is required for all `InputStream` and `Reader` that are created to have an
  existing resource to which they are created, and that is not required for `OutputStream` or `Writer`, these will
  silently try to create the resource but they might throw exception if they can not create the resource
- `flush` - flush only exists on output or outgoing output-stream or writer interface, it does not make sense to
  have a flush method on a reader or input-stream interface, see skip method for a counter example of a method that
  only exists on the incoming input-stream and reader.
- `skip` - skip only exists on the reading or input-stream or reader interface, it does not make sense to have skip
  method on a writer or output-stream interface, it is very similar situation to the flush method that only exists on
  outgoing streams and writers instead
- `bridge classes` there are a couple of bridge classes that are meant to bridge the gap between input/output streams
  and the reader/writer interface, these allow us to read from and write to byte streams as if they were initially opened
  through a corresponding writer/reader
