package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CoursesServlet.class);
    CourseDAO repository = new CourseDAO();
    UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Course> courseList = repository.getAll();
        List<Integer> listId = new ArrayList<>();
        for (Course course : courseList) {
            listId.add(course.getTeacherId());
        }
        List<User> users = userDAO.allTeacher(listId);
        Map<Integer,User> allTeacher = new HashMap<>();
       for (int i = 0; i<users.size(); i++){
           allTeacher.put(listId.get(i),users.get(i));
       }
        for (Course course : courseList) {
            course.setTeacher(allTeacher.get(course.getTeacherId()));
        }

        logger.info(courseList);
        req.setAttribute("courseList", courseList);
        req.getRequestDispatcher("/jsp/courses.jsp").forward(req, resp);
    }

}
