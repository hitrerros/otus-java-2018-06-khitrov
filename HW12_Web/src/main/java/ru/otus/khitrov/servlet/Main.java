package ru.otus.khitrov.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.base.DBServiceHibernateImpl;
import ru.otus.khitrov.base.dataSets.AddressDataSet;
import ru.otus.khitrov.base.dataSets.PhoneDataSet;
import ru.otus.khitrov.base.dataSets.UserDataSet;

import java.util.List;

public class Main {

   private final static int PORT = 8090;
   private final static String PUBLIC_HTML = "public_html";

   public static void main(String...args) throws Exception  {

       ResourceHandler resourceHandler = new ResourceHandler();
       resourceHandler.setResourceBase(PUBLIC_HTML);

       ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
       TemplateProcessor templateProcessor = new TemplateProcessor();

       DBService dbServiceHib = new DBServiceHibernateImpl();
//       initWithDefaults( dbServiceHib );

       context.addServlet(new ServletHolder(new LoginServlet(templateProcessor,
                                "anonymous", dbServiceHib )), "/login");

       context.addServlet(AdminServlet.class, "/admin");

       Server server = new Server(PORT);
       server.setHandler(new HandlerList(resourceHandler, context));

       server.start();
       server.join();


   }

    private static void initWithDefaults(DBService dbService) throws  Exception {

        dbService.save( new UserDataSet("Vasechkin",
                25,
                new AddressDataSet("Red square 1"),
                List.of(new PhoneDataSet("123"),
                        new PhoneDataSet("234"))));


        dbService.save( new UserDataSet("Ivanov",
                35,
                new AddressDataSet("Red square 1"),
                List.of(new PhoneDataSet("123"),
                        new PhoneDataSet("234"))));


    }


}
