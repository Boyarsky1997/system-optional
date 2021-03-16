package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.Role;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInServletTest {

    private LogInServlet logInServlet;
    @Mock
    private HttpSession mockSession;
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
        logInServlet = new LogInServlet(mockUserDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        logInServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/login.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn("roman@gmail.com");
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn("123");
        User client = new Student();
        client.setLogin("roman@gmail.com");
        client.setPassword("123");
        client.setName("Roman");
        client.setSurname("Boy");
        client.setRole(Role.STUDENT);
        Mockito.when(mockUserDAO.get("roman@gmail.com", "123"))
                .thenReturn(client);
        Mockito.when(mockRequest.getSession(true))
                .thenReturn(mockSession);

        logInServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockRequest).getSession(true);
        Mockito.verify(mockSession).setAttribute("client", client);
        Mockito.verify(mockResponse).sendRedirect("/profile");
    }

    @Test
    public void testDoPostIfClientEquals() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn("");
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn("");
        Mockito.when(mockUserDAO.get("roman@gmail.com", "123"))
                .thenReturn(null);

        logInServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("unfaithful", "Incorrect login or password");
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/login.jsp");
        Mockito.verify(mockRequestDispatcher).include(mockRequest,mockResponse);
        Mockito.verify(mockRequest,Mockito.never()).getSession(true);
        Mockito.verify(mockResponse,Mockito.never()).sendRedirect("/profile");
    }
}