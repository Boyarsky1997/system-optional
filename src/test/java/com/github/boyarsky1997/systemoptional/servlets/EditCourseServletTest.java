package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.model.Role;
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

public class EditCourseServletTest {

    private EditCourseServlet editCourseServlet;
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
        editCourseServlet = new EditCourseServlet(mockCourseDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGetIfCourseIdEqualsNull() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn(null);

        editCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendError(404);
        Mockito.verify(mockResponse, Mockito.never()).sendError(403);
        Mockito.verify(mockRequest, Mockito.never()).getRequestDispatcher("/jsp/edit.jsp");
    }

    @Test
    public void testDoGetIfCourseIdNotEqualsNull() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn("3");
        Course course = new Course();
        course.setTeacherId(3);
        Mockito.when(mockCourseDAO.getById(3))
                .thenReturn(course);
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User teacher = new Teacher();
        teacher.setId(3);
        teacher.setRole(Role.TEACHER);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(teacher);

        editCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("course", course);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/edit.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
        Mockito.verify(mockResponse, Mockito.never()).sendError(404);
    }

    @Test
    public void testDoGetIfCourseGetTeacherIdNotEqualsClientId() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn("3");
        Course course = new Course();
        course.setTeacherId(3);
        Mockito.when(mockCourseDAO.getById(3))
                .thenReturn(course);
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User teacher = new Teacher();
        teacher.setId(2);
        teacher.setRole(Role.TEACHER);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(teacher);

        editCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest, Mockito.never()).setAttribute("course", course);
        Mockito.verify(mockRequest, Mockito.never()).getRequestDispatcher("/jsp/edit.jsp");
        Mockito.verify(mockRequestDispatcher, Mockito.never()).forward(mockRequest, mockResponse);
        Mockito.verify(mockResponse).sendError(403);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String id = "3";
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn(id);
        String title = "ff";
        Mockito.when(mockRequest.getParameter("title"))
                .thenReturn(title);
        String description = "fff";
        Mockito.when(mockRequest.getParameter("description"))
                .thenReturn(description);

        editCourseServlet.doPost(mockRequest,mockResponse);

        Mockito.verify(mockCourseDAO).update(Integer.parseInt(id),title,description);
        Mockito.verify(mockRequest).setCharacterEncoding("UTF-8");
        Mockito.verify(mockResponse).sendRedirect("/course?id=3");
    }
}