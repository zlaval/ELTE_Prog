package com.zlrx.java.advanced.classes.cls20210421;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadingTest {

    private final Random random = new Random(System.nanoTime());

    @Test
    public void playWithNumbers() {
        var playWithNumbers = new PlayWithNumbers();
        playWithNumbers.start();
    }

    @Test
    public void massTasks() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        var tasks = new ArrayList<Future<Integer>>();

        for (int i = 0; i < 100; i++) {
            var number = i;
            var task = executorService.submit(
                    () -> {
                        sleep(random.nextInt(450) + 50);
                        return number;
                    }
            );
            tasks.add(task);
        }

        while (!tasks.isEmpty()) {
            var iterator = tasks.iterator();
            while (iterator.hasNext()) {
                var future = iterator.next();
                if (future.isDone()) {
                    var res = future.get();
                    System.out.println(res + ". task has finished");
                    iterator.remove();
                }
            }
        }
        executorService.shutdownNow();
    }

    @Test
    public void testThreadPool() {
        ThreadPool tp = new ThreadPool(5);
        for (int i = 0; i < 50; ++i) {
            var index = i;
            Runnable runnable = () -> {
                System.out.println("Task " + index + " started");
                sleep(random.nextInt(1000));
                System.out.println("Task " + index + " finished");
            };
            tp.execute(runnable);
        }
        tp.waitFinish();
        tp.terminate();
    }

    @Test
    public void testApplyAssocFn() throws ExecutionException, InterruptedException {
        ApplyAssocFn a = new ApplyAssocFn();
        a.calculate();
    }

    @Test
    public void testApplyAssocRecTask() throws ExecutionException, InterruptedException {
        ApplyAssocRecTask a = new ApplyAssocRecTask();
        a.compute();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Hmmm");
        }
    }


}
