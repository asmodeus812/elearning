package com.examples.misc;

public interface InterfaceDefaultMethods {

    int STATIC_MEMBER_VARIABLE = 5;

    int STATIC_INTERFACE_METHOD_1 = 1;

    static int staticMethod1() {
        return 1;
    }

    default int methodOne() {
        return 1;
    }

    int methodNoBody();
}
