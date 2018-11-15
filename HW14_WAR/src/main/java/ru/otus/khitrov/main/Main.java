package ru.otus.khitrov.main;

/*
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.khitrov.servlet.AdminServlet;
import ru.otus.khitrov.servlet.LoginServlet;
*/

// Just for running from IDEA
public class Main {

    private final static int PORT = 8090;
        private final static String PUBLIC_HTML = "public_html";
/*
        public static void main(String[] args) throws Exception {

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase(PUBLIC_HTML);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.addServlet(LoginServlet.class, "/login");
            context.addServlet(AdminServlet.class, "/admin");

            Server server = new Server(PORT);
            server.setHandler(new HandlerList(resourceHandler, context));

            server.start();
            server.join();
        }
        */
    }



