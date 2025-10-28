package com.examples.misc;

public interface InterfaceDefaultMethods2 {

    int STATIC_MEMBER_VARIABLE = 5;

    int STATIC_INTERFACE_METHOD_2 = 2;

    static int staticMethod2() {
        return 5;
    }

    default int methodOne() {
        return 2;
    }

    default int methodTwo() {
        return 3;
    }

    int methodNoBody();
}
