package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        field.get(resume);
        field.set(resume,"new UUID");
        System.out.println(resume);
    }
}
