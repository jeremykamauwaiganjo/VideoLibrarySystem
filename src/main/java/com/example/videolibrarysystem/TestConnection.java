package com.example.videolibrarysystem;
import java.sql.Connection;      // ← add this
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();

            if (conn != null) {
                System.out.println("Connected to database successfully!");
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}