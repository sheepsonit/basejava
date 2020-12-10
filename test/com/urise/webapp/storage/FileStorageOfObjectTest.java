package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class FileStorageOfObjectTest extends AbstractStorageTest {

    public FileStorageOfObjectTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}