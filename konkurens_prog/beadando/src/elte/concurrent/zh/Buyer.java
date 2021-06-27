package elte.concurrent.zh;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Container {
    private final Jewellery jewellery;
    private final PolishedDiamond diamond;

    public Container(Jewellery jewellery, PolishedDiamond diamond) {
        this.jewellery = jewellery;
        this.diamond = diamond;
    }

    public Jewellery getJewellery() {
        return jewellery;
    }

    public PolishedDiamond getDiamond() {
        return diamond;
    }
}

public class Buyer {

    private final List<Jewellery> jewelleries;
    private final int averageAmount;

    public Buyer(List<Jewellery> jewelleries) {
        this.jewelleries = jewelleries;
        int minAmount = ThreadLocalRandom.current().nextInt(99_000) + 1000;
        int maxAmount = ThreadLocalRandom.current().nextInt(100_000 - minAmount) + minAmount;
        averageAmount = (minAmount + maxAmount) / 2;
    }

    public void tryBuy() {
        boolean success = false;
        Comparator<Container> comp = (a, b) -> Math.abs(averageAmount - a.getDiamond().getValue()) + Math.abs(averageAmount - b.getDiamond().getValue());
        while (!success) {
            Utils.sleepRandomly(1000, 2000);
            var bestOffer = jewelleries.stream()
                    .map(j -> new Container(j, j.getOffer()))
                    .filter(c -> c.getDiamond() != null)
                    .min(comp)
                    .orElse(null);
            if (bestOffer != null) {
                System.out.println("Try to buy from  " + bestOffer.getJewellery() + " the diamond " + bestOffer.getDiamond() + " " + this);
                success = bestOffer.getJewellery().sellDiamond(bestOffer.getDiamond());
                if (success) {
                    System.out.println("Successfully buy from  " + bestOffer.getJewellery() + " the diamond " + bestOffer.getDiamond() + " " + this);
                } else {
                    System.out.println("Buy not success from  " + bestOffer.getJewellery() + " the diamond " + bestOffer.getDiamond() + " " + this);
                }

            } else {
                System.out.println("No good offer " + this);
            }

        }
    }

}
