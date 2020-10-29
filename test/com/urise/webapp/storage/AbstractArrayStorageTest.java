package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume("uuid" + (i + 1), "fullName_uuid" + (i + 1));
                storage.save(resume);
            }
        } catch (StorageException e) {
            Assert.fail("Error while saving to storage");
        }
        storage.save(new Resume("uuid" + (storage.size() + 1),"fullName_uuid" + (storage.size() + 1)));
    }

}