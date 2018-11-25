package ru.otus.khitrov.frontend;

import ru.otus.khitrov.messageSystem.Addressee;


/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {


    void sendAnswerToClient(String replyJson);

}

