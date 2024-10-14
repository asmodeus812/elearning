package com.java.core;

public class LitteralsAndTypes {

    public static void main(String[] args) {
        try {
            Thread.sleep(2500);
            System.out.println("test");
            for (int i = 0; i < 1000; i++) {
                System.out.println("test");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        }
    }
}
