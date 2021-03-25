package com.github.boyarsky1997.systemoptional.filter;

import com.github.boyarsky1997.systemoptional.model.Role;
import com.github.boyarsky1997.systemoptional.model.Student;
import com.github.boyarsky1997.systemoptional.model.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthorizationFilterTest {
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private FilterChain mockFilterChain;
    @Mock
    private FilterConfig mockFilterConfig;


    private AuthorizationFilter authorizationFilter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        authorizationFilter = new AuthorizationFilter();
    }

    @Test
    public void testDoFilter() throws IOException, ServletException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(2);
        student.setName("name");
        student.setRole(Role.STUDENT);
        student.setSurname("surname");
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn("/login");


        authorizationFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        Mockito.verify(mockResponse).sendRedirect("/profile");
        Mockito.verify(mockFilterChain, Mockito.never()).doFilter(mockRequest, mockResponse);
    }

    @Test
    public void testDoFilterIfSessionEqualsNull() throws IOException, ServletException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(null);
        String a = "a,b,c";
        Mockito.when(mockFilterConfig.getInitParameter("blacklist"))
                .thenReturn(a);
        List<String> blacklists = Arrays.asList(a.split(","));
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn(blacklists.get(0));
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn(blacklists.get(1));
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn(blacklists.get(2));

        authorizationFilter.init(mockFilterConfig);
        authorizationFilter.doFilter(mockRequest, mockResponse, mockFilterChain);


        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/profile");
        Mockito.verify(mockFilterChain).doFilter(mockRequest, mockResponse);
    }

    @Test
    public void testDoFilterIfBlackListNotEndWith() throws IOException, ServletException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(null);
        String a = "a,b,c";
        Mockito.when(mockFilterConfig.getInitParameter("blacklist"))
                .thenReturn(a);
        List<String> blacklists = Arrays.asList(a.split(","));
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn("f");
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn("w");
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn("d");

        authorizationFilter.init(mockFilterConfig);
        authorizationFilter.doFilter(mockRequest, mockResponse, mockFilterChain);


        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/profile");
        Mockito.verify(mockFilterChain, Mockito.never()).doFilter(mockRequest, mockResponse);
    }

    @Test
    public void testDoFilterTwo() throws IOException, ServletException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User user = new Student();
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(user);
        String a = "a,b,c";
        Mockito.when(mockFilterConfig.getInitParameter("blacklist"))
                .thenReturn(a);
        Mockito.when(mockRequest.getRequestURI())
                .thenReturn("/ff");


        authorizationFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

        Mockito.verify(mockFilterChain).doFilter(mockRequest,mockResponse);

    }
}