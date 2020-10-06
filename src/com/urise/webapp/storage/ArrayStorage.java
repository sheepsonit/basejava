package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int indexFoundResume = getIndex(resume.getUuid());
        if (indexFoundResume > -1) {
            storage[indexFoundResume] = resume;
        } else {
            System.out.println("Can`t update resume. Resume with uuid: " + resume.getUuid() + " does`t exist");
        }
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is overflow");
        } else if (getIndex(resume.getUuid()) > -1) {
            System.out.println("Can`t save resume. Resume with uuid: " + resume.getUuid() + " already exist");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void delete(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            System.arraycopy(storage, indexFoundResume + 1, storage, indexFoundResume, size - indexFoundResume - 1);
            storage[size] = null;
            size--;
        } else {
            System.out.println("Can`t delete resume. Resume with uuid: " + uuid + " does`t exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
