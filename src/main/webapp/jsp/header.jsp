<%@ page import="com.github.boyarsky1997.systemoptional.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" style="margin-left: 20px; color: orangered" href="/">Geus</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="row collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="/">Home </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/courses">Courses</a>
            </li>
            <c:if test="${sessionScope.client.role=='TEACHER'}">
                <li class="nav-item">
                    <a class="nav-link" href="/createCourse">Create course</a>
                </li>
            </c:if>
            <div class="navbar-nav ms-auto">
                <c:if test="${sessionScope.client != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/profile">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">logout</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.client == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/registration">Registration</a>
                    </li>
                </c:if>
            </div>
        </ul>
    </div>
</nav>
