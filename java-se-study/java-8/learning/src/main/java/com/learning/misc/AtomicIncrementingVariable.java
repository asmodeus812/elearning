package com.learning.misc;

import com.learning.utils.InstanceMessageLogger;

public class AtomicIncrementingVariable {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(AtomicIncrementingVariable.class);

    private int variable;

    public AtomicIncrementingVariable() {
        this.variable = 0;
    }

    public synchronized int incrementSyncMethod() {
        LOGGER.logInfo("Incrementing inside synchro method");
        this.variable++;
        LOGGER.logInfo(String.format("New value is: %d", this.variable));
        return this.variable;
    }

    public int incrementSyncBlock() {
        synchronized (this) {
            LOGGER.logInfo("Incrementing inside synchro block");
            this.variable++;
            LOGGER.logInfo(String.format("New value is: %d", this.variable));
        }
        LOGGER.logInfo("Execution outside of synchro start");

        LOGGER.logInfo("Execution outside of synchro end");
        synchronized (this) {
            LOGGER.logInfo("Returning value from synchro block");
            return this.variable;
        }
    }

    public int getVariable() {
        return this.variable;
    }
}
