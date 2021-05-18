<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border='1' cellpadding='8' cellspacing='0'>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <%--@elvariable id="resumes" type="java.util.List"--%>
        <c:forEach items="${resumes}" var="resume">
                <tr>
                    <td>
                        <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                    </td>
                    <td>
                            ${resume.getContact(ContactType.MAIL)}
                    </td>
                    <td>
                        <a href="resume?uuid=${resume.uuid}&action=delete">Delete</a>
                    </td>
                    <td>
                        <a href="resume?uuid=${resume.uuid}&action=edit">Edit</a>
                    </td>
                </tr>
        </c:forEach>
    </table>
    <hr>
    <a href="resume?action=create">Добавить новое резюме</a>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
