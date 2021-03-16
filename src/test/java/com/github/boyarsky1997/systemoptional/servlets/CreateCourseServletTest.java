package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.Role;
import com.github.boyarsky1997.systemoptional.model.Student;
import com.github.boyarsky1997.systemoptional.model.Teacher;
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

public class CreateCourseServletTest {

    private CreateCourseServlet createCourseServlet;
    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private CourseDAO mockCourseDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        createCourseServlet = new CreateCourseServlet(mockCourseDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGetWhenRoleEqualsStudent() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(2);
        student.setRole(Role.STUDENT);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        createCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendError(403);
        Mockito.verify(mockRequest, Mockito.never()).getRequestDispatcher("/jsp/createCourse.jsp");
        Mockito.verify(mockRequestDispatcher, Mockito.never()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoGetWhenRoleEqualsTeacher() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User teacher = new Teacher();
        teacher.setId(2);
        teacher.setRole(Role.TEACHER);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(teacher);
        createCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse, Mockito.never()).sendError(403);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/createCourse.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(1);
        student.setRole(Role.STUDENT);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn("roman");
        Mockito.when(mockRequest.getParameter("description"))
                .thenReturn("kdkdk");
        Mockito.when(mockCourseDAO.insert("roman","kdkdk",student.getId()))
                .thenReturn(3);

        createCourseServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setCharacterEncoding("UTF-8");
        Mockito.verify(mockResponse,Mockito.never()).sendRedirect("/createCourse");
        Mockito.verify(mockResponse).sendRedirect("/course?id=3");
    }

    @Test
    public void testDoPostIfNameAndDescriptionEmpty() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(1);
        student.setRole(Role.STUDENT);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn("");
        Mockito.when(mockRequest.getParameter("description"))
                .thenReturn("");

        createCourseServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/createCourse");
        Mockito.verify(mockCourseDAO, Mockito.never()).insert("name", "FG", 2);
        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/course?id=");
        Mockito.verify(mockRequest).setCharacterEncoding("UTF-8");
    }

}