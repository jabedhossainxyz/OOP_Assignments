package com.document;

public class Heading1 extends DocumentElement {
    public Heading1(String content) {
        super(content);
    }

    @Override
    public String getType() {
        return "H1";
    }
}
