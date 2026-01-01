package com.student;

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

    @Override
    public String getDescription() {
        return "Add student: " + student.getName();
    }
}
