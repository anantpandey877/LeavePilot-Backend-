package com.leavepilot.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://leavepilot-db.cqh606mqcx26.us-east-1.rds.amazonaws.com:3306/leavepilot";

    private static final String USERNAME = "admin";

    private static final String PASSWORD = "LeavePilot0987";

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