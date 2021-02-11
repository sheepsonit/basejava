package com.urise.webapp;

import org.w3c.dom.ls.LSOutput;

public class MainDeadlock {

    private static Object lockOne = new Object();
    private static Object lockTwo = new Object();

    public static void main(String[] args) {
        new Thread(() -> doSomething(lockOne, lockTwo)).start();
        new Thread(() -> doSomething(lockTwo, lockOne)).start();
    }

    public static void doSomething(Object firstLock, Object secondLock) {

        synchronized (firstLock) {
            System.out.println("The thread " + Thread.currentThread().getName() + " has captured an object " + firstLock.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The thread " + Thread.currentThread().getName() + " is waiting to capture the object " + secondLock.toString());
            synchronized (secondLock) {
                System.out.println("The thread " + Thread.currentThread().getName() + " has captured an object " + secondLock.toString());
            }

        }
    }
}
