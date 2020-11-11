package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {

    private final String textContent;

    public TextSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.textContent = content;
    }

    public String getContent() {
        return textContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return textContent.equals(that.textContent);
    }

    @Override
    public int hashCode() {
        return textContent.hashCode();
    }
}
