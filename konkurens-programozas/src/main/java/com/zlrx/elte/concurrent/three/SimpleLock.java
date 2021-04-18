package com.zlrx.elte.concurrent.three;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class SimpleLock {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();
        for (int i = 0; i < 2; ++i) {
            new Thread(() -> {
                while (true) {
                    account.doInLock(() -> {
                        account.setBalance(account.getBalance() + 10);
                        System.out.println(Thread.currentThread().getName() + "\tset balance to: " + account.getBalance());
                    });
                }
            }).start();
        }
    }

    private static class Account {
        private final Lock lock = new ReentrantLock(true);
        private int balance;
        private boolean canGetLoan;

        public synchronized int getBalance() {
            return getInLock(() -> balance);
        }

        public void setBalance(int balance) {
            doInLock(() -> this.balance = balance);
        }

        public boolean isCanGetLoan() {
            return getInLock(() -> {
                if (canGetLoan && balance > 1000) {
                    return true;
                } else if (canGetLoan && balance <= 1000) {
                    return false;
                } else {
                    return canGetLoan;
                }
            });
        }

        public void setCanGetLoan(boolean canGetLoan) {
            doInLock(() -> this.canGetLoan = canGetLoan);
        }

        public void doInLock(Runnable task) {
            lock.lock();
            try {
                task.run();
            } finally {
                lock.unlock();
            }
        }

        private <T> T getInLock(Supplier<T> getter) {
            lock.lock();
            try {
                return getter.get();
            } finally {
                lock.unlock();
            }
        }
    }
}
