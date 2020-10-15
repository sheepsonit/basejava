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

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    void saveResume(int index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow", resume.getUuid());
        } else {
            insertResume(index, resume);
            size++;
        }
    }

    void deleteResume(int index) {
        deleteResumeByIndex(index);
        storage[size] = null;
        size--;
    }

    Resume getResume(int index) {
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    abstract void insertResume(int index, Resume resume);

    abstract void deleteResumeByIndex(int index);
}
