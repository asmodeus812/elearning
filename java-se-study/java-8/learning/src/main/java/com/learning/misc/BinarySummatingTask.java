package com.learning.misc;

import java.util.concurrent.RecursiveTask;

public class BinarySummatingTask extends RecursiveTask<Integer> {

    private final int[] array;

    private final int start;

    private final int end;

    public BinarySummatingTask(int[] array, int start, int end) {
        super();
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int middle = (end - start) / 2;

        if (start >= middle) {
            return 0;
        } else {
            BinarySummatingTask left = new BinarySummatingTask(this.array, start, middle);
            left.fork();

            int sum = 0;
            for (int i = middle; i < end; i++) {
                sum = this.array[i];
            }
            return left.compute() + sum;
        }
    }

}
