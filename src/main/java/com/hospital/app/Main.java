package com.hospital.app;

import com.hospital.db.DatabaseHelper;

import java.sql.*;
import java.util.*;

/**
 * The main entry point for the Hospital Management System console application.
 * This class handles the main application loop, user login, and role-based
 * dispatching.
 */
public class Main {
    private static Map<String, Object> currentUser;

    private static boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Populate the currentUser map with user details
                currentUser = new HashMap<>();
                currentUser.put("userId", rs.getInt("user_id"));
                currentUser.put("username", rs.getString("username"));
                currentUser.put("name", rs.getString("name"));
                currentUser.put("role", rs.getString("role"));
                System.out.println("\nLogin successful. Welcome, " + currentUser.get("name") + "!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return false;
    }

    private static void logout() {
        currentUser = null;
        System.out.println("\nYou have been logged out.");
        System.out.println("==========================================");
    }

    public static Map<String, Object> getCurrentUser() {
        return currentUser;
    }
}