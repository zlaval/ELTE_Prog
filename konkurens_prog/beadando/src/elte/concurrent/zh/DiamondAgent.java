package elte.concurrent.zh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiamondAgent {

    private final Thread thread;
    private final List<Jewellery> jewelleries;
    private final String name;

    public DiamondAgent(List<Jewellery> jewelleries, String name) {
        this.thread = new Thread(this::mining);
        this.jewelleries = jewelleries;
        this.name = name;
    }

    public void start() {
        thread.start();
    }

    public void mining() {
        while (true) {
            Utils.sleepRandomly(100, 500);
            List<RoughDiamond> diamonds = new ArrayList<>();
            var rnd = ThreadLocalRandom.current().nextInt(98) + 2;
            for (int i = 0; i < rnd; i++) {
                var diamond = new RoughDiamond();
                diamonds.add(diamond);
            }
            var indices = IntStream.range(0, jewelleries.size())
                    .boxed()
                    .collect(Collectors.toList());
            Collections.shuffle(indices);
            for (Integer index : indices) {
                var jewellery = jewelleries.get(index);
                var result = jewellery.sellDiamond(diamonds);
                System.out.println("Diamond " + name + " agent try to sell a diamond to " + jewellery + " with result: " + result);
                if (result) {
                    break;
                }
            }
        }
    }


}
