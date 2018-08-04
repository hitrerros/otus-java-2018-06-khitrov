package ru.khitrov.otus;

public class MyTestRunner {

    public static void main(String[] args)
    {
        MyClassFibTest testClass1 = new MyClassFibTest();
        MyClassNumTest testClass2  = new MyClassNumTest();

        TUnitCore.runTests( testClass1.getClass(),
                            testClass2.getClass());
        }
}
