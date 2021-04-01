package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
            try (PreparedStatement ps = connection.prepareStatement("delete from section where resume_uuid =?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }

            writeContacts(resume, connection);
            writeSections(resume, connection);
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
            writeSections(resume, connection);
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
            try (PreparedStatement ps = connection.prepareStatement("select * from section where resume_uuid =?")) {
                resume.get().addSections(getSections(ps, resume.get().getUuid()));
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
        Map<String, Resume> resumes = new LinkedHashMap<>();

        sqlHelper.dbConnectAndExecute("select * from resume r order by full_name, uuid ",
                ps -> {
                    ResultSet resultSet = ps.executeQuery();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");

                        Resume resume = resumes.getOrDefault(uuid, new Resume(uuid,
                                resultSet.getString("full_name")));

                        sqlHelper.dbConnectAndExecute("select * from contact where resume_uuid =?", ps1 -> {
                            resume.addContacts(getContacts(ps1, resume.getUuid()));
                            return null;
                        });

                        sqlHelper.dbConnectAndExecute("select * from section where resume_uuid =?", ps2 -> {
                            resume.addSections(getSections(ps2, resume.getUuid()));
                            return null;
                        });
                        resumes.put(uuid, resume);
                    }
                    return null;
                });


        return new ArrayList<>(resumes.values());
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

    private void writeSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into section (type, content, resume_uuid) " +
                        "VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> contact : resume.getSections().entrySet()) {
                if (!contact.getKey().equals(SectionType.EDUCATION) && !contact.getKey().equals(SectionType.EXPERIENCE)) {

                    ps.setString(1, contact.getKey().name());
                    if (contact.getKey().equals(SectionType.PERSONAL) || contact.getKey().equals(SectionType.OBJECTIVE)) {
                        ps.setString(2, ((TextSection) contact.getValue()).getContent());
                    } else {
                        List<String> content = ((BulletedListSection) contact.getValue()).getContent();
                        ps.setString(2, content.stream().reduce("", (x, y) -> x + y + "\n"));
                    }
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();

                }
            }
            ps.executeBatch();
        }
    }

    private Map<SectionType, AbstractSection> getSections(PreparedStatement ps, String uuid) throws SQLException {
        Map<SectionType, AbstractSection> sectionMap = new HashMap<>();
        ps.setString(1, uuid);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type"));

            if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
                sectionMap.put(sectionType,
                        new TextSection(rs.getString("content")));
            } else if (sectionType.equals(SectionType.QUALIFICATION) || sectionType.equals(SectionType.ACHIEVEMENT)) {
                sectionMap.put(sectionType,
                        new BulletedListSection(rs.getString("content").split("\n")));
            }
        }

        return sectionMap;
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
