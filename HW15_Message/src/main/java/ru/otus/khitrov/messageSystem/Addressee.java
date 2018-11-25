package ru.otus.khitrov.messageSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMessageSystem();

    void registerInMessageSystem();
}
