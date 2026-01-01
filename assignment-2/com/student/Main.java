package com.student;

import java.util.Scanner;

public class Main {
    private static StudentManager manager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    undoLastOperation();
                    break;
                case 5:
                    listAllStudents();
                    break;
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

    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String id = getStringInput("Enter student ID: ");
        String name = getStringInput("Enter student name: ");
        int age = getIntInput("Enter student age: ");
        String major = getStringInput("Enter student major: ");
        double gpa = getDoubleInput("Enter student GPA: ");

        Student student = new Student(id, name, age, major, gpa);
        Command command = new AddStudentCommand(manager, student);
        manager.executeCommand(command);
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        String id = getStringInput("Enter student ID to update: ");
        Student existingStudent = manager.getStudent(id);
        
        if (existingStudent == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Current student info: " + existingStudent);
        String name = getStringInput("Enter new name (or press Enter to keep current): ", existingStudent.getName());
        int age = getIntInput("Enter new age (or 0 to keep current): ", existingStudent.getAge());
        String major = getStringInput("Enter new major (or press Enter to keep current): ", existingStudent.getMajor());
        double gpa = getDoubleInput("Enter new GPA (or -1 to keep current): ", existingStudent.getGpa());

        Student updatedStudent = new Student(id, name, age, major, gpa);
        Command command = new UpdateStudentCommand(manager, updatedStudent);
        manager.executeCommand(command);
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        String id = getStringInput("Enter student ID to delete: ");
        Command command = new DeleteStudentCommand(manager, id);
        manager.executeCommand(command);
    }

    private static void undoLastOperation() {
        System.out.println("\n--- Undo Last Operation ---");
        manager.undoLastCommand();
    }

    private static void listAllStudents() {
        System.out.println("\n--- All Students ---");
        var students = manager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Total students: " + students.size());
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static String getStringInput(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static int getIntInput(String prompt, int defaultValue) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value == 0 ? defaultValue : value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt, double defaultValue) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value == -1 ? defaultValue : value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
