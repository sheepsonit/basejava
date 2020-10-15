package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    int getIndex(String uuid) {
        return storage.containsKey(uuid) ? 0 : -1;
    }

    @Override
    void updateResume(int index, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    void saveResume(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    void deleteResume(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
