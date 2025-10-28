package com.examples.utils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstanceMessageLogger {

    private final Logger logger;

    public InstanceMessageLogger(String className) {
        logger = Logger.getLogger(className);
    }

    public InstanceMessageLogger(Class<?> clazz) {
        logger = Logger.getLogger(clazz.getSimpleName());
    }

    public void logInfo(Object message) {
        logger.log(Level.INFO, getMessage(message));
    }

    public void logWarning(Object message) {
        logger.log(Level.WARNING, getMessage(message));
    }

    public void logSevere(Object message) {
        logger.log(Level.SEVERE, getMessage(message));
    }

    public void logConfig(Object message) {
        logger.log(Level.CONFIG, getMessage(message));
    }

    public void logFine(Object message) {
        logger.log(Level.FINE, getMessage(message));
    }

    public void logFiner(Object message) {
        logger.log(Level.FINER, getMessage(message));
    }

    private static final String getMessage(Object object) {
        return Optional.ofNullable(object).map(Object::toString).orElse("[nil]");
    }
}
