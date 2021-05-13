package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static Resume setupResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        StringBuilder phoneNumber = new StringBuilder("+7(9");
        for (int i = 0; i < 9; i++) {
            if (i == 2)
                phoneNumber.append(") ");
            if (i == 5)
                phoneNumber.append("-");
            phoneNumber.append((int) (0 + Math.random() * 10));
        }
        System.out.println(phoneNumber);
        resume.addContact(ContactType.PHONE_NUMBER, phoneNumber.toString());
        resume.addContact(ContactType.MAIL, uuid + "@yandex.ru");

        AbstractSection position = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, position);

        AbstractSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, personal);


        List<String> listAchievements = new ArrayList<>();
        listAchievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");

        AbstractSection achievement = new BulletedListSection(listAchievements);
        resume.addSection(SectionType.ACHIEVEMENT, achievement);

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        listQualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        listQualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        listQualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        listQualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), " +
                "JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");

        AbstractSection qualification = new BulletedListSection(listQualifications);
        resume.addSection(SectionType.QUALIFICATION, qualification);

        List<Experience> listExperiences = new ArrayList<>();

        Experience sectionExp1 = new Experience("Java Online Projects",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2013, 10),
                        YearMonth.now(),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        listExperiences.add(sectionExp1);

        Experience sectionExp2 = new Experience("Wrike",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2014, 10),
                        YearMonth.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        listExperiences.add(sectionExp2);

        AbstractSection experience = new OrganizationSection(listExperiences);
        resume.addSection(SectionType.EXPERIENCE, experience);

        List<Experience> educations = new ArrayList<>();

        Experience sectionEducation1 = new Experience("Alcatel",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(1997, 9),
                        YearMonth.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)",
                        ""));
        educations.add(sectionEducation1);

        Experience sectionEducation2 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7),
                        "Аспирантура (программист С, С++)",
                        ""),
                new Experience.DateIntervalExperience(
                        YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7),
                        "Инженер (программист Fortran, C)",
                        ""));
        educations.add(sectionEducation2);

        Experience sectionEducation3 = new Experience("Заочная физико-техническая школа при МФТИ",
                null,
                new Experience.DateIntervalExperience(
                        YearMonth.of(1984, 9),
                        YearMonth.of(1987, 6),
                        "Закончил с отличием",
                        null));
        educations.add(sectionEducation3);

        AbstractSection education = new OrganizationSection(educations);
        resume.addSection(SectionType.EDUCATION, education);

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");

        AbstractSection position = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, position);

        AbstractSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, personal);


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

        AbstractSection achievement = new BulletedListSection(listAchievements);
        resume.addSection(SectionType.ACHIEVEMENT, achievement);

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

        AbstractSection qualification = new BulletedListSection(listQualifications);
        resume.addSection(SectionType.QUALIFICATION, qualification);

        List<Experience> listExperiences = new ArrayList<>();

        Experience sectionExp1 = new Experience("Java Online Projects",
                "",
                new Experience.DateIntervalExperience(YearMonth.of(2013, 10),
                        YearMonth.now(),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        listExperiences.add(sectionExp1);

        Experience sectionExp2 = new Experience("Wrike",
                "",
                new Experience.DateIntervalExperience(YearMonth.of(2014, 10),
                        YearMonth.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        listExperiences.add(sectionExp2);

        Experience sectionExp3 = new Experience("RIT Center",
                "",
                new Experience.DateIntervalExperience(YearMonth.of(2012, 4),
                        YearMonth.of(2014, 10),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), " +
                                "миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                                "Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python"));
        listExperiences.add(sectionExp3);

        Experience sectionExp4 = new Experience("Luxoft (Deutsche Bank)",
                "",
                new Experience.DateIntervalExperience(YearMonth.of(2010, 12),
                        YearMonth.of(2012, 4),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                                "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        listExperiences.add(sectionExp4);

        Experience sectionExp5 = new Experience("Yota",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2008, 6),
                        YearMonth.of(2010, 12),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, " +
                                "JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента " +
                                "(Python/ Jython, Django, ExtJS)"));
        listExperiences.add(sectionExp5);

        Experience sectionExp6 = new Experience("Enkata",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2007, 3),
                        YearMonth.of(2008, 6),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        listExperiences.add(sectionExp6);

        Experience sectionExp7 = new Experience("Siemens AG",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2005, 1),
                        YearMonth.of(2007, 2),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        listExperiences.add(sectionExp7);

        Experience sectionExp8 = new Experience("Alcatel",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(1997, 9),
                        YearMonth.of(2005, 1),
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        listExperiences.add(sectionExp8);

        AbstractSection experience = new OrganizationSection(listExperiences);
        resume.addSection(SectionType.EXPERIENCE, experience);

        List<Experience> educations = new ArrayList<>();

        Experience sectionEducation1 = new Experience("Coursera",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2013, 3),
                        YearMonth.of(2013, 5),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
                        ""));
        educations.add(sectionEducation1);

        Experience sectionEducation2 = new Experience("Luxoft",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2011, 3),
                        YearMonth.of(2011, 4),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
                        ""));
        educations.add(sectionEducation2);

        Experience sectionEducation3 = new Experience("Siemens AG",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(2005, 1),
                        YearMonth.of(2005, 4),
                        "3 месяца обучения мобильным IN сетям (Берлин)",
                        ""));
        educations.add(sectionEducation3);

        Experience sectionEducation4 = new Experience("Alcatel",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(1997, 9),
                        YearMonth.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)",
                        ""));
        educations.add(sectionEducation4);

        Experience sectionEducation5 = new Experience("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7),
                        "Аспирантура (программист С, С++)",
                        ""),
                new Experience.DateIntervalExperience(
                        YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7),
                        "Инженер (программист Fortran, C)",
                        ""));
        educations.add(sectionEducation5);

        Experience sectionEducation6 = new Experience("Заочная физико-техническая школа при МФТИ",
                "",
                new Experience.DateIntervalExperience(
                        YearMonth.of(1984, 9),
                        YearMonth.of(1987, 6),
                        "Закончил с отличием",
                        ""));
        educations.add(sectionEducation6);

        AbstractSection education = new OrganizationSection(educations);
        resume.addSection(SectionType.EDUCATION, education);

        System.out.println(resume.getFullName());
        System.out.println();

        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType.getTitle() + " : " + resume.getContact(contactType));
        }

        System.out.println();

        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType.getTitle());
            System.out.println();

            if (sectionType.getTitle().equals("Личные качества") || sectionType.getTitle().equals("Позиция")) {
                System.out.println(((TextSection) resume.getSection(sectionType)).getContent());
            }

            if (sectionType.getTitle().equals("Достижения") || sectionType.getTitle().equals("Квалификация")) {
                List<String> bulletedList = ((BulletedListSection) resume.getSection(sectionType)).getContent();
                for (String str : bulletedList) {
                    System.out.println("- " + str);
                }
            }

            if (sectionType.getTitle().equals("Опыт работы") || sectionType.getTitle().equals("Образование")) {
                List<Experience> bulletedList = ((OrganizationSection) resume.getSection(sectionType)).getContent();
                for (Experience exp : bulletedList) {
                    System.out.println(exp.getOrganization());
                    for (Experience.DateIntervalExperience dates : exp.getDates()) {
                        System.out.println(dates.getDateStart().format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dates.getDateEnd().format(DateTimeFormatter.ofPattern("MM/yyyy")));
                        System.out.println(dates.getMainInfo());
                        System.out.println(dates.getNote());
                    }
                    System.out.println();
                }
            }

            System.out.println();
        }
    }
}
