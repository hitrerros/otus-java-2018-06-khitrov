package ru.otus.khitrov.servlet;

import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    public static final String LOGIN_PARAMETER_NAME = "login";
    private static final String LOGIN_VARIABLE_NAME = "login";

    private final DBService dbService;
    private String login;
    private final TemplateProcessor templateProcessor;


    public LoginServlet(TemplateProcessor templateProcessor, String login, DBService dbService) {
        this.dbService = dbService;
        this.login = login;
        this.templateProcessor = templateProcessor;
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
//            saveToServlet(request, requestLogin); //request.getAttribute("login");
//            saveToVariable( requestLogin ); //request.getAttribute("login");
        }

        if (dbService != null) {
            request.getSession().setAttribute("dbService", dbService);
        }

        String currentLogin = (String) request.getSession().getAttribute("login");

        if (currentLogin == null) {
            currentLogin = login;
        }

        String page = getPage(currentLogin); //save to the page
        response.getWriter().println(page);

    }

    private void saveToServlet(HttpServletRequest request, String requestLogin) {
        request.getServletContext().setAttribute("login", requestLogin);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }


    private String getPage(String login) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    private void saveToVariable(String requestLogin) {
        login = requestLogin != null ? requestLogin : login;
    }

}
