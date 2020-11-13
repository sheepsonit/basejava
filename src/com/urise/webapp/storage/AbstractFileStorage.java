package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    abstract void writeToFile(File searchKey, Resume resume);

    abstract Resume readFromFile(File searchKey);

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    void updateResume(File searchKey, Resume resume) {
        writeToFile(searchKey, resume);
    }

    @Override
    void saveResume(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
            writeToFile(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("IO error ", searchKey.getName(), e);
        }
    }

    @Override
    void deleteResume(File searchKey, String uuid) {
        try {
            Files.delete(searchKey.toPath());
        } catch (IOException e) {
            throw new StorageException("IO error ", searchKey.getName(), e);
        }
    }

    @Override
    Resume getResume(File searchKey, String uuid) {
        return readFromFile(searchKey);
    }

    @Override
    List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();
        File[] fileResumes = directory.listFiles();
        if (fileResumes != null) {
            for (File file : fileResumes) {
                resumes.add(readFromFile(file));
            }
        }
        return resumes;
    }

    @Override
    boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    public void clear() {
        File[] resumes = directory.listFiles();
        if (resumes != null) {
            for (File file : resumes) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    throw new StorageException("IO error ", file.getName(), e);
                }
            }
        }
    }

    @Override
    public int size() {
        String[] fileNames = directory.list();
        return fileNames != null ? fileNames.length : 0;
    }
}
