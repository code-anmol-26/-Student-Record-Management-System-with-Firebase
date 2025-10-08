package model;

/**
 * Student model class representing a student record.
 */
public class Student {
    private String id; // Firebase generated key or custom id
    private String name;
    private int age;
    private String course;
    private String grade;

    public Student() {}

    public Student(String id, String name, int age, String course, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.grade = grade;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s', age=%d, course='%s', grade='%s'}",
                id, name, age, course, grade);
    }
}