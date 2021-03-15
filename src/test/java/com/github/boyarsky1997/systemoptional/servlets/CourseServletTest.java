package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.Course;
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

public class CourseServletTest {

    @Mock
    private HttpSession mockSession;
    @Mock
    private UserDAO mockUserDAO;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private CourseDAO mockCourseDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private CourseServlet spyCourseServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        spyCourseServlet = Mockito.spy(new CourseServlet(mockCourseDAO, mockUserDAO));
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGetWhenIdIsEmpty() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter(Mockito.anyString())).thenReturn(null);

        spyCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/courses");
        Mockito.verify(mockCourseDAO, Mockito.never()).getById(Mockito.anyInt());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn("3");
        Course course = new Course();
        course.setId(3);
        course.setTeacherId(4);
        Mockito.when(mockCourseDAO.getById(Mockito.anyInt()))
                .thenReturn(course);
        User teacher = new Teacher();
        Mockito.when(mockUserDAO.getTeacher(course.getTeacherId()))
                .thenReturn(teacher);
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(2);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        Mockito.when(mockCourseDAO.checkIsAssignedStudentOnCourse(2, 3))
                .thenReturn(true);

        spyCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockCourseDAO).getById(3);
        Mockito.verify(mockRequest).setAttribute("course", course);
        Mockito.verify(mockUserDAO).getTeacher(4);
        Mockito.verify(mockRequest).setAttribute("teacher", teacher);
        Mockito.verify(mockRequest).setAttribute("isAssign", true);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/courses.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoGetWhenStudentNotAssigned() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn("3");
        Course course = new Course();
        course.setId(3);
        course.setTeacherId(4);
        Mockito.when(mockCourseDAO.getById(Mockito.anyInt()))
                .thenReturn(course);
        User teacher = new Teacher();
        Mockito.when(mockUserDAO.getTeacher(course.getTeacherId()))
                .thenReturn(teacher);
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(2);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        Mockito.when(mockCourseDAO.checkIsAssignedStudentOnCourse(2, 3))
                .thenReturn(false);

        spyCourseServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockCourseDAO).getById(3);
        Mockito.verify(mockRequest).setAttribute("course", course);
        Mockito.verify(mockUserDAO).getTeacher(4);
        Mockito.verify(mockRequest).setAttribute("teacher", teacher);
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("isAssign", true);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/course.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn("3");
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        User student = new Student();
        student.setId(1);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        Mockito.doNothing().when(spyCourseServlet).doGet(mockRequest, mockResponse);

        spyCourseServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockCourseDAO).addUserIdAndCourseId(student.getId(), 3);
        Mockito.verify(spyCourseServlet).doGet(mockRequest, mockResponse);
    }
}