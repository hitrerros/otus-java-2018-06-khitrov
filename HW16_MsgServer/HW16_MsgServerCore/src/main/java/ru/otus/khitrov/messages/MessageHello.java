package ru.otus.khitrov.messages;

import ru.otus.khitrov.messages.json.JsonBean;

public class MessageHello extends Message {

    private static final String MESSAGE_SERVER_ADDRESS = "MessageServer";

    public  MessageHello(Address from) {
        super(from,new Address(MESSAGE_SERVER_ADDRESS));
    }

    public MessageHello() {}

    @Override
    public JsonBean getBean() {
        return new JsonBean();
    }
}
