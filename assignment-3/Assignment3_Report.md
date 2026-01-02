# Document Generator - Builder Pattern Implementation

## Introduction

This Document Generator demonstrates the practical application of the Builder Design Pattern in creating a flexible and extensible document conversion system. The system can parse Markdown documents and generate various output formats, including HTML, PDF, Text, and Markdown copies. It supports command-line arguments, an interactive CLI, and a **GUI file picker** for ease of use.

## Purpose of the System

The main goal of this system is to create a document generator that:
- Implements the Builder Pattern for flexible document construction
- Supports multiple markup languages (Markdown, with extensibility for others)
- Generates multiple document formats (HTML, PDF, Text, Markdown)
- Provides clean separation between document structure and output generation
- Offers a user-friendly interface with both text-based input and a graphical file chooser

## System Design

The program is designed around the Builder Design Pattern, which separates the construction of a complex object from its representation.

### 1. Document Model Classes

#### Document Class
**Location:** `/assignment-3/com/document/Document.java`

The `Document` class represents the complete document with metadata and content elements.

```java
public class Document {
    private String title;
    private List<DocumentElement> elements;
    private String author;
    private String studentId;
    // ...
}
```

### 2. Builder Pattern Implementation

#### DocumentBuilder Interface
**Location:** `/assignment-3/com/document/DocumentBuilder.java`

Defines methods for constructing documents element by element.

```java
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

### 3. Parsers and Generators

#### Markdown Parser
**Location:** `/assignment-3/com/document/MarkdownParser.java`

Parses Markdown files into Document objects.

#### Document Generators
- **HTML:** `/assignment-3/com/document/HTMLDocumentGenerator.java`
- **PDF:** `/assignment-3/com/document/PDFDocumentGenerator.java`
- **Text:** `/assignment-3/com/document/TextDocumentGenerator.java`
- **Markdown:** `/assignment-3/com/document/MarkdownDocumentGenerator.java`

### 4. Main Application

#### DocumentGenerator Class
**Location:** `/assignment-3/com/document/DocumentGenerator.java`

The entry point that handles user interaction. It includes a `JFileChooser` to allow users to select files via a popup window if they prefer not to type the path manually.

```java
public class DocumentGenerator {
    public static void main(String[] args) {
        // ...
        // If input is empty, open file chooser
        if (input.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            // ...
            int result = fileChooser.showOpenDialog(null);
            // ...
        }
        // ...
    }
}
```

## Implementation Features

### 1. Interactive Interface
- **File Upload**: Users can type the path OR press **ENTER** to open a **popup file browser**.
- **Format Selection**: Menu-driven selection for output format.
- **Feedback**: Success messages with absolute file locations.

### 2. Supported Conversions
- **Markdown → HTML**: Generates web-ready HTML with metadata.
- **Markdown → PDF**: Generates a PDF file (mock implementation).
- **Markdown → Text**: Extracts plain text content.
- **Markdown → Markdown**: Creates a clean copy of the document.

### 3. Builder Pattern Benefits
- **Separation of Concerns**: Parsing logic is separate from generation logic.
- **Fluent Interface**: Method chaining for readable code.
- **Extensibility**: Easy to add new parsers or generators.

## Project Outcome

### Sample Interaction

```
=== Document Generator ===

Please enter the path of the markdown file to process
(Press ENTER to browse file, or type 'exit' to quit):
> [User presses ENTER]

Opening file browser...
Selected file: /Users/user/Desktop/assignment-3/input.text

File parsed successfully. Title: Sample Document

Select output format:
1. HTML
2. PDF
3. TEXT
4. Markdown (Copy)
> 1

Success: File saved successfully!
Directory: /Users/user/Desktop/assignment-3/Output.html
```

### Generated Files
- **Input:** `input.text` (Markdown content)
- **Output:** `Output.html`, `Output.pdf`, `Output.txt`, etc.

## Conclusion

This implementation successfully demonstrates a robust and interactive document generator using the Builder Pattern. It meets all requirements for extensibility, multiple format support, and user-friendly interaction (including GUI file selection), showcasing the power of design patterns in solving real-world software design problems.
