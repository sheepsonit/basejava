package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

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
    void updateResume(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    void saveResume(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    void deleteResume(Object searchKey, String uuid) {
        storage.remove(((Integer) searchKey).intValue());
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
    Resume getResume(Object searchKey, String uuid) {
        return storage.get((Integer) searchKey);
    }

    @Override
    List<Resume> getResumes() {
        return storage;
    }

    @Override
    boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
