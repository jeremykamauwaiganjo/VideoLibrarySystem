package com.example.videolibrarysystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    /**
     * Handles database connection for the Video Library System.
     * @author YourName
     */
    public class DBConnection {

        private static final String URL = "jdbc:mysql://localhost:3306/videolibrarysystem";
        private static final String USER = "root";      // your MySQL username
        private static final String PASSWORD = "";       // your MySQL password

        /**
         * Returns a connection to the database.
         * @return Connection object
         */
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

