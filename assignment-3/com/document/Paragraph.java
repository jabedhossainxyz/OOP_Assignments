package com.document;

public class Paragraph extends DocumentElement {
    public Paragraph(String content) {
        super(content);
    }

    @Override
    public String getType() {
        return "PARAGRAPH";
    }
}
