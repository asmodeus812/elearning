package com.learning.misc;

import com.learning.utils.InstanceMessageLogger;

public class MultiThreadeadQueue {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(MultiThreadeadQueue.class);

    private boolean hasValue = false;

    private int queueValue = 1;

    public synchronized void setValue(int value) {
        try {
            while (hasValue) {
                wait();
            }
        } catch (InterruptedException e) {
            LOGGER.logWarning(e);
            Thread.currentThread().interrupt();
        }

        this.hasValue = true;
        this.queueValue = value;
        this.notifyAll();
        LOGGER.logInfo(String.format("Set new value %d", this.queueValue));
    }

    public synchronized int getValue() {

        try {
            while (!hasValue) {
                wait();
            }
        } catch (InterruptedException e) {
            LOGGER.logWarning(e);
            Thread.currentThread().interrupt();
        }

        this.hasValue = false;
        LOGGER.logInfo(String.format("Get last value %d", this.queueValue));
        return this.queueValue;
    }
}
