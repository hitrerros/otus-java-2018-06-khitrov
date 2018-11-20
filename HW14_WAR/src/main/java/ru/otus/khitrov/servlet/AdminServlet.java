package ru.otus.khitrov.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.base.dataSets.UserDataSet;
import ru.otus.khitrov.cache.CacheHelper;
import ru.otus.khitrov.template.TemplateProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends MainServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String ADD_USER_PAGE_TEMPLATE = "add_user.html";

    @Autowired
    private DBService dbService;
    @Autowired
    private CacheHelper cache;


    public AdminServlet() throws IOException {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (dbService == null) throw new IOException("Db service not initialized");

        String action =  request.getParameter("action");
        response.setContentType("text/html;charset=utf-8");

        try {
            if (action == null) {
                prepareRespondPage( request,response, ADMIN_PAGE_TEMPLATE, dbService.readAll() );
            } else if ("add_user".equals(action)) {
                prepareRespondPage( request,response, ADD_USER_PAGE_TEMPLATE, null );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action =  request.getParameter("action");

        try {
         if ("find_user".equals( action )) {
              findUserAction(request,response);
            }
        else if ("added_user".equals( action )) {
                addUserAction(request,response);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

}


    private void findUserAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<UserDataSet> usrList;
        Long userId = Long.valueOf(request.getParameter("f_user"));
        UserDataSet usr;

        if ((usr = cache.getValue(userId))==null) {
            dbService.read(userId);
        }

        usrList = ( usr == null ) ? null  :  List.of( usr );
        prepareRespondPage( request,response, ADMIN_PAGE_TEMPLATE, usrList );
    }

    private void addUserAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserDataSet dataSet = UserDataSet.generateFromParameters( request.getParameter( "name"),
                                                                  request.getParameter( "age"),
                                                                  request.getParameter( "address"),
                                                                  request.getParameter( "phones"));

        dbService.save( dataSet );
        cache.setValue( dataSet );

        prepareRespondPage( request,response, ADMIN_PAGE_TEMPLATE, dbService.readAll() );
    }


    private void prepareRespondPage( HttpServletRequest request,
                                     HttpServletResponse response,
                                     String pageName,
                                     List<UserDataSet> usrList ) throws Exception {

        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("items", usrList);
        pageVariables.put("login", request.getSession().getAttribute("login") );
        pageVariables.put("total", dbService.getCount() );

        String page = templateProcessor.getPage(pageName, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
