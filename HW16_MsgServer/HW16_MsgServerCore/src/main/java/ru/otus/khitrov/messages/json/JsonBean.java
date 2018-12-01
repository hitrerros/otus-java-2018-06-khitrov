package ru.otus.khitrov.messages.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.khitrov.messages.Address;
import ru.otus.khitrov.messages.Message;
import ru.otus.khitrov.messages.MessageFrontEnd;

public class JsonBean {

    @JsonProperty("cmd")
    private  String cmd;
    @JsonProperty("id")
    private  long   id;
    @JsonProperty("age")
    private  String age;
    @JsonProperty("name")
    private  String name;
    @JsonProperty("address")
    private   String address;
    @JsonProperty("phones")
    private   String phones;

    public JsonBean (String cmd, long id, String age, String name, String address, String phones){
        this.cmd = cmd;
        this.id  = id;
        this.age = age;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public JsonBean(){ }

    public JsonBean (String cmd){
        this.cmd = cmd;
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


    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public static Message getReadAllMessage() throws Exception {

        Message message = new MessageFrontEnd(new Address("frontend"),
                new Address("db"),
                new JsonBean(ClientCommands.READ_ALL.getCmd()));

        return message;
    }

}
