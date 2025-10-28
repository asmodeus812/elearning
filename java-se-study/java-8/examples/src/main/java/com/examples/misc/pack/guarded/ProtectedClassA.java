package com.examples.utils.misc.guarded;

class ProtectedClassA {

    public ProtectedClassA() {
        // this is perfectly valid use case this class is a sibling of the class ProtectedClassB
        ProtectedClassB clazzB = new ProtectedClassB();
    }
}
