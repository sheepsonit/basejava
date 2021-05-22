<jsp:useBean id="resume" scope="request" type="com.urise.webapp.model.Resume"/>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Редактирование резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input name="fullName" type="text" size="50" value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты: </h3>
        <c:forEach items="${ContactType.values()}" var="type">
            <dl>
                <dt>${type.title}</dt>
                <dd><input name="${type.name()}" type="text" size="30" value="${resume.getContact(type)}"></dd>

            </dl>
        </c:forEach>

        <h3>Секции: </h3>
        <c:set var="newLine" scope="page" value="
"/>
        <c:forEach items="${SectionType.values()}" var="sectionType">
            <dl>
                <dt>${sectionType.title}</dt>
                <c:choose>
                    <c:when test="${sectionType.name().equals('PERSONAL')}">
                        <dd><textarea name="PERSONAL"
                                      rows="2"
                                      cols="200"><c:if
                                test="<%=resume.getSection(SectionType.PERSONAL) != null%>"><%=((TextSection) resume.getSection(SectionType.PERSONAL)).getContent()%>
                        </c:if></textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType.name().equals('OBJECTIVE')}">
                        <dd><textarea name="OBJECTIVE"
                                      rows="2"
                                      cols="200"><c:if
                                test="<%=resume.getSection(SectionType.OBJECTIVE) != null %>"><%=((TextSection) resume.getSection(SectionType.OBJECTIVE)).getContent()%>
                        </c:if></textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType.name().equals('ACHIEVEMENT')}">
                        <dd>
                        <textarea name="ACHIEVEMENT"
                                  rows="6"
                                  cols="200"><c:if
                                test="<%=resume.getSection(SectionType.ACHIEVEMENT) != null %>"><c:forEach
                                items="<%=((BulletedListSection) resume.getSection(SectionType.ACHIEVEMENT)).getContent()%>"
                                var="achievement">${achievement}${newLine}</c:forEach></c:if></textarea>
                        </dd>

                    </c:when>

                    <c:when test="${sectionType.name().equals('QUALIFICATION')}">
                        <dd>
                        <textarea name="QUALIFICATION"
                                  rows="6"
                                  cols="200"><c:if
                                test="<%=resume.getSection(SectionType.QUALIFICATION) != null %>"><c:forEach
                                items="<%=((BulletedListSection) resume.getSection(SectionType.QUALIFICATION)).getContent()%>"
                                var="qualification">${qualification}${newLine}</c:forEach></c:if></textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType.name().equals('EDUCATION')}">
                        <dd>
                            <c:if
                                    test="<%=resume.getSection(SectionType.EDUCATION) != null %>"><c:forEach
                                    items="<%=((OrganizationSection) resume.getSection(SectionType.EDUCATION)).getContent()%>"
                                    var="education">
                                <dl>
                                    <dt>Название организации:</dt>
                                    <dd><input name="organizationNameEDUCATION"
                                               type="text"
                                               size="170"
                                               value="${education.organization.name}"></dd>
                                </dl>

                                <dl>
                                    <dt>Ссылка на сайт организации:</dt>
                                    <dd><input name="urlOf${education.organization.name}"
                                               type="text"
                                               size="170"
                                               value="${education.organization.url}"></dd>
                                </dl>
                                <c:forEach
                                        items="${education.dates}"
                                        var="dates">
                                    <p>
                                    <dl>
                                        <dt>Дата начала:</dt>
                                        <dd><input name="dateStartOf${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.dateStart}"></dd>
                                    </dl>

                                    <dl>
                                        <dt>Дата окончания:</dt>
                                        <dd><input name="dateEndOf${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.dateEnd}"></dd>
                                    </dl>

                                    <dl>
                                        <dt>Основная информация:</dt>
                                        <dd><input name="mainOf${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.mainInfo}"></dd>
                                    </dl>

                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><input name="noteOf${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.note}"></dd>
                                    </dl>
                                    </p>
                                    <p>
                                    <dl>
                                        <dt>Дата начала:</dt>
                                        <dd><input name="newDateStart${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>

                                    <dl>
                                        <dt>Дата окончания:</dt>
                                        <dd><input name="newDateEnd${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>

                                    <dl>
                                        <dt>Основная информация:</dt>
                                        <dd><input name="newMain${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>

                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><input name="newNote${education.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>
                                    </p>
                                </c:forEach>
                                <hr>
                            </c:forEach></c:if>
                            <dl>
                                <dt>Название организации:</dt>
                                <dd><input name="newOrganizationNameEDUCATION"
                                           type="text"
                                           size="170"
                                           value=""></dd>
                            </dl>

                            <dl>
                                <dt>Ссылка на сайт организации:</dt>
                                <dd><input name="newOrganizationUrlEDUCATION"
                                           type="text"
                                           size="170"
                                           value=""></dd>
                            </dl>

                            <p>
                            <dl>
                                <dt>Дата начала:</dt>
                                <dd><input name="newDateStartEDUCATION"
                                           type="text"
                                           size="170"
                                           value=""></dd>
                            </dl>

                            <dl>
                                <dt>Дата окончания:</dt>
                                <dd><input name="newDateEndEDUCATION"
                                           type="text"
                                           size="170"
                                           value=""></dd>
                            </dl>

                            <dl>
                                <dt>Основная информация:</dt>
                                <dd><input name="newMainEDUCATION"
                                           type="text"
                                           size="170"
                                           value=""></dd>
                            </dl>

                            <dl>
                                <dt>Описание:</dt>
                                <dd><input name="newNoteEDUCATION"
                                           type="text"
                                           size="170"
                                           value=""></dd>
                            </dl>
                            </p>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType.name().equals('EXPERIENCE')}">
                        <dd>
                            <c:if
                                    test="<%=resume.getSection(SectionType.EXPERIENCE) != null %>"><c:forEach
                                    items="<%=((OrganizationSection) resume.getSection(SectionType.EXPERIENCE)).getContent()%>"
                                    var="experience">
                                <dl>
                                    <dt>Название организации:</dt>
                                    <dd><input name="organizationNameEXPERIENCE"
                                               type="text"
                                               size="170"
                                               value="${experience.organization.name}"></dd>
                                </dl>

                                <dl>
                                    <dt>Ссылка на сайт организации:</dt>
                                    <dd><input name="urlOf${experience.organization.name}"
                                               type="text"
                                               size="170"
                                               value="${experience.organization.url}"></dd>
                                </dl>
                                <c:forEach
                                        items="${experience.dates}"
                                        var="dates">
                                    <p>
                                    <dl>
                                        <dt>Дата начала:</dt>
                                        <dd><input name="dateStartOf${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.dateStart}"></dd>
                                    </dl>

                                    <dl>
                                        <dt>Дата окончания:</dt>
                                        <dd><input name="dateEndOf${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.dateEnd}"></dd>
                                    </dl>

                                    <dl>
                                        <dt>Основная информация:</dt>
                                        <dd><input name="mainOf${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.mainInfo}"></dd>
                                    </dl>

                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><input name="noteOf${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value="${dates.note}"></dd>
                                    </dl>
                                    </p>
                                    <p>
                                    <dl>
                                        <dt>Дата начала:</dt>
                                        <dd><input name="newDateStart${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>

                                    <dl>
                                        <dt>Дата окончания:</dt>
                                        <dd><input name="newDateEnd${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>

                                    <dl>
                                        <dt>Основная информация:</dt>
                                        <dd><input name="newMain${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>

                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><input name="newNote${experience.organization.name}"
                                                   type="text"
                                                   size="170"
                                                   value=""></dd>
                                    </dl>
                                    </p>
                                </c:forEach>
                                <hr>
                            </c:forEach></c:if>
                        <dl>
                            <dt>Название организации:</dt>
                            <dd><input name="newOrganizationNameEXPERIENCE"
                                       type="text"
                                       size="170"
                                       value=""></dd>
                        </dl>

                        <dl>
                            <dt>Ссылка на сайт организации:</dt>
                            <dd><input name="newOrganizationUrlEXPERIENCE"
                                       type="text"
                                       size="170"
                                       value=""></dd>
                        </dl>

                        <p>
                        <dl>
                            <dt>Дата начала:</dt>
                            <dd><input name="newDateStartEXPERIENCE"
                                       type="text"
                                       size="170"
                                       value=""></dd>
                        </dl>

                        <dl>
                            <dt>Дата окончания:</dt>
                            <dd><input name="newDateEndEXPERIENCE"
                                       type="text"
                                       size="170"
                                       value=""></dd>
                        </dl>

                        <dl>
                            <dt>Основная информация:</dt>
                            <dd><input name="newMainEXPERIENCE"
                                       type="text"
                                       size="170"
                                       value=""></dd>
                        </dl>

                        <dl>
                            <dt>Описание:</dt>
                            <dd><input name="newNoteEXPERIENCE"
                                       type="text"
                                       size="170"
                                       value=""></dd>
                        </dl>
                        </p>
                    </c:when>

                    <c:otherwise>
                        <dd><textarea name="section"
                                      rows="6"
                                      cols="200"></textarea>
                        </dd>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
