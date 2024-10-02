package com.java.core;

public class LitteralsAndTypes {

    public static void main(String[] args) {
        interface Functional {
            Integer create(int n);
        }

        Functional creator = Integer::new; // reference the constructor of Integer
        Integer one = creator.create(1); // create an instance of the integer class
        Integer two = creator.create(2); // create an instance of the integer class
    }
}
