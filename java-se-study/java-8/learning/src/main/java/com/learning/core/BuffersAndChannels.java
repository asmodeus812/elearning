package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
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
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuffersAndChannels {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(BuffersAndChannels.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        // we obtain a reference channel to the stdout handle, this one we need not close, here, since we want the stdout handle to remain
        // open while the program is running we aregoing to use this channel to actually dump data directly to stdout.
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        FileChannel out = fos.getChannel();

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
        URL loggerPropertiesUrl = BuffersAndChannels.class.getResource("/logger.properties");
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

        LOGGER.logInfo("\nReading with scatter from file into stdout: " + loggerPropertiesUrl.getPath());
        try (FileInputStream fis = new FileInputStream(loggerPropertiesUrl.getPath()); FileChannel in = fis.getChannel()) {
            int size = (int) in.size() / 2;
            ByteBuffer dstOne = ByteBuffer.allocateDirect(size);
            ByteBuffer dstTwo = ByteBuffer.allocateDirect(size);
            ByteBuffer[] buffers = {dstOne, dstTwo};
            in.read(buffers);
            buffers[0].flip();
            buffers[1].flip();
            out.write(buffers);
            buffers[0].clear();
            buffers[1].clear();
            LOGGER.logInfo("\nScatter + gather file content to stdout\n");
        }

        Path sparseFileLocationPath = Paths.get("./sparse.txt");
        Files.writeString(sparseFileLocationPath, "starting content\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE);
        LOGGER.logInfo("Initial starting sparse file size: " + Files.size(sparseFileLocationPath));
        try (FileChannel in = FileChannel.open(sparseFileLocationPath, StandardOpenOption.WRITE)) {
            in.position((long) 10 * 1024 * 1024);
            String stringData = "appended this past the end of file";
            ByteBuffer buffer = ByteBuffer.wrap(stringData.getBytes());
            in.write(buffer);
            LOGGER.logInfo("Wrote sparse file past its size: " + in.size());
        }

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
                        FileLock lock = ch.tryLock(start, size, false); // exclusive

                        if (lock != null) {
                            try (lock) {
                                LOGGER.logInfo(name + " acquired lock");

                                ch.position(start);
                                byte[] msg = (name + " wrote here\n").getBytes(StandardCharsets.UTF_8);
                                ch.write(ByteBuffer.wrap(msg, 0, (int) Math.min(msg.length, size)));
                                LOGGER.logInfo(name + " wrote to the file");

                                Thread.sleep(1000);
                            }
                            return;
                        }
                        LOGGER.logInfo(name + " lock busy, retrying...");
                        Thread.sleep(sleepMs);
                    } catch (OverlappingFileLockException ex) {
                        LOGGER.logInfo(name + " lock busy, retrying...");
                        try {
                            Thread.sleep(sleepMs);
                        } catch (InterruptedException e) {
                        }
                    } catch (IOException | InterruptedException ex) {
                        LOGGER.logSevere(ex);
                        return;
                    }
                }
                LOGGER.logInfo(name + " gave up acquiring lock");
            };
            Thread t1 = new Thread(worker, "T1");
            Thread t2 = new Thread(worker, "T2");
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }

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

            byte[] payload = new byte[writeLen];
            for (int i = 0; i < touches; i++) {
                int off = (int) ((size * i) / touches);
                off = (off / page) * page;
                if (off + writeLen > size) {
                    off = (int) (size - writeLen);
                }

                memoryMappedBuffer.get(off); // touch the file page

                String tag = "page#" + i + " off=" + off + "\n";
                byte[] tagBytes = tag.getBytes(StandardCharsets.US_ASCII);
                for (int j = 0; j < writeLen; j++) {
                    payload[j] = 0;
                }
                System.arraycopy(tagBytes, 0, payload, 0, Math.min(tagBytes.length, writeLen));

                memoryMappedBuffer.position(off);
                memoryMappedBuffer.put(payload);

                LOGGER.logInfo("wrote " + writeLen + "B at " + off);
            }
            memoryMappedBuffer.force(); // best-effort flush of just what we changed
        }

        // we wrap a buffer of data to be sent to the client socket, when a connection is established, note that this is just a dummy data.
        // In actual reality you will most likely not want to wrap or create the data buffer like that as it is not efficient.
        ByteBuffer byteBuffer = ByteBuffer.wrap("sending-data-to-socket".getBytes(StandardCharsets.UTF_8));
        // the server socket object is not enough to being to retrieve connections, we need to create and set the socket object internally
        // and set it, that is done by calling the socket method with the bind params, otherwise the socket is not bound when we create the
        // channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(5000));
        serverSocketChannel.configureBlocking(false);

        boolean hasReceivedConnection = false;
        boolean hasReachedConnectionLimit = false;
        while (!hasReceivedConnection || !hasReachedConnectionLimit) {
            SocketChannel socketChannelConnection = serverSocketChannel.accept();

            if (Objects.isNull(socketChannelConnection)) {
                // we have not received any connection but the thread is not blocked waiting for one, that means that here we can do some
                // actual work in our thread wihtout having to worry that we are being blocked
                for (int i = 0; i < 5; i++) {
                    LOGGER.logInfo("Doing work while awaiting socket connection");
                    Thread.sleep(100);
                }
                hasReachedConnectionLimit = true;
            } else {
                // we have received some connection here, meaning that we can now proceed to do something with it maybe
                // there is data to be pulled or sent to or from that socket.
                byteBuffer.rewind();
                socketChannelConnection.write(byteBuffer);
                socketChannelConnection.close();
                LOGGER.logInfo("Wrote data to the socket and closing the connection");
            }
        }
        LOGGER.logInfo("hasReceivedConnection: " + hasReceivedConnection);
        LOGGER.logInfo("hasReachedConnectionLimit: " + hasReachedConnectionLimit);

        fos.close();
        out.close();
    }
}
