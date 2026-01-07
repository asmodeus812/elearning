package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;

public class InputOutputStreams {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(InputOutputStreams.class);

    public static void main(String[] args) {
        // this ensures that the logger is correctly configured to properly print correct format strings, in our case the logger properties
        // ensures that the logger is ocnfigured to avoid printing data on multiple lines and provide short concise format string
        InstanceMessageLogger.configureLogger(InputOutputStreams.class.getResourceAsStream("/logger.properties"));

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
                LOGGER.logSevere(e.getMessage());
            }
        }
    }
}
