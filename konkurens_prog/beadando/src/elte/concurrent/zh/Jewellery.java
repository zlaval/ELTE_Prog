package elte.concurrent.zh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Jewellery {

    private int money;
    private final ExecutorService workers;
    private final List<DiamondCutter> diamondCutters;
    private final String name;
    private final List<PolishedDiamond> diamonds;
    private final List<RoughDiamond> roughDiamonds;
    private final Thread thread;
    private final ReentrantLock lock = new ReentrantLock();

    public Jewellery(int money, int numberOfWorkers, String name) {
        this.money = money;
        this.name = name;
        workers = Executors.newFixedThreadPool(numberOfWorkers);
        diamondCutters = Collections.synchronizedList(new ArrayList<>());
        diamonds = Collections.synchronizedList(new ArrayList<>());
        roughDiamonds = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < numberOfWorkers; i++) {
            diamondCutters.add(new DiamondCutter(this));
        }
        thread = new Thread(this::polish);
    }

    public void start() {
        thread.start();
    }

    public void polish() {
        while (true) {
            //TODO reentrantlock roughdiamonds+workers all places on this lists
            RoughDiamond d = null;
            synchronized (roughDiamonds) {
                if (!roughDiamonds.isEmpty()) {
                    d = roughDiamonds.remove(0);
                }
            }
            if (d != null) {
                var result = polishDiamond(d);
                if (!result) {
                    roughDiamonds.add(d);
                }
            }
        }
    }

    private boolean polishDiamond(RoughDiamond roughDiamond) {
        DiamondCutter dc = getFree();
        if (dc != null) {
            System.out.println("Polish the diamond " + roughDiamond + " by " + dc + " in " + this);

            workers.submit(() -> {
                try {
                    dc.polishDiamond(roughDiamond);
                } finally {
                    diamondCutters.add(dc);
                }
            });

            return true;
        }
        return false;

    }

    public synchronized boolean buyDiamond(List<RoughDiamond> diamonds) {
        var amount = diamonds.stream().map(RoughDiamond::getValue).mapToInt(i -> i).sum();
        if (money - amount > 0) {
            money = money - amount;
            roughDiamonds.addAll(diamonds);
            System.out.println("Has enough money. New amount: " + money + " jw: " + this);
            return true;
        }
        return false;
    }

    public boolean sellDiamond(PolishedDiamond diamond) {
        var result = diamonds.remove(diamond);
        if (result) {
            synchronized (this) {
                money += diamond.getValue();
            }
            System.out.println("New amount: " + money + " jw: " + this);
            return true;
        }
        return false;
    }

    public PolishedDiamond getOffer() {
        synchronized (diamonds) {
            if (!diamonds.isEmpty()) {
                return diamonds.get(ThreadLocalRandom.current().nextInt(diamonds.size()));
            }
        }
        return null;
    }

    public void storeDiamond(PolishedDiamond polishedDiamond) {
        diamonds.add(polishedDiamond);
    }

    private DiamondCutter getFree() {
        DiamondCutter dc = null;
        synchronized (diamondCutters) {
            if (!diamondCutters.isEmpty()) {
                dc = diamondCutters.remove(0);
            }
        }
        return dc;
    }


    public boolean sellDiamond(List<RoughDiamond> roughDiamonds) {
        System.out.println("Seller try to sell a diamond to " + this);
        DiamondCutter dc = getFree();
        if (dc != null) {
            try {
                var result = workers.submit(() -> dc.inspectDiamond(roughDiamonds));
                return result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();

            } finally {
                diamondCutters.add(dc);
            }
        } else {
            System.out.println("Cant sell because the all cutters are busy " + this);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
