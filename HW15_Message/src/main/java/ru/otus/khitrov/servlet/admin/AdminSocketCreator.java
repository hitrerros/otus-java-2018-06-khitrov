package ru.otus.khitrov.servlet.admin;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class AdminSocketCreator  implements WebSocketCreator {

    private Set<AdminWebSocket> users;

    public AdminSocketCreator() {
        this.users = ConcurrentHashMap.newKeySet();
     }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {

        AdminWebSocket socket = new AdminWebSocket(users);
        return socket;
    }

}
