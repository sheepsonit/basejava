package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class BulletedListSection implements Section<List<String>> {

    private List<String> list = new ArrayList<>();

    @Override
    public void setContent(List<String> content) {
        list.addAll(content);
    }

    @Override
    public List<String> getContent() {
        return list;
    }
}
