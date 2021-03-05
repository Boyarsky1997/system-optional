package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet {
    CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User client = (User) session.getAttribute("client");
        List<Course> courseList = courseDAO.getStudentCourse(client.getId());
        req.setAttribute("courseList", courseList);
        req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
    }

}
