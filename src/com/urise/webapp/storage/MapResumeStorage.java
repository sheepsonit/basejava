package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume getSearchKey(String uuid) {
        return storage.getOrDefault(uuid, new Resume(uuid, null));
    }

    @Override
    void updateResume(Object searchKey, Resume resume) {
        Resume keyResume = (Resume) searchKey;
        storage.replace(keyResume.getUuid(), resume);
    }

    @Override
    void saveResume(Object searchKey, Resume resume) {
        Resume keyResume = (Resume) searchKey;
        storage.put(keyResume.getUuid(), resume);
    }

    @Override
    void deleteResume(Object searchKey, String uuid) {
        Resume keyResume = (Resume) searchKey;
        storage.remove(keyResume.getUuid());
    }

    @Override
    Resume getResume(Object searchKey, String uuid) {
        Resume keyResume = (Resume) searchKey;
        return storage.get(keyResume.getUuid());
    }

    @Override
    List<Resume> getResumes() {
        return new ArrayList<>(storage.values());
    }

    @Override
    boolean isExist(Object searchKey) {
        Resume resumeKey = (Resume) searchKey;
        return resumeKey.getFullName() != null;
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
