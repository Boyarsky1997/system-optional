<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Ratting</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<form action="/ratting" method="post">
    <ul class="list-group list-group-flush">
        <li class="list-group-item">Students:</li>
        <div class="input-group mb-3">
            <label class="input-group-text" for="studentId">Choose student:</label>
            <select class="form-select" id="studentId" name="studentId">
                <option selected>Choose...</option>
                <c:forEach var="student" items="${studentsList}">
                    <option value="${student.getId()}">${student.getName()} ${student.getSurname()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="exampleFormControlTextarea1" class="form-label">Comment</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" name="comment" rows="3"
                      placeholder="Enter the text..."></textarea>
        </div>
        <p></p>
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Send</button>
        </div>
    </ul>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
</body>
</html>
