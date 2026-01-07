package com.learning.misc;

public enum AppleEnumType {

    JOHNATAN("johnatan"), GOLDENDEL("goldendel"), REDDEL("reddel"), WINESAP("winesap"), CORTLAND("cortland");

    String name;

    AppleEnumType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    void setName(String value) {
        this.name = value;
    }
}
