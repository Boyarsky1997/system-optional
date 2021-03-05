package com.github.boyarsky1997.systemoptional.authorization;

import com.github.boyarsky1997.systemoptional.db.CommentDAO;
import com.github.boyarsky1997.systemoptional.db.CourseDAO;
import com.github.boyarsky1997.systemoptional.model.Comment;
import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.model.Role;
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
    CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User client = (User) session.getAttribute("client");
        List<Course> courseList;
        if (client.getRole().equals(Role.STUDENT)) {
            courseList = courseDAO.getStudentCourse(client.getId());
            req.setAttribute("courseList", courseList);
            List<Comment> commentList = commentDAO.getAllCommentOnUserId(client.getId());
            req.setAttribute("commentList", commentList);

        } else if (client.getRole().equals(Role.TEACHER)) {
            courseList = courseDAO.getAllCourseOnTeacherId(client.getId());
            req.setAttribute("courseList", courseList);
        }
        req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
    }

}
