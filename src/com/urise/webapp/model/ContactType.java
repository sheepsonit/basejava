package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Тел."),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINKEDIN("LINKEDIN"),
    GITHUB("GITHUB"),
    STACKOVERFLOW("STACKOVERFLOW");

    String title;

    ContactType(String title) {
        this.title = title;
    }

}
