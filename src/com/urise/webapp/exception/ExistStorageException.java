package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        this(uuid, null);
    }

    public ExistStorageException(String uuid, Exception e) {
        super("Resume " + uuid + " already exist", e);
    }
}
