package com.examples.core;

public class StringAndSequence {

    public static void main(String[] args) {
        // this is simplest example that demoes, that the new string object will not be part of the pool and this comparison will
        // fail,however note that the hello literals we see here will be the same and interned themselves
        String helloHeap = new String("hello");
        String helloInterned = "hello";
        assert (helloHeap == helloInterned); // false

        // the reference here is different because interning will create a new instance which will be put in a special place in the pool,
        // and not use the original reference to put in the pool
        String helloInterned2 = helloHeap.intern();
        assert (helloInterned2 == helloInterned); // false

        // this is happening at runtime, thus it is not possible for the compiler to tell if that can be interned, this is because between
        // the concat and the declaration of the variables we can re-assign the values , and they will no longer point at these constant
        // compile time literals
        String hello = "hello";
        String world = "world";
        String concat = hello + " " + world;
        assert (concat == "hello world"); // false

        // this however is possible to be interned and it will be because there is no way for us to change the values of the string after
        // the declaration, therefore the concat below will be interned and these will be pooled
        final String H = "hello";
        final String W = "world";
        String s = H + " " + W; // all constants → folded → interned
        assert (s == "hello world"); // true

        // here is a gotcha we can certainly do the following as well, note that we are interning the result of the concat after the fact,
        // meaning at even at runtime, the string will be interned, and since it already exists as a literal in the code during compile
        // time, in this case the string "hello world", right below in the assert statement, the result of this assert will be true
        String s1 = (hello + " " + world).intern();
        assert (s1 == "hello world"); // true

        // the general rule of thumb is that any runtime expression like invoking a method, or working with non-constant non-final
        // variables, will not product an interned string in the pool
    }
}
