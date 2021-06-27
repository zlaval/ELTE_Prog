package com.zlrx.concurrent;

import com.zlrx.concurrent.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.zlrx.concurrent.Main.MAX_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER;
import static com.zlrx.concurrent.Main.MIN_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER;
import static com.zlrx.concurrent.Main.NUMBER_OF_PRODUCERS;

public class CustomerGenerator {

    private volatile boolean running = true;

    private final BarberShop barberShop;

    private final List<Thread> producers;

    public CustomerGenerator(BarberShop barberShop) {
        this.barberShop = barberShop;
        producers = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PRODUCERS; ++i) {
            producers.add(new Thread(this::produce));
        }
    }

    public void startProducer() {
        producers.forEach(Thread::start);
    }

    public void stopProducer() {
        running = false;
        producers.forEach(t -> ConcurrencyUtil.runInterruptibly(t::join));
        System.out.println("No other customers will go to the barber shop.");
    }

    private void produce() {
        while (running) {
            sleep(ThreadLocalRandom.current().nextInt(MAX_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER - MIN_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER) + MIN_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER);
            barberShop.customerArrive(new Customer());
        }
    }

    private void sleep(int millis) {
        ConcurrencyUtil.runInterruptibly(() -> Thread.sleep(millis));
    }

}
