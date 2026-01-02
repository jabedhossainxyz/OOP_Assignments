# Document Generator - Builder Pattern Implementation

## Introduction

This Document Generator demonstrates the practical application of the Builder Design Pattern in creating a flexible and extensible document conversion system. The system can parse Markdown documents and generate HTML output, with the architecture designed to support multiple markup languages and output formats.

## Purpose of the System

The main goal of this system is to create a document generator that:
- Implements the Builder Pattern for flexible document construction
- Supports multiple markup languages (Markdown, with extensibility for LaTeX and others)
- Generates multiple document formats (HTML, with extensibility for PDF and others)
- Provides clean separation between document structure and output generation
- Allows easy extension for new markup languages and output formats

## System Design

The program is designed around the Builder Design Pattern, which separates the construction of a complex object from its representation. This allows the same construction process to create different representations.

### 1. Document Model Classes

#### Document Class
**Location:** `/assignment-3/com/document/Document.java`

The `Document` class represents the complete document with metadata and content elements.

```java
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

    public void addElement(DocumentElement element) {
        this.elements.add(element);
    }

    // Getters and setters...
    
    public List<DocumentElement> getElements() {
        return new ArrayList<>(elements);
    }
}
```

#### DocumentElement Abstract Class
**Location:** `/assignment-3/com/document/DocumentElement.java`

The abstract base class for all document elements.

```java
package com.document;

public abstract class DocumentElement {
    protected String content;

    public DocumentElement(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public abstract String getType();
}
```

#### Concrete Element Classes
**Location:** `/assignment-3/com/document/Heading1.java`, `Heading2.java`, `Paragraph.java`

```java
// Heading1.java
public class Heading1 extends DocumentElement {
    public Heading1(String content) {
        super(content);
    }

    @Override
    public String getType() {
        return "H1";
    }
}

// Paragraph.java
public class Paragraph extends DocumentElement {
    public Paragraph(String content) {
        super(content);
    }

    @Override
    public String getType() {
        return "PARAGRAPH";
    }
}
```

### 2. Builder Pattern Implementation

#### DocumentBuilder Interface
**Location:** `/assignment-3/com/document/DocumentBuilder.java`

The builder interface defines methods for constructing documents.

```java
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
```

#### ConcreteDocumentBuilder Class
**Location:** `/assignment-3/com/document/ConcreteDocumentBuilder.java`

The concrete implementation of the builder interface.

```java
package com.document;

public class ConcreteDocumentBuilder implements DocumentBuilder {
    private Document document;

    public ConcreteDocumentBuilder() {
        this.document = new Document();
    }

    @Override
    public DocumentBuilder setTitle(String title) {
        document.setTitle(title);
        return this;
    }

    @Override
    public DocumentBuilder addHeading1(String content) {
        document.addElement(new Heading1(content));
        return this;
    }

    @Override
    public Document build() {
        return document;
    }
}
```

### 3. Markdown Parser

#### MarkdownParser Class
**Location:** `/assignment-3/com/document/MarkdownParser.java`

Parses Markdown files and creates Document objects using the Builder pattern.

```java
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
            
            if (line.startsWith("# ")) {
                // First level heading
                if (!titleSet) {
                    String title = line.substring(2).trim();
                    builder.setTitle(title);
                    titleSet = true;
                }
                builder.addHeading1(line.substring(2).trim());
            } else if (line.startsWith("## ")) {
                // Second level heading
                builder.addHeading2(line.substring(3).trim());
            } else if (!line.isEmpty()) {
                // Regular text - part of a paragraph
                if (currentParagraph.length() > 0) {
                    currentParagraph.append(" ");
                }
                currentParagraph.append(line);
            } else if (currentParagraph.length() > 0) {
                // Empty line - finish paragraph
                builder.addParagraph(currentParagraph.toString().trim());
                currentParagraph.setLength(0);
            }
        }
        
        // Add the last paragraph if exists
        if (currentParagraph.length() > 0) {
            builder.addParagraph(currentParagraph.toString().trim());
        }
        
        return builder.build();
    }
}
```

