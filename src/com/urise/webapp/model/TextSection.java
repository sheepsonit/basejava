package com.urise.webapp.model;

public class TextSection implements Section<String> {

    private String textContent;

    public TextSection(String content) {
        this.textContent = content;
    }

    @Override
    public String getContent() {
        return textContent;
    }
}
