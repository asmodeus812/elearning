package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.io.Console;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuffersAndChannels {

    private static final String LOGGER_PROPERTIES = "/logger.properties";
    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(BuffersAndChannels.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        // ensure that the logger is first configured to print abridges messages we do this to ensure that the logger format string does
        // not
        // pollute the stdout with needless noise when printing to stdout
        InstanceMessageLogger.configureLogger(BuffersAndChannels.class.getResourceAsStream(LOGGER_PROPERTIES));

        // we obtain a reference channel to the stdout handle, this one we need not close, here, since we want the stdout handle to remain
        // open while the program is running we aregoing to use this channel to actually dump data directly to stdout.
        try (FileOutputStream fos = new FileOutputStream(FileDescriptor.out); FileChannel out = fos.getChannel()) {
            // transferToAnotherFile(out);
            // scatterFileToChannel(out);
            // touchSparseFileHole(out);
            // lockedFileThreadWrite(out);
            // sparseFilePageWrite(out);
            // serverSocketChannel(out);
            // clientServerConnection(out);
            socketChannelSelectors(out);
        }
    }

    private static void transferToAnotherFile(FileChannel out) throws IOException {
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

        // we put the lower order bytes first, that implies that this is LITTLE_ENDIAN, meaning that when content is read from this bufer it
        // will be read according to its order which we have set to be LITTLE_ENDIAN.
        byteBufferL.put((byte) 'H');
        byteBufferL.put((byte) 0);

        byteBufferL.put((byte) 'I');
        byteBufferL.put((byte) 0);

        byteBufferL.put((byte) '!');
        byteBufferL.put((byte) 0);

        byteBufferL.put((byte) 0);

        // prepare the buffers to be read from after we have written the data to them we have to first flip them, that would reset the
        // positoin to 0, and the limit to 7, that is how many bytes can be read from it.
        byteBufferB.flip();
        byteBufferL.flip();

        // we expect to see the exact same content here from both buffers, HI! in both cases but that shows how we can have different data
        // technically in the buffer (or at least ordered differently) and yet still have the same result
        LOGGER.logInfo("CharBuffer(B): " + byteBufferB.asCharBuffer().asReadOnlyBuffer().toString());
        LOGGER.logInfo("CharBuffer(L): " + byteBufferL.asCharBuffer().asReadOnlyBuffer().toString());

        // demonstrate how we can write to a byte buffer, integer values which are spanning 4 bytes usually, we have allocated
        // 16 bytes, enough for exactly 4 integers, if we try to put a 5th one this implementation will throw exception because
        // of the fact that we overflow the buffer it can not hold more than 4 ints
        byteBufferL = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferL.putInt(5);
        byteBufferL.putInt(15);
        byteBufferL.putInt(25);
        byteBufferL.putInt(35);
        byteBufferL.flip();

        // we convert this byte buffer to a read only integer buffer, that will allow us to read the content as if it were plain
        // integers that were stored in the byte buffer, even though every integer is in essense 4 bytes long.
        IntBuffer asReadOnlyBuffer = byteBufferL.asIntBuffer().asReadOnlyBuffer();
        String intBufferElements = IntStream.range(0, asReadOnlyBuffer.remaining())
                        .map(asReadOnlyBuffer::get)
                        .<String>mapToObj(String::valueOf)
                        .collect(Collectors.joining(","));
        LOGGER.logInfo("IntBuffer(L): " + intBufferElements);

        // obtain a reference path to the log file, we are going to use this file to demonstrate how we can actually move the full content
        // of the file to another file without having to hop into user space in the jvm process
        URL loggerPropertiesUrl = BuffersAndChannels.class.getResource(LOGGER_PROPERTIES);
        LOGGER.logInfo("\nTransfering content from file to stdout: " + loggerPropertiesUrl.getPath());
        try (FileInputStream fis = new FileInputStream(loggerPropertiesUrl.getPath()); FileChannel in = fis.getChannel()) {
            long pos = 0;
            long size = in.size();
            // what is going on here, is we have the file channels, one is the incoming file channel presenting the file data, and the other
            // is the output file channel consuming the data, we use transfer to direclty move data from one file to the other without
            // jumping into JVM user space to create temporary buffers first
            while (pos < size) {
                long n = in.transferTo(pos, size - pos, out);
                if (n < 0) {
                    break;
                }
                pos += n;
            }
            // at this point we should have written the entirety of our input file into the destinatiof
            LOGGER.logInfo("\ntransferTo(): Printed file contents of size: " + out.size());
        }
    }

    private static void scatterFileToChannel(FileChannel out) throws IOException {
        // this example demonstrates how we can read into two buffers at the same time and scatter write the content again at once saving us
        // precious system call overhead, doing instead a vectored read/write using the - readv and writev syscalls
        URL loggerPropertiesUrl = BuffersAndChannels.class.getResource(LOGGER_PROPERTIES);
        LOGGER.logInfo("\nReading with scatter from file into stdout: " + loggerPropertiesUrl.getPath());
        try (FileInputStream fis = new FileInputStream(loggerPropertiesUrl.getPath()); FileChannel in = fis.getChannel()) {
            int size = (int) in.size() / 2;
            // we allocate two buffers that are exactly half the size of the logger file, that means we will sequentiall fill both with the
            // content of the file and then we can write those two same buffers with a single call to the stdout channel that we have opened
            // already
            ByteBuffer dstOne = ByteBuffer.allocateDirect(size);
            ByteBuffer dstTwo = ByteBuffer.allocateDirect(size);
            ByteBuffer[] buffers = {dstOne, dstTwo};

            // write the information of the file back into the buffers, then we flip them and write the same content to stdout channel,
            // effectively transfering data from one file descriptor the file into another which is the stdout (pseudo-file-descriptor)
            in.read(buffers);
            buffers[0].flip();
            buffers[1].flip();
            out.write(buffers);
            buffers[0].clear();
            buffers[1].clear();

            LOGGER.logInfo("\nScatter + gather file content to stdout\n");
        }
    }

    private static void touchSparseFileHole(FileChannel out) throws IOException {
        // create a sparse file, that is going to be used to demonstrate how to write anywhere in a file, even though the file is must
        // smaller than the target location of the write, that will create a sparse file, forcing the operating system to store the file as
        // holes, meaning that the file will actually not take that much space on disk, but it will appear to do so. What the operating
        // system does is immitate that content exists in these file holes, by returning NULL bytes when a space that is empty/hole is read.
        Path sparseFileLocationPath = Paths.get("./sparse.txt");
        Files.writeString(sparseFileLocationPath, "starting content\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE);
        LOGGER.logInfo("Initial starting sparse file size: " + Files.size(sparseFileLocationPath));
        try (FileChannel in = FileChannel.open(sparseFileLocationPath, StandardOpenOption.WRITE)) {
            // open the file for writed and move forward, into the file way past its size to write some data to it, to the client that file
            // will now be at least 10mb big, but to the operating system and the hard drive / storage that file is still a few bytes in
            // side
            in.position((long) 10 * 1024 * 1024);
            String stringData = "appended this past the end of file";
            ByteBuffer buffer = ByteBuffer.wrap(stringData.getBytes());
            in.write(buffer);
            // the size here will reflect not the size on-disk but the size as the operating system supplies it, in this case it will show a
            // size well above 10MB but we know that the file actually is much less than that, it has a few dozen bytes of actual
            // information, the rest is the operating system 'tricking' the user space into seeing more
            LOGGER.logInfo("Wrote sparse file past its size: " + in.size());
        }
    }

    private static void lockedFileThreadWrite(FileChannel out) throws IOException, InterruptedException {
        // we create a file that we want to lock dump some random data into it and allow the threads to read from this file and write but
        // only exclusively, note that we write to the file with the truncate option ensuring we create a fresh-content file every time
        Path lockedFileLocation = Path.of("./lock.txt");
        Files.writeString(lockedFileLocation, "Lorem ipsum dolor sit amet...\n", StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        try (FileChannel ch = FileChannel.open(lockedFileLocation, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            Runnable worker = () -> {
                String name = Thread.currentThread().getName();

                long start = 0;
                long size = 32;
                int attempts = 40;
                long sleepMs = 250;

                for (int i = 1; i <= attempts; i++) {
                    try {
                        // need to remember that this is an exclusive lock, meaning that we are actually asking the operating system to lock
                        // the file for any other process, that is to say any other process attemtping to acquire a lock exclusive or
                        // otherwise will not get it until we release lock here, if we do not get the lock or this method will return null,
                        // we can try a few times with a back-off and bail.
                        FileLock lock = ch.tryLock(start, size, false); // exclusive

                        if (lock != null) {
                            try (lock) {
                                // at this point we have the lock exclusively, that means that we can write to the file and no other process
                                // running on the system can, it is important to note that this lock it put into a try-with-resources, to
                                // release it otherwise we risk leaking the lock and file remaining locked, until the GC collects it
                                LOGGER.logInfo(name + " acquired lock");

                                // put the cursor somewhere in the file and write to the file at that location, in this case we just write
                                // that the thread wrote to the file at that location we put the name of the thread as well so we can
                                // identify which thread acquired the lock and wrote to the file last, overriding the previous data
                                ch.position(start);
                                byte[] msg = (name + " wrote here\n").getBytes(StandardCharsets.UTF_8);
                                ch.write(ByteBuffer.wrap(msg, 0, (int) Math.min(msg.length, size)));
                                LOGGER.logInfo(name + " wrote to the file");

                                // Simulate some heavy duty work being done, keeping the lock for an actual measurable amount of time
                                Thread.sleep(1000);
                            }
                            return;
                        }
                        // on each iteration sleep for a bit before we try to get the lock again, in a real world scenario we will not use
                        // sleep but rahter probably measure with system.time to check how much time has elapsed since our last check, or we
                        // will have a scheduler to do this for us, either way it is a good practice to attempt to acquire a lock more than
                        // once since the file might not be available for writing always
                        LOGGER.logInfo(name + " lock busy, retrying...");
                        Thread.sleep(sleepMs);
                    } catch (OverlappingFileLockException ex) {
                        LOGGER.logInfo(name + " lock busy, retrying...");
                        try {
                            Thread.sleep(sleepMs);
                        } catch (InterruptedException e) {
                            // no needed to be handled here
                        }
                    } catch (InterruptedException e) {
                        // no needed to be handled here
                    } catch (IOException ex) {
                        LOGGER.logSevere(ex);
                    }
                }
                // we have attempted a few time, gave our best the lock can not be acquired, that implies that the file is locked by a
                // process for much longer than expected, at this point we have tried 40*250ms times, that is usually not a good sign if the
                // file is locked for that long, that might imply a leak of a lock.
                LOGGER.logInfo(name + " gave up acquiring lock");
            };

            // start two threads that will attempt to write to a single file, whichever thread acquires the lock last will be the one that
            // will write to the file last, and therefore we will know which thread was that, the information from the preivous thread will
            // be lost
            Thread t1 = new Thread(worker, "T1");
            Thread t2 = new Thread(worker, "T2");

            // start both at the same time, and wait for them to finish, joining the current thread onto them, will ensure that this thread
            // will continue only when these two finish, either giving up on the lock or writing to the file.
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }
    }

    private static void sparseFilePageWrite(FileChannel out) throws IOException {
        // this is another example of writing a very big sparse file in this scenario we will write a file that is about 2GB big, as a
        // sparse file not holding more than a few hundred BYTES, the idea here is that we will touch the start of each file system page,
        // typically spannin 4096 bytes, at the start of each page we write a few bytes, of data and move to the next
        Path veryBigSparseFile = Path.of("./bigfile.dat");
        try (FileChannel ch =
                        FileChannel.open(veryBigSparseFile, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            final int page = 4096; // a typical page size
            int writeLen = 256; // how much we will write
            int touches = 32; // touch 32 memory pages
            long size = ch.size(); // 2 GiB fits fine
            size = Math.min(size, Integer.MAX_VALUE);

            ch.truncate(size); // sparse extension is fine for Linux
            MappedByteBuffer memoryMappedBuffer = ch.map(FileChannel.MapMode.READ_WRITE, 0, size);

            // we will touch in total 32 pages, each 4KB big, on each page we move the file cursor to that offset write some small amount of
            // data no more than a dozen bytes and move to the next page, that immediately means we never fill more than a percent of the
            // page actually
            byte[] payload = new byte[writeLen];
            for (int i = 0; i < touches; i++) {
                int off = (int) ((size * i) / touches);
                off = (off / page) * page;
                if (off + writeLen > size) {
                    off = (int) (size - writeLen);
                }

                // we touch the file page here that is strictly not really needed the write to that page offset below should pull the page
                // into memory too
                memoryMappedBuffer.get(off);

                String tag = "page#" + i + " off=" + off + "\n";
                byte[] tagBytes = tag.getBytes(StandardCharsets.US_ASCII);
                for (int j = 0; j < writeLen; j++) {
                    payload[j] = 0;
                }
                System.arraycopy(tagBytes, 0, payload, 0, Math.min(tagBytes.length, writeLen));

                // move the position of the file cursor and write the data to the page, the page is already in memory but even if it was not
                // it would have been fetched by the OS to execute the write here
                memoryMappedBuffer.position(off);
                memoryMappedBuffer.put(payload);

                // print out at which point of the file and page we are writing data at, as well as the number of the current page being
                // written to
                LOGGER.logInfo("wrote " + writeLen + "B at " + off + " page #" + page);
            }
            memoryMappedBuffer.force(); // best-effort flush of just what we changed
        }
    }

    private static void serverSocketChannel(FileChannel out) throws IOException, InterruptedException {
        // we wrap a buffer of data to be sent to the client socket, when a connection is established, note that this is just a dummy
        // data.
        // In actual reality you will most likely not want to wrap or create the data buffer like that as it is not efficient.
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8);

        // the server socket object is not enough to being to retrieve connections, we need to create and set the socket object
        // internally
        // and set it, that is done by calling the socket method with the bind params, otherwise the socket is not bound when we create
        // the
        // channel
        InetSocketAddress serverInetAddress = new InetSocketAddress("0.0.0.0", 9999);
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(serverInetAddress);
            serverSocketChannel.configureBlocking(false);

            int attemptedConnectionsMade = 0;
            boolean hasReceivedConnection = false;
            boolean hasReachedConnectionLimit = false;
            while (!hasReceivedConnection && !hasReachedConnectionLimit) {
                SocketChannel clientSocketChannelConnection = serverSocketChannel.accept();

                if (Objects.isNull(clientSocketChannelConnection)) {
                    // we have not received any connection but the thread is not blocked waiting for one, that means that here we can do
                    // some actual work in our thread wihtout having to worry that we are being blocked
                    LOGGER.logInfo("Doing work while awaiting socket connection");
                    if (attemptedConnectionsMade++ == 20) {
                        hasReachedConnectionLimit = true;
                    }
                    Thread.sleep(1000);
                } else {
                    // we have received some connection here, meaning that we can now proceed to do something with it maybe
                    // there is data to be pulled or sent to or from that socket.
                    LOGGER.logInfo("Reading data from client connection");
                    while (clientSocketChannelConnection.read(byteBuffer) != -1) {
                        byteBuffer.flip();
                        out.write(byteBuffer);
                        byteBuffer.clear();
                    }
                    clientSocketChannelConnection.close();
                    hasReceivedConnection = true;
                }
            }
            LOGGER.logInfo("hasReceivedConnection: " + hasReceivedConnection);
            LOGGER.logInfo("hasReachedConnectionLimit: " + hasReachedConnectionLimit);
        }
    }

    private static void clientServerConnection(FileChannel out) throws InterruptedException, IOException {
        int attemptedConnectionsMade = 0;
        boolean hasReceivedConnection = false;
        boolean hasReachedConnectionLimit = false;

        SocketChannel clientSocketChannel = null;
        InetSocketAddress serverInetAddress = new InetSocketAddress("0.0.0.0", 9999);

        // we wrap a buffer of data to be sent to the client socket, when a connection is established, note that this is just a dummy
        // data. In actual reality you will most likely not want to wrap or create the data buffer like that as it is not efficient.
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8);
        while (!hasReachedConnectionLimit && !hasReceivedConnection) {
            try {
                clientSocketChannel = SocketChannel.open();
                clientSocketChannel.configureBlocking(false);
                clientSocketChannel.connect(serverInetAddress);

                while (!clientSocketChannel.finishConnect()) {
                    LOGGER.logInfo("Awaiting for connection to be accepted...");
                    LOGGER.logInfo("Doing some work while connecting to server");
                    Thread.sleep(1000);
                }

                LOGGER.logInfo("Reading data from server connection");
                while (clientSocketChannel.read(byteBuffer) != -1) {
                    byteBuffer.flip();
                    out.write(byteBuffer);
                    byteBuffer.clear();
                }
                hasReceivedConnection = true;
            } catch (Exception e) {
                LOGGER.logInfo("Attempting to establish connection....");
                LOGGER.logInfo("Doing some work while waiting for server");
                if (attemptedConnectionsMade++ == 20) {
                    hasReachedConnectionLimit = true;
                }
                Thread.sleep(1000);
            } finally {
                if (!Objects.isNull(clientSocketChannel)) {
                    clientSocketChannel.close();
                }
            }
        }
        // print out the state of the received and connection limits, to know which state we hit after the attempted connects or successful
        // connection
        LOGGER.logInfo("hasReceivedConnection: " + hasReceivedConnection);
        LOGGER.logInfo("hasReachedConnectionLimit: " + hasReachedConnectionLimit);
    }

    private static void socketChannelSelectors(FileChannel out) throws IOException, InterruptedException {
        SocketChannel client1Channel = null;
        SocketChannel client2Channel = null;

        try (Selector selector = Selector.open()) {
            client1Channel = SocketChannel.open();
            client2Channel = SocketChannel.open();

            Console systemConsole = System.console();

            InetSocketAddress serverInetAddress = new InetSocketAddress("0.0.0.0", 9999);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32);

            // ensure that the sockets are not blocking, that is required if we are going to be using channels with selectors, otherwise we
            // will be getting an exception if they are not configured to be non-blocking state
            client1Channel.configureBlocking(false);
            client2Channel.configureBlocking(false);

            // connect to the server, we will not handle the possibility of having the server down or unavailable before this connection
            // establish3ed, assume that the server is up and running for the time being
            boolean connected1 = client2Channel.connect(serverInetAddress);
            boolean connected2 = client1Channel.connect(serverInetAddress);
            int workDoneIterations = 0; // track how much work we are doing

            // register the two socket channels with the selector, this is important to also provide the set of valid operations each
            // selectable channel exposes - for the socket channel these are connect, write and read, by default.
            SelectionKey channel1Key =
                            client1Channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ, "channel-client-1");
            SelectionKey channel2Key =
                            client2Channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ, "channel-client-2");

            // iterate while we break out of this loop which will happen when all the socket clients that we have connected get
            // disconnected, probably with the server going down or disconnecting them forcibly
            while (true) {
                // there are a few ways to select the channels from the selector, either we can use a blocking method, or one that
                // immediately returns the number of available channels - int numberOfKeys = selector.select(250) - waith 250ms,
                // the selectNow version is just retuning immediately without blocking the thread
                int numberOfKeys = selector.selectNow();

                // we have at least one channel that is ready and available for connection
                if (numberOfKeys > 0) {
                    Iterator<SelectionKey> selectedKeysIterator = selector.selectedKeys().iterator();

                    // loop over the selected keys and channels and for each check what state they are ready to accept, connect, write or
                    // read, based on this state we can execute the given action
                    while (selectedKeysIterator.hasNext()) {
                        // get the current selected key but also remove it from the list of selected keys that is important otherwise we
                        // might re-play the same action on the same channel multiple times.
                        SelectionKey selectionKey = selectedKeysIterator.next();
                        selectedKeysIterator.remove();

                        if (!selectionKey.isValid()) {
                            continue;
                        }

                        // for our use-case all channels registered in the selector are of type socket channel, so we can safely cast here
                        // without any concerns
                        SelectableChannel selectableChannel = selectionKey.channel();
                        SocketChannel currentClientChannel = (SocketChannel) selectableChannel;

                        if (!selectableChannel.isOpen()) {
                            continue;
                        }

                        // first we ensure that the socket is connectable if it is we finish the connection to the server, first, then we
                        // can proceed to read/write, there is also not any possiblity that the channel will become read/write available
                        // before we establish the connection
                        if (currentClientChannel.isConnectionPending() && selectionKey.isConnectable()) {
                            // this clal should exit immediatel with true, but it is safer to be in a while loop to ensure that the
                            // connection is finalized, until this method returns it is not safe so write/read from the channel yet
                            try {
                                while (!currentClientChannel.finishConnect()) {
                                    LOGGER.logInfo("Finalizing connection for channel");
                                }
                                // at this point we can read/write from the channel the connection is established between the client channel
                                // and the server
                                LOGGER.logInfo("Connected the client channel to server");
                            } catch (IOException e) {
                                selectionKey.cancel();
                                selectableChannel.close();
                            }
                        }

                        // the channel will become readable when data is sent from the server to which we have connected, that is the only
                        // way we will ever enter this conedtion, and also if the selection key is also configured to accept read operations
                        if (currentClientChannel.isConnected() && selectionKey.isReadable()) {
                            // read what ever was ready from the server into the buffer, flip the buffer write the result directly into the
                            // stdout channel, that should pose minimal overhead to the performance as it implies almost direct transfer
                            // between the two channels through a direct buffer.
                            LOGGER.logInfo("Reading the data from channel: " + selectionKey.attachment());
                            try {
                                if (currentClientChannel.read(byteBuffer) >= 0) {
                                    byteBuffer.flip();
                                    out.write(byteBuffer);
                                    byteBuffer.clear();
                                } else {
                                    selectableChannel.close();
                                    selectionKey.cancel();
                                }
                            } catch (IOException e) {
                                selectionKey.cancel();
                                selectableChannel.close();
                                LOGGER.logInfo("Closing socket channel: " + selectionKey.attachment());
                            }
                        }

                        // similarly to the read, however write operation is controled by US the user of the socket, we know when we need to
                        // write to it and that will be set externally when data is ready to be written, after we write data we remove that
                        // operation from the interested operations
                        if (currentClientChannel.isConnected() && selectionKey.isWritable()) {
                            try {
                                LOGGER.logInfo("Writing the data from channel: " + selectionKey.attachment());
                                String userInputData = systemConsole.readLine("Enter data to send to server: ");
                                // once we have consumed the write operation, we can safely remove it, next time we want to enable the
                                // socket for write, we will add the write action back to the interested operations, usually done from the
                                // outside
                                selectionKey.interestOpsOr(SelectionKey.OP_READ);

                                // write some user specified data, remember that we have to re-set the write action in interested
                                // operations, the next time we want to write to the channel, we remove write since the socket is always
                                // WRITE ready, and we will always enter here in case we do not remove it once we write whatever we have to
                                // write.
                                currentClientChannel.write(ByteBuffer.wrap(userInputData.getBytes()));
                            } catch (IOException e) {
                                selectionKey.cancel();
                                currentClientChannel.close();
                                LOGGER.logInfo("Closing socket channel: " + selectionKey.attachment());
                            }
                        }
                    }
                } else {
                    try {
                        // if we ever enter here that means that the socket channels might have gotten disconnected in this case try to
                        // re-connect to the server, that will also work in case the server comes online later than the program starts
                        if (!client1Channel.isConnected()) {
                            channel1Key.cancel();
                            client1Channel.close();
                            client1Channel = SocketChannel.open();
                            client1Channel.configureBlocking(false);
                            connected1 = client1Channel.connect(serverInetAddress);
                            if (client1Channel.isConnectionPending()) {
                                channel1Key = client1Channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ,
                                                "channel-client-1");
                            }
                        }
                        if (!client2Channel.isConnected()) {
                            channel2Key.cancel();
                            client2Channel.close();
                            client2Channel = SocketChannel.open();
                            client2Channel.configureBlocking(false);
                            connected2 = client2Channel.connect(serverInetAddress);
                            if (client1Channel.isConnectionPending()) {
                                channel2Key = client2Channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ,
                                                "channel-client-2");
                            }
                        }
                    } catch (IOException e) {
                    }
                    // just terminate the while - true loop here to ensure that we do not loop too much while we are going work and there is
                    // no data from the server or in other words there are no selection keys that signal if client channels are ready to
                    // receive/emit data
                    if (workDoneIterations++ > 20) {
                        LOGGER.logInfo("Terminating the selector and client channels");
                        break;
                    }
                    // in the mean time while there are no ready selection keys, we can certainly do some other work in the same thread,
                    // this actually will be the most common block since data is usually received sparingly, and we are free to do important
                    // work on the thread.
                    LOGGER.logInfo("Doing work on the thread awaiting ready channels");
                    Thread.sleep(1000);
                }
            }
        } finally {
            client1Channel.close();
            client2Channel.close();
        }
    }
}
