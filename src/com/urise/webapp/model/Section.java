package com.urise.webapp.model;

public interface Section<Content> {
    void setContent(Content content);
    Content getContent();
}
