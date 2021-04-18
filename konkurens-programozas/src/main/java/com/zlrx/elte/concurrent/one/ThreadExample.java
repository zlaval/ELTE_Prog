package com.zlrx.elte.concurrent.one;

import java.time.Duration;
import java.time.Instant;

public class ThreadExample {
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(ThreadExample::callInThread);
        thread.start();
        System.out.println("Thread start called in main");
        thread.join();
        System.out.println("End");
    }


    private static void callInThread() {
        try {
            System.out.println("Hello I'm thread: " + Thread.currentThread().getName());
            Instant start = Instant.now();
            Thread.sleep(2_000);
            Instant end = Instant.now();
            System.out.println("I slept a little: " + Duration.between(start, end).toMillis());
        } catch (Exception e) {
            System.out.println("I caught this pokemon.");
        }

    }

}
