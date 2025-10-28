package com.examples.misc;

public class PrivateConstructorClass {

    private PrivateConstructorClass() {
        // private constructors usually used to prevent instantiation,
    }

    PrivateConstructorClass(int m) {
        // package protected constructor
    }

    public PrivateConstructorClass(double m) {
        // package protected constructor
    }
}
