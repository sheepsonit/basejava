package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid").trim();
        String fullName = request.getParameter("fullName").trim();

        Resume resume;
        try {
            resume = sqlStorage.get(uuid);
            resume.setFullName(fullName);
            updateResume(resume, request);
        } catch (NotExistStorageException e) {
            if (!fullName.isEmpty()) {
                resume = new Resume(uuid, fullName);
                sqlStorage.save(resume);
                updateResume(resume, request);
            }
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
                    String txtSection = request.getParameter(sectionType.name()).trim();
                    if (!txtSection.isEmpty()) {
                        resume.addSection(sectionType, new TextSection(txtSection));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    String bullListSection = request.getParameter(sectionType.name()).trim();
                    if (!bullListSection.isEmpty()) {
                        List<String> bulletedList = new ArrayList<>(Arrays.asList(bullListSection.split("\r\n")));
                        bulletedList.removeIf(String::isEmpty);
                        resume.addSection(sectionType, new BulletedListSection(bulletedList));
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    String[] updateOrganizations = request.getParameterValues("organizationName" + sectionType.name());
                    String[] newOrganizations = request.getParameterValues("newOrganizationName" + sectionType.name());
                    List<Experience> experiences = new ArrayList<>();

                    if (updateOrganizations != null) {
                        experiences.addAll(readExistOrganizationSection(
                                updateOrganizations,
                                request));
                    }

                    experiences.addAll(readNewOrganizationSection(newOrganizations,
                            sectionType.name(),
                            request)
                    );

                    if (!experiences.isEmpty()) {
                        resume.addSection(sectionType, new OrganizationSection(experiences));
                    }

                    break;
            }
        }

        sqlStorage.update(resume);
    }

    private List<Experience> readExistOrganizationSection(String[] organizations, HttpServletRequest request) {
        List<Experience> experiences = new ArrayList<>();
        for (String organization : organizations) {
            String name = organization.trim();
            if (!name.isEmpty()) {

                String url = request.getParameter("urlOf" + name).trim();

                List<Experience.DateIntervalExperience> dates = readDateIntervals(
                        request.getParameterValues("dateStartOf" + name),
                        request.getParameterValues("dateEndOf" + name),
                        request.getParameterValues("mainOf" + name),
                        request.getParameterValues("noteOf" + name)
                );

                dates.addAll(readDateIntervals(
                        request.getParameterValues("newDateStart" + name),
                        request.getParameterValues("newDateEnd" + name),
                        request.getParameterValues("newMain" + name),
                        request.getParameterValues("newNote" + name)
                ));

                experiences.add(new Experience(new Link(name, url), dates));
            }
        }
        return experiences;
    }

    private List<Experience> readNewOrganizationSection(String[] organizations, String type, HttpServletRequest request) {
        List<Experience> experiences = new ArrayList<>();
        for (String organization : organizations) {
            String name = organization.trim();
            if (!name.isEmpty()) {

                String url = request.getParameter("newOrganizationUrl" + type).trim();

                List<Experience.DateIntervalExperience> dates = readDateIntervals(
                        request.getParameterValues("newDateStart" + type),
                        request.getParameterValues("newDateEnd" + type),
                        request.getParameterValues("newMain" + type),
                        request.getParameterValues("newNote" + type)
                );
                if (!dates.isEmpty()) {
                    experiences.add(new Experience(new Link(name, url), dates));
                }
            }
        }
        return experiences;
    }

    private List<Experience.DateIntervalExperience> readDateIntervals(String[] datesStart, String[] datesEnd, String[] mains, String[] notes) {
        List<Experience.DateIntervalExperience> dates = new ArrayList<>();

        for (int j = 0; j < datesStart.length; j++) {
            String dateStart = datesStart[j].trim();
            String dateEnd = datesEnd[j].trim();
            String mainInfo = mains[j].trim();

            if (!dateStart.isEmpty() && !dateEnd.isEmpty() && !mainInfo.isEmpty()) {
                dates.add(new Experience.DateIntervalExperience(
                        YearMonth.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM")),
                        YearMonth.parse(dateEnd, DateTimeFormatter.ofPattern("yyyy-MM")),
                        mainInfo,
                        notes[j])
                );
            }
        }
        return dates;
    }
}
