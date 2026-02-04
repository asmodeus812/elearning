# NIO.1 - New input output v1

As part of the basic java Input output, the new input output was introduced after java 1.4 that adds new things to the
language to work with buffers, channels and provides async input and output actions to fetch data from all types of
resources not just file. This API is complementary to the old school IO, and in a way extends that API well adds new
capabilities in the old IO API to interface with the new input output.

## Buffer

The buffers are FIXED size collection of bytes, internally the new java Buffer api components simply wrap around basic
primitive arrays (on the JVM heap), or native ones (external of the JVM heap) more on that later, they are often
referred to as Heap (JVM heap based) or Direct (external to the JVM) respectively. There are all types of primitive
based Buffers, but they all boil down to byte size translation units - `ByteBuffer` (the finest grain one) `LongBuffer`,
`ShortBuffer`, `DoubleBuffer` etc.

Buffers are the gateway of the new `NIO.1` API and are the building blocks and glue that is used cross other API
components of the new `NIO` package to perform actions. Buffers are used to either store or retrieve data to/from them
by things like Channels and all types of I/O operation constructs. But its primary goal is to work with the newly
introduced components in the `NIO` library mostly

The buffer api starts with the abstract class Buffer, that provides all the necessary methods and constructs for a
generic buffer operations you might think of. Then the hierarchy splits down to `ByteBuffer`, `LongBuffer`,
`ShortBuffer`, etc. There is also a special type of `ByteBuffer` that is the `MemoryMappedBuffer`, that allows users to
directly work with memory mapped virtual pages, more on that later.

### Properties

1. Capacity - that is the maximum number of elements the buffer can hold, the capacity is fixed size, the buffer can not
   be re-sized once it has been created,

2. Limit - the index based boundary representing the total extend of the buffer, in other words that is the value that
   the current cursor/position must never exceed, when reading we should never go past that size, when reading
   and writing that limit is updated.

3. Position - the index of the next element to be written or read, this position is mutated when we call methods such as
   put or get, when we write or read to the buffer respectively

4. Mark - mark is a way to cache the current position, and remember it so later on we can reset the buffer position back
   to the mark, the mark must also comply with the limit restrictions meaning that we should be extra careful when
   working with the mark and resetting the position back to the mark. The mark is usually used in a single transaction or
   set of read/write pipeline to shuffle around the position back and forth allowing us to recall previous positions
   temporarily after which we restore them back using the mark() and rest() paradigm.

`Limit, position (cursor) and mark are all index based values, they represnt the maximum valid extend (limit), the
current extend (position) and previous extend (mark) in the buffer`

### Interface

The interface of the core Buffer class employs fluent interface, meaning that most methods do not return void but rather
return the current `this` buffer instance. That allows us to chain call methods on the Buffer instance easily

`Note that not all buffers can be written to, all buffers can be READ but not all are write enabled, that for example
could be a MemoryMappedBuffer to a read only file, there is a method in the buffer API to validate or check if the
current buffer instance is read only, writing to such buffers will produce exceptions, and segmentation faults
internally, on the OS and CPU level`

```java
public abstract class ByteBuffer extends Buffer implements Comparable
{
    public abstract byte get( );
    public abstract byte get (int index);
    public abstract ByteBuffer put (byte b);
    public abstract ByteBuffer put (int index, byte b);
    // This is a partial API listing
}
```

Gets and puts can be relative or absolute. In the previous listing, the relative versions are those that don't take an
index argument. When the relative methods are called, the position is advanced by one upon return. Relative operations
can throw exceptions if the position advances too far. For put( ), if the operation would cause the position to exceed
the limit, a `BufferOverflowException` will be thrown. For get( ), `BufferUnderflowException` is thrown if the position
is not smaller than the limit. The absolute put/get methods those that take index position do not advance the internal
buffer position pointer.

### Writing

Let us take an example that writes to a buffer instance, assume this buffer instance is created with enough capacity and
will be filled with 5 bytes, characters which are part of the base ASCII table.

```java
// we but data in the buffer, assume the buffer has enough capacity to hold these elements, we cast the chars to bytes,
// because by default Java characters are 2 byte long, we need to convert them to 1 byte, in this case the ascii codes for
// these Latin characters can fit into 1 byte
buffer.put((byte) '0').put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd')
```

After having written these few bytes or elements to the buffer, the internal position and limit would have moved
forward, in this case our limit is now 5, we have added 5 valid elements and our position is 4 because that is the
index of the last valid element that was lastly written.

```java
// we can also use the absolute version to update an element within the constraints of the buffer, meaning that this
// must not exceed the capacity of the buffer, but also it MUST NOT exceed the limit of the buffer, had we done a write
// to index 5 that is past the valid limit we would have gotten an exception.
buffer.put(0, (byte) '1')

// we can however update the limit, in all essence telling the buffer that the valid sequential elements of  data
// now span from the start of the buffer and are not 5 but 10 elements, we can then put a new element at index 5,
// without getting an exception
buffer.limit(10).put(5, (byte) '1')
```

### Reading

The buffer is now filled with some data, and it is crucial to understand that since we have a single position that
tracks the current element position inside the buffer we have to somehow reset that position for reading.

If we pass this buffer to a consumer that wants to read this data without preparing it for being read, then the
reader or consumer will read garbage. This is done by what is called `flippng` the buffer, that prepares the buffer
for reading. What it does actually is set the position back to the start, and updating the limit

