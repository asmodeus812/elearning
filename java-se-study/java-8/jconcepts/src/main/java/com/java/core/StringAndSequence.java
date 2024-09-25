package com.java.core;

import java.lang.reflect.Method;

public class StringAndSequence {

    @interface Annotation {
        int val();
    }

    public static class Meta {

        @Annotation(val = 1234)
        public void method() {}

        @Annotation(val = 1234)
        public void method(String arg1, int arg2) {}
    }

    public static void main(String[] args) {
        Meta ob = new Meta();
        try {
            Class<?> c = ob.getClass();
            Method m = c.getMethod("method");
            Method m2 = c.getMethod("method", String.class, int.class);
            Annotation anno = m.getAnnotation(Annotation.class);
            m.getAnnotations()
            Annotation anno2 = m2.getAnnotation(Annotation.class);
            System.out.println(" " + anno.val());
            System.out.println(" " + anno2.val());
        } catch (NoSuchMethodException exc) {
            System.out.println("Method Not Found.");
        }
    }
}
