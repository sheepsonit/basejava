package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int indexInsert = -index - 1;
        System.arraycopy(storage, indexInsert, storage, indexInsert + 1, size - indexInsert);
        storage[indexInsert] = resume;
    }

    @Override
    protected void deleteResumeByIndex(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
