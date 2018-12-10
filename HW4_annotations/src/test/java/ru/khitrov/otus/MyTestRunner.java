package ru.khitrov.otus;

public class MyTestRunner {

    public static void main(String[] args) {
        TUnitCore.runTests(MyClassFibTest.class,
                MyClassNumTest.class);
    }
}
