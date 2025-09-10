package com.clinic.db;

import java.sql.*;
import java.util.Scanner;

/**
 * Manages database connection, table creation and data seeding.
 */
public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:clinic_management.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found." + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        createTables();
        createDefaultAdmin();
        seedDatabase();
    }

    public static void createTables() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            for (String sql : DatabaseSeed.getCreateTableSQLs()) {
                stmt.execute(sql);
            }
            System.out.println("All tables created or already exist.");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    private static void createDefaultAdmin() {
        String checkSql = "SELECT COUNT(*) FROM users WHERE username = 'admin'";
        String insertSql = "INSERT INTO users(username, password, role, name, contact) VALUES('admin', '1234', 'admin', 'Default Admin', 'N/A')";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute(insertSql);
                System.out.println("Default admin user (admin/1234) created successfully.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while creating default admin user: " + e.getMessage());
        }
    }

    private static void seedDatabase() {
        String checkSql = "SELECT COUNT(*) FROM doctors";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("The database is empty. Would you like to add sample data? (yes/no)");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine();

                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("Inserting sample data...");
                    for (String sql : DatabaseSeed.getSeedData()) {
                        stmt.execute(sql);
                    }
                    System.out.println("Sample data seeded successfully.");

                    System.out.println("========= Please Restart the Application =========");
                    System.exit(0);
                }
                scanner.close();
            }

        } catch (SQLException e) {
            System.err.println("Error during database seeding: " + e.getMessage());
        }
    }
}