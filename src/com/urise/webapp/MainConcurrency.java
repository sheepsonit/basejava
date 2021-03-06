package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static Integer counter = 0;
    private static AtomicInteger atomicCounter = new AtomicInteger(0);
    private static final int THREADS_NUMBER = 10000;

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService completionService = new ExecutorCompletionService(executorService);
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread = new Thread(() -> {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return 5;
            });
//            thread.start();
//            threads.add(thread);

            System.out.println(future.get());
        }
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(counter);
        System.out.println(atomicCounter.get());
    }

    private void inc() {
        atomicCounter.incrementAndGet();
//        synchronized (this) {
//        lock.lock();
//        try {
//            counter++;
//        } finally {
//            lock.unlock();
//        }
//        }

    }
}