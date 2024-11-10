package com.java.core.packtwo;

class TestJava2 {

    private static final int outerStaticMember = 5;

    private int outerMember;

    private Inner outerInstance;

    public TestJava2() {
        // create the instance of the inner static class passing current instance as reference
        outerInstance = this.new Inner();
    }

    public class Inner {

        private static final int innerStaticMember = 5;

        private int innerMember;

        public Inner() {
            // this is valid access of the member of the outer class as long as there is an instance to use reference
            int k = TestJava2.this.outerMember;
            // this is also a valid reference to the outer static member which requires no instance reference
            this.innerMember = TestJava2.outerStaticMember;
        }
    }
}

