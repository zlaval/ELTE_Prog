package com.zlrx.elte.slideout.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameData {
    private final Long blackRock;
    private final Long whiteRock;
    private final Integer remainingStep;
    private final Player player;
}
