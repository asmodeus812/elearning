package com.java.misc;

public class UsingThisClass {

    public UsingThisClass(String test) {
        // recursrive constructor error, this is not valid as we are calling the same constructor from within itself, note that the compiler
        // immediately notices that
        this("test");
    }

    public UsingThisClass(double m) {
        // this is possible because we have a constructor that has an integer argument, however if we had not, and only had the double one
        // calling it with 5, will auto promote the type to double and be a compiler error
        this(5);
    }

    public UsingThisClass(int k) {
        // this is invalid, there is no default constructor anymore we have provided several explicit ones, therefore the compiler will
        // complain here too
        this();
    }
}
