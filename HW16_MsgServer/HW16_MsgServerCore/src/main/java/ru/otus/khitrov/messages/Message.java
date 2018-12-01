package ru.otus.khitrov.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.otus.khitrov.messages.json.JsonBean;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
                visible = true,
                include = JsonTypeInfo.As.PROPERTY
        )
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonBean.class,        name = "JsonBean"),
        @JsonSubTypes.Type(value = MessageFrontEnd.class, name = "MessageFrontEnd"),
        @JsonSubTypes.Type(value = MessageDBServer.class, name = "MessageDBServer"),
        @JsonSubTypes.Type(value = MessageHello.class,    name = "MessageHello"),
}
)
public abstract class Message {

    @JsonProperty("from")
    private Address from;
    @JsonProperty("to")
    private Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Message() { }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public abstract JsonBean getBean();
}
