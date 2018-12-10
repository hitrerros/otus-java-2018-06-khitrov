package ru.khitrov.otus;

import ru.khitrov.otus.annotations.After;
import ru.khitrov.otus.annotations.Before;
import ru.khitrov.otus.annotations.Test;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.*;

class TUnitCore {

    private TUnitCore() {
    }

    public static void assertEquals(long expected, long actual) {
        if (expected != actual) {
            throw new AssertionException("Failed: Expected value " + expected + ", actual value " + actual);
        }
    }

    public static void runTests(Class<?>... classes) {

        for (Class klass : classes) {

            List<Method> beforeArray = ReflectionHelper.getMethodsWithAnnotation(klass, Before.class);
            List<Method> afterArray = ReflectionHelper.getMethodsWithAnnotation(klass, After.class);
            List<Method> testsArray = ReflectionHelper.getMethodsWithAnnotation(klass, Test.class);

            for (Method method : testsArray) {
                Object myTestClass = ReflectionHelper.instantiate(klass);

                if (!beforeArray.isEmpty()) {
                    ReflectionHelper.callMethod(myTestClass, beforeArray.get(0).getName());
                }

                try {
                    ReflectionHelper.callMethod(myTestClass, method.getName());
                } catch (AssertionException assertionException) {
                    System.out.println(assertionException.getMessage());
                }

                if (!afterArray.isEmpty()) {
                    ReflectionHelper.callMethod(myTestClass, afterArray.get(0).getName());
                }

            }

        }

    }

}
