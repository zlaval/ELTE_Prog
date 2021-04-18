package com.zlrx.elte.concurrent.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Callables {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService e = Executors.newFixedThreadPool(100);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            int j = i;
            Future<Integer> future = e.submit(() -> fib(j));
            futures.add(future);
            System.out.println("submitted: " + j);
        }
        for (int i = 0; i < futures.size(); ++i) {
            Future<Integer> future = futures.get(i);
            try {
                System.out.println(future.get(1, TimeUnit.SECONDS));
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            } catch (TimeoutException te) {
                te.printStackTrace();
                future.cancel(true);
            }
        }
    }

    static int fib(int n) {
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("I was interrupted");
            throw new IllegalStateException("I was interrupted");
        }
        if (n <= 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
