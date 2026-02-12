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
- `compact()` - copies elements between [pos-limit] to be starting from 0
- `limit(int)` - set the current size (content) of the byte buffer
- `asReadOnlyBuffer` - convert the buffer to read-only wrapper instance
- `asXxxBuffer` - convert the buffer to a primitive based wrapper

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
- MappedByteBuffer - direct memory mapping buffer of a file on the file system

## Channels

Channels are one of the new primary structures in the new input/output API. Channels are a new alternative way to
communicate with I/O devices and any kind of native operating system structures such as files and sockets etc.

### Properties

### Interface

- `isOpen()` - check if the channel is open - generic method for channels, has different implications based on the
  channel type
- `size()` - used with file channels, represents the size of the underlying file resource, that is mostly relevant for
  files, and has not much meaning for other types of sockets
- `connect(addr)` - used to connect to an `InetSocketAddress` address, with `SocketChannel`, connect can fail, non blocking,
  invalidates the state of the socket instance
- `bind(addr)` - used with to bind to an `InetSocketAddress` address, with `ServerSocketChannl`, bind can fail, non
  blocking, invalidates the state of the socket instance
- `map(...)` - used with `MappedByteBuffer`, to create memory mapping of a file on the file system, not capable of mapping
  more than Integer.MAX_VALUE bytes
- `write(buffer)` - write the byte (direct/heap) buffer to the channel, might not write the entirety of the buffer,
  write until EOF or 0 is reached
- `read(buffer)` - read into the target byte (direct/heap) buffer, might not read the entirety of the buffer, read until
  EOF or 0 is reached

### Implementations

1. Abstract classes / Interfaces

- SelectableChannel
- ByteChannel
- FileChannel
- SocketChannel
- NetworkChannel
- WritableByteChannel
- ReadableByteChannel
- ScatteringByteChannel
- GatheringByteChannel

`Read and write operations might not read or write the complete data from/to the buffer therefore it is important to
keep read/write until these methods return EOF (-1) or 0, otherwise the read/write might be incomplete. Certain types of
channels such as the SocketChannel, become invalid after failing to connect/bind, therefore it is safer to close and
re-open them instead of trying to re-use them`

## Selectors

The final cornerstone of the new input output, is the selector, the selector is the glue that allows us to read / write
or otherwise interact with these I/O structures without blocking the current thread. As we have seen a number of methods
from channels already are non-blocking. Selectors allow us to provide more control over when/what actions we can perform
on these channels.

### Properties

- keys - each selector holds a bucket of keys that transition between 3 states, registered, ready, canceled. When a
  channel is first attached to a selector the key is registered but not active, it becomes active when an operation that
  the channel is configured with becomes available - read, write, connect etc. When an operation becomes valid the
  selector key can be pulled from the selector and we can act upon the channel linked to that selector key. Each selector
  key exposes methods to check if the given action is currently available like - read,write,connect,accept

- ops - operations are the different actions a selection key watches for. For example when data becomes available on a
  channel, the read operation will become active on the key and the `isReadable` method will return true, we can then pull
  the data from the channel. Desired or interested operations can be changed on a channel, we can always change which ops
  we are interested in receiving updates for by setting them on the selection key.

- cancelation - each key can be canceled, once canceled the underlying channel is no longer registered with that
  selector, however that does not close the channel itself, as it remains open until we explicitly close it ourselves,
  that is because selectors have no control over the lifecycle and because a channel can be registered with multiple
  selectors

### Interface

- `register(channel, ops)` - register a channel that has to have been configured as non-blocking before hand already,
  using the method configureBlocking(false), otherwise this register method will throw an exception
- `selectNow` - returns in a non blocking way the number of keys that are available/ready for consumption for this
  selector, combine with a selectedKeys to extract these keys as a Set, for which we can obtain an iterator over which to
  loop over
- `select` - blocking call that does not return until there are at least one key that is ready for consumption, combine
  wit selectedKeys, to extract these keys as a Set, for which we can obtain an iterator over which we can loop over
- `selectedKeys` - returns the set of currently selected keys ready to be consumed, loop over them pull a key from the
  set, and process it, check if valid and remove it from the set once done with it.

`When using selectedKeys, it is important to - obtain the key, wit it.next, check if the key is valid, and then remove
it from the set, easiest is to just call it.remove, if we do not remove the key from the selected keys set, then the key
will be considered un-consumed and it will be returned every time by the selector until we remove it from the set.`

### Implementations

- Selector
- SelectionKey
- SelectableChannel

## Caveats

