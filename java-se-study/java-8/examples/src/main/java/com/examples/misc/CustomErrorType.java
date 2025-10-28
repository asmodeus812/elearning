package com.examples.misc;

public class CustomErrorType extends Error {

    public CustomErrorType() {
        super();
    }

    public CustomErrorType(String message) {
        super(message);
    }
}
