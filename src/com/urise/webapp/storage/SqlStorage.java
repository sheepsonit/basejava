package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.dbConnectAndExecute("delete from resume");
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
            try (PreparedStatement ps = connection.prepareStatement("delete from contact where resume_uuid =?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            writeContacts(resume, connection);
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
            writeContacts(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        AtomicReference<Resume> resume = new AtomicReference<>();
        return sqlHelper.dbTransactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("select * from resume where uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume.set(new Resume(uuid, resultSet.getString("full_name")));
            }
            try (PreparedStatement ps = connection.prepareStatement("select * from contact where resume_uuid =?")) {
                resume.get().addContacts(getContacts(ps, resume.get().getUuid()));
            }
            return resume.get();
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.dbTransactionExecute(connection -> {
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
        sqlHelper.dbConnectAndExecute("select * from resume r " +
                        " left join contact c " +
                        " on r.uuid = c.resume_uuid " +
                        " order by full_name, uuid ",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");
                        Resume resume = resumes.stream().filter(r -> r.getUuid().equals(uuid)).
                                findFirst().
                                orElse(new Resume(uuid,
                                        resultSet.getString("full_name")));

                        resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                                resultSet.getString("value"));

                        if (!resumes.contains(resume)) {
                            resumes.add(resume);
                        }
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

    private void writeContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("insert into contact (type, value, resume_uuid) " +
                "VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, contact.getKey().name());
                ps.setString(2, contact.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
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
