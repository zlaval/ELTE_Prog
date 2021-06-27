package com.zlrx.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public final class ConcurrencyUtil {

    @FunctionalInterface
    public interface InterruptibleRunnable {
        void run() throws InterruptedException;
    }

    private ConcurrencyUtil() {
        throw new UnsupportedOperationException("Util class");
    }

    public static void runInterruptibly(InterruptibleRunnable runnable) {
        try {
            runnable.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void shutDownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        var shutdownResult = false;
        try {
            shutdownResult = executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!shutdownResult && !executorService.isTerminated()) {
                executorService.shutdownNow();
                System.out.println("Force shutdown ExecutorService.");
            }
        }
        System.out.println("ExecutorService is down.");
    }

}
