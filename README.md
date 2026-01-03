# OOP Assignments Repository

This repository contains Object-Oriented Programming assignments implemented in Java, demonstrating various design patterns and programming concepts.

## Assignments

### Assignment 1: NPC Interaction System
- **Location**: `assignment-1/`
- **Description**: An interactive NPC (Non-Player Character) dialogue system
- **Key Features**: 
  - Interactive conversation system with NPCs
  - Menu-driven interface
  - Command-line interaction

### Assignment 2: Command Pattern Implementation
- **Location**: `assignment-2/`
- **Description**: Implementation of the Command Pattern with undo functionality
- **Key Features**:
  - Command interface with execute, undo, and description methods
  - Support for reversible operations
  - Extensible command system

### Assignment 3: Document Generator with Builder Pattern
- **Location**: `assignment-3/`
- **Description**: A flexible document generator that converts Markdown files to multiple formats
- **Key Features**:
  - Builder Pattern implementation for document construction
  - Markdown parser supporting headings (#, ##) and paragraphs
  - Multiple output formats: HTML, PDF, Text, Markdown
  - Interactive GUI file selection with JFileChooser
  - Auto-opening of generated files
  - Watermark support in generated documents

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java IDE (IntelliJ, Eclipse, etc.)

### Running Assignment 3 (Document Generator)

1. Navigate to the assignment directory:
   ```bash
   cd assignment-3
   ```

2. Compile the Java files:
   ```bash
   javac com/document/*.java
   ```

3. Run the main application:
   ```bash
   java com.document.DocumentGenerator
   ```

4. Follow the interactive prompts:
   - Enter a markdown file path or press ENTER to browse files
   - Select output format (HTML, PDF, TEXT, or Markdown)
   - The generated file will be saved and automatically opened

### Sample Input Files
- `input.txt` - Sample markdown file for testing
- `Build_pattern.md` - Another test file with markdown content

## Project Structure

```
OOP_Assignments/
├── assignment-1/
│   └── com/npc/
│       └── Main.java
├── assignment-2/
│   └── com/student/
│       └── Command.java
├── assignment-3/
│   ├── com/document/
│   │   ├── Document.java
│   │   ├── DocumentElement.java
│   │   ├── Heading1.java
│   │   ├── Heading2.java
│   │   ├── Paragraph.java
│   │   ├── DocumentBuilder.java
│   │   ├── ConcreteDocumentBuilder.java
│   │   ├── MarkdownParser.java
│   │   ├── HTMLDocumentGenerator.java
│   │   ├── PDFDocumentGenerator.java
│   │   ├── TextDocumentGenerator.java
│   │   ├── MarkdownDocumentGenerator.java
│   │   └── DocumentGenerator.java
│   ├── input.txt
│   ├── Build_pattern.md
│   └── Assignment3_Report.md
└── README.md
```

## Design Patterns Demonstrated

### Builder Pattern (Assignment 3)
- **Purpose**: Construct complex objects step by step
- **Implementation**: `DocumentBuilder` interface and `ConcreteDocumentBuilder` class
- **Benefits**: Flexible document construction, clear separation of concerns

### Command Pattern (Assignment 2)
- **Purpose**: Encapsulate requests as objects
- **Implementation**: `Command` interface with execute/undo operations
- **Benefits**: Undo/redo functionality, extensible command system

## Features

### Document Generator (Assignment 3)
- **Supported Formats**:
  - HTML: Styled web pages with embedded CSS
  - PDF: Valid PDF files with proper structure
  - Text: Plain text format
  - Markdown: Copy of original markdown content

- **Interactive Features**:
  - GUI file chooser dialog
  - Auto-opening of generated files
  - Success messages with file paths
  - Error handling for file operations

- **Extensibility**:
  - Easy to add new output formats
  - Pluggable parser system
  - Modular design for maintenance

## Usage Examples

### Command Line Usage
```bash
# Run with specific file
java com.document.DocumentGenerator input.txt

# Interactive mode
java com.document.DocumentGenerator
```

### Sample Markdown Input
```markdown
# Main Title
This is a paragraph of text.

## Subtitle
Another paragraph with more content.
```

### Generated Output
- **HTML**: Styled webpage with headings and paragraphs
- **PDF**: Valid PDF document with text content
- **Text**: Plain text representation
- **Markdown**: Copy of original content

## Documentation
- `Assignment3_Report.md` - Detailed documentation for Assignment 3
- Inline code comments throughout the source files

## Requirements

- Java 8+ compatibility
- Swing library for GUI components (included in JDK)
- File system access for reading/writing documents
- Desktop API support for auto-opening files (platform-dependent)

## Troubleshooting

### Common Issues
1. **File not found**: Check file paths and ensure input files exist
2. **PDF won't open**: Generated PDFs are minimal but valid; use standard PDF viewers
3. **GUI doesn't appear**: Some IDEs/terminals may not support GUI; use command-line mode
4. **Auto-open fails**: Check if Desktop API is supported on your platform

### Solutions
- Use absolute file paths for input files
- Ensure proper Java installation with GUI support
- Check file permissions for output directory
- Use compatible PDF viewers (Adobe Reader, system default)

## Future Enhancements

### Document Generator
- Support for additional markdown syntax (lists, links, images)
- More PDF formatting options (fonts, colors, layouts)
- Batch processing of multiple files
- Configuration files for custom settings

### General
- Unit tests for all components
- Maven/Gradle build configuration
- Continuous integration setup
- Extended documentation and examples

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes as part of OOP coursework.

## Contact

For questions or issues related to these assignments, please refer to the course documentation or contact the instructor.
