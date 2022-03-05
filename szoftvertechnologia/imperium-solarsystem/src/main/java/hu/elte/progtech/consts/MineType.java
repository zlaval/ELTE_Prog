package hu.elte.progtech.consts;

import lombok.Getter;

import java.util.List;

@Getter
public enum MineType {
    IRONMINE(List.of(5, 10, 20, 35, 50), List.of(1, 2, 3, 4, 5), List.of(10, 25, 40, 60, 100), "Iron mine", 2),
    SONARPANEL(List.of(2, 4, 7, 11, 16), List.of(0, 0, 0, 0, 0), List.of(15, 20, 30, 50, 80), "Sonar panel", 2),
    DEUTERIUMMINE(List.of(5, 10, 20, 35, 50), List.of(1, 2, 3, 4, 5), List.of(10, 20, 35, 50, 70), "Deuterium field", 3);

    private final List<Integer> productionRateLevels;
    private final List<Integer> energyConsumptionLevels;
    private final List<Integer> updatePriceLevels;
    private final String name;
    private final int constructionTime;

    MineType(List<Integer> productionRateLevels, List<Integer> energyConsumptionLevels, List<Integer> updatePriceLevels, String name, int constructionTime) {
        this.productionRateLevels = productionRateLevels;
        this.energyConsumptionLevels = energyConsumptionLevels;
        this.updatePriceLevels = updatePriceLevels;
        this.name = name;
        this.constructionTime = constructionTime;
    }

    public String getName() {
        return name;
    }
}
