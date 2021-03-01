package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User client = (User) session.getAttribute("client");
        req.setAttribute("name", client.getName());
        req.setAttribute("surname", client.getSurname());
        req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
    }

}
