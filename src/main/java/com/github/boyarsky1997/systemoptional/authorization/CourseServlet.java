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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CourseServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CourseServlet.class);
    CourseDAO repository = new CourseDAO();
    UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Course course = repository.getById(id);
        req.setAttribute("course", course);
        System.out.println(course);
        User teacher = userDAO.getTeacher(course.getTeacherId());
        req.setAttribute("teacher", teacher);
        HttpSession session = req.getSession(false);
        User client = (User) session.getAttribute("client");
        if (client != null) {
            int idUser = client.getId();
            int idCourse = course.getId();
            boolean check = repository.check(idUser, idCourse);
            if (check) {
                req.setAttribute("isAssign", true);
            }
        }
        req.getRequestDispatcher("/jsp/course.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("client");
        repository.addUserIdAndCourseId(user.getId(), Integer.parseInt(id));
        doGet(req, resp);
    }
}
