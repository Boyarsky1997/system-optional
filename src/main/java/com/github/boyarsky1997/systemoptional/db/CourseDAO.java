package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.model.User;
import com.github.boyarsky1997.systemoptional.util.Resources;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private static final Logger logger = Logger.getLogger(CourseDAO.class);

    public List<Course> getAll() {
        List<Course> result = new ArrayList<>();
        ;
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/getAllCourse.sql"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt(1));
                course.setTitle(resultSet.getString(2));
                course.setTeacherId(resultSet.getInt(3));
                course.setDescription(resultSet.getString(4));
                result.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addUserIdAndCourseId(int userId, int courseId) {
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/insertUserAndCourseId.sql"));
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean check(int idUser, int idCourse) {
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/checkUserIdAndCourseId.sql"));
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idCourse);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(String.format("Відбувається пошук клієнта по userId %s та courseID %s : ", idUser, idCourse));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    public Course getById(int id) {
        Course course = new Course();

        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/getCourse.sql"));
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                course.setId(resultSet.getInt(1));
                course.setTitle(resultSet.getString(2));
                course.setTeacherId(resultSet.getInt(3));
                course.setDescription(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;

    }
}
