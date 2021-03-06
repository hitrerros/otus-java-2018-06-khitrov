package ru.otus.khitrov.servlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends MainServlet {

    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    public static final String LOGIN_PARAMETER_NAME = "login";
    private static final String LOGIN_VARIABLE_NAME = "login";

    private String login;

    public LoginServlet() throws IOException {
        this.login = "anonymous";
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {

    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);

        if (requestLogin != null) {
            saveToSession(request, requestLogin); //request.getSession().getAttribute("login");
        }

        String currentLogin = (String) request.getSession().getAttribute("login");

        if (currentLogin == null) {
            currentLogin = login;
        }

        String page = getPage(currentLogin); //save to the page
        response.getWriter().println(page);
    }


    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }


    private String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }


}
