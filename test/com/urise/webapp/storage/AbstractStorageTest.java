package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractStorageTest {
    Storage storage;
    static final File STORAGE_DIR = new File("src/storage");
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String DUMMY = "dummy";

    static final Resume RESUME_1 = ResumeTestData.setupResume(UUID_1, "fullName_uuid1");
    static final Resume RESUME_2 = ResumeTestData.setupResume(UUID_2, "fullName_uuid2");
    static final Resume RESUME_3 = ResumeTestData.setupResume(UUID_3, "fullName_uuid3");

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
        Resume resume = ResumeTestData.setupResume(UUID_2, "fullName_uuid1");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(ResumeTestData.setupResume(DUMMY, "fullName_dummy"));
    }

    @Test
    public void save() {
        Resume resume = ResumeTestData.setupResume("uuid4", "fullName_uuid4");
        storage.save(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME_2);
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
        assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(DUMMY);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(expectedResumes, actualResumes);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}