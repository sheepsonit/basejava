<jsp:useBean id="resume" scope="request" type="com.urise.webapp.model.Resume"/>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h1>
    <p>
        <c:forEach items="${resume.contacts}" var="contactEntry">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <c:forEach items="${resume.sections}" var="sectionEntry">
        <p>
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
                <c:set var="sectionType" scope="page" value="<%=sectionEntry.getKey()%>"/>
        <h1>
                ${sectionType.title}
        </h1>
        <p>
        <c:choose>
            <c:when test="${sectionType.name().equals('PERSONAL')}">
                <%=((TextSection) sectionEntry.getValue()).getContent()%>
            </c:when>
            <c:when test="${sectionType.name().equals('OBJECTIVE')}">
                <%=((TextSection) sectionEntry.getValue()).getContent()%>
            </c:when>
            <c:when test="${sectionType.name().equals('ACHIEVEMENT')}">
                <ul>
                    <c:forEach items="<%=((BulletedListSection)sectionEntry.getValue()).getContent()%>"
                               var="achievement">
                        <li>${achievement}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${sectionType.name().equals('QUALIFICATION')}">
                <ul>
                    <c:forEach items="<%=((BulletedListSection)sectionEntry.getValue()).getContent()%>"
                               var="qualification">
                        <li>${qualification}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${sectionType.name().equals('EDUCATION')}">
                <c:forEach items="<%=((OrganizationSection)sectionEntry.getValue()).getContent()%>"
                           var="education">
                    <h3><a href="${education.organization.url}">${education.organization.name}</a></h3>
                    <c:forEach items="${education.dates}" var="partOfEducation">
                        <p>${partOfEducation.dateStart} - ${partOfEducation.dateEnd}</p>
                        <p><b>${partOfEducation.mainInfo}</b></p>
                        <p>${partOfEducation.note}</p>
                    </c:forEach>
                </c:forEach>
            </c:when>
            <c:when test="${sectionType.name().equals('EXPERIENCE')}">
                <c:forEach items="<%=((OrganizationSection)sectionEntry.getValue()).getContent()%>"
                           var="experience">
                    <h3><a href="${experience.organization.url}">${experience.organization.name}</a></h3>
                    <c:forEach items="${experience.dates}" var="partOfExperience">
                        <p>${partOfExperience.dateStart} - ${partOfExperience.dateEnd}</p>
                        <p><b>${partOfExperience.mainInfo}</b></p>
                        <p>${partOfExperience.note}</p>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
        </p>
        </p>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

