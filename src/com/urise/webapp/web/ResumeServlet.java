package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        sqlStorage =  Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String param = request.getParameter("name");
//        response.getWriter().write("Hello " + (param == null ? "Resumes!" : param + "!"));
        PrintWriter printWriter = response.getWriter();
        List<Resume> resumes = sqlStorage.getAllSorted();
        printWriter.println("Количество резюме: " + resumes.size());
        printWriter.println("<h1>Список резюме</h1>");
        printWriter.println("<table>");
        printWriter.println("<tr>");
        printWriter.println("<th>Uuid</th>");
        printWriter.println("<th>Full name</th>");
        printWriter.println("</tr>");
        for (Resume resume : resumes) {
            printWriter.println("<tr>");
            printWriter.println("<td>" + resume.getUuid() + "</td>");
            printWriter.println("<td>" + resume.getFullName() + "</td>");
            printWriter.println("</tr>");
        }
        printWriter.println("</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
