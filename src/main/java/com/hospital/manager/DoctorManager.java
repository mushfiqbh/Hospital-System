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

    private void viewMyAppointments(int doctorId) {
        System.out.println("\n--- My Appointments ---");
        String sql = "SELECT a.*, p.name as patient_name FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "WHERE a.doctor_id = ? ORDER BY a.appointment_date, a.appointment_time";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("%-5s | %-20s | %-12s | %-10s | %-10s | %s\n", "ID", "Patient", "Date", "Time", "Status", "Notes");
            System.out.println("------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-12s | %-10s | %-10s | %s\n",
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("status"),
                        rs.getString("notes"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching appointments: " + e.getMessage());
        }
    }
}
