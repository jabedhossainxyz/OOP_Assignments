# Student Information Management System - Command Pattern Implementation

## Introduction

This Student Information Management System demonstrates the practical application of the Command Design Pattern in building a robust, maintainable, and extensible student management application. The system provides a command-line interface for managing student records with full undo functionality and persistent data storage.

## Purpose of the System

The main goal of this system is to provide a comprehensive student information management solution that:
- Manages student records efficiently
- Implements the Command Pattern for operation management
- Provides undo functionality for all operations
- Persists data across program sessions
- Ensures data integrity and consistency

## System Design

The program is designed around the Command Design Pattern, which encapsulates requests as objects, allowing for parameterization of clients with different requests, queuing of requests, and logging of operations.

### 1. Student Model Class

**Location:** `/assignment-2/com/student/Student.java`

The `Student` class represents the data model with Serializable interface for file persistence.

```java
package com.student;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int age;
    private String major;
    private double gpa;

    public Student(String id, String name, int age, String major, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.major = major;
        this.gpa = gpa;
    }

    // Getters and Setters...
    
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Age: %d, Major: %s, GPA: %.2f", 
                           id, name, age, major, gpa);
    }
}
```

### 2. Command Interface

**Location:** `/assignment-2/com/student/Command.java`

The `Command` interface defines the contract for all operations in the system.

```java
package com.student;

public interface Command {
    void execute();
    void undo();
    String getDescription();
}
```

### 3. Concrete Command Classes

#### AddStudentCommand
**Location:** `/assignment-2/com/student/AddStudentCommand.java`

```java
public class AddStudentCommand implements Command {
    private StudentManager manager;
    private Student student;

    public AddStudentCommand(StudentManager manager, Student student) {
        this.manager = manager;
        this.student = student;
    }

    @Override
    public void execute() {
        manager.addStudent(student);
        System.out.println("Student added successfully: " + student.getName());
    }

    @Override
    public void undo() {
        manager.removeStudent(student.getId());
        System.out.println("Undo: Student removed - " + student.getName());
    }
}
```

#### UpdateStudentCommand
**Location:** `/assignment-2/com/student/UpdateStudentCommand.java`

```java
public class UpdateStudentCommand implements Command {
    private StudentManager manager;
    private Student oldStudent;
    private Student newStudent;

    public UpdateStudentCommand(StudentManager manager, Student newStudent) {
        this.manager = manager;
        this.newStudent = newStudent;
        this.oldStudent = manager.getStudent(newStudent.getId());
    }

    @Override
    public void execute() {
        manager.updateStudent(newStudent);
        System.out.println("Student updated successfully: " + newStudent.getName());
    }

    @Override
    public void undo() {
        if (oldStudent != null) {
            manager.updateStudent(oldStudent);
            System.out.println("Undo: Student restored to previous state - " + oldStudent.getName());
        }
    }
}
```

#### DeleteStudentCommand
**Location:** `/assignment-2/com/student/DeleteStudentCommand.java`

```java
public class DeleteStudentCommand implements Command {
    private StudentManager manager;
    private Student deletedStudent;

    public DeleteStudentCommand(StudentManager manager, String studentId) {
        this.manager = manager;
        this.deletedStudent = manager.getStudent(studentId);
    }

    @Override
    public void execute() {
        if (deletedStudent != null) {
            manager.removeStudent(deletedStudent.getId());
            System.out.println("Student deleted successfully: " + deletedStudent.getName());
        }
    }

    @Override
    public void undo() {
        if (deletedStudent != null) {
            manager.addStudent(deletedStudent);
            System.out.println("Undo: Student restored - " + deletedStudent.getName());
        }
    }
}
```

### 4. StudentManager Class

**Location:** `/assignment-2/com/student/StudentManager.java`

The `StudentManager` class acts as the receiver in the Command Pattern and handles data persistence.

```java
public class StudentManager {
    private Map<String, Student> students;
    private List<Command> commandHistory;
    private static final String FILE_NAME = "students.dat";

    public StudentManager() {
        students = new HashMap<>();
        commandHistory = new ArrayList<>();
        loadStudents(); // Load data on startup
    }

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.add(command);
        saveStudents(); // Save after each operation
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.remove(commandHistory.size() - 1);
            lastCommand.undo();
            saveStudents();
        }
    }

    private void loadStudents() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                students = (Map<String, Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading student data: " + e.getMessage());
                students = new HashMap<>();
            }
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }
}
```

### 5. Main Application Class

**Location:** `/assignment-2/com/student/Main.java`

The `Main` class provides the user interface and acts as the invoker in the Command Pattern.

```java
public class Main {
    private static StudentManager manager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: undoLastOperation(); break;
                case 5: listAllStudents(); break;
                case 6: 
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Student Information Management System ===");
        System.out.println("1. Add a new student");
        System.out.println("2. Modify an existing student");
        System.out.println("3. Delete an existing student");
        System.out.println("4. Undo the last operation");
        System.out.println("5. List all students");
        System.out.println("6. Exit");
        System.out.println("==========================================");
    }
}
```

## Implementation Features

### 1. Command Pattern Implementation
- **Encapsulation**: Each operation is encapsulated as a command object
- **Undo Support**: All commands implement undo functionality
- **Command History**: Maintains a history of executed commands
- **Extensibility**: Easy to add new operations without modifying existing code

### 2. Data Persistence
- **Automatic Loading**: Data is loaded when the program starts
- **Automatic Saving**: Data is saved after each operation
- **Serialization**: Uses Java serialization for object persistence
- **Error Handling**: Robust error handling for file operations

### 3. User Interface
- **Menu-Driven**: Clean, intuitive menu interface
- **Input Validation**: Proper validation for all user inputs
- **User Feedback**: Clear feedback for all operations
- **Error Messages**: Informative error messages

## Project Outcome

### Sample Interaction

**Main Menu:**
```
=== Student Information Management System ===
1. Add a new student
2. Modify an existing student
3. Delete an existing student
4. Undo the last operation
5. List all students
6. Exit
==========================================
Enter your choice: 
```

**Adding a Student:**
```
--- Add New Student ---
Enter student ID: S001
Enter student name: John Doe
Enter student age: 20
Enter student major: Computer Science
Enter student GPA: 3.8
Student added successfully: John Doe
```

**Listing All Students:**
```
--- All Students ---
Total students: 2
ID: S001, Name: John Doe, Age: 20, Major: Computer Science, GPA: 3.80
ID: S002, Name: Jane Smith, Age: 21, Major: Mathematics, GPA: 3.90
```

**Undo Operation:**
```
--- Undo Last Operation ---
Undo: Student restored - John Doe
```

### File Persistence
The system automatically saves all student data to `students.dat` file in the program directory. When the program restarts, it loads all previously saved student records, ensuring data persistence across sessions.

## Conclusion

This implementation successfully demonstrates the Command Pattern in creating a comprehensive student information management system. The system provides:

- **Robust Architecture**: Clean separation of concerns with the Command Pattern
- **Data Persistence**: Automatic saving and loading of student data
- **Undo Functionality**: Complete undo support for all operations
- **Extensible Design**: Easy to add new features and operations
- **User-Friendly Interface**: Intuitive command-line interface with proper validation

The system showcases how design patterns can be effectively applied to create maintainable and scalable applications that meet real-world requirements.
