package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.OutputStream;

public interface SerializedStrategy {
    void write(OutputStream os, Resume resume) throws IOException;
}
