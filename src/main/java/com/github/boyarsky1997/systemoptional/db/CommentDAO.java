package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.Comment;
import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.util.Resources;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private static final Logger logger = Logger.getLogger(CommentDAO.class);
    public List<Comment> getAllCommentOnUserId(int studentId) {
        List<Comment> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/getAllCommentOnUserId.sql"));
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setMessage(resultSet.getString(2));
                comment.setTeacherId(resultSet.getInt(3));
                comment.setStudentId(resultSet.getInt(4));
                comment.setDate(resultSet.getDate(5));
                comment.setNameTeacher(resultSet.getString(6));
                comment.setTeacherSurname(resultSet.getString(7));
                result.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
