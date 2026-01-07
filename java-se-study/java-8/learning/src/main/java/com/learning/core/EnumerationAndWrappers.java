package com.learning.core;

import com.learning.misc.AppleEnumType;
import com.learning.utils.InstanceMessageLogger;
import java.io.Closeable;
import java.io.IOException;

public class EnumerationAndWrappers {

    // by default enums are static final so there is no reason to define an enum as a static final explicitly, an enum can not be non-static
    // because that would imply that it is somehow linked to an instance of the class within which it is nested, which is simply not
    // possible, simply because enumerations types are singleton instances
    private enum NestedEnumClass implements Closeable {

        // enums can not declare method implementations and not have any instance enums defined, if no enums are defined we have to have an
        // empty line ending with ; otherwise the comiler will complain
        ;

        @Override
        public void close() throws IOException {
            // demonstrates that an enum can indeed implement an interface, but not extend a class, since it already does extend the
            // java.lang.Enum, and since in java we have no multiple inheritance
        }

        @Override
        public String toString() {
            return "";
        }
    }

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(EnumerationAndWrappers.class);

    public static void main(String[] args) {
        AppleEnumType johnType = AppleEnumType.JOHNATAN;
        AppleEnumType goldenType = AppleEnumType.GOLDENDEL;

        // this enum has no constants defined therefore this method will actually throw an exception - Exception in thread "main"
        // java.lang.IllegalArgumentException: No enum constant com.learning.core.EnumerationAndConstants.NestedEnumClass.
        NestedEnumClass nestedEmptyEnum = NestedEnumClass.valueOf("");
        LOGGER.logWarning(nestedEmptyEnum);

        LOGGER.logInfo(johnType.getName());
        LOGGER.logInfo(johnType.name());

        LOGGER.logInfo(goldenType.getName());
        LOGGER.logInfo(johnType.name());

        // as we can see we can certainly switch on a value of a type enum, since they are constant, instances created at the begining of
        // the program the moment the enum class is loaded into the runtime
        switch (johnType) {
            case JOHNATAN:
                LOGGER.logInfo("Johnatan selected");
                break;
            case GOLDENDEL:
                LOGGER.logInfo("Golden del selected");
                break;
            case REDDEL:
                LOGGER.logInfo("Red del selected");
                break;
            default:
                break;
        }

        // every enum is an implicit instance of the java.lang.Enum type, this also allows us to use all the methods that the enum class
        // exposes in this case there are a few
        if (johnType instanceof Enum) {
            // notice that enum itself is a generic however enums can not be generic themselves, actually enums can not extend any class
            // since they are already extending off of Enum, they can imlement an interface just as any other class can
            Enum<AppleEnumType> enumType = johnType;
            int comparisonValue = enumType.compareTo(goldenType);
            LOGGER.logInfo(String.format("Compared the two enums [johnType] and [goldenType] the result is %d", comparisonValue));
        }

        // this will produce comple time error since by default the enum fields are package protected
        // johnType.name;

        // this will produce a compile time error since by default the enum constructors are private
        // new AppleEnumType();

        // these are all valid, and notice that last assignment that we do in that case we are assigning a wrapper to a primitive and that
        // is perfectly valid use case, since java compiler will do the unwrapping itself, it does not do anything special just calling the
        // byteValue() method on the variable byteValuePrimitive, therefore is that was nil it will fail, see the boolean use case below
        Double doubleValue = 5.0;
        Byte byteValue = doubleValue.byteValue();
        Byte byteValuePrimitive = 5;
        byte byteFromWrapper = byteValuePrimitive;

        // this is valid conversion because we are direclty using the methods provided by the Number class, however the assignment below is
        // not valid we are directly assigning two object references that are not overlapping in any way, java still treats this as regular
        // object assignment no matter the fact that we are using the special wrapper types
        Integer integerValue = 5;
        Byte byteFromInteger = integerValue.byteValue();
        // Integer byteInvalidAssign = byteFromInteger; // not possible with object wrapper types

        LOGGER.logInfo(doubleValue);
        LOGGER.logInfo(byteValue);
        LOGGER.logInfo(byteValuePrimitive);
        LOGGER.logInfo(byteFromInteger);

        // this is a runtime issue, the compiler will, when converting to byte code try to invoke the method booleanValue() on this object
        // and since that boolean object is null it will fail with NullPointerException, this is generally a bad practice using wrapper
        // boolean like that
        Boolean invalidBooleanValue = null;
        if (invalidBooleanValue) {
        }
        // this is effectively what the compiled time code will do, invoke a method on a nil object, which will fail immediately
        if (invalidBooleanValue.booleanValue()) {
        }


        // this is valid the compiler will auto promote this expression into integer, since we are assigning the result to a wrapper of
        // integer type
        Byte k = 5;
        Byte o = 10;
        Integer e = k * o;
        LOGGER.logInfo(e);

        // this however is an error, even though the primitive assignment will work and we can convert the double q = f; just fine, we can
        // not convert wrappedF = f, even though we are still doing the same up-cast promotion, that is not valid and will produce a compile
        // time error
        // int f = 5;
        // double q = f; // auto promoting to double
        // Double wrappedQ = q; // that is just fine
        // Double wrappedF = f; // that is plain error
        // LOGGER.logInfo(wrappedF);
    }
}
