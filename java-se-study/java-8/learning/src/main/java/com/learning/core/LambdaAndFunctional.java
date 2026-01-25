package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaAndFunctional {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(LambdaAndFunctional.class);

    public interface ConsumerThatThrowsException<T> {

        void accept(T t) throws Exception;
    }

    public static void main(String[] args) throws Exception {
        // configure the logger formatting rules format specifiers, to ensure that the logs are printed in a more concise way
        InstanceMessageLogger.configureLogger(LambdaAndFunctional.class.getResourceAsStream("/logger.properties"));

        // the predicate here is a reference of the string method isEmpty, however isEmpty is an instance method, it implicitly accepts the
        // this reference as its first argument therefore the signature is actually boolean isEmpty(String), therefore that matches the
        // signature of the test method in the predicate functional interface, which is boolean test(T). Notice how we can use / reference
        // instance methods from the static context of the class type in this case String
        String literalStringValue = "literal-value";
        Predicate<String> predicate = String::isEmpty;
        LOGGER.logInfo(predicate.test(literalStringValue));

        // here we are doing the inverse of the example above, so we are referencing, the method from the instance / object, what does this
        // do ? Well this will implicitly pass the reference, of the object/instance directly as first argument to this function, observe.
        // Notice that the equals reference here is bound to the instance, and the actual argument that we call the predicate with is the
        // actual argument of the instance equals method, so we are doing the equivalent of -
        // `literalStringValue.equals(literalStringValue2)`
        String literalStringValue2 = "literal-value2";
        Predicate<String> predicateTestEqualsBound = literalStringValue::equals;
        LOGGER.logInfo(predicateTestEqualsBound.test(literalStringValue2));

        // here we can see how a method binding to an instance variable works, the supplier signature takes no arguments, and length does
        // not as well since it is an instance method. That instance method signature looks like this - int String.length(String this), we
        // have an implicit `this` instance argumetn, the method reference above binds the length call to the instance variable
        // literalStringValue
        IntSupplier lengthSupplier = literalStringValue::length;
        LOGGER.logInfo(lengthSupplier.getAsInt());

        // here is an example of using an instance method referenced thorugh the type, in this case, we have to pass two arguments, the
        // actual instance implicit this argument, and the argument to the function, so here equals is an instance method, takes, one
        // argument by default the object to compare/equal against, but we must not forget the implicit `this` reference since the method is
        // not a static one
        BiPredicate<String, String> predicateTestEqualsStatic = String::equals;
        LOGGER.logInfo(predicateTestEqualsStatic.test(literalStringValue, literalStringValue2));

        Stream<String> stringStreamValues = Stream.of("value1", "value2", "value2longer", "value3", "val3", "");
        // the filter method takes in a predicate, we know that the predicate has a test method with one argument, here type of the argument
        // is a string, we can reference the instance method isEmpty, through the type, that will generate a reference that has implicit
        // `this` first argument, which matches the signature of the predicate functional interface perfectly. We are using the inverted
        // .not from the Predicate interface to invert the result of the method lambda reference, that is not really doing anything special
        // it returns the same signature just wraps around the call of the source and inverts the result to give us a stream of non-emtpy
        // strings
        Stream<String> filteredNonEmptyStrings = stringStreamValues.filter(Predicate.not(String::isEmpty));

        // another method reference that might be not as obvious, what is going on here, the stream map method takes a Function<T, T>, where
        // the T is of type String for this particular stream, so what happens, is that String::toUpperCase is an instance method of
        // String,therefore the signature implcitly looks like this static String toUpperCase(String this), or if you take a look at the
        // String
        // file you will see that the method is an instance method, that takes no additional arguments and returns the upper cased string -
        // String toUpperCase(), perfect fit for map combined with instance reference binding.
        Stream<String> mappedUpperCaseStrings = filteredNonEmptyStrings.map(String::toUpperCase);

        // here is an even more complex example, why does it work ? Reduce takes an accumulator, that accepts two arguments of the type of
        // the Stream, String, and returns a single result of the same type, String. The concat method is an instance method, therefore
        // implicitly it looks like this `String concat(String this, String other)`, we can see that it looks exactly like what the binary
        // operation in reduce requires, it is basically a `BiFunction<T,T,T>`, where T in our case is String.
        String string = mappedUpperCaseStrings.reduce("", String::concat);
        LOGGER.logInfo(string);

        // here is a more complex reducer, where we change the identity result of the reduce operation, so in this case we use a string
        // builder as the result of the reduction, creating a new base instance of it, and using the append to append the String entries to
        // the string builder. This version of reduce takes as second argument `U (U, T)`, where U is our changed identity, which is
        // StringBuilder, and T is the source type of the stream, which is String. Append method of StringBuilder, has the same signature,
        // it is an instance method, that looks like this `StringBuilder append(StringBuilder this, String toAppend)`. We can see that this
        // is the same signature, when we infer the implicit `this` argument. The third argument of the reduce in this case is not that
        // important, it is a binary operator that looks like this - T (T, T) basically it is used when the reducer is used for parallel
        // streams, to combine the results of parallel reduction operations, here we pass append again, append is overloaded and has a
        // method that accepts `StringBuilder append(StringBuilder this, StringBuilder toAppend)`, we are not using a parallel stream, but
        // still have to provide a valid method reference there.
        Stream<String> stringSequenceNumbers = Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        StringBuilder stringBuilderAppendStreamConstructor =
                        stringSequenceNumbers.reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append);
        LOGGER.logInfo(stringBuilderAppendStreamConstructor);

        // here we can also see how to flatten a stream that consists of another collection, the stream below is a stream of collections
        // that are flattened into a single sequence structure that contains all elements as they occur in the original order. In this case
        // 0,1,2,3,4. Then we are joining them with the collectors default joiner, that is basically a fancy reduce operatin that concats
        // entries and puts a delimiter between them but made more efficient.
        Stream<List<String>> nestedStreamListValues = Stream.of(List.of("0"), List.of("1"), List.of("2"), List.of("3"), List.of("4"));
        Stream<String> flattenedStreamListValues = nestedStreamListValues.flatMap(List::stream);
        Stream<String> filteredStreamListValues = flattenedStreamListValues.filter(Predicate.not(String::isEmpty));
        LOGGER.logInfo(filteredStreamListValues.collect(Collectors.joining(",")));

        // this is a compile time error, had we used the regular Consumer functional interfac here instead, even though we define the lambda
        // correctly, the wait here throws an exception, therefore we have to use lambda signature that also throws an exception. We have to
        // remember that the method signatures must match exactly, that also includes the throws declaration as well.
        ConsumerThatThrowsException<Object> consumer = Object::wait;
        LOGGER.logInfo("Calling accept method on our custom consumer that may throw exception");
        consumer.accept(literalStringValue);
    }
}
