package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.Student;
import com.github.boyarsky1997.systemoptional.model.Teacher;
import com.github.boyarsky1997.systemoptional.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        User user;
        if (role.equals("STUDENT")) {
            user = new Student(login, password, name, surname);
            System.out.println(user);
        } else {
            user = new Teacher(login, password, name, surname);
            System.out.println(user);
        }
        if (!userDAO.checkExistLogin(login)) {
            userDAO.insertUser(user);
            resp.sendRedirect("/login");
        } else {
            req.setAttribute("message", "This login already exists");
            req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
        }
    }
}
