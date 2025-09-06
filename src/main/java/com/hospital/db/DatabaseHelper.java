package com.hospital.db;

import java.sql.*;
import java.util.Scanner;

/**
 * Manages database connection, table creation and data seeding.
 */
public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:hospital_management.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found." + e.getMessage());
        }
        return DriverManager.getConnection(DB_URL);
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
}