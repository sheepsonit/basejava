package com.urise.webapp;

import org.w3c.dom.ls.LSOutput;

public class MainDeadlock {

    private static Object counter = 0;
    private static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> doSomething(lock, counter)).start();
        new Thread(() -> doSomething(counter, lock)).start();
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
