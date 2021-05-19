package com.zlrx.java.advanced.classes.cls20210421;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class Entity<T> {

    private final List<T> genes;
    private final Settings<T> settings;
    private int fitness;

    public Entity(Settings<T> settings) {
        this.settings = settings;
        genes = new ArrayList<>();
        for (int i = 0; i < settings.getSequenceLength(); ++i) {
            genes.add(settings.getRandomGeneGenerator().get());
        }
    }

    public T getGene(int index) {
        return genes.get(index);
    }

    public void setGene(int index, T value) {
        genes.set(index, value);
        fitness = 0;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = settings.getFitnessCalculator().apply(genes);
        }
        return fitness;
    }

    public int size() {
        return genes.size();
    }

    @Override
    public String toString() {
        return genes.stream()
                .map(i -> {
                    if (i == null) {
                        return "[]";
                    } else {
                        return i.toString();
                    }
                })
                .collect(Collectors.joining("-", "[", "]"));
    }

}

class Settings<T> {
    private final int sequenceLength;
    private final Supplier<T> randomGeneGenerator;
    private final Function<List<T>, Integer> fitnessCalculator;
    private final double mutationProbability;
    private final double crossOverRate;
    private final int crossoverCount;
    private final int pruneCount;

    public Settings(int sequenceLength, Supplier<T> randomGeneGenerator, Function<List<T>,
            Integer> fitnessCalculator, double mutationProbability,
                    double crossOverRate, int crossoverCount, int pruneCount) {
        this.sequenceLength = sequenceLength;
        this.randomGeneGenerator = randomGeneGenerator;
        this.fitnessCalculator = fitnessCalculator;
        this.mutationProbability = mutationProbability;
        this.crossOverRate = crossOverRate;
        this.crossoverCount = crossoverCount;
        this.pruneCount = pruneCount;
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

    public Supplier<T> getRandomGeneGenerator() {
        return randomGeneGenerator;
    }

    public Function<List<T>, Integer> getFitnessCalculator() {
        return fitnessCalculator;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public double getCrossOverRate() {
        return crossOverRate;
    }

    public int getCrossoverCount() {
        return crossoverCount;
    }

    public int getPruneCount() {
        return pruneCount;
    }
}

class Population<T> {

    private final List<Entity<T>> individuals;
    private final Settings<T> settings;

    public Population(int populationSize, Settings<T> settings) {
        this.settings = settings;
        individuals = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            individuals.add(null);
        }
    }

    public void initializePopulation() {
        for (int i = 0; i < individuals.size(); i++) {
            var entity = new Entity<>(settings);
            saveEntity(i, entity);
        }
    }

    public List<Entity<T>> getFittests() {
        return individuals.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Entity::getFitness))
                .limit(settings.getPruneCount())
                .collect(Collectors.toList());

    }

    public void saveEntity(int index, Entity<T> entity) {
        individuals.set(index, entity);
    }

    public Entity<T> getEntity(int index) {
        return individuals.get(index);
    }

    public int size() {
        return individuals.size();
    }

    public Entity<T> getFittest() {
        return individuals.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Entity::getFitness)).orElseThrow();
    }

}


//not very efficient algorithm
class GeneticAlgorithm<T> {

    private final Settings<T> settings;

    private final Random random = new Random(System.nanoTime());

    public GeneticAlgorithm(Settings<T> settings) {
        this.settings = settings;
    }

    public Population<T> evolvePopulation(Population<T> population) {
        for (int i = 0; i < settings.getCrossoverCount(); i++) {
            Entity<T> first = randomSelection(population);
            Entity<T> second = randomSelection(population);
            Entity<T> child = null;
            if (first != null && second != null) {
                child = doCrossover(first, second);
            } else if (first != null) {
                child = first;
            } else if (second != null) {
                child = second;
            }
            population.saveEntity(i, child);
        }
        for (int i = 0; i < population.size(); i++) {
            mutate(population.getEntity(i));
        }
        List<Entity<T>> fittests = population.getFittests();
        Population<T> newPopulation = new Population<>(population.size(), settings);
        newPopulation.initializePopulation();
        for (int i = 0; i < fittests.size(); ++i) {
            newPopulation.saveEntity(i, fittests.get(i));
        }
        return newPopulation;
    }

