package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        updateResume(checkExist(resume.getUuid()), resume);
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
        deleteResume(checkExist(uuid), uuid);
    }

    @Override
    public Resume get(String uuid) {
        return getResume(checkExist(uuid), uuid);
    }

    private int checkExist(String uuid) {
        int indexFoundResume = getIndex(uuid);
        if (indexFoundResume == -1)
            throw new NotExistStorageException(uuid);
        return indexFoundResume;
    }

    abstract int getIndex(String uuid);

    abstract void updateResume(int searchKey, Resume resume);

    abstract void saveResume(int searchKey, Resume resume);

    abstract void deleteResume(int searchKey, String uuid);

    abstract Resume getResume(int searchKey, String uuid);

}
