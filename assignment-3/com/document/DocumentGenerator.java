package com.document;

import java.io.IOException;

public class DocumentGenerator {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java com.document.DocumentGenerator <markdown-file>");
            System.out.println("Example: java com.document.DocumentGenerator Build_pattern.md");
            return;
        }

        String markdownFile = args[0];
        String author = "Your Name";
        String studentId = "Your Student ID";
        
        try {
            // Parse markdown file
            Document document = MarkdownParser.parseMarkdownFile(markdownFile, author, studentId);
            
            // Generate HTML output
            String htmlFile = markdownFile.replace(".md", ".html");
            HTMLDocumentGenerator.generateHTML(document, htmlFile);
            
            System.out.println("Successfully generated HTML file: " + htmlFile);
            System.out.println("Document title: " + document.getTitle());
            System.out.println("Number of elements: " + document.getElements().size());
            
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}
