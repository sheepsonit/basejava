package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.dbConnectAndExecute(connectionFactory, "delete from resume", (PreparedStatement::execute));
    }

    @Override
    public void update(Resume resume) {
        SqlHelper.dbConnectAndExecute(connectionFactory, "update resume set full_name =? where uuid =?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    ps.execute();
                    if (ps.getUpdateCount() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                });
    }

    @Override
    public void save(Resume resume) {
        SqlHelper.dbConnectAndExecute(connectionFactory, "insert into resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.execute();
                });
    }

    @Override
    public Resume get(String uuid) {
        AtomicReference<Resume> resume = new AtomicReference<>();
        SqlHelper.dbConnectAndExecute(connectionFactory, "select * from resume where uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        resume.set(new Resume(uuid, resultSet.getString("full_name").strip()));
                    } else {
                        throw new NotExistStorageException(uuid);
                    }
                });
        return resume.get();
    }

    @Override
    public void delete(String uuid) {
        SqlHelper.dbConnectAndExecute(connectionFactory, "delete from resume where uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ps.execute();
                    if (ps.getUpdateCount() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        SqlHelper.dbConnectAndExecute(connectionFactory, "select * from resume",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        resumes.add(new Resume(resultSet.getString("uuid").strip(),
                                resultSet.getString("full_name").strip()));
                    }
                });
        return resumes;
    }

    @Override
    public int size() {
        AtomicInteger cnt = new AtomicInteger();
        SqlHelper.dbConnectAndExecute(connectionFactory, "select count(uuid) cnt from resume",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    resultSet.next();
                    cnt.set(resultSet.getInt("cnt"));
                });
        return cnt.get();
    }
}
