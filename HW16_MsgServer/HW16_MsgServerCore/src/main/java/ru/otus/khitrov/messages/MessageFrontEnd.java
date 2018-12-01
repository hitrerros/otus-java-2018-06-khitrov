package ru.otus.khitrov.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.khitrov.messages.json.JsonBean;

public class MessageFrontEnd extends   Message {

    @JsonProperty("bean")
    private JsonBean bean;

    public MessageFrontEnd(Address from, Address to, JsonBean bean){
        super(from,to);
        this.bean = bean;
    }

    public MessageFrontEnd(){

    }

    public JsonBean getBean() {
        return bean;
    }

    @Override
    public String toString() {
        return "MessageFrontEnd{" +
                "bean=" + bean +
                '}';
    }
}
