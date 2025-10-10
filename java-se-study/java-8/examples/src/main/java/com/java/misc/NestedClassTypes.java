package com.java.misc;

public class NestedClassTypes {

    private int memberVariable1;

    private String memberVariable2;

    public NestedClassTypes() {
        // we create an instance of the non-static class, which can ONLY be created through the instance of the enclosing `this` outer class
        NestedNonStaticClassType nestedNonStatic = this.new NestedNonStaticClassType(null);
    }

    public static final void createNonStaticInstance() {
        // we create an instance of the static class, which can be created without the need of an instance of a nested class type
        NestedStaticClassType nestedStatic = new NestedStaticClassType(null);
    }

    public static final class NestedStaticClassType {

        private int memberVariable2;

        public NestedStaticClassType(NestedClassTypes instance) {
            // perfectly valid we are using the local member varialbe
            int k = this.memberVariable2;

            // not valid, we are in a nested STATIC class type we can not access the instance variable of the enclosing type
            int m = NestedClassTypes.this.memberVariable2;

            // valid use case we can access the private members of the enclosing class and also we do that through an instnace of that class
            String o = instance.memberVariable2;
        }
    }

    public final class NestedNonStaticClassType {

        private int memberVariable2;

        public NestedNonStaticClassType(NestedClassTypes instance) {
            // valid use case, we are accessing the local member variable
            int k = this.memberVariable2;

            // also valid we are in a NON-STATIC nested class therefore this instance of this class is tied to an instance of the enclosing
            // class
            String m = NestedClassTypes.this.memberVariable2;

            // this is also valid, however it is not necessarily true that NestedClassTypes.this === instance, it depends on thow the
            // constructor is invoked
            String o = instance.memberVariable2;
        }
    }
}
