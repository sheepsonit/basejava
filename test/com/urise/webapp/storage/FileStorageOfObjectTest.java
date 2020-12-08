package com.urise.webapp.storage;

public class FileStorageOfObjectTest extends AbstractStorageTest {

    public FileStorageOfObjectTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}