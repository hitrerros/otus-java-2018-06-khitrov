package ru.otus.khitrov.channel;

import ru.otus.khitrov.messages.Message;

import java.io.Closeable;


public interface MessageWorker extends Closeable {
    void send(Message message);

    Message poll();
    boolean remove(Object o);

    Message take() throws InterruptedException;

    void close();
}
