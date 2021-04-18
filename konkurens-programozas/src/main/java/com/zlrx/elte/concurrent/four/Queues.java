package com.zlrx.elte.concurrent.four;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Queues {

    public static void main(String[] args) {
        Random r = new Random();
        BlockingQueue<Integer> ints = new SynchronousQueue<>();
        //new ArrayBlockingQueue<>(10);
        //new LinkedBlockingQueue<>();
        for (int i = 0; i < 20; ++i) {
            new Thread(() -> {
                while (true) {
                    dontInterrupt(() -> ints.offer(r.nextInt(100), 2, TimeUnit.SECONDS));
                    System.out.println("put new size: " + ints.size());
                    sleep(3000);
                }
            }, "filler-thread").start();
        }

        new Thread(() -> {
            while (true) {
                int[] value = new int[]{0};
                dontInterrupt(() -> {
                    System.out.println("peek: " + ints.peek());
                    value[0] = ints.poll(1L, TimeUnit.SECONDS);
                });
                System.out.println("taken: " + value[0] + "\tcurrent size: " + ints.size());
                sleep(10);
            }
        }, "coonsumer-thread").start();
    }

    private static void sleep(long millis) {
        dontInterrupt(() -> Thread.sleep(millis));
    }

    private static void dontInterrupt(Erroneous e) {
        try {
            e.doIt();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static interface Erroneous {
        void doIt() throws Exception;
    }
}
