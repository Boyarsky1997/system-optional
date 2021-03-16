package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.CommentDAO;
import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.*;
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
import java.util.Arrays;
import java.util.List;

public class ProfileServletTest {

    private ProfileServlet profileServlet;
    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private CommentDAO mockCommentDAO;
    @Mock
    private CourseDAO mockCourseDAO;

    @Mock
    private RequestDispatcher mockRequestDispatcher;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        profileServlet = new ProfileServlet(mockCourseDAO, mockCommentDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        User student = new Student();
        student.setId(1);
        student.setRole(Role.STUDENT);
        Mockito.when(mockRequest.getSession())
                .thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(student);
        List<Course> courseList = Arrays.asList(new Course(), new Course(), new Course());
        Mockito.when(mockCourseDAO.getStudentCourse(student.getId()))
                .thenReturn(courseList);
        List<Comment> commentList = Arrays.asList(new Comment(), new Comment(), new Comment());
        Mockito.when(mockCommentDAO.getAllCommentOnUserId(student.getId()))
                .thenReturn(commentList);

        profileServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("courseList", courseList);
        Mockito.verify(mockRequest).setAttribute("commentList", commentList);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/welcome.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
        Mockito.verify(mockCourseDAO,Mockito.never()).getAllCourseOnTeacherId(student.getId());
    }
    @Test
    public void testDoGetIfRoleEqualsTeacher() throws ServletException, IOException {
        User teacher = new Teacher();
        teacher.setId(1);
        teacher.setRole(Role.TEACHER);
        Mockito.when(mockRequest.getSession())
                .thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute("client"))
                .thenReturn(teacher);
        List<Course> courseList = Arrays.asList(new Course(), new Course(), new Course());
        Mockito.when(mockCourseDAO.getAllCourseOnTeacherId(teacher.getId()))
                .thenReturn(courseList);

        profileServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockCourseDAO,Mockito.never()).getStudentCourse(teacher.getId());
        Mockito.verify(mockRequest).setAttribute("courseList", courseList);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/welcome.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
        Mockito.verify(mockCourseDAO).getAllCourseOnTeacherId(teacher.getId());
    }
}