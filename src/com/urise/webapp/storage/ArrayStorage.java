package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    void insertResume(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    void deleteResumeByIndex(int index) {
        storage[index] = storage[size-1];
    }
}
