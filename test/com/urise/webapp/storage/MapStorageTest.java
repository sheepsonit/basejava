package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapStorageTest {

    private Storage storage = new MapStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String DUMMY = "dummy";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    void assertSize(int size) {
        assertEquals(size, storage.size());
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
    public void updateResume() {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        assertEquals(storage.get(UUID_2), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        Resume resume = new Resume(DUMMY);
        storage.update(resume);
        assertEquals(storage.get(DUMMY), resume);
    }

    @Test
    public void saveResume() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        assertSize(4);
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistResume() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteResume() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        storage.delete(DUMMY);
    }

    @Test
    public void getResume() {
        assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistResume() {
        storage.get(DUMMY);
    }

    @Test
    public void getAll() {
        Resume[] expectedResumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        assertSize(3);
        for (Resume resume : expectedResumes) {
            assertEquals(resume, storage.get(resume.getUuid()));
        }
    }
}