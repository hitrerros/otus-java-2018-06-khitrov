package ru.otus.khitrov;

import ru.otus.khitrov.channel.SocketMessageConnector;
import ru.otus.khitrov.channel.SocketMessageClient;
import ru.otus.khitrov.message.MessageExecutor;
import ru.otus.khitrov.messages.Address;
import ru.otus.khitrov.messages.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServerLoaderMain {

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    private SocketMessageConnector socketClient;
    private final MessageExecutor msgExecutor;
    private static final Logger logger = Logger.getLogger(DBServerLoaderMain.class.getName());
    private final Address address;

    DBServerLoaderMain(){
        address     = new Address("db");
        msgExecutor = new MessageExecutor();
    }


    public static void main(String[] args) throws Exception {
        DBServerLoaderMain dbServerLoader =  new DBServerLoaderMain();
        dbServerLoader.startSocketClient();
    }

    private void  startSocketClient() throws Exception {
        System.out.println("Starting db listener on port " + PORT );
        socketClient = new SocketMessageClient(HOST, PORT,address);
        socketClient.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final Message inMessage  = socketClient.take();

                    if (!address.getId().equals(inMessage.getTo().getId())) continue;

                    System.out.println("Message received on db client: " + inMessage.toString());

                    final Message outMessage = msgExecutor.doReply( inMessage );
                    if (outMessage !=null) {
                        System.out.println("outMessage from db: " + inMessage.toString());
                        socketClient.send(outMessage);

                    }

                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });






   }



}
