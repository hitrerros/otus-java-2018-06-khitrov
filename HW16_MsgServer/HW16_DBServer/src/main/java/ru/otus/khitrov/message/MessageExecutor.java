package ru.otus.khitrov.message;

import ru.otus.khitrov.db.CachedDBService;
import ru.otus.khitrov.db.CachedDBServiceHibernateImpl;
import ru.otus.khitrov.db.dataSets.UserDataSet;
import ru.otus.khitrov.messages.json.ClientCommands;
import ru.otus.khitrov.messages.json.JsonBean;
import ru.otus.khitrov.messages.Message;
import ru.otus.khitrov.messages.MessageDBServer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageExecutor {

    private final CachedDBService dbService;
    private static final List<JsonBean> EMPTY_JSON_BEAN = new ArrayList<>();

    public MessageExecutor() {
        dbService = new CachedDBServiceHibernateImpl();
    }

    private void readAllAndSave(MessageDBServer reply) throws Exception {
        List<UserDataSet> usrList = dbService.readAll();
        reply.setListMessages(
                (usrList.isEmpty()) ? EMPTY_JSON_BEAN :
                        usrList.stream().map(UserDataSet::generateBean).collect(Collectors.toList()));

    }

    public Message doReply(Message incoming) throws Exception {

        MessageDBServer reply = new MessageDBServer();
        JsonBean bean = incoming.getBean();
        String clientCmd = bean.getCmd();

        if (ClientCommands.READ_ALL.equals(clientCmd)) {
            readAllAndSave(reply);
        } else if (ClientCommands.FIND.equals(clientCmd)) {
            UserDataSet dataSet = dbService.read(bean.getId());
            reply.setListMessages((dataSet == null) ? EMPTY_JSON_BEAN : List.of(dataSet.generateBean()));
        } else if (ClientCommands.ADD.equals(clientCmd)) {

            UserDataSet dataSet = UserDataSet.generateFromParameters(bean.getName(),
                    bean.getAge(),
                    bean.getAddress(),
                    bean.getPhones());

            dbService.save(dataSet);
            readAllAndSave(reply);
        } else {
            return null;
        }

        reply.setFrom(incoming.getTo());
        reply.setTo(incoming.getFrom());

        return reply;
    }


}
