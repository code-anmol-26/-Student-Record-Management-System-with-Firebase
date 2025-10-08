# Student Record Management System

A Java console application that manages student records using Firebase Realtime Database.

## Screenshots

### Main Menu
![Main Menu](images/main_menu.png)

### Adding a Student
![Add Student](images/add_student.png)

### Viewing All Students
![View Students](images/view_students.png)

### Searching Students
![Search Student](images/search_student.png)

### Updating Student Details
![Update Student](images/update_student.png)

### Deleting a Student
![Delete Student](images/delete_student.png)

## Project Structure

```
src/main/java/
├── firebase/
│   └── DBConnection.java     # Firebase database connection
├── model/
│   └── Student.java         # Student data model
├── dao/
│   └── StudentDAO.java      # Data access using Firebase REST API
└── main/
    └── Main.java           # Console interface
```

## Prerequisites

1. Java JDK 8 or above
2. Maven
3. Firebase Project with Realtime Database enabled

## Firebase Setup Steps

1. Create a Firebase Project:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Click "Add Project" and follow the setup wizard
   - Give your project a name and continue

2. Enable Realtime Database:
   - In Firebase Console, click "Realtime Database" in the left menu
   - Click "Create Database"
   - Choose region and start in test mode
   - Copy the database URL (e.g., https://your-project.firebaseio.com/)

3. Configure Database Rules:
   - In Realtime Database section, go to "Rules" tab
   - For testing, you can use these rules (NOT for production):
   ```json
   {
     "rules": {
       ".read": true,
       ".write": true
     }
   }
   ```

## Building and Running

1. Clone this repository
2. Edit `config.properties` with your Firebase database URL
3. Build the project:
   ```bash
   mvn clean package
   ```
4. Run the application:
   ```bash
   java -cp target/student-record-firebase-1.0-SNAPSHOT.jar main.Main
   ```

## Features

1. Add new student records
2. View all students
3. Search students by ID or name
4. Update student details
5. Delete student records

## Data Structure in Firebase

```
students/
   studentId/
      name: string
      age: number
      course: string
      grade: string
```

## Dependencies

- Gson: JSON serialization/deserialization
- Apache HttpClient: HTTP requests for Firebase REST API

## Notes

- This is a demo project using Firebase REST API
- For production, implement proper security rules
- Consider implementing pagination for large datasets
- Error handling can be enhanced further