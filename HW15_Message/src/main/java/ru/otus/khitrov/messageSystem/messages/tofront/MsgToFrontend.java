package ru.otus.khitrov.messageSystem.messages.tofront;


import ru.otus.khitrov.frontend.FrontendService;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.Addressee;
import ru.otus.khitrov.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}