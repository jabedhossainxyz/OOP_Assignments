package com.student;

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
        } else {
            System.out.println("Student not found with the given ID.");
        }
    }

    @Override
    public void undo() {
        if (deletedStudent != null) {
            manager.addStudent(deletedStudent);
            System.out.println("Undo: Student restored - " + deletedStudent.getName());
        }
    }

    @Override
    public String getDescription() {
        return "Delete student: " + (deletedStudent != null ? deletedStudent.getName() : "Unknown");
    }
}
