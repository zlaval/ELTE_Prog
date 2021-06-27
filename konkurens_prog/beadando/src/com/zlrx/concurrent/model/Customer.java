package com.zlrx.concurrent.model;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Customer {

    private final boolean shaveBeard = ThreadLocalRandom.current().nextBoolean();
    private final String id = UUID.randomUUID().toString();

    private volatile CustomerStatus status = CustomerStatus.NOT_ARRIVED;
    private volatile long waitingStartMillis;
    private volatile long waitTime;
    private volatile int day;

    public boolean isShaveBeard() {
        return shaveBeard;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public int getDay() {
        return day;
    }

    public long getWaitingStartMillis() {
        return waitingStartMillis;
    }

    public void setStatus(CustomerStatus customerStatus) {
        this.status = customerStatus;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setWaitingStartMillis(long waitingStartMillis) {
        this.waitingStartMillis = waitingStartMillis;
    }

    @Override
    public String toString() {
        return id + " " + (shaveBeard ? " beard and hair " : " hair ");
    }
}
