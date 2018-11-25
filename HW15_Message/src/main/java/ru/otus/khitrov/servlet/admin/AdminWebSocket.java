package ru.otus.khitrov.servlet.admin;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.khitrov.frontend.AbstractFrontendService;
import ru.otus.khitrov.json.JsonToClientBean;
import ru.otus.khitrov.json.ClientCommands;
import ru.otus.khitrov.json.JsonHelper;
import ru.otus.khitrov.messageSystem.Address;
import ru.otus.khitrov.messageSystem.Message;
import ru.otus.khitrov.messageSystem.messages.todb.MsgAddUser;
import ru.otus.khitrov.messageSystem.messages.todb.MsgGetID;
import ru.otus.khitrov.messageSystem.messages.todb.MsgReadAll;

import java.io.IOException;
import java.util.Set;


@WebSocket
public class AdminWebSocket  extends AbstractFrontendService {

    private Set<AdminWebSocket> users;
    private Session session;

    public AdminWebSocket(Set<AdminWebSocket> users) {
        super();
        this.users = users;
    }

    @OnWebSocketMessage
    public void onMessage(String data) throws Exception {

        JsonToClientBean cmd = JsonHelper.deserializeMessage(data);
        System.out.println("out: " + cmd.toString() );

        if (ClientCommands.READ_ALL.equals( cmd.getCmd())) {

            Message message = new MsgReadAll(getAddress(), context.getDbAddress());
            context.getMessageSystem().sendMessage(message);
        }
        else if (ClientCommands.FIND.equals( cmd.getCmd())) {

            Message message = new MsgGetID(getAddress(), context.getDbAddress(),cmd.getId());
            context.getMessageSystem().sendMessage(message);
        }
        else if (ClientCommands.ADD.equals( cmd.getCmd())) {

            Message message = new MsgAddUser(getAddress(), context.getDbAddress(),cmd );
            context.getMessageSystem().sendMessage(message);

        }

      }


      @Override
      public void sendAnswerToClient(String replyJson ) {

        for (AdminWebSocket user : users) {
            try {
                user.getSession().getRemote().sendString( replyJson );
                System.out.println("Sending message: " + replyJson );
            } catch (Exception e) {
                System.out.print(e.toString());
            }
        }



    }


    @OnWebSocketConnect
    public void onOpen(Session session) throws IOException {
        users.add(this);
        setSession(session);
        registerInMessageSystem();
        context.getMessageSystem().start();
         }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        users.remove(this);
    }

}
