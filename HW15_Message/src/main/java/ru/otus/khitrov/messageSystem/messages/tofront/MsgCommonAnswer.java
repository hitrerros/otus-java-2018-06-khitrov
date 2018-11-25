package ru.otus.khitrov.messageSystem.messages.tofront;

import ru.otus.khitrov.frontend.FrontendService;
import ru.otus.khitrov.messageSystem.Address;

public class MsgCommonAnswer extends MsgToFrontend {

    private final String jsonAnswer;

    public MsgCommonAnswer(Address from, Address to, String jsonAnswer ) {
        super(from, to);
        this.jsonAnswer = jsonAnswer;
    }


    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendAnswerToClient(jsonAnswer);
    }
}
