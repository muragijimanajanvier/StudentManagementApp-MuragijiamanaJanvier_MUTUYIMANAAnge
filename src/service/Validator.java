package service;

import java.util.regex.Pattern;

/**
 * Validation utility class for student data.
 * Contains static methods to validate various student fields.
 */
public class Validator {
    // Email regex pattern for validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Array of valid grade values
    private static final String[] VALID_GRADES = {"A+", "A", "A-", "B+", "B", "B-", 
                                                  "C+", "C", "C-", "D+", "D", "D-", "F"};
    
    /**
     * Validates email format.
     * @param email Email address to validate
     * @return true if email format is valid
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates student age (16-100).
     * @param age Age to validate
     * @return true if age is within valid range
     */
    public static boolean isValidAge(int age) {
        return age >= 16 && age <= 100;
    }
    
    /**
     * Validates grade format (standard letter grades).
     * @param grade Grade to validate (e.g., "A+", "B-")
     * @return true if grade is valid, also true if grade is empty/null (optional field)
     */
    public static boolean isValidGrade(String grade) {
        if (grade == null || grade.trim().isEmpty()) return true; // Grade is optional
        
        // Check against valid grade list (case-insensitive)
        String trimmedGrade = grade.trim().toUpperCase();
        for (String valid : VALID_GRADES) {
            if (valid.equals(trimmedGrade)) return true;
        }
        return false;
    }
    
    /**
     * Validates student name (minimum 2 characters).
     * @param name Name to validate
     * @return true if name is valid
     */
    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }
    
    /**
     * Validates course name (cannot be empty).
     * @param course Course name to validate
     * @return true if course is valid
     */
    public static boolean isValidCourse(String course) {
        return course != null && !course.trim().isEmpty();
    }
}