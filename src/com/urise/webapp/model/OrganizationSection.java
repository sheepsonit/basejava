package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private static final long serialVersionUID = 1L;

    private List<Experience> list = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Experience... experiences) {
        this(Arrays.asList(experiences));
    }

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
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
