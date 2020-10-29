package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    Integer getSearchKey(String uuid) {
        return storage.containsKey(uuid) ? storage.get(uuid).hashCode() : null;
    }

    @Override
    void updateResume(Object searchKey, Resume resume) {
        storage.replace((String) searchKey, resume);
    }

    @Override
    void saveResume(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    void deleteResume(Object searchKey, String uuid) {
        storage.remove(searchKey);
    }

    @Override
    Resume getResume(Object searchKey, String uuid) {
        return storage.get(searchKey);
    }

    @Override
    List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
