package ru.khitrov.otus;

public class MyClassFib {

    public int getFibonacci(int maxNum) {
        int n1 = 0, n2 = 1, fib = 0;
        for (int idx = 1; idx <= maxNum; idx++) {
            n1 = n2;
            n2 = fib;
            fib = n1 + n2;
        }
        return fib;
    }

    public static void main(String... args) {

        MyClassFib myClassFib = new MyClassFib();
        for (int i = 1; i <= 30; i++) {
            System.out.println(myClassFib.getFibonacci(i));
        }
    }
}