```java
// notice that we set the cursor position to 0, and we tell it that the upper limit to which we are allowed to read
// is the original position.
public Buffer flip() {
    limit = position;
    position = 0;
    mark = -1;
    return this;
}
```

The flip method has a companion support method, in case we wish to reset the buffer again before we have fully read
the full content off of it, we can not just call flip() again, that would reduce the limit to the current position
to which we have read() it last. Instead what we do is called - rewind.

```java
// reset the position to 0, but DOES NOT change the limit, meaning we will be able to read up until the original
// limit set by the last flip but again starting form position 0
public Buffer rewind() {
    position = 0;
    mark = -1;
    return this;
}
```

`If you flip a buffer twice it effectively becomes 0 sized, and you can not read from it, that is because the limit
and position will be both set to 0, the first call to flip will set the position to 0, and the second will set the
limit to position which is 0`

To clear the buffer effectively reset the position and limit back to the original extend, we call the clear method,
simply ensures that state of the buffer internals point to the original state of the buffer after it was created
first.

```java
public Buffer clear() {
    position = 0;
    limit = capacity;
    mark = -1;
    return this;
}
```

`The clear method DOES NOT clear the actual data, that would be too expensive, it just allows you to write over it
if you so desire, but the data remains in the buffer until something else tramps over it with antoher write.`

### Draining

Draining a buffer is the process of reading the full contents of the buffer that were written to it. When we pass a
buffer to a channel for example that channel will fully drain the buffer out of its data, calling get() or an
alternative of that method, until there are no longer remaining elements to read that is achieved by using the
`hasRemaining()` or `remaining()` methods, that tell you if you have more elements to read until you reach the limit -
i.e. if the internal cursor position has not yet reached the limit after we have flipped the buffer for reading.

```java
public class BufferFillDrain {

    // a tracking index for the amount of data we have moved form the string array into the buffer
    private static int index = 0;

    // a string array that is being moved to the buffer, we fill each array element into the buffer then drain
    // it then fill the next one and so on
    private static String [] strings = {
        "A random string value",
        "The product of an infinite number of monkeys",
        "Hey hey we're the Monkees",
        "Opening act for the Monkees: Jimi Hendrix",
        "'Scuse me while I kiss this fly",
        "Help Me! Help Me!",
    };

    // each time we call the fill buffer we fill the data from the current/next index of the string data into
    // the buffer, the buffer has already been cleared for us after the last draining
    private static boolean fillBuffer(CharBuffer buffer) {
        if (index >= strings.length) {
            return false;
        }
        String string = strings[index++];
        for (int i = 0; i < string.length(); i++) {
            buffer.put(string.charAt(i));
        }
        return true;
    }

    // drain the buffer, the buffer is read from, but notice that before each call to drain we must and we do
    // flip the buffer readying it for reading
    private static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print (buffer.get());
        }
    }

    // main entry point does the fill-drain loop, based on the static data of the, note that we consistently
    // re-use the same buffer for each entry in the strings array
    public static void main(String [] argv) throws Exception {
        // allocate a buffer big enoguh to fit the biggest entry in the strings array, that is important to note here
        CharBuffer buffer = CharBuffer.allocate(100);
        // fill the buffer with the current entry from the strings array
        while (fillBuffer(buffer)) {
            // flip the buffer for reading after we have done the filling
            buffer.flip();
            // read from the buffer as it is already prepared to be read from
            drainBuffer(buffer);
            // clear effectively just resets the position to 0, and limit to max capacity
            buffer.clear();
        }
    }
}
```

### Compacting

The NIO.1 buffers have a very cool capability called compacting, basically if we have read some data into the buffer,
then we wish to resume filling it, we can compact it essentially moving all elements to the left, shifting them.
That means that all elements that we have already read will be overridden with ones that we have not read
effectively 'freeing' space in the buffer, that could be expensive if done too often, but is a neat way to
'increase' the capacity of the buffer when it is of very limited size.

```java
// effectively override the currently read elements and copy everything that starts from position (our next read)
// back to 0, up until the remaining elements that have to be read.
public ByteBuffer compact() {
    int pos = position;
    int lim = limit;
    int remaining = (pos <= lim ? lim - pos : 0);
    System.arraycopy(hb, ix(pos), hb, ix(0), remaining);
    position(remaining);
    limit(capacity);
    discardMark();
    return this;
}
```

### Marking

Marking a buffer is the process which allows us to save the position of the buffer at a given state so we can return
back to it at a later point. Notice that most of the methods that deal with resetting or in some sort modifying the
limit/position of the buffer always invalidate the mark setting it back to -1, this is obviously important because
no guarantee that the mark will remain valid after we have done an operation like flip/rewind/clear/compact etc.

`The mark is always invalid when the buffer is created or invalidated when some of the mutating methods above
metnioned are (clear, rewind, flip, etc) called, therefore if we call reset on an invalid mark we will get an
exception thrown at our face`

### Comparing

All buffers provide a way to compare each other, using the known `equals and compareTo`, these work as expected, the
valid portion or the content of the buffers is compared it is commutative, and internally actually uses SIMD -
single instruction multiple data instructions for vector comparison

