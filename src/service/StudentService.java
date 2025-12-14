package service;

import model.Student;
import db.StudentDAO;
import java.util.List;

/**
 * Service layer implementation for Student operations.
 * Contains business logic and validation.
 */
public class StudentService implements PersonService<Student> {
    
    private final StudentDAO studentDAO; // Data access object
    
    public StudentService() {
        this.studentDAO = new StudentDAO(); // Initialize DAO
    }
    
    @Override
    public void addPerson(Student student) throws Exception {
        // Validate all student fields before adding to database
        if (!Validator.isValidName(student.getName())) {
            throw new IllegalArgumentException("Name must be at least 2 characters");
        }
        if (!Validator.isValidAge(student.getAge())) {
            throw new IllegalArgumentException("Age must be between 16 and 100");
        }
        if (!Validator.isValidEmail(student.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!Validator.isValidCourse(student.getCourse())) {
            throw new IllegalArgumentException("Course is required");
        }
        if (!Validator.isValidGrade(student.getGrade())) {
            throw new IllegalArgumentException("Invalid grade format. Use A+, A, A-, B+, etc.");
        }
        
        studentDAO.create(student); // Save to database if validation passes
    }
    
    @Override
    public Student getPerson(int id) throws Exception {
        return studentDAO.read(id); // Get student by ID
    }
    
    @Override
    public void updatePerson(Student student) throws Exception {
        studentDAO.update(student); // Update existing student
    }
    
    @Override
    public void deletePerson(int id) throws Exception {
        studentDAO.delete(id); // Delete student by ID
    }
    
    @Override
    public List<Student> getAllPersons() throws Exception {
        return studentDAO.findAll(); // Get all students
    }
    
    // ---------- Additional Business Methods ----------
    
    /**
     * Search students by name keyword.
     * @param keyword Search term (partial name)
     * @return List of matching students, or all if keyword is empty
     */
    public List<Student> searchStudents(String keyword) throws Exception {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPersons(); // Return all if no keyword
        }
        return studentDAO.searchByName(keyword); // Search by name
    }
    
    /**
     * Get all unique courses from the database.
     * @return List of distinct course names
     */
    public List<String> getAllCourses() throws Exception {
        return studentDAO.getAllCourses();
    }
}