    private void mutate(Entity<T> individual) {
        if (individual != null) {
            for (int i = 0; i < individual.size(); i++) {
                if (Math.random() <= settings.getMutationProbability()) {
                    individual.setGene(i, settings.getRandomGeneGenerator().get());
                }
            }
        }
    }

    private Entity<T> doCrossover(Entity<T> firstParent, Entity<T> secondParent) {
        Entity<T> child = new Entity<>(settings);
        for (int i = 0; i < firstParent.size(); i++) {
            if (Math.random() <= settings.getCrossOverRate()) {
                child.setGene(i, firstParent.getGene(i));
            } else {
                child.setGene(i, secondParent.getGene(i));
            }
        }
        return child;
    }

    private Entity<T> randomSelection(Population<T> population) {
        Population<T> newPopulation = new Population<>(30, settings);

        for (int i = 0; i < newPopulation.size(); i++) {
            int randomIndex = (int) (Math.random() * population.size());
            newPopulation.saveEntity(i, population.getEntity(randomIndex));
        }
        return newPopulation.getFittest();
    }

}

public class GeneticAlgorithmExample {

    private final Random random = new Random(System.nanoTime());
    private static final int[] SOLUTION_SEQUENCE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public void simulate() {
        int generationCount = 1000;
        int populationCount = 100;
        int geneSequenceLength = 10;
        var settings = new Settings<>(
                geneSequenceLength,
                () -> random.nextInt(geneSequenceLength),
                (List<Integer> l) -> {
                    int fitness = 0;
                    for (int i = 0; i < l.size(); ++i) {
                        if (Objects.equals(l.get(i), SOLUTION_SEQUENCE[i])) {
                            fitness++;
                        }
                    }
                    return fitness;
                },
                0.01, 0.1, populationCount / 2, 80
        );

        var geneticAlgorithm = new GeneticAlgorithm<>(settings);
        Population<Integer> population = new Population<>(populationCount, settings);
        population.initializePopulation();
        int generationCounter = 0;
        while (generationCounter < generationCount) {
            population = geneticAlgorithm.evolvePopulation(population);
            generationCounter++;
            System.out.println("Generation " + generationCounter + " fittest is " + population.getFittest().getFitness());
            System.out.println(population.getFittest());
            System.out.println();
        }

        System.out.println("Fittest: " + population.getFittest());

    }

    public void knapsack() {
        int generationCount = 10000;
        int populationCount = 100;
        int knapsackSize = 5;
        int knapsackCapacity = 10;

        List<Item> items = List.of(
                new Item("Mobile", 100, 3),
                new Item("Laptop", 700, 6),
                new Item("Book", 10, 1),
                new Item("Monitor", 300, 5),
                new Item("Mouse", 55, 1),
                new Item("Headphone", 30, 2)
        );

        var settings = new Settings<>(
                knapsackSize,
                () -> {
                    int index = random.nextInt(items.size() * 2);
                    if (index >= items.size()) {
                        return null;
                    }
                    return items.get(index);
                },
                (List<Item> l) -> {
                    int fitness = 0;
                    int weights = 0;
                    for (Item item : l) {
                        if (item != null) {
                            fitness += item.value;
                            weights += item.weight;
                        }
                    }
                    if (weights > knapsackCapacity) {
                        return Integer.MIN_VALUE;
                    }
                    return fitness;
                },
                0.01, 0.1, populationCount, populationCount
        );


        var geneticAlgorithm = new GeneticAlgorithm<>(settings);
        Population<Item> population = new Population<>(populationCount, settings);
        population.initializePopulation();
        int generationCounter = 0;
        while (generationCounter < generationCount) {
            population = geneticAlgorithm.evolvePopulation(population);
            generationCounter++;
            System.out.println("Generation " + generationCounter + " fittest is " + population.getFittest().getFitness());
            System.out.println(population.getFittest());
            System.out.println();
        }

        System.out.println("Fittest: " + population.getFittest());


    }

}

class Item {
    String name;
    int value;
    int weight;

    public Item(String name, int value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[" + name + ": price = " + value + ", weight: " + weight + "]";
    }
}
