package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        this(uuid, null);
    }

    public NotExistStorageException(String uuid, Exception e) {
        super("Resume " + uuid + " not exist", e);
    }
}
