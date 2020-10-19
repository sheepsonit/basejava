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
    void updateResume(int searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    void saveResume(int searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    void deleteResume(int searchKey, String uuid) {
        storage.remove(searchKey);
    }

    @Override
    int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    Resume getResume(int searchKey, String uuid) {
        return storage.get(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
