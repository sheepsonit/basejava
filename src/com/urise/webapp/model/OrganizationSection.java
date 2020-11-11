package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {

    private List<Experience> list = new ArrayList<>();

    public OrganizationSection(List<Experience> experiences) {
        Objects.requireNonNull(experiences, "experiences must not be null");
        this.list.addAll(experiences);
    }

    public List<Experience> getContent() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

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
