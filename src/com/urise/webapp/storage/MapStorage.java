package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    void updateResume(String searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }

    @Override
    void saveResume(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    void deleteResume(String searchKey, String uuid) {
        storage.remove(uuid);
    }

    @Override
    Resume getResume(String searchKey, String uuid) {
        return storage.get(uuid);
    }

    @Override
    List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
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
