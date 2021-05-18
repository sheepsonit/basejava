package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.JsonParser;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
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
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume existResume;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                existResume = sqlStorage.get(uuid);
                break;
            case "create":
                existResume = new Resume("New resume");
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", existResume);
        request.getRequestDispatcher(
                "view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        Resume resume;
        try {
            resume = sqlStorage.get(uuid);
            resume.setFullName(fullName);
            updateResume(resume, request);
        } catch (NotExistStorageException e) {
            resume = new Resume(uuid, fullName);
            sqlStorage.save(resume);
            updateResume(resume, request);
        } finally {
            response.sendRedirect("resume");
        }
    }

    private void updateResume(Resume resume, HttpServletRequest request) {

        for (ContactType type : ContactType.values()) {
            String contactValue = request.getParameter(type.name());
            if (contactValue.isEmpty() && contactValue.trim().isEmpty()) {
                resume.getContacts().remove(type);
            } else {
                resume.addContact(type, contactValue);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    String txtSection = request.getParameter(sectionType.name());
                    if (!txtSection.isEmpty()) {
                        resume.addSection(sectionType, new TextSection(txtSection.trim()));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    String bullListSection = request.getParameter(sectionType.name());
                    if (!bullListSection.isEmpty()) {
                        List<String> bulletedList = Arrays.asList(bullListSection.trim().split("\r\n"));
                        resume.addSection(sectionType, new BulletedListSection(bulletedList));
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    break;
            }
        }

        sqlStorage.update(resume);
    }
}
