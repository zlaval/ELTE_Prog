package com.zlrx.concurrent.model;

public enum CustomerStatus {
    NOT_ARRIVED(" customers did not arrive"),
    CLOSED_SHOP(" customers arrived when the shop was closed"),
    NO_SEAT(" customers cannot take a seat"),
    SUCCESS(" customers successfully had their hair cut");

    private String title;

    CustomerStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