- `get vs get(int)` -there are two types of read methods that the buffer implementations usually provide, the get one
  always moves the cursor position forward, unlike the get(int) which allows you to read a random position but does
  not move the internal cursor position of the buffer. This is crucial as it completely changes how you interact with
  the buffer.
- `put vs put(int)` - similarly to the get variations the put method will actually again move the cursor position
  internally for the buffer, while the put(int) will not, this can cause all kinds of bugs and incorrect expectations
  for the buffer's state.
- `buf.hasArray()` - tells the client if this buffer is actually backed by an accessible array, this is important if the
  buffer was a direct buffer accessing the array method will return something tat might not be actually accessible to the
  JVM directly as an array reference. One can also consult the `isDirect` method too.
- `buf.clear()` - that method on the abstract buffer class does not actually clear the buffer contents, it just reset
  the internal pointer that tells the buffer at which position it is supposed to write to
- `buf.flip()` - buffers have to be flipped if they were written to because the internal pointer is moved forward, and
  stay there if you wish to read from the contents of the buffer flip it first then read or consume it.
- `buf.rewind()` - this is a company method to flip, after we flip and start reading we actually can not re-read again
  from the very start, and we should NOT call flip again to do that , what we can do is call rewind that will actually
  reset the cursor position 0
- `buf.compact()` - this method can be tricky to understand, what it does it move all bytes from the current cursor
  position up until the limit in the buffer back to the start (left shift) and sets the cursor position to 0. Effectively
  if you had read some amount of bytes and moved the current cursor position, stomp over the read data to compact it to
  the left
- `buf.map(...)` - when memory mapping a file as a `MemoryMappedBuffer`, we can at most map 2^31, Integer.MAX_VALUE,
  that is because the method accepts integer as type, create multiple memory mapping buffer, with different offsets to
  cover a bigger span/range
- `chan.isOpen()` - socket channels remain open even after the connection fails, therefore it is better to re-create the
  instance with a new one rather than re-use existing one to try to re-connect or re-bind if these calls failed.
- `chan.size()`- returns the size of the underlying channel, for files that might mean the size might represent a sparse
  file, a sparse file is such that has 'file holes' meaning that the size of the file reported by the file system might
  not be representative of the actual size of the file on disk
- `chan.write/read(buf)` are always blocking, they return the number of bytes that were read or written use them in a
  loop to write/read into buffer until we receive 0 or -1, they might throw and that should be handled gracefully
- `chan.read/write(buf)` - may write fewer bytes than remaining, that is because the receiving side may not immediately
  accept the entirety of our buffer, we have to keep a loop over the buffer until `!buf.hasRemaining()`.
- accept() - for server socket channel is a non blocking, it returns null if no socket connection can be established or
  is incoming after calling this method
- `chan.connect()` - for client socket channel is non blocking, returns the connect status, always false if the socket
  is configured to be non blocking with `configureBlocking(false)` it should be combined with `isConnectionPending` and
  call to check if/while `finishConnect` returns true, on a socket to finalize the connection, returns
- `chan.validOps()` - method from `SelectableChannel` that exposes all valid operations a channel can have - read,
  write, connect, accept etc, based on the channel implementation - `SocketChannel`, `ServerSocketChannl`, etc.
- `selector.register(channel, actions)` - register a channel to a selector returns a `SelectionKey` that can be used to
  control the state of the channel, a key can be canceled, effectively removing it from the selector, must use the method
  to ensure channel is configured as NON-BLOCKING - `configureBlocking(false)` on the channel
- `selector.selectNow()` - non-blocking returns the number of keys ready to be selected over, combine with usage of the
  selector's selectedKeys method that will return the list of current selected keys over which we can loop over
- `selector.selectedKeys()`- obtain iterator from the selected keys for each of the keys in the iterator, pull a key
  with iterator.next, and always remove keys with iterator.remove, that have been processed, combine with key.isValid,
  key.isReadable, key.isWritable to check on the status of the registered channel for this key
- `key.channel()` - the registered channel can be obtained from the key, we have to cast to the concrete type to use, as
  it returns a `SelectableChannel` interface.
- `key.cancel()` - cancel a key, invalidating it, usually combined with the actual removal of the key, when an exception
  in the channel occurs that might force us to close or throw away the channel instance, effectively that de-registers the
  channel from the selector
- `key.interestOps(ops)` - set the new operations we are interested in, this does OR operation with the current
  actions/operations, therefore it accumulative to remove interested operations use XOR against the current ops like so
  `ops = key.interestOps(); ops &= ~SelectionKey.OP_WRITE`, then set them back `key.interestOps(ops)`
