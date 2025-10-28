package com.examples.misc.pack;

class ProtectedClassC {

    public ProtectedClassC() {
        // this is not possible even though class ProtectedClassA is under the pack package, it is not a sibling of the current class
        ProtectedClassA clazzA = new ProtectedClassA();
    }
}
