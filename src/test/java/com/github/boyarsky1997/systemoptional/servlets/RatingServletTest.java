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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

public class RatingServletTest {

    private RatingServlet ratingServlet;
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
    @Mock
    private Supplier<Date> mockDate;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        ratingServlet = new RatingServlet(mockUserDAO, mockDate);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id1"))
                .thenReturn("3");
        List<User> studentList = Arrays.asList(new Student(), new Student(), new Student());
        Mockito.when(mockUserDAO.getAllStudentOnCourseId(3))
                .thenReturn(studentList);

        ratingServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("studentsList", studentList);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/ratting.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("studentId"))
                .thenReturn("3");
        Mockito.when(mockRequest.getParameter("comment"))
                .thenReturn("ffff");
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User teacher = new Student();
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(teacher);
        Mockito.when(mockDate.get())
                .thenReturn(new Date(1615903299L));

        ratingServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockUserDAO).addComment("ffff", teacher.getId(), 3, new Date(1615903299L));
    }

}