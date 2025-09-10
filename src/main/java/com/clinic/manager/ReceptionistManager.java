package com.clinic.manager;

import com.clinic.db.DatabaseHelper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReceptionistManager {
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Receptionist Dashboard ---");
            System.out.println("1. View All Patients");
            System.out.println("2. Add New Patient");
            System.out.println("3. View All Appointments");
            System.out.println("4. Schedule New Appointment");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        viewAllPatients();
                        break;
                    case 2:
                        addNewPatient();
                        break;
                    case 3:
                        viewAllAppointments();
                        break;
                    case 4:
                        scheduleNewAppointment();
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

    private void viewAllPatients() {
        System.out.println("\n--- List of All Patients ---");
        String sql = "SELECT * FROM patients";
        try (Connection conn = DatabaseHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-5s | %-20s | %-10s | %-12s | %-15s\n", "ID", "Name", "Gender", "DOB", "Contact");
            System.out.println("--------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-10s | %-12s | %-15s\n",
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("date_of_birth"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching patients: " + e.getMessage());
        }
    }

    private void addNewPatient() {
        System.out.println("\n--- Add New Patient ---");
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Gender (Male/Female/Other): ");
            String gender = scanner.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            System.out.print("Enter Contact Number: ");
            String contact = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            String sql = "INSERT INTO patients(name, gender, date_of_birth, contact, address) VALUES(?,?,?,?,?)";
            try (Connection conn = DatabaseHelper.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, gender);
                pstmt.setString(3, dob);
                pstmt.setString(4, contact);
                pstmt.setString(5, address);
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("Patient added successfully!");
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding patient: " + e.getMessage());
        }
    }

    private void viewAllAppointments() {
        System.out.println("\n--- List of All Appointments ---");
        String sql = "SELECT a.appointment_id, p.name as patient_name, d.name as doctor_name, a.appointment_date, a.appointment_time, a.status "
                +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.patient_id " +
                "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                "ORDER BY a.appointment_date, a.appointment_time";

        try (Connection conn = DatabaseHelper.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-5s | %-20s | %-20s | %-12s | %-10s | %-10s\n", "ID", "Patient", "Doctor", "Date",
                    "Time", "Status");
            System.out.println(
                    "-------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-20s | %-12s | %-10s | %-10s\n",
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getString("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching appointments: " + e.getMessage());
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        return timeFormat.format(new Date());
    }

    private void scheduleNewAppointment() {
        System.out.println("\n--- Schedule New Appointment ---");
        viewAllPatients();
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();

        // Reusing DoctorManager's method to show doctors
        new AdminManager().viewAllDoctors();
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO appointments(patient_id, doctor_id, appointment_date, appointment_time) VALUES(?,?,?,?)";
        String getFeeSql = "SELECT consultation_fee FROM doctors WHERE doctor_id = ?";
        String billingSql = "INSERT INTO billing(appointment_id, amount, payment_status, payment_method) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement feeStmt = conn.prepareStatement(getFeeSql)) {
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, doctorId);
            pstmt.setString(3, getCurrentDate());
            pstmt.setString(4, getCurrentTime());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                int appointmentId = -1;
                if (generatedKeys.next()) {
                    appointmentId = generatedKeys.getInt(1);
                }
                System.out.println("Appointment scheduled successfully!");
                // Get doctor's consultation fee
                feeStmt.setInt(1, doctorId);
                ResultSet feeRs = feeStmt.executeQuery();
                double fee = 0.0;
                if (feeRs.next()) {
                    fee = feeRs.getDouble("consultation_fee");
                }
                // Create billing record
                try (PreparedStatement billStmt = conn.prepareStatement(billingSql)) {
                    billStmt.setInt(1, appointmentId);
                    billStmt.setDouble(2, fee);
                    billStmt.setString(3, "unpaid");
                    billStmt.setString(4, "cash"); // Default payment method
                    if (billStmt.executeUpdate() > 0) {
                        System.out.println("Billing record created with amount: " + fee);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error scheduling appointment or creating billing: " + e.getMessage());
        }
    }
}
