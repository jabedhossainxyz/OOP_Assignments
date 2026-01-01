package com.student;

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
        } else {
            manager.removeStudent(newStudent.getId());
            System.out.println("Undo: Student removed (was newly added) - " + newStudent.getName());
        }
    }

    @Override
    public String getDescription() {
        return "Update student: " + newStudent.getName();
    }
}
