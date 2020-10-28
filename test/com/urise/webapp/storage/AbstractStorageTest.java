package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    Storage storage;
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String DUMMY = "dummy";

    static final String FULLNAME_1 = "fullName1";
    static final String FULLNAME_2 = "fullName2";
    static final String FULLNAME_3 = "fullName3";
    static final String FULLNAME_DUMMY = "fullNameDummy";

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1, FULLNAME_1));
        storage.save(new Resume(UUID_2, FULLNAME_2));
        storage.save(new Resume(UUID_3, FULLNAME_3));
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_2, FULLNAME_1);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(DUMMY, FULLNAME_DUMMY));
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(new Resume(UUID_2, FULLNAME_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(DUMMY);
        assertSize(3);
    }

    @Test
    public void get() {
        assertNotNull(storage.get(UUID_2));
        assertEquals(new Resume(UUID_2, FULLNAME_2), storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAllSorted() {
        Resume[] expectedResumes = new Resume[]{new Resume(UUID_1, FULLNAME_1), new Resume(UUID_2, FULLNAME_2), new Resume(UUID_3, FULLNAME_3)};
        Resume[] actualResumes = storage.getAllSorted().toArray(new Resume[0]);
        assertArrayEquals(expectedResumes, actualResumes);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}