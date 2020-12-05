package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    void updateResume(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    void saveResume(Integer searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow", resume.getUuid());
        } else {
            insertResume(searchKey, resume);
            size++;
        }
    }

    @Override
    void deleteResume(Integer searchKey) {
        deleteResumeByIndex(searchKey);
        storage[--size] = null;
    }

    @Override
    Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    boolean isExist(Integer searchKey) {
        return searchKey > -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    List<Resume> getResumes() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    abstract void insertResume(int index, Resume resume);

    abstract void deleteResumeByIndex(int index);
}
