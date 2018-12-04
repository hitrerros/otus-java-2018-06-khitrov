package ru.otus.khitrov.servlet.admin;

import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.khitrov.servlet.MainServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AdminSocketServlet extends MainServlet {

    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new AdminSocketCreator());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("show".equals(action)) {
                prepareRespondPage(request, response, ADMIN_PAGE_TEMPLATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void prepareRespondPage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String pageName) throws Exception {

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("login", request.getSession().getAttribute("login"));

        String page = templateProcessor.getPage(pageName, pageVariables);
        response.setContentType("text/html;charset=utf-8");

        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
