package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializedStrategy {
    void write(OutputStream os, Resume resume) throws IOException;
    Resume read(InputStream is) throws IOException;
}
