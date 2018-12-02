package ru.otus.khitrov.servlet.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.otus.khitrov.channel.SocketMessageClient;
import ru.otus.khitrov.messages.Address;
import ru.otus.khitrov.messages.Message;
import ru.otus.khitrov.messages.MessageDBServer;
import ru.otus.khitrov.messages.MessageFrontEnd;
import ru.otus.khitrov.messages.json.JsonBean;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebSocket
public class AdminWebSocket {

    private static final String HOST = "localhost";
    private static final int SOCKET_PORT = 5050;

    private Set<AdminWebSocket> users;
    private Session session;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(AdminWebSocket.class.getName());

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final SocketMessageClient socketClient;

    private final Address address;


    public AdminWebSocket(Set<AdminWebSocket> users) throws IOException {
        address = new Address("frontend");
        this.socketClient = new SocketMessageClient(HOST, SOCKET_PORT, address);
        this.users = users;
    }


    @OnWebSocketMessage
    public void onMessage(String data) throws Exception {
        JsonBean bean = MAPPER.readValue(data, JsonBean.class);
        Message msgFront = new MessageFrontEnd(address, new Address("db"), bean);
        socketClient.send(msgFront);
    }

    public void sendAnswerToClient(MessageDBServer message) throws Exception {

        List<JsonBean> listBean = message.getListMessages();
        String replyJson = MAPPER.writeValueAsString(listBean);

        for (AdminWebSocket user : users) {
            try {
                user.getSession().getRemote().sendString(replyJson);
                System.out.println("Sending message to web: " + replyJson);
            } catch (Exception e) {
                System.out.print(e.toString());
            }
        }
    }


    @OnWebSocketConnect
    public void onOpen(Session session) throws IOException {
        users.add(this);
        setSession(session);
        socketClient.init();

        executorService.submit(() -> {
            try {
                while (true) {
                    final Message inMessage = socketClient.take();

                    if (!address.getId().equals(inMessage.getTo().getId())) continue;
                    System.out.println("Message received on frontend client: " + inMessage.toString());
                    sendAnswerToClient((MessageDBServer) inMessage);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

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