`They are considered equal when they have the same number of remaining elements, and the elements themselves
individually as returned by calls to get() must be equal to each other. The capacities of the buffers NEED NOT be
the same. The compareTo method uses lexicographical comparison with the same premise as equals, compares bytes`

### Transfer

As we have seen the get and put methods that work on a single element level are not very efficient we can do better
with methods that allow bulk move between draining or writing to the buffer.

```java
// A very big array that will not fit in the buffer
char [] bigArray = new char[1000];
// Get count of chars remaining in the buffer
int length = buffer.remaining();
// Buffer is known to having less than 1,000 elements
buffer.get(bigArrray, 0, length);
// Do something useful with the data
processData (bigArray, length);
```

The reason we use the remaining here is to ensure that we safely move the data to the big array and we move only the
amount of data that the buffer has remaining to be actually copied, otherwise, the default method that accepts the
destination only will use the destination length not the remaining, and if there are less remaining that the length
then the method will throw exception.

```java
// Note that this calls the overloaded get with the length of the destination, not the amount of remaining elements
// in our buffer, that is a bad idea if the length of the destination is bigger than what we have actually remaining
// in the buffer as actual content/elements to be read.
public ByteBuffer get(byte[] dst) {
    return get(dst, 0, dst.length);
}
```

The general pattern that is should be followed, should be of using the `hasRemaining` or `remaining` methods to check
what is the actual readable content size of the buffer before we attempt to do any read.

```java
char[] smallArray = new char[10];
while (buffer.hasRemaining()) {
    // ensure that we never read more than we can from the buffer
    int length = Math.min (buffer.remaining(), smallArray.length);
    // get exactly the safe amount of content that we can get
    buffer.get(smallArray, 0, length);
    // do something cool with this data
    processData(smallArray, length);
}
```

The put method does the same operation as the get method, however the length of the array to be put must not exceed
how many remaining elements we can put, remember remaining returns the amount of data we can read or write.
Depending on the state of the buffer, it does this simple arithmetic calculation `limit - position`.

`Just like get we have to first ensure that the elements we attempt to put into the target destination are not less
than how many we actually request to be writting into the target array from the buffer otherwise, an exception is
thrown`

```java
// just like get, the put methods have two major flavors, for moving data, the base one that takes just source will
// use the length of the source, and unless that is the same or less than the remaining we will get exception, that is
// why it is better to use the second overload to ensure we never move more data from the buffer into the src
public ByteBuffer put(byte[] src);
public ByteBuffer put(byte[] src, int offset, int length);
```

### Creating

First when creating Buffers we can either allocate one, or wrap existing buffers, when wrapping we provide the
source buffer to be wrapped around. When allocating the internal implementation takes care of creating the actual
buffer.

```java
CharBuffer buffer = CharBuffer.allocate(100);

char[] charArray = new char[100];
CharBuffer buffer = CharBuffer.wrap(charArray);
```

When using allocate or wrap we always create what is known as non-direct (heap) buffers, we will discuss later.
However these always have a backing array that sits somewhere and is managed directly by the JVM therefore we can
always be confident that `Buffer.hasArray` will return true.

### Duplication

When duplication is play we have something that is known as view buffers, view buffers are special type of buffers
that represent the original or part of the original buffer, and provide a reference or a view into its data
contents. Usually view buffers are not copying the data that the source buffer contains, rather they only provide a
partial or full view into it, often are read-only but are not restricted to being read-only.

What the buffer view is is a duplicate of the original source buffer but with its own position capacity limit and
mark, however the underlying backing buffer reference is the exact same that means that putting or writing data into
either one will have an effect on both. If we wish to only duplicate the buffer for read-only operations where all
write / put operations are we going to throw `ReadOnlyBufferException`, can use the method `asReadOnlyBuffer` that
is effectively the same as the duplicate one.

```java
// create the initial state of the buffer
bufferOne = CharBuffer.allocate(100);
// perform write or read operations
bufferOne.put('a').put('b');
// remember the current state
bufferTwo = bufferOne.duplicate();
// create a read-only duplicate snapshot
bufferThree = bufferOne.asReadOnlyBuffer();
// clear the original source buffer
bufferOne.clear();
// buffer two state remains untouched
char res1 = bufferTwo.flip().get();
// buffer three can also be read from
char res2 = bufferThree.flip().get();
// we read the same from both buffers
assert (res 1 == res2)
```

`The duplicate buffer creates a snapshot of the current state of the buffer at the current point in time. We can
then change the original buffer as we see fit. It is effectively a more advanced version of the mark/reset paradigm,
we 'mark' the entire state of the buffer, save it and can later retrieve and use that state of the buffer as it was
in that point of time`

### Slicing

Slicing a buffer is very similar to the duplication, it creates a snapshot of the buffer at its current state, however
the snapshot is a bit different, the state that slice represents is the remaining pat that can be read/written from/to
the buffer, at the moment we call slice. The resulting buffer is

```java
// create and allocate the original buffer
bufferOne = CharBuffer.allocate(10);
// add new elements to the buffer, 3 elem
buffer.put('a').put('b').put('c');
// new slice will be from indices [4-9]
bufferTwo = bufferOne.slice();
// put a new element right after the 'c'
bufferTwo.put('d');
```

`Slice allows us to share the underlying buffer itself (heap or direct) but have a completely independent slices of
it assigned to different intermediate buffers.`

