package com.examples.core;

import com.examples.misc.InterfaceDefaultMethods;
import com.examples.misc.InterfaceDefaultMethods2;

public class PackageAndInterfaces {

    // here is a good example, these two interfaces have a default method that has the same signature and name, this forces the compiler to
    // mandate the DEFAULT method to be overridden or else that is a compile time error, in essense the default method becomes a regular
    // interface method without a body, we are required to implement the default method
    private static final class InnerInterfaceImplementor implements InterfaceDefaultMethods, InterfaceDefaultMethods2 {

        @Override
        public int methodNoBody() {
            // a regular interface method without a body that is always required to be overridden, also here we use the opportuinity to show
            // how we can access an ambigious field, in this case both interfaces have the same static field which is named
            // STATIC_MEMBER_VARIABLE, in that case we have to qualify the variable by the name of the interface
            return InterfaceDefaultMethods2.STATIC_MEMBER_VARIABLE;
        }

        @Override
        public int methodOne() {
            // we also have to provide method body for the default method, since both have overlapping signatures and have implementations
            // in both interfaces.
            return 5;
        }
    }

    public static void main(String[] args) {
        // create an instance of our class, that implements the new 2 interfaces, that have overlapping default methods
        InnerInterfaceImplementor implementor = new InnerInterfaceImplementor();
        implementor.methodNoBody();

        // static member variables however are inherited, and we are allowed to use them, in this case we are directly accessing one from
        // the interface
        int k = implementor.STATIC_INTERFACE_METHOD_2;

        // we use the static member by qualifying the name
        int o = InterfaceDefaultMethods2.STATIC_MEMBER_VARIABLE;

        // static methods are not inherited by default from an interface, therefore we are not allowed to use this static method that are
        // defined in these interfaces
        implementor.staticMethod2();

        // package and type shadowing, can occur when we have packaegs, in this case the java.lang, that is imported by default, shadows the
        // type that we have defined in our custom utils package, this can be dangereous and potentially cause errors
        Error error = new Error();
        com.examples.utils.Error error2 = new com.examples.utils.Error();
    }
}
