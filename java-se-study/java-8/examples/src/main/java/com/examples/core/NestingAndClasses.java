package com.examples.core;

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

    public static class Outer {
        private int x = 24;

        public int getX() {
            String message = "x is ";
            class Inner {
                private int x = Outer.this.x;

                public void printX() {
                    System.out.println(message + x);
                }
            }
            Inner in = new Inner();
            in.printX();
            return x;
        }
    }

    public static void main(String[] args) {
        OuterScope kk = new OuterScope();
        int value = new Outer().getX();
    }
}
