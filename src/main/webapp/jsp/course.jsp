<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Course</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container g-3 col-sm-8">
    <div class="row justify-content-md-center">
        <p></p>
        <p class="lead">
            <b>Title: </b>${course.getTitle()}
        </p>
        <p class="lead">
            <b>Author: </b>${teacher.getName()} ${teacher.getSurname()}
        </p>
        <p class="lead">
            <b>Description: </b>
        </p>
        <p class="lead">
            ${course.getDescription()}
        </p>
        <c:if test="${sessionScope.client.role == 'STUDENT'}">
            <form class="row g-3" method="post" action="/course?id=${course.getId()}">
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button class="btn btn-primary me-md-2" type="submit"
                            <c:if test="${isAssign}">
                                disabled
                            </c:if>
                    >Join
                    </button>
                </div>
            </form>
        </c:if>
        <c:if test="${sessionScope.client.role == 'TEACHER'}">
            <form class="row g-3" method="get" action="/edit">
                <input type="hidden" id="id" name="id" value="${course.getId()}">
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button class="btn btn-primary me-md-2" type="submit">Edit</button>

                </div>
            </form>
            <form class="row g-3" method="get" action="/ratting?id=${course.getId()}">
                <input type="hidden" id="id1" name="id1" value="${course.getId()}">
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button class="btn btn-primary me-md-2" type="submit">Leave a comment</button>
                </div>
            </form>
        </c:if>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
</body>
</html>
