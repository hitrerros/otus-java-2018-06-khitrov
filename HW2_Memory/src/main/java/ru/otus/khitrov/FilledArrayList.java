package ru.otus.khitrov;

import java.util.ArrayList;
import java.util.List;

public class FilledArrayList {

   private FilledArrayList(){
   }


    public static List<Integer> getInstance( int valuesNumber){

        List<Integer> filledArrayList = new ArrayList<>();
        for (int i = 0; i < valuesNumber; i++) {
            filledArrayList.add(i);
        }

        return filledArrayList;
    }
}
