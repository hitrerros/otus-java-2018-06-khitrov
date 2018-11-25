package ru.otus.khitrov.messageSystem.messages.todb;

import ru.otus.khitrov.db.CachedDBService;
import ru.otus.khitrov.db.dataSets.UserDataSet;
import ru.otus.khitrov.json.JsonHelper;
import ru.otus.khitrov.json.JsonToClientBean;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.messages.tofront.MsgCommonAnswer;

import java.util.List;

public class MsgAddUser extends MsgToDB {

    private final JsonToClientBean bean;

    public MsgAddUser(Address from, Address to, JsonToClientBean bean ) {
        super(from, to);
        this.bean = bean;
    }


    @Override
    public void exec(CachedDBService dbService) {

        String answer = EMPTY_JSON_ARRAY;

        List<UserDataSet> users = null;

        UserDataSet dataSet = UserDataSet.generateFromParameters( bean.getName(),
                                                          bean.getAge(),
                                                          bean.getAddress(),
                                                          bean.getPhones() );

        try {
            dbService.save( dataSet );
            users = dbService.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!users.isEmpty())
            answer = JsonHelper.serializeDataSetToJson(users);

         dbService.getMessageSystem().sendMessage( new MsgCommonAnswer( getTo(), getFrom(), answer ));
    }
}
