package com.zlrx.java.advanced.classes.four;

import org.junit.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Lambda {

    @Test
    public void first() {

        var getNextLong = new Supplier<>() {
            Long number = 0L;

            @Override
            public Long get() {
                return ++number;
            }
        };

        assertEquals(Long.valueOf(1), getNextLong.get());
        assertEquals(Long.valueOf(2), getNextLong.get());
        assertEquals(Long.valueOf(3), getNextLong.get());
        assertEquals(Long.valueOf(4), getNextLong.get());
    }

    @Test
    public void second() {
        Consumer<Integer> writeMultipleTimes = (number) ->
                IntStream.range(0, number)
                        .forEach(i -> System.out.println(number));

        writeMultipleTimes.accept(10);
    }

    @Test
    public void third() {
        Function<Integer, Long> factorial = (num) -> LongStream.range(1, num + 1).reduce(1, (a, b) -> a * b);
        assertEquals(Long.valueOf(24), factorial.apply(4));
    }

    @Test
    public void fourth() {
        Function<Long, Long> fibonacci = (num) -> {
            if (num <= 1) {
                return num;
            }
            Long result = 1L;
            Long prevResult = 1L;
            for (int i = 2; i < num; i++) {
                Long tmp = result;
                result += prevResult;
                prevResult = tmp;
            }
            return result;
        };

        assertEquals(Long.valueOf(1), fibonacci.apply(10L));

    }

    @Test
    public void fifth() {
        BiFunction<Map<String, Integer>, Map<String, Integer>, Map<String, Integer>> sumIntToText = (first, second) -> {
            var result = new HashMap<>(first);
            second.forEach((k, v) -> {
                result.computeIfPresent(k, (k2, v2) -> v2 + v);
                result.putIfAbsent(k, v);
            });
            return result;
        };

        var firstMap = Map.of("a", 2, "b", 3, "c", 10);
        var secondMap = Map.of("a", 5, "c", 1, "d", 5);

        var result = sumIntToText.apply(firstMap, secondMap);
        assertEquals(Integer.valueOf(7), result.get("a"));
        assertEquals(Integer.valueOf(3), result.get("b"));
        assertEquals(Integer.valueOf(11), result.get("c"));
        assertEquals(Integer.valueOf(5), result.get("d"));
    }

    @Test
    public void sixth() {
        Predicate<Integer> isPrime = (number) -> BigInteger.valueOf(number).isProbablePrime(1);
        assertFalse(isPrime.test(10));
        assertTrue(isPrime.test(7));
    }

    @Test
    public void seventh() {
        HashMap<String, String>m=new HashMap<>();
        m.put("a","a")
                m.entrySet()
        Supplier<Long> nextPrimes = new Supplier<>() {
            Long lastPrime = 1L;

            @Override
            public Long get() {
                lastPrime = BigInteger.valueOf(lastPrime).nextProbablePrime().longValue();
                return lastPrime;
            }
        };

        assertEquals(Long.valueOf(2), nextPrimes.get());
        assertEquals(Long.valueOf(3), nextPrimes.get());
        assertEquals(Long.valueOf(5), nextPrimes.get());
        assertEquals(Long.valueOf(7), nextPrimes.get());


    }


}
