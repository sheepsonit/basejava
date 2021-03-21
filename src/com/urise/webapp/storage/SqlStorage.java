package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.dbConnectAndExecute("delete from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.dbConnectAndExecute("update resume set full_name =? where uuid =?",
                ps -> {
                    String uuid = resume.getUuid();
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.dbConnectAndExecute("insert into resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    return ps.execute();
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.dbConnectAndExecute("select * from resume where uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet resultSet = ps.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, resultSet.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.dbConnectAndExecute("delete from resume where uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.dbConnectAndExecute("select * from resume order by full_name, uuid",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        resumes.add(new Resume(resultSet.getString("uuid"),
                                resultSet.getString("full_name")));
                    }
                    return null;
                });
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.dbConnectAndExecute("select count(uuid) cnt from resume",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    resultSet.next();
                    return resultSet.getInt("cnt");
                });
    }
}