So here is an example of doing exactly that, three slices of the source buffer spanning their own sub-range/slices
of the original buffer. Each can write and read independently of each other, but still be done in the same data
store / buffer

```java
// create and allocate the original buffer
buffer = CharBuffer.allocate(1024);
// set position and the limit of buffer
buffer.position(0).limit(127);
// new slice from indices [0 to 127]
bufferOne = buffer.slice();
// set position and the limit of buffer
buffer.position(128).limit(255);
// new slice from indices [128 to 255]
bufferTwo = buffer.slice();
// set position and the limit of buffer
buffer.position(256).limit(511);
// new slice from indices [256 to 511]
bufferThree = buffer.slice();
// .................................
```

### Byte-buffers

These are the core base class and type for all types of buffers, besides the boolean buffers. As we know the real
building blocks of the modern computers are really the byte. Every byte is 8 bits in size, but that was not always
the case in the past there were differing hardware implementations, where 1 byte was of size 8 bits, 4 bits, 9 bits
and so on. Later on the industry settled on 8 because it is a good power of 2, and it can hold all ASCII characters
at the time which were no more than 128.

The important part here to take a note of is that more complex data in the computer is represented as a sequence of
bytes , and that these bytes have order how that order is defined is called `byte endianess`, and it is very important
in hardware design and protocol definitions. What does it mean ? We have really two types of byte ordering, big and
little endian.

- `BIG_ENDIAN` - bytes of a multibyte value are ordered from the most significant to least significant.
- `LITTLE_ENDIAN` - bytes of a multibyte value are ordered from the least significant to the most significant.

The byte order is something that most programs do not concern themselves with, but hardware designers do, and also,
one example where a specification such as the IP protocol is using byte order, where big endian to transfer
information on the internet, that means that when a host machine that is implementing `LITTLE_ENDIAN` receives this
data it has to convert it to or interpret it as `BIG_ENDIAN` otherwise the multi byte sequence that is coming in would
not make much sense to such a system.

Just as a side not the hardware industry has long ago chosen that `LITTLE_ENDIAN` will be the industry standard that
most hardware computer devices implement, that is there are still `BIG_ENDIAN` devices of course but those are not as
wide spread as they were in the past.

Now why are we talking about this why does it matter, the byte buffer in java is by default, at least these that
represent multibyte data - BIG_ENDIAN, that might sound strange but it is done for compatibility reasons, and it
can have some non-insignificant impact on systems that work with LITTLE_ENDIAN order.

The java standard library exposes a class called `ByteOrder` that defines two constants big and little endian to
represent the byte order, and the native order which is what. Below is an excerpt from that class that is showing
how the values are defined for these constants

```java
/**
* Constant denoting big-endian byte order.  In this order, the bytes of a
* multibyte value are ordered from most significant to least significant.
*/
public static final ByteOrder BIG_ENDIAN = new ByteOrder("BIG_ENDIAN");

/**
* Constant denoting little-endian byte order.  In this order, the bytes of
* a multibyte value are ordered from least significant to most
* significant.
*/
public static final ByteOrder LITTLE_ENDIAN = new ByteOrder("LITTLE_ENDIAN");

private static final ByteOrder NATIVE_ORDER = Unsafe.getUnsafe().isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
```

`As mentioned the reason this entire ByteOrder is required to be exposed into the internal implementation and actually
known to the program is because there are other types of buffers, that represent multibyte primitive such as short,
integer, float, double etc and they do require or need deeper knowledge of the byte order in order to correctly encode
the value that is being written or read to the buffer`

### Direct-buffers

Direct buffers unlike their Heap based JVM managed counterparts are not, or may not be sequential in nature that is
there is no guarantee how or where the JVM will allocate the array backing the buffer. However direct buffers as the
name suggests are a more direct approach to dealing with this problem they instead do not reside or are also not managed
by the JVM but by the operating system, still reside in user space but have a better interoperability with native I/O
operations.

So what happens, when we use a direct vs non-direct heap based buffer in our attempt to do input or output operations.
Well if we use a non-direct buffer, what might happen internally is as follows

1. Creates a direct temporary buffer
2. Copies the data from the backing non-direct buffer into the temporary direct buffer
3. Execute the I/O native operation, drain data into the temporary direct buffer
4. Copy the data back from the direct temporary buffer to the non-direct buffer

As you can see there are two levels of indirection here, that consist of a lot of data copying around, we can bypass
this by directly using a direct buffer, and save ourselves the copy round-trips. And that can happen on every I/O
operation we execute, you can imagine that for high throughput systems that can be a total killer of performance

So instead we allocate a direct buffer, direct buffers as we said are not managed by the JVM process they are not even a
part of the native java language, they exist in a bubble outside of it obtained by system calls, these can technically
be expensive sure, but they are extremely useful when needed, and fast.

```java
// what this does is basically calling the underlying operating system telling it give me a chunk of native memory
// that i will not manage, and a pointer to that memory, it is important to know that this is still a user space
// memory assigned and managed by the operating system, part of the JVM process itself, that is how the OS sees it,
// this is not kernel level memory.
ByteBuffer nativeDirectBuffer = ByteBuffer.allocateDirect(1024);
```

### View-buffers

