package com.java.misc;

public class MethodOverloadAction {

    public int intMethod(int k) {
        return 1;
    }

    public int intMethod(double k, int f) {
        return 2;
    }

    public int intMethod(int k, double f) {
        return 2;
    }

    public int intMethod(int k, String o) {
        return 3;
    }

    public int intMethod(double w) {
        return 5;
    }

    public int intMethod(double m, double l) {
        return 6;
    }

    public int intMethod(float m, double l) {
        return 7;
    }

    public int vaMethod(int ...v) {
        return 1;
    }

    public int vaMethod(double ...v) {
        return 1;
    }

    public int vaMethod(int k, int[] v) {
        return 2;
    }
}
