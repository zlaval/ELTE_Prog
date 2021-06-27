package com.zlrx.elte.slideout.model;

import com.zlrx.elte.slideout.resource.ImageResource;

import java.awt.event.KeyEvent;

public enum Arrows {
    UP(ImageResource.ARROW_UP, KeyEvent.VK_UP),
    DOWN(ImageResource.ARROW_DOWN, KeyEvent.VK_DOWN),
    LEFT(ImageResource.ARROW_LEFT, KeyEvent.VK_LEFT),
    RIGHT(ImageResource.ARROW_RIGHT, KeyEvent.VK_RIGHT);

    private final ImageResource imageKey;
    private final int keyEvent;

    Arrows(ImageResource imageKey, int keyEvent) {
        this.imageKey = imageKey;
        this.keyEvent = keyEvent;
    }

    public ImageResource getImageKey() {
        return imageKey;
    }

    public int keyEvent() {
        return keyEvent;
    }

}
