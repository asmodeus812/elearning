package com.learning.core;

import com.learning.utils.InstanceMessageLogger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsAndCollections {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(StreamsAndCollections.class);

    public static void main(String[] args) throws InterruptedException {
        // ensures that the logger is correctly configured to properly print correct format strings, in our case the logger properties,
        InstanceMessageLogger.configureLogger(StreamsAndCollections.class.getResourceAsStream("/logger.properties"));

        // this example demonstrates how we can use the comparator interface to chain comparison operations by composition, in here we are
        // using the default comparator for the string, which is then converted into the functional interface, and chained with the default
        // methods, which simply wrap around the original method, calling the comparator along side the new lambda
        Comparator<String> comparator = String::compareTo;
        comparator = comparator.thenComparing((a, b) -> {
            return a.length() - b.length();
        });
        // the reversed call simply wraps the call of the comparator to a .negate instead of the plain direct call, negate swaps the
        // arguments
        // before passing them to the call of the initial comparator function reference, that is how the negation is achieved, in the
        // comparator interface
        comparator = comparator.reversed();

        // the simple pipeline, demonstrates how we can apply multiple intermediate operations, such as filter, sorted, and so on, before we
        // actually use a terminal operation to get the results.
        List<String> filteredAndSortedStrings = Stream.of("value1", "value2", "value2longer", "value3", "val3").map(value -> {
            return String.format("[%s]-[%s]", value, value);
        })
                        .parallel()
                        .filter(Predicate.not(Objects::isNull))
                        .filter(Predicate.not(String::isEmpty))
                        .sorted(comparator)
                        .collect(Collectors.toList());

        // the stream pipeline will be triggered once the call to collect is executed, all preivous intermediate operations will be then
        // executed, in order, the stream internally stores them and chain executes them when the terminating operation is
        // detected/executed/attached/registered on the stream object instance
        LOGGER.logInfo("Logging data from the filtered stream");
        for (String var : filteredAndSortedStrings) {
            LOGGER.logInfo(String.format("Stream list value is %s", var));
        }

        // collect a summary statistics for the stream, this demonstrates that a lot of the terminal operations for the stream, especially
        // primtive streams, can be collated into a single shot operation that executes all of those at once, such as average, min, max,
        // sum, etc.
        IntSummaryStatistics summaryStatistics = IntStream.range(0, 100).map(value -> {
            return value * value;
        }).parallel().summaryStatistics();
        LOGGER.logInfo(String.format("Summary statistics for int stream is [%s]", summaryStatistics));

        // demonstrate how we can split the stream into multiple split iterators in our case the split iterator is of primitive type, that
        // is not really that important, but the Spliterator has specialization types for primitives, it is something to keep in mind for
        // the future
        List<Spliterator<Integer>> splitIterators = new ArrayList<>();
        IntStream streamInteger = IntStream.range(0, 100);
        Spliterator<Integer> splitIterator = null;
        while (true) {
            if (splitIterator == null) {
                splitIterator = streamInteger.spliterator();
            } else {
                splitIterator = splitIterator.trySplit();
            }

            if (Objects.isNull(splitIterator)) {
                break;
            }
            splitIterators.add(splitIterator);
        }
        // print how many iterators were actually generated from the splitting, we do this until we can split them, the internal
        // implementation will actually decide when we can no longer split it further
        LOGGER.logInfo(String.format("Integer stream of 100 items was split into [%d] iterators", splitIterators.size()));

        ExecutorService executor = Executors.newFixedThreadPool(splitIterators.size());
        for (int i = 0; i < splitIterators.size(); i++) {
            Spliterator<Integer> spliterator = splitIterators.get(i);
            // this will ensure that we can capture the local variable index, which has to be either effectively final or actually final,
            // effectively final it is not, since the i index mutates between iterations of the loop
            final int index = i;
            executor.execute(() -> {
                // note that the spliterator interface does not have the next/hasNext interface rather it has the interface pattern of
                // tryAdvance, which both checks if we have a next element and advances the pointer, if it has next it will return that
                // element as the result of that function
                spliterator.forEachRemaining(t -> LOGGER.logInfo(String.format("Printing value from iterator [%d]: [%d]", index, t)));
            });
        }
        LOGGER.logInfo("All split iterators submited to the executor service");
        // block the main thread on the executor service to wait for the operation to complete first, to avoid mixing up print statements
        // with operations and examples below after this example here
        executor.awaitTermination(250, TimeUnit.MILLISECONDS);

        // shows how to generate a stream out of a sequence or a seed items, both generate and iterate are used in different situations, the
        // generate method is used when the generator can produce a stateful results, mostly, such that it can produce new result every time
        // it is called, for example using Random and its interface nextInt
        LOGGER.logInfo("generate(1, inf): " + Stream.generate(() -> 1).limit(5).map(String::valueOf).collect(Collectors.joining()));
        // the iterate is more akin a regular loop, where we have control over the starting value then we also have direct control over the
        // limit, in this case it will loop until i <= 10, and for each iteration we increment the previous value by 1 and return it back
        LOGGER.logInfo("iterate(1, i <= 10, ++i): "
                        + Stream.iterate(1, v -> v <= 10, x -> ++x).map(String::valueOf).collect(Collectors.joining()));

        // invoke the any and all match on empty streams that is a bit of an edge case becaues the allMatch will return false, and noneMatch
        // will return true
        LOGGER.logInfo("allMatch(i > 5): " + Stream.<Integer>empty().allMatch(v -> v > 5));
        LOGGER.logInfo("noneMatch(i > 5): " + Stream.<Integer>empty().noneMatch(v -> v > 5));

        // invoke the average method on both empty and non empty streams to demonstrate how the value that can be returned is empty for the
        // optional
        LOGGER.logInfo("average([empty]): " + IntStream.empty().average());
        LOGGER.logInfo("average(1...5): " + IntStream.of(1, 2, 3, 4, 5).average());

        Random random = new Random();
        // natural ordering for int, is going to be used here for compariso, that implies the default implementation for compareTo that is
        // declared and defined inside the Integer class.
        LOGGER.logInfo("sorted(random::nextInt): "
                        + Stream.generate(random::nextInt).limit(10).sorted().map(String::valueOf).collect(Collectors.joining()));

        // force a class cast exception by attempting at sorting for natural order, for a class that does not implement any
        // natural ordering, in this simple example what we do is to simply just pass the logger instance as elements to the
        // underlying stream, since we know that this LOGGER instance does not implement the natural compareTo comparator.
        try {
            LOGGER.logInfo("sorted() + count(): " + Stream.generate(() -> LOGGER).limit(10).sorted().count());
        } catch (Exception e) {
            LOGGER.logSevere("sorted() + count(): " + e);
        }

        // parallel sum a big stream of items, that is going to demonstrate how a stream that was not initially created as a parallel one
        // can be converted to one after the fact. That is quite helpful,
        LOGGER.logInfo("parallel() + sum()" + IntStream.range(1, 2 ^ 16).parallel().sum());
    }
}

