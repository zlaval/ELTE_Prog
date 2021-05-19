package com.zlrx.java.advanced.classes.cls20210421;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

class Parent implements Comparable<Parent> {
    protected String value;

    public Parent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Parent o) {
        return value.compareTo(o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parent parent = (Parent) o;
        return Objects.equals(value, parent.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

class Child extends Parent {

    public Child(String value) {
        super(value);
    }
}

public class Generics {


    @Test
    public void test() {
        BiMap<Integer, String> bm = BiMap.create();
        bm.put(3, "three");
        bm.put(1, "one");
        bm.put(4, "four");

        bm.forEach((k, v) -> System.out.println(k + " = " + v));

        var c1 = (Comparator<Integer>) (o1, o2) -> o2 - o1;
        Comparator<String> c2 = Comparator.reverseOrder();

        BiMap<Integer, String> bm2 = BiMap.create(c1, c2);
        bm2.put(3, "three");
        bm2.put(1, "one");
        bm2.put(4, "four");

        bm2.forEach((k, v) -> System.out.println(k + " = " + v));

        var children = List.of(new Child("zalan"), new Child("alex"), new Child("erik"));
        BiMap<Parent, String> bm3 = BiMap.create();
        bm3.putAll(
                children,
                List.of("three", "one", "two")
        );
        bm3.forEach((k, v) -> System.out.println(k + " = " + v));

    }

    @Test
    public void geneticTest() {
        GeneticAlgorithmExample ga = new GeneticAlgorithmExample();
        ga.simulate();
        System.out.println("Knapsack:");
        ga.knapsack();
    }


}
