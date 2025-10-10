package com.java.core;

import com.java.misc.AbstractClassType;
import com.java.misc.FinalClassType;
import com.java.misc.FinalQualifiedMember;
import com.java.misc.FinalizeAndDestructors;
import com.java.misc.NestedClassTypes;
import com.java.misc.NestedClassTypes.NestedNonStaticClassType;
import com.java.misc.NestedClassTypes.NestedStaticClassType;
import com.java.misc.OverrideObjectMethods;
import com.java.misc.PolymorphicFigureType;
import com.java.misc.PrivateConstructorClass;
import com.java.misc.StaticAccessMember;

public class ClassesAndTypes {

    public static void main(String[] args) {
        // the constructor is not visible for this class outside of here leading to compiler error for this line, therefore we can not
        // compile the programe like so
        PrivateConstructorClass thisUsage = new PrivateConstructorClass();
        // the double constructor will be used here instead of the package protected one because of the type promotion that will happen
        PrivateConstructorClass thisUsage2 = new PrivateConstructorClass(4);

        // once the garbage collector runs the finalize method will also be run on this object instance
        FinalizeAndDestructors finalizeUsage = new FinalizeAndDestructors();

        // shocase incoccrect ussage of static members in the class
        StaticAccessMember staticAccessMember = new StaticAccessMember();

        // demonstrate how final members can be used and initialized
        FinalQualifiedMember finalQualifiedMember = new FinalQualifiedMember();

        // showcase how we can notoverride a final method in a child / inherited
        FinalQualifiedMember finalQualifiedMember2 = new FinalQualifiedMember() {
            @Override
            // this usage is invad, will produce compile time erorr since we are overriding a final method
            public int concreteMethod() {
                return 1024;
            }
        };

        // demonstrate that a final class can not be sub-classed, and will be a compile time error
        FinalClassType finalClassType = new FinalClassType() {
        }

        // showcase the relationship between operating with instance of nested static and non-static classes
        NestedClassTypes nestedClassTypes = new NestedClassTypes();
        // we can easily create an instance of the inner class outside by simply using the reference to the nestedClassTypes instance
        NestedNonStaticClassType nestedNonStatic = nestedClassTypes.new NestedNonStaticClassType(null);
        // this is possible and valid as well we instantiate the STATIC class as a normal class
        NestedStaticClassType nestedStatic2 = new NestedStaticClassType(null);
        // this however is not possible, since we are instantiating the STATIC class from an instance of the enclosing one
        NestedStaticClassType nestedStatic = nestedClassTypes.new NestedStaticClassType(null);

        // this is not possible even though this class has no abstract methods it is marked as abstract and an instance creation of it is not possible
        AbstractClassType abstractClassType = new AbstractClassType();

        // shocase how polymorphic types can be treated as one and the same, in this case two different shapes/figures being grouped together into a single array of Figure objects
        PolymorphicFigureType.Figure[] figures = new PolymorphicFigureType.Figure[2];
        figures[0] = new PolymorphicFigureType.Triangle(5, 3);
        figures[1] = new PolymorphicFigureType.Rectangle(1, 2);

        for(PolymorphicFigureType.Figure fig : figures) {
            System.out.println(fig.area());
        }

        // demonstrate how different methods from the base Object class can be overriden, and sometimes required, such as hashCode and equals, also show that wait, notify variants can not since they are final methods
        OverrideObjectMethods overrideObjectMethods = new OverrideObjectMethods();
    }
}
