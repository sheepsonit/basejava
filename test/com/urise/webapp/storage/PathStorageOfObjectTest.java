package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamPathSerializer;

public class PathStorageOfObjectTest extends AbstractStorageTest {

    public PathStorageOfObjectTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamPathSerializer()));
    }
}