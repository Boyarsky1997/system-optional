package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateCourseServlet extends HttpServlet {
    CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/createCourse.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        System.out.println(name);
        System.out.println(description);
        HttpSession session = req.getSession(false);
        User client = (User) session.getAttribute("client");
        int id = client.getId();
        int lastId = courseDAO.insert(name, description, id);
        resp.sendRedirect("/course?id="+lastId);


    }
}
