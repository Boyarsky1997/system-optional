<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
              crossorigin="anonymous">
        <title>CreateCourse</title>
    </head>
</head>

<body>
<form action="/createCourse" method="post" class="container g-3 col-sm-6">
    <h1>Create Course</h1>
    <div class="form-group row">
        <label for="inputName" class="col-sm-2 col-form-label">Title</label>
        <div class="col-sm-10">
            <input type="text" name="name" class="form-control" id="inputName">
        </div>
    </div>
    <p></p>
    <div class="mb-3">
        <label for="exampleFormControlTextarea1" class="form-label">Description</label>
        <textarea class="form-control" id="exampleFormControlTextarea1" name="description" rows="3"></textarea>
    </div>
    <p></p>
    <div class="col-12">
        <button type="submit" class="btn btn-primary">Create</button>
    </div>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
</body>
</html>
