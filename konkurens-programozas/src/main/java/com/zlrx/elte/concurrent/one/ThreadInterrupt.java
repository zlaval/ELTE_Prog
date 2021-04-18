package com.zlrx.elte.concurrent.one;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(ThreadInterrupt::callInThread);
        thread.start();
        Thread.sleep(100);
        System.out.println("Now I'm going to interrupt the thread - main");
        thread.interrupt();
        System.out.println("I poked it - said the main");
    }


    private static void callInThread() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("I'm still running - child");
        }
        System.out.println("Someone was interrupted me - child");
    }

}
