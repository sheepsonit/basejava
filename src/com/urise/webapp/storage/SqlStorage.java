package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.dbTransactionExecute(connection -> {
            sqlHelper.dbConnectAndExecute("delete from contact");
            sqlHelper.dbConnectAndExecute("delete from resume");
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.dbTransactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("update resume set full_name =? where uuid =?")) {
                String uuid = resume.getUuid();
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                ps.execute();
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement("update contact " +
                    " set type =?, " +
                    " value =?, " +
                    " resume_uuid =?")) {
                writeContacts(resume, ps);
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.dbTransactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("insert into resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement("insert into contact (type, value, resume_uuid) " +
                    "VALUES (?,?,?)")) {
                writeContacts(resume, ps);
            }
            return null;
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
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    sqlHelper.dbConnectAndExecute("select * from contact where resume_uuid =?",
                            ps1 -> {
                                resume.addContacts(getContacts(ps1, resume.getUuid()));
                                return null;
                            });
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.dbTransactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("delete from contact where resume_uuid =?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement("delete from resume where uuid =?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
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
                        Resume resume = new Resume(resultSet.getString("uuid"),
                                resultSet.getString("full_name"));
                        sqlHelper.dbConnectAndExecute("select * from contact where resume_uuid =?",
                                ps1 -> {
                                    resume.addContacts(getContacts(ps1, resume.getUuid()));
                                    return null;
                                });
                        resumes.add(resume);
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

    private void writeContacts(Resume resume, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            ps.setString(1, contact.getKey().name());
            ps.setString(2, contact.getValue());
            ps.setString(3, resume.getUuid());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private Map<ContactType, String> getContacts(PreparedStatement ps, String uuid) throws SQLException {
        Map<ContactType, String> contacts = new HashMap<>();
        ps.setString(1, uuid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            contacts.put(ContactType.valueOf(rs.getString("type")),
                    rs.getString("value"));
        }
        return contacts;
    }
}
