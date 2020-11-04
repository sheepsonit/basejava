package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume getSearchKey(String uuid) {
        return storage.get(uuid);
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
        return (Resume) searchKey;
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
