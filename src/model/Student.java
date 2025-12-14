package model;

public class Student extends Person {
    private String course;
    private String grade;
    
    public Student() {}
    
    // UPDATED constructor to match YOUR database columns
    public Student(int id, String name, int age, String email, String course, String grade) {
        super(id, name, age, email);
        this.course = course;
        this.grade = grade;
    }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    @Override
    public String getDisplayText() {
        return String.format("Student | %s | Course: %s | Grade: %s", 
            super.getDisplayText(), course, grade);
    }
    
    @Override
    public String toString() {
        return name + " - " + course + " (" + grade + ")";
    }
}