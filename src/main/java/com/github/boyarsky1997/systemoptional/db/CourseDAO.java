package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.Course;
import com.github.boyarsky1997.systemoptional.util.Resources;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private static final Logger logger = Logger.getLogger(CourseDAO.class);

    private final Connection connection;

    public CourseDAO() {
        this(ConnectionSingleton.getConnection());
    }

    CourseDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Course> getAll() {
        List<Course> result = new ArrayList<>();
        ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/getAllCourse.sql"));
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
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public void addUserIdAndCourseId(int userId, int courseId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/insertUserAndCourseId.sql"));
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public int insert(String name, String description, int teacherId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/insertCourse.sql"));
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, teacherId);
            preparedStatement.setString(3, description);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(Resources.load("/sql/getLastId.sql"));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    public void update(int id, String title, String description) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/updateCourse.sql"));
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public List<Course> getAllCourseOnTeacherId(int teacherId) {
        List<Course> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/getAllCourseOnTeacherId.sql"));
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt(1));
                course.setTitle(resultSet.getString(2));
                course.setDescription(resultSet.getString(3));
                result.add(course);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public List<Course> getStudentCourse(int studentId) {
        List<Course> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/getAllCourseOnUserId.sql"));
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt(1));
                course.setTitle(resultSet.getString(2));
                course.setDescription(resultSet.getString(3));
                result.add(course);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public boolean check(int idUser, int idCourse) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/checkUserIdAndCourseId.sql"));
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idCourse);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(String.format("The client is being searched by userId %s and courseID %s : ", idUser, idCourse));
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
            PreparedStatement preparedStatement = connection
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
            logger.error(e.getMessage(),e);
        }

        return course;

    }
}
