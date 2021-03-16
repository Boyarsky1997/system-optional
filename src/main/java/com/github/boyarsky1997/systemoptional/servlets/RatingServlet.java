package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

public class RatingServlet extends HttpServlet {

    private final static Calendar calendar = Calendar.getInstance();

    private final UserDAO userDAO;
    private final Supplier<Date> dateSupplier;

    public RatingServlet() {
        this(new UserDAO(), () -> new Date(calendar.getTime().getTime()));
    }

    public RatingServlet(UserDAO userDAO, Supplier<Date> dateSupplier) {
        this.userDAO = userDAO;
        this.dateSupplier = dateSupplier;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("id1");
        List<User> studentsList = userDAO.getAllStudentOnCourseId(Integer.parseInt(courseId));
        req.setAttribute("studentsList", studentsList);
        req.getRequestDispatcher("/jsp/ratting.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("studentId");
        String comment = req.getParameter("comment");
        HttpSession session = req.getSession(false);
        User client = (User) session.getAttribute("client");
        userDAO.addComment(comment, client.getId(), Integer.parseInt(studentId), dateSupplier.get());
        resp.sendRedirect("/courses");
    }
}
