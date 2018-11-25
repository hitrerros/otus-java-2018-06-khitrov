package ru.otus.khitrov.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.Addressee;
import ru.otus.khitrov.messageSystem.MessageSystem;
import ru.otus.khitrov.messageSystem.MessageSystemContext;

public abstract  class AbstractFrontendService implements FrontendService {

    protected  Address address;

    @Autowired
    protected MessageSystemContext context;

    public AbstractFrontendService(  ){
         SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void registerInMessageSystem() {
        address = context.getFrontAddress();
        context.getMessageSystem().addAddressee(address,this);
    }

    @Override
    public Address getAddress() {
        return address;
    }


    @Override
    public MessageSystem getMessageSystem() {
        return context.getMessageSystem();
    }



}
