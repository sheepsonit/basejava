package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.JsonParser;

import java.io.*;

public class JsonStreamSerializer implements SerializedStrategy {

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(os)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
