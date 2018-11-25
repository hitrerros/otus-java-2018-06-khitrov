package ru.otus.khitrov.json;

public class JsonToClientBean {

    private final  String cmd;
    private final  long   id;
    private final  String    age;
    private final  String name;
    private final  String address;
    private final  String phones;

    JsonToClientBean(String cmd, long id, String age, String name, String address, String phones){
        this.cmd = cmd;
        this.id  = id;
        this.age = age;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public String getCmd() {
        return cmd;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        return phones;
    }

    public String getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "JsonToClientBean{" +
                "cmd='" + cmd + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}
