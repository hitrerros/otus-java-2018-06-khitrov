package ru.otus.khitrov.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.khitrov.messages.json.JsonBean;

import java.util.ArrayList;
import java.util.List;

public class MessageDBServer extends Message {

   @JsonProperty("list")
   private List<JsonBean> listMessages = new ArrayList<>();

    public MessageDBServer(Address from, Address to, List<JsonBean> listMessages){
        super(from,to);
        this.listMessages = listMessages;
    }

    public MessageDBServer(){}

    @Override
    public JsonBean getBean() {
        return new JsonBean();
    }

    public List<JsonBean> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<JsonBean> listMessages) {
        this.listMessages = listMessages;
    }

    @Override
    public String toString() {
        return "MessageDBServer{" +
                "listMessages=" + listMessages +
                '}';
    }
}
