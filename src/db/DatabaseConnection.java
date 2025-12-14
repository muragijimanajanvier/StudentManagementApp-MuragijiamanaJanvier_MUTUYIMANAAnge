package db;

import java.sql.*;

/**
 * Database connection utility class.
 * Manages connection to the MySQL database.
 */
public class DatabaseConnection {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/university_db";
    private static final String USER = "root";      // Default XAMPP username
    private static final String PASS = "";          // Default XAMPP password (empty)
    
    // Static block runs once when class is loaded
    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found!");
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a connection to the university_db database.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Create and return database connection
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected to university_db database");
            return conn;
        } catch (SQLException e) {
            // Print detailed error information
            System.err.println("❌ Database connection failed!");
            System.err.println("URL: " + URL);
            System.err.println("User: " + USER);
            System.err.println("Error: " + e.getMessage());
            throw e; // Re-throw exception for calling code
        }
    }
}