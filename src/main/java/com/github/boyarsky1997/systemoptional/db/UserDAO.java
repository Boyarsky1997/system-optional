package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.*;
import com.github.boyarsky1997.systemoptional.util.Resources;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class);

    public User get(String login, String password) {
        User client;
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/getClientByLoginAndPassword.sql"));
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(String.format("Відбувається пошук клієнта по %s ", "логіну і паролю "));

            if (resultSet.next()) {

                String string = resultSet.getString(2);
                if (string.equals(Role.STUDENT.toString())) {
                    client = new Student();
                    client.setRole(Role.STUDENT);
                } else {
                    client = new Teacher();
                    client.setRole(Role.TEACHER);
                }

                client.setId(resultSet.getInt(1));

                client.setName(resultSet.getString(3));
                client.setSurname(resultSet.getString(4));
                client.setLogin(resultSet.getString(5));
                client.setPassword(resultSet.getString(6));

                return client;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return null;
    }


    public boolean checkExistLogin(String login) {
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/checkClient.sql"));
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(String.format("Відбувається пошук клієнта по логіну %s ", login));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    public List<User> allTeacher(List<Integer> listId) {
        List<User> result = new ArrayList<>();
        for (Integer integer : listId) {
            try {
                PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                        .prepareStatement(Resources.load("/sql/getAllTeacher.sql"));
                preparedStatement.setInt(1, integer);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    User client = new Teacher();
                    client.setId(resultSet.getInt(1));
                    client.setRole(Role.TEACHER);
                    client.setName(resultSet.getString(3));
                    client.setSurname(resultSet.getString(4));
                    client.setLogin(resultSet.getString(5));
                    client.setPassword(resultSet.getString(6));
                    result.add(client);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void insertUser(User user) {
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/insertUser.sql"));
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());

            preparedStatement.execute();
            logger.info("Клієнт був успішно доданий");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getTeacher(int id) {
        User client;
        try {
            PreparedStatement preparedStatement = ConnectionSingleton.getConnection()
                    .prepareStatement(Resources.load("/sql/getTeacher.sql"));
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                client = new Teacher();
                client.setId(resultSet.getInt(1));
                client.setRole(Role.TEACHER);
                client.setName(resultSet.getString(3));
                client.setSurname(resultSet.getString(4));
                client.setLogin(resultSet.getString(5));
                client.setPassword(resultSet.getString(6));
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}