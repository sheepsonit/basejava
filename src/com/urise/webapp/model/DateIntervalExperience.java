package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class DateIntervalExperience {

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
        return note != null ? note.equals(that.note) : that.note == null;
    }

    @Override
    public int hashCode() {
        int result = dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + mainInfo.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
