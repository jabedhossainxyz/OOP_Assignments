package com.document;

public interface DocumentBuilder {
    DocumentBuilder setTitle(String title);
    DocumentBuilder setAuthor(String author);
    DocumentBuilder setStudentId(String studentId);
    DocumentBuilder addHeading1(String content);
    DocumentBuilder addHeading2(String content);
    DocumentBuilder addParagraph(String content);
    Document build();
}
