package com.zlrx.concurrent;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static final int MIN_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER = 50;
    public static final int MAX_CUSTOMER_SEND_DELAY_MS_PER_PRODUCER = 100;
    public static final int NUMBER_OF_PRODUCERS = 3;
    public static final int MIN_HAIRCUT_MS = 20;
    public static final int MAX_HAIRCUT_MS = 200;
    public static final int HOUR_LENGTH_MILLIS = 400;
    public static final int OPEN_HOUR = 9;
    public static final int CLOSE_HOUR = 17;
    public static final int DAY_LENGTH_MILLIS = 24 * HOUR_LENGTH_MILLIS;

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        Statistics statistics = new Statistics();
        BarberShop barberShop = new BarberShop(statistics, latch);
        ShopDirector shopDirector = new ShopDirector(barberShop);
        CustomerGenerator generator = new CustomerGenerator(barberShop);

        generator.startProducer();
        shopDirector.start();
        barberShop.start();

        ConcurrencyUtil.runInterruptibly(latch::await);
        System.out.println("Simulation has been ended. Shutting down the system and calculate the results...");

        generator.stopProducer();
        shopDirector.stop();
        barberShop.stop();
        statistics.printResult();

        System.out.println("Exit");
    }

}
