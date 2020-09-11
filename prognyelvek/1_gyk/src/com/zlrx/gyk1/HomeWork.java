package com.zlrx.gyk1;

import java.math.BigInteger;
import java.util.Arrays;

public class HomeWork {

    private String[] numbers;

    public static void main(String[] args) {
        var hw = new HomeWork(args);
        hw.printValueIsPrime();
    }

    public HomeWork(String[] numbers) {
        this.numbers = numbers;
    }

    private void printValueIsPrime() {
        Arrays.stream(numbers)
                .map(Integer::parseInt)
                .peek(this::putNotPossiblePrimeToSerr)
                .filter(it -> it >= 2)
                .map(this::createResultStr)
                .forEach(System.out::println);
    }

    private void putNotPossiblePrimeToSerr(Integer integer) {
        if (integer < 2) {
            System.err.println(integer + " can't be prime as it is lower then the lowest possible prime.");
        }
    }

    private String createResultStr(Integer integer) {
        var prime = BigInteger.valueOf(integer).isProbablePrime(15);
        if (prime) {
            return integer + " is prime";
        } else {
            return integer + " is not prime";
        }
    }
}
