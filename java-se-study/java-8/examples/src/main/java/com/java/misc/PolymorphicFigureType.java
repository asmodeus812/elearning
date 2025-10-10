package com.java.misc;

public class PolymorphicFigureType {

    public class Figure {

        double dim1;

        double dim2;

        Figure(double a, double b) {
            dim1 = a;
            dim2 = b;
        }

        public double area() {
            System.out.println("Area for Figure is undefined.");
            return 0;
        }
    }

    public class Rectangle extends Figure {

        public Rectangle(double a, double b) {
            super(a, b);
        }

        // override area for rectangle
        public double area() {
            System.out.println("Inside Area for Rectangle.");
            return dim1 * dim2;
        }
    }

    public class Triangle extends Figure {

        public Triangle(double a, double b) {
            super(a, b);
        }

        // override area for right triangle
        public double area() {
            System.out.println("Inside Area for Triangle.");
            return dim1 * dim2 / 2;
        }
    }

    public PolymorphicFigureType() {}
}
