package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static Integer counter = 0;
    private static final int THREADS_NUMBER = 10000;

    private static Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
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

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
    }

    private void inc() {
        synchronized (this) {
            counter++;
        }

    }
}