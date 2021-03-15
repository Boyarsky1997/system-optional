package com.github.boyarsky1997.systemoptional.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionSingleton {

    private static final Logger logger = Logger.getLogger(ConnectionSingleton.class);
    private static Connection connection;

    private ConnectionSingleton() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                Properties properties = new Properties();
                properties.load(ConnectionSingleton.class.getResourceAsStream("/database.properties"));
                logger.info(String.format("Connect to database %s", properties.getProperty("db.name")));

                connection = DriverManager.getConnection(
                        String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&serverTimezone=UTC",
                                properties.getProperty("db.host"),
                                properties.getProperty("db.port"),
                                properties.getProperty("db.name")),
                        properties.getProperty("db.user"),
                        properties.getProperty("db.password"));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        return connection;
    }
}
