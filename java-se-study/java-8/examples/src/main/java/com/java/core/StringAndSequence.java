package com.java.core;

public class StringAndSequence {

    public static void main(String[] args) {
        String helloHeap = new String("hello");
        String helloInterned = "hello";
        assert (helloHeap != helloInterned);

        String helloInterned2 = helloHeap.intern();
        assert (helloInterned2 == helloInterned);
    }
}
