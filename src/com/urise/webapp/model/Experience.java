package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Experience {

    private Link organization;

    private List<DateIntervalExperience> dates = new ArrayList<>();

    public Experience(String organizationName, String url, List<DateIntervalExperience> intervalsOfExperience) {
        this.organization = new Link(organizationName, url);
        this.dates.addAll(intervalsOfExperience);
    }

    public Link getOrganization() {
        return organization;
    }

    public List<DateIntervalExperience> getDates() {
        return dates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!organization.equals(that.organization)) return false;
        return dates.equals(that.dates);
    }

    @Override
    public int hashCode() {
        int result = organization.hashCode();
        result = 31 * result + dates.hashCode();
        return result;
    }
}
