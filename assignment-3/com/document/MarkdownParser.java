package com.document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkdownParser {
    public static Document parseMarkdownFile(String filePath, String author, String studentId) throws IOException {
        List<String> lines = readLines(filePath);
        
        DocumentBuilder builder = new ConcreteDocumentBuilder();
        builder.setAuthor(author);
        builder.setStudentId(studentId);
        
        boolean titleSet = false;
        StringBuilder currentParagraph = new StringBuilder();
        
        for (String line : lines) {
            line = line.trim();
            
            if (line.isEmpty()) {
                // Empty line, finish current paragraph if any
                if (currentParagraph.length() > 0) {
                    builder.addParagraph(currentParagraph.toString().trim());
                    currentParagraph.setLength(0);
                }
                continue;
            }
            
            if (line.startsWith("# ")) {
                // First level heading
                if (!titleSet) {
                    String title = line.substring(2).trim();
                    builder.setTitle(title);
                    titleSet = true;
                }
                builder.addHeading1(line.substring(2).trim());
                
                // Finish any pending paragraph
                if (currentParagraph.length() > 0) {
                    builder.addParagraph(currentParagraph.toString().trim());
                    currentParagraph.setLength(0);
                }
            } else if (line.startsWith("## ")) {
                // Second level heading
                builder.addHeading2(line.substring(3).trim());
                
                // Finish any pending paragraph
                if (currentParagraph.length() > 0) {
                    builder.addParagraph(currentParagraph.toString().trim());
                    currentParagraph.setLength(0);
                }
            } else {
                // Regular text - part of a paragraph
                if (currentParagraph.length() > 0) {
                    currentParagraph.append(" ");
                }
                currentParagraph.append(line);
            }
        }
        
        // Add the last paragraph if exists
        if (currentParagraph.length() > 0) {
            builder.addParagraph(currentParagraph.toString().trim());
        }
        
        return builder.build();
    }
    
    private static List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
