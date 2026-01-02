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
    public DocumentBuilder setAuthor(String author) {
        document.setAuthor(author);
        return this;
    }

    @Override
    public DocumentBuilder setStudentId(String studentId) {
        document.setStudentId(studentId);
        return this;
    }

    @Override
    public DocumentBuilder addHeading1(String content) {
        document.addElement(new Heading1(content));
        return this;
    }

    @Override
    public DocumentBuilder addHeading2(String content) {
        document.addElement(new Heading2(content));
        return this;
    }

    @Override
    public DocumentBuilder addParagraph(String content) {
        document.addElement(new Paragraph(content));
        return this;
    }

    @Override
    public Document build() {
        return document;
    }
}
