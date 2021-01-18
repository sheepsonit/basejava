package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private String uuid;

    private String fullName;

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

     public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public void addContacts(Map<ContactType, String> contacts) {
        this.contacts.putAll(contacts);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void addSection(SectionType type, AbstractSection section) {
        this.sections.put(type, section);
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections.putAll(sections);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && contacts.equals(resume.contacts) && sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
