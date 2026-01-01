package com.student;

public interface Command {
    void execute();
    void undo();
    String getDescription();
}
