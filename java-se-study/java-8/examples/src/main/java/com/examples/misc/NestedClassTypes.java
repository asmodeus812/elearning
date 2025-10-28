package com.examples.misc;

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
            String m = NestedClassTypes.this.memberVariable2;

            // this is not possible to do here as we are in a static context class, thus we have no implicit access to the enclosing
            // instance of this class
            int e = memberVariable1;

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

            // this is also possible we can create a new instance of the current nested class by referencing the enclosing instance, note
            // that this references the enclosing instance because we prefix the this keyword with the enclosing class type, if we just use
            // this that will only refer to this instance/the nested class
            NestedNonStaticClassType n = NestedClassTypes.this.new NestedNonStaticClassType(null);

            // this is also possible, notice that this is actually the same as the line above, why? Well the implicit instance of the
            // enclosing class is used here, just like we can used enclosing member variables, or methods, we can also construct the nested
            // type itself
            NestedNonStaticClassType f = new NestedNonStaticClassType(null);

            // this is also allowed we can directly access the outer class variable by name directly this in this case here does not
            // conflict with the variable naming from the current class scope thus we can safely access the variable belonging to the
            // enclosing class
            int e = memberVariable1;

            // this is also valid, however it is not necessarily true that NestedClassTypes.this === instance, it depends on thow the
            // constructor is invoked
            String o = instance.memberVariable2;
        }
    }
}
