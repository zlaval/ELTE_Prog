package com.zlrx.progtech.vegetation.simulator.helper;

import com.zlrx.progtech.vegetation.simulator.model.Radiation;
import com.zlrx.progtech.vegetation.simulator.model.plants.Plant;

import java.util.List;

public class ResultPrinter {

    /**
     * Prints the actual state of the plants to the standard outpot
     *
     * @param plants
     * @param day
     * @param today
     * @param tomorrow
     */
    public void printResult(List<Plant> plants, int day, Radiation today, Radiation tomorrow) {
        System.out.println();
        System.out.println("********************** DAY " + (day + 1) + " **********************");
        System.out.println("-----------------------------------------------------------");
        plants.forEach(p -> {
            System.out.println(p);
            System.out.println("-----------------------------------------------------------");
        });
        System.out.println("Actual radiation: " + today);
        System.out.println("Radiation tomorrow: " + tomorrow);
        System.out.println();
    }

}
