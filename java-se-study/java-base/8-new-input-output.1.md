## Buffers

The `NIO.1` introduced some wrappers around the primitive buffers such as `CharBuffer`, `IntBuffer`, and `ByteBuffer`, these
are abstract classes that enclose have usually two primary implementations one is the Heap and the other is the
Direct based one. The heap buffer implementations are simple, they lie in the JVM heap and are directly accessible
from it making working with them relatively easy. We can obtain a reference to the underlying buffer/array easily,
that is NOT the case with direct type-allocated buffers. The direct buffer types

### Properties

- mark - similar to stream marks, can mark the buffer at a given position and reset to the mark;
- position - the current cursor position into the buffer, moved when doing writes;
- capacity - the total capacity of the buffer or how much data it can actually fit
- limit - the current size of the buffer, always less than then the capacity
- byte[] - hold the reference when the buffer is of heap type;

### Interface

All of them extend off of the abstract Buffer class, all of them share a very common interface that allows the user
to operate with the underlying buffer and data representation. Some of these methods include

- `hasArray()` - checks if the buffer is backed by an accessible array
- `isDirect()` - company method to check if the buffer is a direct one
- `flip()` - prepare the buffer for reading, resets the position to 0
- `mark()` - mark the current position in the buffer, can be restored
- `reset()` - set buffer position to where the mark was last set, if set
- `clear()` - clear the buffer, does only simply rest the buffer cursor
- `remaining()` - calculates how many more elements can be read from the buffer
- `compact()` - copies elements between [pos-limit] to starting from 0
- `limit(int)` - set the current limit of the byte buffer

`Extra care should be taken when calling flip, calling flip again after not having read the entire content, will
shrink the limit of the buffer, if you wish to return back to the start and read again, call rewind, not flip again,
flip can be called again only after we have remaining == 0, that means that we have read until the limit and we can
safely flip again`

### Implementations

1. Abstract classes

- ByteBuffer
- DoubleBuffer
- FloatBuffer
- LongBuffer
- ShortBuffer
- IntBuffer
- CharBuffer

2. Concrete classes

- \[Heap/Direct]ByteBuffer - heap or direct based byte buffer
- \[Heap/Direct]DoubleBuffer - heap or direct based double buffer
- \[Heap/Direct]FloatBuffer - heap or direct based float buffer
- \[Heap/Direct]LongBuffer - heap or direct based long buffer
- \[Heap/Direct]ShortBuffer - heap or direct based short buffer
- \[Heap/Direct]IntBuffer - heap or direct based int buffer
- \[Heap/Direct]CharBuffer - heap or direct based char buffer
- MappedByteBuffer - a direct memory mapping of a file on the file system

## Caveats

- `get vs get(int)` -there are two types of read methods that the buffer implementations usually provide, the get one
  always moves the cursor position forward, unlike the get(int) which allows you to read a random position but does
  not move the internal cursor position of the buffer. This is crucial as it completely changes how you interact with
  the buffer.
- `put vs put(int)` - similarly to the get variations the put method will actually again move the cursor position
  internally for the buffer, while the put(int) will not, this can cause all kinds of bugs and incorrect expectations
  for the buffer's state.
- `hasArray` - tells the client if this buffer is actually backed by an accessible array, this is important if the
  buffer was a direct buffer accessing the array method will return something tat might not be actually accessible to
  the JVM directly as an array reference. One can also consult the `isDirect` method too.
- `clear` - that method on the abstract buffer class does not actually clear the buffer contents, it just reset the
  internal pointer that tells the buffer at which position it is supposed to write to
- `flip` - buffers have to be flipped if they were written to because the internal pointer is moved forward, and
  stay there if you wish to read from the contents of the buffer flip it first then read or consume it.
- `rewind` - this is a company method to flip, after we flip and start reading we actually can not read again from
  the very start, and we should NOT call flip again to do that , what we can do is call rewind that will actually
  reset the cursor position 0
- `compact` - this method can be tricky to understand, what it does it move all bytes from the current cursor
  position up until the limit in the buffer back to the start (left shift) and sets the cursor position to 0.
  Effectively if you had read some amount of bytes and moved the current cursor position, stomp over the read data to
  compact it to the left
