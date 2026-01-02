package com.document;

import java.io.FileWriter;
import java.io.IOException;

public class HTMLDocumentGenerator {
    public static void generateHTML(Document document, String outputPath) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>" + document.getTitle() + " " + document.getAuthor() + " " + document.getStudentId() + "</title>\n");
            writer.write("</head>\n");
            writer.write("<body>");
            
            for (DocumentElement element : document.getElements()) {
                switch (element.getType()) {
                    case "H1":
                        writer.write("<h1>" + element.getContent() + "</h1>\n");
                        break;
                    case "H2":
                        writer.write("<h2>" + element.getContent() + "</h2>\n");
                        break;
                    case "PARAGRAPH":
                        writer.write("<p>" + element.getContent() + "</p>\n");
                        break;
                }
            }
            
            writer.write("</body>\n");
            writer.write("</html>");
        }
    }
}
