package com.urise.webapp;

public class MainDeadlock {

    private static Integer counter = 0;
    private static Object LOCK = new Object();

    public static void main(String[] args) {
        new Thread(() -> doSomething(LOCK, counter)).start();
        new Thread(() -> doSomething(counter, LOCK)).start();
    }

    public static void doSomething(Object firstLock, Object secondLock) {
        System.out.println(Thread.currentThread().getName());
        synchronized (firstLock) {
            System.out.println("into first lock");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (secondLock) {
                System.out.println("into second lock");
            }

        }
    }
}
