package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.model.Role;
import com.github.boyarsky1997.systemoptional.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;

public class EditCourseServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(EditCourseServlet.class);
    CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("id");
        Course course = courseDAO.getById(Integer.parseInt(courseId));
        HttpSession session = req.getSession(false);
        User client = (User) session.getAttribute("client");
        if (course.getTeacherId() == client.getId()) {
            req.setAttribute("course", course);
            if (client.getRole().equals(Role.TEACHER)) {
                req.getRequestDispatcher("/jsp/edit.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        courseDAO.update(Integer.parseInt(id), title, description);
        resp.sendRedirect("/course?id=" + id);
    }
}
