package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.Student;
import com.github.boyarsky1997.systemoptional.model.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RegistrationServletTest {

    private RegistrationServlet registrationServlet;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private UserDAO mockUserDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        registrationServlet = new RegistrationServlet(mockUserDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoPostIfRoleEqualsTeacher() throws ServletException, IOException {
        String name = "name";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String surname = "surname";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "login";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "password";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);
        String role = "TEACHER";
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role);
        Mockito.when(mockUserDAO.checkExistLogin(login))
                .thenReturn(true);

        registrationServlet.doPost(mockRequest, mockResponse);

        User user = new Student(null, login, password, name, surname);

        Mockito.verify(mockUserDAO, Mockito.never()).insertUser(user);
        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/login");
        Mockito.verify(mockRequest).setAttribute("message", "This login already exists");
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/registration.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPostIfRoleEqualsStudent() throws ServletException, IOException {
        String name = "name";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String surname = "surname";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "login";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "password";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);
        String role = "STUDENT";
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role);
        Mockito.when(mockUserDAO.checkExistLogin(login))
                .thenReturn(false);

        registrationServlet.doPost(mockRequest, mockResponse);

        User user = new Student(null, login, password, name, surname);

        Mockito.verify(mockUserDAO).insertUser(user);
        Mockito.verify(mockResponse).sendRedirect("/login");
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("message", "This login already exists");
        Mockito.verify(mockRequest, Mockito.never()).getRequestDispatcher("/jsp/registration.jsp");
        Mockito.verify(mockRequestDispatcher, Mockito.never()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String name = "name";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn("");
        String surname = "surname";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "login";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "password";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);
        String role = "STUDENT";
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role);

        registrationServlet.doPost(mockRequest, mockResponse);

        User user = new Student(null, login, password, "", surname);

        Mockito.verify(mockResponse).sendRedirect("/registration");
        Mockito.verify(mockUserDAO, Mockito.never()).insertUser(user);
        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/login");
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("message", "This login already exists");
        Mockito.verify(mockRequest, Mockito.never()).getRequestDispatcher("/jsp/registration.jsp");
        Mockito.verify(mockRequestDispatcher, Mockito.never()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        registrationServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/registration.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}