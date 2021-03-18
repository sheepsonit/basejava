package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.SerializedStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private SerializedStrategy serializedStrategy;

    public PathStorage(String directory, SerializedStrategy strategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory");
        }
        if (!Files.isReadable(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not readable/writable");
        }
        serializedStrategy = strategy;
    }

    @Override
    Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    void updateResume(Path searchKey, Resume resume) {
        try {
            serializedStrategy.write(new BufferedOutputStream(Files.newOutputStream(searchKey)), resume);
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
    Resume getResume(Path searchKey) {
        try {
            return serializedStrategy.read(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Couldn`t read file " + searchKey.toAbsolutePath());
        }
    }

    @Override
    List<Resume> getResumes() {
        return getListFiles().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    public void clear() {
        getListFiles().forEach(this::deleteResume);

    }

    @Override
    public int size() {
        return (int) getListFiles().count();
    }

    private Stream<Path> getListFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error " + directory);
        }
    }
}
