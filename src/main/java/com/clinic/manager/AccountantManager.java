package com.clinic.manager;

import com.clinic.db.DatabaseHelper;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountantManager {
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Accountant Dashboard ---");
            System.out.println("1. View All Bills");
            System.out.println("2. Update Payment Status");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        viewAllBills();
                        break;
                    case 2:
                        updatePaymentStatus();
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

    private void viewAllBills() {
        System.out.println("\n--- All Billing Records ---");
        String sql = "SELECT b.*, p.name as patient_name FROM billing b " +
                "JOIN appointments a ON b.appointment_id = a.appointment_id " +
                "JOIN patients p ON a.patient_id = p.patient_id";

        try (Connection conn = DatabaseHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-8s | %-10s | %-20s | %-10s | %-10s | %s\n", "Bill ID", "Appt ID", "Patient", "Amount",
                    "Status", "Method");
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-8d | %-10d | %-20s | %-10.2f | %-10s | %s\n",
                        rs.getInt("bill_id"),
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getDouble("amount"),
                        rs.getString("payment_status"),
                        rs.getString("payment_method"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bills: " + e.getMessage());
        }
    }

    private void updatePaymentStatus() {
        viewAllBills();
        System.out.print("\nEnter Bill ID to update: ");
        int billId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new payment status (unpaid/paid/partial): ");
        String status = scanner.nextLine();
        System.out.print("Enter payment method (cash/card/mobile): ");
        String method = scanner.nextLine();

        String sql = "UPDATE billing SET payment_status = ?, payment_method = ? WHERE bill_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, method);
            pstmt.setInt(3, billId);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Payment status updated.");
            } else {
                System.out.println("Bill ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating payment: " + e.getMessage());
        }
    }
}
