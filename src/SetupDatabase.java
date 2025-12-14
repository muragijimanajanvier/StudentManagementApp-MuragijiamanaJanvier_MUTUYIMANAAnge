import java.sql.*;

/**
 * Database setup utility for the Student Management System.
 * Creates database, table, and inserts sample data.
 */
public class SetupDatabase {
    public static void main(String[] args) {
        // Database connection settings
        String url = "jdbc:mysql://localhost:3306/"; // Connection URL (no database specified yet)
        String username = "root"; // MySQL username
        String password = ""; // MySQL password (empty by default in XAMPP)
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("✅ Connected to MySQL server");
            
            // Create database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS university_db");
            stmt.executeUpdate("USE university_db"); // Switch to the database
            
            // Create students table with proper structure
            String createTable = """
                CREATE TABLE IF NOT EXISTS students (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL,
                    age INT NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    course VARCHAR(100) NOT NULL,
                    grade VARCHAR(2),
                    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            stmt.executeUpdate(createTable);
            
            // Insert sample student records (only if they don't exist)
            String insertData = """
                INSERT IGNORE INTO students (name, age, email, course, grade) VALUES
                ('John Doe', 20, 'john.doe@university.edu', 'Computer Science', 'A'),
                ('Jane Smith', 21, 'jane.smith@university.edu', 'Mathematics', 'B+'),
                ('Robert Johnson', 22, 'robert.johnson@university.edu', 'Physics', 'A-'),
                ('Emily Williams', 19, 'emily.williams@university.edu', 'Chemistry', 'B'),
                ('Michael Brown', 20, 'michael.brown@university.edu', 'Engineering', 'C+')
                """;
            stmt.executeUpdate(insertData);
            
            // Success messages
            System.out.println("✅ Database 'university_db' created/verified");
            System.out.println("✅ Table 'students' created with correct structure");
            System.out.println("✅ 5 sample students inserted");
            System.out.println("\n🎯 Your database is ready!");
            
        } catch (Exception e) {
            // Error handling
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}