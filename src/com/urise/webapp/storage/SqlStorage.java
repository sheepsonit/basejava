package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No driver found for PostgreSQL database", e);
        }
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
        return sqlHelper.dbTransactionExecute(connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement("select * from resume where uuid =?")) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement ps = connection.prepareStatement("select * from contact where resume_uuid =?")) {
                resume.addContacts(getContacts(ps, uuid));
            }
            try (PreparedStatement ps = connection.prepareStatement("select * from section where resume_uuid =?")) {
                resume.addSections(getSections(ps, uuid));
            }
            return resume;
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

        return sqlHelper.dbTransactionExecute(connection -> {
            String uuid;
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("select * from resume r order by full_name, uuid ")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    uuid = resultSet.getString("uuid");

                    Resume resume = resumes.getOrDefault(uuid, new Resume(uuid,
                            resultSet.getString("full_name")));


                    resumes.put(uuid, resume);
                }
            }
            try (PreparedStatement ps1 = connection.prepareStatement("select c.resume_uuid as uuid, " +
                    "c.type as contact_type, c.value as contact_value, " +
                    "s.type as section_type, s.value as section_value " +
                    "from contact c " +
                    "join section s on c.resume_uuid = s.resume_uuid")) {
                ResultSet rs = ps1.executeQuery();

                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("uuid"));

                    resume.addContact(ContactType.valueOf(rs.getString("contact_type")),
                            rs.getString("contact_value"));

                    SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));

                    switch (sectionType) {
                        case EDUCATION:
                        case EXPERIENCE:
                            break;
                        case PERSONAL:
                        case OBJECTIVE:
                            resume.addSection(sectionType, new TextSection(rs.getString("section_value")));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            resume.addSection(sectionType,
                                    new BulletedListSection(rs.getString("section_value").split("\n")));
                            break;
                    }
                    resumes.put(resume.getUuid(), resume);
                }
            }

            return new ArrayList<>(resumes.values());
        });
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
                "insert into section (type, value, resume_uuid) " +
                        "VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> contact : resume.getSections().entrySet()) {

                switch (contact.getKey()) {
                    case EDUCATION:
                    case EXPERIENCE:
                        break;
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(2, ((TextSection) contact.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> content = ((BulletedListSection) contact.getValue()).getContent();
                        ps.setString(2, String.join("\n", content));
                        break;
                }
                ps.setString(1, contact.getKey().name());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
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

            switch (sectionType) {
                case EDUCATION:
                case EXPERIENCE:
                    break;
                case PERSONAL:
                case OBJECTIVE:
                    sectionMap.put(sectionType, new TextSection(rs.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    sectionMap.put(sectionType,
                            new BulletedListSection(rs.getString("value").split("\n")));
                    break;
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
