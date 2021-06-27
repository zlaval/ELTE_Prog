package com.zlrx.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.zlrx.concurrent.Main.CLOSE_HOUR;
import static com.zlrx.concurrent.Main.DAY_LENGTH_MILLIS;
import static com.zlrx.concurrent.Main.HOUR_LENGTH_MILLIS;
import static com.zlrx.concurrent.Main.OPEN_HOUR;

public class ShopDirector {

    private final BarberShop barberShop;
    private final ScheduledExecutorService dayElapseScheduler = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService shopOpenScheduler = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService shopCloseScheduler = Executors.newScheduledThreadPool(1);

    public ShopDirector(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    public void start() {
        shopOpenScheduler.scheduleAtFixedRate(barberShop::open, OPEN_HOUR * HOUR_LENGTH_MILLIS, DAY_LENGTH_MILLIS, TimeUnit.MILLISECONDS);
        shopCloseScheduler.scheduleAtFixedRate(barberShop::close, CLOSE_HOUR * HOUR_LENGTH_MILLIS, DAY_LENGTH_MILLIS, TimeUnit.MILLISECONDS);
        dayElapseScheduler.scheduleAtFixedRate(barberShop::dayElapsed, 0, DAY_LENGTH_MILLIS, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        ConcurrencyUtil.shutDownExecutorService(dayElapseScheduler);
        ConcurrencyUtil.shutDownExecutorService(shopOpenScheduler);
        ConcurrencyUtil.shutDownExecutorService(shopCloseScheduler);
    }

}
