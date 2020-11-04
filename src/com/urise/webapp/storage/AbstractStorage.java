package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    abstract SK getSearchKey(String uuid);

    abstract void updateResume(SK searchKey, Resume resume);

    abstract void saveResume(SK searchKey, Resume resume);

    abstract void deleteResume(SK searchKey, String uuid);

    abstract Resume getResume(SK searchKey, String uuid);

    abstract List<Resume> getResumes();

    abstract boolean isExist(SK searchKey);

    @Override
    public void update(Resume resume) {
        updateResume(getExistedKey(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        saveResume(getNotExistedKey(resume.getUuid()), resume);
    }

    @Override
    public void delete(String uuid) {
        deleteResume(getExistedKey(uuid), uuid);
    }

    @Override
    public Resume get(String uuid) {
        return getResume(getExistedKey(uuid), uuid);
    }

    private SK getExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey))
            throw new NotExistStorageException(uuid);
        return searchKey;
    }

    private SK getNotExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey))
            throw new ExistStorageException(uuid);
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getResumes();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }
}
