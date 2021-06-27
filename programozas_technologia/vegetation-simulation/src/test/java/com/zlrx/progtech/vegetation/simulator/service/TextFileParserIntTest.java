package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.helper.PlantFactory;
import com.zlrx.progtech.vegetation.simulator.model.SimulationData;
import com.zlrx.progtech.vegetation.simulator.model.plants.Deltafa;
import com.zlrx.progtech.vegetation.simulator.model.plants.Parabokor;
import com.zlrx.progtech.vegetation.simulator.model.plants.Puffancs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextFileParserIntTest {

    private SimulationDataProvider underTest = new TextFileParser(new PlantFactory());

    @Test
    @DisplayName("should return empty() when file not found on path")
    public void testInvalidFileRead() {
        var result = underTest.provide("nofile.txt");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should return the the content of the file when path and content correct")
    public void testFileRead() {
        var path = getClass().getClassLoader().getResource("test.txt").getFile();

        var result = underTest.provide(path);

        assertTrue(result.isPresent());
        var expected = SimulationData.builder()
                .days(5)
                .plant(new Puffancs("PU", 5))
                .plant(new Deltafa("DE", 3))
                .plant(new Parabokor("PA", 4))
                .build();
        result.ifPresent(data ->
                assertEquals(expected, data)
        );
    }


}
