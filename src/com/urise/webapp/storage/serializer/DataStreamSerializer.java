package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
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
                dos.writeUTF(section.getKey().name());
                AbstractSection tmpSection = section.getValue();
                if (tmpSection instanceof TextSection) {
                    dos.writeUTF(((TextSection) tmpSection).getContent());
                } else if (tmpSection instanceof BulletedListSection) {
                    dos.writeInt(((BulletedListSection) tmpSection).getContent().size());
                    ((BulletedListSection) tmpSection).getContent().forEach(content -> {
                        try {
                            dos.writeUTF(content);
                        } catch (IOException e) {
                            throw new StorageException("Error write resume bulleted list section", null, e);
                        }
                    });
                } else if (tmpSection instanceof OrganizationSection) {
                    dos.writeInt(((OrganizationSection) tmpSection).getContent().size());
                    ((OrganizationSection) tmpSection).getContent().forEach(content -> {
                        try {
                            dos.writeUTF(content.getOrganization().getName());
                            dos.writeUTF(content.getOrganization().getUrl());
                            dos.writeInt(content.getDates().size());
                            content.getDates().forEach(experience -> {
                                try {
                                    dos.writeInt(experience.getDateStart().getYear());
                                    dos.writeInt(experience.getDateStart().getMonthValue());
                                    dos.writeInt(experience.getDateEnd().getYear());
                                    dos.writeInt(experience.getDateEnd().getMonthValue());
                                    dos.writeUTF(experience.getMainInfo());
                                    dos.writeUTF(experience.getNote());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (IOException e) {
                            throw new StorageException("Error write resume organization section", null, e);
                        }
                    });
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

                if (type.equals(SectionType.PERSONAL.name()) || type.equals(SectionType.OBJECTIVE.name())) {
                    resume.addSection(SectionType.valueOf(type), new TextSection(dis.readUTF()));
                }

                if (type.equals(SectionType.ACHIEVEMENT.name()) || type.equals(SectionType.QUALIFICATION.name())) {
                    int cnt = dis.readInt();
                    List<String> bulletedList = new ArrayList<>(cnt);
                    for (int i = 0; i < cnt; i++) {
                        bulletedList.add(dis.readUTF());
                    }
                    resume.addSection(SectionType.valueOf(type), new BulletedListSection(bulletedList));
                }

                if (type.equals(SectionType.EXPERIENCE.name()) || type.equals(SectionType.EDUCATION.name())) {
                    int cnt = dis.readInt();
                    List<Experience> bulletedList = new ArrayList<>(cnt);
                    for (int i = 0; i < cnt; i++) {
                        String organizationName = dis.readUTF();
                        String url = dis.readUTF();
                        int cntDates = dis.readInt();
                        List<Experience.DateIntervalExperience> dates = new ArrayList<>(cntDates);

                        for (int j = 0; j < cntDates; j++) {
                            dates.add(new Experience.DateIntervalExperience(YearMonth.of(dis.readInt(), dis.readInt()), YearMonth.of(dis.readInt(), dis.readInt()), dis.readUTF(), dis.readUTF()));
                        }
                        bulletedList.add(new Experience(new Link(organizationName, url), dates));

                    }
                    resume.addSection(SectionType.valueOf(type), new OrganizationSection(bulletedList));
                }
            }

            return resume;
        }
    }
}
