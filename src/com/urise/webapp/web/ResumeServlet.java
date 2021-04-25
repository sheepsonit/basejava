package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        sqlStorage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String param = request.getParameter("name");
//        response.getWriter().write("Hello " + (param == null ? "Resumes!" : param + "!"));
        List<Resume> resumes = sqlStorage.getAllSorted();
        Writer writer = response.getWriter();
        writer.write("<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">" +
                "    <title>Список всех резюме.</title>" +
                "</head>" +
                "<body>" +
                "<section>" +
                "<table border='1' cellpadding='8' cellspacing='0'>" +
                "   <tr>" +
                "   <th>Имя</th>" +
                "   <th>Email</th>" +
                "   </tr>");
        for (Resume resume : resumes) {
            writer.write("<tr>" +
                    "<td><a href='resume?uuid=' " + resume.getUuid() + "'>" + resume.getFullName() + "</a></td>" +
                    "<td>" + resume.getContact(ContactType.MAIL) + "</td>" +
                    "</tr>");
        }
        writer.write("</table>" +
                "</section>" +
                "</body>" +
                "</html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
