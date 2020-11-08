package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrderedListSection implements Section<List<TableSection>> {

    private List<TableSection> list = new ArrayList<>();

    @Override
    public void setContent(List<TableSection> tableSections) {
        this.list.addAll(tableSections);
    }

    @Override
    public List<TableSection> getContent() {
        return list;
    }
}
