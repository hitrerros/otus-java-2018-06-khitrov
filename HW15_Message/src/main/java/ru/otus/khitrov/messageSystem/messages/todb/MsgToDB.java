package ru.otus.khitrov.messageSystem.messages.todb;

import ru.otus.khitrov.db.CachedDBService;
import ru.otus.khitrov.db.DBService;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.Addressee;
import ru.otus.khitrov.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {

    protected final static String EMPTY_JSON_ARRAY = "[]";

    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CachedDBService) {
            exec((CachedDBService) addressee);
        }
    }

    public abstract void exec(CachedDBService dbService);
}
