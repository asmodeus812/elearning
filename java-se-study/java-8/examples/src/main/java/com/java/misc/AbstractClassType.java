package com.java.misc;

public abstract class AbstractClassType {

    public AbstractClassType() {}

    // we can define as many non-abstract methods as we wish, but at least one non final abstract method
    public void nonAbstract() {}

    // this is not a possible scneario, we can not have abstract , which requires to be inherited and final which prohibits class
    // inheritance together, we need to choose EITHER one
    public final abstract class AbstractFinalClassType {
    }

    // first the class MUST be defined as abstract if an abstract method is present, in its body
    public class AbstractMethodClassType {

        // second the abstract method can NOT have a body when it is defined as abstract
        public abstract void method() {
        }
    }

    public abstract class AbstractFinalMethodClassType {

        // that is also not possible abstract + final method just like abstract + final class does not make sense, compiler error
        public abstract final void method();
    }
}
