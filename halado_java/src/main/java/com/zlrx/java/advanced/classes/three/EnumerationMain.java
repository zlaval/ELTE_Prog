package com.zlrx.java.advanced.classes.three;

import java.util.Arrays;

public class EnumerationMain {

    public static void main(String[] args) {
        Arrays.stream(City.values()).sequential().forEach(System.out::println);
    }

}
