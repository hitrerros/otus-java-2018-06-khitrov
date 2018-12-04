package ru.otus.khitrov.servlet.admin;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class AdminSocketCreator implements WebSocketCreator {

    private final  Set<AdminWebSocket> users;

    public AdminSocketCreator() {
        this.users = ConcurrentHashMap.newKeySet();
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {

        AdminWebSocket socket = null;
        try {
            socket = new AdminWebSocket(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

}
