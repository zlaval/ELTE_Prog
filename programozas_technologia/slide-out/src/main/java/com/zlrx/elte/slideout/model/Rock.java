package com.zlrx.elte.slideout.model;

import com.zlrx.elte.slideout.resource.ImageResource;

public enum Rock {

    BLACK(ImageResource.ROCK_BLACK, Player.BLACK),

    WHITE(ImageResource.ROCK_WHITE, Player.WHITE);

    private final ImageResource imageKey;
    private final Player player;

    Rock(ImageResource imageKey, Player player) {
        this.imageKey = imageKey;
        this.player = player;
    }

    public ImageResource getImageKey() {
        return imageKey;
    }

    public Player getPlayer() {
        return player;
    }
}
