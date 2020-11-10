package database;

import exceptions.DatabaseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {

    private static Connection connection = null;

    private static Properties loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            Properties properties = loadProperties();
            String url = properties.getProperty("dburl");
            try {
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                throw new DatabaseException(exception.getMessage());
            }
        }
    }
}
