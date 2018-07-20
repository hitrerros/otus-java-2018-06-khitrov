package ru.otus.khitrov;

import java.util.ArrayList;
import java.util.List;

public class FilledArrayList {

    private FilledArrayList(){
    }


    public static List<Integer> getInstance(){

        List<Integer> filledArrayList = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            filledArrayList.add(i);
        }

        return filledArrayList;
    }
}
