package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection implements Section {

    private List<Experience> list = new ArrayList<>();

    public OrganizationSection(List<Experience> experiences) {
        this.list.addAll(experiences);
    }

    @Override
    public List<Experience> getContent() {
        return list;
    }
}
