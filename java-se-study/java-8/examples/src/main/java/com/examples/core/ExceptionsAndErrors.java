package com.examples.core;

import com.examples.misc.CustomCheckedException;
import com.examples.misc.CustomErrorType;
import com.examples.misc.CustomRuntimeException;
import com.examples.utils.InstanceMessageLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.NonReadableChannelException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import javax.management.ReflectionException;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;

public class ExceptionsAndErrors {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(ExceptionsAndErrors.class);

    // this will produce a compile time error as it is not possible to define generic class that extends off of exception from the java.lang
    // package the reason should be obvious , there is no way for the compiler or the runtime to distinguish between the different types of
    // exceptions in the catch block to determine where to jump off to when an exception is thrown
    public static class GenericExceptionType<T extends Number> extends Exception {

        public GenericExceptionType() {}
    }

    private static void throwChecked() {
        // this will result in an unhandle exception erro by the compiler our custom exception extends off of the Exception class, method
        // MUST have been declared with throws in the method signature
        throw new CustomCheckedException();
    }

    private static void tryWithoutCatchChecked() {
        try {
            // this is also not valid we can not have an empty try without a catch when a checked exception is thrown
            throw new InvalidNameException();
        } finally {
            LOGGER.logInfo("always");
        }
    }

    private static void throwUnchecked() {
        // this is fine because we are throwing an exception that extends off of runtime exception, which is actually caught by default by
        // the java runtime, the compiler can recognize this and handle it accordingly
        throw new CustomRuntimeException();
    }

    private static void tryWithoutCatchUnchecked() {
        try {
            throw new CustomRuntimeException();
        } finally {
            LOGGER.logInfo("always");
        }
    }

    private static void throwError() {
        // this is also valid, will not produce an error, and does not require any explicit throws to be specified in the method signature
        // too, just like RuntimeException
        throw new CustomErrorType();
    }

    private static void tryCatchGenericSuperclass() {
        try {
            // we catch this exception which is a checked one, in this case the immediate block will catch it
            throw new InvalidNameException();
        } catch (Exception e) {
            // logs the exception in this case we should ensure that no information is lost
            LOGGER.logSevere(e);
        } finally {
            // this will always execute no matter what happens or even if the catch itself throws an exception
            LOGGER.logInfo("always");
        }
    }

    private static void tryCatchCheckedHierarchy() {
        try {
            // we catch this exception which is a checked one, in this case the immediate block will catch it
            throw new InvalidNameException();
        } catch (NamingException e) {
            // note that we must always have the catch clause which defines the closest relative to the exception be before the more generic
            // one, here InvalidNameException extends off of NamingException which itself extends off of Exception
            LOGGER.logSevere(NamingException.class.getSimpleName());
        } catch (Exception e) {
            // we catch the "generic" exception last
            LOGGER.logSevere(Exception.class.getSimpleName());
        } finally {
            LOGGER.logInfo("always");
        }
    }

    private static void tryCatchCheckedInvalidHierarchy() {
        try {
            // this is not valid, we have no exception in the catch clause that has overlapping hierarchy with the InvalidNameException,
            // that we are throwing here, if we add a `catch(Exception e)` then the compiler would not error out because that will be the
            // catch block taht matches what we throw
            throw new InvalidNameException();
        } catch (IllegalAccessError e) {
            LOGGER.logSevere(IllegalAccessException.class.getSimpleName());
        }
    }

    private static void tryCheckedWithIncorrectOrder() {
        try {
            // demonstrate that it is not possible to invert the order of catching blocks such that we have the parents before the children,
            // that would produce a compile time error immediately
            throw new InvalidNameException();
        } catch (Exception e) {
            // having code here is perfect valid, but the catch block below would not allow use to compile this program due to the `dead
            // code` problem
        } catch (NamingException e) {
            // compiler error that is dead code we can never reach this point because there is already a catch for a more generic exception
            // from the hierarchy of NamingException, which is the Exception class
        }
    }

    private static void tryCatchCheckedWithPipe() {
        try {
            if (System.currentTimeMillis() > 100) {
                throw new SQLException();
            } else {
                throw new IOException();
            }
        } catch (IOException | SQLException e) {
            // this is valid, both exceptions have no overlapping hierarchy, they are not within the same type hierarchy
            LOGGER.logInfo("specific");
        } catch (Exception e) {
            // this is also valid we catch a generic exception that is the parent of the ones above, but in case something else throws
            // something else that is not IOException or SQLException we can catch it here
            LOGGER.logInfo("generic");
        }
    }

    private static void tryCatchCheckedDeadCodePipe() {
        try {
            // regular exception throwing
            throw new SQLException();
        } catch (SQLException | IOException e) {
            // that is not valid we never throw an IOException from this try block
            LOGGER.logInfo("invalid");
        }
    }

