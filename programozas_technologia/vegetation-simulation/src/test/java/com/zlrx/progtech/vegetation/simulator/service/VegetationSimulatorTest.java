package com.zlrx.progtech.vegetation.simulator.service;

import com.zlrx.progtech.vegetation.simulator.helper.ResultPrinter;
import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import com.zlrx.progtech.vegetation.simulator.model.SimulationData;
import com.zlrx.progtech.vegetation.simulator.model.plants.Deltafa;
import com.zlrx.progtech.vegetation.simulator.model.plants.Parabokor;
import com.zlrx.progtech.vegetation.simulator.model.plants.Plant;
import com.zlrx.progtech.vegetation.simulator.model.plants.Puffancs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VegetationSimulatorTest {


    @Mock
    private SimulationDataProvider simulationDataProvider;

    private final ResultPrinter resultPrinter = spy(new ResultPrinter());

    private final DayElapseSimulator dayElapseSimulator = spy(new DayElapseSimulator());

    @Captor
    private ArgumentCaptor<List<Plant>> plantsCaptor;

    private VegetationSimulator underTest;

    @BeforeEach
    public void init() {
        underTest = new VegetationSimulator(simulationDataProvider, dayElapseSimulator, resultPrinter);
    }

    @Test
    @DisplayName("should not call simulator and printer when no data provided")
    public void noDataProvidedTest() {
        when(simulationDataProvider.provide(anyString())).thenReturn(Optional.empty());

        underTest.runSimulation("test.txt");

        verifyNoInteractions(resultPrinter);
        verifyNoInteractions(dayElapseSimulator);
    }

    @Test
    @DisplayName("should run simulation when file provided")
    public void dataProvidedTest() {
        when(simulationDataProvider.provide(anyString())).thenReturn(
                Optional.of(SimulationData.builder()
                        .plant(new Puffancs("Puffancs", 10))
                        .plant(new Parabokor("Parabokor", 3))
                        .plant(new Deltafa("Deltafa1", 7))
                        .plant(new Deltafa("Deltafa2", 5))
                        .plant(new Deltafa("Deltafa3", 5))
                        .plant(new Deltafa("Deltafa4", 7))
                        .days(10)
                        .build())
        );

        underTest.runSimulation("test.txt");

        Mockito.verify(simulationDataProvider, times(1)).provide(anyString());
        Mockito.verify(dayElapseSimulator, times(10)).simulateDay(any(), any());
        verifyNoMoreInteractions(dayElapseSimulator);
        Mockito.verify(resultPrinter, times(10))
                .printResult(plantsCaptor.capture(), anyInt(), any(Radiation.class), any(Radiation.class));

        List<Plant> plantsOnLastDay = plantsCaptor.getValue();
        var expectedNutrientsData = List.of(3, 0, 8, 6, 6, 8);
        var actualNutrientsData = plantsOnLastDay.stream()
                .map(Plant::getNutrient)
                .collect(Collectors.toList());

        assertEquals(expectedNutrientsData, actualNutrientsData, "Last day's nutrients");
    }

}



