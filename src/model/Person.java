package model;

public abstract class Person implements Identifiable, Displayable {
    protected int id;
    protected String name;
    protected int age;
    protected String email;
    
    public Person() {}
    
    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    @Override
    public int getId() { return id; }
    
    @Override
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String getDisplayText() {
        return String.format("ID: %d | Name: %s | Age: %d | Email: %s", 
            id, name, age, email);
    }
}