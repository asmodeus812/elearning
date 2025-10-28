package com.learning.core;

import com.learning.misc.AtomicIncrementingVariable;
import com.learning.misc.BinarySummatingTask;
import com.learning.misc.MultiThreadeadQueue;
import com.learning.misc.SimpleCoroutineThread;
import com.learning.utils.InstanceMessageLogger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadingAndProcessing {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(ThreadingAndProcessing.class);

    /**
     * This class is meant to represent a consume of the queue, it will be responsible for pulling data from the queue
     */
    public static class Consumer implements Runnable {

        private boolean running = true;

        private final MultiThreadeadQueue queue;

        public Consumer(MultiThreadeadQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (running) {
                queue.getValue();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // not needed to catch here
                }
            }
        }
    }

    /**
     * This class is meant to be a producer for the queue, it will be responsible for putting data into the queue
     */
    public static class Producer implements Runnable {

        private boolean running = true;

        private int value = 1;

        private final MultiThreadeadQueue queue;

        public Producer(MultiThreadeadQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (running) {
                queue.setValue(value++);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // not needed to catch here
                }
            }
        }
    }

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR =
                    new ThreadPoolExecutor(5, 10, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5));

    private static final ExecutorService FIXED_THREAD_EXECUTOR = Executors.newFixedThreadPool(5);

    private static final ForkJoinPool WORK_STEALING_POOL = new ForkJoinPool(5);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        AtomicIncrementingVariable atomicVariable = new AtomicIncrementingVariable();

        THREAD_POOL_EXECUTOR.execute(atomicVariable::incrementSyncBlock);
        THREAD_POOL_EXECUTOR.execute(atomicVariable::incrementSyncMethod);

        Future<Integer> future = WORK_STEALING_POOL.submit(atomicVariable::incrementSyncMethod);
        LOGGER.logInfo(String.format("Result from the future is %d", future.get()));

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BinarySummatingTask summer = new BinarySummatingTask(array, 0, array.length);
        WORK_STEALING_POOL.execute(summer);

        // we craete the shared monitor object, that will be use across the consumer and the producer, this is simply a monitor wrapper that
        // tells our conumer and producer when / what conditions do we enter on in the different scenarios
        MultiThreadeadQueue queue = new MultiThreadeadQueue();

        // both the consumer and the producer lock and sync around the same monitor object, that in this case is the quee object which has
        // its sync methods called by both the consumer and the producer, this ensures that if one thread produces the other will wait until
        // there is something to consume from the queue
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);

        // register each consumer and producer as a runnable, and create a thread from them, this ensures that the thread can then be
        // started and the runnables run
        Thread consumerThread = new Thread(consumer);
        Thread producerThread = new Thread(producer);

        // starting both threads, they will alternate between putting data into the queue and pulling data from the queue, this is ensured
        // by the fact that the threads will wait until the given conditions are met, in this case that means when consuming the thread will
        // wait until there is something to consume and the same process is valid for producing thread will wait until there is space to
        // produce, that happens only when the consumer has pulled data from the queue
        consumerThread.start();
        producerThread.start();

        // this example is very similar to the one above, we again have two threads, that ping-pong the monitor between each other, the
        // monitor being the co routine, here though, the monitor itself, not only does the work but also pauses itself when some work is
        // done, and the other thread, takes care of resuming the monitor state in this case the co-routine
        SimpleCoroutineThread coRoutine = new SimpleCoroutineThread();
        Thread coThread = new Thread(coRoutine);
        Thread mainThread = new Thread(() -> {
            // this simulates a main thread that runs constantly, and pauses itself to allow the co-routine to run for a while, then gets
            // control back from the co-routine and resumes it when it does some work
            synchronized (coRoutine) {
                try {
                    // we run constatnly simulating a main thread behavior, here
                    while (true) {
                        // while the co-routine is doing work we pause the main thread
                        while (coRoutine.running()) {
                            coRoutine.wait();
                        }
                        // here the main thread executes some long running task and it decides when or if to resume the co-routine now,
                        LOGGER.logInfo("Main thread doing actual work");
                        Thread.sleep(2000);
                        // we resume the routine, notice that here the main thread makes the decision control that it should resume the
                        // co-routine, allowing it to keep the routine in a paused state for however long it wants
                        LOGGER.logInfo("Main thread resumed coroutine");
                        coRoutine.resume();
                    }
                } catch (InterruptedException e) {
                    LOGGER.logWarning("Main thread interrupted");
                    Thread.currentThread().interrupt();
                }
            }
        });
        // start both threads, in the real world scenario that main thread would already be actually running, and we will be creating co
        // routines as the program progresses in its execution
        mainThread.start();
        coThread.start();
    }
}
