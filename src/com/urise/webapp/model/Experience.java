package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Experience {

    private Link organization;

    private List<DateIntervalExperience> dates = new ArrayList<>();

    public Experience(String organizationName, String url, DateIntervalExperience... intervalsOfExperience) {
        this(new Link(organizationName, url), Arrays.asList(intervalsOfExperience));
    }

    public Experience(Link organization, List<DateIntervalExperience> intervalsOfExperience) {
        this.organization = organization;
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
        return organization.equals(that.organization) &&
                dates.equals(that.dates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, dates);
    }

    @Override
    public String toString() {
        return "Organization(" + organization +
                ", " + dates +
                ')';
    }

    public static class DateIntervalExperience {

        private YearMonth dateStart;

        private YearMonth dateEnd;

        private String mainInfo;

        private String note;

        public DateIntervalExperience(YearMonth dateStart, YearMonth dateEnd, String mainInfo, String note) {
            Objects.requireNonNull(dateStart, "dateStart must not be null");
            Objects.requireNonNull(dateEnd, "dateEnd must not be null");
            Objects.requireNonNull(mainInfo, "mainInfo must not be null");
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.mainInfo = mainInfo;
            this.note = note;
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

            DateIntervalExperience that = (DateIntervalExperience) o;

            if (!dateStart.equals(that.dateStart)) return false;
            if (!dateEnd.equals(that.dateEnd)) return false;
            if (!mainInfo.equals(that.mainInfo)) return false;
            return Objects.equals(note, that.note);
        }

        @Override
        public int hashCode() {
            int result = dateStart.hashCode();
            result = 31 * result + dateEnd.hashCode();
            result = 31 * result + mainInfo.hashCode();
            result = 31 * result + (note != null ? note.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "DateIntervalExperience{" +
                    dateStart +
                    ", " + dateEnd +
                    ", '" + mainInfo + '\'' +
                    ", '" + note + '\'' +
                    '}';
        }
    }
}
