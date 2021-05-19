package com.zlrx.java.advanced.classes.cls20210421;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


class Worker implements Runnable {

    private volatile boolean stopped = false;
    private volatile Runnable runnable;

    @Override
    public void run() {
        while (!stopped) {
            if (runnable != null) {
                runnable.run();
                runnable = null;
            }
        }
    }

    public void submit(Runnable runnable) {
        this.runnable = runnable;
    }

    public boolean isBusy() {
        return runnable != null;
    }

    public void stop() {
        stopped = true;
    }
}

public class ThreadPool {

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Worker> workers = new ArrayList<>();
    private final Thread eventLoop;

    public ThreadPool(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            var worker = new Worker();
            workers.add(worker);
            new Thread(worker).start();
        }
        eventLoop = createEventLoop();
        eventLoop.start();
    }

    private Thread createEventLoop() {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                workers.forEach(w -> {
                    if (!w.isBusy()) {
                        try {
                            var task = tasks.take();
                            w.submit(task);
                        } catch (InterruptedException e) {
                            System.out.println("I have been terminated");
                        }
                    }
                });
            }
        });

    }

    public void execute(Runnable runnable) {
        tasks.offer(runnable);
    }

    public void waitFinish() {
        while (!tasks.isEmpty()) ;
    }

    public void terminate() {
        System.out.println("Terminate...");
        eventLoop.interrupt();
        workers.forEach(Worker::stop);
    }

}
