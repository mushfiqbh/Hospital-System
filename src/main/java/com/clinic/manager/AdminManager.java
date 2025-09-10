package com.clinic.manager;

import com.clinic.db.DatabaseHelper;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminManager {
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. View All Users");
            System.out.println("2. Add New User");
            System.out.println("3. View All Doctors");
            System.out.println("4. Add New Doctor");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        viewAllUsers();
                        break;
                    case 2:
                        addNewUser();
                        break;
                    case 3:
                        viewAllDoctors();
                        break;
                    case 4:
                        addNewDoctor();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
        }
    }

    private void viewAllUsers() {
        System.out.println("\n--- List of All Users ---");
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-5s | %-20s | %-15s | %-12s | %-15s\n", "ID", "Name", "Username", "Role", "Contact");
            System.out.println("--------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-15s | %-12s | %-15s\n",
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }

    private void addNewUser() {
        System.out.println("\n--- Add New User ---");
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Role (admin/doctor/receptionist/accountant): ");
            String role = scanner.nextLine();
            System.out.print("Enter Contact: ");
            String contact = scanner.nextLine();

            String sql = "INSERT INTO users (name, username, password, role, contact) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseHelper.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, role);
                pstmt.setString(5, contact);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("User added successfully!");
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public void viewAllDoctors() {
        System.out.println("\n--- List of All Doctors ---");
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DatabaseHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-5s | %-20s | %-18s | %-15s | %-10s\n", "ID", "Name", "Specialization", "Contact",
                    "Fee");
            System.out.println("----------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-18s | %-15s | %-10.2f\n",
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("contact"),
                        rs.getDouble("consultation_fee"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching doctors: " + e.getMessage());
        }
    }

    private void addNewDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        System.out.println("Note: A doctor must first be a user with the 'doctor' role.");
        viewAllUsers();
        System.out.print("Enter the User ID for the new doctor: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.print("Enter Doctor's Full Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Specialization: ");
            String specialization = scanner.nextLine();
            System.out.print("Enter Contact: ");
            String contact = scanner.nextLine();
            System.out.print("Enter Consultation Fee: ");
            double fee = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Availability (e.g., Mon-Fri 9am-5pm): ");
            String availability = scanner.nextLine();

            String sql = "INSERT INTO doctors (user_id, name, specialization, contact, consultation_fee, availability) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseHelper.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, name);
                pstmt.setString(3, specialization);
                pstmt.setString(4, contact);
                pstmt.setDouble(5, fee);
                pstmt.setString(6, availability);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Doctor added successfully!");
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding doctor: " + e.getMessage());
        }
    }
}
