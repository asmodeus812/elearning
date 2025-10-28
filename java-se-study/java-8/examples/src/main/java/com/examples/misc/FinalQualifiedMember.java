package com.examples.misc;

public class FinalQualifiedMember {

    public final int constant1;

    public final int constant2 = 256;

    public FinalQualifiedMember() {
        // this wilL produce a compile time error because all constructors have to initialize the variable that is defined as final and does
        // not have a value, in this case constant1;
    }

    public FinalQualifiedMember(int c1) {
        // this one is perfrectly valid as we are initializing the value of the final field in the constructor
        this.constant1 = c1;
    }

    public FinalQualifiedMember(int c1, int c2) {
        // this is not valid, as the constant2 member has already been initialized in the class declaration above
        this.constant1 = c1;
        this.constant2 = c2;
    }

    public final int concreteMethod() {
        return 512;
    }
}
