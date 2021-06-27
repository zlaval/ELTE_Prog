package com.zlrx.progtech.vegetation.simulator;

import com.zlrx.progtech.vegetation.simulator.helper.PlantFactory;
import com.zlrx.progtech.vegetation.simulator.helper.ResultPrinter;
import com.zlrx.progtech.vegetation.simulator.service.DayElapseSimulator;
import com.zlrx.progtech.vegetation.simulator.service.TextFileParser;
import com.zlrx.progtech.vegetation.simulator.service.VegetationSimulator;

import java.util.Scanner;

public class Main {

    public static class ScannerWrapper {
        private final Scanner scanner = new Scanner(System.in);

        public String next() {
            return scanner.next();
        }
    }

    private final ScannerWrapper scanner;

    private final VegetationSimulator vegetationSimulator;

    public static void main(String[] args) {
        new Main(new ScannerWrapper()).start();
    }

    public Main(ScannerWrapper scanner) {
        this.scanner = scanner;
        var plantFactory = new PlantFactory();
        var printer = new ResultPrinter();
        var provider = new TextFileParser(plantFactory);
        var dayElapseSimulator = new DayElapseSimulator();
        vegetationSimulator = new VegetationSimulator(provider, dayElapseSimulator, printer);
    }

    /**
     * Starts the simulation.
     * Read file path and run simulation until
     * user type 'exit'.
     */
    public final void start() {
        var end = false;
        System.out.println("Type 'exit' to leave!");
        System.out.println();
        while (!end) {
            System.out.println("Please type in the resource path (or exit):");
            String path = scanner.next();
            if ("exit".equals(path)) {
                end = true;
            } else {
                vegetationSimulator.runSimulation(path);
            }
        }
        System.out.println("Bye");
    }

}
