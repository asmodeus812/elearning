package com.examples.misc;

public class MethodOverloadAction {

    public int intMethod(int k) {
        return 1;
    }

    public int intMethod(Integer k) {
        return 2;
    }

    public int intMethod(double k, int f) {
        return 3;
    }

    public int intMethod(int k, double f) {
        return 5;
    }

    public int intMethod(int k, String o) {
        return 6;
    }

    public int intMethod(double w) {
        return 7;
    }

    public int intMethod(double m, double l) {
        return 8;
    }

    public int intMethod(float m, double l) {
        return 9;
    }

    public int vaMethod(int... v) {
        return 10;
    }

    public int vaMethod(double... v) {
        return 11;
    }

    public int vaMethod(int k, int[] v) {
        return 11;
    }

    public int boxedType(Integer boxed) {
        return 12;
    }

    public int boxedDoubleType(Double boxed) {
        return 13;
    }
}
