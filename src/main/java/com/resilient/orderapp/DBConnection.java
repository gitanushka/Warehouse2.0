package com.resilient.orderapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/warehouse?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "warehouse@123"; // change this

    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load MySQL driver (sometimes optional, but safer)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);    }
}


