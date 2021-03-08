<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Profile</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form class="container g-3 col-sm-6" action="/welcome" method="get">
    <h1>Welcome, ${sessionScope.client.name} ${sessionScope.client.surname}</h1>
    <c:if test="${sessionScope.client.role == 'STUDENT'}">
        <li class="list-group-item">Courses you are subscribed to:</li>
    </c:if>
    <c:if test="${sessionScope.client.role == 'TEACHER'}">
        <li class="list-group-item">Your courses:</li>
    </c:if>
    <ul class="list-group list-group-flush">
        <c:forEach var="course" items="${courseList}">
            <li class="list-group-item"><a class="nav-link" href="/course?id=${course.getId()}">${course.getTitle()}</a>
            </li>
        </c:forEach>
    </ul>
    <ul class="list-group list-group-flush">
        <c:forEach var="comment" items="${commentList}">
            <figure>
                <blockquote class="blockquote">
                    <p>${comment.getMessage()}</p>
                </blockquote>
                <figcaption class="blockquote-footer">
                        ${comment.getNameTeacher()} ${comment.getTeacherSurname()}
                </figcaption>
            </figure>
        </c:forEach>
    </ul>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
</body>
</html>
