package ru.otus.khitrov.channel;

import ru.otus.khitrov.channel.SocketMessageConnector;
import ru.otus.khitrov.messages.Address;
import ru.otus.khitrov.messages.MessageHello;

import java.io.IOException;
import java.net.Socket;

public class SocketMessageClient extends SocketMessageConnector {

    private final Socket socket;

    public SocketMessageClient(Socket socket) {
        super(socket);
        this.socket = socket;
    }

    public SocketMessageClient(String host, int port, Address address) throws IOException {
        this(new Socket(host, port));
        this.address = address;
    }

    @Override
    public void init() {
        super.init();
        this.send(new MessageHello(address));  // Initialization message from clients
    }

    @Override
    public void close() {
        try {
            super.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
