package com.document;

import java.io.FileWriter;
import java.io.IOException;

public class MarkdownDocumentGenerator {
    public static void generateMarkdown(Document document, String outputPath) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("# " + document.getTitle() + "\n\n");
            
            for (DocumentElement element : document.getElements()) {
                switch (element.getType()) {
                    case "H1":
                        writer.write("# " + element.getContent() + "\n\n");
                        break;
                    case "H2":
                        writer.write("## " + element.getContent() + "\n\n");
                        break;
                    case "PARAGRAPH":
                        writer.write(element.getContent() + "\n\n");
                        break;
                }
            }
        }
    }
}
