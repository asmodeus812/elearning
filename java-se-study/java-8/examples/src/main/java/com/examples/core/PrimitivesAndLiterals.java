package com.examples.core;

public class PrimitivesAndLiterals {

    public static void main(String[] args) {
        // this is a valid value that can fit into a single byte, the compiler knows that so there is no isse
        byte validByteValue = 0x01;

        // this is not a valid short value, remember shorts are signed and values range form 2^16 to -2^16, this value is greater than 2^16
        short invalidShortValue = 43_000;

        // different notations based on different radix systems
        byte notationBinary = 0b00000001;
        byte notationOctal = 017;
        byte notationTen = 17;

        // demonstrate how to assign a valid unicode 2 byte sized value to a cahr
        char unicodeValue = '\u1212';

        // showcase that this is a string sequence, can not be assigned to a char type
        char invalidValue = "invalid";

        // the value is explicitly defined as Long, therefore we can not assign it here, as taht would require downcasting
        int invalidLongDownCast = 254L;

        // demosntrate that a long can be assigned by specifically marking the literla as long, int this case that is not needed, since
        // without the L at the end the compiler would still auto-promote it
        long validLongValue = 1024L;

        // showcase two things, we down-cast the left size to short, which effectively overflows the value and then assign to int which
        // auto-promotes the overflowing short to int
        int validDownCastFloat = (short) 43_000.0f;

        // this expression evaluated left to right with precedence of division first, will produce (double) / (float) - (long) -> (double) -
        // (long) -> (double), therefore the left hand side declared as double is correct
        double complexExpression = (5.0 * 1) / (255.0f * 2L) - (300L - 100);

        // simple expression that shows how the auto-promotion will actually force the entire expression to be promoted to the highgest
        // order primitive, in this case in this expression that is double, we can not then assign it to an int without a cast, this is a
        // compile time error of course
        int incorrectExpression = (500 - 200) / (2.0 - 5);
    }
}
