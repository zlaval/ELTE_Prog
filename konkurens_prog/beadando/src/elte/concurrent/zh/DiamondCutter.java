package elte.concurrent.zh;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiamondCutter {

    private final Jewellery jewellery;

    public DiamondCutter(Jewellery jewellery) {
        this.jewellery = jewellery;
    }

    public boolean inspectDiamond(List<RoughDiamond> roughDiamonds) {
        Utils.sleepRandomly(100, 200);
        for (RoughDiamond roughDiamond : roughDiamonds) {
            var rnd = ThreadLocalRandom.current().nextInt(100) + 1;
            if (rnd > 90) {
                System.out.println("Cant buy diamond, it's broken." + jewellery);
                return false;
            }
        }

        boolean canBuy = jewellery.buyDiamond(roughDiamonds);
        if (canBuy) {
            System.out.println("Can buy diamond, has enough money." + jewellery);
            return true;
        }
        System.out.println("Cant buy diamond, not enough money." + jewellery);
        return false;
    }

    public void polishDiamond(RoughDiamond roughDiamond) {
        System.out.println("Polish the diamond." + jewellery);
        Utils.sleep(roughDiamond.getValue() / 10);
        int value = (int) (roughDiamond.getValue() * 1.2);
        jewellery.storeDiamond(new PolishedDiamond(value));
    }

}
