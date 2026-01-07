package com.learning.core;

import com.learning.misc.GenericAnnotationType;
import com.learning.misc.TypeAnnotationType;
import com.learning.utils.InstanceMessageLogger;
import java.beans.JavaBean;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.management.MXBean;

public class AnnotationsAndMetadata {

    private static final InstanceMessageLogger LOGGER = new InstanceMessageLogger(AnnotationsAndMetadata.class);

    @JavaBean
    @GenericAnnotationType
    @TypeAnnotationType(name = "class-top")
    public static class TopLevelAnnotated {
        public TopLevelAnnotated() {}
    }

    @MXBean
    @TypeAnnotationType(name = "class-next")
    public static class NextLevelAnnotated extends TopLevelAnnotated {
        public @TypeAnnotationType(name = "constructor-top") NextLevelAnnotated(
                        @TypeAnnotationType(name = "constructor-argument-top") String arg)
                        throws @TypeAnnotationType(name = "exception-throws-top") IllegalStateException {}
    }

    @FunctionalInterface
    public static interface FunctionalComparatorInterface<T extends Number> {

        public int compare(T left, T right);
    }

    @FunctionalInterface
    public static interface FunctionalJoinInterface {

        public String join(String delimiter, String... string);
    }


    public static final void printAnnotatedElements(AnnotatedElement[] elements) {
        for (AnnotatedElement element : elements) {
            printAnnotationList(element.getDeclaredAnnotations());
        }
    }

    public static final void printAnnotationList(Annotation[] annotations) {
        LOGGER.logInfo("------------------------------------------------");
        for (Annotation anno : annotations) {
            LOGGER.logInfo(anno.annotationType().getCanonicalName());
        }
        LOGGER.logInfo("------------------------------------------------\n\n");
    }

    public static void main(String[] args) {
        InstanceMessageLogger.configureLogger(AnnotationsAndMetadata.class.getResourceAsStream("/logger.properties"));
        // We pull the metadata for our custom type class, to inspect the annotation details, note the usage of the two methods, one is the
        // getAnnotations and getDeclaredAnnotations which serve very precise purposes, in one case we want to pull all annotations
        // including the inherited ones, in another just the direct ones that are declared in code on our type
        Class<NextLevelAnnotated> next = NextLevelAnnotated.class;
        Annotation[] annosDirect = next.getDeclaredAnnotations();
        Annotation[] annosAll = next.getAnnotations();

        // here we are pulling all annotations that means all inherited annotations will also show up in this list, for the
        // NextLevelAnnotated type, in this case we have one that is inherited, that is the GenericAnnotationType
        LOGGER.logInfo("Combined annotations");
        printAnnotationList(annosAll);

        // we only list the ones that are not inherited and are directly declared on our type, there fore we expect to see just the one
        // @MXBean that our type has
        LOGGER.logInfo("Declared annotations");
        printAnnotationList(annosDirect);

        Method[] methods = next.getDeclaredMethods();
        Constructor<?>[] constructors = next.getDeclaredConstructors();

        printAnnotatedElements(methods);
        printAnnotatedElements(constructors);

        for (Constructor<?> ctor : constructors) {
            LOGGER.logInfo("Constructor annotations");
            printAnnotationList(ctor.getDeclaredAnnotations());
            LOGGER.logInfo("Parameter annotations");
            printAnnotatedElements(ctor.getParameters());
            LOGGER.logInfo("Exception types annotations");
            printAnnotatedElements(ctor.getAnnotatedExceptionTypes());
            LOGGER.logInfo("Parameter types annotations");
            printAnnotatedElements(ctor.getAnnotatedParameterTypes());
        }

        // FunctionalJoinInterface joiner = String::join;
        // FunctoinalComparatorInterface comparator =
    }
}
