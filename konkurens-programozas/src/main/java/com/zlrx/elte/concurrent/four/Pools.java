package com.zlrx.elte.concurrent.four;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pools {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5, (Runnable r) -> {
            Thread t = new Thread(r);
            t.setPriority(1);
            //t.setDaemon(true);
            return t;
        });
        new Thread(() -> {
            while (true) {
                for (int i = 0; i < 100; ++i) {
                    executor.submit(() -> System.out.print("."));
                }
            }
        }).start();
        Thread.sleep(2_000);
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            List<Runnable> myList = executor.shutdownNow();
            System.out.println(myList.size());
        }
        System.out.println("shut down was called");
    }
}
