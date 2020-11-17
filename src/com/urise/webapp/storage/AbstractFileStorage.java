package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    abstract void writeToFile(OutputStream searchKey, Resume resume) throws IOException;

    abstract Resume readFromFile(InputStream searchKey) throws IOException;

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
        try {
            writeToFile(new BufferedOutputStream(new FileOutputStream(searchKey)), resume);
        } catch (IOException e) {
            throw new StorageException("Write file error " + searchKey.getAbsolutePath(), searchKey.getName(), e);
        }
    }

    @Override
    void saveResume(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn`t create file " + searchKey.getAbsolutePath(), searchKey.getName(), e);
        }
        updateResume(searchKey, resume);
    }

    @Override
    void deleteResume(File searchKey) {
        try {
            Files.delete(searchKey.toPath());
        } catch (IOException e) {
            throw new StorageException("IO error ", searchKey.getName(), e);
        }
    }

    @Override
    Resume getResume(File searchKey, String uuid) {
        try {
            return readFromFile(new BufferedInputStream(new FileInputStream(searchKey)));
        }
        catch (IOException e) {
            throw new StorageException("Couldn`t read file " + searchKey.getAbsolutePath(), uuid);
        }
    }

    @Override
    List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();
        File[] fileResumes = directory.listFiles();
        if (fileResumes != null) {
            for (File file : fileResumes) {
                try {
                    resumes.add(readFromFile(new BufferedInputStream(new FileInputStream(file))));
                }
                catch (IOException e) {
                    throw new StorageException("Couldn`t read file " + file.getAbsolutePath(), null);
                }
            }
        }
        else {
            throw new StorageException("Directory read error " + directory.getAbsolutePath(), null);
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
