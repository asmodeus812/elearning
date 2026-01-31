package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class InputOutputStreams {

    public static final class MyCustomNonSerializable {

        private Integer value = 2;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(" value: ");
            builder.append(value);
            return builder.toString();
        }
    }

    private static final class MyCustomSerializable implements Serializable {

        private transient MyCustomNonSerializable custom = new MyCustomNonSerializable();

        private transient Integer value = 500;

        private List<String> friends = new ArrayList<>();

        private String name;

        private Integer age;

        private Object surname;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append(" name: ");
            builder.append(name);
            builder.append(" age: ");
            builder.append(age);
            builder.append(" friends: ");
            builder.append(friends);
            builder.append(" surname: ");
            builder.append(surname);
            builder.append(" value: ");
            builder.append(value);
            builder.append(" custom: ");
            builder.append(custom);
            builder.append("]");
            return builder.toString();
        }
    }

    private static final String LOGGER_PROPERTEIS = "/logger.properties";

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(InputOutputStreams.class);

    public static void main(String[] args) throws IOException {
        // this ensures that the logger is correctly configured to properly print correct format strings, in our case the logger properties
        // ensures that the logger is ocnfigured to avoid printing data on multiple lines and provide short concise format string
        InstanceMessageLogger.configureLogger(InputOutputStreams.class.getResourceAsStream(LOGGER_PROPERTEIS));

        // validate that the console is valid, in case the app was not started from an environment that has an stdout handle to which the
        // jvm can attach to, this console reference will be null, Generally that should not really happen but that is just to demonstrate
        // that the API can be finicky, and we have to handle these cases gracefully,
        Optional<Console> console = Optional.ofNullable(System.console());
        if (console.isPresent()) {
            Console terminalConsole = console.get();
            terminalConsole.format("Output redirected to the user console or terminal\n");
            // the example below shows how we can chain and wrap around different readers and streams, in here we are first obtaining a
            // reference to a regular input strea, that is then wrapped into a buffered input stream, which is then converted into a reader,
            // through the input-reader interface, and lastly, that is wrapped into a buffered reader, that is not done in practice but here
            // we demonstrate the flexibility of the API of Java
            try (InputStream resource = InputOutputStreams.class.getResourceAsStream("/logger.properties");
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(resource);
                            Reader inputStreamReader = new InputStreamReader(bufferedInputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
                while (true) {
                    // read from the file line by line, we have wrapped this into buffered readers so when the reader has nothing to read
                    // the read() method will return -1, and thus the line will be null, so we know that when we read null line, we can
                    // safely know that we have reached the end of the file already
                    String line = bufferedReader.readLine();
                    if (line == null || line.isEmpty()) {
                        break;
                    }
                    terminalConsole.format("%s\n", line);
                }
            } catch (Exception e) {
                LOGGER.logSevere(e);
            }
        }

        // attempt to serialize our custom object instance, the object instance that we have created is serializable and we have
        // ensure that all fields are serializable or those that are not are marked as transient
        try (FileOutputStream fos = new FileOutputStream(new File("object.bin")); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            var myCustomObjectInstance = new MyCustomSerializable();
            myCustomObjectInstance.age = 15;
            myCustomObjectInstance.name = "first-name";
            myCustomObjectInstance.surname = "sur-name";
            myCustomObjectInstance.friends.add("my-new-friend");
            myCustomObjectInstance.custom = new MyCustomNonSerializable();
            myCustomObjectInstance.custom.value = 100;
            LOGGER.logInfo("Writing custom object to file");
            oos.writeObject(myCustomObjectInstance);
            oos.flush();
        } catch (IOException exception) {
            LOGGER.logSevere(exception);
        }

        // note that the constructor of this custom object will not actually be run, and on top of that we will actually see
        // that the fields that are not serializable will not be persisted into the file, they are going to remain null, even
        // though they have initializers.
        try (FileInputStream fis = new FileInputStream(new File("object.bin")); ObjectInputStream ois = new ObjectInputStream(fis)) {
            // the class cast not found here might occur, because intenrally the class loader tries to load the class target for this data,
            // and if it was not found then it will certainly throw exception.
            MyCustomSerializable myObject = (MyCustomSerializable) ois.readObject();
            LOGGER.logInfo("MyCustomSerializable: " + myObject);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.logSevere(e);
        }

        // create a new buffer that is going to be used to be filled with a new data, the buffers are filled with randomly
        // generated bytes, and are used to read from the buffered input stream that wraps around the byte input stream. Do use the allocate
        // not the allocateDirect because we want to allocate user spaced array buffer to be used in the program
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Random random = new Random();
        // fill the byte array with some ranodm bytes generated by the random generator, fill the entire buffer with random bytes first then
        // wrap it into stream
        random.nextBytes(buffer.array());
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
                        BufferedInputStream bufferdInputStream = new BufferedInputStream(byteArrayInputStream)) {
            // read all the bytes from the buffer at once, which read method we use does not amtter that much because internally the data is
            // buffered into the internal buffer that the buffered input stream.
            LOGGER.logInfo(Arrays.toString(bufferdInputStream.readAllBytes()));

            // what we can do is also convert the byte buffer of random bytes to a UTF-8 encoded string, while that will not makee much
            // sense we can actually see what typle of garbage that is going to print
            // like so creating a new string instance from the bytes that are contained in the buffer, by default java will attempt to
            // decode this as Charset.defaultCharset(), new String(bufferdInputStream.readAllBytes())
        } catch (Exception e) {
            LOGGER.logSevere(e);
        }

        URL uriToLoggerFile = InputOutputStreams.class.getResource("/logger.properties");
        Path pathToLoggerFile = Path.of(uriToLoggerFile.getPath());
        try {
            // note that the push back reader here is constructed with an argument of 4, those are the number of chars we are allowed to
            // pushback at one go, at most, with multiple calls to unread(char) or single call to unread(char[]), but the general gist of
            // this is that the pushback buffer by default is constructed with a capacity to pushback only a single char back, we need 4
            // because our byte order mark is 4 chars long.
            String stringContentToReadFrom = Files.readString(pathToLoggerFile);
            try (StringReader reader = new StringReader(stringContentToReadFrom);
                            PushbackReader pushbackReader = new PushbackReader(reader, 4)) {
                int current;

                // we use a char array to read the imaginary byte order mark from here, the idea is that we read at most 4 chars and then we
                // compare them to a byte order mark if that does not match then we un-read them and read the content of the push back
                // reader from the start
                char[] byteOrderMark = new char[4];
                pushbackReader.read(byteOrderMark);
                if (new String(byteOrderMark).equalsIgnoreCase("ASDF")) {
                    // we have read a byte order mark, in this example it is just some gibberish that we use to mimick how a byte order mark
                    // header from a file might be read, you can do something here with the rest of the content, knowing tha the byte order
                    // mark matches. Do something else here with the byte order mark matching
                    LOGGER.logInfo("The byte order mark matches: " + Arrays.toString(byteOrderMark));
                } else {
                    // the byte order mark did not match the expectations, we write back the same characters that we read from the reader,
                    // so we can read them agian into the actual final content to be printed.
                    pushbackReader.unread(byteOrderMark);
                    // we read the entire content from the start, since we have pushed back the byte order mark into the pushback reader,
                    // this
                    // will allow us to read the complete content
                    StringBuilder resultString = new StringBuilder(64);
                    while ((current = pushbackReader.read()) != -1) {
                        resultString.append(Character.valueOf((char) current));
                    }
                    LOGGER.logInfo(resultString);
                }
            } catch (Exception e) {
                LOGGER.logSevere(e);
            }
        } catch (IOException e) {
            LOGGER.logSevere(e);
        }
    }
}
