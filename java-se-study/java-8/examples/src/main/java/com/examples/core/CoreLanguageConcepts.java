package com.examples.core;

import com.examples.misc.EncapsulatedJavaBean;
import com.examples.misc.InheritanceJavaBean;

public class CoreLanguageConcepts {

    public static void main(String[] args) {
        // encapsulation - demonstrate how data and fields are bound together into a class
        EncapsulatedJavaBean encapsulated = new EncapsulatedJavaBean();
        encapsulated.setField1("stringfield");

        // inheritance - demonstrate that one class can inherit the properties of another
        InheritanceJavaBean inherited = new InheritanceJavaBean();
        inherited.setField1("stringfield2");
        inherited.setField3(5);

        Object objects[] = new Object[2];
        objects[0] = new EncapsulatedJavaBean();
        objects[1] = new InheritanceJavaBean();

        // polymorphism - demonstrate that two instances can be treated as one super type, in this case the top of the object hierarchy in
        // java
        for (Object o : objects) {
            o.toString();
        }
    }
}
