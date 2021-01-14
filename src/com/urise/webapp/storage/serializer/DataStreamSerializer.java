package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializedStrategy {

    public DataStreamSerializer() {

    }

    @Override
    public void write(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            }
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
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
            }
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int cntContacts = dis.readInt();

            for (int i = 0; i < cntContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            while (dis.available() > 0) {
                String type = dis.readUTF();

                switch (SectionType.valueOf(type)) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(SectionType.valueOf(type), new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        int cnt = dis.readInt();
                        List<String> bulletedList = new ArrayList<>(cnt);
                        for (int i = 0; i < cnt; i++) {
                            bulletedList.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.valueOf(type), new BulletedListSection(bulletedList));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        cnt = dis.readInt();
                        List<Experience> experienceList = new ArrayList<>(cnt);
                        for (int i = 0; i < cnt; i++) {
                            String organizationName = dis.readUTF();
                            String url = dis.readUTF();
                            int cntDates = dis.readInt();
                            List<Experience.DateIntervalExperience> dates = new ArrayList<>(cntDates);

                            for (int j = 0; j < cntDates; j++) {
                                dates.add(new Experience.DateIntervalExperience(YearMonth.of(dis.readInt(), dis.readInt()), YearMonth.of(dis.readInt(), dis.readInt()), dis.readUTF(), dis.readUTF()));
                            }
                            experienceList.add(new Experience(new Link(organizationName, url), dates));

                        }
                        resume.addSection(SectionType.valueOf(type), new OrganizationSection(experienceList));
                        break;
                }
            }

            return resume;
        }
    }

    @FunctionalInterface
    public interface ConsumerThrow<T> {
        void accept(T t) throws IOException;
    }

    private void writeDate(YearMonth date, DataOutputStream dos) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private <T> void writeWithException(List<T> collection, DataOutputStream dos, ConsumerThrow<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T section : collection) {
            action.accept(section);
        }
    }

}
