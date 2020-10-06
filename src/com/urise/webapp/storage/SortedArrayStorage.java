package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResume(Resume resume) {
        int indexInsert = Arrays.binarySearch(storage, 0, size, resume);
        indexInsert = -(indexInsert) - 1;
        System.arraycopy(storage, indexInsert, storage, indexInsert + 1, size - indexInsert);
        storage[indexInsert] = resume;
    }
}
