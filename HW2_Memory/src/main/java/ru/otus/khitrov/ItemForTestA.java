package ru.otus.khitrov;

public class ItemForTestA {

    private ItemForTestA(){
    }

    private int forTestInt = 1;

    public static ItemForTestA getInstance(){
        return new ItemForTestA();
    }
}
