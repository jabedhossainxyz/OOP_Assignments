package com.document;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private String title;
    private List<DocumentElement> elements;
    private String author;
    private String studentId;

    public Document() {
        this.elements = new ArrayList<>();
    }

    public Document(String title, String author, String studentId) {
        this.title = title;
        this.author = author;
        this.studentId = studentId;
        this.elements = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void addElement(DocumentElement element) {
        this.elements.add(element);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getStudentId() {
        return studentId;
    }

    public List<DocumentElement> getElements() {
        return new ArrayList<>(elements);
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", studentId='" + studentId + '\'' +
                ", elements=" + elements.size() +
                '}';
    }
}
