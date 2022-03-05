package com.elte.progtech.model.player;

import hu.elte.progtech.consts.Const;
import hu.elte.progtech.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlayerTest {

    Player player;

    @BeforeEach
    void init() {
        player = spy(new Player("Mock Player", Color.BLUE));
    }

    @Test
    @DisplayName("produceDeleriumTest() should be equal when we use 10 delerium")
    void removeDeleriumTest() {
        player.useDeuterium(10);
        assertEquals(player.getDeuterium().getQuantity(), 40);
    }

    @Test
    @DisplayName("produceDeleriumTest() should be equal when we produce 10 more delerium")
    void produceDeleriumTest() {
        player.produceDelerium(10);
        assertEquals(player.getDeuterium().getQuantity(), 60);
    }

    @Test
    @DisplayName("useActionPoint() should return false when we use more action point than we have")
    void useMoreActionPointThanCan() {
        assertFalse(player.useActionPoint(Const.INIT_ACTION_POINTS + 20));
    }

    @Test
    @DisplayName("useActionPoint() should return true when we use less action point than we have")
    void useLessActionPointThanHave() {
        assertTrue(player.useActionPoint(Const.INIT_ACTION_POINTS - 20));
    }

    @Test
    @DisplayName("nextRound() set actionPoints to the default value")
    void nextRoundActionPoint() {
        player.nextRound();
        assertEquals(player.getActionPoints(), Const.INIT_ACTION_POINTS);
    }

}
