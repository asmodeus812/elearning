package com.examples.core;

import com.examples.misc.MethodOverloadAction;

public class MethodsAndFunctions {

    public static void main(String[] args) {
        // this class demonstrates how differnt overloads of the same method can be used to call different methods, also demosntrates how
        // auto-boxing can be leveraged to help us write more re-usable code without having to provide multiple versions of the same method
        // with boxed and unboxed types
        MethodOverloadAction overloaded = new MethodOverloadAction();

        // call the method with a single integer, that is valid there is a method that accepts a single integer, even though there is
        // another method that takes the boxed type Integer, we are still going to call the one that is using the primitive, and that is not
        // a conflict
        overloaded.intMethod(5);

        // this method call is using the boxed type as its only argument and we are passing a primitive, the compiler will recognize this
        // and ensure that the parameter is boxed
        overloaded.boxedType(5);

        // another example of boxing, however here the java compiler will box the integer into a double because that is the only matching
        // signature for this method, in this case this will call the method as if double boxed type was provided
        overloaded.boxedDoubleType(5);

        // here the samemethod will also be called in this case we are ourseelves boxing the value into adouble ,this can be useful if we
        // wish toprovide more precision for the value
        overloaded.boxedDoubleType(Double.vaueOf(5));

        // this is not valid or at the very least ambigious, why ? because we have two methods with two similar signatures (double, int) and
        // (int, double) the automatic type promotion can not know which parameter to auto-promote to match only one unique method signature
        overloaded.intMethod(5, 10);

        // however unlike the preivous example we auto-promote the 10 into a double this way we know that the we intend on calling the (int,
        // double) version of the overloaded method there is no ambiguity at all
        overloaded.intMethod(5, 10.0);

        // we can also directly call the variant that is (double, double), by default all floating point numbers in java are double unless
        // specified otherwise
        overloaded.intMethod(5.0, 10.0);

        // a tricky one, this will actually auto-promote that 10.0f into a double, and we will still be calling the method variant that is
        // (float, double)
        overloaded.intMethod(5.0f, 10.0f);

        // a tricky one again, this is the variant of (double, double), this is because here the 10.0f will be auto-promoted to double to
        // match the closest signature
        overloaded.intMethod(5.0, 10.0f);

        // tricky one, we have (int ...) and (int, int[]), might seem strange but the compiler can distinguish them, based on the fact that
        // one
        // takes one additional single int argument first, and then the int[]
        overloaded.vaMethod(5, 5, 5);
        overloaded.vaMethod(5, new int[] {5, 5});

        // this one will auto-promote all arguments to double since we have only one method that takes (double ...), therefore all arguments
        // will become double, the longs, the float, the integer etc.
        overloaded.vaMethod(5.0, 500000, 5.0f, 127, 5L);

        // since byte can be from -128 to 127, we can assign this literal to the byte at compile time, this will ensure that the compiler
        // does not mess with us
        byte validLessThanRange = 127;

        // 128 can not fit into a single signed byte value, it will overflow, therefore the compiler will complain here and throw a compiler
        // error
        byte invalidOverflowing = 128;

        // we can not down cast a value from a higher byte order to a lower one without an explicit cast, this is a compile time error
        int integerValue = 1024;
        byte invalidDownCast = integerValue;

        // this is valid we are forcing the compiler to auto-promote the float into a double when assigning perfect valid to do
        float floatValue = 10.0f;
        double validAutoPromotion = floatValue;

        // performing an explicit down cast to a lower byte order type is valid, however that will overflow and the value of the byte will
        // be actually integerValue2 % 256
        int integerValue2 = 2048;
        byte overflowDownCast = (byte) integerValue2;
    }
}
