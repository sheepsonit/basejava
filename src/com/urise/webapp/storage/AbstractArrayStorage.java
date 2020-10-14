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

    @Override
    boolean checkSize(String uuid) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow", uuid);
        }
        return true;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void setResume(int index, Resume resume, boolean isUpdate) {
        if (isUpdate) {
            storage[index] = resume;
        } else {
            insertResume(index, resume);
            size++;
        }
    }

    public void deleteResume(int index) {
        deleteResumeByIndex(index);
        storage[size] = null;
        size--;
    }

    public Resume getResume(int index) {
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResumeByIndex(int index);
}
