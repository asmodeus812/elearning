package com.java.misc;

public class InheritanceJavaBean extends EncapsulatedJavaBean {

    private double field3;

    public double getField3() {
        return field3;
    }

    public void setField3(double field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return "InheritanceJavaBean{" +
                                        "field3=" + field3 +
                                        ", " + super.toString() +
                                        '}';
    }
}
