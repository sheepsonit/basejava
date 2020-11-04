package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    void updateResume(Object searchKey, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    void saveResume(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    void deleteResume(Object searchKey, String uuid) {
        storage.remove(uuid);
    }

    @Override
    Resume getResume(Object searchKey, String uuid) {
        return storage.get(uuid);
    }

    @Override
    List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
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
