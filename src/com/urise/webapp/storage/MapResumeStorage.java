package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    void updateResume(Resume searchKey, Resume resume) {
        storage.replace(searchKey.getUuid(), resume);
    }

    @Override
    void saveResume(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    void deleteResume(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    boolean isExist(Resume searchKey) {
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
