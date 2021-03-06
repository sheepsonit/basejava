package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {

    private static final long serialVersionUID = 1L;

    private List<String> list = new ArrayList<>();

    public BulletedListSection() {
    }

    public BulletedListSection(String... content) {
        this(Arrays.asList(content));
    }

    public BulletedListSection(List<String> content) {
        Objects.requireNonNull(content, "list must not be null");
        list.addAll(content);
    }

    public List<String> getContent() {
        return list;
    }

    public void addContent(String content) {
        list.add(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListSection that = (BulletedListSection) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
