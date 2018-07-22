package ru.otus.khitrov;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class MemoryCalc{

   private long currentCalculation;
   private int size = 20_000_000;

    private  long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

 public void calculate(Supplier supplier) throws InterruptedException  {


    long initialMem = getMem();
     Object[] array = new Object[size];

     long withRefMem = getMem();

     for (int i = 0; i < array.length; i++) {
        array[i] = supplier.get();
     }

    long finalMem = getMem();
     currentCalculation = (finalMem -initialMem )/array.length;
    System.out.println("Object size: " +   currentCalculation  );

    Thread.sleep(1000); //wait for 1 sec

 }


 public void calculateFullContainer(Supplier supplier) throws InterruptedException {

     List<? extends Object> filledArray = (List<? extends Object>) supplier.get();

     long initialMem = getMem();
     Object[] array  = new Object[size*filledArray.size()];

     long withRefMem = getMem();

     for (int i=0; i<array.length; i += size  ){
         for (int x = 0; x < filledArray.size(); x++ ) {
             array[i + x ] = filledArray.get( x );
         }
     }

     long finalMem = getMem();
     currentCalculation = (finalMem -initialMem )/array.length;
     System.out.println("Object size: " +   currentCalculation  );

     Thread.sleep(1000); //wait for 1 sec

 }



}
