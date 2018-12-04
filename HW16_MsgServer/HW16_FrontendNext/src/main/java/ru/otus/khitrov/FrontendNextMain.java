package ru.otus.khitrov;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class FrontendNextMain {

    public static final int WEB_PORT = 8070;

    private final static String PUBLIC_HTML = "HW16_FrontendNext/public_html";
    private final static String WEBXML_PATH = "HW16_FrontendNext/src/main/resources/WEB-INF/web.xml";

    public static void main(String[] args) throws Exception  {
        FrontendNextMain frontendNextMain = new FrontendNextMain();
        frontendNextMain.startWeb();
    }

    private void startWeb() throws Exception {

         Server server = new Server( WEB_PORT );

         WebAppContext webContext = new WebAppContext();
         webContext.setResourceBase(PUBLIC_HTML);
         webContext.setContextPath("/users");
         webContext.setDescriptor(WEBXML_PATH );

         server.setHandler(webContext);
         server.start();
         server.join();
    }



}
