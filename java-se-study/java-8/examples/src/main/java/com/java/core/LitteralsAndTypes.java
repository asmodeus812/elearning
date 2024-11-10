package com.java.core;

public class LitteralsAndTypes {

    int test = 0;

    void method(int arg) {
        Runnable r = () -> {
            this.test = 1;
            arg = 23;
            System.out.println("test");
        };
        r.run();
    }

    public static void main(String[] args) {}
}
