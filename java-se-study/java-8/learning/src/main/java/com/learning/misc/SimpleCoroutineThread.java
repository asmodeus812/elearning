package com.learning.misc;

import com.learning.utils.InstanceMessageLogger;

public class SimpleCoroutineThread implements Runnable {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(SimpleCoroutineThread.class);

    private boolean pauseWorkExecution = false;

    private boolean workNeedsDone = true;

    @Override
    public synchronized void run() {
        try {
            LOGGER.logInfo("Coroutine starting work");
            while (workNeedsDone) {
                while (pauseWorkExecution) {
                    wait();
                }
                LOGGER.logInfo("Coroutine executing work");
                wait(1000);
                LOGGER.logInfo("Coroutine yielding control");
                this.pause();
            }
        } catch (InterruptedException e) {
            LOGGER.logWarning(e);
            Thread.currentThread().interrupt();
        }
        LOGGER.logInfo("Coroutine finsihed work");
    }

    public synchronized void kill() {
        this.workNeedsDone = false;
        this.pauseWorkExecution = false;
        this.notifyAll();
    }

    public synchronized void resume() {
        this.pauseWorkExecution = false;
        this.notifyAll();
    }

    public synchronized void pause() {
        this.pauseWorkExecution = true;
        this.notifyAll();
    }

    public synchronized boolean running() {
        return !this.pauseWorkExecution;
    }
}
