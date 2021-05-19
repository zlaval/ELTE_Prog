package com.zlrx.java.advanced.classes.cls20210421;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PlayWithNumbers {

    private final Map<String, List<Integer>> nums = new HashMap<>();

    private final Random random = new Random(System.currentTimeMillis());

    private final List<Thread> threads = new ArrayList<>();

    private volatile boolean stop = false;

    public void start() {
        int round = 20;
        createPlayers();
        while (round > 0) {
            Map<String, List<Integer>> copy;
            synchronized (nums) {
                copy = new HashMap<>(nums);
                nums.clear();
            }
            if (!copy.isEmpty()) {
                Supplier<Stream<Integer>> allTipsStream = () -> copy.values().stream().flatMap(Collection::stream);
                allTipsStream.get()
                        .filter(i -> Collections.frequency(allTipsStream.get().collect(Collectors.toList()), i) == 1)
                        .mapToInt(i -> i)
                        .min().ifPresent(i -> {
                            var name = getKeyByValue(i, copy);
                            System.out.println("Min individual: " + i + " by " + name);
                            System.out.println("**************************************************");
                        }
                );
            }
            round--;
            sleep(1000);
        }
        stop = true;
    }

    private String getKeyByValue(int value, Map<String, List<Integer>> map) {
        return map.entrySet().stream()
                .filter(e -> e.getValue().contains(value))
                .map(Map.Entry::getKey)
                .findFirst().orElse("Noname");
    }

    private void createPlayers() {
        createPlayer("drizzt");
        createPlayer("superman");
        createPlayer("han");
        createPlayer("brujah");
    }

    private void createPlayer(String name) {
        var playerThread = new Thread(() -> {
            while (!stop) {
                var tip = random.nextInt(1000000);
                synchronized (nums) {
                    nums.putIfAbsent(name, new ArrayList<>());
                    nums.computeIfPresent(name, (s, integers) -> {
                        integers.add(tip);
                        return integers;
                    });
                }
                System.out.println(name + " makes a tip " + tip);
                sleep(50);
            }
        });
        playerThread.start();
        threads.add(playerThread);
    }


    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Ooooo");
        }
    }

}
