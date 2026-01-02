package com.document;

import java.io.FileWriter;
import java.io.IOException;

public class TextDocumentGenerator {
    public static void generateText(Document document, String outputPath) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("Title: " + document.getTitle() + "\n");
            writer.write("Author: " + document.getAuthor() + "\n");
            writer.write("Student ID: " + document.getStudentId() + "\n\n");
            
            for (DocumentElement element : document.getElements()) {
                writer.write(element.getContent() + "\n\n");
            }
        }
    }
}
