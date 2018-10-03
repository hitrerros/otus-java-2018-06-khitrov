package ru.otus.khitrov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainMyKSON {

    private String json;
    CommonObjectForJSON comObj;

    private void writeJSon(){

        comObj  = new CommonObjectForJSON();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gsonString = gson.toJson(comObj);
        System.out.println("GSON generation with pretty printer: ");
        System.out.println( gsonString );

        System.out.println("MyKSON generation: ");
        KSonParser kson = new KSonParser();
        kson.parseObject( comObj );
        json = kson.getJsonString();
        System.out.println(json);

    }

    private void readAndCompare(){

        Gson gson = new Gson();
        CommonObjectForJSON testObj = gson.fromJson( json, CommonObjectForJSON.class );

        if (testObj.equals( comObj )) {
            System.out.println("Objects are equal!");
        } else {
            System.out.println("Objects are not equal!");
            }
        }


    public static void main(String...args){

        MainMyKSON myKson = new MainMyKSON();
        myKson.writeJSon();
        myKson.readAndCompare();


    }


}
