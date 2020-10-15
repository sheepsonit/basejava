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
    void updateResume(int index, Resume resume) {
       storage.set(index,resume);
    }

    @Override
    void saveResume(int index, Resume resume) {
        storage.add(index,resume);
    }

    @Override
    void deleteResume(int index, String uuid) {
        storage.remove(index);
    }

    @Override
    int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    Resume getResume(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    public Resume[] getAll() {
        return (Resume[])storage.toArray();
    }
}
