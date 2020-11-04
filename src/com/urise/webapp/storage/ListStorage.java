package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }


    @Override
    void updateResume(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    void saveResume(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    void deleteResume(Integer searchKey, String uuid) {
        storage.remove((searchKey).intValue());
    }

    @Override
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }
        return null;
    }

    @Override
    Resume getResume(Integer searchKey, String uuid) {
        return storage.get(searchKey);
    }

    @Override
    List<Resume> getResumes() {
        return new ArrayList<>(storage);
    }

    @Override
    boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}
