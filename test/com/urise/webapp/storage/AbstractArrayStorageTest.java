package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String DUMMY = "dummy";

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        Assert.assertEquals(storage.get(resume.getUuid()), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(DUMMY);
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        int currentSize = storage.size();
        storage.save(resume);
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
        assertSize(++currentSize);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        Resume resume = new Resume(UUID_2);
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
        storage.delete(UUID_2);
        assertSize(--currentSize);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        int currentSize = storage.size();
        storage.delete(DUMMY);
        assertSize(currentSize);
    }

    @Test
    public void get() {
        Assert.assertNotNull(storage.get(UUID_2));
        Assert.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}