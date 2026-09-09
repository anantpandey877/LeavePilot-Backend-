package com.leavepilot.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/leavepilot_db";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "Mathura@12";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD);

        } catch (Exception e) {
            throw new RuntimeException("Database connection failed",e);
        }
    }
}