package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        int indexFoundResume = getIndex(resume.getUuid());
        if (indexFoundResume > -1) {
            updateResume(indexFoundResume, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        if (checkSize(resume.getUuid())) {
            int index = getIndex(resume.getUuid());
            if (index > -1) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                saveResume(index, resume);
            }
        }
    }

    public void delete(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            deleteResume(indexFoundResume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            return getResume(indexFoundResume);
        }
        throw new NotExistStorageException(uuid);
    }


    abstract boolean checkSize(String uuid);

    abstract int getIndex(String uuid);

    abstract void updateResume(int index, Resume resume);

    abstract void saveResume(int index, Resume resume);

    abstract void deleteResume(int index);

    abstract Resume getResume(int index);

}
