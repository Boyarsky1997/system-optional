package com.github.boyarsky1997.systemoptional.servlets;

import com.github.boyarsky1997.systemoptional.db.UserDAO;
import com.github.boyarsky1997.systemoptional.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogInServlet extends HttpServlet {
    private final UserDAO userDAO;

    public LogInServlet() {
        this(new UserDAO());
    }

    public LogInServlet(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User client = userDAO.get(login, password);
        System.out.println("POST " + client);
        if (client == null) {
            req.setAttribute("unfaithful", "Incorrect login or password");
            req.getRequestDispatcher("/jsp/login.jsp").include(req, resp);

        } else {
            HttpSession session = req.getSession(true);
            session.setAttribute("client", client);
            resp.sendRedirect("/profile");
        }
    }
}
