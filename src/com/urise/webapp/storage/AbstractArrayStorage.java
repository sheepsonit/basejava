package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

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
            System.out.println("Can`t update resume. Resume with uuid: " + resume.getUuid() + " does`t exist");
        }
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage is overflow");
        } else {
            int index = getIndex(resume.getUuid());
            if (index > -1) {
                System.out.println("Can`t save resume. Resume with uuid: " + resume.getUuid() + " already exist");
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
            System.out.println("Can`t delete resume. Resume with uuid: " + uuid + " does`t exist");
        }
    }

    public Resume get(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            return storage[indexFoundResume];
        }

        System.out.println("Can`t get resume. Resume with uuid: " + uuid + " does`t exist");
        return null;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
