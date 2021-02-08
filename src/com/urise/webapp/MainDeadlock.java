package com.urise.webapp;

public class MainDeadlock {

    private static Integer counter = 0;
    private static Object LOCK = new Object();

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println(getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (counter) {
                        System.out.println("in counter");
                        for (int i = 0; i < 1000; i++) {
                            counter++;
                        }
                    }
                }
            }
        }.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());

            synchronized (counter) {
                System.out.println("before inc counter");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    System.out.println("in LOCK");

                }
            }
        }).start();

    }
}
