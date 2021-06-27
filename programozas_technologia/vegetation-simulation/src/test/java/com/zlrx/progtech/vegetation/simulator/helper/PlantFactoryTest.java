package com.zlrx.progtech.vegetation.simulator.helper;

import com.zlrx.progtech.vegetation.simulator.model.plants.Deltafa;
import com.zlrx.progtech.vegetation.simulator.model.plants.Parabokor;
import com.zlrx.progtech.vegetation.simulator.model.plants.Puffancs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlantFactoryTest {

    private PlantFactory underTest = new PlantFactory();

    @Test
    @DisplayName("When type is 'a' factory should return a Puffancs instance")
    void testPuffancsFromFactory() {
        var type = "a";

        var maybePlant = underTest.getPlant("Test", type, 1);

        assertTrue(maybePlant.isPresent());
        maybePlant.ifPresent(plant ->
                assertTrue(plant instanceof Puffancs)
        );
    }

    @Test
    @DisplayName("When type is 'd' factory should return a Deltafa instance")
    void testDeltafaFromFactory() {
        var type = "d";

        var maybePlant = underTest.getPlant("Test", type, 1);

        assertTrue(maybePlant.isPresent());
        maybePlant.ifPresent(plant ->
                assertTrue(plant instanceof Deltafa)
        );
    }

    @Test
    @DisplayName("When type is 'p' factory should return a Parabokor instance")
    void testParabokorFactory() {
        var type = "p";

        var maybePlant = underTest.getPlant("Test", type, 1);

        assertTrue(maybePlant.isPresent());
        maybePlant.ifPresent(plant ->
                assertTrue(plant instanceof Parabokor)
        );
    }

    @Test
    @DisplayName("Factory should return empty() when type not in 'a,d,p'")
    void testFactoryException() {
        var type = "x";

        var maybePlant = underTest.getPlant("Test", type, 1);

        assertFalse(maybePlant.isPresent());
    }

}
