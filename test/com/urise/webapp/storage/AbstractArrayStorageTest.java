package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update(){
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        Assert.assertEquals(storage.get(resume.getUuid()), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume("dummy");
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        int currentSize = storage.size();
        storage.save(resume);
        Assert.assertEquals(storage.get(resume.getUuid()), resume);
        Assert.assertEquals(++currentSize, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = new Resume("uuid2");
        storage.save(resume);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                Resume resume = new Resume("uuid" + (i + 1));
                storage.save(resume);
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume("uuid" + (storage.size() + 1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int currentSize = storage.size();
        String uuidForDelete = "uuid2";
        storage.delete(uuidForDelete);
        Assert.assertEquals(--currentSize, storage.size());
        storage.get(uuidForDelete);
    }

    @Test
    public void get() {
        String uuid = "uuid2";
        Assert.assertNotNull(storage.get(uuid));
        Assert.assertEquals(uuid, storage.get(uuid).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3,storage.getAll().length);
    }
}