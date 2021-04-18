package com.zlrx.elte.concurrent.three;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class ComplexLock {

    public static void main(String[] args) {
        Account account = new Account();
        for (int i = 0; i < 2; ++i) {
            new Thread(() -> {
                while (true) {
                    account.writeInLock(() -> {
                        account.setBalance(account.getBalance() + 10);
                        System.out.println(Thread.currentThread().getName() + "\tset balance to: " + account.getBalance());
                    });
                    /*
                    account.readInLock(()->{
                        for (int j=0; j<100; ++j) {
                            System.out.println(Thread.currentThread().getName() + "\treads balance: " + account.getBalance()+"\t"+j);
                        }
                        return 0;
                    });
                     */
                }
            }).start();
        }
    }

    private static class Account {
        private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
        private int balance;
        private boolean canGetLoan;

        public synchronized int getBalance() {
            return readInLock(() -> balance);
        }

        public void setBalance(int balance) {
            writeInLock(() -> this.balance = balance);
        }

        public boolean isCanGetLoan() {
            return readInLock(() -> {
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
            writeInLock(() -> this.canGetLoan = canGetLoan);
        }

        public void writeInLock(Runnable task) {
            lock.writeLock().lock();
            try {
                task.run();
            } finally {
                lock.writeLock().unlock();
            }
        }

        public <T> T readInLock(Supplier<T> getter) {
            lock.readLock().lock();
            try {
                return getter.get();
            } finally {
                lock.readLock().unlock();
            }
        }

        public void downgrade() {
            boolean isWriteLocked = false;
            lock.writeLock().lock();
            try {
                isWriteLocked = true;
                // code to modify object
                lock.readLock().lock();
                try {
                    lock.writeLock().unlock();
                    isWriteLocked = false;
                    // Codes only to read
                } finally {
                    lock.readLock().unlock();
                }
            } finally {
                if (isWriteLocked) {
                    lock.writeLock().unlock();
                }
            }
        }

        public void upgrade() {
            boolean isReadLocked = false;
            lock.readLock().lock();
            try {
                isReadLocked = true;
                // codes only to read
                if (verifyWriteLockIsNeeded()) {
                    lock.readLock().unlock();
                    isReadLocked = false;
                    lock.writeLock().lock();
                    try {
                        if (verifyWriteLockIsNeeded()) {
                            // codes need write lock
                        }
                    } finally {
                        lock.writeLock().unlock();
                    }
                }
            } finally {
                if (isReadLocked) {
                    lock.readLock().unlock();
                }
            }
        }

        private boolean verifyWriteLockIsNeeded() {
            return new Random().nextBoolean();
        }
    }

}
