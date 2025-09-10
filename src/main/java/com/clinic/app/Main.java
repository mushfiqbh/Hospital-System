package com.clinic.app;

import com.clinic.db.DatabaseHelper;
import com.clinic.manager.*;

import java.sql.*;
import java.util.*;

/**
 * The main entry point for the Hospital Management System console application.
 * This class handles the main application loop, user login, and role-based
 * dispatching.
 */
public class Main {
    private static Map<String, Object> currentUser;

    public static void main(String[] args) {
        DatabaseHelper.initializeDatabase();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n==========================================");
        System.out.println("   Welcome to Hospital Management System  ");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                // If no user is logged in, show the login prompt
                running = login(scanner);
            } else {
                // If a user is logged in, direct them to their respective manager/dashboard
                dispatchToManager();
                // After the user exits their dashboard, log them out
                logout();
            }
        }
        System.out.println("Application exited.");
    }

    private static boolean login(Scanner scanner) {
        String selectedUsername = showUserSelection(scanner);

        if (selectedUsername == null) {
            return true; // Invalid input was entered, loop again
        }
        if (selectedUsername.equals("EXIT")) {
            System.out.println("Exiting application. Goodbye!");
            return false; // Stop the main loop
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!authenticateUser(selectedUsername, password)) {
            System.out.println("Invalid password. Please try again.");
        }
        return true;
    }

    private static String showUserSelection(Scanner scanner) {
        String fetchUsersSql = "SELECT username FROM users";
        try (Connection conn = DatabaseHelper.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(fetchUsersSql);
                ResultSet rs = pstmt.executeQuery()) {

            List<String> usernames = new ArrayList<>();
            System.out.println("\n------------------------------------------");
            System.out.println("Please select a user to login or exit:");
            System.out.println("0. Exit application");

            int idx = 1;
            while (rs.next()) {
                String uname = rs.getString("username");
                usernames.add(uname);
                System.out.println(idx + ". " + uname);
                idx++;
            }

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                return "EXIT";
            }
            if (choice < 1 || choice > usernames.size()) {
                System.out.println("Invalid choice. Please try again.");
                return null;
            }
            return usernames.get(choice - 1);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        } catch (SQLException e) {
            System.err.println("Database error while fetching users: " + e.getMessage());
            return null;
        }
    }

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

    private static void dispatchToManager() {
        String role = (String) currentUser.get("role");
        switch (role) {
            case "admin":
                new AdminManager().showMenu();
                break;
            case "receptionist":
                new ReceptionistManager().showMenu();
                break;
            case "doctor":
                new DoctorManager().showMenu();
                break;
            case "accountant":
                new AccountantManager().showMenu();
                break;
            default:
                System.out.println("Your role does not have an assigned dashboard.");
                break;
        }
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