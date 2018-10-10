package ru.otus.khitrov.dataset;

public class UserDataSet extends DataSet {

    private String name;
    private int age;
    private static final String STORAGE_TABLE = "ages";

    public UserDataSet(){
        super(0);
        name = "";
        age  = 0;

    }

    public UserDataSet( long id, String name, int age){
        super(id);
        this.name = name;
        this.age  = age;
     }

    public UserDataSet(  String name, int age){
        this.name = name;
        this.age  = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }


    @Override
    public String getStorageTable() {
        return STORAGE_TABLE;
    }

    @Override
    public String toString(){
        return "id: " + id + " = Name " + name + ", age " + age;
    }
}
