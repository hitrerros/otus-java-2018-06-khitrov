package ru.otus.khitrov;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class MemoryMain {

    public static void main(String... args) throws InterruptedException {

        MemoryCalc memoryCalc = new MemoryCalc();

        System.out.println("---Class with primitive types");
        Supplier<ItemForTestA> stringItemForTestA = ItemForTestA::getInstance;
        memoryCalc.calculate(stringItemForTestA);

        System.out.println("---Filled string");
        Supplier<String> stringSupplier = () -> "Hello world";
        memoryCalc.calculate(stringSupplier);

        System.out.println("--Empty string");
        Supplier<String> emptyStringSupplier = String::new;
        memoryCalc.calculate(emptyStringSupplier);

        System.out.println("---Empty container");
        Supplier<List<Integer>> emptyArrayList = ArrayList::new;
        memoryCalc.calculate(emptyArrayList);


    }
}