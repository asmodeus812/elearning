# Throwable

This is the top level class which is used in pretty much all exception handling and error handling in the Java language,
used by the `JVM` itself, and all exceptions - checked and unchecked extend off of it. Further more the special type of
Error, which is mostly used by the JVM, internally, is also extending off of it.

Every thrown object from java Code must be a subclass of the `Throwable` class, or one of its sub-classes, exception
handling constructs in the language such as throw statements, throws clause, and the catch clause, deal only with
`Throwable` and its subclasses. There are three important subclasses of `Throwable` - `Error`, `Exception` and
`RuntimeException`.

## Exceptions
