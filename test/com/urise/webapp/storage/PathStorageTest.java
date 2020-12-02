package com.urise.webapp.storage;

import org.junit.Before;

import static org.junit.Assert.*;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath()));
    }
}