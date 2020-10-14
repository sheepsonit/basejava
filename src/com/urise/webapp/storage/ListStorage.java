package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ListStorage extends AbstractStorage {

    static final private int DEFAULT_CAPACITY = 10;
    static final private Resume[] EMPTY_STORAGE = {};
    private Resume[] storage;
    int size = 0;

    ListStorage() {
        this(DEFAULT_CAPACITY);
    }

    ListStorage(int initialCapacity) {
        storage = new Resume[initialCapacity];
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    boolean checkSize(String uuid) {
        if (size == storage.length) {
            grow();
        }
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        trimToSize();
    }

    @Override
    void setResume(int index, Resume resume, boolean isUpdate) {
        if (isUpdate) {
            storage[index] = resume;
        } else {
            storage[size] = resume;
            size++;
        }
    }

    @Override
    void deleteResume(int index) {
        storage[index] = storage[--size];
        trimToSize();
    }

    @Override
    int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    private void trimToSize() {
        if (size < storage.length) {
            storage = (size == 0) ? EMPTY_STORAGE : Arrays.copyOf(storage, size);
        }
    }

    private void grow() {

    }
}