The view buffers are a way to create a temporary view into a byte buffer that maintain its own tracking information
like mark, position, limit, etc. These exist as methods on the `ByteBuffer` class and have the format of
`asXXXBuffer`, where XXX stands for the primitive view buffer that is being created, float, int, double, short etc.
Each implementation of this method follows the following idea. As we have discussed so far we have the basic
view-buffers which are created by the `duplicate()` and `asReadOnlyBuffer()` methods, but `ByteBuffer` class exposes a
few more, which are mostly related to creating a multibyte buffer view of the byte buffer instance.

```java
// create a read-write enabled view integer buffer, from the current `ByteBuffer`, if we wish to make a read-only
// one we can then instead use - `buffer.asIntBuffer().asReadOnlyBuffer()`
public IntBuffer asIntBuffer() {
    int off = this.position();
    int lim = this.limit();
    assert (off <= lim);
    int rem = (off <= lim ? lim - off : 0);

    int size = rem >> 2;
    if (!UNALIGNED && ((address + off) % (1 << 2) != 0)) {
        return (bigEndian
            ? (IntBuffer)(new ByteBufferAsIntBufferB(this,
                -1,
                0,
                size,
                size,
                address + off))
            : (IntBuffer)(new ByteBufferAsIntBufferL(this,
                -1,
                0,
                size,
                size,
                address + off)));
    } else {
        return (nativeByteOrder
            ? (IntBuffer)(new DirectIntBufferU(this,
                -1,
                0,
                size,
                size,
                off))
            : (IntBuffer)(new DirectIntBufferS(this,
                -1,
                0,
                size,
                size,
                off)));
    }
}
```

`Remember that only the ByteBuffer provides these extended view-buffers that allow us to have a direct conversion or
translations into a higer order multibyte buffer representations. Those are also by deafult read/write enabled`

```java
// this example demonstrates how we can write data in completely different order, as long as that order matches the
// expected big or little endian ordering then the data can be retrieved correctly, note that we write to two byte
// buffers, the text HI!, but it is put differently where the higher and lower order bits come accordingly to the type
// of the multi byte order that the buffer is set with.
public class BufferCharView {

    public static void main(String [] argv) throws Exception {
        // ensure that the logger is first configured to print abridges messages we do this to ensure that the logger format string does not
        // pollute the stdout with needless noise when printing to stdout
        InstanceMessageLogger.configureLogger(BuffersAndChannels.class.getResourceAsStream("/logger.properties"));

        // we create two types of byte buffers here with different byte ordering, both contain the same data but the bytes are
        // ordered differently, either the most significant byte is first or the least significant
        ByteBuffer byteBufferB = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
        ByteBuffer byteBufferL = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN);

        // we put the higher order bytes first that implies that this is big_endian, meaning that when content is read from this buffer it
        // will be read according to its order which we have set to be BIG_ENDIAN
        byteBufferB.put((byte) 0);
        byteBufferB.put((byte) 'H');

        byteBufferB.put((byte) 0);
        byteBufferB.put((byte) 'I');

        byteBufferB.put((byte) 0);
        byteBufferB.put((byte) '!');

        byteBufferB.put((byte) 0);

        // we put the lower order bytes first, that implies that this is LITTLE_ENDIAN, meaning that when content is read from this buffer it
        // will be read according to its order which we have set to be LITTLE_ENDIAN.
        byteBufferL.put((byte) 'H');
        byteBufferL.put((byte) 0);

        byteBufferL.put((byte) 'I');
        byteBufferL.put((byte) 0);

        byteBufferL.put((byte) '!');
        byteBufferL.put((byte) 0);

        byteBufferL.put((byte) 0);

        // prepare the buffers to be read from after we have written the data to them we have to first flip them, that would reset the
        // position to 0, and the limit to 7, that is how many bytes can be read from it.
        byteBufferB.flip();
        byteBufferL.flip();

        // we expect to see the exact same content here from both buffers, HI! in both cases but that shows how we can have different data
        // technically in the buffer (or at least ordered differently) and yet still have the same result
        LOGGER.logInfo("CharBuffer(B): " + byteBufferB.asCharBuffer().asReadOnlyBuffer().toString());
        LOGGER.logInfo("CharBuffer(L): " + byteBufferL.asCharBuffer().asReadOnlyBuffer().toString());
    }
}
```

Notice the way this buffer data is written though, we write the higher byte first then the lower one, which in this
case the higher bytes are 0, the lower ones are the ASCII representations of the chars `H, i, !`, when the view is
created into a char buffer, reading them will be 2 bytes at a time, since that is how big a char is in java

### Multibyte

As we talked about the capabilities of the byte buffer,we also spoke how we can interpret a byte buffer content as a
different type of a multibyte buffer, however it is also possible to directly put multibyte primitives into a byte
buffer, by using the `getXXX` and `putXXX`, methods. Where XXX stands for short, int, double, float etc.

The underlying implementation is similar as the view buffer, as the native methods used to pack/unpack the multibyte
values used are all coming from the but instead of first creating the class `jdk.internal.misc.Unsafe` which exposes
access to converting / reading and translating multibyte values to and from multiple bytes, based on the `endianess` of the buffer

