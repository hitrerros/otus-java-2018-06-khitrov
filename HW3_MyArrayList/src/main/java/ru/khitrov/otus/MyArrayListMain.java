package ru.khitrov.otus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MyArrayListMain {

    public static void main(String... args) {

        System.out.println("1. addAll()");
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        boolean addResult = Collections.addAll(myArrayList, 1, 2);

        if (addResult) {
            myArrayList.stream().forEach(System.out::println);
        }

        System.out.println("2. copy()");
        MyArrayList<Integer> srcList = new MyArrayList<>();
        MyArrayList<Integer> dstList = new MyArrayList<>(srcList.size());


        for (int i = 0; i < 11; i++) {
            srcList.add(i);
            dstList.add(i*2);
        }
        System.out.println("before:");
        dstList.stream().forEach(System.out::println);

        Collections.copy( dstList, srcList );

        System.out.println("after:");
        if  (!dstList.isEmpty()) {
            dstList.stream().forEach(System.out::println);
        }


        System.out.println("3. sort() in reversal");
        Collections.sort(dstList, (o1, o2) -> (o2 - o1));
        dstList.stream().forEach(System.out::println);


    }
}