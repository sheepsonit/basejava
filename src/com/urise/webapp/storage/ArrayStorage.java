package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (get(resume.getUuid()) != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    storage[i] = resume;
                }
            }
        } else {
            System.out.println("Resume does`t exist");
        }
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Array is overflow");
        } else if (get(r.getUuid()) != null) {
            System.out.println("Resume already exist");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        if (get(uuid) != null) {
            int indexOfStorage = -1;
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    indexOfStorage = i;
                    break;
                }
            }
            System.arraycopy(storage, indexOfStorage + 1, storage, indexOfStorage, size - indexOfStorage - 1);
            storage[size] = null;
            if (size > 0) {
                size--;
            }
        } else {
            System.out.println("Resume does`t exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage,0,size);
    }

    public int size() {
        return size;
    }
}
