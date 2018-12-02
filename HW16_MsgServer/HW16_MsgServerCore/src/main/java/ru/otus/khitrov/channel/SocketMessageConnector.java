package ru.otus.khitrov.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.khitrov.messages.Address;
import ru.otus.khitrov.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMessageConnector implements MessageWorker {

    private static final Logger logger = Logger.getLogger(SocketMessageConnector.class.getName());
    private static final int WORKERS_COUNT = 2;
    private static final int QUEUE_CAPACITY = 10;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    private final ExecutorService executor;
    private final Socket socket;
    protected Address address;

    public SocketMessageConnector(Socket socket) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public Message poll() {
        return input.poll();
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() {
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }


    private void sendMessage() {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {

                final Message message = output.take();
                final String json = MAPPER.writeValueAsString(message);

                if (this instanceof SocketMessageClient)
                    System.out.println(address.getId() + ": sending message: " + json);

                writer.println(json);
                writer.println();//line with json + an empty line
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void receiveMessage() {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) { //blocks
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    final String json = stringBuilder.toString();

                    if (this instanceof SocketMessageClient)
                        System.out.println(address.getId() + ": Receiving message: " + json);

                    final Message message = MAPPER.readValue(json, Message.class);
                    input.add(message);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            close();
        }
    }


}