    private static void tryCatchCheckedMultiBlock() {
        try {
            // regular exception throwing
            throw new SQLException();
        } catch (SQLException e) {
            LOGGER.logInfo("target");
        } catch (IOException e) {
            // this will be a dead code block compiler error immediately here, nothing in the try block ever declares that it throws this
            // CHECKED exception
            LOGGER.logInfo("invalid");
        } catch (Exception e) {
            LOGGER.logInfo("generic");
        }
    }

    private static void tryCatchUncheckedCodePipe() {
        try {
            // we throw some random runtime exception, that is never going to be caught because the ones below are not within the type
            // hierarchy of the NonReadableChannelException, there is no compiler warning for dead code, since those are unchecked
            // exceptions we are okay
            throw new NonReadableChannelException();
        } catch (UnsupportedOperationException e) {
            LOGGER.logInfo("never-thrown");
        } catch (InvalidParameterException e) {
            LOGGER.logInfo("never-thrown");
        }
    }

    private static void tryCatchExceptionWithResources() {
        try (InputStream is = new FileInputStream(new File("test.txt"))) {
            // that is only applicable for resources that implement the AutoCloseable interface in this case
            LOGGER.logInfo("handle-file-reading");
        } catch (FileNotFoundException e) {
            // handle the exception here, in this case we can catch all exceptions up the chain of FileNotFoundException which is thrown by
            // the FileInputStream constructor
            LOGGER.logSevere(e);
        } catch (IOException e) {
            // this will catch generic io exceptions that are not FileNotFoundException, since the catch above will handle that, but other
            // IOException might occur, e.g. while reading the file data
            LOGGER.logSevere(e);
        }
    }

    private static void tryCatchExceptionWithResourcesInvalid() {
        // this will produce a compile time error, the resource we are trying to wrap in try_resources block is not actually implementing
        // AutoCloseable interface, neither does the List interface
        try (List<String> list = new ArraysAndLists()) {
        }
    }

    public static void main(String[] args) {
        // note that this method throws checked exception but is never declared that it does, therefore it will result in compiler error
        throwChecked();
        // this method does not define that it throws any exception but the one it throws is of type runtime so even so that is perfectly
        // fine
        throwUnchecked();

        // we can also throw errors, these are still valid but generally not used in production, these are only ever thrown by the runtime
        // and signal very serious unrecoverable error
        throwError();

        // we can certainly catch the exception based on the generic Exception super class directly that would ensure that a very wide group
        // of exceptions is caught but might not always be desireable
        tryCatchGenericSuperclass();

        // we can catch different exceptions based on the hierarchy, going form the closest parent of the thrown exception, to the most
        // distant one, in this case NamingException is a parent of the InvalidNameException, and Exception is a parent of the
        // NamingException, we follow this link
        tryCatchCheckedHierarchy();

        // it is not possible to catch an exception that is not part of the hierarchy, that would produce an error from the compilerk,
        // immediately, it knows at compile time which exception is extending off of which other exception
        tryCatchCheckedInvalidHierarchy();

        // it IS NOT possible to skip the catch clause when there is a checked exception being thrown, otherwise this is a compiler erro
        tryWithoutCatchChecked();

        // it IS possible to throw a runtime exception and skip the catch clause that is valid, and the exception will be caught by the
        // default java runtime exception catcher
        tryWithoutCatchUnchecked();

        // catching with a wrong order parent - child exceptions will trigger the compiler immediately for `dead code` and produce a compile
        // time error
        tryCheckedWithIncorrectOrder();

        // shows that we can chain with pipes multiple exception types as long as they are not forming the same type hierarchy, or have
        // overlapping type hierarchy, i.e one can not be a parent (immediate or distant) of the other. Also the compile will know if we
        // throw these exceptions so if we do not it will produce a compile time error for dead code
        tryCatchCheckedWithPipe();

        // when we try to use the pipe operation with an exception that might not be thrown or declared as being thrown from the try block,
        // we get a dead code path compiler error
        tryCatchCheckedDeadCodePipe();

        // demonstrate that the pipe is mostly syntactic sugar for multi catch blocks nothing special, it works the same way as multiple
        // catch blocks we can just simplify the code branching and reduce the number of catch blocks by using the pipe operator
        tryCatchCheckedMultiBlock();

        // for unchecked, since they are not checked we can define as many catch blocks as we want, the compiler will simply ignore the fact
        // that they might not be declared as being thrown from the try block, since it knows that there is a handler in the runtime so if
        // they ever bubble up to that point they will be caught
        tryCatchUncheckedCodePipe();

        // demonstrate how to use try with resources to avoid having to close the resources in the finally block, only those that are
        // implementing AutoCloseable interface however can be used in a try with resources block
        tryCatchExceptionWithResources();

        // showcase that we can not use just any resource, only those that are implement the AutoCloseable interface must be used, otherwise
        // the compiler can easily catch and detect and throw an error
        tryCatchExceptionWithResourcesInvalid();

        // having a generic exception is not possible that would break the semantics of the catch block, that is why Java does not allow
        // generic exceptions at all
        GenericExceptionType<Integer> genericException = new GenericExceptionType<>();
    }
}
