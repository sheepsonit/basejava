<jsp:useBean id="resume" scope="request" type="com.urise.webapp.model.Resume"/>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.BulletedListSection" %>
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
                                      type="text"
                                      rows="2"
                                      cols="200"><c:if
                                test="<%=resume.getSection(SectionType.PERSONAL) != null%>"><%=((TextSection) resume.getSection(SectionType.PERSONAL)).getContent()%></c:if></textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType.name().equals('OBJECTIVE')}">
                        <dd><textarea name="OBJECTIVE"
                                      type="text"
                                      rows="2"
                                      cols="200"><c:if
                                test="<%=resume.getSection(SectionType.OBJECTIVE) != null %>"><%=((TextSection) resume.getSection(SectionType.OBJECTIVE)).getContent()%></c:if></textarea>
                        </dd>
                    </c:when>

                    <c:when test="${sectionType.name().equals('ACHIEVEMENT')}">
                        <dd>
                        <textarea name="ACHIEVEMENT"
                                  type="text"
                                  rows="6"
                                  cols="200"><c:if
                                test="<%=resume.getSection(SectionType.OBJECTIVE) != null %>"><c:forEach
                                items="<%=((BulletedListSection) resume.getSection(SectionType.ACHIEVEMENT)).getContent()%>"
                                var="achievement">${achievement}${newLine}</c:forEach></c:if></textarea>
                        </dd>

                    </c:when>

                    <c:when test="${sectionType.name().equals('QUALIFICATION')}">
                        <dd>
                        <textarea name="QUALIFICATION"
                                  type="text"
                                  rows="6"
                                  cols="200"><c:if
                                test="<%=resume.getSection(SectionType.OBJECTIVE) != null %>"><c:forEach
                                items="<%=((BulletedListSection) resume.getSection(SectionType.QUALIFICATION)).getContent()%>"
                                var="qualification">${qualification}${newLine}</c:forEach></c:if></textarea>
                        </dd>
                    </c:when>

                    <c:otherwise>
                        <dd><textarea name="section"
                                      type="text"
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
