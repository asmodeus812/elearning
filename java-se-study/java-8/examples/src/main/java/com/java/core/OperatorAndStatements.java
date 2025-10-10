package com.java.core;

import com.java.utils.InstanceMessageLogger;

public class OperatorAndStatements {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(OperatorAndStatements.class);

    public static void main(String[] args) {
        // showcase how to switch on non-constant identifier, in this case a variable
        String switcher = "1";
        switch (switcher) {
            case "1":
                LOGGER.logInfo("case 1");
                break;
            case "2":
                LOGGER.logInfo("case 2");
                break;
            case "3":
                LOGGER.logInfo("case 3");
                break;
            default:
                LOGGER.logInfo("default");
                break;
        }

        // demonstrate that we can also switch on an expression as well, in this case a multiplication
        switch (switcher + "00") {
            case "100":
                LOGGER.logInfo("case 100");
                break;
            case "200":
                LOGGER.logInfo("case 200");
                break;
            case "300":
                LOGGER.logInfo("case 300");
                break;
            default:
                break;
        }

        // showcase that a switch statement can not have a duplicate case, in this scenario where we have two casews which match on the same
        // condition the compiler will throw an error
        switch (switcher) {
            case "1":
                LOGGER.logInfo("case 1");
                break;
            case "1":
                LOGGER.logInfo("case invalid");
                break;
            default:
                break;
        }

        // showcase that it is not possible to switch on arbitrary objects, since there is no way to know how to compare the object to the
        // case statements.
        Object value = new String("test");
        Object value2 = new String("test");
        switch (value) {
            case value2:
                break;
            default:
                break;
        }

        // demonstrate that the case labels themselves can not be expressions or variable and they must always be constants deduced at
        // compile time
        String switcher2 = "1";
        switch (switcher) {
            case switcher2:
                break;
            default:
                break;
        }

        // demonstrate how to use label blocks, we can use these to, skip continue, break, to a different position in the code after the
        // label has been defined or within the block of the label
        first: for (String string : args) {
            second: for (String string2 : args) {
                if (string2.equals("test")) {
                    break second;
                }
                continue first;
            }
        }

        // showcase how we can early exit a block of code by using a label, this will ensure that only part of this block of code will be
        // executed in this case the first log statement after which we break out of the block and continue, forward
        breakout: {
            LOGGER.logInfo("just before break block");
            break breakout;
            LOGGER.logInfo("this will not execute");
        }

        LOGGER.logInfo("execute is after break block");
    }
}
