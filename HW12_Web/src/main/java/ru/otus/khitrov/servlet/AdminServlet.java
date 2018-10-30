package ru.otus.khitrov.servlet;

import ru.otus.khitrov.base.DBService;
import ru.otus.khitrov.base.dataSets.UserDataSet;
import ru.otus.khitrov.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String ADD_USER_PAGE_TEMPLATE = "add_user.html";
    private static final String TAB_LINE_START = "<tr><td>";
    private static final String TAB_LINE_MIDDLE = "</td><td>";
    private static final String TAB_LINE_END = "</td></tr>";

    private final TemplateProcessor templateProcessor;

    public AdminServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    public AdminServlet() throws IOException {
        this(new TemplateProcessor());
    }

/*
    private static String prepareUserListTable( List<UserDataSet> dataSets ) {
        StringBuilder strConst = new StringBuilder();

        dataSets.forEach( x -> strConst.append(TAB_LINE_START)
                .append(x.getId())
                .append(TAB_LINE_MIDDLE)
                .append(x.getName())
                .append(TAB_LINE_MIDDLE)
                .append(x.getAge())
                .append(TAB_LINE_MIDDLE)
                .append(x.getAddress())
                .append(TAB_LINE_MIDDLE)
                .append(x.getPhones())
                .append(TAB_LINE_END));

        return  strConst.toString();
    }
*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBService dbService = (DBService) request.getSession().getAttribute("dbService");

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
        DBService dbService = (DBService) request.getSession().getAttribute("dbService");
        List<UserDataSet> usrList;

        try {
         if ("find_user".equals( action )) {
               UserDataSet   usr = dbService.read(Long.valueOf(request.getParameter("f_user")));
               usrList = ( usr == null ) ? null  :  List.of( usr );
               prepareRespondPage( request,response, ADMIN_PAGE_TEMPLATE, usrList );
            }
        else if ("added_user".equals( action )) {

            UserDataSet dataSet = UserDataSet.generateFromParameters( request.getParameter( "name"),
                                                                  request.getParameter( "age"),
                                                                  request.getParameter( "address"),
                                                                  request.getParameter( "phones"));
             dbService.save( dataSet );
             prepareRespondPage( request,response, ADMIN_PAGE_TEMPLATE, dbService.readAll() );

        }

    } catch (Exception e) {
        e.printStackTrace();
    }


}

    private void prepareRespondPage( HttpServletRequest request,
                                     HttpServletResponse response,
                                     String pageName,
                                     List<UserDataSet> usrList ) throws Exception {

        Map<String, Object> pageVariables = new HashMap<>();
        DBService dbService = (DBService) request.getSession().getAttribute( "dbService");

        pageVariables.put("items", usrList);
        pageVariables.put("login", request.getSession().getAttribute("login") );
        pageVariables.put("total", dbService.getCount() );

        String page = templateProcessor.getPage(pageName, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