```java
// the method we call, will advance the position of the buffer as well.
public ByteBuffer putInt(int x) {
    putInt(ix(nextPutIndex((1 << 2))), x);
    return this;
}

// the actual overload being called does not advance the buffer position
private ByteBuffer putInt(long a, int x) {
    try {
        int y = (x);
        UNSAFE.putIntUnaligned(null, a, y, bigEndian);
    } finally {
        Reference.reachabilityFence(this);
    }
    return this;
}

// so here is where and how the magic happens, the integer is literally unpacked into bytes, based on the size. You
// can see that this achieved by extracting the byte values of each of the multiple bytes that this integer consists of
// by leveraging several bit shift operations.
public final void putIntUnaligned(Object o, long offset, int x) {
    if ((offset & 3) == 0) {
        putInt(o, offset, x);
    } else if ((offset & 1) == 0) {
        putIntParts(o, offset,
        (short)(x >> 0),
        (short)(x >>> 16));
    } else {
        putIntParts(o, offset,
        (byte)(x >>> 0),
        (byte)(x >>> 8),
        (byte)(x >>> 16),
        (byte)(x >>> 24));
    }
}
```

Now all of that being said, we have to remember that these examples work for both a Native i.e. a direct buffer and
a heap i.e. non-direct buffer managed by JVM.

Here is an example of actually writing multibyte values into byte buffer with little endian order and then reading
it as a integer buffer view, as you can see we can easily map between different views that would greatly simplify
the amount of work needed to be done to read/write data to a buffer

```java
public static void main(String[] args) {
    // demonstrate how we can write to a byte buffer, integer values which are spanning 4 bytes usually, we have allocated
    // 16 bytes, enough for exactly 4 integers, if we try to put a 5th one this implementation will throw exception because
    // of the fact that we overflow the buffer it can not hold more than 4 ints
    byteBufferL = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
    byteBufferL.putInt(5);
    byteBufferL.putInt(15);
    byteBufferL.putInt(25);
    byteBufferL.putInt(35);

    // if we include this line the function will fail with buffer overflow exception because we are attempting
    // to fit a 5th integer into the byte buffer that can only hold 4 integers, since we have allocated exactly
    // space for 4 integers or that would be 16 bytes - `byteBufferL.putInt(45);`

    // flip the buffer so we can prepare it for reading
    byteBufferL.flip();

    // we convert this byte buffer to a read only integer buffer, that will allow us to read the content as if it were plain
    // integers that were stored in the byte buffer, even though every integer is in essense 4 bytes long.
    IntBuffer asReadOnlyBuffer = byteBufferL.asIntBuffer().asReadOnlyBuffer();
    String intBufferElements = IntStream.range(0, asReadOnlyBuffer.remaining())
                    .map(asReadOnlyBuffer::get)
                    .<String>mapToObj(String::valueOf)
                    .collect(Collectors.joining(","));
    LOGGER.logInfo("IntBuffer(L): " + intBufferElements);

}
```

### Memory-mapping

Memory mapped buffers are always direct and can be created by `FileChannel` objects. They always represent a direct
one to one mapping with the underlying, memory that the Operating System provides the Java process when a memory
mapped file is created. There is a special chapter that will take a great look at how to work with `mmap` and memory
mapped files

## Channels

They are the second big innovation in the new NIO.1 API. They are not an extension of enhancement of any of the old
school I/O components but a completely new construct that deals with direct communication with I/O devices of any
kind usually Sockets and Files. Channels work tightly with buffers filling them with data, and or reading data from
them.

The channel API, unlike buffers is primarily implemented and defined through interfaces, the primary Channel
interface has two methods only `isOpen and close`, since it is implementing the `Closeable` interface

There is a special interface type of channel called the `InterruptibleChannel` these allow the implementation to
interrupt or cancel the channel but will be taken a look at a bit later, in a future chapter

Then we come to the more concrete channel interfaces that provide different behavior for channels, base on the
different properties and actions that can be performed onto them. Not all channels are ReadableByteChannel or
WritableByteChannel etc.

- `ReadableByteChannel` - channel that can read bytes from a byte buffer
- `WritableByteChannel` - channel that can write to bytes to a byte buffer
- `ScatteringByteChannel` - channel that can write bytes to multiple byte buffers at once
- `GatheringByteChannel` - channel that can read from multiple byte buffers at once
- `SeekableByteChannel` - channel that allows its position to be changed during read/write

### Creating

All channels need to first be created using some sort of a resource be it a destination or a source. Either way the
different types of concrete channel implementation such as Socket or File have their own factory methods that allow
us to create instances of these channels. The method is usually called open and is often overloaded. There are other
means of opening a channel some classes such as `RandomAccessFile` or `FileInputStream` have methods called
`getChannel`

```java
public static FileChannel open(Path path, OpenOption... options)
public static FileChannel open(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
```

```java
public static SocketChannel open()
public static SocketChannel open(SocketAddress remote)
```

#### FileChannel

So creating a file channel can be easily done from a `FileInputStream` object, below we have created a
try-with-resources block that does exactly that.

The example below is quite interesting because it allows us to stream the content of the file directly the stdout.
How does it work ?

Well first we need to recognize that both the file and the stdout are in essence "files" they are exposed from the
file system through what is known as a file descriptors, this we can use to our advantage to attach to these file
descriptors, for both files and use them to directly transfer data from one to the other

The file channel class has a perfect method to do this exact thing. It bypasses the entire intermediate buffer copy
that usually is associated with transfer between channels where the data from the channel is pulled into the JVM in
a java array then copied back to the destination channel.