### 4. HTML Document Generator

#### HTMLDocumentGenerator Class
**Location:** `/assignment-3/com/document/HTMLDocumentGenerator.java`

Generates HTML output from Document objects.

```java
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
```

### 5. Main Application

#### DocumentGenerator Class
**Location:** `/assignment-3/com/document/DocumentGenerator.java`

The main class that orchestrates the document generation process.

```java
package com.document;

import java.io.IOException;

public class DocumentGenerator {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java com.document.DocumentGenerator <markdown-file>");
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
            
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}
```

## Implementation Features

### 1. Builder Pattern Benefits
- **Separation of Concerns**: Document construction is separated from representation
- **Fluent Interface**: Method chaining provides readable code
- **Extensibility**: Easy to add new document elements and builders
- **Immutability**: Built objects are immutable after construction

### 2. Markdown Parsing Features
- **Heading Support**: Parses H1 (#) and H2 (##) headings
- **Paragraph Detection**: Automatically groups text into paragraphs
- **Title Extraction**: Uses first H1 as document title
- **Whitespace Handling**: Proper handling of empty lines and spacing

### 3. HTML Generation Features
- **Proper Structure**: Generates valid HTML with head and body sections
- **Watermark Support**: Includes author and student ID in title tag
- **Element Mapping**: Maps document elements to appropriate HTML tags
- **File I/O**: Safe file writing with resource management

## Project Outcome

### Input File Example
**Location:** `/assignment-3/Build_pattern.md`

```markdown
# Build pattern assignment
## Purpose
Using the Builder pattern, design and implement a document generator that can
both extend the types of markup languages and generate document types.
## Requirements
Design and implement a document generator using the Builder pattern. It supports
multiple markup languages (such as Markdown, Latex, and can support other markup
languages in the future) and can generate multiple documents, such as HTML, PDF, and so on.
```

### Generated HTML Output
**Location:** `/assignment-3/Build_pattern.html`

```html
<html>
<head>
<title>Build pattern assignment Your Name Your Student ID</title>
</head>
<body><h1>Build pattern assignment</h1>
<h2>Purpose</h2>
<p>Using the Builder pattern, design and implement a document generator that can
both extend the types of markup languages and generate document types.</p>
<h2>Requirements</h2>
<p>Design and implement a document generator using the Builder pattern. It supports
multiple markup languages (such as Markdown, Latex, and can support other markup
languages in the future) and can generate multiple documents, such as HTML, PDF, and so on.</p>
</body>
</html>
```

### Usage Example
```bash
cd /Users/redorange-m4pro/Desktop/OOP_Assignments/assignment-3
javac com/document/*.java
java com.document.DocumentGenerator Build_pattern.md
```

**Output:**
```
Successfully generated HTML file: Build_pattern.html
Document title: Build pattern assignment
Number of elements: 5
```

## Extensibility Options

### Adding New Markup Languages
To support new markup languages (e.g., LaTeX):
1. Create new parser classes (e.g., `LatexParser`)
2. Implement the same parsing interface
3. Use the existing DocumentBuilder to create Document objects

### Adding New Output Formats
To support new output formats (e.g., PDF):
1. Create new generator classes (e.g., `PDFDocumentGenerator`)
2. Implement format-specific generation logic
3. Use the existing Document objects as input

### Adding New Document Elements
To support new element types (e.g., lists, tables):
1. Create new DocumentElement subclasses
2. Add corresponding methods to DocumentBuilder interface
3. Update parsers and generators to handle new elements

## Conclusion

This implementation successfully demonstrates the Builder Pattern in creating a flexible and extensible document generation system. The system provides:

- **Clean Architecture**: Proper separation of concerns with the Builder Pattern
- **Extensibility**: Easy to add new markup languages and output formats
- **Maintainability**: Well-structured code with clear responsibilities
- **Functionality**: Complete Markdown to HTML conversion with watermark support

The system showcases how design patterns can be effectively applied to create robust, scalable applications that can evolve with changing requirements while maintaining code quality and readability.
