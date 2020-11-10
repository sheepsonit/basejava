package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");

        Map<SectionType, Section> sectionsList = new HashMap<>();

        Section position = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        sectionsList.put(SectionType.OBJECTIVE, position);

        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        sectionsList.put(SectionType.PERSONAL, personal);


        List<String> listAchievements = new ArrayList<>();
        listAchievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, " +
                "Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        listAchievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        listAchievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        listAchievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), " +
                "Белоруcсии(Erip, Osmp) и Никарагуа.");

        Section achievement = new BulletedListSection(listAchievements);
        sectionsList.put(SectionType.ACHIEVEMENT, achievement);

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        listQualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        listQualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        listQualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        listQualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), " +
                "JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        listQualifications.add("Python: Django.");
        listQualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        listQualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        listQualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, " +
                "AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        listQualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        listQualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        listQualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        listQualifications.add("Родной русский, английский \"upper intermediate\"");

        Section qualification = new BulletedListSection(listQualifications);
        sectionsList.put(SectionType.QUALIFICATION, qualification);

        List<Experience> listExperiences = new ArrayList<>();

        Experience sectionExp1 = new Experience("Java Online Projects",
                YearMonth.of(2013, 10),
                null,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        listExperiences.add(sectionExp1);

        Experience sectionExp2 = new Experience("Wrike",
                YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        listExperiences.add(sectionExp2);

        Experience sectionExp3 = new Experience("RIT Center",
                YearMonth.of(2012, 4),
                YearMonth.of(2014, 10),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), " +
                        "миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                        "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                        "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                        "Unix shell remote scripting via ssh tunnels, PL/Python");
        listExperiences.add(sectionExp3);

        Experience sectionExp4 = new Experience("Luxoft (Deutsche Bank)",
                YearMonth.of(2010, 12),
                YearMonth.of(2012, 4),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                        "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                        "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        listExperiences.add(sectionExp4);

        Experience sectionExp5 = new Experience("Yota",
                YearMonth.of(2008, 6),
                YearMonth.of(2010, 12),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, " +
                        "JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента " +
                        "(Python/ Jython, Django, ExtJS)");
        listExperiences.add(sectionExp5);

        Experience sectionExp6 = new Experience("Enkata",
                YearMonth.of(2007, 3),
                YearMonth.of(2008, 6),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        listExperiences.add(sectionExp6);

        Experience sectionExp7 = new Experience("Siemens AG",
                YearMonth.of(2005, 1),
                YearMonth.of(2007, 2),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        listExperiences.add(sectionExp7);

        Experience sectionExp8 = new Experience("Alcatel",
                YearMonth.of(1997, 9),
                YearMonth.of(2005, 1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        listExperiences.add(sectionExp8);

        Section experience = new OrganizationSection(listExperiences);
        sectionsList.put(SectionType.EXPERIENCE, experience);

        List<Experience> educations = new ArrayList<>();

        Experience sectionEducation1 = new Experience("Coursera",
                YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");
        educations.add(sectionEducation1);

        Experience sectionEducation2 = new Experience("Luxoft",
                YearMonth.of(2011, 3),
                YearMonth.of(2011, 4),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");
        educations.add(sectionEducation2);

        Experience sectionEducation3 = new Experience("Siemens AG",
                YearMonth.of(2005, 1),
                YearMonth.of(2005, 4),
                "3 месяца обучения мобильным IN сетям (Берлин)",
                "");
        educations.add(sectionEducation3);

        Experience sectionEducation4 = new Experience("Alcatel",
                YearMonth.of(1997, 9),
                YearMonth.of(1998, 3),
                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                "");
        educations.add(sectionEducation4);

        Experience sectionEducation5 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                YearMonth.of(1993, 9),
                YearMonth.of(1996, 7),
                "Аспирантура (программист С, С++)",
                "");
        educations.add(sectionEducation5);

        Experience sectionEducation6 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                YearMonth.of(1987, 9),
                YearMonth.of(1993, 7),
                "Инженер (программист Fortran, C)",
                "");
        educations.add(sectionEducation6);

        Experience sectionEducation7 = new Experience("Заочная физико-техническая школа при МФТИ",
                YearMonth.of(1984, 9),
                YearMonth.of(1987, 6),
                "Закончил с отличием",
                "");
        educations.add(sectionEducation7);

        Section education = new OrganizationSection(educations);
        sectionsList.put(SectionType.EDUCATION, education);

        resume.setSections(sectionsList);

        System.out.println(resume.getFullName());
        System.out.println();

        for (Map.Entry<ContactType, String> contactEntry : resume.getContacts().entrySet()) {
            System.out.println(contactEntry.getKey().getTitle() + " : " + contactEntry.getValue());
        }

        System.out.println();

        for (Map.Entry<SectionType, Section> sectionEntry : resume.getSections().entrySet()) {
            String sectionTitle = sectionEntry.getKey().getTitle();
            System.out.println(sectionTitle);
            System.out.println();

            if (sectionTitle.equals("Личные качества") || sectionTitle.equals("Позиция")) {
                System.out.println(sectionEntry.getValue().getContent().toString());
            }

            if (sectionTitle.equals("Достижения") || sectionTitle.equals("Квалификация")) {
                List<String> bulletedList = (List<String>) sectionEntry.getValue().getContent();
                for (String str : bulletedList) {
                    System.out.println("- " + str);
                }
            }

            if (sectionTitle.equals("Опыт работы") || sectionTitle.equals("Образование")) {
                List<Experience> bulletedList = (List<Experience>) sectionEntry.getValue().getContent();
                for (Experience exp : bulletedList) {
                    System.out.println(exp.getOrganization());
                    System.out.println(exp.getDateStart().format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + (exp.getDateEnd() == null ? "по настоящее время" : exp.getDateEnd().format(DateTimeFormatter.ofPattern("MM/yyyy"))));
                    System.out.println(exp.getMainInfo());
                    System.out.println(exp.getNote());
                    System.out.println();
                }
            }

            System.out.println();
        }
    }
}
