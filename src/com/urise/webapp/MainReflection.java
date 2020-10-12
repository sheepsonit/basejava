package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        field.get(resume);
        field.set(resume,"new UUID");
        Method toStringMethod = resume.getClass().getMethod("toString");
        System.out.println(toStringMethod.invoke(resume));
    }
}
