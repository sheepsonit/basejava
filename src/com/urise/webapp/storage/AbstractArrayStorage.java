package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
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

    public void update(Resume resume) {
        int indexFoundResume = getIndex(resume.getUuid());
        if (indexFoundResume > -1) {
            storage[indexFoundResume] = resume;
        } else {
           throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage is overflow",resume.getUuid());
        } else {
            int index = getIndex(resume.getUuid());
            if (index > -1) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                saveResume(index, resume);
                size++;
            }
        }
    }

    public void delete(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            deleteResume(indexFoundResume);
            storage[size] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            return storage[indexFoundResume];
        }
        throw new NotExistStorageException(uuid);
    }

    abstract int getIndex(String uuid);

    abstract void saveResume(int index, Resume resume);

    abstract void deleteResume(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
