package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    Integer getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    void insertResume(int searchKey, Resume resume) {
        int indexInsert = -(Integer)searchKey - 1;
        System.arraycopy(storage, indexInsert, storage, indexInsert + 1, size - indexInsert);
        storage[indexInsert] = resume;
    }

    @Override
    void deleteResumeByIndex(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
