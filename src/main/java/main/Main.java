package main;

import dao.StudentDAO;
import firebase.DBConnection;
import model.Student;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main console application for Student Record Management System.
 */
public class Main {
    private static final StudentDAO dao = new StudentDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize Firebase URL from config.properties
        String url = DBConnection.getFirebaseUrl();
        if (url.isEmpty()) {
            System.out.println("Please enter your Firebase Realtime Database URL:");
            System.out.println("(e.g., https://your-project.firebaseio.com/)");
            url = scanner.nextLine().trim();
            DBConnection.setFirebaseUrl(url);
        }

        while (true) {
            try {
                showMenu();
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        searchStudent();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        System.out.println("Thank you for using Student Record Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Student Record Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.print("\nEnter your choice (1-6): ");
    }

    private static void addStudent() throws IOException {
        System.out.println("\n=== Add New Student ===");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());
        
        System.out.print("Enter course: ");
        String course = scanner.nextLine().trim();
        
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine().trim();

        Student student = new Student(null, name, age, course, grade);
        String id = dao.addStudent(student);
        System.out.println("Student added successfully with ID: " + id);
    }

    private static void viewAllStudents() throws IOException {
        System.out.println("\n=== All Students ===");
        List<Student> students = dao.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void searchStudent() throws IOException {
        System.out.println("\n=== Search Student ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.print("Enter choice (1-2): ");
        
        int choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice == 1) {
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine().trim();
            Student student = dao.getStudentById(id);
            if (student != null) {
                System.out.println(student);
            } else {
                System.out.println("Student not found.");
            }
        } else if (choice == 2) {
            System.out.print("Enter name to search: ");
            String name = scanner.nextLine().trim();
            List<Student> students = dao.searchByName(name);
            if (students.isEmpty()) {
                System.out.println("No students found.");
            } else {
                for (Student s : students) {
                    System.out.println(s);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void updateStudent() throws IOException {
        System.out.println("\n=== Update Student ===");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        
        Student existing = dao.getStudentById(id);
        if (existing == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Current details: " + existing);
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Name [" + existing.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) existing.setName(name);
        
        System.out.print("Age [" + existing.getAge() + "]: ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) existing.setAge(Integer.parseInt(ageStr));
        
        System.out.print("Course [" + existing.getCourse() + "]: ");
        String course = scanner.nextLine().trim();
        if (!course.isEmpty()) existing.setCourse(course);
        
        System.out.print("Grade [" + existing.getGrade() + "]: ");
        String grade = scanner.nextLine().trim();
        if (!grade.isEmpty()) existing.setGrade(grade);

        if (dao.updateStudent(id, existing)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Failed to update student.");
        }
    }

    private static void deleteStudent() throws IOException {
        System.out.println("\n=== Delete Student ===");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        
        Student student = dao.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Student to delete: " + student);
        System.out.print("Are you sure you want to delete this student? (y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y")) {
            if (dao.deleteStudent(id)) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }
}