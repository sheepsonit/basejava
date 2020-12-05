package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;

    private SerializedStrategy serializedStrategy;

    public FileStorage(File directory, SerializedStrategy strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializedStrategy = strategy;
    }

    @Override
    File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    void updateResume(File searchKey, Resume resume) {
        try {
            this.serializedStrategy.write(new BufferedOutputStream(new FileOutputStream(searchKey)), resume);
        } catch (IOException e) {
            throw new StorageException("Write file error " + searchKey.getAbsolutePath(), resume.getUuid(), e);
        }
    }

    @Override
    void saveResume(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn`t create file " + searchKey.getAbsolutePath(), resume.getUuid(), e);
        }
        updateResume(searchKey, resume);
    }

    @Override
    void deleteResume(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("IO error ", null);
        }
    }

    @Override
    Resume getResume(File searchKey) {
        try {
            return this.serializedStrategy.read(new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Couldn`t read file " + searchKey.getAbsolutePath(), null);
        }
    }

    @Override
    List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();
        File[] fileResumes = directory.listFiles();
        if (fileResumes == null) {
            throw new StorageException("Directory read error " + directory.getAbsolutePath(), null);
        } else {
            for (File file : fileResumes) {
                resumes.add(getResume(file));
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
                deleteResume(file);
            }
        }
    }

    @Override
    public int size() {
        String[] fileNames = directory.list();
        if (fileNames == null) {
            throw new StorageException("Directory read error ", null);
        }
        return fileNames.length;
    }
}
