package ru.otus.khitrov;

import ru.otus.khitrov.process.ProcessRunnerImpl;
import ru.otus.khitrov.server.SocketMessageServer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());
    private static final String DB_SERVER_START_COMMAND = "java -jar HW16_DBServer/target/dbserver.jar";
    private static final String FRONTEND_START_COMMAND = "java -jar HW16_Frontend/target/frontend.jar";
    private static final String FRONTEND_NEXT_START_COMMAND = "java -jar HW16_FrontendNext/target/frontend_next.jar";

    private static final int CLIENT_START_DELAY_SEC = 2;
    private static final int THREAD_CONNECTORS_POOL = 3;


    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREAD_CONNECTORS_POOL);
        SocketMessageServer server = new SocketMessageServer();
        startClient(executorService);
        server.start();
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {

        executorService.schedule(() -> {
            try {

                new ProcessRunnerImpl().start(DB_SERVER_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

        executorService.schedule(() -> {
            try {

                new ProcessRunnerImpl().start(FRONTEND_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC + 2, TimeUnit.SECONDS);

        executorService.schedule(() -> {
            try {

                new ProcessRunnerImpl().start(FRONTEND_NEXT_START_COMMAND );
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC + 10, TimeUnit.SECONDS);
    }


}