The `transferTo` method can use native OS system calls to move the data directly with 0 copy overhead from the source
to the destination. This is quite powerful as we avoid completely the middle man and we never pull any data into the
JVM, that is if we do not need it of course.

```java
// first obtain the full path to the file we wish to print to stdout, in this case we use a sample file that we know
// is very big, and pulling / copying data chunk by chunk will be expensive.
URL loggerPropertiesUrl = BuffersAndChannels.class.getResource("/very-big-file.txt");
LOGGER.logInfo("Reading content from file: " + loggerPropertiesUrl.getPath());

// open channels to the two handles, we have the source and destination file handles here, both are created from a
// file stream. File streams have the ability to create channels by using the file descriptor handle. For the
// destination we use the FileDescriptor handle to stdout form which we create the target destination channel
try (FileInputStream fis = new FileInputStream(loggerPropertiesUrl.getPath());
                FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
                FileChannel in = fis.getChannel();
                FileChannel out = fos.getChannel()) {

    // start off at the start of the file and we copy the entire contents of the file which is expressed by the size method property.
    long pos = 0;
    long size = in.size();

    // until we have not copied the entire file to stdout, transfer the data to the stdout channel
    while (pos < size) {
        // here is where the magic happens, the method `transferTo` is capable of taking leverage of some
        // native OS capabilities which allow the operating system to transfer the data from one channel(file
        // descriptor) to another without involving intermediate copy of the data through additional user space
        // buffers, such as what we might have to do had we used standard old school IO patterns
        long n = in.transferTo(pos, size - pos, out);
        if (n < 0) {
            break;
        }
        // move the position for the next read, this pos value is also used to reduce the total size we have
        // to copy over in the transfer method
        pos += n;
    }

    // print out some information about the total amount of bytes that were transferred to the stdout
    LOGGER.logInfo("transferTo(): Printed file contents of size: " + size);
}
```

The implementation above shows pretty much the most efficient way to move data between two files in Java using
channels, there is nothing more efficient we can do here and all intermediate work is completely skipped and data is
copied directly between the channel

Note that this implementation actually might close the stdout file descriptor after we exit the try-with-resources
block, which might not be desired, for this simple example that is fine since we have nothing other that outputs to
stdout after outside the try-with-resources. In case we had we might not be able to use stdout any longer so be
extra careful with it.

### Closing

Unlike the buffers channels once closed can not be reused, we open them do some work and then close the resource,
usually in a try-with-resources block. That has always been the case and that is host most of he resources in java
operate, the pattern is pretty straightforward.

The close method might cause the current thread to block for a while that is intended but can be a problem, what
will happen to the resource or the channel if that thread does that, well there are special types of channels that
handle this use-case they are called `InterruptibleChannel`, these allow the close to be automatically handled in case
the current thread for some reason becomes interrupted by another thread while it is waiting for the resource to
close, that would prevent the channel resources form leaking out in the wild

This is also applicable to channels that are being interrupted while there is read/write operation going on, what
does it mean - that means that if the current thread gets interrupted and the channel is of type
`InterruptibleChannel` then it will immediately get closed. That might seem excessive but the idea is that the
resource has to be reliably cleared and freed to avoid leaks

The regular channels that do not require interrupt handling are these that directly implement only Channel, these
are usually not tied to lower level constructs and they are not in danger of leaking any resources. These types of
channels implement a more basic closing mechanic and do not care about any spurious state interrupts in their
thread.

So here is how it works on a more basic level, first every time the channel that does some sort of low level work
that involves a lower level resource wraps these actions in what are called begin-end methods, these methods prepare
the interrupt machinery. After calling begin the channel performs some blocking task involved this lower level
resource on the current thread.

Another thread calls interrupt on the blocked thread that was working with our channel, and the interruptible
channel receives / captures the interrupt and immediately gets closed, freeing the used resources.

The state of the blocking thread that was doing the channel work is set and it receives the
`ClosedByInterruptException`.

`AbstractInterruptibleChannel requires implementations to write implCloseChannel() such that if it runs while
another thread is blocked in native I/O, that native call returns immediately (either normally or with
error/exception).`

### Scatter/Gather

Channels provide a new very important capability that is known as scatter or gather. that is a vectorized IO
operation that allows the channel to receive data from multiple buffers or to spread its data across multiple
buffers in one go. This is pretty powerful as it allows us to avoid a lot of low level system calls that would
otherwise be quite expensive this method or approach uses the native writev and readv methods provided by the
operating system, those stand for read/write vector. The source or destination of the read/write is a vector of
buffers instead of a single buffer.

We have two important interfaces here that extends the `ReadableByteChannel` and the `WritableByteChannel`, those are
the `ScatteringByteChannel` and the `GatheringByteChannel`. So the interface looks something like this

```java
// ScatteringByteChannel
public long read(ByteBuffer[] dsts)
public long read(ByteBuffer[] dsts, int offset, int length)

// GatheringByteChannel
public long write(ByteBuffer[] srcs)
public long write(ByteBuffer[] srcs, int offset, int length)
```

Let us focus on the simpler methods that just take one argument for src and dst. Then we will investigate the
overloaded versions that take in offset and length.

