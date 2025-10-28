package com.examples.misc;

import com.examples.utils.InstanceMessageLogger;

public class FinalizeAndDestructors {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(FinalizeAndDestructors.class);

    @Override
    protected void finalize() throws Throwable {
        LOGGER.logInfo("Method was finalized");
    }
}
