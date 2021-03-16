package com.github.boyarsky1997.systemoptional.servlets;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutServletTest {
    private LogOutServlet logOutServlet;

    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    HttpSession mockSession;
    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        logOutServlet = new LogOutServlet();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);

        logOutServlet.doGet(mockRequest,mockResponse);

        Mockito.verify(mockRequest).getSession(false);
        Mockito.verify(mockSession).invalidate();
        Mockito.verify(mockResponse).sendRedirect("/");

    }
}