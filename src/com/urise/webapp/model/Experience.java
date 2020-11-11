package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {

    private Link organization;

    private YearMonth dateStart;

    private YearMonth dateEnd;

    private String mainInfo;

    private String note;

    public Experience(String organizationName, String url, YearMonth dateStart, YearMonth dateEnd, String mainInfo, String note) {
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        Objects.requireNonNull(dateEnd, "dateEnd must not be null");
        Objects.requireNonNull(mainInfo, "mainInfo must not be null");
        this.organization = new Link(organizationName, url);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.mainInfo = mainInfo;
        this.note = note;
    }

    public Link getOrganization() {
        return organization;
    }

    public YearMonth getDateStart() {
        return dateStart;
    }

    public YearMonth getDateEnd() {
        return dateEnd;
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public String getNote() {
        return note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!organization.equals(that.organization)) return false;
        if (!dateStart.equals(that.dateStart)) return false;
        if (!dateEnd.equals(that.dateEnd)) return false;
        if (!mainInfo.equals(that.mainInfo)) return false;
        return Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        int result = organization.hashCode();
        result = 31 * result + dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + mainInfo.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "organization=" + organization +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", mainInfo='" + mainInfo + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
