package com.hospital.manager;

import com.hospital.app.Main;
import com.hospital.db.DatabaseHelper;

import java.sql.*;
import java.util.Scanner;

public class DoctorManager {
    private final Scanner scanner = new Scanner(System.in);

    private int getDoctorIdForCurrentUser() {
        String sql = "SELECT doctor_id FROM doctors WHERE user_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, (int) Main.getCurrentUser().get("userId"));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("doctor_id");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching doctor ID: " + e.getMessage());
        }
        return  -1;
}
}
