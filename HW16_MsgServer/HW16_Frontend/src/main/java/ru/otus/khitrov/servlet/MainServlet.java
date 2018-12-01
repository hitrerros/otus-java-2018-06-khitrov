package ru.otus.khitrov.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.khitrov.template.TemplateProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public abstract class MainServlet extends WebSocketServlet {

    @Autowired
    protected TemplateProcessor templateProcessor;

    public MainServlet() {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


}
