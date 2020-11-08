package com.urise.webapp.model;

import java.time.YearMonth;

public class TableSection {

    private String organization;

    private YearMonth dateStart;

    private YearMonth dateEnd;

    private String mainInfo;

    private String note;

    public TableSection(String organization, YearMonth dateStart, YearMonth dateEnd, String mainInfo) {
        this.organization = organization;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.mainInfo = mainInfo;
    }

    public String getOrganization() {
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

    public void setNote(String note) {
        this.note = note;
    }
}
