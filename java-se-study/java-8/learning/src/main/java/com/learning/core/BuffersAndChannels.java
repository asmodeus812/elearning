package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuffersAndChannels {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(BuffersAndChannels.class);

    public static void main(String[] args) throws IOException {
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

        URL loggerPropertiesUrl = BuffersAndChannels.class.getResource("/logger.properties");
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        FileChannel out = fos.getChannel();

        LOGGER.logInfo("Transfering content from file to stdout: " + loggerPropertiesUrl.getPath());
        try (FileInputStream fis = new FileInputStream(loggerPropertiesUrl.getPath()); FileChannel in = fis.getChannel()) {
            long pos = 0;
            long size = in.size();
            while (pos < size) {
                long n = in.transferTo(pos, size - pos, out);
                if (n < 0) {
                    break;
                }
                pos += n;
            }
            LOGGER.logInfo("transferTo(): Printed file contents of size: " + size);
        }

        LOGGER.logInfo("Reading with scatter from file into stdout: " + loggerPropertiesUrl.getPath());
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
            LOGGER.logInfo("Scatter + gather file content to stdout");
        }

        fos.close();
        out.close();
    }
}
