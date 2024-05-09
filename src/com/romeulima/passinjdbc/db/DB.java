package com.romeulima.passinjdbc.db;

import com.romeulima.passinjdbc.db.exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DB {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (Objects.isNull(connection)) {
                Properties properties = loadProperties();
                connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fs);

            return properties;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
