package com.document;

public abstract class DocumentElement {
    protected String content;

    public DocumentElement(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public abstract String getType();
}
