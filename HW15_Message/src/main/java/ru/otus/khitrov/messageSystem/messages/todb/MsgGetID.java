package ru.otus.khitrov.messageSystem.messages.todb;

import ru.otus.khitrov.db.CachedDBService;
import ru.otus.khitrov.db.dataSets.UserDataSet;
import ru.otus.khitrov.json.JsonHelper;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.messages.tofront.MsgCommonAnswer;

import java.util.List;

public class MsgGetID extends  MsgToDB {

    private final long id;

    public MsgGetID(Address from, Address to, long id ) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(CachedDBService dbService) {

        String answer = EMPTY_JSON_ARRAY;
        UserDataSet dataSet = null;

        try {
            dataSet = dbService.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dataSet != null )
                answer = JsonHelper.serializeDataSetToJson(List.of( dataSet ));

        dbService.getMessageSystem().sendMessage( new MsgCommonAnswer( getTo(), getFrom(), answer ));

    }
}
