package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    void updateResume(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    void saveResume(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow", resume.getUuid());
        } else {
            insertResume((Integer)searchKey, resume);
            size++;
        }
    }

    @Override
    void deleteResume(Object searchKey, String uuid) {
        deleteResumeByIndex((Integer)searchKey);
        storage[--size] = null;
    }

    @Override
    Resume getResume(Object searchKey, String uuid) {
        return storage[(Integer)searchKey];
    }

    @Override
    boolean isExist(Object searchKey) {
        return (Integer)searchKey > -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(Arrays.copyOf(storage, size));
        resumes.sort(Comparator.comparing(Resume::getFullName));
        return resumes;
    }

    abstract void insertResume(int index, Resume resume);

    abstract void deleteResumeByIndex(int index);
}
