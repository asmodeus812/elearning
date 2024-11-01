package com.java.core;

public class NestingAndClasses {

    public static class OuterScope {
        private int var;

        public class NestedOne {
            private int var;

            NestedOne() {
                this.var = OuterScope.this.var + 1;
            }
        }

        public static class NestedTwo {

            NestedTwo() {}
        }

    }

    public static void main(String[] args) {
        OuterScope kk = new OuterScope();
        return;
    }

}
