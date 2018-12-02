package ru.otus.khitrov.server;

import ru.otus.khitrov.channel.MessageWorker;
import ru.otus.khitrov.channel.SocketMessageConnector;
import ru.otus.khitrov.messages.Address;
import ru.otus.khitrov.messages.Message;
import ru.otus.khitrov.messages.MessageHello;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageServer {
    private static final Logger logger = Logger.getLogger(SocketMessageServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService executor;
    private final List<MessageWorker> workers;
    private final Map<Address, MessageWorker> addresseeMap;

    public SocketMessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
        addresseeMap = new ConcurrentHashMap<>();
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMessageConnector worker = new SocketMessageConnector(socket);
                worker.init();
                workers.add(worker);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MessageWorker worker : workers) {
                Message message = worker.poll();
                while (message != null) {

                    if (message instanceof MessageHello) {
                        System.out.println("Init client on server: " + message.getFrom().getId());
                        addresseeMap.put(message.getFrom(), worker);
                    } else {
                        System.out.println("Redirecting the message: " + message.toString());
                        MessageWorker destination = addresseeMap.get(message.getTo());
                        destination.send(message);
                    }
                    message = worker.poll();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }

}
