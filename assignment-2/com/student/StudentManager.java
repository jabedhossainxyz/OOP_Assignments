package com.student;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentManager {
    private Map<String, Student> students;
    private List<Command> commandHistory;
    private static final String FILE_NAME = "students.dat";

    public StudentManager() {
        students = new HashMap<>();
        commandHistory = new ArrayList<>();
        loadStudents();
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        saveStudents();
    }

    public void removeStudent(String studentId) {
        students.remove(studentId);
        saveStudents();
    }

    public void updateStudent(Student student) {
        students.put(student.getId(), student);
        saveStudents();
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.add(command);
        saveStudents();
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.remove(commandHistory.size() - 1);
            lastCommand.undo();
            saveStudents();
        }
    }

    @SuppressWarnings("unchecked")
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
