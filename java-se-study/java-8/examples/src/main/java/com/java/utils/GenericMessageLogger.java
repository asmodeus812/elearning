package com.java.utils;

public class GenericMessageLogger {

    public static final InstanceMessageLogger LOGGER_INSTANCE = new InstanceMessageLogger(GenericMessageLogger.class);

    private GenericMessageLogger() {
        // Prevent class instantiation
    }

    public static void logInfo(String message) {
        LOGGER_INSTANCE.logInfo(message);
    }

    public static void logWarning(String message) {
        LOGGER_INSTANCE.logWarning(message);
    }

    public static void logSevere(String message) {
        LOGGER_INSTANCE.logSevere(message);
    }
}
