package com.github.boyarsky1997.systemoptional.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionSingleton {
    private static final Logger logger = Logger.getLogger(UserDAO.class);
    private static Connection connection;

    private ConnectionSingleton() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties properties = new Properties();
                properties.load(UserDAO.class.getResourceAsStream("/database.properties"));
                logger.info(String.format("Підключаємось до бази даних %s", properties.getProperty("db.name")));

                connection = DriverManager.getConnection(
                        String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&serverTimezone=UTC",
                                properties.getProperty("db.host"),
                                properties.getProperty("db.port"),
                                properties.getProperty("db.name")),
                        properties.getProperty("db.user"),
                        properties.getProperty("db.password"));
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        return connection;
    }
}
