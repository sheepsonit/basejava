package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    abstract SK getSearchKey(String uuid);

    abstract void updateResume(SK searchKey, Resume resume);

    abstract void saveResume(SK searchKey, Resume resume);

    abstract void deleteResume(SK searchKey);

    abstract Resume getResume(SK searchKey);

    abstract List<Resume> getResumes();

    abstract boolean isExist(SK searchKey);

    @Override
    public void update(Resume resume) {
        LOG.info("update resume: " + resume);
        updateResume(getExistedKey(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("save resume: " + resume);
        saveResume(getNotExistedKey(resume.getUuid()), resume);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete resume: " + uuid);
        deleteResume(getExistedKey(uuid));
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get resume: " + uuid);
        return getResume(getExistedKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getResumes();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    private SK getExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
