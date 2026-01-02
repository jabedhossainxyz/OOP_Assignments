package com.document;

public class Heading2 extends DocumentElement {
    public Heading2(String content) {
        super(content);
    }

    @Override
    public String getType() {
        return "H2";
    }
}
