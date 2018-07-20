package ru.otus.khitrov;

import java.util.function.Supplier;

public class MemoryCalc{

    private  long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

 public void calculate(Supplier supplier) throws InterruptedException  {

    int size = 20_000_000;
    long initialMem = getMem();

     Object[] array = new Object[size];

     long withRefMem = getMem();

     for (int i = 0; i < array.length; i++) {
         array[i] = supplier.get();
     }

    long finalMem = getMem();
    System.out.println("Object size: " + (finalMem -initialMem )/array.length );

    Thread.sleep(1000); //wait for 1 sec

 }


}
