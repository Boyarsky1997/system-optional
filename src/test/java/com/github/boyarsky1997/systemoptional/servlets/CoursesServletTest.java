package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.Course;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoursesServletTest {
    @Mock
    private UserDAO mockUserDAO;
    @Mock
    private CourseDAO mockCourseDAO;

    @Mock
    private HttpServletRequest mockReq;

    @Mock
    private HttpServletResponse mockResp;

    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private CoursesServlet coursesServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        coursesServlet = new CoursesServlet(mockCourseDAO, mockUserDAO);
        Mockito.when(mockReq.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "мова", 2, null));
        courses.add(new Course(2, null, 3, null));
        Mockito.when(mockCourseDAO.getAll())
                .thenReturn(courses);

        List<User> users = new ArrayList<>();
        users.add(new Teacher(2, "teac", null, null, null));
        users.add(new Teacher(3, "ac", null, null, null));
        Mockito.when(mockUserDAO.getAllTeachersById(Mockito.anyList()))
                .thenReturn(users);

        coursesServlet.doGet(mockReq, mockResp);

        Mockito.verify(mockCourseDAO).getAll();
        Mockito.verify(mockUserDAO).getAllTeachersById(Arrays.asList(2, 3));
        List<Course> courseList = new ArrayList<>();
        Course course1 = new Course(1, "мова", 2, null);
        course1.setTeacher(users.get(0));
        Course course2 = new Course(2, null, 3, null);
        course2.setTeacher(users.get(1));
        courseList.add(course1);
        courseList.add(course2);
        Mockito.verify(mockReq).setAttribute("courseList", courseList);
        Mockito.verify(mockReq).getRequestDispatcher("/jsp/courses.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockReq, mockResp);
    }
}