package com.zlrx.concurrent;

import com.zlrx.concurrent.model.Customer;
import com.zlrx.concurrent.model.CustomerStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zlrx.concurrent.Main.MAX_HAIRCUT_MS;
import static com.zlrx.concurrent.Main.MIN_HAIRCUT_MS;
import static com.zlrx.concurrent.model.CustomerStatus.CLOSED_SHOP;
import static com.zlrx.concurrent.model.CustomerStatus.NO_SEAT;
import static com.zlrx.concurrent.model.CustomerStatus.SUCCESS;

public class BarberShop {

    private final Statistics statistics;
    private final AtomicInteger dayCount = new AtomicInteger(0);
    private final AtomicBoolean open = new AtomicBoolean(false);
    private final AtomicBoolean isWeekDay = new AtomicBoolean(true);
    private final AtomicInteger customerCounter = new AtomicInteger();
    private final Semaphore seats = new Semaphore(5);
    private final CountDownLatch latch;
    private final Thread hairDresser;
    private final Thread barber;
    private final List<Customer> waitingCustomers = Collections.synchronizedList(new ArrayList<>());

    public BarberShop(Statistics statistics, CountDownLatch latch) {
        this.statistics = statistics;
        this.latch = latch;
        hairDresser = new Thread(() -> doWork(false));
        barber = new Thread(() -> doWork(true));
    }

    public void start() {
        hairDresser.start();
        barber.start();
    }

    public void stop() {
        isWeekDay.set(false);
        hairDresser.interrupt();
        barber.interrupt();
        ConcurrencyUtil.runInterruptibly(hairDresser::join);
        ConcurrencyUtil.runInterruptibly(barber::join);
    }

    private void doWork(boolean cutBeard) {
        while (isWeekDay.get()) {
            Optional<Customer> maybeCustomer = getNext(cutBeard);
            maybeCustomer.ifPresent(customer -> {
                customer.setWaitTime(System.currentTimeMillis() - customer.getWaitingStartMillis());
                System.out.println("Start: " + customer + " cut.");
                cut();
                if (customer.isShaveBeard()) {
                    cut();
                }
                addToStat(customer, SUCCESS);
                System.out.println(customerCounter.incrementAndGet() + ". finish: " + customer + " cut in thread " + Thread.currentThread().getName());
            });
        }
    }

    private void cut() {
        var millis = ThreadLocalRandom.current().nextInt(MAX_HAIRCUT_MS - MIN_HAIRCUT_MS) + MIN_HAIRCUT_MS;
        ConcurrencyUtil.runInterrupt
    ibly(() -> Thread.sleep(millis));
    }

    public void customerArrive(Customer customer) {
        if (!isWeekDay.get()) {
            return;
        }
        if (open.get()) {
            try {
                var result = seats.tryAcquire(0, TimeUnit.MILLISECONDS);
                if (result) {
                    customer.setDay(dayCount.get());
                    customer.setWaitingStartMillis(System.currentTimeMillis());
                    synchronized (waitingCustomers) {
                        waitingCustomers.add(customer);
                        waitingCustomers.notifyAll();
                    }
                } else {
                    addToStat(customer, NO_SEAT);
                }
            } catch (InterruptedException e) {
                addToStat(customer, NO_SEAT);
            }
        } else {
            addToStat(customer, CLOSED_SHOP);
        }
    }

    private void addToStat(Customer customer, CustomerStatus status) {
        customer.setStatus(status);
        statistics.addStat(customer);
    }

    public void dayElapsed() {
        if (dayCount.incrementAndGet() == 6) {
            latch.countDown();
        } else {
            System.out.println(dayCount.get() + " day started.");
        }
    }

    public void open() {
        System.out.println("Open the shop.");
        open.set(true);
    }

    public void close() {
        open.set(false);
        System.out.println("Shop was closed.");
    }

    private Optional<Customer> getNext(boolean withShave) {
        synchronized (waitingCustomers) {
            if (waitingCustomers.isEmpty()) {
                try {
                    waitingCustomers.wait();
                } catch (InterruptedException e) {
                    return Optional.empty();
                }
            }
            Optional<Customer> customer = Optional.empty();
            if (!waitingCustomers.isEmpty()) {
                if (!withShave) {
                    customer = waitingCustomers.stream().filter(c -> !c.isShaveBeard()).findFirst();
                } else {
                    customer = Optional.of(waitingCustomers.get(0));
                }
            }
            customer.ifPresent(c -> {
                try {
                    waitingCustomers.remove(c);
                } finally {
                    seats.release();
                }
            });
            return customer;
        }
    }
}
