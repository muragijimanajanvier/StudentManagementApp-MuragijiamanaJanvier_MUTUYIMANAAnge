package db;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Student entities.
 * Implements CRUD operations for the students table.
 */
public class StudentDAO implements GenericDAO<Student> {
    
    @Override
    public void create(Student student) throws Exception {
        // SQL insert with column names matching the database
        String sql = "INSERT INTO students (name, age, email, course, grade) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters for the prepared statement
            pstmt.setString(1, student.getName());      // name
            pstmt.setInt(2, student.getAge());          // age
            pstmt.setString(3, student.getEmail());     // email
            pstmt.setString(4, student.getCourse());    // course
            pstmt.setString(5, student.getGrade());     // grade
            
            pstmt.executeUpdate(); // Execute the insert
            
            // Retrieve auto-generated ID and set it on the student object
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    student.setId(rs.getInt(1));
                }
            }
        }
    }
    
    @Override
    public Student read(int id) throws Exception {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null; // Initialize as null (not found)
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id); // Set the ID parameter
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // If a record is found
                    student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getString("grade")
                    );
                }
            }
        }
        return student; // Returns null if not found
    }
    
    @Override
    public void update(Student student) throws Exception {
        // SQL update with all student fields
        String sql = "UPDATE students SET name=?, age=?, email=?, course=?, grade=? WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set all field values
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getCourse());
            pstmt.setString(5, student.getGrade());
            pstmt.setInt(6, student.getId()); // WHERE clause uses ID
            
            pstmt.executeUpdate(); // Execute the update
        }
    }
    
    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id); // Set the ID to delete
            pstmt.executeUpdate(); // Execute delete
        }
    }
    
    @Override
    public List<Student> findAll() throws Exception {
        List<Student> students = new ArrayList<>(); // List to hold results
        String sql = "SELECT * FROM students ORDER BY id"; // Order by ID
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) { // For each row in result set
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    rs.getString("course"),
                    rs.getString("grade")
                );
                students.add(student); // Add to list
            }
        }
        return students; // Return all students
    }
    
    // ---------- Additional Helper Methods ----------
    
    /**
     * Search for students by name (partial match).
     * @param name Search term (can be partial)
     * @return List of matching students
     */
    public List<Student> searchByName(String name) throws Exception {
        List<Student> students = new ArrayList<>();
        // LIKE with % for partial matching
        String sql = "SELECT * FROM students WHERE name LIKE ? ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%"); // Add wildcards
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getString("grade")
                    );
                    students.add(student);
                }
            }
        }
        return students;
    }
    
    /**
     * Get all distinct courses from the students table.
     * @return List of unique course names
     */
    public List<String> getAllCourses() throws Exception {
        List<String> courses = new ArrayList<>();
        String sql = "SELECT DISTINCT course FROM students ORDER BY course";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                courses.add(rs.getString("course")); // Add each course
            }
        }
        return courses; // Return unique courses
    }
}