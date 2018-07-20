package ru.otus.khitrov2018_06;

import org.apache.commons.text.*;




public class Main {

    public static void main(String[] args){

        TextStringBuilder textStringBuilder = new TextStringBuilder("Hello world!");
        StringTokenizer stringTokenizer =  textStringBuilder.reverse().asTokenizer();


        while (stringTokenizer.hasNext()) {
            System.out.println( stringTokenizer.next() );
        }

    }

}
