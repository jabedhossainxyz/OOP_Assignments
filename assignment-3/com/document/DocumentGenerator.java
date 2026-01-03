package com.document;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DocumentGenerator {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Document Generator ===");
        
        if (args.length > 0) {
            handleSingleFile(args[0]);
            return;
        }

        while (true) {
            System.out.println("\nPlease enter the path of the markdown file to process");
            System.out.println("(Press ENTER to browse file, or type 'exit' to quit):");
            System.out.print("> ");
            
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                break;
            }
            
            String filePath = input;
            
            if (input.isEmpty()) {
                System.out.println("Opening file browser...");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Markdown File");
                
                fileChooser.setCurrentDirectory(new File("."));
                
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Markdown & Text Files", "md", "txt", "text");
                fileChooser.setFileFilter(filter);
                
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected file: " + filePath);
                } else {
                    System.out.println("File selection cancelled.");
                    continue;
                }
            }

            handleSingleFile(filePath);
        }
        scanner.close();
    }

    private static void handleSingleFile(String filePath) {
        File inputFile = new File(filePath);
        if (!inputFile.exists()) {
            System.out.println("Error: File not found: " + filePath);
            return;
        }

        String author = "Your Name";
        String studentId = "Your Student ID";

        try {
            Document document = MarkdownParser.parseMarkdownFile(filePath, author, studentId);
            System.out.println("File parsed successfully. Title: " + document.getTitle());

            System.out.println("\nSelect output format:");
            System.out.println("1. HTML");
            System.out.println("2. PDF");
            System.out.println("3. TEXT");
            System.out.println("4. Markdown (Copy)");
            System.out.print("> ");
            
            String choice = "";
            if (scanner.hasNextLine()) {
                choice = scanner.nextLine().trim();
            }
            
            String outputExt = "";
            switch (choice) {
                case "1": outputExt = ".html"; break;
                case "2": outputExt = ".pdf"; break;
                case "3": outputExt = ".txt"; break;
                case "4": outputExt = "_copy.md"; break;
                default:
                    System.out.println("Invalid choice. Defaulting to HTML.");
                    outputExt = ".html";
            }

            String outputFileName = "Output" + outputExt;
            String parentDir = inputFile.getParent();
            String outputPath;
            if (parentDir != null) {
                outputPath = new File(parentDir, outputFileName).getPath();
            } else {
                outputPath = outputFileName;
            }

            // Generate output
            switch (outputExt) {
                case ".html":
                    HTMLDocumentGenerator.generateHTML(document, outputPath);
                    break;
                case ".pdf":
                    PDFDocumentGenerator.generatePDF(document, outputPath);
                    break;
                case ".txt":
                    TextDocumentGenerator.generateText(document, outputPath);
                    break;
                case "_copy.md":
                    MarkdownDocumentGenerator.generateMarkdown(document, outputPath);
                    break;
            }

            System.out.println("\nSuccess: File saved successfully!");
            File outputFile = new File(outputPath).getAbsoluteFile();
            System.out.println("Directory: " + outputFile.getAbsolutePath());
            openFileIfPossible(outputFile);

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    private static void openFileIfPossible(File file) {
        try {
            if (!file.exists()) {
                System.out.println("Note: Output file not found to open: " + file.getAbsolutePath());
                return;
            }
            if (!Desktop.isDesktopSupported()) {
                System.out.println("Note: Auto-open is not supported on this system.");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.OPEN)) {
                System.out.println("Note: Auto-open is not supported on this system.");
                return;
            }
            desktop.open(file);
        } catch (Exception e) {
            System.out.println("Note: Could not auto-open file. Please open manually: " + file.getAbsolutePath());
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
