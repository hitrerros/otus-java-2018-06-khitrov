package ru.otus.khitrov.messageSystem.messages.todb;

import ru.otus.khitrov.db.CachedDBService;
import ru.otus.khitrov.db.dataSets.UserDataSet;
import ru.otus.khitrov.json.JsonHelper;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.messages.tofront.MsgCommonAnswer;

import java.util.List;

public class MsgReadAll extends  MsgToDB {

    public MsgReadAll(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(CachedDBService dbService) {

        List<UserDataSet> dataSet = null;
        String answer = EMPTY_JSON_ARRAY;

        try {
             dataSet = dbService.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!dataSet.isEmpty())
                answer = JsonHelper.serializeDataSetToJson(dataSet);

        dbService.getMessageSystem().sendMessage( new MsgCommonAnswer( getTo(), getFrom(), answer ));
    }
}
