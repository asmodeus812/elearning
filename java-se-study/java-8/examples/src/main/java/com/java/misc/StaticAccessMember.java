package com.java.misc;

public class StaticAccessMember {

    private final int privateNonStaticMember = 5;

    private static final int privateStaticMember = 12;

    private static final int privateStaticNonInitialized;

    static {
        // this is valid as we are initializing it in the static block this is mostly semantically similar to how we can initialize final
        // members in the constructors of classes, can be useful to run actual code which is not possible in the variable declaration step
        privateStaticNonInitialized = 256 + 12 - 300;
    }

    public static void staticMethod() {
        // using this is not possible in a static context, static members as methods are not tied to a particular 'this' instance of a class
        int f = this.privateNonStaticMember;

        // these are valid usages and would not cause and compile time error when using the fields like so
        int m = privateStaticMember;
        m = StaticAccessMember.privateStaticMember;
    }
}
