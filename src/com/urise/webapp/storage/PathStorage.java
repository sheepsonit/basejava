package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private SerializedStrategy serializedStrategy;

    void setStrategy(SerializedStrategy strategy) {
        this.serializedStrategy = strategy;
    }

    void executeWriteStrategy(OutputStream os, Resume resume) {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            this.serializedStrategy.write(os, resume);
        } catch (IOException e) {
            throw new StorageException("Error write resume", resume.getUuid(), e);
        }
    }

    Resume executeReadStrategy(InputStream is) {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return this.serializedStrategy.read(is);
        } catch (IOException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    public PathStorage(String directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory");
        }
        if (!Files.isReadable(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not readable/writable");
        }
    }

    @Override
    Path getSearchKey(String uuid) {
        return Paths.get(directory.toAbsolutePath().toString(), uuid);
    }

    @Override
    void updateResume(Path searchKey, Resume resume) {
        try {
            executeWriteStrategy(new BufferedOutputStream(new FileOutputStream(searchKey.toString())), resume);
        } catch (IOException e) {
            throw new StorageException("Write file error " + searchKey.toAbsolutePath(), resume.getUuid(), e);
        }
    }

    @Override
    void saveResume(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn`t create file " + searchKey.toAbsolutePath(), resume.getUuid(), e);
        }
        updateResume(searchKey, resume);
    }

    @Override
    void deleteResume(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("IO error ", null, e);
        }
    }

    @Override
    Resume getResume(Path searchKey, String uuid) {
        try {
            return executeReadStrategy(new BufferedInputStream(new FileInputStream(searchKey.toString())));
        } catch (IOException e) {
            throw new StorageException("Couldn`t read file " + searchKey.toAbsolutePath(), uuid);
        }
    }

    @Override
    List<Resume> getResumes() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).forEach(file -> {
                try {
                    resumes.add(executeReadStrategy(new BufferedInputStream(new FileInputStream(file.toString()))));
                } catch (IOException e) {
                    throw new StorageException("Couldn`t read file " + file.toAbsolutePath(), null);
                }
            });
        } catch (IOException e) {
            throw new StorageException("Directory read error " + directory.toAbsolutePath(), null);
        }

        return resumes;
    }

    @Override
    boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error ", null);
        }
    }
}
