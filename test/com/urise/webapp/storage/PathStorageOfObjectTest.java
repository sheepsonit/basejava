package com.urise.webapp.storage;

public class PathStorageOfObjectTest extends AbstractStorageTest {

    public PathStorageOfObjectTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamPathStorage()));
    }
}