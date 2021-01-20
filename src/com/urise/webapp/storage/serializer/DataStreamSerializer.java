package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements SerializedStrategy {

    public DataStreamSerializer() {

    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeWithException(resume.getContacts().entrySet(), dos, contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });
            writeWithException(resume.getSections().entrySet(), dos, section -> {
                SectionType sectionType = section.getKey();
                dos.writeUTF(sectionType.name());
                AbstractSection tmpSection = section.getValue();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) tmpSection).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeWithException(((BulletedListSection) tmpSection).getContent(), dos, dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeWithException(((OrganizationSection) tmpSection).getContent(), dos, content -> {

                            Link organization = content.getOrganization();
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(organization.getUrl());

                            writeWithException(content.getDates(), dos, experience -> {
                                writeDate(experience.getDateStart(), dos);
                                writeDate(experience.getDateEnd(), dos);
                                dos.writeUTF(experience.getMainInfo());
                                dos.writeUTF(experience.getNote());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
                String type = dis.readUTF();

                switch (SectionType.valueOf(type)) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(SectionType.valueOf(type), new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> bulletedList = new ArrayList<>();
                        readWithException(dis, () -> bulletedList.add(dis.readUTF()));
                        resume.addSection(SectionType.valueOf(type), new BulletedListSection(bulletedList));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Experience> experienceList = new ArrayList<>();
                        readWithException(dis, () -> {
                            String organizationName = dis.readUTF();
                            String url = dis.readUTF();

                            List<Experience.DateIntervalExperience> dates = new ArrayList<>();

                            readWithException(dis, () -> dates.add(new Experience.DateIntervalExperience(YearMonth.of(dis.readInt(), dis.readInt()), YearMonth.of(dis.readInt(), dis.readInt()), dis.readUTF(), dis.readUTF())));
                            experienceList.add(new Experience(new Link(organizationName, url), dates));
                        });
                        resume.addSection(SectionType.valueOf(type), new OrganizationSection(experienceList));
                        break;
                }
            });

            return resume;
        }
    }

    @FunctionalInterface
    public interface ConsumerThrow<T> {
        void accept(T t) throws IOException;
    }

    @FunctionalInterface
    public interface FunctionThrow {
        void apply() throws IOException;
    }

    private void writeDate(YearMonth date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerThrow<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T section : collection) {
            action.accept(section);
        }
    }

    private void readWithException(DataInputStream dis, FunctionThrow action) throws IOException {
        int cnt = dis.readInt();
        for (int i = 0; i < cnt; i++) {
            action.apply();
        }
    }

}
