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
    void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    void saveResume(int index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow", resume.getUuid());
        } else {
            insertResume(index, resume);
            size++;
        }
    }

    @Override
    void deleteResume(int index, String uuid) {
        deleteResumeByIndex(index);
        storage[size] = null;
        size--;
    }

    @Override
    Resume getResume(int index, String uuid) {
        return storage[index];
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
