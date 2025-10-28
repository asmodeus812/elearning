package com.examples.misc;

public class OverrideObjectMethods extends Object {

    public OverrideObjectMethods() {}

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    // that is not possible since wait and the other wait variants are declared as final, and we know that final methods are not allowed to
    // be overridden.
    public void wait() throws InterruptedException {
        wait(0L);
    }
}