```java
// collect some data that we are going to use to detect the file that we need to read from and print to stdout, the
// example here does something very similar to what the previous example does however here instead of direct transfer
// we read data into buffers, the benefit of that is that we can if we wish inspect the buffer content before writing
// to the stdout
URL loggerPropertiesUrl = BuffersAndChannels.class.getResource("/very-big-file.txt");
FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
FileChannel out = fos.getChannel();
try (FileInputStream fis = new FileInputStream(loggerPropertiesUrl.getPath()); FileChannel in = fis.getChannel()) {
    // here we calculate how to split the content of the file, into two buffers of equal size in this case to
    // demo how we can scatter between the two independent buffers
    int size = (int) in.size() / 2;

    // allocate two buffers to hold the full content of the file, when the scatter read executes it will fill
    each buffer from the array and then move to the next one
    ByteBuffer dstOne = ByteBuffer.allocateDirect(size);
    ByteBuffer dstTwo = ByteBuffer.allocateDirect(size);
    ByteBuffer[] buffers = {dstOne, dstTwo};

    // scatter readv into the two buffers
    in.read(buffers);

    // flip the buffers ready to be read
    buffers[0].flip();
    buffers[1].flip();

    // gather writev from the buffers to stdout
    out.write(buffers);

    // that is optional but we restore buffer state
    buffers[0].clear();
    buffers[1].clear();

    // at this point the stdout will contain the full file content
    LOGGER.logInfo("Scatter + gather file content to stdout");
} finally {
    // optionally we can close the stdout but not a good idea if that part of the program is not the last thing
    // that would be executed, anything else might want to write to stdout will probably explode.
    fos.close();
    out.close();
}
```

`Note that if we wish to pull or read data from the buffers we have to bring that data into user space implying that
JVM will internally allocate normal byte arrays and move the content from the direct buffers into them. We can not
escape that reality.`

The arguments of the overloaded version might seem a bit daunting what would offset and length imply here that we
have multiple sources or destinations.

`The overloaded methods that take offset and length, simply tell the read/write methods from which position of the
buffers array to start (offset) and how many of the buffers from the vector of buffers to actually write/read
to/from (length)`

So what is the benefit or use case of using vectorized buffer writes and read like this, the idea is rather simple
if we use one big buffer to replace these vector buffers.

Say we want to read a file or stream of data that we know the structure of , we know that this stream is separate
into multiple differing chunks of data that we care about it has header of certain size, body of certain size, and
footer of certain size, Now given that we wish to get only the body and send it to one place, get the footer and
send it somewhere else, and only read the header to verify its integrity, now we can either:

- first option is dump everything into one big mega buffer, that would be bad because now we have to provide the user
  space code parse and read the header, separate the body and the footer so we can send it over to the desired
  destinations, in other words we need to provide all of the glue code to do this. This might incur some overhead in the
  processing department as well if we have to copy some data over to read and parse the header for example.

- second option is provide pre-defined number of buffers (3 in our case) with specific sizes that reflect the
  structure of the stream that we are reading, after the vectorized read (which is going to be just as expensive as a
  single read) we have all 3 buffers neatly packed with the data we care about, now we just pick the header buffer
  validate it, then the body buffer, or the footer buffer and send it over to wherever it needs to go, maybe the body
  is sent to one channel over a socket, the footer is saved to a file etc.

The vectorized buffers approach is mostly about providing cleaner code, that need less support and maintenance because
most of the low level job of splitting the structure to accommodate our needs is already done by the vectorized
read/write.

### Caveats

There is one general caveat that we need to address regarding reading and writing from channels, as we already mentioned
reading or writing is usually an operation that does not guarantee that all the buffers provided in the read or the
write operation will be filled or drained with a single call to write or read. Why is that ? The operating system might
not be able to provide the entire information we are asking for in a single system call, if we take a file channel for
example, the file might be opened or used by another process, it might be temporary blocked for a variety of reasons.
That is why we usually read or write in a loop until the read or write operations have no longer anything to provide or
we reach EOF. This is done by checking the return value of the read or write when it reaches -1, that implies that we
have drained or filled the buffers and the channel can be closed and the data is read/written

```java
// so here is an example with vectorized reads that ensures that all buffers are filled and that it tries to read until
// there is nothing more to read or there is no more space in the buffers when either of these criteria is met the while
// loop will terminate, and we can ensure that we have read everything that this channel has to offer
static void readFully(ScatteringByteChannel ch, ByteBuffer... bufs) {
    while (true) {
        long n = ch.read(bufs);
        if (n < 0) {
            return
        }
        boolean done = true;
        for (ByteBuffer b : bufs) {
            if (b.hasRemaining()) {
                done = false;
                break;
            }
        }
        if (done) {
            return
        }
    }
}
```

The same applies for writing fully to a channel by draining the buffer from all of its data until the channel can accept
data, we do something analogous to the `readFully` method when we want to drain the buffers into the channel

```java
// same premise, we ensure that we have no-remaining data to be drained from the buffers, and we also ensure that the
// channel accepted the data, if the buffers are empty already or the channel accepted no more data (i.e we get a 0)
// then we can be sure that we have written the data
static void writeFully(GatheringByteChannel ch, ByteBuffer... bufs) throws IOException {
    while (true) {
        boolean done = true;
        for (ByteBuffer b : bufs) {
            if (b != null && b.hasRemaining()) {
                done = false;
                break;
            }
        }
        if (done) {
            return;
        }
        long n = ch.write(bufs);
        if (n == 0) {
            return;
        }
    }
}
```
