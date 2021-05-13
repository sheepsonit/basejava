package com.urise.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Тел."),
    MOBILE("Мобильный тел."),
    SKYPE("Skype") {
        @Override
        protected String toHtml0(String value) {
            return title + ": <a href='" + value + "'>" + value + "</a>";
        }
    },
    MAIL("Почта") {
        @Override
        protected String toHtml0(String value) {
            return title + ": <a href='" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + " : " + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }
}
