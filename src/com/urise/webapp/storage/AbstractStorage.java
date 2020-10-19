package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int indexFoundResume = getIndex(resume.getUuid());
        if (indexFoundResume > -1) {
            updateResume(indexFoundResume, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(index, resume);
        }
    }

    @Override
    public void delete(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            deleteResume(indexFoundResume, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume > -1) {
            return getResume(indexFoundResume, uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    abstract int getIndex(String uuid);

    abstract void updateResume(int searchKey, Resume resume);

    abstract void saveResume(int searchKey, Resume resume);

    abstract void deleteResume(int searchKey, String uuid);

    abstract Resume getResume(int searchKey, String uuid);

}
