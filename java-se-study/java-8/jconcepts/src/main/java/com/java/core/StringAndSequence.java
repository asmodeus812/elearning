package com.java.core;

public class StringAndSequence {

    static void vamethod(String n, int... v) {
    }

    public static void main(String[] args) {
        String myString1 = "this is a test"; // string is interned by the java runtime
        String myString2 = new String("this is a test"); // string is dynamically created, even though it is 'technically the same'
        vamethod("", new int[]{1, 2, 3});
        return;
    }
}
