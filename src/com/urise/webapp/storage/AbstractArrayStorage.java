package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;
    Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    void updateResume(int searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    void saveResume(int searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow", resume.getUuid());
        } else {
            insertResume(searchKey, resume);
            size++;
        }
    }

    @Override
    void deleteResume(int searchKey, String uuid) {
        deleteResumeByIndex(searchKey);
        storage[--size] = null;
//        size--;
    }

    @Override
    Resume getResume(int searchKey, String uuid) {
        return storage[searchKey];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    abstract void insertResume(int index, Resume resume);

    abstract void deleteResumeByIndex(int index);
}
